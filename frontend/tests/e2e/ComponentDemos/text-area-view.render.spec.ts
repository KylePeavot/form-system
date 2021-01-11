import App from '../pageobjects/app.page';

describe("Text area demo page", () => {

  before(() => {
    App.open();
    browser.url("/components/text-area");
  })

  it("has the correct title with the correct level", () => {
    expect($('h1=Large text field demo')).toBeDefined();
  })
  it("has 2 text area questions with the correct text", () => {
    const question1ContainerElement = $('h2=Tell us about yourself').$('..');

    expect(question1ContainerElement).toBeDefined();
    expect(question1ContainerElement.$('h2=Tell us about yourself')).toBeDefined();
    expect(question1ContainerElement.$('p=100 words max')).toBeDefined();
    expect(question1ContainerElement.$('textarea')).toBeDefined();

    const question2ContainerElement = $('h2=Why do you want to change to this module?').$('..');

    expect(question2ContainerElement).toBeDefined();
    expect(question2ContainerElement.$('h2=Why do you want to change to this module?')).toBeDefined();
    expect(question2ContainerElement.$('p').isExisting()).toBeFalsy();
    expect(question2ContainerElement.$('textarea')).toBeDefined();

  })

})