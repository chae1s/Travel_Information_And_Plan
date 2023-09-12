<template>
    <v-container class="login-container" style="max-width: 500px">
        <v-layout align-center row wrap>
            <v-col cols="12" class="vertical-center">
                <v-card>
                    <v-toolbar color="#C4DFFF" dark>
                        <v-toolbar-title style="font-size: 20px; color: #FFFFFF">
                            L O G I N
                        </v-toolbar-title>
                    </v-toolbar>
                    <div class="pa-5 mt-3">
                        <v-form ref="form" style="min-width: 400px">
                            <v-text-field
                                    v-model="formData.email"
                                    label="e-mail"
                                    :rules="emailRules"
                                    required
                            ></v-text-field>

                            <v-text-field
                                    v-model="formData.password"
                                    :type="show ? 'text' : 'password'"
                                    :rules="[rules.required, rules.min, rules.max, rules.password]"
                                    label="password"
                                    required
                            ></v-text-field>
                            <div class="d-flex flex-row align-center font-adjust">
                                <div class="ml-2 flex-row align-center">
                                    <v-icon
                                            size="19"
                                            color="grey"
                                            :class="show ? 'mdi mdi-eye' : 'mdi mdi-eye-off'"
                                            @click="show = !show"
                                    ></v-icon>
                                    <span class="ml-1" @click="show = !show">비밀번호 보기</span>
                                </div>
                                <div class="ml-auto mr-1">
                                    <v-btn
                                            flat
                                            depressed
                                            large
                                            block
                                            class="mb-1"
                                            @click="initForm"
                                    >모두 지우기
                                    </v-btn>
                                </div>
                            </div>
                            <v-btn
                                    flat
                                    color="#C4DFFF"
                                    depressed
                                    large
                                    block
                                    @click="login"
                                    style="font-size: 17px; color: #FFFFFF;"
                            >로그인
                            </v-btn>
                            <div class="d-flex flex-row align-center font-adjust">
                                <div class="ml-2 flex-row align-center">
                                    <v-btn
                                            flat
                                            depressed
                                            large
                                            block
                                            class="ml-1"
                                            @click="goToSignUp"
                                    >회원가입
                                    </v-btn>
                                </div>
                                <div class="ml-auto mr-1">
                                    <v-btn
                                            flat
                                            depressed
                                            large
                                            block
                                            class="mb-1"
                                            @click="goToForgotPassword"
                                    >비밀번호 찾기
                                    </v-btn>
                                </div>
                            </div>
                        </v-form>
                    </div>
                </v-card>
            </v-col>
        </v-layout>
    </v-container>
</template>

<script>
import {loginUser} from '@/api/index';

export default {
    name: 'LoginView',

    data() {
        return {
            formData: {
                email: '',
                password: '',
            },
            show: false,
            rules: {
                required: (value) => !!value || "비밀번호를 입력해주세요.",
                password: v => !!(v || '').match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(_|[!@#$%^&])).+$/) ||
                    "영문 대소문자와 숫자, 특수기호(!@#$%^&)를 포함해주세요.",
                min: (v) => v.length >= 8 || "8자리 이상으로 작성해주세요.",
                max: (v) => v.length <= 20 || "20자리 이하로 작성해주세요.",
            }
        }
    },
    computed: {
        emailRules() {
            return [
                (v) => !!v || "e-mail을 입력해주세요.",
                (v) => /.+@.+\..+/.test(v) || "e-mail 형식을 확인해주세요.",
            ];
        },
    },
        methods: {
        async login() {
            if (!this.formData.email || !this.formData.password) {
                alert("email과 비밀번호를 모두 입력해주세요.")
                return
            }
            try {
                const userData = {
                    email: this.formData.email,
                    password: this.formData.password,
                };
                const {data} = await loginUser(userData);
                this.token = data.token;
                this.$store.commit('setToken', this.token);
                // console.log("$store.state:", this.$store.state);
                this.$router.push("/");
            } catch (error) {
                alert(error.response.data.message)
                console.log(error.response.data);

            }
        },
        initForm() {
            this.formData.email = '';
            this.formData.password = '';
        },
        goToSignUp() {
            this.$router.push("/sign-up"); // 회원가입 페이지로 이동
        }
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