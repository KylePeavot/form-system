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
    // expect($('h2=First name')).toBeDefined();
    // expect($('p=Please enter your first name')).toBeDefined();
    // expect($('input')).toBeDefined();
    //
    // expect($('h3=Surname')).toBeDefined();
    // expect($('p=Please enter your surname')).toBeDefined();
    // expect($('input')).toBeDefined();

    let question1ContainerElement = $('h2=First name');
    question1ContainerElement = question1ContainerElement.$('..');


    expect(question1ContainerElement).toBeDefined();
    expect(question1ContainerElement.$('h2=First name')).toBeDefined();
    expect(question1ContainerElement.$('p=Please enter your first name')).toBeDefined();
    expect(question1ContainerElement.$('input')).toBeDefined();

    let question2ContainerElement = $('h3=Surname').$('..');
    // question2ContainerElement = question2ContainerElement.$('..');

    expect(question2ContainerElement).toBeDefined();
    expect(question2ContainerElement.$('h3=Surname')).toBeDefined();
    expect(question2ContainerElement.$('p=Please enter your surname')).toBeDefined();
    expect(question2ContainerElement.$('input')).toBeDefined();

  })

})