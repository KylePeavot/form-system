package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.error.NotAuthorizedException;
import co600.weffs.application.internal.model.team.frontend.FrontendTeamCreation;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.services.team.TeamCreationService;
import co600.weffs.application.internal.services.team.TeamMemberService;
import co600.weffs.application.internal.validators.teams.TeamCreationValidator;
import co600.weffs.application.internal.view.team.TeamView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

  private final TeamMemberService teamMemberService;
  private final TeamCreationService teamCreationService;
  private final TeamCreationValidator teamCreationValidator;

  @Autowired
  public TeamController(
      TeamMemberService teamMemberService,
      TeamCreationService teamCreationService,
      TeamCreationValidator teamCreationValidator) {
    this.teamMemberService = teamMemberService;
    this.teamCreationService = teamCreationService;
    this.teamCreationValidator = teamCreationValidator;
  }

  @MustBeAuthorized
  @GetMapping
  public List<TeamView> getAvailableTeams(@RequestAttribute("User") AppUser appUser,
                                          @RequestParam(name = "canModifyForms", required = false) Optional<Boolean> modifyFormsFilter,
                                          @RequestParam(name = "canManageTeam", required = false) Optional<Boolean> manageTeamFilter) {
    return teamMemberService.getTeamViewForUsername(appUser.getUsername())
            .stream()
            .filter(teamView -> modifyFormsFilter.isEmpty() || teamMemberService.canUserModifyFormsForTeamView(appUser, teamView))
            .filter(teamView -> manageTeamFilter.isEmpty() || teamMemberService.canUserManageTeamView(appUser, teamView))
            .collect(Collectors.toList());
  }

  @MustBeAuthorized
  @PostMapping("/new")
  public Map<String, ?> createTeam(@RequestAttribute("User") AppUser appUser,
      @RequestBody FrontendTeamCreation teamCreation) {

    try {
      teamCreationValidator.validate(teamCreation);
      teamCreationService.createTeam(appUser, teamCreation);
      return Map.of("success", true);
    } catch (Exception e) {
      return Map.of(
          "success", false,
          "error", e.getMessage()
      );
    }
  }

  @MustBeAuthorized
  @GetMapping("/{id}")
  public TeamView getTeam(@RequestAttribute("User") AppUser appUser,
      @PathVariable("id") Integer id) {

    var teamView = teamMemberService.getTeamViewById(id);
    if (teamMemberService.canUserManageTeamView(appUser, teamView)) {
      return teamView;
    }
    throw new NotAuthorizedException(String
        .format("User [%s] is not authorized to manage team with ID: [%d]", appUser.getUsername(),
            id));
  }

  @MustBeAuthorized
  @PostMapping("/{id}")
  public Map<String, ?> updateTeam(@RequestAttribute("User") AppUser appUser,
      @PathVariable("id") Integer id,
      @RequestBody FrontendTeamCreation teamCreation) {

    try {
      teamCreationValidator.validate(teamCreation);
      var teamView = teamMemberService.getTeamViewById(id);
      if (!teamMemberService.canUserManageTeamView(appUser, teamView)) {
        throw new NotAuthorizedException(String.format(
            "User [%s] is not authorized to modify team with ID [%d]",
            appUser.getUsername(), id
        ));
      }
      teamCreationService.updateTeam(appUser, id, teamCreation);
      return Map.of("success", true);
    } catch (Exception e) {
      return Map.of(
          "success", false,
          "error", e.getMessage()
      );
    }
  }

}
