import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    createFormComponent: []
  },
  mutations: {
    setCreatedFormComponents(state, components) {
      state.createFormComponent = components;
    }
  },
  actions: {
    updateCreatedFormComponents({ commit }, components) {
      commit("setCreatedFormComponents", components);
    }
  },
  modules: {
  }
})
