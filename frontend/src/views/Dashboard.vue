<template>
  <div>
    <BaseStyleLayout title="Page title" :selected-page="page">
      <Heading :level="2">Your dashboard</Heading>

      <button @click="assignForm">Assign form to user (me)</button>
      <br />
      <button @click="deleteForm">Delete form</button>
      <br />
      <button @click="submitForm">Submit form for review</button>
      <br />
      <button @click="withdrawForm">Withdraw form from review</button>
      <br />
      <button @click="completeReviewApproved">Approve review</button>
      <br />
      <button @click="completeReviewRejected">Reject review (return to filler)</button>

      <p>Items: {{ response }}</p>

    </BaseStyleLayout>
  </div>
</template>

<script lang="ts">

import  {Component, Vue} from "vue-property-decorator";
import Heading from "../components/core/Heading.vue";
import BaseStyleLayout from "../components/layout/BaseStyleLayout.vue";
import WebRequestUtils from "../utils/WebRequestUtils";
import Pages from "../models/navigation/Pages";
import AuthenticationUtils from "@/utils/AuthenticationUtils";

@Component({
  components: {
    BaseStyleLayout,
    Heading
  }
})
export default class Dashboard extends Vue {

  private text = 'test';
  private response = "Awaiting dashboard items";
  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD;

  mounted() {
    //get all assigned tasks
    this.getDashboardContents();

    // AuthenticationUtils.isLoggedIn().then(v => {
    //   if (v) {
    //     const validateAuthStartTime = new Date().getUTCMilliseconds();
    //     WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/test-auth`, true)
    //     .then(value => value.json())
    //     .then(JSON.stringify)
    //     .then(value => {
    //       console.log("Expect successful AuthReq:", value);
    //     })
    //     .then(() => {
    //       this.text = `Proved authentication in ${new Date().getUTCMilliseconds() - validateAuthStartTime}ms`
    //     });
    //   } else {
    //     WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/test-auth`, false)
    //     .then(value => value.json())
    //     .then(JSON.stringify)
    //     .then(value => {
    //       console.log("Expect successful AuthReq:", value);
    //     });
    //   }
    // });
  }

  getDashboardContents() {
    //get all assigned tasks
    WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/assigned-tasks`, true)
    .then(async value => {
      this.response = await value.text();
    });
    this.$forceUpdate();
  }

  async assignForm() {
    await WebRequestUtils.post(`${process.env.VUE_APP_API_URL!}/api/flowable/workflow/start`, {test: "test"});
    this.getDashboardContents();
  }

  async deleteForm() {
    await WebRequestUtils.post(`${process.env.VUE_APP_API_URL!}/api/flowable/workflow/delete-form`, {test: "test"});
    this.getDashboardContents();
  }

  async submitForm() {
    await WebRequestUtils.post(`${process.env.VUE_APP_API_URL!}/api/flowable/workflow/submit-form`, {test: "test"});
    this.getDashboardContents();
  }

  async withdrawForm() {
    await WebRequestUtils.post(`${process.env.VUE_APP_API_URL!}/api/flowable/workflow/withdraw-form`, {test: "test"});
    this.getDashboardContents();
  }

  async completeReviewApproved() {
    await WebRequestUtils.post(`${process.env.VUE_APP_API_URL!}/api/flowable/workflow/complete-review-approved`, {test: "test"});
    this.getDashboardContents();
  }

  async completeReviewRejected() {
    await WebRequestUtils.post(`${process.env.VUE_APP_API_URL!}/api/flowable/workflow/complete-review-rejected`, {test: "test"});
    this.getDashboardContents();
  }



}

</script>