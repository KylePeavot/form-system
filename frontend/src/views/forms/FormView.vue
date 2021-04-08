<template>
  <div>
    <FormStyleLayout :selected-page="page" :title="title">
      <template v-if="form !== null">
        <Heading :level="1">{{ form.name }}</Heading>
        <div v-for="(component, index) in form.componentList" :key="component.order">
          <component :is="component.componentType" v-bind="component.componentProps" :level="2" :id="index" :id-prefix="index" :current-form-display-mode="currentFormDisplayMode" @props-updated="updateComponentProps($event, component)"/>
        </div>
        <router-link :to="getDashboardUrl()">
          <button v-if="currentFormDisplayMode.isFill" class="button my-5 p-2 rounded hover:bg-gray-100" @click="submitFormResponseAsDraft">Save as draft</button>
          <button v-if="currentFormDisplayMode.isFill" class="button--primary my-5 p-2 rounded" @click="submitFormResponse">Submit form</button>
        </router-link>
      </template>
      <template v-else>
        <Heading :level="1">Awaiting form</Heading>
      </template>
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
import FormComponent from "@/models/form/FormComponent";

@Component({
  components: {FormStyleLayout, Heading, TextField, TextArea, CheckboxQuestion, CheckboxGroup, RadioGroup}
})
export default class FormView extends Vue {
  private page: string | undefined;

  private title: string | undefined;

  private form: Form | null = null;

  private currentFormDisplayMode: CurrentFormDisplayMode = new CurrentFormDisplayMode(false, false, false);

  @Prop({required: true})
  private mode!: FormDisplayModeEnum;

  @Prop({required: true})
  private id!: number;

  created() {
    if (this.mode === FormDisplayModeEnum.FORM_FILLING) {
      this.title = Pages.ROUTES.FORM.FILL_FORM.name;
      this.page = Pages.ROUTES.FORM.FILL_FORM.url.replace(":id", this.id.toString());

      WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/form-response/${this.id}`, true)
      .then(async value => await value.json())
      .then(value => value as FormInterface)
      .then(value => this.form = Form.mapFormInterfaceToForm(value));

      this.currentFormDisplayMode = new CurrentFormDisplayMode(false, false, true);

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

  updateComponentProps(newProp: any, component: FormComponent) {
    const unsafeComponent = component as any;
    Object.keys(newProp).forEach(key => {
      unsafeComponent.componentProps[key] = newProp[key];
      unsafeComponent.componentProps = (() => unsafeComponent.componentProps)();
    })
  }

  submitFormResponseAsDraft() {
    WebRequestUtils.post(`${WebRequestUtils.BASE_URL}/api/form-response/save-draft/${this.id}`, this.form!);
  }

  submitFormResponse() {
    WebRequestUtils.post(`${WebRequestUtils.BASE_URL}/api/form-response/submit`, {responseId: this.id, form: this.form});
  }

  getDashboardUrl(): string {
    return Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD.url;
  }
}
</script>