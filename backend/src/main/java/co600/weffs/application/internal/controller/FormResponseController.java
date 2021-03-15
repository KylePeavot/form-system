package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.form.frontend.FrontendForm;
import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.services.form.FrontendFormService;
import co600.weffs.application.internal.services.formResponse.FormResponseService;
import co600.weffs.application.internal.services.formResponse.QuestionResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/form-response")
public class FormResponseController {
  private final FormResponseService formResponseService;
  private final FrontendFormService frontendFormService;
  private final QuestionResponseService questionResponseService;

  @Autowired
  public FormResponseController(
      FormResponseService formResponseService,
      FrontendFormService frontendFormService,
      QuestionResponseService questionResponseService) {
    this.formResponseService = formResponseService;
    this.frontendFormService = frontendFormService;
    this.questionResponseService = questionResponseService;
  }

  @GetMapping("/get/{formResponseId}")
  public FrontendForm getForm(@PathVariable("formResponseId") int formResponseId) {
    FormResponse formResponse = formResponseService.getFormResponseById(formResponseId);

    FrontendForm frontendForm = frontendFormService.getFrontendFormFromFormDetailId(formResponse.getFormDetail().getId());

    questionResponseService.loadQuestionResponsesIntoFrontendForm(frontendForm, formResponse);

    return frontendForm;
  }

  @PostMapping("/submit/{formResponseId}")
  public void submitFormResponse(@RequestBody FrontendForm frontendForm, @RequestParam("formResponseId") int formResponseId) {
    formResponseService.submitFormResponse(frontendForm, formResponseId);
  }

  @PostMapping("/save-draft/{formResponseId}")
  public void saveDraftFormResponse(@RequestBody FrontendForm frontendForm, @RequestParam("formResponseId") int formResponseId) {
//    formResponseService.submitFormResponseAsDraft();
  }

}
