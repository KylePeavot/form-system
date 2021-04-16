package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.error.InsufficientParametersException;
import co600.weffs.application.internal.model.form.FormView;
import co600.weffs.application.internal.model.form.frontend.FrontendForm;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.services.form.FormCreationService;
import co600.weffs.application.internal.services.form.FormDetailService;
import co600.weffs.application.internal.services.form.FrontendFormService;
import co600.weffs.application.internal.services.team.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/form")
public class FormController {
  private final FormCreationService formCreationService;
  private final FormDetailService formDetailService;
  private final FrontendFormService frontendFormService;
  private final TeamMemberService teamMemberService;

  @Autowired
  public FormController(FormCreationService formCreationService,
                        FormDetailService formDetailService,
                        FrontendFormService frontendFormService, TeamMemberService teamMemberService) {
    this.formCreationService = formCreationService;
    this.formDetailService = formDetailService;
    this.frontendFormService = frontendFormService;
    this.teamMemberService = teamMemberService;
  }

  @MustBeAuthorized
  @PostMapping("/save")
  public Map<String, ?> saveForm(@RequestAttribute("User") AppUser appUser, @RequestBody FrontendForm frontendForm) throws InsufficientParametersException {
    if (frontendForm == null || frontendForm.get_team() == null) {
      throw new InsufficientParametersException("FrontendForm is not valid.");
    }
    formCreationService.createForm(appUser, frontendForm);
    return Map.of("success", true);
  }

  @MustBeAuthorized
  @GetMapping("/browse")
  public List<FormView> getForm(@RequestAttribute("User") AppUser appUser) {
    return teamMemberService.getActiveViewableForms(appUser);
  }

  @MustBeAuthorized
  @GetMapping("/get/{formDetailId}")
  public FrontendForm getForm(@PathVariable("formDetailId") int formDetailId) {
    // Ensure FormDetail exists
    formDetailService.getFormDetailById(formDetailId);
    return frontendFormService.getFrontendFormFromFormDetailId(formDetailId);
  }

}



