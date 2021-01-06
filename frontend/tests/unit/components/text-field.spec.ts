import { shallowMount } from '@vue/test-utils'
// import TextFieldView from "@/views/components/TextFieldView.vue";
import TextFieldView from "../../../src/views/components/TextFieldView.vue";

describe('TextFieldView.vue', () => {
  it('Displays 2 text fields and can read the data from them', () => {
    const msg = 'new message'
    const wrapper = shallowMount(TextFieldView);



    expect(wrapper.text()).toMatch(msg)
  })
})
