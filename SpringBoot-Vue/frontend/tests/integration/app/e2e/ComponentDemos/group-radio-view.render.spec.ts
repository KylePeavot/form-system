import Pages from "@/models/navigation/Pages";

describe("Radio checkbox view", () => {


    before(() => {
        cy.visit(Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.GROUP_RADIO.url);
    });

    it("All states are false", () => {
        // Should be false
        cy.get(':radio').first().should('be.not.checked');
        // Should be false
        cy.get(':radio').last().should('be.not.checked');
    });

    it("First option is selected", () => {
        cy.get(':radio').first().click();
        // Should be true
        cy.get(':radio').first().should('be.checked');
        // Should be false
        cy.get(':radio').last().should('be.not.checked');
    });

    it("First one false second true", () => {
        cy.get(':radio').last().click();
        // Should be false
        cy.get(':radio').first().should('be.not.checked');
        // Should be true
        cy.get(':radio').last().should('be.checked');
})})