<template>
  <div>
    <BaseStyleLayout title="Grouped checkbox demo" :selected-page="page">
      <CheckboxGroup id-prefix="cg-col" title="Example checkbox group" guidance="Tick all that apply" :level="2" v-model="inputValues"/>
      <hr class="my-2"/>
      <div v-for="(colour, index) of colours" :key="`value-${colour}`">
        <p v-if="inputValues[index].value" :id="`likes-${colour}`">You like the colour {{ colour }}</p>
      </div>
    </BaseStyleLayout>
  </div>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import CheckboxQuestion from "../../components/core/checkbox/CheckboxQuestion.vue";
import SelectionValue from "../../models/form/SelectionValue";
import Pages from "@/models/navigation/Pages";
import BaseStyleLayout from "@/components/layout/BaseStyleLayout.vue";
import CheckboxGroup from "@/components/core/checkbox/CheckboxGroup.vue";

@Component({
  components: {
    CheckboxGroup,
    CheckboxQuestion,
    BaseStyleLayout,
  }
})
export default class GroupedCheckboxView extends Vue {

  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.SINGLE_CHECKBOX;

  private colours = ["red", "green", "blue"]

  private inputValues = this.colours.map(colour => new SelectionValue(`I like the colour ${colour}`, false));

}

</script>