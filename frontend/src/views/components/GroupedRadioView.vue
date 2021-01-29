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
import RadioQuestion from "../../components/core/radio/RadioQuestions.vue";
import RadioValue from "@/models/form/RadioValue";


@Component({
  components: {
    RadioGroup,
    RadioQuestion,
    BaseStyleLayout,
  }
})
export default class GroupedRadioView extends Vue {

  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.GROUP_RADIO;
  private radioValues = [
    new RadioValue("Yes",false),
    new RadioValue("No",false),
  ]

}

</script>