package co600.weffs.application.internal.controller.users;

import co600.weffs.application.internal.services.KentUserRestService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kent/users")
public class UserController {

  private final KentUserRestService kentUserRestService;

  @Autowired
  public UserController(KentUserRestService kentUserRestService) {
    this.kentUserRestService = kentUserRestService;
  }

  @GetMapping
  public List<?> getKentUsers() {
    return kentUserRestService.getKentUsersAsStringList();
  }

}
