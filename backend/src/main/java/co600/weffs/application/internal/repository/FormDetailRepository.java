package co600.weffs.application.internal.repository;

import co600.weffs.application.internal.model.form.FormDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormDetailRepository extends CrudRepository<FormDetail, Integer> {
    List<FormDetail> findAllByIdAndStatusControlIsTrue(Integer id);
}

