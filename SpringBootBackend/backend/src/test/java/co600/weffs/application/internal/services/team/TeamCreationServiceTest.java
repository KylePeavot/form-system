package co600.weffs.application.internal.services.team;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verify;

import co600.weffs.application.MockitoTest;
import co600.weffs.application.internal.model.team.Team;
import co600.weffs.application.internal.model.team.TeamDetail;
import co600.weffs.application.internal.model.team.TeamMember;
import co600.weffs.application.internal.model.team.frontend.FrontendTeamCreation;
import co600.weffs.application.internal.model.team.frontend.FrontendTeamMember;
import co600.weffs.application.utils.UserTestUtils;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class TeamCreationServiceTest extends MockitoTest {

  @Mock
  private TeamService teamService;

  @Mock
  private TeamDetailService teamDetailService;

  @Mock
  private TeamMemberService teamMemberService;

  @InjectMocks
  private TeamCreationService teamCreationService;

  @Test
  void createTeam() {

    var user = UserTestUtils.createDefaultUndergraduateAppUser();
    var teamCreation = new FrontendTeamCreation();
    teamCreation.setName("Test name");
    var member = new FrontendTeamMember();
    member.setCanModifyForms(true);
    member.setCanManageTeam(true);
    member.setUsername("other_user");
    teamCreation.setMembers(new FrontendTeamMember[]{member});

    teamCreationService.createTeam(user, teamCreation);

    var teamCaptor = ArgumentCaptor.forClass(Team.class);
    verify(teamService).save(teamCaptor.capture());

    var teamDetailCaptor = ArgumentCaptor.forClass(TeamDetail.class);
    verify(teamDetailService).save(teamDetailCaptor.capture());

    var teamDetail = teamDetailCaptor.getValue();
    assertThat(teamDetail.getTeam()).isEqualTo(teamCaptor.getValue());
    assertThat(teamDetail.getName()).isEqualTo(teamCreation.getName());
    assertThat(teamDetail.getCreatedBy()).isEqualTo(user.getUsername());
    assertThat(teamDetail.getStatusControl()).isTrue();

    ArgumentCaptor<List<TeamMember>> membersCaptor = ArgumentCaptor.forClass(List.class);
    verify(teamMemberService).saveAll(membersCaptor.capture());

    assertThat(membersCaptor.getValue()).extracting(
        TeamMember::getTeamDetail,
        TeamMember::getUsername,
        TeamMember::getCreatedBy,
        TeamMember::getCanManageTeam,
        TeamMember::getCanModifyForms,
        TeamMember::getStatusControl
    ).containsExactlyInAnyOrder(
        tuple(
            teamDetail,
            teamCreation.getMembers()[0].getUsername(),
            user.getUsername(),
            teamCreation.getMembers()[0].getCanManageTeam(),
            teamCreation.getMembers()[0].getCanModifyForms(),
            true
        )
    );

  }
}