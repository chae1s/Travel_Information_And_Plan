<!--mypage에 노출될 프로필-->
<template>
    <div class="profile">
        <div class="profile-image">
            <v-img :src="profileImage" alt="프로필 이미지"/>
        </div>
        <h6 class="mb-2">{{ nickname }}</h6>
        <v-btn flat @click="goToInfo()">회원정보 조회</v-btn>
    </div>
</template>

<script>
export default {
    name: "Profile",
    data() {
        return {
            id: '',
            email: '',
            nickname: '닉네임 표시될 것',
            profileImage: '/img/default-profile.png',
            // isLikedByme:''
        }
    },
    methods: {
        goToInfo() {
            console.log("내 정보 조회로 이동")
            this.$router.push({name: 'MyInfoView'})
        },
    }
}
</script>

<style scoped>
.profile {
    flex-direction: row;
    border: 1px solid #ccc;
    border-radius: 6px;
    padding: 15px;
    min-height: 200px;
}
/*.profile-image {*/
/*    !*width: 150px; !* 원의 지름 크기 설정 *!*!*/
/*    !*height: 150px; !* 원의 지름 크기 설정 *!*!*/
/*    border: 1px solid #ccc;*/
/*    border-radius: 50%; !* 원형 모양을 만듭니다. *!*/
/*    overflow: hidden; !* 이미지를 원형에 맞게 자르기 위해 필요합니다. *!*/
/*    margin: 0 auto;*/
/*}*/

</style>