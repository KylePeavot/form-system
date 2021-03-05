package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.services.team.TeamMemberService;
import co600.weffs.application.internal.view.team.TeamView;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

  private final TeamMemberService teamMemberService;

  @Autowired
  public TeamController(
      TeamMemberService teamMemberService) {
    this.teamMemberService = teamMemberService;
  }

  @MustBeAuthorized
  @GetMapping
  public List<TeamView> getAvailableTeams(@RequestAttribute("User") AppUser appUser) {
    return teamMemberService.getTeamViewForUsername(appUser.getUsername());
  }

}
