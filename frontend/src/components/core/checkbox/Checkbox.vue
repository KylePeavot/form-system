<template>
  <div class="checkbox__container">
    <input :id="id" type="checkbox" class="checkbox__item" v-model="checkboxValue.value">
    <label :for="id">{{ checkboxValue.label }}</label>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import SelectionValue from "@/models/form/SelectionValue";

@Component
export default class Checkbox extends Vue {

  @Prop({required: true})
  private id!: string;

  @Prop({required: true})
  private checkboxValue!: SelectionValue;

}

</script>