<template>
  <div class="sidebar-group__container">
    <Heading class="sidebar-group__title" :level="2">{{ title }}</Heading>
    <div class="sidebar-group__item-container">
      <slot></slot>
    </div>
  </div>
</template>

<script lang="ts">


import {Component, Prop, Vue} from "vue-property-decorator";
import Heading from "@/components/core/Heading.vue";
@Component({
  components: {Heading}
})
export default class SidebarGroup extends Vue {
  @Prop({required: true})
  private title!: string;
}
</script>