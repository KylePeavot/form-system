<template>
  <div>
    <p>{{ text }}</p>
  </div>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import WebRequestUtils from "@/utils/WebRequestUtils";

@Component
export default class Dashboard extends Vue {

  private text = 'Test';

  mounted() {
    WebRequestUtils.get(`${process.env.VUE_APP_API_URL!}/api`)
        .then(async value => {
          this.text = await value.text();
        });
  }

}

</script>