import PageDetail, {SubrouteType} from "@/models/navigation/PageDetail";
import AuthenticationUtils from "../../utils/AuthenticationUtils";
import PageDetailLink from "./PageDetailLink";

type PageRouteStructure = {
    [area: string]: {
        [routeKey: string]: (() => Promise<PageDetail>) | PageDetail;
    };
};

export default class Pages {

    public static readonly ROUTES = {
        SHOWN_IN_NAVBAR: {
            DASHBOARD: {
                name: "Dashboard",
                url: "/"
            },
            TEAMS: {
                name: "Teams",
                url: "/teams",
                subRoutes: {
                    CREATE_TEAM: {name: "Create team", url: "/teams/new"}
                }
            },
            FORMS: {
                name: "Forms",
                url: "/forms/browse",
                subRoutes: {
                    SEARCH_FORMS: {name: "Browse forms", url: "/forms/browse"},
                    NEW_FORM: {name: "Create new form", url: "/forms/new"}
                }
            },
            COMPONENTS: {
                name: "Components",
                url: "/components",
                subRoutes: {
                    TEXT_FIELD: {name: "Text field", url: "/components/text-field"},
                    TEXT_AREA: {name: "Large text field", url: "/components/text-area"},
                    SINGLE_CHECKBOX: {name: "Single checkbox", url: "/components/checkbox/single"},
                    GROUP_CHECKBOX: {name: "Grouped checkbox", url: "/components/checkbox/group"},
                    GROUP_RADIO: {name: "Grouped radio", url: "/components/radio-group"},
                }
            },
        },
        FORM: {
            VIEW_FORM: {
                name: "View form",
                url: "/forms/view/:id"
            },
            FILL_FORM: {
                name: "Fill form",
                url: "/forms/respond/:id"
            },
            SEND_FORM: {
                name: "Send form to users",
                url: "/forms/send/:id"
            }
        },
        AUTHENTICATION: {
            COMPUTED_LOGIN: async function(): Promise<PageDetail> {
                return new Promise(resolve => {
                    const login = Pages.ROUTES.STATIC.LOGIN;
                    const logout = Pages.ROUTES.STATIC.LOGOUT;
                    AuthenticationUtils.isLoggedIn().then(authenticated => {
                        if (!authenticated) {
                            resolve({name: login.name, url: login.url});
                        } else {
                            AuthenticationUtils.getUser().then(user => {
                                resolve({name: `${logout.name} (${user.name})`, url: logout.url});
                            });
                        }
                    });
                });
            }
        },
        STATIC: {
            LOGIN: {
                name: "Login",
                url: "/account/login"
            },
            LOGOUT: {
                name: "Logout",
                url: "/account/logout"
            },
            EDIT_TEAM: {
                name: "Edit team",
                url: "/teams/edit/:id"
            }
        }
    }

    // Workaround to keep auto-completion working whilst enforcing type-safety of PageRouteStructure type.
    // If {PAGES} doesn't contain required data, this line will error.
    private static safetyCheck: PageRouteStructure = Pages.ROUTES;

    /**
     * Generate a list of PageDetailLinks for building the structure of the ROUTES variable.
     * The value should be kept in a computed method or stored on created().
     */
    public static async generatePageDetailLinks(): Promise<PageDetailLink[]> {
        const routes: PageDetailLink[] = [];
        for (const category of Object.values(Pages.ROUTES)) {
            for (const route of Object.values(category)) {
                let detail: PageDetail;
                if (typeof(route) === "function") {
                    detail = await route();
                } else {
                    detail = route;
                }
                routes.push(Pages.getChildPageDetailLink(detail));
            }
        }
        return routes;
    }

    /**
     * Retrieve a PageDetailLink related to a specific route.
     * @param detail
     * @param routes
     */
    public static getPageDetailLinkForRoute(detail: PageDetail, routes: PageDetailLink[]) {
        for (const route of routes) {
            if (route.findNestedChildRouteAsLink(detail)) {
                return route;
            }
        }
        return undefined;
    }

    /**
     * Build up a PageDetailLink and the children connected to the detail route.
     * @param detail        The IPageDetail of the route requested.
     * @param parentLink    Attach as the parent of the created PageDetailLink. Is not added as a child.
     * @private
     */
    private static getChildPageDetailLink(detail: PageDetail | (() => PageDetail), parentLink?: PageDetailLink): PageDetailLink {
        let safeDetail: PageDetail;
        if (typeof(detail) === "function") {
            safeDetail = detail();
        } else {
            safeDetail = detail;
        }
        const link = new PageDetailLink(safeDetail, parentLink);
        if (safeDetail.subRoutes !== undefined) {
            for (const subDetail of Object.values(safeDetail.subRoutes)) {
                link.addChild(this.getChildPageDetailLink(subDetail, link));
            }
        }
        return link;
    }

}