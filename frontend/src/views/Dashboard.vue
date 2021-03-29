<template>
  <div>
    <BaseStyleLayout title="Your dashboard" :selected-page="page">
      <p v-if="!loaded">
        <i class="animate-spin ph-arrow-clockwise " />
        Awaiting dashboard items
      </p>
      <p v-else-if="response.length === 0">You have no forms to respond to</p>
      <div class="two-column-grid" v-else>
        <div v-for="(resp, index) of response" :key="`${resp.formName}-${index}`">
          <DashboardItem :response="resp"/>
        </div>
      </div>
    </BaseStyleLayout>
  </div>
</template>

<script lang="ts">

import  {Component, Vue} from "vue-property-decorator";
import Heading from "../components/core/componentExtras/Heading.vue";
import BaseStyleLayout from "../components/layout/BaseStyleLayout.vue";
import WebRequestUtils from "../utils/WebRequestUtils";
import Pages from "../models/navigation/Pages";
import FormResponse, {FormResponseUtils} from "@/models/form/FormResponse";
import DashboardItem from "@/components/layout/Dashboard/DashboardItem.vue";

@Component({
  components: {
    DashboardItem,
    BaseStyleLayout,
    Heading
  }
})
export default class Dashboard extends Vue {

  private response: FormResponse[] = [];
  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD;
  private loaded = false;

  private refreshInterval: number | undefined = undefined;

  mounted() {
    this.getDashboardContents();
    this.refreshInterval = setInterval(this.getDashboardContents, 100);
  }

  unmounted() {
    clearInterval(this.refreshInterval);
    this.refreshInterval = undefined;
  }

  getDashboardContents() {
    //get all assigned tasks
    WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/assigned-tasks`, true)
    .then(async value => {
      const resp = await value.json();
      this.response = FormResponseUtils.createFromResponse(resp).sort((a, b) => {
        const aDate = new Date(a.assignedTimestamp);``
        const bDate = new Date(b.assignedTimestamp);
        return aDate.toLocaleDateString().localeCompare(bDate.toLocaleDateString());
      });
    })
    .finally(() => {
      this.loaded = true;
      console.log(this.response);
    });
  }
}

</script>

<style lang="scss">
  .two-column-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-gap: 1em;
  }
</style>