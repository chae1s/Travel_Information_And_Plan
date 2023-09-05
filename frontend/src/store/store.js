import Vuex from 'vuex';

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
});