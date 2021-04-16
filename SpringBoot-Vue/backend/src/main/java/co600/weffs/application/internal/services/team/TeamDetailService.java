package co600.weffs.application.internal.services.team;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.team.TeamDetail;
import co600.weffs.application.internal.repository.team.TeamDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamDetailService {

  private final TeamDetailRepository teamDetailRepository;

  @Autowired
  public TeamDetailService(
      TeamDetailRepository teamDetailRepository) {
    this.teamDetailRepository = teamDetailRepository;
  }

  public TeamDetail getTeamDetailById(int id) {
    return teamDetailRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No TeamDetail found with id: " + id));
  }

  public void save(TeamDetail teamDetail) {
    teamDetailRepository.save(teamDetail);
  }

  public void saveAll(Iterable<TeamDetail> teamDetails) {
    teamDetailRepository.saveAll(teamDetails);
  }

  public TeamDetail getActiveTeamDetailByTeamId(Integer teamId) {
    return teamDetailRepository.findByTeam_IdAndStatusControlIsTrue(teamId)
        .orElseThrow(() -> new EntityNotFoundException(String.format("No active TeamDetail for team [%d]", teamId)));
  }
}
