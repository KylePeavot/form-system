<template>
  <div>
    <BaseStyleLayout title="Page title" :selected-page="page">
      <Heading :level="2">Response: {{ text }}</Heading>
    </BaseStyleLayout>
  </div>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import Heading from "../components/core/Heading.vue";
import BaseStyleLayout from "../components/layout/BaseStyleLayout.vue";
import {CorePage} from "@/models/navigation/CorePage";

@Component({
  components: {
    BaseStyleLayout,
    Heading
  }
})
export default class Dashboard extends Vue {

  private text = 'Test';
  private page = CorePage.DASHBOARD;

  mounted() {
    fetch(process.env.VUE_APP_API_URL!)
        .then(async value => {
          this.text = await value.text();
        });
  }

}

</script>