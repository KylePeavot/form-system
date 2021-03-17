package co600.weffs.application.internal.repository.formResponse;

import co600.weffs.application.internal.model.formResponse.FormResponseDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormResponseDetailRepository extends CrudRepository<FormResponseDetail, Integer> {

}
