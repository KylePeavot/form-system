<template>
  <div>
    <BaseStyleLayout title="Grouped radio demo" :selected-page="page">
      <RadioGroup title="Radio group" guidance="Radio selection" id-prefix="rg" v-model="radioValues"/>
    </BaseStyleLayout>
  </div>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import Pages from "@/models/navigation/Pages";
import BaseStyleLayout from "@/components/layout/BaseStyleLayout.vue";
import RadioGroup from "../../components/core/radio/RadioGroup.vue";
import SelectionValue from "@/models/form/SelectionValue";


@Component({
  components: {
    RadioGroup,
    BaseStyleLayout,
  }
})
export default class GroupedRadioView extends Vue {

  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.GROUP_RADIO;
  private radioValues = [
    new SelectionValue("Yes",false),
    new SelectionValue("No",false)
  ];
}

</script>