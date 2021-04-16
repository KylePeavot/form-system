package co600.weffs.application.internal.model.flowable.frontend;

import lombok.Data;

@Data
public class FrontendAssignWorkflowVariables {

  private String _assigner;
  private String[] _targetUsers;
  private Integer _formDetailId;

}