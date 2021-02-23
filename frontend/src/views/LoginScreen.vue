<template>
  <BaseStyleLayout title="Login" :selected-page="page" v-show="loaded">

    <Heading :level="2">
      You must be logged in to access the application
    </Heading>
    <br/>
    <button class="button button--primary" @click="login">Login</button>

  </BaseStyleLayout>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import Pages from "../models/navigation/Pages";
import BaseStyleLayout from "../components/layout/BaseStyleLayout.vue";
import TwoColumnStyleLayout from "../components/layout/TwoColumnStyleLayout.vue";
import AuthenticationUtils from "@/utils/AuthenticationUtils";
import Heading from "@/components/core/Heading.vue";

@Component({
  components: {Heading, TwoColumnStyleLayout, BaseStyleLayout}
})
export default class LoginHandler extends Vue {

  private page = Pages.ROUTES.STATIC.LOGIN;
  private loaded = false;

  login() {
    AuthenticationUtils.getContext().loginWithRedirect({
      /* eslint-disable @typescript-eslint/camelcase */
      redirect_uri: "http://localhost:8080/"
    });
  }

  created() {
    AuthenticationUtils.isLoggedIn().then(authenticated => {
      if (authenticated) {
        this.$router.replace(Pages.ROUTES.STATIC.LOGOUT.url);
      }
      this.loaded = true;
    });
  }

}

</script>