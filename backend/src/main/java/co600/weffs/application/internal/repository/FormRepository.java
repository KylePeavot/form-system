package co600.weffs.application.internal.repository;

import co600.weffs.application.internal.model.form.Form;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends CrudRepository<Form, Integer> {

}
