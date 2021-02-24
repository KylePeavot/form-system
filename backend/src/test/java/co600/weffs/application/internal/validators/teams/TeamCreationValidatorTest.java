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
    });
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
    });
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
    });
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
    });
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
    });
  }

  @Test
  @SneakyThrows
  void validate_valid() {
    var teamCreation = new FrontendTeamCreation();
    teamCreation.setMembers(new FrontendTeamMember[]{
        new FrontendTeamMember(),
        new FrontendTeamMember()
    });
    teamCreation.setName("a b");
    validator.validate(teamCreation);
  }
}