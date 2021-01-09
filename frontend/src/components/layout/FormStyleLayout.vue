<template>
  <div>
    <Navbar :selected-page="selectedPage" component-class="flex flex-0 items-start justify-start"/>
    <header class="bg-blue-700 text-white h-64 border-b-5 border-blue-500" v-if="title.length > 0">
      <div class="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
        <Heading :level="1">{{title}}</Heading>
      </div>
    </header>
    <main>
      <div class="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8 -mt-40 bg-white lg:rounded-lg shadow min-h-screen-half">
        <slot>

        </slot>
      </div>
    </main>
  </div>
</template>

<script lang="ts">
import {Component, Prop, Vue} from "vue-property-decorator";
import Heading from "@/components/core/Heading.vue";
import Pages from "@/models/navigation/Pages";
import IPageDetail from "@/models/navigation/IPageDetail";
import Navbar from "@/components/layout/Navigation/Navbar.vue";

@Component({
  components: {Navbar, Heading}
})
export default class SingleNavigationLayout extends Vue {

  @Prop({default: ""})
  private title!: string;

  @Prop({required: true})
  private selectedPage!: IPageDetail;

}
</script>