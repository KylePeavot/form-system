package co600.weffs.application.internal.services.team;

import co600.weffs.application.internal.model.team.TeamDetail;
import co600.weffs.application.internal.model.team.TeamMember;
import co600.weffs.application.internal.repository.team.TeamMemberRepository;
import co600.weffs.application.internal.view.team.TeamView;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    Set<TeamDetail> teamDetails = teamMemberRepository.findAllByUsernameAndStatusControlIsTrue(username)
        .stream()
        .collect(Collectors.groupingBy(TeamMember::getTeamDetail))
        .keySet();

    boolean isAdmin = teamDetails.stream()
        .anyMatch(teamDetail -> teamDetail.getName().equalsIgnoreCase("Admin"));

    // If user is admin then all teams should be visible.
    if (isAdmin) {
      teamDetails = ((List<TeamMember>) teamMemberRepository.findAll()).stream()
          .collect(Collectors.groupingBy(TeamMember::getTeamDetail))
          .keySet();
    }

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

  public void saveAll(List<TeamMember> teamMembers) {
    teamMemberRepository.saveAll(teamMembers);
  }
}
