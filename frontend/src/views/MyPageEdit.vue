<template>
    <main>
        <div class="content">
            <div class="sidebar_content">
                <AppSideMenu :sidebar-title="sidebarTitle" :sidebar-list="sidebarList" :sidebar-router="sidebarRouter" :sidebar-active="sidebarActive"/>
                <div class="sidebar_main">
                    <div class="main_title">회원정보 수정</div>
                    <div class="my_page">
                        <div class="my_page_profile_image">
                            <v-img src="@/assets/images/site_1.jpg" width="120px" height="120px" class="rounded-circle" inline="true"></v-img>
                            <label for="file" class="my_page_profile_change">프로필 사진 바꾸기</label>
                            <div class="my_page_profile_delete">프로필 사진 삭제</div> <!-- 필요없으면 지우기 -->
                            <input type="file" name="file" id="file"> <!-- 수정 -->
                        </div>
                        <div class="my_page_profile_form">
                            <!-- vuetify 쓰실거면 여기 form에서 -->
                            <div class="my_page_edit">
                                <div class="my_page_input_label">이메일</div>
                                <div class="readonly_data">abc@gmail.com</div>
                            </div>
                            <div class="my_page_edit">
                                <div class="my_page_input_label">닉네임</div>
                                <label>
                                    <input type="text" value="닉네임">
                                    <!-- 닉네임 경고 메세지 -->
                                    <div class="edit_warn">사용중인 닉네임입니다.</div>
                                </label>
                            </div>
                            <div class="my_page_edit">
                                <div class="my_page_input_label introduce">프로필 소개</div>
                                <label>
                                    <textarea name="introduce"></textarea>
                                </label>
                            </div>
                            <div class="my_page_button_wrap">
                                <button class="my_page_button">회원정보 수정</button>
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
    name: "MyPageEdit",
    components: {
        AppSideMenu
    },
    data() {
        return {
            sidebarTitle: '내 정보',
            sidebarList: ['회원정보 수정', '비밀번호 변경', '회원 탈퇴'],
            sidebarRouter: ['/myPage/edit', '/myPage/password', '/myPage/delete'],
            sidebarActive: [true, false, false]
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

    .my_page_profile_image {
        text-align: left;
    }

    .my_page_profile_change {
        display: inline-block;
        font-size: 16px;
        cursor: pointer;
    }

    .my_page_profile_delete {
        display: inline-block;
        font-size: 16px;
        cursor: pointer;
        color: #D3728E;
        margin-left: 14px;
    }

    .my_page_profile_image input {
        display: none;
    }

    .my_page_profile_form {
        margin-top: 28px;
        text-align: left;
    }

    .my_page_edit {
        margin-bottom: 28px;
        display: flex;
        gap: 40px;
    }

    .my_page_input_label {
        font-size: 16px;
        font-weight: bold;
        display: inline-block;
        width: 78px;
        margin-top: 12px;
    }

    .readonly_data {
        font-size: 16px;
        display: inline-block;
        margin-top: 12px;
    }

    .my_page_edit input {
        width: 480px;
        font-size: 16px;
        border-radius: 8px;
        border: 2px solid #DADADA;
        padding: 5px 9px 5px;
    }

    .my_page_edit textarea {
        width: 480px;
        font-size: 16px;
        border-radius: 8px;
        border: 2px solid #DADADA;
        resize: none;
        height: 100px;
        line-height: 20px;
    }

    .edit_warn {
        font-size: 15px;
        color: #D3728E;
        height: 15px;
        margin-top: 9px;
        text-align: left;
    }

    .my_page_button_wrap {
        text-align: center;
    }

    .my_page_button {
        border: none;
        margin: 80px auto 30px;
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