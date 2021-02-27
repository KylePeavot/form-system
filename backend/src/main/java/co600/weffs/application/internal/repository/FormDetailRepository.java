package co600.weffs.application.internal.repository;

import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.model.form.FormDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormDetailRepository extends CrudRepository<FormDetail, Integer> {

  FormDetail findByFormAndStatusControlIsTrue(Form form);

}