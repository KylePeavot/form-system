export default class WebRequestUtils {

    /**
     * Wrapper around fetch() to provide default values to prevent CORS errors.
     * @param input Identical input parameter to fetch().
     * @param init Identical init parameter to fetch().
     */
    public static async get(input: RequestInfo, init?: RequestInit): Promise<Response> {
        if (init !== undefined) {
            init.mode = init.mode || "cors";
            init.headers = init.headers || {};
            // @ts-expect-error
            // Ignore warning because Access-Control-Allow-Origin is not a recognised property.
            init.headers["Access-Control-Allow-Origin"] = "*";
        }
        return fetch(input, init);
    }

}