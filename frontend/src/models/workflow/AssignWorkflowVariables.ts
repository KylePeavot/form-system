import Team from "@/models/team/Team";

export default class AssignWorkflowVariables {

  private readonly _assigner: string;
  private readonly _targetUsers: string[];
  private readonly _formDetailId: number;


  constructor(assigner: string, targetUsers: string[], formDetailId: number) {
    this._assigner = assigner;
    this._targetUsers = targetUsers;
    this._formDetailId = formDetailId;
  }
}