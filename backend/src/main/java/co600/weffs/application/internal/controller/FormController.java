package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.model.form.FormView;
import co600.weffs.application.internal.model.form.FrontendForm;
import co600.weffs.application.internal.repository.FormDetailRepository;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.services.FormCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/form")
public class FormController {

  private final FormCreationService formCreationService;
  private final FormDetailRepository formDetailRepository;

  @Autowired
  public FormController(FormCreationService formCreationService, FormDetailRepository formDetailRepository) {
    this.formCreationService = formCreationService;
    this.formDetailRepository = formDetailRepository;
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
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    return ((List<FormDetail>) formDetailRepository.findAll()).stream()
            .map(formDetail -> new FormView(formDetail.getForm().getId(), "name",
                    formDetail.getForm().getCreatedBy(), formatter.format(Date.from(formDetail.getForm().getCreatedTimestamp())),
                    formDetail.getLastUpdatedBy(), formatter.format(Date.from(formDetail.getLastUpdatedTimestamp()))))
            .collect(Collectors.toList());
  }
}



