package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.form.FrontendForm;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.services.FormCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        formCreationService.createForm(appUser,frontendForm);
        return Map.of();
    }


}

