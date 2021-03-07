<template>
  <VueSelect label="_displayName" :options="users" :value="value" @input="emitInput" :multiple="multiple"></VueSelect>
</template>

<script lang="ts">

import {Component, Model, Prop, Vue, Watch} from "vue-property-decorator";
import VueSelect from "vue-select";
import KentUser from "../../../models/external/users/KentUser";
import AuthenticationUtils from "../../../utils/AuthenticationUtils";
import WebRequestUtils from "../../../utils/WebRequestUtils";

@Component({
  components: {
    VueSelect
  }
})
export default class UserSelector extends Vue {

  private users: KentUser[] = [];

  @Watch("trackValue")
  trackUpdate(newValue: any) {
    this.$emit("input", newValue);
  }

  @Prop({default: false})
  private multiple!: boolean;

  @Model("input", {required: true})
  private value!: KentUser | KentUser[] | null;

  emitInput(event: any) {
    this.$emit("input", event);
  }

  mounted() {
    AuthenticationUtils.isLoggedIn().then(v => {
      if (v) {
        WebRequestUtils.get(`${WebRequestUtils.BASE_URL}/api/kent/users`, true)
        .then(response => response.json())
        .then(values => values.map((x: string) => KentUser.createUserFromResponseString(x)))
        .then(userArray => this.users = userArray);
      }
    });
  }

}

</script>