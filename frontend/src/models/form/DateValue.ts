import DateValueInterface from "@/models/form/interfaces/DateValueInterface";
import moment from "moment";

export default class DateValue {

  public _value: string;

  constructor(value: string) {
    this._value = value;
  }

  public static mapDateValueInterfaceToDateValue(dateValueInterface: any): DateValue {
    dateValueInterface = dateValueInterface as DateValueInterface;
    return new DateValue(moment(dateValueInterface._value).format("YYYY-MM-DD"));
  }

  get value(): string {
    return this._value;
  }

  set value(value: string) {
    this._value = value;
  }
}