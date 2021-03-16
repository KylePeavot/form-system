package co600.weffs.application.internal.services.formResponse;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.model.form.backend.BackendComponentProps;
import co600.weffs.application.internal.model.form.frontend.FrontendComponent;
import co600.weffs.application.internal.model.form.frontend.FrontendComponentProps;
import co600.weffs.application.internal.model.form.frontend.FrontendComponentTypes;
import co600.weffs.application.internal.model.form.frontend.FrontendForm;
import co600.weffs.application.internal.model.form.frontend.FrontendSelectionValue;
import co600.weffs.application.internal.model.form.frontend.FrontendTextValue;
import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.model.formResponse.FormResponseDetail;
import co600.weffs.application.internal.model.formResponse.QuestionResponse;
import co600.weffs.application.internal.repository.formResponse.FormResponseDetailRepository;
import co600.weffs.application.internal.model.team.TeamDetail;
import co600.weffs.application.internal.repository.formResponse.FormResponseRepository;
import co600.weffs.application.internal.services.form.QuestionDetailService;
import co600.weffs.application.internal.services.team.TeamMemberService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormResponseService {

  private final FormResponseRepository formResponseRepository;
  private final FormResponseDetailRepository formResponseDetailRepository;
  private final FormResponseDetailService formResponseDetailService;
  private final QuestionDetailService questionDetailService;
  private final QuestionResponseService questionResponseService;
  private FormResponseRepository formResponseRepository;
  private TeamMemberService teamMemberService;

  @Autowired
  public FormResponseService(FormResponseRepository formResponseRepository, FormResponseDetailRepository formResponseDetailRepository, FormResponseDetailService formResponseDetailService,
      QuestionDetailService questionDetailService, QuestionResponseService questionResponseService) {
    this.formResponseRepository = formResponseRepository;
    this.formResponseDetailRepository = formResponseDetailRepository;
    this.formResponseDetailService = formResponseDetailService;
    this.questionDetailService = questionDetailService;
    this.questionResponseService = questionResponseService;
  }

  public FormResponse create(String filler, String assigner, TeamDetail assignerTeamDetail, FormDetail formDetail) {
    FormResponse newFormResponse = new FormResponse();

    newFormResponse.setFormDetail(formDetail);
    newFormResponse.setAssignedTo(filler);
    newFormResponse.setAssignedTimestamp(Instant.now());
    newFormResponse.setAssignedBy(teamMemberService.getTeamMemberFromUsernameAndTeamDetail(assigner, assignerTeamDetail));
    newFormResponse.setAssignedByTeamDetail(assignerTeamDetail);

    return formResponseRepository.save(newFormResponse);
  }

  public void submitFormResponse(FrontendForm frontendForm, int formResponseId) {
    FormResponse formResponse = getFormResponseById(formResponseId);
    FormResponseDetail newFormResponseDetail = new FormResponseDetail();
    Optional<FormResponseDetail> oldFormResponseDetailOptional = formResponseDetailService.findCurrentDetailByFormResponse(formResponse);

    oldFormResponseDetailOptional.ifPresentOrElse(oldFormResponseDetail -> {
      oldFormResponseDetail.setStatusControl(false);
      formResponseDetailService.save(oldFormResponseDetail);

      newFormResponseDetail.setFormResponse(formResponse);
      //TODO FS-86 remove this once FS-52 is merged
      newFormResponseDetail.setAssigner(oldFormResponseDetail.getAssigner());
      //TODO FS-86 and this
      newFormResponseDetail.setAssignerTeamDetail(oldFormResponseDetail.getAssignerTeamDetail());
      newFormResponseDetail.setLastUpdatedTimestamp(Instant.now());
      newFormResponseDetail.setStatusControl(true);
    }, () -> {
      newFormResponseDetail.setFormResponse(formResponse);
      //TODO FS-86 remove this once FS-52 is merged
      newFormResponseDetail.setAssigner(null);
      //TODO FS-86 and this
      newFormResponseDetail.setAssignerTeamDetail(null);
      newFormResponseDetail.setLastUpdatedTimestamp(Instant.now());
      newFormResponseDetail.setStatusControl(true);
    });

    for (FrontendComponent frontendComponent : frontendForm.get_componentList()) {

      if (FrontendComponentTypes.hasSingleNestedQuestion((frontendComponent.get_componentType()))) {
        QuestionResponse questionResponse = new QuestionResponse();
        FrontendSelectionValue nestedQuestionSelectorValue = (FrontendSelectionValue) frontendComponent.get_componentProps().get(FrontendComponentProps.SELECTION_VALUE.getFrontendName());

        questionResponse.setResponse(String.valueOf(nestedQuestionSelectorValue.is_value()));
        questionResponse.setQuestionDetail(questionDetailService.getQuestionDetailById(nestedQuestionSelectorValue.get_questionDetailId()));
        questionResponse.setFormResponseDetail(newFormResponseDetail);

        questionResponseService.save(questionResponse);
      } else if (FrontendComponentTypes.hasMultipleNestedQuestions(frontendComponent.get_componentType())) {
        ArrayList<FrontendSelectionValue> nestedQuestionSelectorValues = (ArrayList<FrontendSelectionValue>) frontendComponent.get_componentProps().get(FrontendComponentProps.SELECTION_VALUES.getFrontendName());

        for (FrontendSelectionValue frontendSelectionValue : nestedQuestionSelectorValues) {
          QuestionResponse questionResponse = new QuestionResponse();

          questionResponse.setResponse(String.valueOf(frontendSelectionValue.is_value()));
          questionResponse.setFormResponseDetail(newFormResponseDetail);
          questionResponse.setQuestionDetail(questionDetailService.getQuestionDetailById(frontendSelectionValue.get_questionDetailId()));

          questionResponseService.save(questionResponse);
        }
      } else if (FrontendComponentTypes.isText(frontendComponent.get_componentType())) {
        QuestionResponse questionResponse = new QuestionResponse();

        FrontendTextValue frontendTextValue = (FrontendTextValue) frontendComponent.get_componentProps().get(FrontendComponentProps.TEXT_VALUE);

        questionResponse.setResponse(frontendTextValue.get_value());
        questionResponse.setFormResponseDetail(newFormResponseDetail);
        questionResponse.setQuestionDetail(questionDetailService.getQuestionDetailById((Integer) frontendComponent.get_componentProps().get(BackendComponentProps.QUESTION_DETAIL_ID.getName())));
      }
    }
  }

  public FormResponse getFormResponseById(int id) {
    return formResponseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No FormResponse found with id: " + id));
  }
}
