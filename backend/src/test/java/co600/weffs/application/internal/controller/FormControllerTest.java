package co600.weffs.application.internal.controller;

import co600.weffs.application.TestableController;
import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.model.form.FormView;
import co600.weffs.application.internal.model.form.frontend.FrontendForm;
import co600.weffs.application.internal.services.form.FormCreationService;
import co600.weffs.application.internal.services.form.FormDetailService;
import co600.weffs.application.internal.services.form.FrontendFormService;
import co600.weffs.application.utils.UserTestUtils;
import co600.weffs.application.utils.ValueMapUtils;
import co600.weffs.application.utils.routes.Router;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@WebMvcTest(
        controllers = FormController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
class FormControllerTest extends TestableController {

    @MockBean
    private FormCreationService formCreationService;
    @MockBean
    private FormDetailService formDetailService;
    @MockBean
    private FrontendFormService frontendFormService;

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
    void testFormSaving_WithoutUser() {
        mockMvc.perform(
                post(Router.determineRoute(on(FormController.class).saveForm(null, null))))
                .andExpect(status().is(400));
    }

    @SneakyThrows
    @Test
    void testGetForm() {
        var formDetail = new FormDetail();
        var form = new Form();
        formDetail.setLastUpdatedTimestamp(Instant.now());
        form.setCreatedTimestamp(Instant.now());
        form.setId(1);
        formDetail.setForm(form);
        var formView = new FormView(formDetail);
        when(formDetailService.getActiveFormViews()).thenReturn(List.of(formView));
        var response = mockMvc.perform(
                get(Router.determineRoute(on(FormController.class).getForm()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonMapper.writeValueAsString(formDetail))
                )
                .andReturn()
                .getResponse();
        var result = new ValueMapUtils<List<FormView>>().mapResponse(response);
        var responseFormView = jacksonMapper.convertValue(result.get(0),FormView.class);
        assertThat(responseFormView).isEqualToComparingFieldByField(formView);
    }
}