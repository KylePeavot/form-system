import App from '../../pageobjects/app.page'

// TODO
// describe('Navigation rendering', () => {
//
//     before(() => {
//         App.open();
//     })
//
//     it('Should contain image', () => {
//         expect($("//*[@id='university-of-kent-logo']")).toBeDefined();
//     })
//     it('Should have all core pages displayed', () => {
//         for (const value of Object.entries(CorePage)) {
//             const page: CorePage = EnumUtils.getEnumFromKey(CorePage, value[0]);
//             expect($(`//a[@href='${route}' and text()='${page}']`).isExisting()).toBeTruthy();
//         }
//     })
// })