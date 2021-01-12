import { shallowMount } from "@vue/test-utils";
import Checkbox from "@/components/core/checkbox/Checkbox.vue";
import CheckboxValue from "@/models/form/CheckboxValue";

describe("Checkbox component", () => {

  it("Updates model value", () => {

    const checkboxValue = new CheckboxValue("Label", false);
    const wrapper = shallowMount(Checkbox, {
      propsData: {
        id: "id",
        checkboxValue: checkboxValue
      },
    });

    expect((wrapper.find(`#${wrapper.props("id")}`).element as HTMLInputElement).checked).toBeFalsy();
    expect(checkboxValue.value).toBeFalsy();
    wrapper.find("input").trigger("click");
    expect((wrapper.find(`#${wrapper.props("id")}`).element as HTMLInputElement).checked).toBeTruthy();
    expect(checkboxValue.value).toBeTruthy();

  })

});