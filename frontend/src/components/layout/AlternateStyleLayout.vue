<template>
  <div class="h-screen flex flex-col">
    <Navbar :selected-page="selectedPage" class="flex flex-0"/>
    <main class="flex flex-1 overflow-y-hidden">
      <aside class="relative bg-gray-300 w-64 shadow-xl flex flex-col flex-0 overflow-y-auto">
        <nav>
          <br/>
          <p class="px-4 py-2 font-bold">
            Section
          </p>
          <div class="pl-2">
            <p class="px-4 py-1">
              Entry
            </p>
            <p class="px-4 py-1">
              Entry
            </p>
          </div>
        </nav>
      </aside>

      <div class="w-full flex flex-col px-4 py-3 flex-1 overflow-y-hidden">
        <div class="py-6 px-4 bg-white lg:rounded-lg shadow min-h-screen-half overflow-y-hidden">
          <Heading :level="1">{{title}}</Heading>
          <slot>

          </slot>
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
import IPageDetail from "@/models/navigation/IPageDetail";

@Component({
  components: {Navbar, NavbarItem, Heading}
})
export default class AlternateStyleLayout extends Vue {

  @Prop({default: ""})
  private title!: string;

  @Prop({required: true})
  private selectedPage!: IPageDetail;

}
</script>