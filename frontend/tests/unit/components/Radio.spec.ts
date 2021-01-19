import { shallowMount } from "@vue/test-utils";
import RadioValue from "@/models/form/RadioValue";
import RadioGroup from "@/components/core/radio/RadioGroup.vue";


describe("Radio component", () => {

    it("Updates model value", () => {

        const radioValue = new RadioValue("Label", false);
        const wrapper = shallowMount(RadioGroup, {
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