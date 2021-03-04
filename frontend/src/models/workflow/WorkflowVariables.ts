export default class WorkflowVariables {

  //Sometimes we may not need a target user (delete/submit) therefore this is nullable
  private readonly _targetUser: string | null;
  private readonly _formId: number; //TODO FS-52 Update this and all other BPMN stuff to use the id of a (new object called) FormSubmission


  constructor(targetUser: string, formId: number) {
    this._targetUser = targetUser;
    this._formId = formId;
  }

  get targetUser(): string | null {
    return this._targetUser;
  }

  get formId(): number {
    return this._formId;
  }
}