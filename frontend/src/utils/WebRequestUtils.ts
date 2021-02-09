import AuthenticationUtils from "@/utils/AuthenticationUtils";

export default class WebRequestUtils {

    public static readonly BASE_URL = process.env.VUE_APP_API_URL!;

    /**
     * Wrapper around fetch() to provide default values to prevent CORS errors.
     * @param input Identical input parameter to fetch().
     * @param authenticated Should the request send an authenticated JWT?
     * @param init Identical init parameter to fetch().
     */
    public static async get(input: RequestInfo, authenticated?: boolean, init?: RequestInit): Promise<Response> {
        init = init ?? {};
        init.mode = init.mode || "cors";
        init.headers = init.headers || {};
        // @ts-expect-error
        // Ignore warning because Access-Control-Allow-Origin is not a recognised property.
        init.headers["Access-Control-Allow-Origin"] = "*";
        return WebRequestUtils.getWithoutHeaders(input, authenticated, init);
    }

    public static async getWithoutHeaders(input: RequestInfo, authenticated?: boolean, init?: RequestInit): Promise<Response> {
        init = init ?? {};
        init.mode = init.mode || "cors";
        init.headers = init.headers || {};
        if (authenticated) {
            if (!await AuthenticationUtils.isLoggedIn()) {
                throw new Error("User is not authenticated");
            }
            // @ts-expect-error
            init.headers["X-Once-Token"] = await AuthenticationUtils.getContext().getTokenSilently();
        }
        return fetch(input, init);
    }

}