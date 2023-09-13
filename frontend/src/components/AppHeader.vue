<template>
    <header>
        <div class="content">
            <div class="header_content">
                <div class="logo">
                    <router-link to="/">어디갈래?</router-link>
                </div>
                <div class="user_info">
                    <router-link to="/" v-if="isLogin">알림</router-link>
                    <router-link to="/my-page" v-if="isLogin">마이페이지</router-link>
                    <router-link to="/login" v-if="!isLogin">로그인</router-link>
                    <router-link to="/logout"  v-if="isLogin">로그아웃</router-link>
                    <router-link to="/sign-up" v-if="!isLogin">회원가입</router-link>
                </div>
            </div>
        </div>
        <div class="content mb-3">
            <nav>
                <div class="nav_content">
                    <router-link to="/items-list" :class="{'selected': isItemPage}">여행정보</router-link>
                    <router-link to="/schedules/write" :class="{'selected': isSchedulePage}">일정짜기</router-link>
                    <router-link to="/board-list" :class="{'selected': isBoardPage}">커뮤니티</router-link>
                    <router-link to="/map" >여행지도</router-link>
                </div>
                <div>
                    <div class="totalSearch">
                        <form>
                            <input type="text" id="header_keyword">
                            <!-- 검색 버튼 -->
                            <button>
                               <v-img src="../assets/images/icons/u_search.png" alt=""/>
                            </button>
                        </form>
                    </div>
                </div>
            </nav>
        </div>
    </header>
</template>

<script>
export default {
    name: "Header",
    data() {
        return {
          active: 'selected'
        }
    },
    computed: {
        isItemPage() {
            let checked = false
            if (this.$route.path === '/items') {
                checked = true
            }
            return checked;
        },
        isSchedulePage() {
            let checked = false
            if (this.$route.name === 'MakeScheduleDetail' || this.$route.name === 'MakeSchedule') {
                checked = true
            }
            return checked
        },
        isBoardPage() {
            let checked = false
            if (this.$route.name === 'BoardCreate') {
                checked = true
            }
            return checked
        },
        isLogin() {
            return this.$store.getters.isLogin
        },
    },
    methods: {
        logout() {
            this.$store.commit('logout');
            console.log("logout", this.$store.get(state.token))
            this.$router.push('/');
        }

    },

}
</script>

<style scoped>
  header {
      padding: 16px 0 12px;
      /*background-color: #99C7FF;*/
  }

  .header_content {
      display: flex;
      justify-content: space-between;
      margin-bottom: 15px;
      height: 50px;
  }

  .logo {
      font-size: 24px;
      font-weight: bold;
      line-height: 50px;
  }

  .user_info {
      display: flex;
      align-items: center;
  }

  .user_info a {
      line-height: 18px;
      padding: 0 10px 0 24px;
      font-size: 14px;
  }

  nav {
      width: 100%;
      height: 36px;
      display: flex;
      justify-content: space-between;
  }

  .nav_content a {
      align-items: center;
      margin: 0 57px 0 0;
      font-size: 16px;
      line-height: 36px;
  }

  .totalSearch {
      width: 420px;
      height: 36px;
      border: 1px solid #F2F2F2;
      background-color: #F2F2F2;
      border-radius: 18px;
  }

  .totalSearch:focus-within {
      background-color: white;
  }

  .totalSearch form {
      display: flex;
      align-items: center;
      box-sizing: border-box;
      padding: 5px 16px;
      width: 100%;
      justify-content: space-between;
  }

  .totalSearch input {
      width: 338px;
      height: 22px;
      padding: 0 8px;
      font-size: 16px;
      background-color: transparent;
      border: none;
  }

  .totalSearch button {
      width: 23px;
      height: 23px;
      border: none;
      padding: 0;
      display: inline-block;
      vertical-align: top;
      background-color: transparent;
      cursor: pointer;
  }

  .totalSearch button img {
      width: 100%;
      height: 100%;
  }

  .selected {
      font-weight: bold;
      color: #99C7FF;
  }

</style>