<template>
    <main>
        <div class="content">
            <div class="sidebar_content">
                <div class="my_page_main_sidebar">
                    <div class="profile_information">
                        <div class="">프로필</div>
                        <!-- 프로필 사진 등 프로필 정보 -->
                        <router-link to="/myPage/main/edit">내 정보</router-link>
                    </div>
                    <AppSideMenu v-for="sidebar in sidebarData" :key="sidebar"
                        @profile-active="profileSelectedPage"
                        :sidebar-title="sidebar.sidebarTitle"
                        :sidebar-list="sidebar.sidebarList"
                        :sidebar-router="sidebar.sidebarRouter"
                        :sidebar-active="sidebar.sidebarActive" class="mt-3"/>
                </div>
                <div class="sidebar_main">
                    <router-view></router-view>


                </div>
            </div>
        </div>
    </main>

</template>

<script>
import AppSideMenu from "@/components/AppSideMenu.vue";

export default {
    name: "MyPageMain",
    components: {
        AppSideMenu
    },
    data() {
        return {
            sidebarData: [
                {
                    sidebarTitle: '내 정보',
                    sidebarList: ['회원정보 수정', '비밀번호 변경', '회원 탈퇴'],
                    sidebarRouter: ['/myPage/main/edit', '/myPage/main/password', '/myPage/main/delete']
                },
                {
                    sidebarTitle: '나의 여행',
                    sidebarList: ['메이트 초대 리스트', '관심등록 여행지'],
                    sidebarRouter: ['/', '/']
                },
                {
                    sidebarTitle: '나의 포스트',
                    sidebarList: ['내가 쓴 일정', '내가 쓴 게시글', '내가 쓴 후기', '내가 쓴 댓글'],
                    sidebarRouter: ['/', '/', '/', '/']
                }
            ],
        }
    },
}
</script>

<style scoped>
    .sidebar_content {
        display: flex;
        gap: 53px;
    }

    .my_page_main_sidebar {
        width: 180px;
    }

    .profile_information {
        border: 1px solid #DADADA;
    }

</style>