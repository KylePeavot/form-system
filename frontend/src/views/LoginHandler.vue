<template>
  <div>

  </div>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import AuthenticationUtils from "@/utils/AuthenticationUtils";

  @Component
  export default class LoginHandler extends Vue {

  created() {
    AuthenticationUtils.login();
  }

  }

</script>