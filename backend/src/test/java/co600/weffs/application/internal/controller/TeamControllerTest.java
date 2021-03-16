package co600.weffs.application.internal.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import co600.weffs.application.TestableController;
import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.team.TeamMember;
import co600.weffs.application.internal.model.team.frontend.FrontendTeamCreation;
import co600.weffs.application.internal.services.team.TeamCreationService;
import co600.weffs.application.internal.services.team.TeamMemberService;
import co600.weffs.application.internal.validators.teams.TeamCreationValidator;
import co600.weffs.application.internal.view.team.TeamView;
import co600.weffs.application.utils.UserTestUtils;
import co600.weffs.application.utils.ValueMapUtils;
import co600.weffs.application.utils.routes.Router;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;


@WebMvcTest(
    controllers = TeamController.class,
    excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
class TeamControllerTest extends TestableController {

  @MockBean
  private TeamMemberService teamMemberService;

  @MockBean
  private TeamCreationService teamCreationService;

  @MockBean
  private TeamCreationValidator teamCreationValidator;

  private AppUser user;

  @BeforeEach
  void setUp() {
    user = UserTestUtils.createDefaultUndergraduateAppUser();
  }

  @SneakyThrows
  @Test
  void getAvailableTeams() {
    var teamMember = new TeamMember();
    var teamView = new TeamView(1, "test_team", List.of(teamMember));
    when(teamMemberService.getTeamViewForUsername(user.getUsername()))
        .thenReturn(List.of(teamView));

    var response = mockMvc.perform(
        get(Router.determineRoute(on(TeamController.class).getAvailableTeams(null)))
            .with(mockHttpServletRequest -> {
              mockHttpServletRequest.setAttribute("User", user);
              return mockHttpServletRequest;
            }))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();

    List<TeamView> result = new ValueMapUtils<List<LinkedHashMap<?, ?>>>().mapResponse(response)
        .stream()
        .map(linkedHashMap -> jacksonMapper.convertValue(linkedHashMap, TeamView.class))
        .collect(Collectors.toList());
    assertThat(result.get(0)).isEqualTo(teamView);
  }

  @SneakyThrows
  @Test
  void getAvailableTeams_unauthenticated() {
    var teamMember = new TeamMember();
    var teamView = new TeamView(1, "test_team", List.of(teamMember));
    when(teamMemberService.getTeamViewForUsername(user.getUsername()))
        .thenReturn(List.of(teamView));

    mockMvc.perform(
        get(Router.determineRoute(on(TeamController.class).getAvailableTeams(null))))
        .andExpect(status().is4xxClientError());
  }

  @SneakyThrows
  @Test
  void createTeam() {
    var teamCreation = new FrontendTeamCreation();
    var response = mockMvc.perform(
        post(Router.determineRoute(on(TeamController.class).createTeam(null, null)))
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonMapper.writeValueAsString(teamCreation))
            .with(mockHttpServletRequest -> {
              mockHttpServletRequest.setAttribute("User", user);
              return mockHttpServletRequest;
            }))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();
    var result = new ValueMapUtils<Map<String, ?>>().mapResponse(response);
    assertThat(result.get("success")).isEqualTo(true);
  }

  @SneakyThrows
  @Test
  void createTeam_failValidation() {
    var teamCreation = new FrontendTeamCreation();
    Mockito.doThrow(new Exception("Fake error"))
        .when(teamCreationValidator)
        .validate(teamCreation);
    var response = mockMvc.perform(
        post(Router.determineRoute(on(TeamController.class).createTeam(null, null)))
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonMapper.writeValueAsString(teamCreation))
            .with(mockHttpServletRequest -> {
              mockHttpServletRequest.setAttribute("User", user);
              return mockHttpServletRequest;
            }))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();
    var result = new ValueMapUtils<Map<String, ?>>().mapResponse(response);
    assertThat(result.get("success")).isEqualTo(false);
  }

  @SneakyThrows
  @Test
  void createTeam_unauthenticated() {
    var teamCreation = new FrontendTeamCreation();
    mockMvc.perform(
        post(Router.determineRoute(on(TeamController.class).createTeam(null, null)))
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonMapper.writeValueAsString(teamCreation)))
        .andExpect(status().is4xxClientError());
  }

  @SneakyThrows
  @Test
  void createTeam_noData() {
    mockMvc.perform(
        post(Router.determineRoute(on(TeamController.class).createTeam(null, null)))
            .with(mockHttpServletRequest -> {
              mockHttpServletRequest.setAttribute("User", user);
              return mockHttpServletRequest;
            }))
        .andExpect(status().is4xxClientError());
  }

  @SneakyThrows
  @Test
  void updateTeam_noData() {
    mockMvc.perform(
        post(Router.determineRoute(on(TeamController.class).updateTeam(null, 1, null)))
            .with(mockHttpServletRequest -> {
              mockHttpServletRequest.setAttribute("User", user);
              return mockHttpServletRequest;
            }))
        .andExpect(status().is4xxClientError());
  }

  @SneakyThrows
  @Test
  void updateTeam_unauthorized() {
    var teamCreation = new FrontendTeamCreation();
    mockMvc.perform(
        post(Router.determineRoute(on(TeamController.class).updateTeam(null, 1, null)))
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonMapper.writeValueAsString(teamCreation)))
        .andExpect(status().is4xxClientError());
  }

  @SneakyThrows
  @Test
  void updateTeam_valid() {
    var teamCreation = new FrontendTeamCreation();
    var teamView = new TeamView();
    when(teamMemberService.getTeamViewByIdForUser(1)).thenReturn(teamView);
    when(teamMemberService.canUserManageTeamView(user, teamView)).thenReturn(true);

    var response = mockMvc.perform(
        post(Router.determineRoute(on(TeamController.class).updateTeam(null, 1, null)))
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonMapper.writeValueAsString(teamCreation))
            .with(mockHttpServletRequest -> {
              mockHttpServletRequest.setAttribute("User", user);
              return mockHttpServletRequest;
            }))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();
    var result = new ValueMapUtils<Map<String, ?>>().mapResponse(response);
    assertThat(result.get("success")).isEqualTo(true);
  }

  @SneakyThrows
  @Test
  void updateTeam_invalidData() {
    var teamCreation = new FrontendTeamCreation();
    var teamView = new TeamView();
    when(teamMemberService.getTeamViewByIdForUser(1)).thenReturn(teamView);
    when(teamMemberService.canUserManageTeamView(user, teamView)).thenReturn(true);

    doAnswer(invocationOnMock -> {
      throw new Exception("Fake error");
    }).when(teamCreationValidator).validate(teamCreation);

    var response = mockMvc.perform(
        post(Router.determineRoute(on(TeamController.class).updateTeam(null, 1, null)))
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonMapper.writeValueAsString(teamCreation))
            .with(mockHttpServletRequest -> {
              mockHttpServletRequest.setAttribute("User", user);
              return mockHttpServletRequest;
            }))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();
    var result = new ValueMapUtils<Map<String, ?>>().mapResponse(response);
    assertThat(result.get("success")).isEqualTo(false);
  }

  @SneakyThrows
  @Test
  void updateTeam_cantManageTeam() {
    var teamCreation = new FrontendTeamCreation();
    var teamView = new TeamView();
    when(teamMemberService.getTeamViewByIdForUser(1)).thenReturn(teamView);
    when(teamMemberService.canUserManageTeamView(user, teamView)).thenReturn(false);

    var response = mockMvc.perform(
        post(Router.determineRoute(on(TeamController.class).updateTeam(null, 1, null)))
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonMapper.writeValueAsString(teamCreation))
            .with(mockHttpServletRequest -> {
              mockHttpServletRequest.setAttribute("User", user);
              return mockHttpServletRequest;
            }))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();
    var result = new ValueMapUtils<Map<String, ?>>().mapResponse(response);
    assertThat(result.get("success")).isEqualTo(false);
  }

  @SneakyThrows
  @Test
  void updateTeam_noTeamView() {
    var teamCreation = new FrontendTeamCreation();
    var teamView = new TeamView();
    when(teamMemberService.getTeamViewByIdForUser(1)).thenAnswer(invocationOnMock -> {
      throw new Exception("Internal error");
    });
    when(teamMemberService.canUserManageTeamView(user, teamView)).thenReturn(true);

    var response = mockMvc.perform(
        post(Router.determineRoute(on(TeamController.class).updateTeam(null, 1, null)))
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonMapper.writeValueAsString(teamCreation))
            .with(mockHttpServletRequest -> {
              mockHttpServletRequest.setAttribute("User", user);
              return mockHttpServletRequest;
            }))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();
    var result = new ValueMapUtils<Map<String, ?>>().mapResponse(response);
    assertThat(result.get("success")).isEqualTo(false);
  }
}