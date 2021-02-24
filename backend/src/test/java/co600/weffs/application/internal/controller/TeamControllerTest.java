package co600.weffs.application.internal.controller;

import co600.weffs.application.TestableController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


@WebMvcTest(
    controllers = FormController.class,
    excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
class TeamControllerTest extends TestableController {

  @Test
  void getAvailableTeams() {
  }

  @Test
  void createTeam() {
  }
}