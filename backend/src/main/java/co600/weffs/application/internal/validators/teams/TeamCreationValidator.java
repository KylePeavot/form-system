package co600.weffs.application.internal.validators.teams;

import co600.weffs.application.internal.model.team.frontend.FrontendTeamCreation;
import co600.weffs.application.internal.model.team.frontend.FrontendTeamMember;
import java.util.Arrays;
import org.springframework.stereotype.Service;

@Service
public class TeamCreationValidator implements RequestValidator {

  public static final String AT_LEAST_ONE_MEMBER_ERROR_MESSAGE = "A team must have at least one member";
  public static final String NAME_NOT_BLANK_ERROR_MESSAGE = "Name must not be empty";
  public static final String SENSIBLE_NAME_ERROR_MESSAGE = "Name is not sensible";
  public static final String AT_LEAST_ONE_MANAGE_TEAM_ERROR_MESSAGE = "At least one member must be able to manage the team";
  public static final String AT_LEAST_ONE_MODIFY_FORMS_ERROR_MESSAGE = "At least one member must be able to modify forms within the team";

  @Override
  public void validate(Object responseBody) throws Exception {
    var teamCreation = (FrontendTeamCreation) responseBody;
    if (teamCreation.getMembers() == null || teamCreation.getMembers().length == 0) {
      throw new Exception(AT_LEAST_ONE_MEMBER_ERROR_MESSAGE);
    }
    if (teamCreation.getName() == null || teamCreation.getName().trim().length() == 0) {
      throw new Exception(NAME_NOT_BLANK_ERROR_MESSAGE);
    }
    if (!teamCreation.getName().matches("^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$")) {
      throw new Exception(SENSIBLE_NAME_ERROR_MESSAGE);
    }

    // Ensure at least one member can manage the team
    var atLeastOneCanManageTeam = Arrays.stream(teamCreation.getMembers()).anyMatch(FrontendTeamMember::getCanManageTeam);
    if (!atLeastOneCanManageTeam) {
      throw new Exception(AT_LEAST_ONE_MANAGE_TEAM_ERROR_MESSAGE);
    }

    // Ensure at least one member can modify forms in the team
    var atLeastOneCanModifyForms = Arrays.stream(teamCreation.getMembers()).anyMatch(FrontendTeamMember::getCanModifyForms);
    if (!atLeastOneCanModifyForms) {
      throw new Exception(AT_LEAST_ONE_MODIFY_FORMS_ERROR_MESSAGE);
    }
  }

}
