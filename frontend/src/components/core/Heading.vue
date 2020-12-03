<template>
  <div>
    <component :is="`h${level}`" :class="textSize">
      <slot></slot>
    </component>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";

@Component
export default class Heading extends Vue {

  @Prop({default: 2})
  private level!: number;

  get textSize(): string {
    switch (this.level) {
      case 1: return "text-3xl font-semibold";
      case 2: return "text-2xl";
      case 3: return "text-xl";
      case 4: return "text-lg";
      default: return "text-base";
    }
  }

}

</script>