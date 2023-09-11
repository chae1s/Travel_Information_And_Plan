<template>
    <v-container>
        <div class="content">
            <div class="mypage-container">
                <div class="left-content">
                    <div class="profile"> 프로필</div>
                    <div class="myinfo clickable" @click="goToInfo()">회원정보</div>
                    <nav>
                        <div class="nav_content mt-5">
                            <h4>나의 여행</h4>
                            <div class="invitation clickable" @click="goToInvitation()">메이트 초대 리스트</div>
                            <h4>하위메뉴</h4>
                        </div>
                    </nav>
                </div>

                <div class="right-content">
                    <div class="likes-item">
                        즐겨찾기
                    </div>
                    <div class="main-content">
                        <router-view></router-view>
                    </div>
                </div>
            </div>
        </div>
    </v-container>
</template>

<script>
import InvitationList from "@/components/InvitationList.vue";

export default {
    components: {
        InvitationList
    },
    methods: {
        goToInfo() {
            console.log("내 정보 조회로 이동")
            this.$router.push({name:'MyInfoView'})
        },
        goToInvitation() {
            console.log("메이트 초대 리스트로 이동")
            this.$router.push({name:'InvitationList'})

        }
    }
}

</script>
<style scoped>
h4 {
    margin-top: 12px;
}

.mypage-container {
    height: 100%;
    min-height: 600px;
    display: flex;
    flex-direction: row;
}

.profile {
    height: 200px;
}

.left-content {
    flex: 0 0 300px;
    flex-direction: column;
    background-color: #f2f2f2;
    align-items: center;
}
.nav_content{
    text-align: left;
    margin-left: 30px;
}

.right-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    background-color: #cccccc;
}
.clickable {
    cursor: pointer; /* 손가락 모양으로 커서 설정 */
    font-size: 14px;
}


</style>