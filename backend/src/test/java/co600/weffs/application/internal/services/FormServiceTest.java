package co600.weffs.application.internal.services;

import co600.weffs.application.MockitoTest;
import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.repository.FormRepository;
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

class FormServiceTest extends MockitoTest {

    @Mock
    private FormRepository formRepository;

    @InjectMocks
    private FormService formService;

    private Form form;

    @BeforeEach
    void setUp() {
        form = new Form();
    }

    @Test
    void getFormById() {
        when(formRepository.findById(322)).thenReturn(Optional.of(form));
        var serviceForm = formService.getFormById(322);
        assertThat(form).isEqualTo(serviceForm);
    }
    @Test
    void getFormById_Does_Not_Exist() {
        when(formRepository.findById(322)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->{
            formService.getFormById(322);
        });
    }

    @Test
    void save() {
        formService.save(form);
        var formCaptor = ArgumentCaptor.forClass(Form.class);
        verify(formRepository).save(formCaptor.capture());
        assertThat(form).isEqualTo(formCaptor.getValue());
    }
}