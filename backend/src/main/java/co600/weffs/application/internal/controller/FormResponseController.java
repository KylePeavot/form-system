package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.form.frontend.FrontendForm;
import co600.weffs.application.internal.services.form.FrontendFormService;
import co600.weffs.application.internal.services.formResponse.FormResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/form-response")
public class FormResponseController {


  private FormResponseService formResponseService;
  private FrontendFormService frontendFormService;

  @Autowired
  public FormResponseController(
      FormResponseService formResponseService,
      FrontendFormService frontendFormService) {
    this.formResponseService = formResponseService;
    this.frontendFormService = frontendFormService;
  }

  @GetMapping("/get/{formResponseId}")
  public FrontendForm getForm(@PathVariable("formResponseId") int formResponseId) {
    return frontendFormService.getFrontendFormFromFormDetailId(
        formResponseService.getFormResponseById(formResponseId).getFormDetail().getId()
    );
  }

}
