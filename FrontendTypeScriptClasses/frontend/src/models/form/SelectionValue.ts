import SelectionValueInterface from "@/models/form/interfaces/SelectionValueInterface";

export default class SelectionValue {

  private _questionDetailId: number | null;
  private _label: string;
  private _value: boolean;

  constructor(label: string, value: boolean, questionDetailId?: number) {
    this._label = label;
    this._value = value;
    this._questionDetailId = questionDetailId || -1;
  }

  static mapSelectionValueInterfaceToSelectionValue(selectionValueInterface: any): SelectionValue {
    selectionValueInterface = selectionValueInterface as SelectionValueInterface;
    return new SelectionValue(selectionValueInterface._label, selectionValueInterface._value, selectionValueInterface._questionDetailId);
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