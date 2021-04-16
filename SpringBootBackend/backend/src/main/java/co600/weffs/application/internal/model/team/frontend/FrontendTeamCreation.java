package co600.weffs.application.internal.model.team.frontend;

import lombok.Data;

@Data
public class FrontendTeamCreation {

  private FrontendTeamMember[] members;
  private String name;

}
