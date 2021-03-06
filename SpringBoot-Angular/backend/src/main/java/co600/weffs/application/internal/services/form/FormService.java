package co600.weffs.application.internal.services.form;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.repository.form.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "WeffsFormService")
public class FormService {
    private final FormRepository formRepository;

    @Autowired
    public FormService(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    public Form getFormById(Integer id){
        return formRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find form entity with id " + id));
    }

    public void save(Form form){
        formRepository.save(form);
    }
}
