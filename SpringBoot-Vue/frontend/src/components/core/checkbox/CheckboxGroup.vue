<template>
  <div name="checkbox-group-container" class="pb-5">
    <BaseQuestion :base-question-props="baseQuestionProps" @finish-editing="updateBaseQuestionProps($event)" @move-component="moveComponent($event)" @delete-component="deleteComponent" :current-form-display-mode="currentFormDisplayMode" />
    <div v-for="(checkbox, index) of selectionValues" :key="`${idPrefix}-${index}`">
      <Checkbox :id="`${idPrefix}-${index}`" :selection-value="checkbox" :can-remove="true" @props-updated="updateProps" @deleteCheckbox="deleteCheckbox(checkbox)" :current-form-display-mode="currentFormDisplayMode"/>
    </div>
    <button v-if="currentFormDisplayMode.isEdit" type="button" class="text-blue-500" @click="addNewCheckbox">+ Add new checkbox</button>
  </div>
</template>

<script lang="ts">

import {Component, Model, Prop, Vue} from "vue-property-decorator";
import Checkbox from "@/components/core/checkbox/Checkbox.vue";
import Heading from "@/components/core/componentExtras/Heading.vue";
import SelectionValue from "@/models/form/SelectionValue";
import BaseQuestion from "@/components/core/BaseQuestion.vue";
import BaseQuestionProps from "@/models/form/BaseQuestionProps";
import SelectionValueInterface from "@/models/form/interfaces/SelectionValueInterface";
import CurrentFormDisplayMode from "@/models/form/CurrentFormDisplayMode";
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

  @Prop({required: true})
  private currentFormDisplayMode!: CurrentFormDisplayMode;

  @Model("input", {required: true})
  private selectionValues!: SelectionValue[];

  private baseQuestionProps: BaseQuestionProps | undefined;

  created() {
    this.baseQuestionProps = new BaseQuestionProps(this.level, this.title, this.guidance);
  }

  addNewCheckbox() {
    this.selectionValues.push(new SelectionValue("Add a response here", false));
  }

  deleteCheckbox(checkboxToDelete: SelectionValue) {
    const newValues = this.selectionValues.filter(value => {
      return checkboxToDelete !== value;
    })
    this.$emit('props-updated', {selectionValues: newValues});
  }

  updateProps() {
    this.$emit('props-updated', this.$props);
  }

  updateBaseQuestionProps(newProps: BaseQuestionProps) {
    this.title = newProps.title;
    this.guidance = newProps.guidance
    this.updateProps();
  }

  moveComponent(direction: string) {
    this.$emit('move-component', direction);
  }

  deleteComponent() {
    this.$emit("delete-component");
  }
}

</script>