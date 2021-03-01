import Pages from "@/models/navigation/Pages";

describe("Group checkbox view", () => {

  const colours: string[] = [];

  before(() => {
    cy.visit(Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.GROUP_CHECKBOX.url);
  });

  specify("Valid colours", () => {
    cy.get("label").each(element => {
      const wordsInLabel = element.text().split(" ");
      colours.push(wordsInLabel[wordsInLabel.length - 1]);
    });
  });

  it("All states are false", () => {
    cy.get("input").each(input => expect((input.get()[0] as HTMLInputElement).checked).eq(false));
  });

  it("Has toggled second element", () => {
    cy.get("input").then(firstInput => firstInput.get()[3].click());
  });

  it("Now has one true state", () => {
    cy.get("input").each((element, index) => {
      cy.get("input").eq(index).should(currentSubject =>
          currentSubject.is((index, element) => {
            const checked = (element as HTMLInputElement).checked;
            return index == 1 ? checked : !checked;
          }));
    });
    // Ensure this element exists as it shows the selection state.
    cy.get(`[id="likes-${colours[1]}"]`);
  });



})