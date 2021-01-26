import PageDetail, {SubrouteType} from "@/models/navigation/PageDetail";
import AuthenticationUtils from "../../utils/AuthenticationUtils";
import PageDetailLink from "../../models/navigation/PageDetailLink";

type PageRouteStructure = {
    [area: string]: {
        [routeKey: string]: (() => PageDetail) | PageDetail;
    };
};

export default class Pages {

    public static readonly ROUTES = {
        SHOWN_IN_NAVBAR: {
            DASHBOARD: {
                name: "Dashboard",
                url: "/"
            },
            ACCOUNT: {
                name: "Account",
                url: "/account"
            },
            FORMS: {
                name: "Forms",
                url: "/forms",
                subRoutes: {
                    MY_FORMS: {name: "Browse forms", url: "/forms/browse"},
                    NEW_FORM: {name: "Create new form", url: "/forms/new"}
                }
            },
            COMPONENTS: {
                name: "Components",
                url: "/components",
                subRoutes: {
                    TEXT_FIELD: {name: "Text field", url: "/components/text-field"},
                    TEXT_AREA: {name: "Large text field", url: "/components/text-area"}
                }
            },
        },
        AUTHENTICATION: {
            COMPUTED_LOGIN: function(): PageDetail {
                const login = Pages.ROUTES.STATIC.LOGIN;
                const logout = Pages.ROUTES.STATIC.LOGOUT;
                if (AuthenticationUtils.isLoggedIn()) {
                    return {name: logout.name, url: logout.url}
                } else {
                    return {name: login.name, url: login.url}
                }
            }
        },
        STATIC: {
            LOGIN: {
                name: "Login",
                url: "/account/login"
            },
            LOGOUT: {
                name: "Logout",
                url: "/"
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
    public static generatePageDetailLinks(): PageDetailLink[] {
        const routes: PageDetailLink[] = [];
        for (const category of Object.values(Pages.ROUTES)) {
            for (const route of Object.values(category)) {
                let detail: PageDetail;
                if (typeof(route) === "function") {
                    detail = route();
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