describe("Text area demo page", () => {

  before(() => {
    cy.visit("/components/text-area");
  });

  it("has the correct title with the correct level", () => {
    cy.get('h1').contains('Large text field demo').should('exist');
  });

  it("has 2 text area questions with the correct text", () => {
    cy.get('[name="text-area-container"]').eq(0).within(() => {
      cy.get('h2').contains('Tell us about yourself').should('exist');
      cy.get('p').contains('100 words max').should('exist');
      cy.get('textarea').should('exist');
    });

    cy.get('[name="text-area-container"]').eq(1).within(() => {
      cy.get('h2').contains('Why do you want to change to this module?').should('exist');
      cy.get('p').should('not.exist');
      cy.get('textarea').should('exist');
    });
  });

})