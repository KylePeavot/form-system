package co600.weffs.application.internal.services.form;

import co600.weffs.application.internal.model.error.NoQuestionFoundException;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.model.form.frontend.FrontendComponent;
import co600.weffs.application.internal.model.form.frontend.FrontendComponentTypes;
import co600.weffs.application.internal.model.form.frontend.FrontendForm;
import co600.weffs.application.internal.model.form.frontend.FrontendSelectionValue;
import co600.weffs.application.internal.model.form.QuestionDetail;
import co600.weffs.application.internal.model.form.frontend.FrontendTextValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FrontendFormService {


  private QuestionDetailService questionDetailService;
  private QuestionService questionService;

  @Autowired
  public FrontendFormService(QuestionDetailService questionDetailService,
      QuestionService questionService) {
    this.questionDetailService = questionDetailService;
    this.questionService = questionService;
  }

  public FrontendForm getFrontendFormFromFormDetailId(int formDetailId) {
    List<FrontendComponent> frontendComponents = new ArrayList<>();

    //partitioningBy returns two lists of QuestionDetail. The elements are added to the second list if the expression in partitioningBy is true, the first if false
    List<List<QuestionDetail>> questionsAndNestedQuestions = new ArrayList<>(
        questionService.getQuestionsForFormDetailId(formDetailId).stream()
        .map(question -> questionDetailService.getCurrentQuestionDetailByQuestionId(question.getId()))
        .collect(Collectors.partitioningBy(o -> FrontendComponentTypes.NESTED_QUESTION.getComponentType().equals(o.getQuestionType())))
        .values()
    );

    List<QuestionDetail> questions = questionsAndNestedQuestions.get(0);
    List<QuestionDetail> nestedQuestions = questionsAndNestedQuestions.get(1);

    for (QuestionDetail questionDetail : questions) {
      Map<String, Object> componentProps = new HashMap<>();
      componentProps.put("title", questionDetail.getTitle());
      componentProps.put("guidance", questionDetail.getGuidance());
      componentProps.put("questionDetailId", questionDetail.getId());

      if (FrontendComponentTypes.hasSingleNestedQuestion(questionDetail.getQuestionType())) {
        try {
          QuestionDetail nestedQuestion = nestedQuestions.stream()
            .filter(nestedQuestionDetail -> nestedQuestionDetail.getParentQuestion().getId().equals(questionDetail.getId()))
            .findFirst()
            .orElseThrow(() -> new NoQuestionFoundException("No nested question found for QuestionDetail with id: " + questionDetail.getId()));

            componentProps.put("selectionValue", new FrontendSelectionValue(nestedQuestion.getTitle(), false, nestedQuestion.getId()));
        } catch (NoQuestionFoundException e) {
          e.printStackTrace();
        }
      } else if (FrontendComponentTypes.hasMultipleNestedQuestions(questionDetail.getQuestionType())) {
        ArrayList<FrontendSelectionValue> frontendSelectionValues = new ArrayList<>();
        nestedQuestions.stream()
            .filter(nestedQuestionDetail -> nestedQuestionDetail.getParentQuestion().getId().equals(questionDetail.getId()))
            .forEach(nestedQuestionDetail -> frontendSelectionValues.add(new FrontendSelectionValue(nestedQuestionDetail.getTitle(), false, nestedQuestionDetail.getId())));
        componentProps.put("selectionValues", frontendSelectionValues);
      } else if (FrontendComponentTypes.isText(questionDetail.getQuestionType())) {
        componentProps.put("textValue", new FrontendTextValue(""));
      }

      FrontendComponent componentToAdd = new FrontendComponent(questionDetail.getQuestionType(), componentProps, questionDetail.getOrderNumber());

      frontendComponents.add(componentToAdd);
    }

    FrontendForm frontendForm = new FrontendForm();
    //TODO FS-52 merge in name branch
    frontendForm.set_name("Form name");
    frontendForm.set_componentList(frontendComponents);

    return frontendForm;
  }

}
