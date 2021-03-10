package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.form.FormView;
import co600.weffs.application.internal.model.form.FrontendForm;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.services.form.FormCreationService;
import co600.weffs.application.internal.services.form.FormDetailService;
import co600.weffs.application.internal.services.form.FrontendFormService;
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
  private FrontendFormService frontendFormService;

  @Autowired
  public FormController(FormCreationService formCreationService,
      FormDetailService formDetailService,
      FrontendFormService frontendFormService) {
    this.formCreationService = formCreationService;
    this.formDetailService = formDetailService;
    this.frontendFormService = frontendFormService;
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
  public FrontendForm getForm(@PathVariable("formDetailId") int formDetailId) {
    return frontendFormService.getFrontendFormFromFormDetailId(formDetailId);
  }

}



