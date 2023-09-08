<template>
    <div class="sidebar_main">
        <div class="main_title">비밀번호 변경</div>
        <div class="my_page">
            <!-- 현재 비밀번호 확인 기존 비밀번호가 일치하면 display: none-->
            <div>
                <div class="my_page_password_form">
                    <div class="my_page_password_edit">
                        <div class="my_page_input_label">비밀번호</div>
                        <label>
                            <input type="password">
                            <div class="edit_warn">비밀번호가 일치하지 않습니다.</div>
                        </label>
                    </div>
                    <div class="my_page_button_wrap">
                        <button class="my_page_button">비밀번호 확인</button>
                    </div>
                </div>
            </div>
            <!-- 새로운 비밀번호 기존 비밀번호가 일치하면 display: block-->
            <div>
                <div class="my_page_new_password_form">
                    <div class="my_page_password_edit">
                        <div class="my_page_input_label">새 비밀번호</div>
                        <label>
                            <input type="password">
                            <div class="edit_warn">사용할 수 없는 비밀번호입니다.</div>
                        </label>
                    </div>
                    <div class="my_page_password_edit">
                        <div class="my_page_input_label">새 비밀번호 확인</div>
                        <label>
                            <input type="password">
                            <div class="edit_warn">비밀번호가 일치하지 않습니다.</div>
                        </label>
                    </div>
                    <div class="my_page_button_wrap">
                        <button class="my_page_button">비밀번호 변경</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

export default {
    name: "Password",
}
</script>

<style scoped>

    .main_title {
        font-size: 22px;
        font-weight: 700;
        text-align: left;
        padding-bottom: 20px;
    }

    .my_page_new_password_form {
        display: flex;
        flex-direction: column;
        gap: 30px;
    }

    .my_page_password_edit {
        display: flex;
        gap: 40px;
    }

    .my_page_input_label {
        font-size: 16px;
        font-weight: bold;
        margin-top: 12px;
        width: 118px;
        text-align: left;
    }

    .my_page_password_edit input {
        width: 450px;
        font-size: 16px;
        border-radius: 8px;
        border: 2px solid #DADADA;
        padding: 5px 9px 5px;
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