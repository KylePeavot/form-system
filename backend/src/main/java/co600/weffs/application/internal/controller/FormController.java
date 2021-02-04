package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.form.FrontendForm;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/form")
public class FormController {


    @MustBeAuthorized
    @PostMapping("/save")
    public Map<String,?> saveForm(@RequestAttribute("User") AppUser appUser, @RequestBody FrontendForm frontendForm) {
        return Map.of(
                "authorized", true,
                "user", appUser
        );
    }



}

