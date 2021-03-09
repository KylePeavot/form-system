export default class FormComponent {
  private readonly _componentType: string;
  private _componentProps: object;
  private _order: number;


  constructor(componentType: string, componentProps: object, order: number) {
    this._componentType = componentType;
    this._componentProps = componentProps;
    this._order = order;
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

  get order(): number {
    return this._order;
  }

  set order(value: number) {
    this._order = value;
  }
}