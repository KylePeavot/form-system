package co600.weffs.application.internal.services;

import co600.weffs.application.MockitoTest;
import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.Question;
import co600.weffs.application.internal.repository.QuestionRepository;
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

class QuestionServiceTest extends MockitoTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    private Question question;

    @BeforeEach
    void setUp() {
        question = new Question();
    }

    @Test
    void getFormById() {
        when(questionRepository.findById(322)).thenReturn(Optional.of(question));
        var serviceQuestion = questionService.getFormById(322);
        assertThat(question).isEqualTo(serviceQuestion);
    }
    @Test
    void getFormById_DoesNotExist() {
        when(questionRepository.findById(322)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->{
            questionService.getFormById(322);
        });
    }

    @Test
    void save() {
        questionService.save(question);
        var questionCaptor = ArgumentCaptor.forClass(Question.class);
        verify(questionRepository).save(questionCaptor.capture());
        assertThat(question).isEqualTo(questionCaptor.getValue());
    }
}