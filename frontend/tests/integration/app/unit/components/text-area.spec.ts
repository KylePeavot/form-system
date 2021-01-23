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
  });

  afterEach(() => {
    wrapper.destroy();
  });

  it('contains the given question name, guidance, and input field', () => {
    assert.equal(wrapper.find("h2").text(), title); //title
    assert.equal(wrapper.find("p").text(), guidance); //guidance
    assert.isDefined(wrapper.find("textarea"));
  });

  it('text is updated when text entered in the input field',  () => {
    const testInput = "Test input";

    assert.equal(textValue.value, "");
    assert.equal((wrapper.find("textarea").element as HTMLInputElement).value, "");

    wrapper.find("textarea").setValue(testInput);

    assert.equal(textValue.value, testInput);
    assert.equal((wrapper.find("textarea").element as HTMLInputElement).value, testInput);
  });

})