<template>
  <div>
    <h3>유저 검색</h3>
    <!-- 검색 폼 -->
    <form @submit.prevent="searchUsers">
      <input v-model="keyword" type="text" placeholder="닉네임 또는 이메일 입력">
      <button class="search-button" type="submit">
        검색
      </button>
    </form>

    <!-- 검색 결과 컨테이너 -->
    <div class="search-results-container">
      <table  class="search-results" v-show="showTable">
        <thead>
        <tr v-show="showTableHeader">
          <th>닉네임</th>
          <th>이메일</th>
          <th>초대하기</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(user, index) in searchResults" :key="user.id" class="user-info">
          <td>{{ user.nickname }}</td>
          <td>{{ user.email }}</td>
          <td>
            <button class="invite-button" @click="inviteUser(user.nickname)">
              초대
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import {  findUser  } from '@/api/index.js';

export default {
  data() {
    return {
      keyword: "",
      searchResults: [],
      isLoading: false,
      showTableHeader:false,
      showTable:false,
    };
  },
  computed:{
    showTable(){
      return this.searchResults.length > 0 || this.isLoading;
    },
  },
  methods: {
    async searchUsers() {
      try {
        const response = await findUser(this.keyword);
        this.searchResults = response.data;
        this.isLoading = false;

        this.showTableHeader = this.searchResults.length > 0;
      } catch (error) {
        console.error("검색 오류:", error);
        alert("검색 결과가 없습니다. 검색어를 다시 확인해주세요.");
        this.isLoading = false;
      }
    },
    async inviteUser(username) {
      try {
        // 초대 요청 로직
      } catch (error) {
        console.error("초대 오류:", error);
      }
    },
  },
};
</script>

<style scoped>
th, td {
  border: none;
}
.search-results-container {
  max-width: 500px;
  max-height: 200px;
  overflow-y: scroll;
  margin: 0 auto; /* 수평 가운데 정렬 */
}

.search-results {
  width: 100%;
  border-collapse: collapse;
}

.search-results td {
  padding: 8px;
}
.search-button{
  font-size: 14px;
  width: 40px;
  height: 27px;
  display: inline-block;
  border-radius: 10px;
  line-height: 30px;
  cursor: pointer;
  text-align: center;
  border: none;
  background-color: #EAEAEA;
}

.invite-button {
  font-size: 14px;
  width: 40px;
  height: 27px;
  display: inline-block;
  border-radius: 10px;
  line-height: 30px;
  cursor: pointer;
  text-align: center;
  border: none;
  background-color: #C4DFFF;
}
</style>
