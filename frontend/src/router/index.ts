import Vue from 'vue'
import VueRouter, {RouteConfig} from 'vue-router'
import Pages from "@/models/navigation/Pages";

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD.url,
    name: Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD.name,
    component: () => import("../views/Dashboard.vue")
  },
  {
    path: Pages.ROUTES.STATIC.LOGIN.url,
    name: Pages.ROUTES.STATIC.LOGIN.name,
    component: () => import("../views/LoginHandler.vue")
  },
  {
    path: Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.TEXT_FIELD.url,
    name: Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.TEXT_FIELD.name,
    component: () => import ("../views/components/TextFieldView.vue")
  },
  {
    path: Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.TEXT_AREA.url,
    name: Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.TEXT_AREA.name,
    component: () => import ("../views/components/TextAreaView.vue")
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.afterEach(to => {
  document.title = to.name!;
})

export default router
