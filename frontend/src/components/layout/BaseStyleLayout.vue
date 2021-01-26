<template>
  <div>
    <Navbar :selected-page="selectedPage" component-class="flex flex-0 items-start justify-start"/>
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
import Heading from "@/components/core/Heading.vue";
import NavbarItem from "@/components/layout/Navigation/NavbarItem.vue";
import Navbar from "@/components/layout/Navigation/Navbar.vue";
import PageDetail from "@/models/navigation/PageDetail";
import Pages from "@/models/navigation/Pages";

@Component({
  components: {Navbar, NavbarItem, Heading}
})
export default class BaseStyleLayout extends Vue {

  @Prop({default: ""})
  private title!: string;

  @Prop({required: true})
  private selectedPage!: PageDetail;

}
</script>