<template>
  <div class="checkbox__container">
    <input :id="id" type="checkbox" class="checkbox__item" v-model="checkboxValue.value" @finish-editing="$emit('props-updated', $event)">
    <EditableComponent edit-component-css="checkbox__label-edit" :value="checkboxValue.label" @finish-editing="updateLabel">
      <label class="checkbox__label" :for="id">{{ checkboxValue.label }}</label>
    </EditableComponent>
    <button v-if="isDeletable" class="hidden-button ph-trash" @click="$emit('deleteCheckbox')"/>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import SelectionValue from "@/models/form/SelectionValue";
import EditableComponent from "@/components/core/componentExtras/EditableComponent.vue";
import BaseQuestionProps from "@/models/form/BaseQuestionProps";

@Component({
  components: {EditableComponent}
})
export default class Checkbox extends Vue {

  @Prop({required: true})
  private id!: string;

  @Prop({required: true})
  private checkboxValue!: SelectionValue;

  @Prop({required: true, default: false})
  private isDeletable!: boolean;

  updateLabel(newLabel: string) {
    this.checkboxValue.label = newLabel;
  }

  updateProps(baseQuestionProps: BaseQuestionProps) {
    this.$emit('props-updated', {title: baseQuestionProps.title, guidance: baseQuestionProps.guidance});
  }
}

</script>