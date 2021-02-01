import Pages from "@/models/navigation/Pages";

describe("Single checkbox view", () => {

  before(() => {
    cy.visit(Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.SINGLE_CHECKBOX.url);
  });

  it("Has an initial state of true", () => {
    cy.get("input").should(currentSubject =>
        currentSubject.is((index, element) => element.checked));
    cy.get("[id='show_value']").contains("true");
  });

  it("Has been clicked", () => {
    cy.get("input").click();
  });

  it("Now has a state of false", () => {
    cy.get("input").should(currentSubject =>
        currentSubject.is((index, element) => !element.checked));
    cy.get("[id='show_value']").contains("false");
  })



})