package co600.weffs.application.internal.services.formResponse;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.error.NoComponentTypeFoundException;
import co600.weffs.application.internal.model.form.QuestionDetail;
import co600.weffs.application.internal.model.form.backend.BackendComponentProps;
import co600.weffs.application.internal.model.form.frontend.FrontendComponent;
import co600.weffs.application.internal.model.form.frontend.FrontendComponentProps;
import co600.weffs.application.internal.model.form.frontend.FrontendComponentTypes;
import co600.weffs.application.internal.model.form.frontend.FrontendForm;
import co600.weffs.application.internal.model.form.frontend.FrontendSelectionValue;
import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.model.formResponse.FormResponseDetail;
import co600.weffs.application.internal.model.formResponse.QuestionResponse;
import co600.weffs.application.internal.repository.formResponse.QuestionResponseRepository;
import co600.weffs.application.internal.services.form.FrontendFormService;
import co600.weffs.application.internal.services.form.QuestionDetailService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionResponseService {

  private final QuestionResponseRepository questionResponseRepository;
  private final FormResponseDetailService formResponseDetailService;
  private final FrontendFormService frontendFormService;
  private final QuestionDetailService questionDetailService;

  @Autowired
  public QuestionResponseService(QuestionResponseRepository questionResponseRepository, FormResponseDetailService formResponseDetailService, FrontendFormService frontendFormService,
      QuestionDetailService questionDetailService) {
    this.questionResponseRepository = questionResponseRepository;
    this.formResponseDetailService = formResponseDetailService;
    this.frontendFormService = frontendFormService;
    this.questionDetailService = questionDetailService;
  }

  public void loadQuestionResponsesIntoFrontendForm(FrontendForm frontendForm, FormResponse formResponse) {
    FormResponseDetail formResponseDetail = formResponseDetailService.findCurrentDetailByFormResponse(formResponse)
        .orElseThrow(() -> new EntityNotFoundException("No FormResponseDetail found for FormResponse with id: " + formResponse.getId()));

    List<QuestionResponse> questionResponses = questionResponseRepository.findAllByFormResponseDetail(formResponseDetail);

    for (QuestionResponse questionResponse : questionResponses) {

      String questionResponseType = questionResponse.getQuestionDetail().getQuestionType();

      try {
        if (FrontendComponentTypes.isNestedQuestion(questionResponseType)) {
          QuestionDetail parentQuestionDetail = questionDetailService.getCurrentQuestionDetailByQuestionId(questionResponse.getQuestionDetail().getParentQuestion().getId());

          //Single checkbox
          if (FrontendComponentTypes.hasSingleNestedQuestion(parentQuestionDetail.getQuestionType())) {

            frontendForm.get_componentList().stream()
                .filter(frontendComponent -> frontendComponent.get_componentProps().get(BackendComponentProps.QUESTION_DETAIL_ID.getName()).equals(parentQuestionDetail.getId()))
                .map(frontendComponent -> frontendComponent.get_componentProps().put(FrontendComponentProps.SELECTION_VALUE.getFrontendName(), questionResponse.getResponse()));

            //checkbox/radio group
          } else if (FrontendComponentTypes.hasMultipleNestedQuestions(parentQuestionDetail.getQuestionType())) {
            // arrayList of FrontendSelectionValues
            frontendForm.get_componentList().stream()
              .filter(frontendComponent -> frontendComponent.get_componentProps().get(BackendComponentProps.QUESTION_DETAIL_ID.getName()).equals(parentQuestionDetail.getId()))
              .map(frontendComponent -> {
                  Map<String, Object> newComponentProps = frontendComponent.get_componentProps();

                  ((ArrayList<FrontendSelectionValue>) newComponentProps.get(FrontendComponentProps.SELECTION_VALUES.getFrontendName())).stream()
                      .filter(frontendSelectionValue -> frontendSelectionValue.get_label().equals(questionResponse.getQuestionDetail().getTitle()))
                      .map(frontendSelectionValue -> new FrontendSelectionValue(frontendSelectionValue.get_label(), Boolean.valueOf(questionResponse.getResponse()), frontendSelectionValue.get_questionDetailId()));

                  return new FrontendComponent(
                      frontendComponent.get_componentType(),
                      newComponentProps,
                      frontendComponent.get_order()
                  );
                }
              );
            //Text field/area
          } else if (FrontendComponentTypes.isText(questionResponseType)) {
            frontendForm.get_componentList().stream()
                .filter(frontendComponent -> frontendComponent.get_componentProps().get(BackendComponentProps.QUESTION_DETAIL_ID.getName()).equals(parentQuestionDetail.getId()))
                .map(frontendComponent -> frontendComponent.get_componentProps().put(FrontendComponentProps.TEXT_VALUE.getFrontendName(), questionResponse.getResponse()));

          } else {
            throw new NoComponentTypeFoundException("No matching component type found for QuestionResponse with id: " + questionResponse.getId());
          }
        }
      } catch (NoComponentTypeFoundException e) {
        e.printStackTrace();
      }

    }
  }

  public void save(QuestionResponse questionResponse) {
    questionResponseRepository.save(questionResponse);
  }
}






















