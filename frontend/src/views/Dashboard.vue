<template>
  <div>
    <BaseStyleLayout title="Page title" :selected-page="page">
      <Heading :level="2">Response: {{ text }}</Heading>

      <button @click="assignForm">Assign form to user (me)</button>

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

  private text = 'Test';
  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD;

  mounted() {
    WebRequestUtils.get(`${process.env.VUE_APP_API_URL!}/api`)
        .then(async value => {
          this.text = await value.text();
        });
  }

  assignForm() {
    // const requestOptions = {
    //   method: "POST",
    //   headers: { "Content-Type": "application/json" },
    //   body: JSON.stringify({ title: "Vue POST Request Example" })
    // };
    //
    // fetch(`${process.env.VUE_APP_API_URL!}/api/flowable/workflow/start`, requestOptions);

    WebRequestUtils.get(`${process.env.VUE_APP_API_URL!}/api/flowable/workflow/start`);
  }

}

</script>