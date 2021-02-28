package co600.weffs.application.internal.services;

import co600.weffs.application.MockitoTest;
import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.repository.FormDetailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
        var serviceFormDetail = formDetailService.getFormById(322);
        assertThat(formDetail).isEqualTo(serviceFormDetail);
    }
    @Test
    void getFormById_DoesNotExist() {
        when(formDetailRepository.findById(322)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->{
            formDetailService.getFormById(322);
        });
    }


    @Test
    void save() {
        formDetailService.save(formDetail);
        var formDetailCaptor = ArgumentCaptor.forClass(FormDetail.class);
        verify(formDetailRepository).save(formDetailCaptor.capture());
        assertThat(formDetail).isEqualTo(formDetailCaptor.getValue());
    }
}