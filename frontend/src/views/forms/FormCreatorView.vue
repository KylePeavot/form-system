<template>
  <div>
    <TwoColumnStyleLayout title="Create a new form" :selected-page="page">
      <template v-slot:sidebar>
        <SidebarGrloup title="Components">
          <button name="addTextField" type="button" class="sidebar-group__item-button" @click="addComponentToList">Text field</button>
          <button name="addTextArea" type="button" class="sidebar-group__item-button" @click="addComponentToList">Large text field</button>
          <button name="addCheckboxSingle" type="button" class="sidebar-group__item-button" @click="addComponentToList">Single checkbox</button>
          <button name="addCheckboxGroup" type="button" class="sidebar-group__item-button" @click="addComponentToList">Multiple checkboxes</button>
          <button name="addRadioGroup" type="button" class="sidebar-group__item-button" @click="addComponentToList">Radio group</button>
        </SidebarGrloup>
      </template>
      <slot>
        <div :v-if="components !== undefined" v-for="component in components" :key="component.order">
          <component :is="component.componentType" v-bind="component.componentProps" />
        </div>
      </slot>
    </TwoColumnStyleLayout>
  </div>
</template>
<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import Pages from "@/models/navigation/Pages";
import TwoColumnStyleLayout from "@/components/layout/TwoColumnStyleLayout.vue";
import TextField from "@/components/core/TextField.vue";
import TextValue from "@/models/form/TextValue";
import FormCreationComponent from "@/models/form/FormCreationComponent";
import TextArea from "@/components/core/TextArea.vue";
import SidebarGroup from "@/components/layout/Navigation/SidebarGroup.vue";
import CheckboxQuestion from "@/components/core/checkbox/CheckboxQuestion.vue";
import CheckboxGroup from "@/components/core/checkbox/CheckboxGroup.vue";
import SelectionValue from "@/models/form/SelectionValue";
import RadioGroup from "@/components/core/radio/RadioGroup.vue";

@Component({
  components: {
    RadioGroup,
    CheckboxGroup,
    CheckboxQuestion, SidebarGroup, TextArea, TextField, TwoColumnStyleLayout}
})
export default class FormCreatorView extends Vue {
  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.FORMS.subRoutes.NEW_FORM;
  private components: FormCreationComponent[] = new Array<FormCreationComponent>();

  addComponentToList(event: Event) {
    const userAction = (event.target as Element).getAttribute("name");

    let componentType = "";
    let componentProps = {};

    const order = (this.components.length + 1) * 100;

    switch (userAction) {
      case "addTextField": {
        componentType = "TextField";
        componentProps = {
          level: 2,
          title: 'Question title',
          guidance: 'Question guidance',
          textValue: new TextValue("")
        };
        break;
      }
      case "addTextArea": {
        componentType = "TextArea";
        componentProps = {
          level: 2,
          title: 'Question title',
          guidance: 'Question guidance',
          textValue: new TextValue("")
        };
        break;
      }
      case "addCheckboxSingle": {
        componentType = "CheckboxQuestion";
        componentProps = {
          id: 'cq',
          title: 'Question title',
          guidance: 'Question guidance',
          level: 2,
          value: new SelectionValue("Add a checkbox option here", false)
        };
        break;
      }
      case "addCheckboxGroup": {
        componentType = "CheckboxGroup";
        componentProps = {
          idPrefix: 'cg',
          title: 'Question title',
          guidance: 'Question guidance',
          level: 2,
          value: [
              new SelectionValue("Add a response here", false),
              new SelectionValue("Add a different response here", false)
          ]
        };
        break;
      }
      case "addRadioGroup": {
        componentType = "RadioGroup";
        componentProps = {
          level: 2,
          idPrefix: 'rg',
          title: 'Question title',
          guidance: 'Question guidance',
          value: [
            new SelectionValue("Add a response here", false),
            new SelectionValue("Add a different response here", false)
          ]
        };
        break;
      }
    }

    this.components.push(new FormCreationComponent(
        componentType,
        componentProps,
        order
    ));
  }
}
</script>