package co600.weffs.application.internal.repository.formResponse;

import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.model.formResponse.FormResponseDetail;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormResponseDetailRepository extends CrudRepository<FormResponseDetail, Integer> {
  Optional<FormResponseDetail> findByFormResponseAndStatusControlIsTrue(FormResponse formResponse);
}
