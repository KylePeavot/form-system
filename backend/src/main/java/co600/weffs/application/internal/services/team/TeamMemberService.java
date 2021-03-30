package co600.weffs.application.internal.services.team;

import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.error.InvalidSizeException;
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
  private final TeamDetailService teamDetailService;

  @Autowired
  public TeamMemberService(TeamMemberRepository teamMemberRepository,
      TeamDetailService teamDetailService) {
    this.teamMemberRepository = teamMemberRepository;
    this.teamDetailService = teamDetailService;
  }

  public List<TeamMember> getMembersInTeamDetail(TeamDetail teamDetail) {
    return teamMemberRepository.findAllByTeamDetailInAndStatusControlIsTrue(List.of(teamDetail));
  }

  public TeamView getTeamViewByIdForUser(Integer id) {
    var membersInTeam = teamMemberRepository.findAllByTeamDetail_Team_IdAndStatusControlIsTrue(id);
    var teamDetail = membersInTeam.stream()
        .map(TeamMember::getTeamDetail)
        .findFirst()
        .orElseThrow(() -> new InvalidSizeException(String.format("Expected members in team [%d] to be greater than 0.", id)));
    return new TeamView(teamDetail.getTeam().getId(), teamDetail.getName(), membersInTeam);
  }

  public Boolean canUserManageTeamView(AppUser appUser, TeamView teamView) {
    return teamView.getTeamMembers()
        .stream()
        .anyMatch(member -> member.getUsername().equals(appUser.getUsername()) && member
            .getCanManageTeam());
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
        .map(teamDetailListEntry -> {
          var teamDetail = teamDetailListEntry.getKey();
          return new TeamView(teamDetail.getTeam().getId(), teamDetail.getName(), teamDetailListEntry.getValue());
        })
        .collect(Collectors.toList());
  }

  private Map<TeamDetail, List<TeamMember>> getMembersInGroups(Collection<TeamDetail> details) {
    return teamMemberRepository.findAllByTeamDetailInAndStatusControlIsTrue(details)
        .stream()
        .collect(Collectors.groupingBy(TeamMember::getTeamDetail));
  }

  public TeamMember getTeamMemberFromUsernameAndTeamDetail(String username, TeamDetail teamDetail) {
    return teamMemberRepository.findByUsernameAndTeamDetail(username, teamDetail)
        .orElseThrow(() -> new EntityNotFoundException("No TeamMember found with username: " + username + " and teamDetail_Id: " + teamDetail.getId()));
  }

  public void saveAll(List<TeamMember> teamMembers) {
    teamMemberRepository.saveAll(teamMembers);
  }
}
