type SubrouteType = {[key: string]: IPageDetail};

export default interface IPageDetail {

    name: string;
    url: string;
    subRoutes?: SubrouteType;

}

export { SubrouteType }