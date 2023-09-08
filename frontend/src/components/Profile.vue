<template>
    <div class="profile">
        <div class="profile-image">
            <v-img :src="profileImage" alt="프로필 이미지"/>
        </div>
        <h5 class="mt-2 mb-2">{{ nickname }}</h5>
        <div class="profile-content">{{ content }}</div>
        <div class="profile-content">{{ location }}</div>
    </div>
</template>

<script>
import {readUserProfile} from "@/api";

export default {
    name: "Profile",
    async created() {
        try {
            const {data} = await readUserProfile();
            console.log(data)
            this.nickname = data.nickname;
            this.content = data.content;
            this.location = data.location;
            this.profileImage = data.profileImage;
        } catch (error) {
            console.log("조회 에러:", error.response.data)
        }
    },
    data() {
        return {
            nickname: 'nickname',
            content:'',
            location:'',
            profileImage: '/img/default-profile.png',
            // isLikedByMe:''
        }
    }
}
</script>

<style scoped>
.profile-content{
    font-size: 12px;
    margin: 2px;
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