<template>
  <div>
    <BaseStyleLayout title="Get forms available to you" :selected-page="page">
      <table class="table__table">
        <thead class="table__thead">
        <tr>
          <th class="table__th">Name</th>
          <th class="table__th">Created</th>
          <th class="table__th">Updated</th>
        </tr>
        </thead>
        <tbody>
        <tr :v-if="forms !== undefined" v-for="form in forms" :key="form.id" class="table__tr">
          <td class="table__td">{{form.name}}</td>
          <td class="table__td">Created by  {{form.createdBy}} on {{form.createdWhen}}</td>
          <td class="table__td">Updated by {{form.lastUpdatedBy }} on {{form.lastUpdatedWhen}}</td>
        </tr>
        </tbody>
      </table>
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

  created() {
    this.getForms();
  }

  getForms() {
    WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/form/browse`, true)
        .then(value => value.json())
        .then(value => value as FormViewInterface[])
        .then(value => this.forms = value);
  }
}
</script>