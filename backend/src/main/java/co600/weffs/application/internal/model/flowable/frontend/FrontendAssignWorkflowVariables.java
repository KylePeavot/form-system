package co600.weffs.application.internal.model.flowable.frontend;

import co600.weffs.application.internal.model.team.TeamDetail;
import lombok.Data;

@Data
public class FrontendAssignWorkflowVariables {

  private String _assigner;
  private TeamDetail _assignerTeamDetail;
  private String _targetUser;
  private int _formId;

}