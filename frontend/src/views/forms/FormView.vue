<!--
  The purpose of this screen is to display a bunch of given components in a readonly/edit/formfilling mode
  //TODO FS-52 remove edit from above (or just the whole comment) or see if edit mode is possible too
-->
<template>
  <div>
    <FormStyleLayout :selected-page="page" :title="title">
      <div :v-if="components !== undefined" v-for="component in components" :key="component.order" >
        <component :is="component.componentType" v-bind="component.componentProps"/>
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

@Component({
  components: {FormStyleLayout}
})
export default class FormView extends Vue {
  private page: string | undefined;

  private title: string | undefined;

  private components: FormComponent[] | undefined;

  @Prop({required: true})
  private mode!: FormDisplayMode;

  @Prop({required: true})
  private id!: number;

  created() {
    if (this.mode === FormDisplayMode.FORM_FILLING) {
      this.title = Pages.ROUTES.FORM.FILL_FORM.name;
      this.page = Pages.ROUTES.FORM.FILL_FORM.url.replace(":id", this.id.toString());
    } else if (this.mode === FormDisplayMode.READ_ONLY) {
      this.title = Pages.ROUTES.FORM.VIEW_FORM.name;
      this.page = Pages.ROUTES.FORM.VIEW_FORM.url.replace(":id", this.id.toString());
    }


  }

}
</script>