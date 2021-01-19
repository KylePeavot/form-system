<template>
  <div>
    <Heading :level="2">{{ title }}</Heading>
    <p v-if="guidance.length > 0">{{ guidance }}</p>
    <div v-for="(radio, index) of value" :key="`${idPrefix}-${index}`">
      <Radio :id="`${idPrefix}-${index}`" :radio-value="radio"/>
    </div>
  </div>
</template>

<script lang="ts">

import {Component, Model, Prop, Vue} from "vue-property-decorator";
import Radio from "@/components/core/radio/Radio.vue";
import Heading from "@/components/core/Heading.vue";
import RadioValue from "@/models/form/RadioValue";
@Component({
  components: {Heading, Radio}
})
export default class RadioGroup extends Vue {

  @Prop({required: true})
  private idPrefix!: string;

  @Prop({required: true})
  private title!: string;

  @Prop({default: ""})
  private guidance!: string;

  @Model("input", {required: true})
  private value!: RadioValue[];

}

</script>