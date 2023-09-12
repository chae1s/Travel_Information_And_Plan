<!--현재 로그인한 유저의 프로필을 불러옴-->
<template>
    <div class="profile">
        <div class="profile-image">
            <v-img cover
                   :src="profileImage"
                   class="rounded-circle" alt="프로필 이미지"/>
        </div>
        <h5 class="mt-2 mb-2">{{ nickname }}</h5>
<!--        <div class="profile-content">{{ content }}</div>-->
<!--        <div class="profile-content">{{ location }}</div>-->
    </div>
</template>

<script>
import {readUserInfo} from "@/api";

export default {
    name: "Profile",
    async created() {
        try {
            const {data} = await readUserInfo();
            console.log(data)
            this.nickname = data.nickname;
            // this.content = data.content;
            // this.location = data.location;
            this.profileImage = data.profileImage;
        } catch (error) {
            console.log("조회 에러:", error.response.data)
        }
    },
    data() {
        return {
            nickname: 'nickname',
            // content: '',
            // location: '',
            profileImage: '/img/default-profile.png',
            // isLikedByMe:''
        }
    }
}
</script>

<style scoped>

.rounded-circle {
    display: block;
    max-width: 300px;
    min-width: 180px;
    min-height: 180px;
    border: 1px solid #ccc;
}

.profile-content {
    font-size: 12px;
    margin: 2px;
}

</style>