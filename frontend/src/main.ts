import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { domain, clientId } from "../auth_config.json";
import { Auth0Plugin } from "./plugins/auth0";
import Pages from "@/models/navigation/Pages";

// Install the authentication plugin here
Vue.use(Auth0Plugin, {
  domain,
  clientId,
  onRedirectCallback: (appState: any) => {
    router.push(window.location.pathname + Pages.ROUTES.STATIC.LOGIN);
  }
});

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
