package co600.weffs.application.internal.services.team;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.team.Team;
import co600.weffs.application.internal.model.team.TeamDetail;
import co600.weffs.application.internal.model.team.TeamMember;
import co600.weffs.application.internal.model.team.frontend.FrontendTeamCreation;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamCreationService {

  private final TeamService teamService;
  private final TeamDetailService teamDetailService;
  private final TeamMemberService teamMemberService;

  @Autowired
  public TeamCreationService(TeamService teamService,
      TeamDetailService teamDetailService,
      TeamMemberService teamMemberService) {
    this.teamService = teamService;
    this.teamDetailService = teamDetailService;
    this.teamMemberService = teamMemberService;
  }

  @Transactional
  public void createTeam(AppUser appUser, FrontendTeamCreation teamCreation) {
    var team = new Team();
    team.setCreatedBy(appUser.getUsername());
    team.setCreatedTimestamp(Instant.now());
    teamService.save(team);

    var teamDetail = new TeamDetail();
    teamDetail.setName(teamCreation.getName());
    teamDetail.setCreatedBy(appUser.getUsername());
    teamDetail.setCreatedTimestamp(Instant.now());
    teamDetail.setStatusControl(true);
    teamDetail.setTeam(team);
    teamDetailService.save(teamDetail);

    List<TeamMember> members = Arrays.stream(teamCreation.getMembers())
        .map(frontendTeamMember -> {
          var teamMember = new TeamMember();
          teamMember.setTeamDetail(teamDetail);
          teamMember.setUsername(frontendTeamMember.getUsername());
          teamMember.setCanManageTeam(frontendTeamMember.getCanManageTeam());
          teamMember.setCanModifyForms(frontendTeamMember.getCanModifyForms());
          teamMember.setCreatedTimestamp(Instant.now());
          teamMember.setCreatedBy(appUser.getUsername());
          teamMember.setStatusControl(true);
          return teamMember;
        })
        .collect(Collectors.toList());

    teamMemberService.saveAll(members);
  }
}
