<template>
  <BaseStyleLayout selected-page="page" title="Logout">
    <Heading :level="2">
      Current user: {{ username }}
    </Heading>
    <br/>
    <button class="button button--danger" @click="logout">Logout</button>
  </BaseStyleLayout>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import Pages from "../models/navigation/Pages";
import BaseStyleLayout from "@/components/layout/BaseStyleLayout.vue";
import Heading from "@/components/core/Heading.vue";
import AuthenticationUtils from "@/utils/AuthenticationUtils";

@Component({
  components: {Heading, BaseStyleLayout}
})
export default class LogoutHandler extends Vue {

  private page = Pages.ROUTES.STATIC.LOGOUT;
  private username = "";

  created() {
    AuthenticationUtils.isLoggedIn().then(async loggedIn => {
      if (loggedIn) {
        this.username = (await AuthenticationUtils.getUser()).email;
      } else {
        await this.$router.replace(Pages.ROUTES.STATIC.LOGIN.url);
      }
    });
  }

  logout() {
    AuthenticationUtils.getContext().logout();
  }

}

</script>