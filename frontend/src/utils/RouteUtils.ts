import {CorePage} from "../models/navigation/CorePage";

export default class RouteUtils {

    /**
     * Used to get the URL path for core pages.
     * @param page
     */
    public static getNavigationRoute(page: CorePage): string {
        switch (page) {
            case CorePage.DASHBOARD: return "/";
            case CorePage.COMPONENTS: return "/components";
            case CorePage.FORMS: return "/forms";
            default:
                throw new Error(`Route not yet implemented for page [${page}]`);
        }
    }

}