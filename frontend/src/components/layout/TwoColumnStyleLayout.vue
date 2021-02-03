<template>
  <div class="h-screen flex flex-col">
    <div class="flex">
      <div class="w-64 bg-blue-700"></div>
      <div class="w-full flex flex-col flex-1 overflow-y-hidden">
        <Navbar :selected-page="selectedPage" component-class="flex flex-0 items-start justify-start"/>
      </div>
    </div>
    <main class="flex flex-1 overflow-y-hidden">
      <aside class="relative bg-gray-200 w-64 shadow-md flex flex-col flex-0 overflow-y-auto border-r-2 border-gray-300">
        <nav>
          <br/>
          <slot name="sidebar"></slot>
        </nav>
      </aside>

      <div class="max-w-7xl mx-auto w-full flex flex-col px-4 py-3 flex-1 overflow-y-hidden">
        <div class="flex flex-col bg-white lg:rounded-lg shadow min-h-3/4 overflow-y-hidden">
          <div class="py-2 px-4">
            <Heading :level="1">{{title}}</Heading>
          </div>
          <div name="pageContent" class="overflow-auto my-4 px-4">
            <slot></slot>
          </div>
        </div>
      </div>

    </main>
  </div>
</template>

<script lang="ts">
import {Component, Prop, Vue} from "vue-property-decorator";
import Heading from "@/components/core/Heading.vue";
import NavbarItem from "@/components/layout/Navigation/NavbarItem.vue";
import Navbar from "@/components/layout/Navigation/Navbar.vue";
import PageDetail from "@/models/navigation/PageDetail";

@Component({
  components: {Navbar, NavbarItem, Heading}
})
export default class TwoColumnStyleLayout extends Vue {

  @Prop({default: ""})
  private title!: string;

  @Prop({required: true})
  private selectedPage!: PageDetail;

}
</script>