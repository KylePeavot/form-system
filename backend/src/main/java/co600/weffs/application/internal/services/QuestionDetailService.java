package co600.weffs.application.internal.services;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.QuestionDetail;
import co600.weffs.application.internal.repository.QuestionDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionDetailService {
    private QuestionDetailRepository questionDetailRepository;

    @Autowired
    public QuestionDetailService(QuestionDetailRepository questionDetailRepository) {
        this.questionDetailRepository = questionDetailRepository;
    }

    public QuestionDetail getFormById(Integer id){
        return questionDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find form entity with id " + id));
    }

    public void save(QuestionDetail questionDetail){
        questionDetailRepository.save(questionDetail);
    }
}
