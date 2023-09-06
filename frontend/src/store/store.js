import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate'

export default new Vuex.Store({
  state: {
    //username: '',
    token: '',
  },
  getters: {
    isLogin(state) {
      return state.token !== '';
    },
  },
  mutations: {
    // setUsername(state, username) {
    //   state.username = username;
    // },
    setToken(state, token) {
      state.token = token;
    },
    // clearUsername(state) {
    //   state.username = '';
    // },
  },
  plugins: [
      createPersistedState()
  ]
});