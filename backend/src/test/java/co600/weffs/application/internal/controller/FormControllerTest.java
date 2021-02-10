package co600.weffs.application.internal.controller;

import co600.weffs.application.TestableController;
import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.form.FrontendForm;
import co600.weffs.application.internal.services.FormCreationService;
import co600.weffs.application.utils.UserTestUtils;
import co600.weffs.application.utils.ValueMapUtils;
import co600.weffs.application.utils.routes.Router;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

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
    void testFormSaving(){
    var user = UserTestUtils.createDefaultUndergraduateAppUser();
    var frontendForm = new FrontendForm();
    var response = mockMvc.perform(
        get(Router.determineRoute(on(FormController.class).saveForm(null,frontendForm)))
            .with(mockHttpServletRequest -> {
                mockHttpServletRequest.setAttribute("user", user);
                return mockHttpServletRequest;
            }))
            .andReturn()
            .getResponse();
    var result = new ValueMapUtils<Map<String,?>>().mapResponse(response);
    var userData = new ObjectMapper().convertValue(result.get("user"), AppUser.class);
    assertThat(userData).isEqualToComparingFieldByField(user);
    var frontendFormData = new ObjectMapper().convertValue(result.get("frontendForm"), FrontendForm.class);
    assertThat(frontendFormData).isEqualToComparingFieldByField(frontendForm);
    }

    @Test
    void saveForm() {
    }
}