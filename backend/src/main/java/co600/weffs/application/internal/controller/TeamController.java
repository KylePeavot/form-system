package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.team.frontend.FrontendTeamCreation;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.services.team.TeamCreationService;
import co600.weffs.application.internal.services.team.TeamMemberService;
import co600.weffs.application.internal.validators.teams.TeamCreationValidator;
import co600.weffs.application.internal.view.team.TeamView;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public List<TeamView> getAvailableTeams(@RequestAttribute("User") AppUser appUser) {
    return teamMemberService.getTeamViewForUsername(appUser.getUsername());
  }

  @MustBeAuthorized
  @PostMapping("/new")
  public Map<String, ?> createTeam(@RequestAttribute("User") AppUser appUser, @RequestBody FrontendTeamCreation teamCreation) {
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

}
