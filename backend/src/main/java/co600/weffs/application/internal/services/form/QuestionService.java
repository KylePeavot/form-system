package co600.weffs.application.internal.services.form;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.Question;
import co600.weffs.application.internal.repository.form.QuestionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question getQuestionById(Integer id){
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find form entity with id " + id));
    }

    public List<Question> getQuestionsForFormDetailId(int formDetailId) {
        return questionRepository.findAllByFormDetail_Id(formDetailId);
    }

    public void save(Question question){
        questionRepository.save(question);
    }
}
