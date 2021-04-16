import Vue from 'vue'
import VueRouter, {RouteConfig} from 'vue-router'
import Pages from "@/models/navigation/Pages";
import AuthenticationUtils from "@/utils/AuthenticationUtils";
import {FormDisplayModeEnum} from "@/models/form/FormDisplayModeEnum";

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD.url,
    name: Pages.ROUTES.SHOWN_IN_NAVBAR.DASHBOARD.name,
    component: () => import("../views/Dashboard.vue"),
    meta: {
      loginRequired: true
    }
  },
  {
    path: Pages.ROUTES.SHOWN_IN_NAVBAR.FORMS.subRoutes.NEW_FORM.url,
    name: Pages.ROUTES.SHOWN_IN_NAVBAR.FORMS.subRoutes.NEW_FORM.name,
    component: () => import("../views/forms/FormCreatorView.vue")
  },
  {
    path: Pages.ROUTES.STATIC.LOGIN.url,
    name: Pages.ROUTES.STATIC.LOGIN.name,
    component: () => import("../views/LoginScreen.vue")
  },
  {
    path: Pages.ROUTES.STATIC.LOGOUT.url,
    name: Pages.ROUTES.STATIC.LOGOUT.name,
    component: () => import("../views/LogoutScreen.vue")
  },
  {
    path: Pages.ROUTES.SHOWN_IN_NAVBAR.TEAMS.url,
    name: Pages.ROUTES.SHOWN_IN_NAVBAR.TEAMS.name,
    component: () => import("../views/teams/TeamsScreen.vue"),
    meta: {
      loginRequired: true
    }
  },
  {
    path: Pages.ROUTES.FORM.VIEW_FORM.url,
    name: Pages.ROUTES.FORM.VIEW_FORM.name,
    component: () => import ("../views/forms/FormView.vue"),
    meta: {
      loginRequired: true
    },
    props: route => ({
      mode: FormDisplayModeEnum.READ_ONLY,
      id: route.params.id
    })
  },
  {
    path: Pages.ROUTES.SHOWN_IN_NAVBAR.TEAMS.subRoutes.CREATE_TEAM.url,
    name: Pages.ROUTES.SHOWN_IN_NAVBAR.TEAMS.subRoutes.CREATE_TEAM.name,
    component: () => import("../views/teams/TeamCreationScreen.vue"),
    meta: {
      loginRequired: true
    },
    props: {
      mode: "CREATE"
    }
  },
  {
    path: Pages.ROUTES.STATIC.EDIT_TEAM.url,
    name: Pages.ROUTES.STATIC.EDIT_TEAM.name,
    component: () => import("../views/teams/TeamCreationScreen.vue"),
    meta: {
      loginRequired: true
    },
    props: route => ({
      mode: "EDIT",
      teamId: route.params.id
    })
  },
  {
    path: Pages.ROUTES.FORM.FILL_FORM.url,
    name: Pages.ROUTES.FORM.FILL_FORM.name,
    component: () => import ("../views/forms/FormView.vue"),
    meta: {
      loginRequired: true
    },
    props: route => ({
      mode: FormDisplayModeEnum.FORM_FILLING,
      id: route.params.id
    })
  },
  {
    path: Pages.ROUTES.FORM.SEND_FORM.url,
    name: Pages.ROUTES.FORM.SEND_FORM.name,
    component: () => import("../views/forms/SendFormView.vue"),
    meta: {
      loginRequired: true
    },
    props: route => ({
      formDetailId: route.params.id
    })
  },
  {
    path: Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.TEXT_FIELD.url,
    name: Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.TEXT_FIELD.name,
    component: () => import("../views/components/TextFieldView.vue")
  },
  {
    path: Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.TEXT_AREA.url,
    name: Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.TEXT_AREA.name,
    component: () => import("../views/components/TextAreaView.vue")
  },
  {
    path: Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.SINGLE_CHECKBOX.url,
    name: Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.SINGLE_CHECKBOX.name,
    component: () => import("../views/components/SingleCheckboxView.vue")
  },
  {
    path: Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.GROUP_CHECKBOX.url,
    name: Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.GROUP_CHECKBOX.name,
    component: () => import("../views/components/GroupedCheckboxView.vue")
  },
  {
    path: Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.GROUP_RADIO.url,
    name: Pages.ROUTES.SHOWN_IN_NAVBAR.COMPONENTS.subRoutes.GROUP_RADIO.name,
    component: () => import("../views/components/GroupedRadioView.vue")
  },
  {
    path: Pages.ROUTES.SHOWN_IN_NAVBAR.FORMS.subRoutes.SEARCH_FORMS.url,
    name: Pages.ROUTES.SHOWN_IN_NAVBAR.FORMS.subRoutes.SEARCH_FORMS.name,
    component: () => import("../views/forms/BrowseFormsView.vue")
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

// We can ensure that all users are logged in when they go to an endpoint with the loginRequired metadata.
router.afterEach(async to => {
  if (to.meta.loginRequired) {
    const isLoggedIn = await AuthenticationUtils.isLoggedIn();
    if (!isLoggedIn) {
      await router.push(Pages.ROUTES.STATIC.LOGIN.url);
    }
  }
  document.title = to.name!;
});

export default router
