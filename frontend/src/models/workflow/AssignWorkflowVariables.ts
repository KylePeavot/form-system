import Team from "@/models/team/Team";

export default class AssignWorkflowVariables {

  private readonly _assigner: string;
  private readonly _assignerTeamDetail: Team;
  private readonly _targetUser: string;
  private readonly _formId: number;


  constructor(assigner: string, assignerTeamDetail: Team, targetUser: string, formId: number) {
    this._assigner = assigner;
    this._assignerTeamDetail = assignerTeamDetail;
    this._targetUser = targetUser;
    this._formId = formId;
  }
}