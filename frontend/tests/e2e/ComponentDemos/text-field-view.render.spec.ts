import App from '../pageobjects/app.page';

describe("Text field demo page", () => {

  before(() => {
    App.open();
    browser.url("/components/text-field");
  })

  it("has the correct title with the correct level", () => {
    expect($('h1=Text field demo')).toBeDefined();
  })
  it("has 2 text field questions with the correct text", () => {
    // const question1ContainerElement = $('h2=First name').$('..');

    // expect(question1ContainerElement).toBeDefined();
    expect($('h2=First name')).toBeDefined();
    expect($('p=Please enter your first name')).toBeDefined();
    expect($('input')).toBeDefined();

    // const question2ContainerElement = $('h3=Surname').$('..');

    // expect(question2ContainerElement).toBeDefined();
    expect($('h3=Surname')).toBeDefined();
    expect($('p=Please enter your surname')).toBeDefined();
    expect($('input')).toBeDefined();

  })

})