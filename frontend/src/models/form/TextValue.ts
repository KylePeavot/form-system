import TextValueInterface from "@/models/form/interfaces/TextValueInterface";

export default class TextValue {
  private _value: string;

  constructor(value: string) {
    this._value = value;
  }

  static mapTextValueInterfaceToTextValue(textValueInterface: any) {
    textValueInterface = textValueInterface as TextValueInterface;
    return new TextValue(textValueInterface._value);
  }

  get value(): string {
    return this._value;
  }

  set value(value: string) {
    this._value = value;
  }
}