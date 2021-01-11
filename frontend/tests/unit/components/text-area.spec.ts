import {mount, Wrapper} from '@vue/test-utils'
import TextArea from "@/components/core/TextArea.vue";
import TextValue from "@/models/form/TextValue";

describe('TextArea.vue', () => {
  let title: string;
  let guidance: string;
  let textValue: TextValue;
  let wrapper: Wrapper<TextArea>;

  beforeEach(() => {
    title = "Test question title";
    guidance = "Test question guidance";
    textValue = new TextValue("");

    wrapper = mount(TextArea, {
      propsData: {
        level: 2,
        title: title,
        guidance: guidance,
        textValue: textValue
      }
    })
  })
  afterEach(() => {
    wrapper.destroy();
  })
  it('contains the given question name, guidance, and input field', () => {
    expect(wrapper.findAll("h2").at(0).text()).toMatch(title); //title
    expect(wrapper.findAll("p").at(0).text()).toMatch(guidance); //guidance
    expect(wrapper.findAll("textarea").length).toEqual(1);

  })
  it('text is updated when text entered in the input field',  () => {
    const testInput = "Test input";

    expect(textValue.value).toEqual("");
    expect((wrapper.find("textarea").element as HTMLInputElement).value).toEqual("");

    wrapper.find("textarea").setValue(testInput);

    expect(textValue.value).toEqual(testInput);
    expect((wrapper.find("textarea").element as HTMLInputElement).value).toMatch(testInput);
  })
})