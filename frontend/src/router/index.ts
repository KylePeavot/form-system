import Vue from 'vue'
import VueRouter, {RouteConfig} from 'vue-router'
import Dashboard from "../views/Dashboard.vue";
import RouteUtils from "../utils/RouteUtils";
import {CorePage} from "../models/navigation/CorePage";

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: RouteUtils.getNavigationRoute(CorePage.DASHBOARD),
    name: 'Dashboard',
    component: Dashboard
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
