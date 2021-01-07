<template>
  <TwoColumnStyleLayout title="Login" :selected-page="page">

    <template v-slot:sidebar>
      <p class="px-4 py-2 font-bold">
        Section
      </p>
      <div class="ml-4 py-2 pb-8">
        <ul>
          <li class="px-4 py-1 hover:underline cursor-pointer">First entry</li>
          <li class="px-4 py-1 hover:underline cursor-pointer">Second entry</li>
        </ul>
      </div>
      <p class="px-4 py-2 font-bold">
        Other section
      </p>
    </template>

    <button class="button" @click="login">Login</button>

  </TwoColumnStyleLayout>
</template>

<script lang="ts">

// @ts-nocheck

import {Component, Vue} from "vue-property-decorator";
import Pages from "../models/navigation/Pages";
import BaseStyleLayout from "../components/layout/BaseStyleLayout.vue";
import TwoColumnStyleLayout from "../components/layout/TwoColumnStyleLayout.vue";

@Component({
  components: {TwoColumnStyleLayout, BaseStyleLayout}
})
export default class LoginHandler extends Vue {

  private page = Pages.ROUTES.STATIC.LOGIN;

  login() {
    this.$auth.loginWithRedirect();
  }

}

</script>