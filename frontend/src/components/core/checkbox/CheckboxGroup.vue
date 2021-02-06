<template>
  <div name="checkbox-group-container">
    <BaseQuestion :base-question-props="baseQuestionProps">
      <button class="popover-menu__item">Move</button>
      <button class="popover-menu__item popover-menu__item--danger" @click="deleteComponent">Delete</button>
    </BaseQuestion>
    <div v-for="(checkbox, index) of value" :key="`${idPrefix}-${index}`">
      <Checkbox :id="`${idPrefix}-${index}`" :checkbox-value="checkbox"/>
    </div>
  </div>
</template>

<script lang="ts">

import {Component, Model, Prop, Vue} from "vue-property-decorator";
import Checkbox from "@/components/core/checkbox/Checkbox.vue";
import Heading from "@/components/core/componentExtras/Heading.vue";
import SelectionValue from "@/models/form/SelectionValue";
import BaseQuestion from "@/components/core/BaseQuestion.vue";
import BaseQuestionProps from "@/models/form/BaseQuestionProps";
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
  private value!: SelectionValue[];

  private baseQuestionProps: BaseQuestionProps | undefined;

  created() {
    this.baseQuestionProps = new BaseQuestionProps(this.level, this.title, this.guidance);
  }

  deleteComponent() {
    this.$emit("delete-component");
  }
}

</script>