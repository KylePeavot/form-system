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


    cy.get('div[name="radio-group-container"]').within(() => {
      cy.get('[name="popper-button"]').click();
      cy.get('button').contains("Delete").click();
    });

    cy.get('div[name="checkbox-group-container"]').within(() => {
      cy.get('[name="popper-button"]').click();
      cy.get('button').contains("Delete").click();
    });

    cy.get('[name="text-field-container"]').should("not.exist");
    cy.get('[name="text-area-container"]').should("not.exist");
    cy.get('[name="checkbox-question-container"]').should("not.exist");
    cy.get('[name="checkbox-group-container"]').should("not.exist");
    cy.get('[name="radio-group-container"]').should("not.exist");
  });

  it("has a button on each component to edit the title and guidance on hover", () => {
    cy.get("button").contains("Text field").click();

    cy.get('div[name="text-field-container"]').get('[class="question__title-row editable-element"]').within(() => {
      //forcing the click because cypress doesn't can't handle hovering over an element to make another element visible
      cy.get('[class="hidden-button ph-pencil"]').click({force: true});
      cy.get('textarea').type(" test {enter}");
      cy.get('h2').contains("Question title test");
    });

    cy.get('[class="question__guidance-container editable-element"]').within(() => {
      //forcing the click because cypress doesn't can't handle hovering over an element to make another element visible
      cy.get('[class="hidden-button ph-pencil"]').click({force: true});
      cy.get('textarea').type(" test {enter}");
      cy.get('p').contains("Question guidance test");
    });
  });

  it("can add and remove checkbox questions to and from a checkbox group", () => {
    cy.get('button').contains("Multiple checkboxes").click();

    cy.get('div[name="checkbox-group-container"]').within(() => {
      cy.get('button').contains("+ Add new checkbox").click();
      cy.get('[class="checkbox__container"]').should('have.length', 3);

      cy.get('[class="checkbox__container"]').eq(0).within(() => {
        cy.get('[class="hidden-button ph-pencil"]').click({force: true});
        cy.get('textarea[class="checkbox__label-edit"]').type(" test {enter}");
        cy.get('label').contains("Add a response here test");

        cy.get('[class="hidden-button ph-trash"]').click({force: true});
      });

      cy.get('[class="checkbox__container"]').should('have.length', 2);
    });
  });
});
