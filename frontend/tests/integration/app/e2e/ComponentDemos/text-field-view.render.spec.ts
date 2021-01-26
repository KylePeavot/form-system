describe("Text field demo page", () => {

  before(() => {
    cy.visit("/components/text-field");
  });

  it("has the correct title with the correct level", () => {
    cy.get('h1').contains('Text field demo').should('exist');
  });

  it("has 2 text field questions with the correct text", () => {
    cy.get('[name="text-field-container"]').eq(0).within(() => {
      cy.get('h2').contains('Full name').should('exist');
      cy.get('p').contains('Please enter your first name, surname, and any middle names').should('exist');
      cy.get('input').should('exist');
    });
    cy.get('[name="text-field-container"]').eq(1).within(() => {
      cy.get('h3').contains('Mother\'s maiden name').should('exist');
      cy.get('p').contains('Please enter your mother\'s maiden name for... security reasons').should('exist');
      cy.get('input').should('exist');
    });
  });

})