import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { domain, clientId } from "../auth_config.json";
import { Auth0Plugin, getInstance as getAuth0Instance } from "./plugins/auth0";
import Pages from "@/models/navigation/Pages";
import AuthenticationUtils from "@/utils/AuthenticationUtils";

// Install the authentication plugin here
Vue.use(Auth0Plugin, {
  domain,
  clientId
});

AuthenticationUtils.bindAuth(getAuth0Instance());

Vue.config.productionTip = false

export const bus = new Vue();

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
