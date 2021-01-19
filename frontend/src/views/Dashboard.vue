<template>
  <div>
    <BaseStyleLayout title="Page title" :selected-page="page">
      <CheckboxQuestion id="checkboxQuestion1" :level="2" title="Checkbox question" guidance="Guidance" v-model="inputValue"/>
      <br/>
      <CheckboxGroup title="Checkbox group" guidance="Select all that apply" id-prefix="cg" v-model="groupValues"/>
      <br/>
      <RadioGroup title="Radio group" guidance="Radio selection" v-model="radioValues"/>
    </BaseStyleLayout>


  </div>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import Heading from "../components/core/Heading.vue";
import BaseStyleLayout from "../components/layout/BaseStyleLayout.vue";
import {CorePage} from "@/models/navigation/CorePage";
import WebRequestUtils from "@/utils/WebRequestUtils";
import CheckboxQuestion from "@/components/core/checkbox/CheckboxQuestion.vue";
import Checkbox from "@/components/core/checkbox/Checkbox.vue";
import CheckboxValue from "@/models/form/CheckboxValue";
import CheckboxGroup from "@/components/core/checkbox/CheckboxGroup.vue";
import RadioQuestion from "@/components/core/radio/RadioQuestions.vue";
import Radio from "@/components/core/radio/Radio.vue";
import RadioValue from "@/models/form/RadioValue";
import RadioGroup from "@/components/core/radio/RadioGroup.vue";
import RadioQuestions from "@/components/core/radio/RadioQuestions.vue";


@Component({
  components: {
    RadioQuestions,
    CheckboxGroup,
    Checkbox,
    CheckboxQuestion,
    Radio,
    RadioGroup,
    RadioQuestion,
    BaseStyleLayout,
    Heading
  }
})
export default class Dashboard extends Vue {

  private text = 'Test';
  private page = CorePage.DASHBOARD;
  private inputValue = new CheckboxValue("Checkbox question", true);
  private groupValues = [
      new CheckboxValue("Option 1", true),
      new CheckboxValue("Option 2", true),
      new CheckboxValue("Option 3", false)
  ]
  private radioValues = [
      new RadioValue("Yes",false),
      new RadioValue("No",false),
      new RadioValue("Maybe",false)
  ]

  mounted() {
    WebRequestUtils.get(`${process.env.VUE_APP_API_URL!}/api`)
        .then(async value => {
          this.text = await value.text();
        });
  }

}

</script>