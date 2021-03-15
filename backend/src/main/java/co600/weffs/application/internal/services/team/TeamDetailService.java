package co600.weffs.application.internal.services.team;

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

  public void save(TeamDetail teamDetail) {
    teamDetailRepository.save(teamDetail);
  }
}
