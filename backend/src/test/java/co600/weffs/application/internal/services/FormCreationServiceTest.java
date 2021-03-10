package co600.weffs.application.internal.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import co600.weffs.application.MockitoTest;
import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.model.form.frontend.FrontendComponent;
import co600.weffs.application.internal.model.form.frontend.FrontendForm;
import co600.weffs.application.internal.model.form.Question;
import co600.weffs.application.internal.model.form.QuestionDetail;
import co600.weffs.application.internal.services.form.FormCreationService;
import co600.weffs.application.internal.services.form.FormDetailService;
import co600.weffs.application.internal.services.form.FormService;
import co600.weffs.application.internal.services.form.QuestionDetailService;
import co600.weffs.application.internal.services.form.QuestionService;
import co600.weffs.application.utils.UserTestUtils;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class FormCreationServiceTest extends MockitoTest {

    @Mock
    private FormService formService;

    @Mock
    private FormDetailService formDetailService;

    @Mock
    private QuestionService questionService;

    @Mock
    private QuestionDetailService questionDetailService;

    @InjectMocks
    private FormCreationService formCreationService;

    private FrontendForm frontendForm;

    private FrontendComponent frontendComponent;

    private FormDetail formDetail;

    @BeforeEach
    void setUp() {
        formDetail = new FormDetail();
        frontendForm = new FrontendForm();
        frontendForm.set_name("ij57");
        frontendComponent = new FrontendComponent();
        frontendComponent.set_componentType("TextField");
        frontendComponent.set_componentProps(Map.of(
                "guidance","Test Guidance",
                "title","Test Title"
        ));
        frontendComponent.set_order(100);
        frontendForm.set_componentList(List.of(frontendComponent));
    }

    @Test
    void createForm() {
        var user = UserTestUtils.createDefaultUndergraduateAppUser();
        formCreationService.createForm(user,frontendForm);
        var formCaptor = ArgumentCaptor.forClass(Form.class);
        verify(formService).save(formCaptor.capture());
        var formDetailCaptor = ArgumentCaptor.forClass(FormDetail.class);
        verify(formDetailService).save(formDetailCaptor.capture());

        var formDetail = formDetailCaptor.getValue();
        assertThat(formDetail.getLastUpdatedBy()).isEqualTo(user.getUsername());

        var form = formCaptor.getValue();
        assertThat(formDetail.getForm()).isEqualTo(form);
        assertThat(form.getCreatedBy()).isEqualTo(user.getUsername());
        // TODO: FS-65 assert name field
    }

    @Test
    void createQuestion() {
        var user = UserTestUtils.createDefaultUndergraduateAppUser();
        formCreationService.createQuestion(user,frontendComponent,formDetail);
        var questionCaptor = ArgumentCaptor.forClass(Question.class);
        verify(questionService).save(questionCaptor.capture());
        var questionDetailCaptor = ArgumentCaptor.forClass(QuestionDetail.class);
        verify(questionDetailService).save(questionDetailCaptor.capture());

        var questionDetail = questionDetailCaptor.getValue();
        assertThat(questionDetail.getGuidance()).isEqualTo(frontendComponent.get_componentProps().get("guidance"));
        assertThat(questionDetail.getTitle()).isEqualTo(frontendComponent.get_componentProps().get("title"));

        var question = questionCaptor.getValue();
        assertThat(question.getCreatedBy()).isEqualTo(user.getUsername());

    }
}