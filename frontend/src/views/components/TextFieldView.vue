<template>
  <div>
    <BaseStyleLayout title="Text field demo" :selected-page="page">
      <TextField :level="2" title="Full name" guidance="Please enter your first name, surname, and any middle names" @change="textFieldChanged($event)"/>
      <TextField :level="2" title="Mother's maiden name" guidance="Please enter your mother's maiden name for... security reasons" @change="textFieldChanged($event)"/>
    </BaseStyleLayout>
  </div>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import Heading from "../../components/core/Heading.vue";
import BaseStyleLayout from "../../components/layout/BaseStyleLayout.vue";
import Pages from "../../models/navigation/Pages";
import TextField from "@/components/core/TextField.vue";
import TextValue from "@/models/form/TextValue";


@Component({
    components: {
      BaseStyleLayout,
      Heading,
      TextField
    }
  })

  export default class TextFieldView extends Vue {
    private page = Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.TEXT_FIELD;

    private textFieldValue = "";

    textFieldChanged(value: string) {
      this.textFieldValue = value;
    }

  }

</script>