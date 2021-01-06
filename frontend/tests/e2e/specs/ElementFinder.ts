export default class ElementFinder {

  private _element: WebdriverIO.Element;

  constructor() {
    this._element = $("<html>");
  }

  get element(): WebdriverIO.Element {
    return this._element;
  }

  public withAttributeWithValueOf(attr: string, value: string) {
    this._element = this._element.$(`[${attr}="${value}"]`);
    return this;
  }

}