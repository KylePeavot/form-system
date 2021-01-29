<template>
  <div>
    <BaseQuestion :level="level" :title="title" :guidance="guidance"/>
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
import BaseQuestion from "@/components/core/BaseQuestion.vue";
@Component({
  components: {BaseQuestion, Heading, Checkbox}
})
export default class CheckboxGroup extends Vue {

  @Prop({required: true})
  private idPrefix!: string;

  @Prop({required: true})
  private title!: string;

  @Prop({default: ""})
  private guidance!: string;

  @Prop({default: 2})
  private level!: number;

  @Model("input", {required: true})
  private value!: CheckboxValue[];

}

</script>