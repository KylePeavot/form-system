package co600.weffs.application.internal.services.team;

import co600.weffs.application.internal.model.team.TeamDetail;
import co600.weffs.application.internal.model.team.TeamMember;
import co600.weffs.application.internal.repository.team.TeamMemberRepository;
import co600.weffs.application.internal.view.team.TeamView;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

  public List<TeamView> getTeamViewForUsername(String username) {
    var teamDetails = teamMemberRepository.findAllByUsernameAndStatusControlIsTrue(username)
        .stream()
        .collect(Collectors.groupingBy(TeamMember::getTeamDetail))
        .keySet();
    return getMembersInGroups(teamDetails)
        .entrySet()
        .stream()
        .map(teamDetailListEntry ->
          new TeamView(teamDetailListEntry.getKey(), teamDetailListEntry.getValue())
        )
        .collect(Collectors.toList());
  }

  private Map<TeamDetail, List<TeamMember>> getMembersInGroups(Collection<TeamDetail> details) {
    return teamMemberRepository.findAllByTeamDetailInAndStatusControlIsTrue(details)
        .stream()
        .collect(Collectors.groupingBy(TeamMember::getTeamDetail));
  }
}
