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
                                required
                            ></v-text-field>

                            <v-text-field
                                v-model="formData.password"
                                :type="show ? 'text' : 'password'"
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
                                    <span class="ml-1">비밀번호 보기</span>
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
import { loginUser } from '@/api/index';
export default {
    name: 'LoginView',

    data() {
        return {
            formData: {
                email: '',
                password: ''
            },
        }
    },

    methods: {
        async login() {
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
                console.log(error.response.data);
                this.errorMsg = error.response.data; // 에러 메시지를 errorMsg에 설정
                this.isError = true; // 에러 발생 상태를 true로 설정
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