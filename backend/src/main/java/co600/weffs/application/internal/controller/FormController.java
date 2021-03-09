package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.form.FormView;
import co600.weffs.application.internal.model.form.FrontendForm;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.services.form.FormCreationService;
import co600.weffs.application.internal.services.form.FormDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/form")
public class FormController {
  private final FormCreationService formCreationService;
  private final FormDetailService formDetailService;

  @Autowired
  public FormController(FormCreationService formCreationService, FormDetailService formDetailService) {
    this.formCreationService = formCreationService;
    this.formDetailService = formDetailService;
  }

  @MustBeAuthorized
  @PostMapping("/save")
  public Map<String, ?> saveForm(@RequestAttribute("User") AppUser appUser, @RequestBody FrontendForm frontendForm) {
    formCreationService.createForm(appUser, frontendForm);
    return Map.of("success", true);
  }

  @MustBeAuthorized
  @GetMapping("/browse")
  public List<FormView> getForm(){
    return formDetailService.getActiveFormViews();
  }

  @MustBeAuthorized
  @GetMapping("/get/{formDetailId}")
  public Object getForm(@PathVariable("formDetailId") int formDetailId) {
    //TODO FS-52 add DB object for FormResponse
    //Should hold id, FormId, then figure out some way to store all the responses to the form
    //Once sorted, for a FormResponseId, get the formId

    //For a form id,
    // get the current form detail id,
    // from that get all questions associated,
    // then get all current question details for that question

//    FrontendForm = formService.getFrontendFormFromFormId(formId);

    return null;
  }

}



