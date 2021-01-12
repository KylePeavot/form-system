<template>
  <div class="py-2">
    <input :id="id" type="checkbox" class="form-checkbox rounded border-2 border-gray-400 h-7 w-7 text-blue-600 mr-2" v-model="checkboxValue.value">
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