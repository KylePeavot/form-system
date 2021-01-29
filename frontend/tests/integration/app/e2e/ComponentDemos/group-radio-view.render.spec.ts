import Pages from "@/models/navigation/Pages";

describe("Radio checkbox view", () => {


    before(() => {
        cy.visit(Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.GROUP_RADIO.url);
    });

    it("All states are false", () => {
        cy.get("input").each(input => expect((input.get()[0] as HTMLInputElement).checked).eq(false));
    });

    it("First option is selected", () => {
        cy.get("input").then(firstInput => firstInput.get()[0].click());
        cy.get("input").each(input => expect((input.get()[0] as HTMLInputElement).checked).eq(true));
    });

    it("First one false second true", () => {
        cy.get("input").then(firstInput => firstInput.get()[1].click());
        cy.get("input").each(input => expect((input.get()[0] as HTMLInputElement).checked).eq(false));
        cy.get("input").each(input => expect((input.get()[1] as HTMLInputElement).checked).eq(true));

})})