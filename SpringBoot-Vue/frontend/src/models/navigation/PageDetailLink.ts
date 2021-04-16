import PageDetail from "@/models/navigation/PageDetail";

export default class PageDetailLink {

  private _parent: PageDetailLink | undefined;
  private readonly _children: PageDetailLink[];
  private _route: PageDetail;

  constructor(detail: PageDetail, parent?: PageDetailLink) {
    this._route = detail;
    this._parent = parent;
    this._children = [];
  }

  get parent(): PageDetailLink | undefined {
    return this._parent;
  }

  get children(): PageDetailLink[] {
    return [...this._children];
  }

  public addChild(link: PageDetailLink) {
    this._children.push(link);
  }

  get route(): PageDetail {
    return this._route;
  }

  public findNestedChildRouteAsLink(detail: PageDetail): PageDetailLink | undefined {
    if (this.route === detail) {
      return this;
    }
    if (this.children.length === 0) {
      return undefined;
    }
    for (const child of this.children) {
      const scan = child.findNestedChildRouteAsLink(detail);
      if (scan !== undefined) {
        return scan;
      }
    }
    return undefined;
  }
}