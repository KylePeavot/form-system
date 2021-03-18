<template>
  <div name="radio-group-container">
    <BaseQuestion :base-question-props="baseQuestionProps">
      <button class="popover-menu__item">Move</button>
      <button class="popover-menu__item popover-menu__item--danger" @click="deleteComponent">Delete</button>
    </BaseQuestion>
    <div v-for="(radio, index) of value" :key="`${idPrefix}-${index}`">
      <div class="radio__container">
        <input :id="`${idPrefix}-${index}`" class="radio__item" type="radio" :value="radio.label" v-model="selected">
        <EditableComponent edit-component-css="radio__label-edit" :value="radio.label" @finish-editing="updateLabel($event, radio)">
          <label :for="`${idPrefix}-${index}`">{{radio.label}}</label>
        </EditableComponent>
        <button type="button" class="hidden-button ph-trash" @click="deleteRadioOption(radio)" />
      </div>
    </div>
    <button type="button" class="text-blue-500" @click="addNewRadioOption">+ Add new radio option</button>
  </div>
</template>

<script lang="ts">

import {Component, Model, Prop, Vue, Watch} from "vue-property-decorator";
import Heading from "@/components/core/componentExtras/Heading.vue";
import SelectionValue from "@/models/form/SelectionValue";
import BaseQuestion from "@/components/core/BaseQuestion.vue";
import BaseQuestionProps from "@/models/form/BaseQuestionProps";
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

  @Model("input", {required: true})
  private value!: SelectionValue[];

  private baseQuestionProps: BaseQuestionProps | undefined;

  created() {
    this.baseQuestionProps = new BaseQuestionProps(this.level, this.title, this.guidance);
  }

  @Watch("selected")
  selectUpdate(newValue: string) {
    this.value.map(SelectionValue => {
      SelectionValue.value = (SelectionValue.label == newValue);
    });
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