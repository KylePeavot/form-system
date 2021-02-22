package co600.weffs.application.internal.services;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.repository.FormDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormDetailService {
    private final FormDetailRepository formDetailRepository;

    @Autowired
    public FormDetailService(FormDetailRepository formDetailRepository){
        this.formDetailRepository = formDetailRepository;
    }

    public FormDetail getFormById(Integer id){
        return formDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find form entity with id " + id));
    }

    public void save(FormDetail formDetail){
        formDetailRepository.save(formDetail);
    }
}
