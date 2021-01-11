import {mount, Wrapper} from '@vue/test-utils'
import TextField from "@/components/core/TextField.vue";
import TextValue from "@/models/form/TextValue";

describe('TextField.vue', () => {
  let title: string;
  let guidance: string;
  let textValue: TextValue;
  let wrapper: Wrapper<TextField>;

  beforeEach(() => {
    title = "Test question title";
    guidance = "Test question guidance";
    textValue = new TextValue("");

    wrapper = mount(TextField, {
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
    expect(wrapper.findAll("input").length).toEqual(1);

  })
  it('text is updated when text entered in the input field',  () => {
    const testInput = "Test input";

    expect(textValue.value).toEqual("");
    expect((wrapper.find("input").element as HTMLInputElement).value).toEqual("");

    wrapper.find("input").setValue(testInput);

    expect(textValue.value).toEqual(testInput);
    expect((wrapper.find("input").element as HTMLInputElement).value).toMatch(testInput);
  })
})