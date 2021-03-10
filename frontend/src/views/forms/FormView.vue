<!--
  The purpose of this screen is to display a bunch of given components in a readonly/edit/formfilling mode
  //TODO FS-52 remove edit from above (or just the whole comment) or see if edit mode is possible too
-->
<template>
  <div>
    <FormStyleLayout :selected-page="page" :title="title">
      <h1>{{ form.name }}</h1>
      <div v-for="component in form.componentList" :key="component.order" >
        <p>{{ component.componentType }}</p>
        <p>{{ getComponentProps(component) }}</p>
        <!--        <component :is="component.componentType" v-bind="component.componentProps"/>-->
      </div>
    </FormStyleLayout>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import FormComponent from "@/models/form/FormComponent";
import FormStyleLayout from "@/components/layout/FormStyleLayout.vue";
import {FormDisplayMode} from "@/models/form/FormDisplayMode";
import Pages from "@/models/navigation/Pages";
import Form from "@/models/form/Form";
import WebRequestUtils from "@/utils/WebRequestUtils";
import FormInterface from "@/models/form/FormInterface";

@Component({
  components: {FormStyleLayout}
})
export default class FormView extends Vue {
  private page: string | undefined;

  private title: string | undefined;

  private form = new Form("Awaiting form", []);

  @Prop({required: true})
  private mode!: FormDisplayMode;

  @Prop({required: true})
  private id!: number;

  created() {
    // eslint-disable-next-line @typescript-eslint/no-this-alias
    const realThis = this;
    if (this.mode === FormDisplayMode.FORM_FILLING) {
      this.title = Pages.ROUTES.FORM.FILL_FORM.name;
      this.page = Pages.ROUTES.FORM.FILL_FORM.url.replace(":id", this.id.toString());
    } else if (this.mode === FormDisplayMode.READ_ONLY) {
      this.title = Pages.ROUTES.FORM.VIEW_FORM.name;
      this.page = Pages.ROUTES.FORM.VIEW_FORM.url.replace(":id", this.id.toString());
      WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/form/get/${this.id}`, true)
        .then(async value => await value.json())
        .then(value => value as FormInterface)
        .then(value => this.form = Form.mapFormInterfaceToForm(value));
    }
  }

  getComponentProps(component: FormComponent) {
    console.log(component);
    return JSON.stringify(component.componentProps);
  }
}
</script>