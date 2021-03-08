package co600.weffs.application.internal.services.formResponse;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.repository.formResponse.FormResponseRepository;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormResponseService {


  @Autowired
  private FormResponseRepository formResponseRepository;

  public FormResponseService(FormResponseRepository formResponseRepository) {
    this.formResponseRepository = formResponseRepository;
  }

  public FormResponse create(String filler, FormDetail formDetail) {
    FormResponse newFormResponse = new FormResponse();

    newFormResponse.setFormDetail(formDetail);
    newFormResponse.setAssignedTo(filler);
    newFormResponse.setAssignedTimestamp(Instant.now());

    return formResponseRepository.save(newFormResponse);
  }

  public FormResponse getFormResponseById(int id) {
    return formResponseRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No FormResponse found with id: " + id));
  }
}
