export default class CheckboxValue {

  private _label: string;
  private _value: boolean;

  constructor(label: string, value: boolean) {
    this._label = label;
    this._value = value;
  }

  get label(): string {
    return this._label;
  }

  set label(value: string) {
    this._label = value;
  }

  get value(): boolean {
    return this._value;
  }

  set value(value: boolean) {
    this._value = value;
  }
}