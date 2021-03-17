<template>
  <div name="radio-group-container">
    <BaseQuestion :base-question-props="baseQuestionProps" @finish-editing="updateProps($event)" :current-form-display-mode="currentFormDisplayMode">
      <button class="popover-menu__item">Move</button>
      <button class="popover-menu__item popover-menu__item--danger" @click="deleteComponent">Delete</button>
    </BaseQuestion>
    <div v-for="(radio, index) of selectionValues" :key="`${idPrefix}-${index}`">
      <div class="radio__container">
        <input :id="`${idPrefix}-${index}`" :class="{'radio__item':true, 'bg-gray-100':(!currentFormDisplayMode.isFill)}" type="radio" :disabled="!currentFormDisplayMode.isFill" :value="radio.label" v-model="selected" @input="updateProps">
        <label :for="`${idPrefix}-${index}`">{{radio.label}}</label>
      </div>
    </div>
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

@Component({
  components: {BaseQuestion, Heading}
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
    this.selectionValues = this.selectionValues.map(value => SelectionValue.mapSelectionValueInterfaceToSelectionValue(value));
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

  deleteComponent() {
    this.$emit("delete-component");
  }
}
</script>