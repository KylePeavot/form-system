<template>
  <div >
    <Heading :level="2">{{ title }}</Heading>
    <p v-if="guidance.length > 0">{{ guidance }}</p>
    <div v-for="(radio, index) of value" :key="`${idPrefix}-${index}`">
      <div class="py-2">
        <input :id="`${idPrefix}-${index}`" class="checkbox" type="radio" :value="radio.label" v-model="selected">
        <label :for="`${idPrefix}-${index}`">{{radio.label}}</label>
      </div>
    </div>
  </div>
</template>

<script lang="ts">

import {Component, Model, Prop, Vue, Watch} from "vue-property-decorator";
import Heading from "@/components/core/Heading.vue";
import RadioValue from "@/models/form/RadioValue";
@Component({
  components: {Heading}
})
export default class RadioGroup extends Vue {

  private selected = "";

  @Prop({required: true})
  private idPrefix!: string;

  @Prop({required: true})
  private title!: string;

  @Prop({default: ""})
  private guidance!: string;

  @Model("input", {required: true})
  private value!: RadioValue[];

  @Watch("selected")
  selectUpdate(newValue: string) {
    this.value.map(radioValue => {

      console.log(radioValue,radioValue.label,newValue)
      radioValue.value = (radioValue.label == newValue);
    });
    console.log(newValue,this.value);
  }
}
</script>