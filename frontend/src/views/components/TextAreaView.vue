<template>
  <div>
    <AlternateStyleLayout title="Large text field demo" :selected-page="page">
      <TextArea :level="2" title="Tell us about yourself" guidance="100 words max" @change="textAreaChanged($event)"/>
      <TextArea :level="2" title="Why do you want to pass this module?" @change="textAreaChanged($event)"/>
    </AlternateStyleLayout>
  </div>
</template>

<script lang="ts">

  import {Component, Vue} from "vue-property-decorator";
  import Heading from "../../components/core/Heading.vue";
  import BaseStyleLayout from "../../components/layout/BaseStyleLayout.vue";
  import Pages from "../../models/navigation/Pages";
  import AlternateStyleLayout from "../../components/layout/AlternateStyleLayout.vue";
  import TextArea from "@/components/core/TextArea.vue";

  @Component({
    components: {
      AlternateStyleLayout,
      BaseStyleLayout,
      Heading,
      TextArea
    }
  })

  export default class TextAreaView extends Vue {

    private page = Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.TEXT_AREA;

    private textAreaValue = "";

    textAreaChanged(value: string) {
      this.textAreaValue = value;
    }

  }

</script>