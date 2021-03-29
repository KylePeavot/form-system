<template>
  <div>
    <div class="card shadow border-2 p-4 px-8 rounded-lg">
      <div class="grid">
        <div>
          <div>
            <Heading :level="3">{{ response.formName }}</Heading>
          </div>
          <div>
            <p>Team: {{ response.assignedByTeamDetail.name }}</p>
            <p>Assigned by: {{ response.assignedBy.username }}@kent.ac.uk</p>
            <p>Assigned on: {{ convertedDate }}</p>
          </div>
        </div>
        <div>
          <div class="text-right">
            <router-link :to="respondUrl(response.id)">
              <button class="button button--action mt-2">Respond</button>
            </router-link>
            <button class="button button--danger mt-1" @click="withdraw" v-if="!isWithdrawing">Withdraw</button>
            <button class="button button--danger mt-1" v-else-if="isWithdrawing"><i class="animate-spin ph-arrow-clockwise " /></button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";
import FormResponse from "../../../models/form/FormResponse";
import Heading from "@/components/core/componentExtras/Heading.vue";
import moment from 'moment';
import WebRequestUtils from "@/utils/WebRequestUtils";
import Pages from "@/models/navigation/Pages";

@Component({
  components: {Heading}
})
export default class DashboardItem extends Vue {

  @Prop({required: true})
  private response!: FormResponse;

  private isWithdrawing = false;

  get convertedDate() {
    const date = new Date(this.response.assignedTimestamp);
    return moment(date).format("MMMM Do YYYY");
  }

  private withdraw() {
    this.isWithdrawing = true;
    WebRequestUtils.post(`${WebRequestUtils.BASE_URL}/api/flowable/workflow/form/delete/${this.response.id}`, {});
  }

  private respondUrl() {
    return Pages.ROUTES.FORM.FILL_FORM.url.replace(":id", this.response.id);
  }

}
</script>

<style lang="scss">

  .grid {
    display: grid;
    grid-template-columns: max-content 1fr;
    grid-gap: 2em;
  }

  .card {
    @apply bg-gray-50;
  }

</style>