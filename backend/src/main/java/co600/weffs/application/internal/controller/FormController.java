package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.form.FormView;
import co600.weffs.application.internal.model.form.FrontendForm;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.services.FormCreationService;
import co600.weffs.application.internal.services.FormDetailService;
import org.springframework.beans.factory.annotation.Autowired;
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
}



