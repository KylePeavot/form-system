package co600.weffs.application.internal.repository.formResponse;

import co600.weffs.application.internal.model.formResponse.QuestionResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionResponseRepository extends CrudRepository<QuestionResponse, Integer> {

}
