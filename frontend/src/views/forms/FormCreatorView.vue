<template>
  <div>
    <TwoColumnStyleLayout title="Create a new form" :selected-page="page">
      <template v-slot:sidebar>
        <SidebarGroup title="Components">
          <button name="addTextField" type="button" class="sidebar-group__item-button" @click="addComponentToList">Text field</button>
          <button name="addTextArea" type="button" class="sidebar-group__item-button" @click="addComponentToList">Large text field</button>
          <button name="addCheckboxSingle" type="button" class="sidebar-group__item-button" @click="addComponentToList">Single checkbox</button>
          <button name="addCheckboxGroup" type="button" class="sidebar-group__item-button" @click="addComponentToList">Multiple checkboxes</button>
          <button name="addRadioGroup" type="button" class="sidebar-group__item-button" @click="addComponentToList">Radio group</button>
        </SidebarGroup>
      </template>
      <slot>
        <div :v-if="components !== undefined" v-for="component in components" :key="component.order" >
          <component :is="component.componentType" v-bind="component.componentProps" @delete-component="removeFromLayout(component)" @props-updated="updateComponentProps($event, component)"/>
        </div>
      </slot>
      <button class="button button--primary" @click="saveForm">Save Form</button>
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
import Form from "@/models/form/Form";
import WebRequestUtils from "@/utils/WebRequestUtils";

@Component({
  components: {
    RadioGroup,
    CheckboxGroup,
    CheckboxQuestion, SidebarGroup, TextArea, TextField, TwoColumnStyleLayout}
})
export default class FormCreatorView extends Vue {
  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.FORMS.subRoutes.NEW_FORM;
  private components: FormCreationComponent[] = new Array<FormCreationComponent>();
  private nextComponentId = 1;

  saveForm(){
    const form = new Form("Form",this.components);
    WebRequestUtils.post(`${WebRequestUtils.BASE_URL}/api/form/save`,form);
  }

  addComponentToList(event: Event) {
    const userAction = (event.target as Element).getAttribute("name");

    let componentType = "";
    let componentProps: any = {};

    for (let i = 0; i < this.components.length; i++) {
      this.components[i].order = (i + 1) * 100;
    }

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
          id: 'cq-' + this.nextComponentId,
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
          idPrefix: 'cg-' + this.nextComponentId,
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
          idPrefix: 'rg-' + this.nextComponentId,
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

  removeFromLayout(componentToDelete: FormCreationComponent) {
    this.components = this.components.filter(item => {
      return item !== componentToDelete;
    })
  }

  updateComponentProps(newProp: any, component: FormCreationComponent) {
    const unsafeComponent = component as any;
    Object.keys(newProp).forEach(key => {
      unsafeComponent.componentProps[key] = newProp[key];
      unsafeComponent.componentProps = (() => unsafeComponent.componentProps)();
    })
  }

}
</script>
