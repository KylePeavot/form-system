<template>
  <div>
    <p>{{ text }}</p>
  </div>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";

@Component
export default class Dashboard extends Vue {

  private text = 'Test';

  mounted() {
    fetch(process.env.VUE_APP_API_URL!)
        .then(async value => {
          this.text = await value.text();
        });
  }

}

</script>