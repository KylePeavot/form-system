package co600.weffs.application.internal.repository.form;

import co600.weffs.application.internal.model.form.QuestionDetail;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDetailRepository extends CrudRepository<QuestionDetail, Integer> {

  Optional<QuestionDetail> findByQuestion_IdAndStatusControlIsTrue(int id);

}
