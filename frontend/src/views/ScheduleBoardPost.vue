<template>
    <main>
        <div class="content">
            <div class="sidebar_content">
                <div class="board_main_sidebar">
                    <AppSideMenu
                            :sidebar-title="sidebarData.sidebarTitle"
                            :sidebar-list="sidebarData.sidebarList"
                            :sidebar-router="sidebarData.sidebarRouter" class="mt-3"/>
                </div>
                <SchedulePost></SchedulePost>
            </div>
        </div>
    </main>
</template>

<script>
import SchedulePost from "@/components/SchedulePost.vue";
import AppSideMenu from "@/components/AppSideMenu.vue";

export default {
    name: "ScheduleBoardPost",
    components: {AppSideMenu, SchedulePost},
    data() {
        return {
            sidebarData:
                {
                    sidebarTitle: '커뮤니티',
                    sidebarList: ['여행 후기 게시판', '여행 일정 게시판'],
                    sidebarRouter: ['/schedule-list', '/schedule-list']
                },

        }
    },
}
</script>

<style scoped>
    .sidebar_content {
        display: flex;
        gap: 53px;
        min-height: 700px;
    }

    .board_main_sidebar {
        width: 180px;
    }
</style>