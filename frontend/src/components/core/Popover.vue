<script>

import { createPopper } from "@popperjs/core";

export default {
  name: 'PopperItem',
  props: {
    popperConfig: { //just the config for popper placement and other popper parameters
      type: Object,
      default: () => ({
        placement: 'right',
        modifiers: [
          {
            name: "offset",
            options: {
              offset: [0, 10]
            }
          }
        ],
      })
    }
  },
  data() {
    return {
      showMenu: false,
      popperButtonElement: null, //the thing you want the user to click to show the menu
      popperMenuElement: null //the menu
    }
  },
  mounted() {
    this.popperButtonElement = this.$el.querySelector("[name=popper-button]"); //needs improving but atm finds the element with an attribute called popper-button
    this.popperMenuElement = this.$el.querySelector("[name=popper-menu]");
  },
  methods: {
    openPopper(event) {
      this.showMenu = true; // show the menu
      if (this.popper === undefined) { //if there is no popper, create one
        createPopper(this.popperButtonElement, this.popperMenuElement, this.popperConfig);
      }
    },
    async closePopper(event) {
      await this.sleep(100);
      this.showMenu = false;
    },
    togglePopper(event) {
      if (this.showMenu === false) {
        this.openPopper(event);
      } else if (this.showMenu === true) {
        this.closePopper(event);
      }
    },
    sleep(ms) {
      return new Promise(resolve => setTimeout(resolve, ms));
    }
  },
  render() {
    return this.$scopedSlots.default({
      showMenu: this.showMenu,
      togglePopper: this.togglePopper,
      closePopper: this.closePopper
    });
  }
}

</script>