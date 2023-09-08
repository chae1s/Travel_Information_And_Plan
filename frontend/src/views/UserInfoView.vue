<template>
    <v-container>
        <div class="content">
            <div class="userInfo-container">
                <div class="left-content">
                    <h3>내 정보</h3>
                    <nav>
                        <div class="nav_content mt-5">
                            <router-link to="/my-info/update" active-class="active">회원정보 수정</router-link>
                            <router-link to="/sdas" active-class="active">비밀정보 수정</router-link>
<!--                            <router-link to="" active-class="active">회원탈퇴</router-link>-->
                        </div>
                    </nav>
                </div>
                <div class="router-area">
                    <router-view></router-view>
                </div>
            </div>
        </div>
    </v-container>
</template>

<script>
export default {
    name: "UserInfoView"
}
</script>

<style scoped>


.userInfo-container{
    height: 100%;
    min-height: 600px;
    display: flex;
    flex-direction: row;
}


.active {
    font-weight: bold; /* 진한 글꼴 */
    color: #000; /* 원하는 색상 */
}

.left-content {
    flex: 0 0 180px;
    flex-direction: column;
    background-color: #f2f2f2;
    justify-content: center;
    align-items: center;
}

.nav_content a {
    display: block; /* <a> 요소를 블록 요소로 변경 */
    margin-bottom: 6px;
    margin-right: 10px;
    text-align: right;

}
</style>