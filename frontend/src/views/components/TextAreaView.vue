<template>
  <div>
    <BaseStyleLayout title="Large text field demo" :selected-page="page">
      <TextArea :level="2" title="Tell us about yourself" guidance="100 words max" :text-value="textValueQ1" :current-form-display-mode="currentFormDisplayMode"/>
      <TextArea :level="2" title="Why do you want to change to this module?" :text-value="textValueQ2" :current-form-display-mode="currentFormDisplayMode"/>
    </BaseStyleLayout>
  </div>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import Heading from "../../components/core/componentExtras/Heading.vue";
import BaseStyleLayout from "../../components/layout/BaseStyleLayout.vue";
import Pages from "../../models/navigation/Pages";
import TextArea from "@/components/core/TextArea.vue";
import TextValue from "@/models/form/TextValue";
import CurrentFormDisplayMode from "@/models/form/CurrentFormDisplayMode";

@Component({
    components: {
      BaseStyleLayout,
      Heading,
      TextArea
    }
  })

  export default class TextAreaView extends Vue {
    private page = Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.TEXT_AREA;

    private textValueQ1 = new TextValue("");
    private textValueQ2 = new TextValue("");

    private currentFormDisplayMode = new CurrentFormDisplayMode(false, true, true);
  }
</script>