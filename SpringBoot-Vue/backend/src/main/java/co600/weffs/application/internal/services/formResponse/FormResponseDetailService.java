package co600.weffs.application.internal.services.formResponse;

import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.model.formResponse.FormResponseDetail;
import co600.weffs.application.internal.repository.formResponse.FormResponseDetailRepository;
import co600.weffs.application.internal.services.team.TeamMemberService;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormResponseDetailService {

  TeamMemberService teamMemberService;

  FormResponseDetailRepository formResponseDetailRepository;

  @Autowired
  public FormResponseDetailService(TeamMemberService teamMemberService, FormResponseDetailRepository formResponseDetailRepository) {
    this.teamMemberService = teamMemberService;
    this.formResponseDetailRepository = formResponseDetailRepository;
  }

  @Transactional
  public FormResponseDetail create(FormResponse formResponse) {
    return create(List.of(formResponse)).get(0);
  }

  @Transactional
  public List<FormResponseDetail> create(Collection<FormResponse> formResponses) {

    var details = formResponses.stream()
        .map(formResponse -> {
          FormResponseDetail newFormResponseDetail = new FormResponseDetail();

          newFormResponseDetail.setFormResponse(formResponse);
          newFormResponseDetail.setStatusControl(true);
          newFormResponseDetail.setLastUpdatedTimestamp(Instant.now());

          return newFormResponseDetail;
        })
        .collect(Collectors.toList());

    return (List<FormResponseDetail>) formResponseDetailRepository.saveAll(details);
  }

  public Optional<FormResponseDetail> findCurrentDetailByFormResponse(FormResponse formResponse) {
    return formResponseDetailRepository.findByFormResponseAndStatusControlIsTrue(formResponse);
  }

  public FormResponseDetail save(FormResponseDetail formResponseDetail) {
    return formResponseDetailRepository.save(formResponseDetail);
  }

}
