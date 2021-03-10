<!--
  The purpose of this screen is to display a bunch of given components in a readonly/edit/formfilling mode
  //TODO FS-52 remove edit from above (or just the whole comment) or see if edit mode is possible too
-->
<template>
  <div>
    <FormStyleLayout :selected-page="page" :title="title">
      <Heading :level="1">{{ form.name }}</Heading>
      <div v-for="(component, index) in form.componentList" :key="component.order">
        <component :is="component.componentType" v-bind="component.componentProps" :level="2" :id="index" :id-prefix="index" :current-form-display-mode="currentFormDisplayMode"/>
      </div>
    </FormStyleLayout>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import FormStyleLayout from "@/components/layout/FormStyleLayout.vue";
import Heading from "@/components/core/componentExtras/Heading.vue";
import {FormDisplayModeEnum} from "@/models/form/FormDisplayModeEnum";
import Pages from "@/models/navigation/Pages";
import Form from "@/models/form/Form";
import WebRequestUtils from "@/utils/WebRequestUtils";
import FormInterface from "@/models/form/interfaces/FormInterface";
import TextField from "@/components/core/TextField.vue";
import TextArea from "@/components/core/TextArea.vue";
import CheckboxQuestion from "@/components/core/checkbox/CheckboxQuestion.vue";
import CheckboxGroup from "@/components/core/checkbox/CheckboxGroup.vue";
import RadioGroup from "@/components/core/radio/RadioGroup.vue";
import CurrentFormDisplayMode from "@/models/form/CurrentFormDisplayMode";

@Component({
  components: {FormStyleLayout, Heading, TextField, TextArea, CheckboxQuestion, CheckboxGroup, RadioGroup}
})
export default class FormView extends Vue {
  private page: string | undefined;

  private title: string | undefined;

  private form = new Form("Awaiting form", []);

  private currentFormDisplayMode: CurrentFormDisplayMode = new CurrentFormDisplayMode(false, false, false);

  @Prop({required: true})
  private mode!: FormDisplayModeEnum;

  @Prop({required: true})
  private id!: number;

  created() {
    if (this.mode === FormDisplayModeEnum.FORM_FILLING) {
      this.title = Pages.ROUTES.FORM.FILL_FORM.name;
      this.page = Pages.ROUTES.FORM.FILL_FORM.url.replace(":id", this.id.toString());
    } else if (this.mode === FormDisplayModeEnum.READ_ONLY) {
      this.title = Pages.ROUTES.FORM.VIEW_FORM.name;
      this.page = Pages.ROUTES.FORM.VIEW_FORM.url.replace(":id", this.id.toString());

      WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/form/get/${this.id}`, true)
        .then(async value => await value.json())
        .then(value => value as FormInterface)
        .then(value => this.form = Form.mapFormInterfaceToForm(value));

      this.currentFormDisplayMode = new CurrentFormDisplayMode(true, false, false);
    }
  }
}
</script>