<template>
  <div>
    <TwoColumnStyleLayout :selected-page="page">
      <EditableComponent edit-component-css="question__title question__edit text-3xl font-semibold" v-model="formName" :current-form-display-mode="currentFormDisplayMode">
        <Heading class="question__title" :level=1>
          {{ this.formName }}
        </Heading>
      </EditableComponent>
      <template v-slot:sidebar>
        <SidebarGroup title="Components">
          <button name="addTextField" type="button" class="sidebar-group__item-button" @click="addComponentToList">Text field</button>
          <button name="addTextArea" type="button" class="sidebar-group__item-button" @click="addComponentToList">Large text field</button>
          <button name="addDateField" type="button" class="sidebar-group__item-button" @click="addComponentToList">Date field</button>
          <button name="addCheckboxSingle" type="button" class="sidebar-group__item-button" @click="addComponentToList">Single checkbox</button>
          <button name="addCheckboxGroup" type="button" class="sidebar-group__item-button" @click="addComponentToList">Multiple checkboxes</button>
          <button name="addRadioGroup" type="button" class="sidebar-group__item-button" @click="addComponentToList">Radio group</button>
        </SidebarGroup>
      </template>
      <slot v-if="teamsLoaded && availableTeams.length > 0">
        <Heading :level="2">Who will own this form?</Heading>
        <select class="rounded mt-2" v-model="selectedTeam">
          <option v-for="(team) in availableTeams" :key="`${team.teamName}-${team.teamId}`">
            {{ team.teamName }}
          </option>
        </select>
        <br/><br/>
        <hr/>
        <br/>
        <div :v-if="components !== undefined" v-for="(component, index) in components" :key="`${component.order}-${new Date().getUTCMilliseconds()}`">
          <component
            :is="component.componentType"
            v-bind="component.componentProps"
            :current-form-display-mode="currentFormDisplayMode" @delete-component="removeFromLayout(component)"
            @props-updated="updateComponentProps($event, component)"
            @move-component="moveComponent($event, index)"
          />
        </div>
        <br/>
        <router-link :to="redirectUrl">
          <button class="button button--primary" @click="saveForm">Save Form</button>
        </router-link>
      </slot>
      <slot v-else-if="teamsLoaded">
        <p>You don't have permissions to modify any forms within any team.</p>
        <p>You can <router-link :to="createTeamLink" class="text-blue-700 underline"><a>create your own team</a></router-link> or ask a team manager for access.</p>
      </slot>
      <slot v-else>
        <i class="animate-spin ph-arrow-clockwise"/><span>Getting suitable teams</span>
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
import FormComponent from "@/models/form/FormComponent";
import TextArea from "@/components/core/TextArea.vue";
import SidebarGroup from "@/components/layout/Navigation/SidebarGroup.vue";
import CheckboxQuestion from "@/components/core/checkbox/CheckboxQuestion.vue";
import CheckboxGroup from "@/components/core/checkbox/CheckboxGroup.vue";
import SelectionValue from "@/models/form/SelectionValue";
import RadioGroup from "@/components/core/radio/RadioGroup.vue";
import Form from "@/models/form/Form";
import WebRequestUtils from "@/utils/WebRequestUtils";
import CurrentFormDisplayMode from "@/models/form/CurrentFormDisplayMode";
import Heading from "@/components/core/componentExtras/Heading.vue";
import EditableComponent from "@/components/core/componentExtras/EditableComponent.vue";
import TeamView from "@/models/team/TeamView";
import DateField from "@/components/core/DateField.vue";

@Component({
  components: {
    Heading,
    EditableComponent,
    RadioGroup,
    CheckboxGroup,
    CheckboxQuestion, SidebarGroup, TextArea, TextField, DateField, TwoColumnStyleLayout}
})

export default class FormCreatorView extends Vue {
  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.FORMS.subRoutes.NEW_FORM;
  private components: FormComponent[] = new Array<FormComponent>();
  private nextComponentId = 1;
  private currentFormDisplayMode: CurrentFormDisplayMode = new CurrentFormDisplayMode(false, true, false);
  private redirectUrl = Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD.url;
  private formName = "Untitled form";
  private availableTeams: TeamView[] = [];
  private selectedTeam: string | null = null;
  private teamsLoaded = false;

  saveForm(){
    if (this.formName.length > 0 && this.selectedTeam !== null && this.components.length > 0) {
      const form = new Form(this.formName, this.availableTeams.find(value => value.teamName === this.selectedTeam)!, this.components);
      WebRequestUtils.post(`${WebRequestUtils.BASE_URL}/api/form/save`, form)
        .then(value => value.json())
        .then(value => {
          if (value.success) {
            this.$router.push(Pages.ROUTES.SHOWN_IN_NAVBAR.FORMS.subRoutes.SEARCH_FORMS.url);
          }
        })
        .catch(e => {
          // TODO FS-39 - Either validation expired or user changed post values to something invalid.
        });
    } else {
      // TODO FS-39 - Show users errors (No components? No form name? No selected team?)
    }
  }

  addComponentToList(event: Event) {
    const userAction = (event.target as Element).getAttribute("name");

    let componentType = "";
    let componentProps: any = {};

    this.normaliseComponentsOrder();

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
      case "addDateField": {
        componentType = "DateField";
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
          selectionValue: new SelectionValue("Add a checkbox option here", false)
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
          selectionValues: [
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
          selectionValues: [
            new SelectionValue("Add a response here", false),
            new SelectionValue("Add a different response here", false)
          ]
        };
        break;
      }
    }

    this.components.push(new FormComponent(
        componentType,
        componentProps,
        order
    ));
  }

  get componentsFromStore(): FormComponent[] {
    console.log(this.$store.state.createFormComponent);
    return this.$store.state.createFormComponent;
  }

  removeFromLayout(componentToDelete: FormComponent) {
    this.components = this.components.filter(item => {
      return item !== componentToDelete;
    })
  }

  moveComponent(direction: string, index: number) {
    const updatedElement = this.components[index];

    //remove the component
    this.removeFromLayout(updatedElement);

    //edit the order
    if (direction === 'up') {
      updatedElement.order -= 101;
    } else if (direction === 'down') {
      updatedElement.order += 101;
    }

    //add it to the end
    this.components.push(updatedElement);

    //sort the list by order
    this.components = this.components.sort((a, b) => {
      if (a.order < b.order) {
        return -1;
      } else if (a.order > b.order) {
        return 1;
      } else {
        return 0;
      }
    });

    //normalise the in order list
    this.normaliseComponentsOrder();
  }

  normaliseComponentsOrder() {
    const oldComponents: FormComponent[] = [];
    this.components.forEach(value => oldComponents.push(value));
    this.components = [];

    for (let i = 0; i < oldComponents.length; i++) {
      const updatedComponent = oldComponents[i];
      updatedComponent.order = ((i + 1) * 100);
      this.components.push(updatedComponent);
    }
  }

  updateComponentProps(newProp: any, component: FormComponent) {
    const unsafeComponent = component as any;
    Object.keys(newProp).forEach(key => {
      unsafeComponent.componentProps[key] = newProp[key];
      unsafeComponent.componentProps = (() => unsafeComponent.componentProps)();
    });
  }

  mounted() {
    WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/teams?canModifyForms=true`, true)
      .then(value => value.json())
      .then(v => v as TeamView[])
      .then(v => this.availableTeams = v)
      .then(() => this.teamsLoaded = true);
  }

  get createTeamLink() {
    return Pages.ROUTES.SHOWN_IN_NAVBAR.TEAMS.subRoutes.CREATE_TEAM.url;
  }

}
</script>
