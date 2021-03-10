package co600.weffs.application.internal.services.form;

import co600.weffs.application.internal.model.error.NoNestedQuestionFoundException;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.model.form.FrontendComponent;
import co600.weffs.application.internal.model.form.FrontendComponentTypes;
import co600.weffs.application.internal.model.form.FrontendForm;
import co600.weffs.application.internal.model.form.FrontendSelectionValue;
import co600.weffs.application.internal.model.form.QuestionDetail;
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
  private FormDetailService formDetailService;

  @Autowired
  public FrontendFormService(QuestionDetailService questionDetailService,
      QuestionService questionService,
      FormDetailService formDetailService) {
    this.questionDetailService = questionDetailService;
    this.questionService = questionService;
    this.formDetailService = formDetailService;
  }

  public FrontendForm getFrontendFormFromFormDetailId(int formDetailId) {
    FormDetail formDetail = formDetailService.getFormDetailById(formDetailId);

    //partitioningBy returns two lists of T. The elements are added to the second list if the expression in partitioningBy is true, the first if false
    List<List<QuestionDetail>> questionsAndNestedQuestions = new ArrayList<>(
        questionService.getQuestionsForFormDetailId(formDetailId).stream()
        .map(question -> questionDetailService.getCurrentQuestionDetailByQuestionId(question.getId()))
        .collect(Collectors.partitioningBy(o -> FrontendComponentTypes.NESTED_QUESTION.getComponentType().equals(o.getQuestionType())))
        .values()
    );

    List<QuestionDetail> questions = questionsAndNestedQuestions.get(0);
    List<QuestionDetail> nestedQuestions = questionsAndNestedQuestions.get(1);

    List<FrontendComponent> frontendComponents = new ArrayList<>();

    for (QuestionDetail questionDetail : questions) {
      FrontendComponent componentToAdd = new FrontendComponent();

      Map<String, Object> componentProps = new HashMap<>();
      componentProps.put("title", questionDetail.getTitle());
      componentProps.put("guidance", questionDetail.getGuidance());

      if (FrontendComponentTypes.hasSingleNestedQuestion(questionDetail.getQuestionType())) {

        QuestionDetail nestedQuestion = nestedQuestions.stream()
          .filter(nestedQuestionDetail -> nestedQuestionDetail.getParentQuestion().getId().equals(questionDetail.getId()))
          .findFirst()
          .get();

        componentProps.put("selectionValue", new FrontendSelectionValue(nestedQuestion.getTitle(), false));


      } else if (FrontendComponentTypes.hasMultipleNestedQuestions(questionDetail.getQuestionType())) {
        ArrayList<FrontendSelectionValue> frontendSelectionValues = new ArrayList<>();
        nestedQuestions.stream()
            .filter(nestedQuestionDetail -> nestedQuestionDetail.getParentQuestion().getId().equals(questionDetail.getId()))
            .forEach(nestedQuestionDetail -> frontendSelectionValues.add(new FrontendSelectionValue(nestedQuestionDetail.getTitle(), false)));
        componentProps.put("selectionValues", frontendSelectionValues);
      }

      componentToAdd.set_componentType(questionDetail.getQuestionType());
      componentToAdd.set_componentProps(componentProps);
      componentToAdd.set_order(questionDetail.getOrderNumber());

      frontendComponents.add(componentToAdd);
    }

    FrontendForm frontendForm = new FrontendForm();
    //TODO FS-52 merge in name branch
    frontendForm.set_name("Form name");
    frontendForm.set_componentList(frontendComponents);

    return frontendForm;
  }

}
