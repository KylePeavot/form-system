package co600.weffs.application.internal.services.team;

import co600.weffs.application.internal.repository.team.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamMemberService {

  private final TeamMemberRepository teamMemberRepository;

  @Autowired
  public TeamMemberService(
      TeamMemberRepository teamMemberRepository) {
    this.teamMemberRepository = teamMemberRepository;
  }
}
