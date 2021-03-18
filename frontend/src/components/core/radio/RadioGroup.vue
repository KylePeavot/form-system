<template>
  <div name="radio-group-container">
    <BaseQuestion :base-question-props="baseQuestionProps" @finish-editing="updateProps($event)" :current-form-display-mode="currentFormDisplayMode">
      <button class="popover-menu__item">Move</button>
      <button class="popover-menu__item popover-menu__item--danger" @click="deleteComponent">Delete</button>
    </BaseQuestion>
    <div v-for="(radio, index) of selectionValues" :key="`${idPrefix}-${index}`">
      <div class="radio__container">
        <input :id="`${idPrefix}-${index}`" :class="{'radio__item':true, 'bg-gray-100':(!currentFormDisplayMode.isFill)}" type="radio" :disabled="!currentFormDisplayMode.isFill" :value="radio.label" v-model="selected" @input="updateProps">
        <EditableComponent edit-component-css="radio__label-edit" :value="radio.label" @finish-editing="updateLabel($event, radio)" :current-form-display-mode="currentFormDisplayMode">
          <label :for="`${idPrefix}-${index}`">{{radio.label}}</label>
        </EditableComponent>
        <button v-if="currentFormDisplayMode.isEdit" type="button" class="hidden-button ph-trash" @click="deleteRadioOption(radio)" />
      </div>
    </div>
    <button v-if="currentFormDisplayMode.isEdit" type="button" class="text-blue-500" @click="addNewRadioOption">+ Add new radio option</button>
  </div>
</template>

<script lang="ts">

import {Component, Model, Prop, Vue, Watch} from "vue-property-decorator";
import Heading from "@/components/core/componentExtras/Heading.vue";
import SelectionValue from "@/models/form/SelectionValue";
import BaseQuestion from "@/components/core/BaseQuestion.vue";
import BaseQuestionProps from "@/models/form/BaseQuestionProps";
import SelectionValueInterface from "@/models/form/interfaces/SelectionValueInterface";
import CurrentFormDisplayMode from "@/models/form/CurrentFormDisplayMode";
import EditableComponent from "@/components/core/componentExtras/EditableComponent.vue";

@Component({
  components: {EditableComponent, BaseQuestion, Heading}
})
export default class RadioGroup extends Vue {

  private selected = "";

  @Prop({default: 2})
  private level!: number;

  @Prop({required: true})
  private idPrefix!: string;

  @Prop({required: true})
  private title!: string;

  @Prop({default: ""})
  private guidance!: string;

  @Prop({required: true})
  private currentFormDisplayMode!: CurrentFormDisplayMode;

  @Model("input", {required: true})
  private selectionValues!: SelectionValue[];

  private baseQuestionProps: BaseQuestionProps | undefined;

  created() {
    this.baseQuestionProps = new BaseQuestionProps(this.level, this.title, this.guidance);
    this.selectionValues.filter(value => {
      return value.value;
    }).forEach(value => {
      this.selected = value.label;
    });
  }

  @Watch("selected")
  selectUpdate(newValue: string) {
    this.selectionValues.map(SelectionValue => {
      SelectionValue.value = (SelectionValue.label == newValue);
    });
  }

  updateProps() {
    this.$emit('props-updated', this.$props);
  }

  updateLabel(newLabel: string, radio: SelectionValue) {
    this.value.map(value => {
      if (value === radio) {
        value.label = newLabel;
      }
    })
  }

  addNewRadioOption() {
    this.value.push(new SelectionValue("Add a response here", false));
  }

  deleteRadioOption(radioToDelete: SelectionValue) {
    const newValues = this.value.filter(value => {
      return radioToDelete !== value;
    })
    this.$emit('propsUpdated', {value: newValues});
  }

  deleteComponent() {
    this.$emit("delete-component");
  }
}
</script>