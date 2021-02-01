<template>
  <div>
    <TwoColumnStyleLayout title="Create a new form" :selected-page="page">
      <template v-slot:sidebar>
        <SidebarMenu title="Components">
          <button name="addTextField" type="button" class="sidebar-menu__item-button" @click="addTextField">Text field</button>
          <button name="addTextArea" type="button" class="sidebar-menu__item-button" @click="addTextArea">Large text field</button>
        </SidebarMenu>
        <SidebarMenu title="Test section">
          <p class="sidebar-menu__item-link">Login</p>
          <p class="sidebar-menu__item-link">Account settings</p>
        </SidebarMenu>
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
import SidebarMenu from "@/components/layout/Navigation/SidebarMenu.vue";

@Component({
  components: {SidebarMenu, TextArea, TextField, TwoColumnStyleLayout}
})
export default class FormCreatorView extends Vue {
  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.FORMS.subRoutes.NEW_FORM;
  private components: FormCreationComponent[] = new Array<FormCreationComponent>();

  addTextField() {
    this.components.push(new FormCreationComponent(
      'TextField',
        {
            level: 2,
            title: 'Title',
            guidance: 'Guidance',
            textValue: new TextValue("")
        },
        this.components.length * 100
    ));
  }

  addTextArea() {
    this.components.push(new FormCreationComponent(
      'TextArea',
        {
            level: 2,
            title: 'Title',
            guidance: 'Guidance',
            textValue: new TextValue("")
        },
        this.components.length * 100
    ));
  }

}
</script>