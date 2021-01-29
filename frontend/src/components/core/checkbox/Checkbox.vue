<template>
  <div class="py-2">
    <input :id="id" type="checkbox" class="checkbox" v-model="checkboxValue.value">
    <label :for="id">{{ checkboxValue.label }}</label>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import CheckboxValue from "@/models/form/CheckboxValue";

@Component
export default class Checkbox extends Vue {

  @Prop({required: true})
  private id!: string;

  @Prop({required: true})
  private checkboxValue!: CheckboxValue;

}

</script>