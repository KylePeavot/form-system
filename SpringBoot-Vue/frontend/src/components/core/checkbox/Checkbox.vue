<template>
  <div class="checkbox__container">
    <input :id="id" type="checkbox" :class="{'checkbox__item':true, 'bg-gray-100':(!currentFormDisplayMode.isFill)}" :disabled="!currentFormDisplayMode.isFill" v-model="selectionValue.value" @input="updateProps">
    <EditableComponent edit-component-css="checkbox__label-edit" :value="selectionValue.label" @finish-editing="updateLabel($event)" :current-form-display-mode="currentFormDisplayMode">
      <label class="checkbox__label" :for="id">{{ selectionValue.label }}</label>
    </EditableComponent>
    <button v-if="canRemove && currentFormDisplayMode.isEdit" class="hidden-button ph-trash" @click="$emit('deleteCheckbox')"/>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import SelectionValue from "@/models/form/SelectionValue";
import EditableComponent from "@/components/core/componentExtras/EditableComponent.vue";
import BaseQuestionProps from "@/models/form/BaseQuestionProps";
import CurrentFormDisplayMode from "@/models/form/CurrentFormDisplayMode";

@Component({
  components: {EditableComponent}
})
export default class Checkbox extends Vue {

  @Prop({required: true})
  private id!: string;

  @Prop({required: true})
  private selectionValue!: SelectionValue;

  @Prop({required: true, default: false})
  private canRemove!: boolean;

  @Prop({required: true})
  private currentFormDisplayMode!: CurrentFormDisplayMode;

  // created() {
  //   this.selectionValue = SelectionValue.mapSelectionValueInterfaceToSelectionValue(this.selectionValue);
  // }

  updateLabel(newLabel: string) {
    this.selectionValue.label = newLabel;
  }

  updateProps() {
    this.$emit('props-updated', this.$props);
  }
}

</script>