package co600.weffs.application.internal.services.form;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.model.form.FormView;
import co600.weffs.application.internal.repository.form.FormDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormDetailService {
    private final FormDetailRepository formDetailRepository;

    @Autowired
    public FormDetailService(FormDetailRepository formDetailRepository){
        this.formDetailRepository = formDetailRepository;
    }

    public FormDetail getFormDetailById(Integer id){
        return formDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find form entity with id " + id));
    }

    public List<FormView> getActiveFormViews() {
        var formDetailList = formDetailRepository.findAllByStatusControlIsTrue();
        return formDetailList.stream()
                .map(FormView::new)
                .collect(Collectors.toList());
    }

    public FormDetail getFormDetailByForm(Form form) {
        return formDetailRepository.findByFormAndStatusControlIsTrue(form);
    }

    public void save(FormDetail formDetail){
        formDetailRepository.save(formDetail);
    }
}
