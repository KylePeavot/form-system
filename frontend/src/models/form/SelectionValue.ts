import SelectionValueInterface from "@/models/form/interfaces/SelectionValueInterface";

export default class SelectionValue {

  private _label: string;
  private _value: boolean;

  constructor(label: string, value: boolean) {
    this._label = label;
    this._value = value;
  }

  static mapSelectionValueInterfaceToSelectionValue(selectionValueInterface: SelectionValueInterface): SelectionValue {
    return new SelectionValue(selectionValueInterface._label, selectionValueInterface._value);
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