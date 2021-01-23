/// <reference types="cypress" />

import Pages from "@/models/navigation/Pages";

describe("Dashboard", () => {

  beforeEach(() => {
    cy.visit(Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD.  url);
  })

  it("Navbar rendering", async () => {
    for (let key in Pages.ROUTES.SHOWN_IN_NAVBAR) {
      cy.get("a").contains((Pages.ROUTES.SHOWN_IN_NAVBAR as any)[key].name);
    }
  });
});