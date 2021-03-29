<template>
  <div>
    <BaseStyleLayout :title="`Request for users to complete the form: ${formName}`" :selected-page="page">
      <Heading :level="2">Who will be filling out the form?</Heading>
      <UserSelector :multiple="true" v-model="selectedUsers"/>
      <br/>
      <hr/>
      <template v-if="form !== null && currentUser !== undefined">
        <br/>
        <button class="button button--primary" @click="sendForms">Send form</button>
        <button class="button" v-if="showingFormPreview" @click="showingFormPreview = false">Hide form preview</button>
        <button class="button" v-else @click="showingFormPreview = true">Show form preview</button>
        <template v-if="showingFormPreview">
          <div class="p-2 bg-gray-100 mt-2">
            <div v-for="(component, index) in form.componentList" :key="component.order">
              <component :is="component.componentType" v-bind="component.componentProps" :level="2" :id="index" :id-prefix="index" :current-form-display-mode="formDisplayMode"/>
            </div>
          </div>
        </template>
      </template>
    </BaseStyleLayout>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import BaseStyleLayout from "../../components/layout/BaseStyleLayout.vue";
import Pages from "@/models/navigation/Pages";
import Heading from "@/components/core/componentExtras/Heading.vue";
import UserSelector from "@/components/core/widgets/UserSelector.vue";
import KentUser from "@/models/external/users/KentUser";
import WebRequestUtils from "@/utils/WebRequestUtils";
import FormInterface from "@/models/form/interfaces/FormInterface";
import Form from "@/models/form/Form";
import FlashUtils, {FlashType} from "@/utils/FlashUtils";
import CurrentFormDisplayMode from "@/models/form/CurrentFormDisplayMode";
import TextField from "@/components/core/TextField.vue";
import TextArea from "@/components/core/TextArea.vue";
import CheckboxQuestion from "@/components/core/checkbox/CheckboxQuestion.vue";
import CheckboxGroup from "@/components/core/checkbox/CheckboxGroup.vue";
import RadioGroup from "@/components/core/radio/RadioGroup.vue";
import AssignWorkflowVariables from "@/models/workflow/AssignWorkflowVariables";
import AuthenticationUtils from "@/utils/AuthenticationUtils";

@Component({
  components: {
    TextField,
    TextArea,
    CheckboxQuestion,
    CheckboxGroup,
    RadioGroup,
    UserSelector,
    Heading,
    BaseStyleLayout
  }
})
export default class SendFormView extends Vue {

  @Prop({required: true})
  private formDetailId!: number;

  private page = Pages.ROUTES.FORM.SEND_FORM;
  private selectedUsers: KentUser[] = [];
  private form: Form | null = null;
  private showingFormPreview = false;
  private currentUser: any | undefined = undefined;

  private formDisplayMode = new CurrentFormDisplayMode(true, false, false);

  created() {
    AuthenticationUtils.getUser().then(user => {
      this.currentUser = user;
    });
    WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/form/get/${this.formDetailId}`, true)
    .then(async value => await value.json())
    .then(value => {
      return value;
    })
    .then(value => value as FormInterface)
    .then(value => this.form = Form.mapFormInterfaceToForm(value))
    .catch((e: Error) => {
      FlashUtils.flash(FlashType.ERROR, "Failed to retrieve form information", e.message);
      this.$router.back();
    })
  }

  get formName(): string {
    return this.form?.name || "";
  }

  private sendForms() {
    const usernames = this.selectedUsers.map(value => value.id);
    const workflowVars = new AssignWorkflowVariables(this.currentUser!.name, usernames, this.formDetailId);
    WebRequestUtils.post(`${WebRequestUtils.BASE_URL}/api/flowable/workflow/form/start`, workflowVars)
      .then(() => {
        this.$router.back();
      });
  }

}

</script>