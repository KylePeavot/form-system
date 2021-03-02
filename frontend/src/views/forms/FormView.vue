<template>
  <div>
    <BaseStyleLayout title="Get forms available to you" :selected-page="page">
      <table v-if="loaded" class="results-table">
        <thead class="results-table__thead">
        <tr>
          <th class="results-table__th">Name</th>
          <th></th>
          <th class="results-table__th">Created</th>
          <th class="results-table__th">Updated</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="form in forms" :key="form.id" class="results-table__tr">
          <td colspan="2" class="results-table__td results-table__td--name">{{ form.name }}</td>
          <td class="results-table__td">Created by {{ form.createdBy }} on {{ form.createdWhen }}</td>
          <td class="results-table__td">Updated by {{ form.lastUpdatedBy }} on {{ form.lastUpdatedWhen }}</td>
        </tr>
        </tbody>
      </table>
      <div v-else><span> <i class="animate-spin ph-arrow-clockwise text-xl"></i>Loading</span></div>
    </BaseStyleLayout>
  </div>

</template>
<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import Pages from "@/models/navigation/Pages";
import BaseStyleLayout from "@/components/layout/BaseStyleLayout.vue";
import WebRequestUtils from "@/utils/WebRequestUtils";
import FormViewInterface from "@/models/form/FormViewInterface";

@Component({
  components: {
    BaseStyleLayout,
  }
})

export default class FormView extends Vue {
  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.FORMS.subRoutes.MY_FORMS;
  private forms: FormViewInterface[] = [];
  private loaded = false;

  created() {
    this.getForms();
  }

  getForms() {
    WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/form/browse`, true)
        .then(value => value.json())
        .then(value => value as FormViewInterface[])
        .then(value => this.forms = value)
        .then(() => this.loaded = true);
  }
}
</script>