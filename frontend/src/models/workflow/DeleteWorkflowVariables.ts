export default class DeleteWorkflowVariables {
  private readonly _fillerUsername: string;
  private readonly _formResponseId: number;

  constructor(fillerUsername: string, formResponseId: number) {
    this._fillerUsername = fillerUsername;
    this._formResponseId = formResponseId;
  }
}