package co600.weffs.application.internal.services;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.model.form.FormView;
import co600.weffs.application.internal.repository.FormDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public  class FormDetailService {
    private final FormDetailRepository formDetailRepository;

    @Autowired
    public FormDetailService(FormDetailRepository formDetailRepository){
        this.formDetailRepository = formDetailRepository;
    }

    public FormDetail getFormById(Integer id){
        return formDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find form entity with id " + id));
    }

    public List<FormView> getActiveFormViewsById(Integer id) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        var formDetailList = formDetailRepository.findAllByIdAndStatusControlIsTrue(id);
        return formDetailList.stream()
                .map(formDetail -> new FormView(formDetail.getForm().getId(), "name",
                        formDetail.getForm().getCreatedBy(), formatter.format(Date.from(formDetail.getForm().getCreatedTimestamp())),
                        formDetail.getLastUpdatedBy(), formatter.format(Date.from(formDetail.getLastUpdatedTimestamp()))))
                .collect(Collectors.toList());
    }

    public void save(FormDetail formDetail){
        formDetailRepository.save(formDetail);
    }
}
