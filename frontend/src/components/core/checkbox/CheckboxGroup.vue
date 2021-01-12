<template>
  <div>
    <Heading :level="2">{{ title }}</Heading>
    <p v-if="guidance.length > 0">{{ guidance }}</p>
    <div v-for="(checkbox, index) of value" :key="`${idPrefix}-${index}`">
      <Checkbox :id="`${idPrefix}-${index}`" :checkbox-value="checkbox"/>
    </div>
  </div>
</template>

<script lang="ts">

import {Component, Model, Prop, Vue} from "vue-property-decorator";
import Checkbox from "@/components/core/checkbox/Checkbox.vue";
import Heading from "@/components/core/Heading.vue";
import CheckboxValue from "@/models/form/CheckboxValue";
@Component({
  components: {Heading, Checkbox}
})
export default class CheckboxGroup extends Vue {

  @Prop({required: true})
  private idPrefix!: string;

  @Prop({required: true})
  private title!: string;

  @Prop({default: ""})
  private guidance!: string;

  @Model("input", {required: true})
  private value!: CheckboxValue[];

}

</script>