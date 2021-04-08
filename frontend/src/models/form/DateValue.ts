import DateValueInterface from "@/models/form/interfaces/DateValueInterface";

export default class DateValue {

  private _value: Date;

  constructor(value: Date) {
    this._value = value;
  }

  static mapDateValueInterfaceToDateValue(dateValueInterface: any): DateValue {
    dateValueInterface = dateValueInterface as DateValueInterface;
    return new DateValue(dateValueInterface._value);
  }

  get value(): Date {
    return this._value;
  }

  set value(value: Date) {
    this._value = value;
  }
}