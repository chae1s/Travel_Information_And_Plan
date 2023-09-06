<template>
    <v-container class="signUp-container" style="max-width: 500px">
        <v-layout align-center row wrap>
            <v-flex xs12>
                <v-alert v-if="isError" type="error">
                    {{ errorMsg }}
                </v-alert>
                <v-card>
                    <v-toolbar flat color=#C4DFFF>
                        <v-toolbar-title
                        ><span class="white--text">회원가입</span></v-toolbar-title
                        >
                    </v-toolbar>
                    <div class="pa-5">
                        <v-form ref="form" v-model="valid" lazy-validation style="min-width: 400px">
                            <v-text-field
                                    v-model="formData.email"
                                    :rules="emailRules"
                                    label="e-mail"
                                    class="text-field"
                                    required
                            ></v-text-field>

                            <v-text-field
                                    v-model="formData.nickname"
                                    :counter="12"
                                    :rules="nameRules"
                                    label="nickname"
                                    class="text-field"
                                    required
                            ></v-text-field>

                            <v-text-field
                                    v-model="formData.password"
                                    :rules="[rules.required, rules.min, rules.valid]"
                                    :type="show ? 'text' : 'password'"
                                    label="password"
                                    class="text-field"
                                    hint="영문 대,소문자와 숫자, 특수기호(!@#$%^&)를 포함"
                                    counter="8"
                                    :append-inner-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                                    @click:append="show = !show"
                            ></v-text-field>

                            <v-text-field
                                    v-model="chkPassword"
                                    :rules="[rules.required, rules.check]"
                                    :type="show ? 'text' : 'password'"
                                    label="password check"
                                    class="text-field"
                                    hint="비밀번호를 다시한번 입력해주세요."
                                    counter="8"
                                    :append-inner-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                                    @click:append="show = !show"
                            ></v-text-field>

                            <h6 v-if="chkPassword && !sameChk(chkPassword)" class="mb-5 teal--text accent-3">
                                비밀번호가 일치하지 않습니다.
                            </h6>
                            <div class="mt-3 d-flex flex-row-reverse">
                                <v-btn
                                        :disabled="!valid"
                                        color="blue"
                                        class="mr-4"
                                        @click="register(formData)"
                                >회원가입
                                </v-btn>
                                <v-btn color="pink" class="mr-4" @click="reset"> 지우기</v-btn>
                            </div>
                        </v-form>
                    </div>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
import RegisterObj from "../models/registerObj"
import axios from 'axios'

export default {
    name: 'signUp',
    data: () => ({
            formData: new RegisterObj("", "", ""),
            valid: false,
            chkPassword: undefined,
            isError: false,
            errorMsg: "",
            show: false,
            nameRules: [
                (v) => !!v || "nickname을 입력해주세요.",
                (v) => (v && v.length <= 12) || "열두글자 이하로 작성해주세요."
            ],
            rules: {
                required: (value) => !!value || "비밀번호를 입력해주세요.",
                valid: v => !!(v || '').match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(_|[!@#$%^&])).+$/) ||
                    "영문 대,소문자와 숫자, 특수기호(!@#$%^&)를 포함하여 작성해주세요.",
                min: (v) => v.length >= 8 || "8자리 이상으로 작성해주세요."
            }
        }
    ),

    methods: {
        goToMain() {
            this.$router.push({
                name: "login"
            })
        }
        ,
        // password와 check 입력 일치하는지 확인
        sameChk(passwordChk) {
            this.formData.password == passwordChk
        }
        ,
        register(RegisterObj) {
            if ( // null, undefined, false, 빈문자열인 경우
                !this.formData.email ||
                !this.formData.nickname ||
                !this.formData.password
            ) { // 에러메시지
                this.isError = true
                this.errorMsg = "이메일과 닉네임과 비밀번호를 모두 입력해주세요."
                return
            }

            // 폼이 모두 입력 되었다면 제출
            axios
                .post("/users/register", RegisterObj)
                .then(() => {
                    this.goToMain()
                })
                .catch((err) => {
                    if (err.response) {
                        this.isError = true
                        this.errorMsg = err.response.data.message
                    }
                })
        }
        ,
        validate() {
            this.$refs.form.validate()
        }
        ,
        reset() {
            this.$refs.form.reset()
        }
        ,
        resetValidation() {
            this.$refs.form.resetValidation()
        }
    }
}
</script>
<style>

</style>