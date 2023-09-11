<template>
    <main>
        <div class="content">
            <div class="sidebar_content">
                <AppSideMenu :sidebar-title="sidebarTitle" :sidebar-list="sidebarList" :sidebar-router="sidebarRouter" :sidebar-active="sidebarActive"/>
                <div class="sidebar_main">
                    <div class="main_title">회원 탈퇴</div>
                    <div class="my_page">
                        <div class="my_page_delete_text">
                            <div>
                                <p>회원 탈퇴 신청하기 전에 확인해 주세요.</p>
                                <p>회원 탈퇴 시 게시물 관리</p>
                                <div class="delete_check_message">
                                    회원탈퇴 후 <span>'어디갈래?'</span> 서비스에 입력한 게시물 및 댓글은 삭제되지 않으며, 회원정보 삭제로 인해 <br>
                                    작성자 본인을 확인할 수 없으므로 게시물 편집 및 삭제 처리가 원천적으로 불가능 합니다. <br>
                                    게시물 삭제를 원하시는 경우에는 먼저 해당 게시물을 삭제 하신 후, 탈퇴를 신청하시기 바랍니다.
                                </div>
                            </div>
                            <div class="my_page_button_wrap">
                                <button class="my_page_button">회원 탈퇴</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>

<script>
import AppSideMenu from "@/components/AppSideMenu.vue";

export default {
    name: "MyPageDelete",
    components: {
        AppSideMenu
    },
    data() {
        return {
            sidebarTitle: '내 정보',
            sidebarList: ['회원정보 수정', '비밀번호 변경', '회원 탈퇴'],
            sidebarRouter: ['/myPage/edit', '/myPage/password', '/myPage/delete'],
            sidebarActive: [false, false, true]
        }
    }
}
</script>

<style scoped>
    .sidebar_content {
        display: flex;
        gap: 95px;
    }

    .main_title {
        font-size: 22px;
        font-weight: 700;
        text-align: left;
        padding-bottom: 20px;
    }

    .my_page_delete_text p {
        margin-bottom: 20px;
        font-weight: bold;
        font-size: 18px;
        text-align: left;
    }

    .delete_check_message {
        width: 519px;
        line-height: 25px;
        font-size: 16px;
        text-align: left;
    }

    .delete_check_message span {
        font-weight: bold;
    }

    .my_page_button_wrap {
        text-align: center;
    }

    .my_page_button {
        border: none;
        margin: 50px auto 30px;
        width: 198px;
        height: 42px;
        display: inline-block;
        background-color: #99C7FF;
        font-size: 18px;
        font-weight: bold;
        color: #FFF;
        cursor: pointer;
    }
</style>