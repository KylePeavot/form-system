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
    });
  });

  afterEach(() => {
    wrapper.destroy();
  });

  it('contains the given question name, guidance, and input field', () => {
   assert.equal(wrapper.find("h2").text(), title); //title
   assert.equal(wrapper.find("p").text(), guidance); //guidance
   assert.isDefined(wrapper.find("input"));
  });

  it('text is updated when text entered in the input field',  () => {
    const testInput = "Test input";

    assert.equal(textValue.value, "");
    assert.equal((wrapper.find("input").element as HTMLInputElement).value, "");

    wrapper.find("input").setValue(testInput);

    assert.equal(textValue.value, testInput);
    assert.equal((wrapper.find("input").element as HTMLInputElement).value, testInput);
  })
})