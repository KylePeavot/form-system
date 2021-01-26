import PageDetail from "@/models/navigation/PageDetail";
import PageDetailLink from "@/models/navigation/PageDetailLink";
import Pages from "@/models/navigation/Pages";

describe("PageDetailLink Tests", () => {

  describe("Creation", () => {
    it("Has expected default value", async () => {
      const pageDetail: PageDetail = {name: "Detail", url: "/link"};
      const link = new PageDetailLink(pageDetail);
      assert.equal(link.children.length, 0);
      assert.isUndefined(link.parent);
    });

    it("Has correct parent", async () => {
      const parentDetail: PageDetail = {name: "Detail", url: "/link"};
      const childDetail: PageDetail = {name: "Child", url: "/link/child"};
      const parentLink = new PageDetailLink(parentDetail);
      const childLink = new PageDetailLink(childDetail, parentLink);
      assert.equal(childLink.parent, parentLink);
    });

    it("Has correct children", async () => {
      const parentDetail: PageDetail = {name: "Detail", url: "/link"};
      const childDetail: PageDetail = {name: "Child", url: "/link/child"};
      const parentLink = new PageDetailLink(parentDetail);
      const childLink = new PageDetailLink(childDetail, parentLink);
      parentLink.addChild(childLink);
      assert.includeMembers(parentLink.children, [childLink]);
      assert.equal(childLink.children.length, 0);
    });

  });

  describe("Page Routes", () => {

    const links = Pages.generatePageDetailLinks();

    it("Generates expected top-level route", async () => {
      const routeLink = Pages.getPageDetailLinkForRoute(Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD, links);
      assert.isDefined(routeLink);
    });

    it("Contains an expected subroute", async () => {
      const routeLink = Pages.getPageDetailLinkForRoute(Pages.ROUTES.SHOWN_IN_NAVBAR.FORMS, links);
      assert.isDefined(routeLink);
      assert.isDefined(routeLink!.findNestedChildRouteAsLink(Pages.ROUTES.SHOWN_IN_NAVBAR.FORMS.subRoutes.MY_FORMS));
    });
  });

});