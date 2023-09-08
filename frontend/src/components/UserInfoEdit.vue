<template>
    <div class="sidebar_main">
        <div class="main_title">회원정보 수정</div>
        <div class="my_page">
            <v-alert v-if="isError" type="error">
                {{ errorMsg }}
            </v-alert>
            <div class="my_page_profile_image">
                <v-img :src="formDataProfile.profileImage" width="160px" height="160px" class="rounded-circle"
                       :inline="true"></v-img>
                <label for="file" class="my_page_profile_change">프로필 사진 바꾸기</label>
                <div class="my_page_profile_delete">프로필 사진 삭제</div> <!-- 필요없으면 지우기 -->
                <input type="file" name="file" id="file"> <!-- 수정 -->
            </div>
<!--                        <div class="my_page_profile_form">-->
            <v-form ref="form" v-model="valid" lazy-validation class="my_page_profile_form">
                <div class="my_page_edit">
                    <div class="my_page_input_label">이메일</div>
                    <div class="readonly_data">{{ this.formData.email }}</div>
                </div>
                <div class="my_page_edit">
                    <div class="my_page_input_label">닉네임</div>
                    <v-text-field class="v-text-field-custom"
                                  v-model="formData.nickname"
                                  variant="outlined"
                                  :counter="12"
                                  :rules="nameRules"
                                  @input="checkDuplicateNickname"
                    ></v-text-field>
                </div>
                <div class="my_page_edit">
                    <div class="my_page_input_label introduce">프로필 소개</div>
                    <v-textarea class="v-text-field-custom"
                                v-model="formDataProfile.content"
                                variant="outlined"
                                :rows="3"
                                :counter="35"
                                :rules="[rules.contentRules]"
                    ></v-textarea>
                </div>
                <div class="my_page_edit">
                    <div class="my_page_input_label introduce">지역</div>
                    <v-text-field class="v-text-field-custom"
                                  v-model="formDataProfile.location"
                                  variant="outlined"
                                  :counter="10"
                                  :rules="[rules.locationRules]"
                    ></v-text-field>
                </div>
                <div class="my_page_button_wrap">
                    <button class="my_page_button">회원정보 수정</button>
                </div>
            </v-form>
            <!--            </div>-->
        </div>
    </div>
</template>

<script>

import {readUserInfo} from "@/api";

export default {
    name: "ProfileEdit",

    async created() {
        try {
            const {data} = await readUserInfo();
            console.log(data)
            this.email = data.email;
            this.formData.nickname = data.nickname;
            this.formDataProfile.profileImage = data.profileImage;
            this.formDataProfile.content = data.content;
            this.formDataProfile.location = data.location;
            this.presentNickname = data.nickname;
        } catch (error) {
            console.log("조회 에러:", error.response.data)
        }
    },
    data() {
        return {
            valid: undefined,
            formData: {
                nickname: '현재 닉네임',
            },
            formDataProfile: {
                profileImage: '/img/default-profile.png',
                content: '',
                location: ''
            },
            email: '현재 email',
            presentNickname: '',
            isNicknameUnique: undefined,
            isError: false,
            errorMsg: "",
            rules: {
                contentRules: (v) => v.length <= 35 || "35글자 이내로 작성해주세요.",
                locationRules: (v) => v.length <= 10 || "10글자 이내로 작성해주세요."
            }
        }
    },
    computed: {
        nameRules() {
            return [
                (v) => !!v || "닉네임을 입력해주세요.",
                (v) => (v && v.length <= 12) || "한글자 이상 열두글자 이하로 작성해주세요.",
                (v) => {
                    if (v != this.presentNickname) {
                        if (this.isNicknameUnique === false) {
                            return "이미 사용중인 nickname 입니다.";
                        }
                    }
                    return true;
                    console.log(this.isNicknameUnique)
                },
            ];
        },
    },
    methods: {
        submitForm() {
            if (!this.formData.nickname) {
                this.isError = true
                this.errorMsg = "닉네임을 입력해주세요."
                return
            }        },
        goToInfo() {
            console.log("내 정보 조회로 이동")
            this.$router.push({name: 'ProfileEdit'})
        },
        // 닉네임 중복 확인
        async checkDuplicateNickname() {
            // 기존 닉네임과 비교
            if (this.formData.nickname === this.presentNickname) {
                console.log("닉네임 유지: ${this.presentNickname}")
                return;
            }
            // 입력값이 없을 때
            if (this.formData.nickname.trim() === '') {
                console.log("입력값 없음")
                return;
            }
            // 닉네임 변경시 중복체크
            if (this.formData.nickname != this.presentNickname) {
                try {
                    console.log(`닉네임: nickname: ${this.nickname}`)
                    const response = await this.axios.post(`/users/check/nickname/${this.nickname}`);
                    this.isNicknameUnique = !response.data; // 중복시 true 반환, false일 때 unique
                    console.log(`중복 여부: ${response.data ? '중복' : '고유'}`);
                } catch (error) {
                    console.error("nickname 중복확인 오류: " + error);
                }
                console.log(`isNicknameUnique: ${this.isNicknameUnique}`);
            } else {
                console.log(`닉네임 유지: ${this.isNicknameUnique}`);
            }
        },
        validate() {
            this.$refs.form.validate().then((validation) => {
                console.log("check Valid: " + "isNicknameUnique: " + this.isNicknameUnique)
                console.log("validate:", validation)
                if (validation && this.isNicknameUnique) {
                    this.valid = true;
                } else {
                    this.valid = false;
                }
            });
        },
    }
}
</script>

<style scoped>


.main_title {
    font-size: 22px;
    font-weight: 700;
    text-align: left;
    padding-bottom: 20px;
}

.my_page_profile_image {
    text-align: left;
    flex-direction: column;
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