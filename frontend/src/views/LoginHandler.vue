<template>
  <TwoColumnStyleLayout title="Login" :selected-page="page">
    <template v-slot:sidebar>
      <p class="sidebar-menu__title">
        Section
      </p>
      <div class="sidebar-menu__item-container">
        <ul>
          <li class="sidebar-menu__item-link">First entry</li>
          <li class="sidebar-menu__item-link">Second entry</li>
        </ul>
      </div>
      <p class="sidebar-menu__title">
        Other section
      </p>
    </template>
  </TwoColumnStyleLayout>
</template>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";
import AuthenticationUtils from "../utils/AuthenticationUtils";
import Pages from "../models/navigation/Pages";
import BaseStyleLayout from "../components/layout/BaseStyleLayout.vue";
import TwoColumnStyleLayout from "../components/layout/TwoColumnStyleLayout.vue";

@Component({
  components: {TwoColumnStyleLayout, BaseStyleLayout}
})
export default class LoginHandler extends Vue {

  private page = Pages.ROUTES.STATIC.LOGIN;

  created() {
    AuthenticationUtils.login();
  }

}

</script>