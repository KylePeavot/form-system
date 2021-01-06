import IPageDetail from "@/models/navigation/IPageDetail";

export default class PageDetailLink {

  private _parent: PageDetailLink | undefined;
  private readonly _children: PageDetailLink[];
  private _route: IPageDetail;

  constructor(detail: IPageDetail, parent?: PageDetailLink) {
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

  get route(): IPageDetail {
    return this._route;
  }

  public findNestedChildRouteAsLink(detail: IPageDetail): PageDetailLink | undefined {
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