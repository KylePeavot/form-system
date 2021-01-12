import { shallowMount } from "@vue/test-utils";
import CheckboxQuestion from "@/components/core/checkbox/CheckboxQuestion.vue";
import CheckboxValue from "@/models/form/CheckboxValue";
import Checkbox from "@/components/core/checkbox/Checkbox.vue";

describe("Checkbox question component", () => {

  it("Updates model value", () => {

    const checkboxValue = new CheckboxValue("Label", false);
    const wrapper = shallowMount(CheckboxQuestion, {
      stubs: {Checkbox},
      propsData: {
        id: "id",
        title: "QTitle",
        guidance: "GText",
        level: 2,
        value: checkboxValue
      }
    });

    expect((wrapper.find(`#${wrapper.props("id")}`).element as HTMLInputElement).checked).toBeFalsy();
    expect(checkboxValue.value).toBeFalsy();

    expect(wrapper.text()).toContain(wrapper.props("title"));
    expect(wrapper.text()).toContain(wrapper.props("guidance"));
    wrapper.find(`#${wrapper.props("id")}`).trigger("click");
    expect((wrapper.find(`#${wrapper.props("id")}`).element as HTMLInputElement).checked).toBeTruthy();
    expect(checkboxValue.value).toBeTruthy();

  })

});