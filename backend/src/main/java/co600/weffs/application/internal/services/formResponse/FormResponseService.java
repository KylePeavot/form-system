package co600.weffs.application.internal.services.formResponse;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.model.form.backend.BackendComponentProps;
import co600.weffs.application.internal.model.form.frontend.FrontendComponent;
import co600.weffs.application.internal.model.form.frontend.FrontendComponentProps;
import co600.weffs.application.internal.model.form.frontend.FrontendComponentTypes;
import co600.weffs.application.internal.model.form.frontend.FrontendForm;
import co600.weffs.application.internal.model.form.frontend.FrontendSelectionValue;
import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.model.formResponse.FormResponseDetail;
import co600.weffs.application.internal.model.formResponse.QuestionResponse;
import co600.weffs.application.internal.model.team.TeamDetail;
import co600.weffs.application.internal.repository.formResponse.FormResponseDetailRepository;
import co600.weffs.application.internal.repository.formResponse.FormResponseRepository;
import co600.weffs.application.internal.services.form.QuestionDetailService;
import co600.weffs.application.internal.services.team.TeamMemberService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import net.bytebuddy.dynamic.scaffold.MethodGraph.Linked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormResponseService {

  private final FormResponseRepository formResponseRepository;
  private final FormResponseDetailRepository formResponseDetailRepository;
  private final FormResponseDetailService formResponseDetailService;
  private final QuestionDetailService questionDetailService;
  private final QuestionResponseService questionResponseService;
  private final TeamMemberService teamMemberService;

  @Autowired
  public FormResponseService(FormResponseRepository formResponseRepository, FormResponseDetailRepository formResponseDetailRepository, FormResponseDetailService formResponseDetailService,
      QuestionDetailService questionDetailService, QuestionResponseService questionResponseService, TeamMemberService teamMemberService) {
    this.formResponseRepository = formResponseRepository;
    this.formResponseDetailRepository = formResponseDetailRepository;
    this.formResponseDetailService = formResponseDetailService;
    this.questionDetailService = questionDetailService;
    this.questionResponseService = questionResponseService;
    this.teamMemberService = teamMemberService;
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

  @Transactional
  public void saveResponseToForm(FrontendForm frontendForm, FormResponse formResponse) {
    FormResponseDetail newFormResponseDetail = new FormResponseDetail();
    Optional<FormResponseDetail> oldFormResponseDetailOptional = formResponseDetailService.findCurrentDetailByFormResponse(formResponse);

    oldFormResponseDetailOptional.ifPresentOrElse(oldFormResponseDetail -> {
      oldFormResponseDetail.setStatusControl(false);
      formResponseDetailService.save(oldFormResponseDetail);

      newFormResponseDetail.setFormResponse(formResponse);
      newFormResponseDetail.setLastUpdatedTimestamp(Instant.now());
      newFormResponseDetail.setStatusControl(true);
    },
    () -> {
      newFormResponseDetail.setFormResponse(formResponse);
      newFormResponseDetail.setLastUpdatedTimestamp(Instant.now());
      newFormResponseDetail.setStatusControl(true);
    });

    formResponseDetailService.save(newFormResponseDetail);

    for (FrontendComponent frontendComponent : frontendForm.get_componentList()) {

      if (FrontendComponentTypes.hasSingleNestedQuestion((frontendComponent.get_componentType()))) {
        QuestionResponse questionResponse = new QuestionResponse();
        FrontendSelectionValue nestedQuestionSelectorValue = FrontendSelectionValue.fromLinkedHashMap((LinkedHashMap) frontendComponent.get_componentProps().get(FrontendComponentProps.SELECTION_VALUE.getFrontendName()));

        questionResponse.setResponse(String.valueOf(nestedQuestionSelectorValue.is_value()));
        questionResponse.setQuestionDetail(questionDetailService.getQuestionDetailById(nestedQuestionSelectorValue.get_questionDetailId()));
        questionResponse.setFormResponseDetail(newFormResponseDetail);

        questionResponseService.save(questionResponse);
      } else if (FrontendComponentTypes.hasMultipleNestedQuestions(frontendComponent.get_componentType())) {
        List<FrontendSelectionValue> nestedQuestionSelectorValues = ((ArrayList<LinkedHashMap>) frontendComponent.get_componentProps().get(FrontendComponentProps.SELECTION_VALUES.getFrontendName())).stream()
            .map(linkedHashMap -> FrontendSelectionValue.fromLinkedHashMap((linkedHashMap)))
            .collect(Collectors.toList());

        for (FrontendSelectionValue frontendSelectionValue : nestedQuestionSelectorValues) {
          QuestionResponse questionResponse = new QuestionResponse();

          questionResponse.setResponse(String.valueOf(frontendSelectionValue.is_value()));
          questionResponse.setFormResponseDetail(newFormResponseDetail);
          questionResponse.setQuestionDetail(questionDetailService.getQuestionDetailById(frontendSelectionValue.get_questionDetailId()));

          questionResponseService.save(questionResponse);
        }
      } else if (FrontendComponentTypes.isText(frontendComponent.get_componentType())) {
        QuestionResponse questionResponse = new QuestionResponse();

        String response = ((LinkedHashMap<String, String>) frontendComponent.get_componentProps().get(FrontendComponentProps.TEXT_VALUE.getFrontendName())).get("_value");

        questionResponse.setResponse(response);
        questionResponse.setFormResponseDetail(newFormResponseDetail);
        questionResponse.setQuestionDetail(questionDetailService.getQuestionDetailById((Integer) frontendComponent.get_componentProps().get(BackendComponentProps.QUESTION_DETAIL_ID.getName())));

        questionResponseService.save(questionResponse);
      } else if (FrontendComponentTypes.isDate(frontendComponent.get_componentType())) {
        QuestionResponse questionResponse = new QuestionResponse();

        String response = ((LinkedHashMap<String, String>) frontendComponent.get_componentProps().get(FrontendComponentProps.DATE_VALUE.getFrontendName())).get("_value");

        questionResponse.setResponse(response);
        questionResponse.setFormResponseDetail(newFormResponseDetail);
        questionResponse.setQuestionDetail(questionDetailService.getQuestionDetailById((Integer) frontendComponent.get_componentProps().get(BackendComponentProps.QUESTION_DETAIL_ID.getName())));

        questionResponseService.save(questionResponse);
      }
    }
  }

  public FormResponse getFormResponseById(int id) {
    return formResponseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No FormResponse found with id: " + id));
  }
}
