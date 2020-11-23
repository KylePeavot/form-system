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
    fetch(`http://localhost:8080`)
        .then(value => {
          return value.text()
        })
        .then(value => {
          this.text = value
        });
  }

}

</script>