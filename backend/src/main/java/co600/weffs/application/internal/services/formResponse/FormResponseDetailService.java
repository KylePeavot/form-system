package co600.weffs.application.internal.services.formResponse;

import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.model.formResponse.FormResponseDetail;
import co600.weffs.application.internal.model.team.TeamDetail;
import co600.weffs.application.internal.model.team.TeamMember;
import co600.weffs.application.internal.repository.formResponse.FormResponseDetailRepository;
import co600.weffs.application.internal.services.team.TeamMemberService;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormResponseDetailService {

  TeamMemberService teamMemberService;

  FormResponseDetailRepository formResponseDetailRepository;

  @Autowired
  public FormResponseDetailService(TeamMemberService teamMemberService, FormResponseDetailRepository formResponseDetailRepository) {
    this.teamMemberService = teamMemberService;
    this.formResponseDetailRepository = formResponseDetailRepository;
  }

  public FormResponseDetail create(FormResponse formResponse) {
    FormResponseDetail newFormResponseDetail = new FormResponseDetail();

    newFormResponseDetail.setFormResponse(formResponse);
    newFormResponseDetail.setStatusControl(true);
    newFormResponseDetail.setLastUpdatedTimestamp(Instant.now());

    return formResponseDetailRepository.save(newFormResponseDetail);
  }

}
