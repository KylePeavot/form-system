package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test-auth")
public class AuthorizationTestController {

  @MustBeAuthorized
  @GetMapping
  public Map<String, ?> testAuthorization(@RequestAttribute("User") AppUser appUser) {
    return Map.of(
        "authorized", true,
        "user", appUser
    );
  }

  @GetMapping("/s")
  public String shouldBeFine() {
    return "it's ok";
  }

}
