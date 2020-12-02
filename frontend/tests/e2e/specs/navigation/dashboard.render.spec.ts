import App from '../../pageobjects/app.page'
import {CorePage} from "../../../../src/models/navigation/CorePage";
import RouteUtils from "../../../../src/utils/RouteUtils";
import EnumUtils from "../../../../src/utils/EnumUtils";

describe('Navigation rendering', () => {

    before(() => {
        App.open();
    })

    it('Should contain image', () => {
        expect($("//*[@id='university-of-kent-logo']")).toBeDefined();
    })
    it('Should have all core pages displayed', () => {
        for (const value of Object.entries(CorePage)) {
            const page: CorePage = EnumUtils.getEnumFromKey(CorePage, value[0]);
            const route = RouteUtils.getNavigationRoute(page);
            expect($(`//a[@href='${route}' and text()='${page}']`).isExisting()).toBeTruthy();
        }
    })
})