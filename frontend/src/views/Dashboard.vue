<template>
  <div>
    <BaseStyleLayout title="Page title" :selected-page="page">
      <Heading :level="2">Response: {{ text }}</Heading>
      <UserSelector v-model="user" :multiple="false"/>
      <template v-if="user !== null">
        <p>
          {{ user.id }}
        </p>
      </template>
      <br/>
      <hr/>
      <br/>
      <UserSelector v-model="users" :multiple="true"/>
      <template v-if="users !== null">
        <p v-for="(selectedUser, index) of users" :key="`selected-user-${selectedUser.id}-${index}`">
          {{ selectedUser.id }}
        </p>
      </template>
    </BaseStyleLayout>
  </div>
</template>

<script lang="ts">

import  {Component, Vue} from "vue-property-decorator";
import Heading from "../components/core/Heading.vue";
import BaseStyleLayout from "../components/layout/BaseStyleLayout.vue";
import WebRequestUtils from "../utils/WebRequestUtils";
import Pages from "../models/navigation/Pages";
import AuthenticationUtils from "@/utils/AuthenticationUtils";
import KentUser from "@/models/external/users/KentUser";
import UserSelector from "@/components/core/widgets/UserSelector.vue";

@Component({
  components: {
    UserSelector,
    BaseStyleLayout,
    Heading
  }
})
export default class Dashboard extends Vue {

  private text = 'Test';
  private page = Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD;
  private user: KentUser | null = null;
  private users: KentUser[] | null = null;

  mounted() {
    WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api`)
        .then(async value => {
          this.text = await value.text();
        });

    AuthenticationUtils.isLoggedIn().then(v => {
      if (v) {
        const validateAuthStartTime = new Date().getUTCMilliseconds();
        WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/test-auth`, true)
        .then(value => value.json())
        .then(JSON.stringify)
        .then(value => {
          console.log("Expect successful AuthReq:", value);
        })
        .then(() => {
          this.text = `Proved authentication in ${new Date().getUTCMilliseconds() - validateAuthStartTime}ms`
        });
      } else {
        WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/test-auth`, false)
        .then(value => value.json())
        .then(JSON.stringify)
        .then(value => {
          console.log("Expect successful AuthReq:", value);
        });
      }
    });
  }

}

</script>