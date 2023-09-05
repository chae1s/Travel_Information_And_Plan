<template>
  <form v-on:submit.prevent="submitForm">
    <div>
      <label for="username">id: </label>
      <input id="username" type="text" v-model="username">
    </div>
    <div>
      <label for="password">pw: </label>
      <input id="paswword" type="password" v-model="password">
    </div>
    <button type="submit">login</button>
    <p>{{ this.$store.state.token }}??</p>
  </form>
</template>

<script>
import { loginUser } from '@/api/index';
export default {
  name: 'LoginView',
  data() {
    return {
      username: '',
      password: '',
      token: ''
    };
  },
  methods: {
    async submitForm() {
      try {
        // 비즈니스 로직
        const userData = {
          email: this.username,
          password: this.password,
        };
        const { data } = await loginUser(userData);
        this.token = data.token;
        console.log(this.token)
        //this.$store.commit('setUsername', data.user.username);
        this.$store.commit('setToken', this.token); // 토큰 셋팅
        console.log(this.$store.state)
        //this.$router.push('/');
      } catch (error) {
        // 에러 핸들링할 코드
        console.log(error.response.data);
        this.logMessage = error.response.data;
      } finally {
        this.initForm();
      }
    },
    initForm() {
      this.username = '';
      this.password = '';
    }
    }

  
}
</script>

