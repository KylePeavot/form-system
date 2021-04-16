type SubrouteType = {[key: string]: PageDetail};

export default interface PageDetail {

    name: string;
    url: string;
    subRoutes?: SubrouteType;

}

export { SubrouteType }