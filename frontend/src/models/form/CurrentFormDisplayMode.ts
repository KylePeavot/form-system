export default class CurrentFormDisplayMode {
  private _isReadOnly: boolean;

  private _isEdit: boolean;

  private _isFill: boolean;

  constructor(isReadOnly: boolean, isEdit: boolean, isFill: boolean) {
    this._isReadOnly = isReadOnly;
    this._isEdit = isEdit;
    this._isFill = isFill;
  }


  get isReadOnly(): boolean {
    return this._isReadOnly;
  }

  set isReadOnly(value: boolean) {
    this._isReadOnly = value;
  }

  get isEdit(): boolean {
    return this._isEdit;
  }

  set isEdit(value: boolean) {
    this._isEdit = value;
  }

  get isFill(): boolean {
    return this._isFill;
  }

  set isFill(value: boolean) {
    this._isFill = value;
  }
}