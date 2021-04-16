package co600.weffs.application.internal.services.form;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.error.NotAuthorizedException;
import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.model.form.Question;
import co600.weffs.application.internal.model.form.QuestionDetail;
import co600.weffs.application.internal.model.form.frontend.FrontendComponent;
import co600.weffs.application.internal.model.form.frontend.FrontendComponentProps;
import co600.weffs.application.internal.model.form.frontend.FrontendComponentTypes;
import co600.weffs.application.internal.model.form.frontend.FrontendForm;
import co600.weffs.application.internal.services.team.TeamMemberService;
import co600.weffs.application.internal.services.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class FormCreationService {
    @Qualifier(value = "WeffsFormService")
    private final FormService formService;
    private final FormDetailService formDetailService;
    private final QuestionService questionService;
    private final QuestionDetailService questionDetailService;
    private final TeamMemberService teamMemberService;
    private final TeamService teamService;

    @Autowired
    public FormCreationService(FormService formService, FormDetailService formDetailService, QuestionService questionService, QuestionDetailService questionDetailService, TeamMemberService teamMemberService, TeamService teamService) {
        this.formService = formService;
        this.formDetailService = formDetailService;
        this.questionService = questionService;
        this.questionDetailService = questionDetailService;
        this.teamMemberService = teamMemberService;
        this.teamService = teamService;
    }

    @Transactional
    public void createForm(AppUser appUser, FrontendForm frontendForm) {
        var form = new Form();
        form.setCreatedBy(appUser.getUsername());
        form.setCreatedTimestamp(Instant.now());
        formService.save(form);

        var formDetail = new FormDetail();
        formDetail.setForm(form);
        formDetail.setName(frontendForm.get_name());
        formDetail.setLastUpdatedBy(appUser.getUsername());
        formDetail.setLastUpdatedTimestamp(Instant.now());
        formDetail.setStatusControl(true);
        var teamView = teamMemberService.getTeamViewById(frontendForm.get_team().getTeamId());
        if (!teamMemberService.canUserModifyFormsForTeamView(appUser, teamView)) {
            throw new NotAuthorizedException(String.format("User [%s] is not authorized to create a form for this team", appUser.getUsername()));
        }
        formDetail.setTeam(teamService.getById(teamView.getTeamId()));
        formDetailService.save(formDetail);
        frontendForm.get_componentList().forEach(frontendComponent -> createQuestion(appUser, frontendComponent, formDetail));
    }

    public void createQuestion(AppUser appUser, FrontendComponent frontendComponent, FormDetail formDetail) {

        var question = new Question();
        question.setCreatedBy(appUser.getUsername());
        question.setCreatedTimestamp(Instant.now());
        question.setFormDetail(formDetail);
        questionService.save(question);

        var questionDetail = new QuestionDetail();
        questionDetail.setGuidance((String) frontendComponent.get_componentProps().get("guidance"));
        questionDetail.setLastUpdatedBy(appUser.getUsername());
        questionDetail.setLastUpdatedTimestamp(Instant.now());
        questionDetail.setOrderNumber(frontendComponent.get_order());
        //TODO FS-38 make possible to reveal more questions depending on checkbox/radio state

        if (FrontendComponentTypes.NESTED_QUESTION.getComponentType().equals(frontendComponent.get_componentType())) {
            questionDetail.setParentQuestion(questionService.getQuestionById((Integer) frontendComponent.get_componentProps().get("parentQuestionId")));
        }

        questionDetail.setQuestion(question);
        questionDetail.setQuestionType(frontendComponent.get_componentType());
        questionDetail.setStatusControl(true);
        questionDetail.setTitle((String) frontendComponent.get_componentProps().get("title"));
        questionDetailService.save(questionDetail);

        if (isQuestionSingleNested(frontendComponent)) {
            HashMap<String, ?> nestedQuestionSelectorValue = (HashMap<String, ?>) frontendComponent.get_componentProps().get(
                FrontendComponentProps.SELECTION_VALUE.getFrontendName());

            var subQuestionFrontendComponent = new FrontendComponent(
                FrontendComponentTypes.NESTED_QUESTION.getComponentType(),
                Map.of("title", nestedQuestionSelectorValue.get("_label"), "parentQuestionId", question.getId()),
                0 //on a single checkbox question, we don't care what the order is
            );

            createQuestion(appUser, subQuestionFrontendComponent, formDetail);
        } else if (isQuestionMultiNested(frontendComponent)) {
            int order = 0;
            ArrayList<HashMap<String, ?>> nestedQuestionSelectorValues = (ArrayList<HashMap<String, ?>>) frontendComponent.get_componentProps().get(FrontendComponentProps.SELECTION_VALUES.getFrontendName());

            for (HashMap<String, ?> nestedQuestion : nestedQuestionSelectorValues) {
                var subQuestionFrontendComponent = new FrontendComponent(
                    FrontendComponentTypes.NESTED_QUESTION.getComponentType(),
                    Map.of("title", nestedQuestion.get("_label"), "parentQuestionId", question.getId()),
                    order-- //we want to preserve the order they came in as but don't want to interfere with other question's order
                );

                createQuestion(appUser, subQuestionFrontendComponent, formDetail);
            }
        }
    }

    public boolean isQuestionSingleNested(FrontendComponent frontendComponent) {
        return FrontendComponentTypes.CHECKBOX_QUESTION.getComponentType().equals(frontendComponent.get_componentType());
    }

    public boolean isQuestionMultiNested(FrontendComponent frontendComponent) {
        return FrontendComponentTypes.CHECKBOX_GROUP.getComponentType().equals(frontendComponent.get_componentType())
            || FrontendComponentTypes.RADIO_GROUP.getComponentType().equals(frontendComponent.get_componentType());
    }


}
