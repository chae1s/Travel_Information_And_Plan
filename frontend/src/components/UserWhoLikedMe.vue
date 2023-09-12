<template>
    <div class="likes-user-container">
                <span class="highlight-text"><strong>나를 즐겨찾기한 회원</strong></span>
        <!-- 목록 컨테이너-->
            <div class="mt-5" v-show="!showResultTable">나를 추가한 회원이 없습니다.</div>
            <div class="search-results-container">
                <table class="search-results" v-show="showResultTable">
                    <thead>
                    <th>닉네임</th>
                    <th>이메일</th>
                    <th>즐겨찾기</th>
                    </thead>
                    <tbody>
                    <tr v-for="(user, index) in this.userLikedMe" :key="user.id" class="user-info">
                        <td>{{ user.nickname }}</td>
                        <td>{{ user.email }}</td>
                        <td>
                            <button class="action-button" @click="likeUser(user.id, user)" v-if="!user.isLiked">
                                추가하기
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
import {readUserLikedMe, likeUser} from '@/api/index.js';

export default {
    name: "LikedMe",
    async created() {
        try {
            const response = await readUserLikedMe();
            this.userLikedMe = response.data;
            console.log(response)
        } catch (error) {
            console.log("조회 에러:", error.response.data)
        }
    },
    data() {
        return {
            userLikedMe: [],
            isLiked: false,
            isLoading: false,
        };
    },
    computed: {
        showResultTable() {
            return this.userLikedMe.length > 0
        },
    },
    methods: {
        async likeUser(userId, user) {
            try {
                console.log(`회원 \"${userId}.${user.nickname}\" 즐겨찾기`)
                console.log(`즐겨찾기 취소: isUnLiked ${user.isLiked}`)
                const {data} = await likeUser(userId);
                user.isLiked = true;
                console.log(data);

            } catch (error) {
                if (error.response && error.response.status === 400) {
                    alert("이미 즐겨찾기된 회원입니다.");
                    user.isLiked = true;
                } else {
                    alert(error.response.message)
                }
                console.error("즐겨찾기 오류:", error);
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
