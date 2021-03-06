package co600.weffs.application.internal.services.team;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co600.weffs.application.MockitoTest;
import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.auth.SamlRole;
import co600.weffs.application.internal.model.team.Team;
import co600.weffs.application.internal.model.team.TeamDetail;
import co600.weffs.application.internal.model.team.TeamMember;
import co600.weffs.application.internal.repository.team.TeamMemberRepository;
import co600.weffs.application.internal.view.team.TeamView;
import co600.weffs.application.utils.UserTestUtils;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class TeamMemberServiceTest extends MockitoTest {

  @InjectMocks
  private TeamMemberService teamMemberService;

  @Mock
  private TeamMemberRepository teamMemberRepository;

  private AppUser nonAdminUser;
  private AppUser adminUser;

  private Team nonAdminTeam;
  private TeamDetail nonAdminTeamDetail;
  private Team adminTeam;
  private TeamDetail adminTeamDetail;

  private TeamMember nonAdminMember;
  private TeamMember adminMember;

  @BeforeEach
  void setUp() {
    nonAdminUser = UserTestUtils.createAppUser("non_admin", SamlRole.UNDERGRADUATE);
    adminUser = UserTestUtils.createAppUser("admin", SamlRole.STAFF);

    nonAdminTeam = new Team();
    nonAdminTeam.setId(1);
    nonAdminTeamDetail = new TeamDetail();
    nonAdminTeamDetail.setId(1);
    nonAdminTeamDetail.setName("Non Admin");
    nonAdminTeamDetail.setTeam(nonAdminTeam);

    adminTeam = new Team();
    adminTeam.setId(2);
    adminTeamDetail = new TeamDetail();
    adminTeamDetail.setName("Admin");
    adminTeamDetail.setId(2);
    adminTeamDetail.setTeam(adminTeam);

    nonAdminMember = new TeamMember();
    nonAdminMember.setTeamDetail(nonAdminTeamDetail);
    adminMember = new TeamMember();
    adminMember.setTeamDetail(adminTeamDetail);

    when(teamMemberRepository.findAllByTeamDetailInAndStatusControlIsTrue(eq(Set.of(nonAdminTeamDetail))))
        .thenReturn(List.of(nonAdminMember));

    when(teamMemberRepository.findAllByTeamDetailInAndStatusControlIsTrue(eq(Set.of(adminTeamDetail))))
        .thenReturn(List.of(adminMember));

    when(teamMemberRepository.findAllByTeamDetailInAndStatusControlIsTrue(eq(Set.of(adminTeamDetail, nonAdminTeamDetail))))
        .thenReturn(List.of(adminMember, nonAdminMember));
  }

  @Test
  void getTeamViewForUsername_userNotInAnyTeams() {
    when(teamMemberRepository.findAllByUsernameAndStatusControlIsTrue(nonAdminUser.getUsername()))
        .thenReturn(List.of());
    var result = teamMemberService.getTeamViewForUsername(nonAdminUser.getUsername());
    assertThat(result).isEmpty();

    verify(teamMemberRepository, times(1))
        .findAllByUsernameAndStatusControlIsTrue(nonAdminUser.getUsername());

    verify(teamMemberRepository, times(1))
        .findAllByTeamDetailInAndStatusControlIsTrue(Set.of());
  }

  @Test
  void getTeamViewForUsername_userInNonAdminTeam() {
    when(teamMemberRepository.findAllByUsernameAndStatusControlIsTrue(nonAdminUser.getUsername()))
        .thenReturn(List.of(nonAdminMember));
    var result = teamMemberService.getTeamViewForUsername(nonAdminUser.getUsername());
    assertThat(result).extracting(TeamView::getTeamId)
        .containsExactly(nonAdminTeam.getId());

    verify(teamMemberRepository, times(1))
        .findAllByUsernameAndStatusControlIsTrue(nonAdminUser.getUsername());

    verify(teamMemberRepository, times(1))
        .findAllByTeamDetailInAndStatusControlIsTrue(Set.of(nonAdminTeamDetail));
  }

  @Test
  void getTeamViewForUsername_userInAdminTeam() {
    when(teamMemberRepository.findAllByUsernameAndStatusControlIsTrue(adminUser.getUsername()))
        .thenReturn(List.of(adminMember));

    when(teamMemberRepository.findAll())
        .thenReturn(List.of(nonAdminMember, adminMember));

    var result = teamMemberService.getTeamViewForUsername(adminUser.getUsername());
    assertThat(result).extracting(TeamView::getTeamId)
        .containsExactlyInAnyOrder(adminTeam.getId(), nonAdminTeam.getId());

    verify(teamMemberRepository, times(1))
        .findAllByUsernameAndStatusControlIsTrue(adminUser.getUsername());

    verify(teamMemberRepository, times(1))
        .findAllByTeamDetailInAndStatusControlIsTrue(eq(Set.of(adminTeamDetail, nonAdminTeamDetail)));

    verify(teamMemberRepository, times(1)).findAll();
  }
}