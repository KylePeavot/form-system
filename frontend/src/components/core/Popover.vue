<template>
  <div class="popover-menu">
    <button name="popper-button" class="focus:outline-none" @click="updatePopper">
      <strong><i class="ph-dots-three-bold text-xl"></i></strong>
    </button>
    <div name="popper-menu" class="popover-menu__container">
      <slot></slot>
    </div>
  </div>
</template>
<script lang="ts">

import { createPopper } from "@popperjs/core";
import {Component, Prop, Vue} from "vue-property-decorator";

@Component
export default class Popover extends Vue {
  @Prop({default: () => {
    return {
      placement: 'right',
      modifiers: [
        {
          name: "offset",
          options: {
            offset: [0, 10]
          }
        }
      ]
    }
  }})
  private popperConfig: any | undefined;
  private popper: any | undefined;


  private popperButtonElement: Element | undefined;
  private popperMenuElement: HTMLElement | undefined;

  private showMenu = false;

  mounted() {
    this.popperButtonElement = this.$el.querySelector("[name=popper-button]") as Element;
    this.popperMenuElement = this.$el.querySelector("[name=popper-menu]") as HTMLElement;
    this.popper = createPopper(this.popperButtonElement as Element, this.popperMenuElement as HTMLElement, this.popperConfig) as any;
  }

  async closePopper() {
    // sleep needed so that the browser has time to deal with button presses before closing the popper due to the @focusout event
    await this.sleep(100);
    this.showMenu = false;
    this.popper = null;
  }

  sleep(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }
}

</script>