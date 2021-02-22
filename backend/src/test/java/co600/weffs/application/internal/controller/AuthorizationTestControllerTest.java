package co600.weffs.application.internal.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import co600.weffs.application.TestableController;
import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.utils.UserTestUtils;
import co600.weffs.application.utils.ValueMapUtils;
import co600.weffs.application.utils.routes.Router;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 * A dummy implementation of a test showing authentication.
 */

class AuthorizationTestControllerTest extends TestableController {

  @SneakyThrows
  @Test
  void testAuthorizationAccess_WithUser() {
    var user = UserTestUtils.createDefaultUndergraduateAppUser();
    var response = mockMvc.perform(
        get(Router.determineRoute(on(AuthorizationTestController.class).testAuthorization(null)))
            .with(mockHttpServletRequest -> {
              mockHttpServletRequest.setAttribute("User", user);
              return mockHttpServletRequest;
            }))
        .andReturn()
        .getResponse();
    var result = new ValueMapUtils<Map<String, ?>>().mapResponse(response);
    assertThat(result.get("authorized")).isEqualTo(true);
    assertThat(result.get("user")).isNotNull();
    var userData = new ObjectMapper().convertValue(result.get("user"), AppUser.class);
    assertThat(userData).isEqualToComparingFieldByField(user);
  }

  @SneakyThrows
  @Test
  void testAuthorizationAccess_WithoutUser() {
    mockMvc.perform(
        get(Router.determineRoute(on(AuthorizationTestController.class).testAuthorization(null))))
        .andExpect(status().is(400));
  }

}