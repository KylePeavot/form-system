import Pages from "@/models/navigation/Pages";

describe("Form creator page", () => {
  beforeEach(() => {
    cy.visit(Pages.ROUTES.SHOWN_IN_NAVBAR.FORMS.subRoutes.NEW_FORM.url);
  });

  it("Has all component buttons", () => {
    cy.get('div[class="sidebar-group__container"]').within(() => {
      cy.contains("Text field").should("exist");
      cy.contains("Large text field").should("exist");
      cy.contains("Single checkbox").should("exist");
      cy.contains("Multiple checkboxes").should("exist");
      cy.contains("Radio group").should("exist");
    })
  });

  it("All component buttons add the appropriate components", () => {
    cy.get('[name="text-field-container"]').should("not.exist");
    cy.get('[name="text-area-container"]').should("not.exist");
    cy.get('[name="checkbox-question-container"]').should("not.exist");
    cy.get('[name="checkbox-group-container"]').should("not.exist");
    cy.get('[name="radio-group-container"]').should("not.exist");

    cy.get('button').contains("Text field").click();
    cy.get('button').contains("Large text field").click();
    cy.get('button').contains("Single checkbox").click();
    cy.get('button').contains("Multiple checkboxes").click();
    cy.get('button').contains("Radio group").click();

    cy.get('[name="text-field-container"]').should("exist");
    cy.get('[name="text-area-container"]').should("exist");
    cy.get('[name="checkbox-question-container"]').should("exist");
    cy.get('[name="checkbox-group-container"]').should("exist");
    cy.get('[name="radio-group-container"]').should("exist");
  });

  it("Has popover menu on each component that allows deletion of the component", () => {
    cy.get("button").contains("Text field").click();
    cy.get('button').contains("Large text field").click();
    cy.get('button').contains("Single checkbox").click();
    cy.get('button').contains("Multiple checkboxes").click();
    cy.get('button').contains("Radio group").click();

    cy.get('div[name="text-field-container"]').within(() => {
      cy.get('[name="popper-button"]').click();
      cy.get('button').contains("Delete").click();
    });

    cy.get('div[name="text-area-container"]').within(() => {
      cy.get('[name="popper-button"]').click();
      cy.get('button').contains("Delete").click();
    });

    cy.get('div[name="checkbox-question-container"]').within(() => {
      cy.get('[name="popper-button"]').click();
      cy.get('button').contains("Delete").click();
    });

    cy.get('div[name="checkbox-group-container"]').within(() => {
      cy.get('[name="popper-button"]').click();
      cy.get('button').contains("Delete").click();
    });

    cy.get('div[name="radio-group-container"]').within(() => {
      cy.get('[name="popper-button"]').click();
      cy.get('button').contains("Delete").click();
    });

    cy.get('[name="text-field-container"]').should("not.exist");
    cy.get('[name="text-area-container"]').should("not.exist");
    cy.get('[name="checkbox-question-container"]').should("not.exist");
    cy.get('[name="checkbox-group-container"]').should("not.exist");
    cy.get('[name="radio-group-container"]').should("not.exist");
  });
});
