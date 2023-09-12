<template>
  <div>
    <span class="highlight-text"><strong>일정메이트 초대하기</strong></span>
    <!-- 검색 폼 -->
    <form @submit.prevent="searchUsers">
      <input v-model="keyword" type="text" placeholder="닉네임 또는 이메일 입력">
      <button class="search-button" type="submit">
        검색
      </button>
    </form>

    <!-- 검색 결과 컨테이너 -->
    <div class="search-results-container">
      <table  class="search-results" v-show="showResultTable">
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
            <button class="invite-button" @click="inviteUser(user.nickname,user)" v-if="!user.isInvited">
              초대
            </button>
            <div v-if="user.isInvited"> 초대완료</div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import {  findInvitationList, inviteUserToSchedule  } from '@/api/index.js';

export default {
  props:["scheduleId"],
  data() {
    return {
      keyword: "",
      searchResults: [],
      isLoading: false,
      showTableHeader:false,
      showTable:false,
      isInvited:false,

    };
  },
  computed:{
    showResultTable(){
      return this.searchResults.length > 0 || this.isLoading;
    },
  },
  methods: {
    async searchUsers() {
      try {
        const response = await findInvitationList(this.scheduleId,this.keyword);
        this.searchResults = response.data;
        this.isLoading = false;
        this.showTableHeader = this.searchResults.length > 0;
        if (this.searchResults.length===0)
          alert("검색 결과가 없습니다. 검색어를 다시 확인해주세요.");

      } catch (error) {
        console.error("검색 오류:", error);
        alert("검색 결과가 없습니다. 검색어를 다시 확인해주세요.");
        this.isLoading = false;
      }
    },
    async inviteUser(username,user) {
      try {
        console.log("username=" + username);
        console.log("scheduleId="+this.scheduleId);
        console.log("user.invited=" + user.isInvited);

        const response = await inviteUserToSchedule(this.scheduleId,username);
        alert("초대가 완료되었습니다.");
        user.isInvited=true;

      } catch (error) {
        if (error.response && error.response.status === 400) {
          alert("이미 초대된 유저입니다.");
          user.isInvited=true;
        } else {
          alert("오류가 발생했습니다."); // 다른 오류 처리
        }
        console.error("초대 과정 중 오류 발생:", error);
      }
    },


  },
};
</script>

<style scoped>

.highlight-text {

  font-size: 20px;
  background: linear-gradient(to top, #FFE866 50%, transparent 50%);
}
th, td {
  text-align: center;
  border: none;
}
.search-results-container {
  margin-top: 20px;
  max-width: 500px;
  max-height: 150px;
  overflow-y: scroll;
  //margin: 0 auto; /* 수평 가운데 정렬 */
}
.search-results-container::-webkit-scrollbar {
  width: 10px;
}

.search-results-container::-webkit-scrollbar-thumb {
  background-color: #FFE866; /* 스크롤 막대의 색상 */
  border-radius: 10px;
}
.search-results-container::-webkit-scrollbar-track {
  background-color: beige; /* 스크롤 막대의 색상 */
  border-radius: 10px;
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
  color: #2F3438;
  width: 40px;
  height: 27px;
  display: inline-block;
  border-radius: 10px;
  line-height: 30px;
  cursor: pointer;
  text-align: center;
  border: none;
  background-color: #FAED7D;
}
</style>
