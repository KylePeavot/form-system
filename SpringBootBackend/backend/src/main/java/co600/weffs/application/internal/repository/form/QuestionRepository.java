package co600.weffs.application.internal.repository.form;

import co600.weffs.application.internal.model.form.Question;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {

  List<Question> findAllByFormDetail_Id(int id);

}
