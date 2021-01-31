<template>
  <div >
    <BaseQuestion :level="level" :title="title" :guidance="guidance"/>
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
import Heading from "@/components/core/Heading.vue";
import SelectionValue from "@/models/form/SelectionValue";
import BaseQuestion from "@/components/core/BaseQuestion.vue";

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

  @Watch("selected")
  selectUpdate(newValue: string) {
    this.value.map(SelectionValue => {
      console.log(SelectionValue,SelectionValue.label,newValue)
      SelectionValue.value = (SelectionValue.label == newValue);
    });
    console.log(newValue,this.value);
  }
}
</script>