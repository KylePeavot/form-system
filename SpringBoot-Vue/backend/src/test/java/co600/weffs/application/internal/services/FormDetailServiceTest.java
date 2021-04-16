package co600.weffs.application.internal.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co600.weffs.application.MockitoTest;
import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.repository.form.FormDetailRepository;
import co600.weffs.application.internal.services.form.FormDetailService;
import co600.weffs.application.utils.forms.FormTestUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;


class FormDetailServiceTest extends MockitoTest {

    @Mock
    private FormDetailRepository formDetailRepository;

    @InjectMocks
    private FormDetailService formDetailService;

    private FormDetail formDetail;


    @BeforeEach
    void setUp() {
        formDetail = new FormDetail();
    }

    @Test
    void getFormById() {
        when(formDetailRepository.findById(322)).thenReturn(Optional.of(formDetail));
        var serviceFormDetail = formDetailService.getFormDetailById(322);
        assertThat(formDetail).isEqualTo(serviceFormDetail);
    }
    @Test
    void getFormById_DoesNotExist() {
        when(formDetailRepository.findById(322)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->{
            formDetailService.getFormDetailById(322);
        });
    }

    @Test
    void save() {
        formDetailService.save(formDetail);
        var formDetailCaptor = ArgumentCaptor.forClass(FormDetail.class);
        verify(formDetailRepository).save(formDetailCaptor.capture());
        assertThat(formDetail).isEqualTo(formDetailCaptor.getValue());
    }

    // TODO FS-90 - Reimplement @Test annotation
    void getActiveFormViews(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Form form = FormTestUtils.createBasicForm();
        formDetail = FormTestUtils.createBasicFormDetail(form);
        when(formDetailRepository.findAllByStatusControlIsTrue()).thenReturn(List.of(formDetail));
        var formViews = formDetailService.getActiveFormViews();
        var formView = formViews.get(0);
        var formCreatedTimestamp = formatter.format(Date.from(form.getCreatedTimestamp()));
        var fromUpdatedTimestamp = formatter.format(Date.from(formDetail.getLastUpdatedTimestamp()));
        var formViewCreatedTimestamp = formView.getCreatedWhen();
        var formViewLastUpdatedTimestamp = formView.getLastUpdatedWhen();
        assertThat(formView.getName()).isEqualTo(formDetail.getName());
        assertThat(formViewCreatedTimestamp).isEqualTo(formCreatedTimestamp);
        assertThat(formView.getCreatedBy()).isEqualTo(form.getCreatedBy());
        assertThat(formViewLastUpdatedTimestamp).isEqualTo(fromUpdatedTimestamp);
        assertThat(formView.getLastUpdatedBy()).isEqualTo(formDetail.getLastUpdatedBy());
    }}