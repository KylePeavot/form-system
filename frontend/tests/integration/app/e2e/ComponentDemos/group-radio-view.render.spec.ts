import Pages from "@/models/navigation/Pages";

describe("Radio checkbox view", () => {


    before(() => {
        cy.visit(Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.GROUP_RADIO.url);
    });

    it("All states are false", () => {
        cy.get("input").each(input => expect((input.get()[0] as HTMLInputElement).checked).eq(false));
    });

    it("First option is selected", () => {
        cy.get("input").eq(0).click();
        cy.get("input").each((element, index) => {
            if (index === 0) {
                // Should be true
                expect((element.get()[0] as HTMLInputElement).checked).eq(true);
            } else {
                // Should be false
                expect((element.get()[0] as HTMLInputElement).checked).eq(false);
            }
        });
    });

    it("First one false second true", () => {
        cy.get("input").eq(1).click();
        cy.get("input").each((element, index) => {
            if (index === 1) {
                // Should be true
                expect((element.get()[0] as HTMLInputElement).checked).eq(true);
            } else {
                // Should be false
                expect((element.get()[0] as HTMLInputElement).checked).eq(false);
            }
        });
})})