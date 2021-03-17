package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.form.frontend.FrontendForm;
import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.services.flowable.FormWorkflowService;
import co600.weffs.application.internal.services.form.FrontendFormService;
import co600.weffs.application.internal.services.formResponse.FormResponseService;
import co600.weffs.application.internal.services.formResponse.QuestionResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/form-response")
public class FormResponseController {
  private final FormResponseService formResponseService;
  private final FrontendFormService frontendFormService;
  private final QuestionResponseService questionResponseService;
  private final FormWorkflowService formWorkflowService;

  @Autowired
  public FormResponseController(FormResponseService formResponseService, FrontendFormService frontendFormService, QuestionResponseService questionResponseService, FormWorkflowService formWorkflowService) {
    this.formResponseService = formResponseService;
    this.frontendFormService = frontendFormService;
    this.questionResponseService = questionResponseService;
    this.formWorkflowService = formWorkflowService;
  }

  @GetMapping("/get/{formResponseId}")
  public FrontendForm getForm(@PathVariable("formResponseId") int formResponseId) {
    FormResponse formResponse = formResponseService.getFormResponseById(formResponseId);

    FrontendForm frontendForm = frontendFormService.getFrontendFormFromFormDetailId(formResponse.getFormDetail().getId());

    questionResponseService.loadQuestionResponsesIntoFrontendForm(frontendForm, formResponse);

    return frontendForm;
  }

  @PostMapping("/submit/{formResponseId}")
  public void submitFormResponse(@RequestBody FrontendForm frontendForm, @PathVariable("formResponseId") int formResponseId) {
    FormResponse formResponse = formResponseService.getFormResponseById(formResponseId);

    formResponseService.saveResponseToForm(frontendForm, formResponse);
    //TODO FS-86 Uncomment this and make sure it works
//    formWorkflowService.submitFormResponse(formResponse.getAssignedTo(), formResponse);
  }

  @PostMapping("/save-draft/{formResponseId}")
  public void saveDraftFormResponse(@RequestBody FrontendForm frontendForm, @PathVariable("formResponseId") int formResponseId) {
    formResponseService.saveResponseToForm(frontendForm, formResponseService.getFormResponseById(formResponseId));
  }

}
