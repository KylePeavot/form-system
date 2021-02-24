package co600.weffs.application.internal.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import co600.weffs.application.TestableController;
import co600.weffs.application.internal.model.form.FrontendForm;
import co600.weffs.application.internal.services.form.FormCreationService;
import co600.weffs.application.utils.UserTestUtils;
import co600.weffs.application.utils.ValueMapUtils;
import co600.weffs.application.utils.routes.Router;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
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

    @SneakyThrows
    @Test
    void testFormSaving() {
        var user = UserTestUtils.createDefaultUndergraduateAppUser();
        var frontendForm = new FrontendForm();
        var response = mockMvc.perform(
                post(Router.determineRoute(on(FormController.class).saveForm(null, null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonMapper.writeValueAsString(frontendForm))
                        .with(mockHttpServletRequest -> {
                            mockHttpServletRequest.setAttribute("User", user);
                            return mockHttpServletRequest;
                        }))
                .andReturn()
                .getResponse();
        var result = new ValueMapUtils<Map<String, ?>>().mapResponse(response);
        assertThat(result.get("success")).isEqualTo(true);
        verify(formCreationService).createForm(user, frontendForm);
    }

    @SneakyThrows
    @Test
    void testFormSaving_WithoutUser(){
        mockMvc.perform(
                post(Router.determineRoute(on(FormController.class).saveForm(null,null))))
                .andExpect(status().is(400));
    }
}