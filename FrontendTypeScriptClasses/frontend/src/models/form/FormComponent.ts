import FormComponentInterface from "@/models/form/interfaces/FormComponentInterface";
import TextValue from "@/models/form/TextValue";
import SelectionValue from "@/models/form/SelectionValue";
import SelectionValueInterface from "@/models/form/interfaces/SelectionValueInterface";
import TextValueInterface from "@/models/form/interfaces/TextValueInterface";
import DateValue from "@/models/form/DateValue";

export default class FormComponent {
  private readonly _componentType: string;
  private _componentProps: object;
  private _order: number;

  constructor(componentType: string, componentProps: object, order: number) {
    this._componentType = componentType;
    this._componentProps = componentProps;
    this._order = order;
  }

  static mapFormComponentInterfaceToFormComponent(formComponentInterface: FormComponentInterface): FormComponent {
    return new FormComponent(formComponentInterface._componentType, FormComponent.mapComponentPropsToRealClasses(formComponentInterface._componentProps), formComponentInterface._order);
  }

  static mapComponentPropsToRealClasses(componentProps: object): object {
    Object.keys(componentProps).forEach(key => {
      if (key === "textValue") {
        Object(componentProps)[key] = TextValue.mapTextValueInterfaceToTextValue(Object(componentProps)[key]);
      } else if (key === "dateValue") {
        Object(componentProps)[key] = DateValue.mapDateValueInterfaceToDateValue(Object(componentProps)[key]);
      } else if (key === "selectionValue") {
        Object(componentProps)[key] = SelectionValue.mapSelectionValueInterfaceToSelectionValue(Object(componentProps)[key]);
      } else if (key === "selectionValues") {
        Object(componentProps)[key] = ((Object(componentProps)[key]) as Array<any>).map((value: SelectionValueInterface) => SelectionValue.mapSelectionValueInterfaceToSelectionValue(value));
      }
    });
    return componentProps
  }

  get componentType(): string {
    return this._componentType;
  }

  get componentProps(): object {
    return this._componentProps;
  }

  set componentProps(value: object) {
    this._componentProps = value;
  }

  public get order(): number {
    return this._order;
  }

  public set order(value: number) {
    this._order = value;
  }
}