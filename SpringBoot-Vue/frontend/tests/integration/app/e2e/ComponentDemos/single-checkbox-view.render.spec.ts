import Pages from "@/models/navigation/Pages";

describe("Single checkbox view", () => {

  before(() => {
    cy.visit(Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.SINGLE_CHECKBOX.url);
  });

  it("Has an initial state of true", () => {
    cy.get("input").eq(0).should("be.checked");
    cy.get("[id='show_value']").contains("true");
  });

  it("Has been clicked", () => {
    cy.get("input").eq(0).click();
  });

  it("Now has a state of false", () => {
    cy.get("input").eq(0).should("not.be.checked");
    cy.get("[id='show_value']").contains("false");
  })



})