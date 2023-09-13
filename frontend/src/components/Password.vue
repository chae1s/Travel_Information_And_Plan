<template>
    <div class="sidebar_main">
        <v-alert v-if="isError" type="error">
            {{ errorMsg }}
        </v-alert>
        <div class="main_title">비밀번호 변경</div>
        <div class="my_page">
            <v-form ref="form" class="my_page_password_form" v-model="valid" lazy-validation>
                <div class="my_page_password_edit mb-8">
                    <div class="my_page_input_label">비밀번호</div>
                    <label>
                        <v-text-field class="v-text-field-custom"
                                      v-model="formData.currentPassword"
                                      variant="outlined"
                                      :type="show ? 'text' : 'password'"
                                      label="password"
                                      counter="8"
                                      required
                        ></v-text-field>
                    </label>
                </div>
                <div>
                    <div class="my_page_new_password_form">
                        <div class="my_page_password_edit">
                            <div class="my_page_input_label">새 비밀번호</div>
                            <label>
                                <v-text-field class="v-text-field-custom"
                                              v-model="formData.password"
                                              variant="outlined"
                                              :type="show ? 'text' : 'password'"
                                              label="password"
                                              counter="8"
                                              :rules="[rules.min, rules.max, rules.password, isNewPassword]"
                                ></v-text-field>
                            </label>
                        </div>
                        <div class="my_page_password_edit">
                            <div class="my_page_input_label">새 비밀번호 확인</div>
                            <label>
                                <v-text-field class="v-text-field-custom"
                                              v-model="formData.passwordCheck"
                                              variant="outlined"
                                              :type="show ? 'text' : 'password'"
                                              label="password check"
                                              counter="8"
                                              :rules="[passwordConfirmationRule]"
                                ></v-text-field>
                            </label>
                        </div>
                        <div class="d-flex flex-row align-center font-adjust">
                            <v-icon
                                    size="19"
                                    color="grey"
                                    :class="show ? 'mdi mdi-eye' : 'mdi mdi-eye-off'"
                                    @click="show = !show"
                            ></v-icon>
                            <span class="ml-1" @click="show = !show">비밀번호 보기</span>
                            <div class="ml-auto mr-1">
                                <v-btn flat
                                       depressed
                                       large
                                       block
                                       class="mb-1"
                                       @click="initForm"
                                >지우기
                                </v-btn>
                            </div>
                        </div>
                        <div class="my_page_button_wrap">
                            <v-btn flat class="my_page_button" @click="editPassword">비밀번호 변경</v-btn>
                        </div>
                    </div>
                </div>
            </v-form>
        </div>
    </div>
</template>

<script>

import {updateUserPassword} from "@/api";

export default {
    name: "Password",
    data() {
        return {
            formData: {
                currentPassword: '',
                password: '',
                passwordCheck: ''
            },
            show: false,
            valid: false,
            isError: false,
            errorMsg: "",
            rules: {
                password: v => !!(v || '').match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(_|[!@#$%^&])).+$/) ||
                    "영문 대소문자와 숫자, 특수기호(!@#$%^&)를 포함해주세요.",
                min: (v) => v.length >= 8 || "8자리 이상으로 작성해주세요.",
                max: (v) => v.length <= 20 || "20자리 이하로 작성해주세요.",
            }
        }
    },
    computed: {
        passwordConfirmationRule() {
            return () =>
                this.formData.password === this.formData.passwordCheck || "비밀번호가 일치하지 않습니다.";
            console.log("비밀번호 일치:" + this.formData.password === this.formData.passwordCheck)
        },
        isNewPassword() {
            return () =>
                this.formData.currentPassword != this.formData.password || "기존 비밀번호와 동일합니다.";
            console.log("비밀번호 일치:" + this.formData.password === this.formData.passwordCheck)
        },
    },
    methods: {
        async editPassword() {
            if (!this.formData.currentPassword || !this.formData.password || !this.formData.passwordCheck) {
                this.isError = true
                this.errorMsg = "모든 값을 입력해주세요."
                return
            }
            this.validate()
            console.log("valid:", this.valid)
            if (this.valid) {
                try {
                    const {data} = await updateUserPassword(this.formData);
                    console.log(data)
                    alert(data.message);
                    this.goToLogin();
                } catch (error) {
                    if (error.response) {
                        console.log(error.response.data);
                        this.isError = true;
                        this.errorMsg = error.response.data.message;
                    } else {
                        console.log("응답 error 없음")
                    }
                }
            }
        },
        goToLogin() {
            this.$router.push({name: "Login"})
        },
        validate() {
            this.$refs.form.validate().then((validation) => {
                console.log("validate:", validation)
                if (validation && this.isNicknameUnique) {
                    this.valid = true;
                } else {
                    this.valid = false;
                }
            });
        },
        initForm() {
            this.formData.currentPassword = '';
            this.formData.password = '';
            this.formData.passwordCheck = '';
            this.isError = '';
            this.valid = false;
        }
        ,
    },
}
</script>

<style scoped>
.sidebar_main {
    min-width: 500px;
    /*background-color: #42b983;*/

}

.v-text-field-custom {
    min-width: 400px;
}


.main_title {
    font-size: 22px;
    font-weight: 700;
    text-align: left;
    padding-bottom: 50px;
}

.my_page_new_password_form {
    display: flex;
    flex-direction: column;
    gap: 30px;
    /*background-color: #E5F1FF;*/
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

.font-adjust {
    font-size: 15px;
    color: #455A64;
}

</style>