<template>
    <v-container class="signUp-container" style="max-width: 500px">
        <v-layout align-center row wrap>
            <v-col cols="12" class="vertical-center">
                <v-alert v-if="isError" type="error">
                    {{ errorMsg }}
                </v-alert>
                <v-card>
                    <v-toolbar color=#C4DFFF style="text-align: center;">
                        <v-toolbar-title style="text-align: center; font-size: 20px; color: #FFFFFF">
                            회 원 가 입
                        </v-toolbar-title>
                    </v-toolbar>
                    <div class="pa-5 mt-3">
                        <v-form ref="form" v-model="valid" lazy-validation style="min-width: 400px">
                            <v-text-field
                                    v-model="formData.email"
                                    label="e-mail"
                                    :rules="emailRules"
                                    :error-messages="emailErrors"
                                    required
                                    @input="checkDuplicateEmail"
                            ></v-text-field>
                            <v-text-field
                                    v-model="formData.nickname"
                                    label="nickname"
                                    :counter="12"
                                    :rules="nameRules"
                                    :error-messages="nicknameErrors"
                                    required
                                    @input="checkDuplicateNickname"
                            ></v-text-field>

                            <v-text-field
                                    v-model="formData.password"
                                    :type="show ? 'text' : 'password'"
                                    label="password"
                                    counter="8"
                                    :rules="[rules.required, rules.min, rules.max, rules.password]"
                            ></v-text-field>

                            <v-text-field
                                    v-model="formData.passwordCheck"
                                    :type="show ? 'text' : 'password'"
                                    label="password check"
                                    counter="8"
                                    :rules="[passwordConfirmationRule]"
                            ></v-text-field>
                            <div class="d-flex flex-row align-center font-adjust">
                                <div class="ml-2 flex-row align-center">
                                    <v-icon
                                            size="19"
                                            color="grey"
                                            :class="show ? 'mdi mdi-eye' : 'mdi mdi-eye-off'"
                                            @click="show = !show"
                                    ></v-icon>
                                    <span class="ml-1">비밀번호 보기</span>
                                </div>
                                <div class="ml-auto mr-1">
                                    <v-btn flat
                                           depressed
                                           large
                                           block
                                           class="mb-1"
                                           @click="reset"
                                    >지우기
                                    </v-btn>
                                </div>
                            </div>
                            <v-btn flat
                                   color="#C4DFFF"
                                   depressed
                                   large
                                   block
                                   class="mt-4 mb-4"
                                   @click="register(formData)"
                                   style="font-size: 17px; color: #FFFFFF;"
                            >가입하기
                            </v-btn>
                        </v-form>
                    </div>
                </v-card>
            </v-col>
        </v-layout>
    </v-container>
</template>

<script>
import RegisterObj from "../models/registerObj"

export default {
    name: "signUp",
    data: () => ({
            formData: new RegisterObj("", "", "", ""),
            valid: false,
            isEmailUnique: undefined,
            isNicknameUnique: undefined,
            isError: false,
            errorMsg: "",
            emailErrors: [],
            nicknameErrors: [],
            show: false,
            rules: {
                required: (value) => !!value || "비밀번호를 입력해주세요.",
                password: v => !!(v || '').match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(_|[!@#$%^&])).+$/) ||
                    "영문 대소문자와 숫자, 특수기호(!@#$%^&)를 포함해주세요.",
                min: (v) => v.length >= 8 || "8자리 이상으로 작성해주세요.",
                max: (v) => v.length <= 20 || "20자리 이하로 작성해주세요.",
            }
        }
    ),

    computed: {
        passwordConfirmationRule() {
            return () =>
                this.formData.password === this.formData.passwordCheck || "비밀번호가 일치하지 않습니다.";
            console.log("비밀번호 일치:" + this.formData.password === this.formData.passwordCheck)
        },
        emailRules() {
            return [
                (v) => !!v || "e-mail을 입력해주세요.",
                (v) => /.+@.+\..+/.test(v) || "e-mail 형식을 확인해주세요.",
            ];
        },
        nameRules() {
            return [
                (v) => !!v || "nickname을 입력해주세요.",
                (v) => (v && v.length <= 12) || "열두글자 이하로 작성해주세요."
            ];
        },
    },

    methods: {
        goToMain() {
            console.log("goToMain: login 페이지 이동")
            this.$router.push({
                name: "Login"
            })
        }
        ,
        register(RegisterObj) {
            // 폼 입력 확인
            if ( // null, undefined, false, 빈문자열인 경우
                !this.formData.email ||
                !this.formData.nickname ||
                !this.formData.password ||
                !this.formData.passwordCheck
            ) { // 에러메시지
                this.isError = true
                this.errorMsg = "이메일, 닉네임, 비밀번호를 모두 입력해주세요."
                return
            }
            // 폼이 모두 입력 되었고, vaild가 true면 제출
            this.validate()
            if (this.valid) {
                console.log("vaild:", this.valid)
                this.axios.post("/users/register", RegisterObj)
                    .then(() => {
                        alert("회원가입이 완료되었습니다.")
                        this.goToMain()
                    })
                    .catch((err) => {
                        if (err.response) {
                            this.isError = true
                            this.errorMsg = err.response.data.message
                        }
                    });
            }
        }
        ,
// 이메일 중복 확인
        async checkDuplicateEmail() {
            try {
                const response = await this.axios.post(`/users/check/email/${this.formData.email}`);
                if (response.data == false) {
                    this.isEmailUnique = true; // 중복시 true 반환, false일 때 unique
                    this.emailErrors = [];
                } else {
                    this.isEmailUnique = false; // 중복시 true 반환, false일 때 unique
                    this.emailErrors.push("이미 사용중인 email입니다.");
                }
                // console.log(`이메일: ${this.formData.email}, 중복 여부: ${response.data ? '중복' : '고유'}`);
            } catch (error) {
                console.error("email 중복확인 오류: " + error);
            }
            console.log(`isEmailUnique: ${this.isEmailUnique}`);
        }
        ,
// 닉네임 중복 확인
        async checkDuplicateNickname() {
            try {
                const response = await this.axios.post(`/users/check/nickname/${this.formData.nickname}`);
                if (response.data == false) {
                    this.isNicknameUnique = true; // 중복시 true 반환, false일 때 unique
                    this.nicknameErrors = [];
                } else {
                    this.isNicknameUnique = false; // 중복시 true 반환, false일 때 unique
                    this.nicknameErrors.push("이미 사용중인 이름입니다.");
                }
                // console.log(`nickname: ${this.formData.nickname}, 중복 여부: ${response.data ? '중복' : '고유'}`);
            } catch (error) {
                console.error("nickname 중복확인 오류: " + error);
            }
            console.log(`isNicknameUnique: ${this.isNicknameUnique}`);
        }
        ,
        validate() {
            this.$refs.form.validate().then((validation) => {
                console.log("check Valid: " + "isEmailUnique: " + this.isEmailUnique + ", isNicknameUnique: " + this.isNicknameUnique)
                console.log("validate:", validation)
                if (validation && this.isEmailUnique && this.isNicknameUnique) {
                    this.valid = true;
                } else {
                    this.valid = false;
                }
            });
        }
        ,
        reset() {
            this.$refs.form.reset()
            this.isError = false
        }
        // ,
        // resetValidation() {
        //     this.$refs.form.resetValidation()
        // }
    }
}
</script>
<style>

.align-center {
    align-items: center;
}

.font-adjust {
    font-size: 15px;
    color: #455A64;
}

</style>