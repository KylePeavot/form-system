package co600.weffs.application.internal.validators.teams;

import co600.weffs.application.internal.model.team.frontend.FrontendTeamCreation;
import org.springframework.stereotype.Service;

@Service
public class TeamCreationValidator implements RequestValidator {

  @Override
  public void validate(Object responseBody) throws Exception {
    var teamCreation = (FrontendTeamCreation) responseBody;
    if (teamCreation.getMembers() == null || teamCreation.getMembers().length == 0) {
      throw new Exception("A team must have at least one member");
    }
    if (teamCreation.getName() == null || teamCreation.getName().trim().length() == 0) {
      throw new Exception("Name must not be empty");
    }
    if (!teamCreation.getName().matches("^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$")) {
      throw new Exception("Name is not sensible");
    }
  }

}
