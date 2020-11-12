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
    fetch(`http://${process.env.VUE_APP_API_URL}`)
        .then(value => {
          return value.text()
        })
        .then(value => {
          this.text = value
        });
  }

}

</script>