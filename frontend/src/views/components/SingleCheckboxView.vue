<template>
  <div>
    <BaseStyleLayout title="Single checkbox demo" :selected-page="page">
      <CheckboxQuestion id="cq" title="Example checkbox" guidance="Tick to confirm" :level="2" v-model="inputValue" :current-form-display-mode="currentFormDisplayMode"/>
      <hr class="my-2"/>
      <p id="show_value">{{inputValue.value}}</p>
    </BaseStyleLayout>
  </div>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import CheckboxQuestion from "../../components/core/checkbox/CheckboxQuestion.vue";
import SelectionValue from "../../models/form/SelectionValue";
import Pages from "@/models/navigation/Pages";
import BaseStyleLayout from "@/components/layout/BaseStyleLayout.vue";
import CurrentFormDisplayMode from "@/models/form/CurrentFormDisplayMode";

@Component({
  components: {
    CheckboxQuestion,
    BaseStyleLayout,
  }
})
export default class SingleCheckboxView extends Vue {

  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.SINGLE_CHECKBOX;
  private inputValue = new SelectionValue("I accept the terms and conditions", true);

  private currentFormDisplayMode = new CurrentFormDisplayMode(false, false, true);

}

</script>