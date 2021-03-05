<template>
  <div name="checkbox-group-container" class="pb-5">
    <BaseQuestion :base-question-props="baseQuestionProps" @finish-editing="updateProps($event)">
      <button class="popover-menu__item">Move</button>
      <button class="popover-menu__item popover-menu__item--danger" @click="deleteComponent">Delete</button>
    </BaseQuestion>
    <div v-for="(checkbox, index) of value" :key="`${idPrefix}-${index}`">
      <Checkbox :id="`${idPrefix}-${index}`" :checkbox-value="checkbox" :isDeletable="true" @deleteCheckbox="deleteCheckbox(checkbox)"/>
    </div>
    <button type="button" class="text-blue-500" @click="addNewCheckbox">+ Add new checkbox</button>
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

  addNewCheckbox() {
    this.value.push(new SelectionValue("Add a response here", false));
  }

  deleteCheckbox(checkboxToDelete: SelectionValue) {
    const newValues = this.value.filter(value => {
      return checkboxToDelete !== value;
    })
    this.$emit('props-updated', {value: newValues});
  }

  updateProps(baseQuestionProps: BaseQuestionProps) {
    this.$emit('props-updated', {title: baseQuestionProps.title, guidance: baseQuestionProps.guidance});
  }

  deleteComponent() {
    this.$emit("delete-component");
  }
}

</script>