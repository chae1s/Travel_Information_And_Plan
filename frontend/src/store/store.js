import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate'

export default new Vuex.Store({
  state: {
    //username: '',
    token: '',
    boardId: null
  },
  getters: {
    isLogin(state) {
      return state.token !== '';
    },
  },
  mutations: {
    logout(state, token) {
      localStorage.removeItem(token)
      state.token = ''
    },
    setToken(state, token) {
      state.token = token;
    },
    setBoardId(state, id) {
      state.boardId = id
    }
  },
  actions: {
    updateBoardId({ commit }, id) {
      commit('setBoardId', id)
    }
  },
  plugins: [
      createPersistedState()
  ]
});