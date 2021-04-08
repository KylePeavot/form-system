import TextValueInterface from "@/models/form/interfaces/TextValueInterface";

export default class TextValue {
  // had to set to public due to weird error where doing this.$props on the second edited input that used a TextValue
  // object as a model caused the textValue prop to have both the _value field and the accessor method value
  // I am unable to explain why
  public _value: string;

  constructor(value: string) {
    this._value = value;
  }

  static mapTextValueInterfaceToTextValue(textValueInterface: any): TextValue {
    textValueInterface = textValueInterface as TextValueInterface;
    return new TextValue(textValueInterface._value);
  }

  set value(value: string) {
    this._value = value;
  }
}