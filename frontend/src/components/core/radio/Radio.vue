<template>
  <div class="py-2">
    <input :id="id" type="radio"  class="border-2 border-gray-400 h-7 w-7 text-blue-600 mr-2"  v-model="radioValue.value">
    <label :for="id">{{ radioValue.label }}</label>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import RadioValue from "@/models/form/RadioValue";

@Component
export default class Radio extends Vue {

  @Prop({required: true})
  private id!: string;

  @Prop({required: true})
  private radioValue!: RadioValue;

}

</script>