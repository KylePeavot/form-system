import { shallowMount } from "@vue/test-utils";
import RadioQuestion from "@/components/core/radio/RadioQuestions.vue";
import RadioValue from "@/models/form/RadioValue";
import Radio from "@/components/core/radio/Radio.vue";

describe("Radio question component", () => {

    it("Updates model value", () => {

        const radioValue = new RadioValue("Label", false);
        const wrapper = shallowMount(RadioQuestion, {
            stubs: {Radio},
            propsData: {
                id: "id",
                title: "QTitle",
                guidance: "GText",
                level: 2,
                value: radioValue
            }
        });

        expect((wrapper.find(`#${wrapper.props("id")}`).element as HTMLInputElement).checked).toBeFalsy();
        expect(radioValue.value).toBeFalsy();

        expect(wrapper.text()).toContain(wrapper.props("title"));
        expect(wrapper.text()).toContain(wrapper.props("guidance"));
        wrapper.find(`#${wrapper.props("id")}`).trigger("click");
        expect((wrapper.find(`#${wrapper.props("id")}`).element as HTMLInputElement).checked).toBeTruthy();
        expect(radioValue.value).toBeTruthy();

    })

});