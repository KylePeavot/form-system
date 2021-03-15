package co600.weffs.application.internal.services.formResponse;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.model.form.frontend.FrontendComponent;
import co600.weffs.application.internal.model.form.frontend.FrontendComponentProps;
import co600.weffs.application.internal.model.form.frontend.FrontendComponentTypes;
import co600.weffs.application.internal.model.form.frontend.FrontendForm;
import co600.weffs.application.internal.model.form.frontend.FrontendSelectionValue;
import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.model.formResponse.FormResponseDetail;
import co600.weffs.application.internal.model.formResponse.QuestionResponse;
import co600.weffs.application.internal.repository.formResponse.FormResponseDetailRepository;
import co600.weffs.application.internal.repository.formResponse.FormResponseRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormResponseService {

  private final FormResponseRepository formResponseRepository;
  private final FormResponseDetailRepository formResponseDetailRepository;
  private final FormResponseDetailService formResponseDetailService;

  @Autowired
  public FormResponseService(FormResponseRepository formResponseRepository, FormResponseDetailRepository formResponseDetailRepository, FormResponseDetailService formResponseDetailService) {
    this.formResponseRepository = formResponseRepository;
    this.formResponseDetailRepository = formResponseDetailRepository;
    this.formResponseDetailService = formResponseDetailService;
  }

  public FormResponse create(String filler, FormDetail formDetail) {
    FormResponse newFormResponse = new FormResponse();

    newFormResponse.setFormDetail(formDetail);
    newFormResponse.setAssignedTo(filler);
    newFormResponse.setAssignedTimestamp(Instant.now());

    return formResponseRepository.save(newFormResponse);
  }

  public void submitFormResponse(FrontendForm frontendForm, int formResponseId) {
    FormResponseDetail formResponseDetail = formResponseDetailService.findCurrentDetailByFormResponse(getFormResponseById(formResponseId));

    for (FrontendComponent frontendComponent : frontendForm.get_componentList()) {
      QuestionResponse questionResponse = new QuestionResponse();
//      questionResponse.setResponse();
//      questionResponse.setFormResponseDetail();
//      questionResponse.setQuestionDetail();

      if (FrontendComponentTypes.hasSingleNestedQuestion((frontendComponent.get_componentType()))) {
        FrontendSelectionValue nestedQuestionSelectorValue = (FrontendSelectionValue) frontendComponent.get_componentProps().get(FrontendComponentProps.SELECTION_VALUE.getFrontendName());

//        var subQuestionFrontendComponent = new FrontendComponent(FrontendComponentTypes.NESTED_QUESTION.getComponentType(),
//            Map.of("title", nestedQuestionSelectorValue.get("_label"), "parentQuestionId", question.getId()), 0 //on a single checkbox question, we don't care what the order is
//        );
//
//        createQuestion(appUser, subQuestionFrontendComponent, formDetail);
      } else if (FrontendComponentTypes.hasMultipleNestedQuestions(frontendComponent.get_componentType())) {
        int order = 0;
        ArrayList<FrontendSelectionValue> nestedQuestionSelectorValues = (ArrayList<FrontendSelectionValue>) frontendComponent.get_componentProps().get(FrontendComponentProps.SELECTION_VALUES.getFrontendName());
//
//        for (HashMap<String, ?> nestedQuestion : nestedQuestionSelectorValues) {
//          var subQuestionFrontendComponent = new FrontendComponent(FrontendComponentTypes.NESTED_QUESTION.getComponentType(), Map.of("title", nestedQuestion.get("_label"), "parentQuestionId", question.getId()), order--
//              //we want to preserve the order they came in as but don't want to interfere with other question's order
//          );
//
//          createQuestion(appUser, subQuestionFrontendComponent, formDetail);
      } else if (FrontendComponentTypes.isText(frontendComponent.get_componentType())) {

      }


    }
    /*
    for each question in the form
    pull out the response
    match it up to a question detail in the formDetail attached to hte FormResponse_id
    save that to the DB
     */
  }

  public FormResponse getFormResponseById(int id) {
    return formResponseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No FormResponse found with id: " + id));
  }
}
