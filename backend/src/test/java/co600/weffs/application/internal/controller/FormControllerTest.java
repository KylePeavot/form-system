package co600.weffs.application.internal.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import co600.weffs.application.TestableController;
import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.form.FrontendForm;
import co600.weffs.application.internal.services.FormCreationService;
import co600.weffs.application.utils.UserTestUtils;
import co600.weffs.application.utils.ValueMapUtils;
import co600.weffs.application.utils.routes.Router;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(
    controllers = FormController.class,
    excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
class FormControllerTest extends TestableController {

  @MockBean
  private FormCreationService formCreationService;

  @InjectMocks
  private FormController formController;

  private FrontendForm frontendForm;

  @BeforeEach
  void setUp() {
  }

  @SneakyThrows
  @Test
  void testFormSaving() {
    var user = UserTestUtils.createDefaultUndergraduateAppUser();
    var frontendForm = new FrontendForm();
    var response = mockMvc.perform(
        // Send a POST request to the URL mapping on Controller::Method
        post(Router.determineRoute(on(FormController.class).saveForm(null, null)))
            // Data is sent as JSON
            .contentType(MediaType.APPLICATION_JSON)
            // Convert object to JSON and set as request body.
            .content(jacksonMapper.writeValueAsString(frontendForm))
            // Stub fake user for authentication
            .with(mockHttpServletRequest -> {
              mockHttpServletRequest.setAttribute("User", user);
              return mockHttpServletRequest;
            }))
        .andReturn()
        .getResponse();
    // Convert the result to type { Map<String, ?> }
    var result = new ValueMapUtils<Map<String, ?>>().mapResponse(response);
    // Convert the "user" key of the response Map<String, ?> to an AppUser object
    var userData = new ObjectMapper().convertValue(result.get("user"), AppUser.class);
    assertThat(userData).isEqualToComparingFieldByField(user);
    // Convert the "frontendForm" key of the response to a FrontendForm object
    var frontendFormData = new ObjectMapper()
        .convertValue(result.get("frontendForm"), FrontendForm.class);
    assertThat(frontendFormData).isEqualToComparingFieldByField(frontendForm);
  }

  @Test
  void saveForm() {
  }
}