<template>
  <div>
    <BaseStyleLayout title="Request for users to complete a form" :selected-page="page">
      <Heading :level="2">Who will be filling out the form?</Heading>
      <UserSelector :multiple="true" :value="selectedUsers"/>
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

@Component({
  components: {UserSelector, Heading, BaseStyleLayout}
})
export default class SendFormView extends Vue {

  @Prop({required: true})
  private formId!: number;

  private page = Pages.ROUTES.FORM.SEND_FORM;
  private selectedUsers: KentUser[] = [];
  private form: Form | null = null;

  created() {
    WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/form/get/${this.formId}`, true)
    .then(async value => await value.json())
    .then(value => {
      return value;
    })
    .then(value => value as FormInterface)
    .then(value => this.form = Form.mapFormInterfaceToForm(value))
    .catch((e: Error) => {
      console.log("Error", e);
      FlashUtils.flash(FlashType.ERROR, "Failed to retrieve form information", e.message);
      this.$router.back();
    })
  }

}

</script>