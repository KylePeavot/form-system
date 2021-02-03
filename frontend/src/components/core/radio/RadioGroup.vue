<template>
  <div >
    <BaseQuestion :level="level" :title="title" :guidance="guidance">
      <Popover>
        <button class="popover-menu__item">Edit</button>
        <button class="popover-menu__item">Move</button>
        <button class="popover-menu__item--danger" @click="deleteComponent">Delete</button>
      </Popover>
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
import Heading from "@/components/core/Heading.vue";
import SelectionValue from "@/models/form/SelectionValue";
import BaseQuestion from "@/components/core/BaseQuestion.vue";
import Popover from "@/components/core/Popover.vue";

@Component({
  components: {Popover, BaseQuestion, Heading}
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
      SelectionValue.value = (SelectionValue.label == newValue);
    });
  }

  deleteComponent() {
    this.$emit("delete-component");
  }
}
</script>