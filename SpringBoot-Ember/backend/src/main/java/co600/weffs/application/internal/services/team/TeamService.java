package co600.weffs.application.internal.services.team;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.team.Team;
import co600.weffs.application.internal.repository.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

  private final TeamRepository teamRepository;

  @Autowired
  public TeamService(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  public void save(Team team) {
    teamRepository.save(team);
  }

  public Team getById(Integer id) {
    return teamRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Unable to find a team entity with ID [%d]", id)));
  }

}
