package co600.weffs.application.internal.model.flowable.frontend;

import co600.weffs.application.internal.model.team.TeamDetail;
import java.util.Map;
import lombok.Data;

@Data
public class FrontendAssignWorkflowVariables {

  private String _assigner;
  //FS-55 TODO fix this to correctly receive stuff (might need to update the frontend version of this class too
  private FrontendTeam _assignerTeamDetail;
  private String _targetUser;
  private int _formId;

}