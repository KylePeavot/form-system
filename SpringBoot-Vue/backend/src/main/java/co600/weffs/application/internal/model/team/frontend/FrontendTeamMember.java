package co600.weffs.application.internal.model.team.frontend;

import lombok.Data;

@Data
public class FrontendTeamMember {

  private String username;
  private Boolean canModifyForms;
  private Boolean canManageTeam;

}
