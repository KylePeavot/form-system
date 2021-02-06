<template>
  <div name="radio-group-container">
    <BaseQuestion :base-question-props="baseQuestionProps">
      <button class="popover-menu__item">Move</button>
      <button class="popover-menu__item--danger" @click="deleteComponent">Delete</button>
    </BaseQuestion>
    <div v-for="(radio, index) of value" :key="`${idPrefix}-${index}`">
      <div class="radio__container">
        <input :id="`${idPrefix}-${index}`" class="radio__item" type="radio" :value="radio.label" v-model="selected">
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

  deleteComponent() {
    this.$emit("delete-component");
  }
}
</script>