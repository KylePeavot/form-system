package co600.weffs.application.internal.services.form;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.QuestionDetail;
import co600.weffs.application.internal.repository.form.QuestionDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionDetailService {
    private final QuestionDetailRepository questionDetailRepository;

    @Autowired
    public QuestionDetailService(QuestionDetailRepository questionDetailRepository) {
        this.questionDetailRepository = questionDetailRepository;
    }

    public QuestionDetail getQuestionDetailById(Integer id){
        return questionDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find form entity with id " + id));
    }

    public QuestionDetail getCurrentQuestionDetailByQuestionId(int questionId) {
        return questionDetailRepository.findByQuestion_IdAndStatusControlIsTrue(questionId)
            .orElseThrow(() -> new EntityNotFoundException("No QuestionDetail found with question ID: " + questionId));
    }

    public void save(QuestionDetail questionDetail){
        questionDetailRepository.save(questionDetail);
    }
}
