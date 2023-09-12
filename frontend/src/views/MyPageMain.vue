<template>
    <main>
        <div class="content">
            <div class="sidebar_content">
                <div class="my_page_main_sidebar">
                    <div class="profile_information">
                        <Profile/>
                        <v-btn flat @click="goToInfo()">내 정보</v-btn>
                    </div>
                    <AppSideMenu v-for="sidebar in sidebarData" :key="sidebar"
                                 :sidebar-title="sidebar.sidebarTitle"
                                 :sidebar-list="sidebar.sidebarList"
                                 :sidebar-router="sidebar.sidebarRouter"
                                 :sidebar-active="sidebar.sidebarActive" class="mt-3"/>
                </div>
                <router-view></router-view>

            </div>
        </div>
    </main>

</template>

<script>
import AppSideMenu from "@/components/AppSideMenu.vue";
import Profile from "@/components/Profile.vue";

export default {
    name: "MyPageMain",
    components: {
        AppSideMenu,
        Profile
    },
    data() {
        return {
            sidebarData: [
                {
                    sidebarTitle: '내 정보',
                    sidebarList: ['회원정보 수정', '비밀번호 변경', '회원 탈퇴'],
                    sidebarRouter: ['/my-page/my-info/edit', '/my-page/my-info/password', '/my-page/my-info/delete']
                },
                {
                    sidebarTitle: '나의 여행',
                    sidebarList: ['메이트 초대 리스트', '관심등록 여행지'],
                    sidebarRouter: ['/my-page/my-trip/mate-invitation', '/my-page/my-trip/liked-items']
                },
                {
                    sidebarTitle: '나의 포스트',
                    sidebarList: ['내가 쓴 일정', '내가 쓴 게시글', '내가 쓴 후기', '내가 쓴 댓글'],
                    sidebarRouter: ['/my-page/my-post/schedules', '/my-page/my-post/boards', '/my-page/my-post/review', '/my-page/my-post/comments']
                }
            ],
        }
    },
    methods: {
        goToInfo() {
            console.log("내 정보 조회로 이동")
            this.$router.push({name: 'UserInfoEdit'})
        }
    }
}
</script>

<style scoped>
    .sidebar_content {
        display: flex;
        gap: 53px;
        min-height: 700px;
    }

.my_page_main_sidebar {
    width: 180px;
}

.profile_information {
    flex-direction: row;
    /*border: 1px solid #DADADA;*/
    /*border-radius: 6px;*/
    /*padding: 15px;*/
    min-height: 300px;
}

</style>