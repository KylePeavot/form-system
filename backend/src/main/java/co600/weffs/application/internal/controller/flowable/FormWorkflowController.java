package co600.weffs.application.internal.controller.flowable;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.service.flowable.FormWorkflowService;
import co600.weffs.application.internal.services.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api/flowable/workflow")
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

  //    Assign form -> starting process (which should auto go to assign form to form filler which should then go to Form received to be filled in)

  @MustBeAuthorized
  @PostMapping("/start")
  public void assignFormToUser(@RequestAttribute("User") AppUser appUser) {
    System.out.println("assignFormToUser");
    formWorkflowService.assignFormToFormFiller(appUser, appUser, formService.getFormById(1));
  }

//    Open form -> Filling form
  @PostMapping("open-form")
  public void openForm() {
    System.out.println("openForm");
  }

  //    Delete form
  @PostMapping("delete-form")
  public void deleteForm() {
    System.out.println("deleteForm");
  }

//    Submit form -> Form submitted -> Form received to review
  @PostMapping("submit-form")
  public void submitForm() {
    System.out.println("submitForm");
  }

//    Open review -> Reviewing form
  @PostMapping("open-review")
  public void openReview() {
    System.out.println("openReview");
  }

//    Withdraw form
  @PostMapping("withdraw-form")
  public void withdrawForm() {
    System.out.println("withdrawForm");
  }

//    Complete review -> Approved or rejected -> end process or go back to form received to be filled in.
  @PostMapping("complete-review")
  public void completeReview() {
    System.out.println("completeReview");
  }
}
