package co600.weffs.application.internal.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co600.weffs.application.MockitoTest;
import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.QuestionDetail;
import co600.weffs.application.internal.repository.form.QuestionDetailRepository;
import co600.weffs.application.internal.services.form.QuestionDetailService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class QuestionDetailServiceTest extends MockitoTest {

    @Mock
    private QuestionDetailRepository questionDetailRepository;

    @InjectMocks
    private QuestionDetailService questionDetailService;

    private QuestionDetail questionDetail;

    @BeforeEach
    void setUp() {
        questionDetail = new QuestionDetail();
    }

    @Test
    void getFormById() {
        when(questionDetailRepository.findById(322)).thenReturn(Optional.of(questionDetail));
        var serviceQuestionDetail = questionDetailService.getQuestionDetailById(322);
        assertThat(questionDetail).isEqualTo(serviceQuestionDetail);
    }

    @Test
    void getFormById_DoesNotExist() {
        when(questionDetailRepository.findById(322)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->{
            questionDetailService.getQuestionDetailById(322);
        });
    }

    @Test
    void save() {
        questionDetailService.save(questionDetail);
        var questionDetailCaptor = ArgumentCaptor.forClass(QuestionDetail.class);
        verify(questionDetailRepository).save(questionDetailCaptor.capture());
        assertThat(questionDetail).isEqualTo(questionDetailCaptor.getValue());
    }
}