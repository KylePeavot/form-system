import Vue from 'vue'
import VueRouter, {RouteConfig} from 'vue-router'
import Pages from "@/models/navigation/Pages";

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD.url,
    name: 'Dashboard',
    component: () => import("../views/Dashboard.vue")
  },
  {
    path: Pages.ROUTES.AUTHENTICATION.LOGIN_OR_LOGOUT().subRoutes!.LOGIN.url,
    name: 'Login',
    component: () => import("../views/LoginHandler.vue")
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
