<template>
  <div>
    <BaseStyleLayout title="Page title" :selected-page="page">
      <Heading :level="2">Your dashboard</Heading>
      <p v-if="!loaded">
        <i class="animate-spin ph-arrow-clockwise " />
        Awaiting dashboard items
      </p>
      <p v-else-if="Object.keys(response).length === 0">You have no forms to respond to</p>
      <router-link :to="getUrlForResponse()" v-else>{{ response }}</router-link>

<!--      FS-86 TODO remove this-->
      <br />
      <button @click="assignFormTestRemoveMe">Assign form</button>
    </BaseStyleLayout>
  </div>
</template>

<script lang="ts">

import  {Component, Vue} from "vue-property-decorator";
import Heading from "../components/core/componentExtras/Heading.vue";
import BaseStyleLayout from "../components/layout/BaseStyleLayout.vue";
import WebRequestUtils from "../utils/WebRequestUtils";
import Pages from "../models/navigation/Pages";
import AuthenticationUtils from "@/utils/AuthenticationUtils";
import AssignWorkflowVariables from "@/models/workflow/AssignWorkflowVariables";
import Team from "@/models/team/Team";
import {User} from "@auth0/auth0-spa-js";

@Component({
  components: {
    BaseStyleLayout,
    Heading
  }
})
export default class Dashboard extends Vue {

  private text = 'test';
  private response: string | undefined;
  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD;
  private loaded = false;

  mounted() {
    this.getDashboardContents();
  }

  getDashboardContents() {
    //get all assigned tasks
    WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/assigned-tasks`, true)
    .then(async value => {
      this.response = await value.json();
    })
    .finally(() => this.loaded = true);
    this.$forceUpdate();
    console.log(this.response);
  }
  //FS-86 TODO remove this
  assignFormTestRemoveMe() {
    WebRequestUtils.post(`${WebRequestUtils.BASE_URL}/api/flowable/workflow/form/start`, new AssignWorkflowVariables("kp535", {id: 1, name: "Admin"} , "ksp5", 1));
  }

  getUrlForResponse(): string {
    return Pages.ROUTES.FORM.FILL_FORM.url.replace(":id", "1");
  }
}

</script>