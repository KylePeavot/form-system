package co600.weffs.application.internal.controller.flowable;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.flowable.FrontendWorkflowVariables;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.service.flowable.FormWorkflowService;
import co600.weffs.application.internal.services.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


//TODO FS-52 Update this class to use the id of a (new object called) FormSubmission
@RestController
@RequestMapping("api/flowable/workflow/form")
public class FormWorkflowController {

  @Autowired
  private FormWorkflowService formWorkflowService;

  @Autowired
  private FormService formService;

  @Autowired
  public FormWorkflowController(FormWorkflowService formWorkflowService, FormService formService) {
    this.formWorkflowService = formWorkflowService;
    this.formService = formService;
  }

  @MustBeAuthorized
  @PostMapping("/start")
  public void assignFormToUser(@RequestAttribute("User") AppUser appUser, @RequestBody FrontendWorkflowVariables frontendWorkflowVariables) {
    formWorkflowService.assignFormToFormFiller(
        appUser,
        frontendWorkflowVariables.get_targetUser(),
        formService.getFormById(frontendWorkflowVariables.get_formId())
    );
  }

  @MustBeAuthorized
  @PostMapping("/delete")
  public void deleteForm(@RequestAttribute("User") AppUser appUser, @RequestBody FrontendWorkflowVariables frontendWorkflowVariables) {
    formWorkflowService.deleteForm(appUser,
        formService.getFormById(frontendWorkflowVariables.get_formId())
    );
  }

  @MustBeAuthorized
  @PostMapping("submit")
  public void submitForm(@RequestAttribute("User") AppUser appUser, @RequestBody FrontendWorkflowVariables frontendWorkflowVariables) {
    formWorkflowService.submitForm(
        appUser,
        formService.getFormById(frontendWorkflowVariables.get_formId())
    );
  }
}
