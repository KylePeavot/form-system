package co600.weffs.application.internal.repository.form;

import co600.weffs.application.internal.model.form.QuestionDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDetailRepository extends CrudRepository<QuestionDetail, Integer> {
}
