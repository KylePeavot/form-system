import App from '../../pageobjects/app.page';
import Pages from "../../../../src/models/navigation/Pages";
import ElementFinder from "../ElementFinder";

describe('Navigation rendering', () => {

  before(() => {
    App.open();
  })

  it('Should contain image', () => {
    expect($("//*[@id='university-of-kent-logo']")).toBeDefined();
  });

  it('Should have all core pages displayed', () => {
    for (const value of Object.values(Pages.ROUTES.SHOWN_IN_NAVBAR)) {
      const node = new ElementFinder()
        .withAttributeWithValueOf("href", value.url);

      expect(node.element).toBeDefined();
      expect(node.element).toHaveTextContaining(value.name);
    }
  });
});