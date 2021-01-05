import IPageDetail from "@/models/navigation/IPageDetail";
import PageDetailLink from "@/models/navigation/PageDetailLink";
import Pages from "@/models/navigation/Pages";

describe("PageDetailLink Tests", () => {

  describe("Creation", () => {
    it("Has expected default value", async () => {
      const pageDetail: IPageDetail = {name: "Detail", url: "/link"};
      const link = new PageDetailLink(pageDetail);
      expect(link.children.length).toEqual(0);
      expect(link.parent).toBeUndefined();
      expect(link.route).toBe(pageDetail);
    });

    it("Has correct parent", async () => {
      const parentDetail: IPageDetail = {name: "Detail", url: "/link"};
      const childDetail: IPageDetail = {name: "Child", url: "/link/child"};
      const parentLink = new PageDetailLink(parentDetail);
      const childLink = new PageDetailLink(childDetail, parentLink);
      expect(childLink.parent).toBe(parentLink);
    });

    it("Has correct children", async () => {
      const parentDetail: IPageDetail = {name: "Detail", url: "/link"};
      const childDetail: IPageDetail = {name: "Child", url: "/link/child"};
      const parentLink = new PageDetailLink(parentDetail);
      const childLink = new PageDetailLink(childDetail, parentLink);
      parentLink.addChild(childLink);
      expect(parentLink.children).toContain(childLink);
      expect(childLink.children.length).toEqual(0);
    });

  });

  describe("Page Routes", () => {
    Pages.generatePageDetailLinks();
  })

});