<template>
  <p class="mr-2 inline-block">
    <i class="ph-check-bold" v-if="value"></i>
    <i class="ph-x-bold" v-if="!value"></i>
  </p>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";

@Component
export default class CheckOrCrossIcon extends Vue {

  @Prop({required: true})
  private value!: boolean;

}

</script>