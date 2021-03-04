package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.form.FrontendForm;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.services.form.FormCreationService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/form")
public class FormController {

  private final FormCreationService formCreationService;

  @Autowired
  public FormController(FormCreationService formCreationService) {
    this.formCreationService = formCreationService;
  }

  @MustBeAuthorized
  @PostMapping("/save")
  public Map<String, ?> saveForm(@RequestAttribute("User") AppUser appUser, @RequestBody FrontendForm frontendForm) {
    formCreationService.createForm(appUser, frontendForm);
    return Map.of("success", true);
  }

  @MustBeAuthorized
  @GetMapping("/get/{formId}")
  public Object getForm(@PathVariable("formId") int formId) {
    //TODO add DB object for FormResponse
    //Should hold id, FormId, then figure out some way to store all the responses to the form
    //Once sorted, for a FormResponseId, get the formId

    //For a form id,
    // get the current form detail id,
    // from that get all questions associated,
    // then get all current question details for that question
    return null;
  }

}

