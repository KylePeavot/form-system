import IPageDetail, {SubrouteType} from "@/models/navigation/IPageDetail";
import AuthenticationUtils from "@/utils/AuthenticationUtils";

type PageRouteStructure = {
    [area: string]: {
        [routeKey: string]: (() => IPageDetail) | IPageDetail;
    };
};

export default class Pages {

    public static readonly ROUTES = {
        SHOWN_IN_NAVBAR: {
            DASHBOARD: {
                name: "Dashboard",
                url: "/",
                subRoutes: {
                    TEST: {name: "Test", url: "/"}
                }
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
                url: "/components"
            },
        },
        AUTHENTICATION: {
            LOGIN_OR_LOGOUT: () => {

                // Allow both routes to be accessible regardless of computed route.
                let subroutes: SubrouteType = {};
                subroutes = {
                    LOGIN: {name: "Login", url: "/account/login", subRoutes: subroutes},
                    LOGOUT: {name: "Logout", url: "/account/logout", subRoutes: subroutes}
                }

                if (AuthenticationUtils.isLoggedIn()) {
                    return {name: "Logout", url: "/account/logout", subRoutes: subroutes} as IPageDetail;
                }
                return {name: "Login", url: "/account/login", subRoutes: subroutes} as IPageDetail;
            }
        }
    }

    // Workaround to keep auto-completion working whilst enforcing type-safety of PageRouteStructure type.
    // If {PAGES} doesn't contain required data, this line will error.
    private static safetyCheck: PageRouteStructure = Pages.ROUTES;

}