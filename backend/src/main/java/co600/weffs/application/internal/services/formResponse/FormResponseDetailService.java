package co600.weffs.application.internal.services.formResponse;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.model.formResponse.FormResponseDetail;
import co600.weffs.application.internal.model.team.TeamDetail;
import co600.weffs.application.internal.model.team.TeamMember;
import co600.weffs.application.internal.repository.formResponse.FormResponseDetailRepository;
import co600.weffs.application.internal.services.team.TeamMemberService;
import java.time.Instant;
import java.util.Optional;
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

  public FormResponseDetail create(FormResponse formResponse, String assigner, TeamDetail assignerTeamDetail) {
    FormResponseDetail newFormResponseDetail = new FormResponseDetail();

    newFormResponseDetail.setAssigner(teamMemberService.getTeamMemberFromUsernameAndTeamDetail(assigner, assignerTeamDetail));
    newFormResponseDetail.setAssignerTeamDetail(assignerTeamDetail);
    newFormResponseDetail.setFormResponse(formResponse);
    newFormResponseDetail.setStatusControl(true);
    newFormResponseDetail.setLastUpdatedTimestamp(Instant.now());

    return formResponseDetailRepository.save(newFormResponseDetail);
  }

  public Optional<FormResponseDetail> findCurrentDetailByFormResponse(FormResponse formResponse) {
    return formResponseDetailRepository.findByFormResponseAndStatusControlIsTrue(formResponse);
  }

  public FormResponseDetail save(FormResponseDetail formResponseDetail) {
    return formResponseDetailRepository.save(formResponseDetail);
  }

}
