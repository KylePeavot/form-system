<template>
  <div>
    <BaseStyleLayout title="Text field demo" :selected-page="page">
      <TextField :level="2" title="Full name" guidance="Please enter your first name, surname, and any middle names" :text-value="textValueQ1" :current-form-display-mode="currentFormDisplayMode"/>
      <TextField :level="3" title="Mother's maiden name" guidance="Please enter your mother's maiden name for... security reasons" :text-value="textValueQ2" :current-form-display-mode="currentFormDisplayMode"/>
    </BaseStyleLayout>
  </div>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import Heading from "../../components/core/componentExtras/Heading.vue";
import BaseStyleLayout from "../../components/layout/BaseStyleLayout.vue";
import Pages from "../../models/navigation/Pages";
import TextField from "@/components/core/TextField.vue";
import TextValue from "@/models/form/TextValue";
import CurrentFormDisplayMode from "@/models/form/CurrentFormDisplayMode";


@Component({
    components: {
      BaseStyleLayout,
      Heading,
      TextField
    }
  })

  export default class TextFieldView extends Vue {
    private page = Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.TEXT_FIELD;

    private textValueQ1 = new TextValue("");
    private textValueQ2 = new TextValue("");

    private currentFormDisplayMode = new CurrentFormDisplayMode(false, true, true);

  }

</script>