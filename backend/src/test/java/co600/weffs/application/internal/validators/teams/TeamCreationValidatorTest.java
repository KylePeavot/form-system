package co600.weffs.application.internal.validators.teams;

import static org.junit.jupiter.api.Assertions.assertThrows;

import co600.weffs.application.internal.model.team.frontend.FrontendTeamCreation;
import co600.weffs.application.internal.model.team.frontend.FrontendTeamMember;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class TeamCreationValidatorTest {

  private TeamCreationValidator validator = new TeamCreationValidator();

  @Test
  void validate_noMembers() {
    assertThrows(Exception.class, () -> {
      var teamCreation = new FrontendTeamCreation();
      teamCreation.setMembers(new FrontendTeamMember[]{});
      teamCreation.setName("Name");
      validator.validate(teamCreation);
    }, TeamCreationValidator.AT_LEAST_ONE_MEMBER_ERROR_MESSAGE);
  }

  @Test
  void validate_noName() {
    assertThrows(Exception.class, () -> {
      var teamCreation = new FrontendTeamCreation();
      teamCreation.setMembers(new FrontendTeamMember[]{
          new FrontendTeamMember(),
          new FrontendTeamMember()
      });
      validator.validate(teamCreation);
    }, TeamCreationValidator.NAME_NOT_BLANK_ERROR_MESSAGE);
  }

  @Test
  void validate_sensibleName_allSpaces() {
    assertThrows(Exception.class, () -> {
      var teamCreation = new FrontendTeamCreation();
      teamCreation.setMembers(new FrontendTeamMember[]{
          new FrontendTeamMember(),
          new FrontendTeamMember()
      });
      teamCreation.setName("    ");
      validator.validate(teamCreation);
    }, TeamCreationValidator.SENSIBLE_NAME_ERROR_MESSAGE);
  }

  @Test
  void validate_sensibleName_twoSpacesBetween() {
    assertThrows(Exception.class, () -> {
      var teamCreation = new FrontendTeamCreation();
      teamCreation.setMembers(new FrontendTeamMember[]{
          new FrontendTeamMember(),
          new FrontendTeamMember()
      });
      teamCreation.setName("a  b");
      validator.validate(teamCreation);
    }, TeamCreationValidator.SENSIBLE_NAME_ERROR_MESSAGE);
  }

  @Test
  void validate_sensibleName_underscores() {
    assertThrows(Exception.class, () -> {
      var teamCreation = new FrontendTeamCreation();
      teamCreation.setMembers(new FrontendTeamMember[]{
          new FrontendTeamMember(),
          new FrontendTeamMember()
      });
      teamCreation.setName("a_b");
      validator.validate(teamCreation);
    }, TeamCreationValidator.SENSIBLE_NAME_ERROR_MESSAGE);
  }

  @Test
  void validate_sensibleName_memberCantManageTeam() {
    assertThrows(Exception.class, () -> {
      var teamCreation = new FrontendTeamCreation();
      var member = new FrontendTeamMember();
      member.setCanManageTeam(false);
      member.setCanModifyForms(true);
      teamCreation.setMembers(new FrontendTeamMember[]{member});
      teamCreation.setName("a_b");
      validator.validate(teamCreation);
    }, TeamCreationValidator.AT_LEAST_ONE_MANAGE_TEAM_ERROR_MESSAGE);
  }

  @Test
  void validate_sensibleName_memberCantModifyForm() {
    assertThrows(Exception.class, () -> {
      var teamCreation = new FrontendTeamCreation();
      var member = new FrontendTeamMember();
      member.setCanManageTeam(true);
      member.setCanModifyForms(false);
      teamCreation.setMembers(new FrontendTeamMember[]{member});
      teamCreation.setName("a_b");
      validator.validate(teamCreation);
    }, TeamCreationValidator.AT_LEAST_ONE_MODIFY_FORMS_ERROR_MESSAGE);
  }

  @Test
  @SneakyThrows
  void validate_valid() {
    var teamCreation = new FrontendTeamCreation();
    var member = new FrontendTeamMember();
    member.setCanManageTeam(true);
    member.setCanModifyForms(true);
    teamCreation.setMembers(new FrontendTeamMember[]{member});
    teamCreation.setName("a b");
    validator.validate(teamCreation);
  }
}