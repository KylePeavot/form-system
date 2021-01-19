import { shallowMount } from "@vue/test-utils";
import Radio from "@/components/core/radio/Radio.vue";
import RadioValue from "@/models/form/RadioValue";

describe("Radio component", () => {

    it("Updates model value", () => {

        const radioValue = new RadioValue("Label", false);
        const wrapper = shallowMount(Radio, {
            propsData: {
                id: "id",
                radioValue: radioValue
            },
        });

        expect((wrapper.find(`#${wrapper.props("id")}`).element as HTMLInputElement).checked).toBeFalsy();
        expect(radioValue.value).toBeFalsy();
        wrapper.find("input").trigger("click");
        expect((wrapper.find(`#${wrapper.props("id")}`).element as HTMLInputElement).checked).toBeTruthy();
        expect(radioValue.value).toBeTruthy();

    })

});