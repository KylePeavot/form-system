<template>
  <div>
    <Navbar :selected-page="selectedPage" component-class="flex flex-0 items-start justify-start"/>
    <template v-if="flashes.length > 0">
      <br/>
      <div v-for="flash of flashes" :key="`${flash.message}-${flash.title}`">
        <div class="p-4 max-w-7xl mx-auto rounded-md shadow-md my-2 bg-" :class="[{'bg-red-700 text-white' : flash.type === 'error'}, {'bg-green-700 text-white' : flash.type === 'success'}]">
          <button class="float-right" @click="removeFlash(flash)"><i class="ph-x-circle cursor-pointer"></i></button>
          <Heading :level="3">{{ flash.title }}</Heading>
          <p v-if="flash.message.length > 0">{{ flash.message }}</p>
        </div>
      </div>
    </template>
    <header class="border-b-5 border-blue-500" v-if="title.length > 0">
      <div class="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
        <Heading :level="1">{{title}}</Heading>
      </div>
    </header>
    <main>
      <div class="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8 bg-white lg:rounded-lg shadow min-h-screen-half">
        <slot>

        </slot>
      </div>
    </main>
  </div>
</template>

<script lang="ts">
import {Component, Prop, Vue} from "vue-property-decorator";
import Heading from "@/components/core/componentExtras/Heading.vue";
import NavbarItem from "@/components/layout/Navigation/NavbarItem.vue";
import Navbar from "@/components/layout/Navigation/Navbar.vue";
import PageDetail from "@/models/navigation/PageDetail";
import Flash from "@/models/generic/Flash";
import FlashUtils, {FlashType} from "@/utils/FlashUtils";

@Component({
  components: {Navbar, NavbarItem, Heading}
})
export default class BaseStyleLayout extends Vue {

  @Prop({default: ""})
  private title!: string;

  @Prop({required: true})
  private selectedPage!: PageDetail;

  private flashes: Flash[] = [];

  removeFlash(flash: Flash) {
    this.flashes = this.flashes.filter(value => value !== flash);
  }

  mounted() {
    this.flashes = FlashUtils.getFlashes();
  }

}
</script>