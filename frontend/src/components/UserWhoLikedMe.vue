<template>
    <div class="likes-user-to-container">
        <span class="highlight-text"><strong>나를 즐겨찾기한 회원</strong></span>
        <div class="user-list-container">
            <div class="mt-5" v-show="!showResultTable">나를 추가한 회원이 없습니다.</div>
            <table class="likes-to-list" v-show="showResultTable">
                <thead class="tread-style">
                <th>닉네임</th>
                <th>이메일</th>
                <th>즐겨찾기</th>
                </thead>
                <tbody>
                <tr v-for="(user, index) in this.userLikesByMe" :key="user.id" class="user-info">
                    <td>{{ user.nickname }}</td>
                    <td>{{ user.email }}</td>
                    <td>
                        <button class="action-button" @click="unLikeUser(user.id, user)" v-if="!user.isUnLiked">
                            취소하기
                        </button>
                        <div class="unLiked" v-if="user.isUnLiked"> 취소됨</div>
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
            this.userLikesByMe = response.data;
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

.highlight-text {
    font-size: 20px;
    background: linear-gradient(to top, #FFE866 50%, transparent 50%);
}

.likes-to-list {
    border-collapse: separate;
}

th, td {
    text-align: center;
    border: none;
    padding: 3px 15px;
}

/*th {*/
/*    background-color: #42b983;*/
/*}*/
/*td {*/
/*    background-color: #FFE866;*/
/*}*/



.likes-user-to-container {
    margin-top: 20px;
    max-width: 600px;
    min-width: 420px;
    max-height: 200px;
    overflow-y: scroll;
}

.likes-user-to-container::-webkit-scrollbar {
    width: 10px;
}

.likes-user-to-container::-webkit-scrollbar-thumb {
    background-color: #FFE866; /* 스크롤 막대의 색상 */
    border-radius: 10px;
}

.likes-user-to-container::-webkit-scrollbar-track {
    background-color: beige; /* 스크롤 막대의 색상 */
    border-radius: 10px;
}

.user-list-container {
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

.unLiked {
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
