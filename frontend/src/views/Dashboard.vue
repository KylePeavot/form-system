<template>
  <div>
    <BaseStyleLayout title="Page title" :selected-page="page">
      <CheckboxQuestion id="checkboxQuestion1" :level="2" title="Checkbox question" guidance="Guidance" v-model="inputValue"/>
      <br/>
      <CheckboxGroup title="Checkbox group" guidance="Select all that apply" id-prefix="cg" v-model="groupValues"/>
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
import WebRequestUtils from "../utils/WebRequestUtils";
import Pages from "../models/navigation/Pages";

@Component({
  components: {
    CheckboxGroup,
    Checkbox,
    CheckboxQuestion,
    BaseStyleLayout,
    Heading
  }
})
export default class Dashboard extends Vue {

  private text = 'Test';
  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD;
  private inputValue = new CheckboxValue("Checkbox question", true);
  private groupValues = [
      new CheckboxValue("I want to die", true),
      new CheckboxValue("This is a cry for help", true),
      new CheckboxValue("Something else", false)
  ]

  mounted() {
    WebRequestUtils.get(`${process.env.VUE_APP_API_URL!}/api`)
        .then(async value => {
          this.text = await value.text();
        });
  }

}

</script>