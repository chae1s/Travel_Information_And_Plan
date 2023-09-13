<template>
    <div class="user-researcher-container">
        <span class="highlight-text"><strong>회원 검색하기</strong></span>
        <!-- 검색 폼 -->
        <form class="search-form mt-3 mb-5" @submit.prevent="searchUsers">
            <input v-model="keyword" type="text" placeholder="닉네임 또는 이메일 입력">
            <button class="search-button" type="submit">
                검색
            </button>
        </form>

        <!-- 검색 결과 컨테이너 -->
        <div class="search-results-container">
            <table class="search-results" v-show="showResultTable">
                <thead>
                <tr v-show="showTableHeader">
                    <th>닉네임</th>
                    <th>이메일</th>
                    <th>즐겨찾기</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(user, index) in searchResults" :key="user.id" class="user-info">
                    <td>{{ user.nickname }}</td>
                    <td>{{ user.email }}</td>
                    <td>
                        <button class="action-button" @click="likeUser(user.id, user)" v-if="!user.isLiked">
                            즐겨찾기
                        </button>
                        <div class="action-done" v-if="user.isLiked"> 추가됨</div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import {findUser, likeUser} from '@/api/index.js';

export default {
    name: "UserResearcher",
    data() {
        return {
            keyword: "",
            searchResults: [],
            isLoading: false,
            showTableHeader: false,
            showTable: false,
            isLiked: false
        };
    },
    computed: {
        showResultTable() {
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
                if (this.searchResults.length === 0) {
                    alert("검색 결과가 없습니다. 검색어를 다시 확인해주세요.");
                }
            } catch (error) {
                alert("검색 결과가 없습니다. 검색어를 다시 확인해주세요.");
                console.error("검색 오류:", error);
                this.isLoading = false;
            }
        },
        async likeUser(userId, user) {
            try {
                console.log(`회원 \"${userId}.${user.nickname}\" 즐겨찾기 추가`)
                console.log(`추가함: isLiked ${user.isLiked}`)
                const {data} = await likeUser(userId);
                user.isLiked = true;
                console.log(data);

            } catch (error) {
                if (error.response && error.response.status === 400) {
                    alert("이미 즐겨찾기 추가된 회원입니다.");
                    user.isLiked = true;
                } else {
                    alert(error.response.message)
                }
                console.error("즐겨찾기 추가 오류:", error);
            }
        },
    },
};
</script>

<style scoped>

th, td {
    text-align: center;
    border: none;
    padding: 3px 15px;
}

th {
    background-color: #FFFFFF;
    position: sticky;
    top: 0;
}

.highlight-text {
    font-size: 20px;
    background: linear-gradient(to top, #FFE866 50%, transparent 50%);
}

.search-results-container {
    margin-top: 20px;
    max-width: 600px;
    min-width: 500px;
    max-height: 300px;
    overflow-y: auto;
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

.search-button {
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

.action-button {
    font-size: 11px;
    color: #2F3438;
    width: 45px;
    height: 27px;
    display: inline-block;
    border-radius: 10px;
    line-height: 30px;
    cursor: pointer;
    text-align: center;
    border: none;
    background-color: #FAED7D;
}

.action-done {
    font-size: 11px;
    color: #2F3438;
    width: 45px;
    height: 27px;
    display: inline-block;
    border-radius: 10px;
    line-height: 30px;
    cursor: pointer;
    text-align: center;
    border: none;
    background-color: #E5F1FF;
}
</style>
