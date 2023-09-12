<template>
    <div class="sidebar_main">
        <div class="main_title">회원정보 수정</div>
        <div class="my_page">
            <v-alert v-if="isError" type="error">
                {{ errorMsg }}
            </v-alert>
            <v-form ref="form" v-model="valid" lazy-validation class="userInfoEdit_form">
                <div class="my_page_profile_image mt-5">
                    <v-img :src="profileImgPreview" cover width="250px" height="250px" class="rounded-circle"
                           :inline="true"></v-img>
                    <label for="file" class="my_page_profile_change mt-3">프로필 사진 바꾸기</label>
                    <input type="file" name="file" id="file" accept="image/*" @change="uploadProfileImage">
                    <!--                    <div class="my_page_profile_delete" @click="removeProfileImage">프로필 사진 삭제</div>-->
                </div>
                <div class="my_page_profile_form ml-3">
                    <div class="my_page_edit">
                        <div class="my_page_input_label">이메일</div>
                        <div class="readonly_data">{{ this.email }}</div>
                    </div>
                    <div class="my_page_edit">
                        <div class="my_page_input_label">닉네임</div>
                        <v-text-field class="v-text-field-custom"
                                      v-model="formData.nickname"
                                      variant="outlined"
                                      :counter="12"
                                      :rules="nameRules"
                                      :error-messages="nicknameErrors"
                                      @input="checkDuplicateNickname"
                        ></v-text-field>
                    </div>

                    <div class="my_page_edit">
                        <div class="my_page_input_label introduce">프로필 소개</div>
                        <v-textarea class="v-text-field-custom"
                                    v-model="formData.profileContent"
                                    variant="outlined"
                                    :rows="3"
                                    :counter="35"
                                    :rules="[rules.contentRules]"
                        ></v-textarea>
                    </div>

                    <div class="my_page_edit">
                        <div class="my_page_input_label introduce">지역</div>
                        <v-text-field class="v-text-field-custom"
                                      v-model="formData.profileLocation"
                                      variant="outlined"
                                      :rules="[rules.locationRules]"
                        ></v-text-field>

                    </div>
                </div>
            </v-form>
            <div class="my_page_button_wrap">
                <v-btn flat class="my_page_button" @click="submitForm">회원정보 수정</v-btn>
            </div>

        </div>
    </div>
</template>

<script>

import {updateUserInfo, readUserInfo} from "@/api";

export default {
    name: "UserInfoEdit",

    async created() {
        try {
            const {data} = await readUserInfo();
            console.log(data)
            // 서버에서 profileImage를 제외한 모든 내용은 생성시 기본값 존재함
            this.email = data.email;
            this.formData.nickname = data.nickname;
            this.formData.profileContent = data.content;
            this.formData.profileLocation = data.location;
            this.profileImgPreview = data.profileImage;
            this.presentNickname = data.nickname;
        } catch (error) {
            console.log("조회 에러:", error.response.data)
        }
    },
    data() {
        return {
            formData:
                {
                    nickname: '현재 닉네임',
                    profileContent: '',
                    profileLocation: '',
                    isImageDefault: undefined,
                    profileImage: []
                },
            valid: undefined,
            email: '현재 email',
            profileImgPreview: '/img/default-profile.png',
            presentNickname: '',
            isNicknameUnique: true,
            nicknameErrors: [],
            isError: false,
            errorMsg: '',
            rules: {
                contentRules: (v) => v.length <= 35 || "35글자 이내로 작성해주세요.",
                locationRules: (v) => v.length <= 20 || "20글자 이내로 작성해주세요.",
            }
        }
    },
    computed: {
        nameRules() {
            return [
                (v) => !!v || "닉네임을 입력해주세요.",
                (v) => (v && v.length <= 12) || "한글자 이상 열두글자 이하로 작성해주세요.",
            ];
        },
    },
    methods: {
        goToMain() {
            // this.$router.go(0);
            this.$router.push('Home')
        }
        ,
        async submitForm() {
            if (
                !this.formData.nickname
            ) {
                this.isError = true
                this.errorMsg = "닉네임을 입력해주세요."
                return;
            }
            this.validate()
            console.log("valid:", this.valid)
            if (this.valid) {
                try {
                    console.log("formData:", this.formData)

                    const dto = {
                        nickname: this.formData.nickname,
                        profileContent: this.formData.profileContent,
                        profileLocation: this.formData.profileLocation,
                        isImageDefault: this.formData.isImageDefault
                    }
                    const userData = new FormData()
                    userData.append("dto", new Blob([JSON.stringify(dto)], {type: "application/json"}));
                    console.log("image:", this.formData.profileImage)
                    userData.append('image', this.formData.profileImage);

                    const {data} = await updateUserInfo(userData);
                    alert(data.message);
                    this.goToMain()
                } catch
                    (error) {
                    console.log(error.response.data)
                    this.isError = true
                    this.errorMsg = error.response.data.message
                }
            }
        }
        ,

        uploadProfileImage(e) {
            let imageFile = e.target.files;
            this.formData.profileImage = imageFile[0] // 첫번째 이미지만 첨부
            this.profileImgPreview = URL.createObjectURL(imageFile[0]);
            console.log("이미지 등록:", this.formData.profileImage)
        }
        ,
        removeProfileImage() {
            // 기본 프로필로 설정, 프로필 이미지 파일을 삭제
            this.profileImgPreview = "/img/default-profile.png";
            this.formData.profileImage = [];
            this.formData.isImageDefault = true;
            console.log("이미지 삭제:", this.formData.profileImage)
            console.log("isImageDefault:", this.formData.isImageDefault)
        }
        ,

// 닉네임 중복 확인
        async checkDuplicateNickname() {
            // 기존 닉네임과 비교
            if (this.formData.nickname === this.presentNickname) {
                console.log(`닉네임 유지: ${this.presentNickname}`)
                return;
            }
            // 입력값이 없을 때
            if (this.formData.nickname === null) {
                console.log("입력값 없음")
                return;
            }
            // 닉네임 변경시 중복체크
            if (this.formData.nickname != this.presentNickname) {
                try {
                    console.log(`닉네임: nickname: ${this.formData.nickname}`)
                    const response = await this.axios.post(`/users/check/nickname/${this.formData.nickname}`);
                    if (response.data == false) {
                        this.isNicknameUnique = true; // 중복시 true 반환, false일 때 unique
                        this.nicknameErrors = [];
                    } else {
                        this.isNicknameUnique = false; // 중복시 true 반환, false일 때 unique
                        this.nicknameErrors.push("이미 사용중인 이름입니다.");
                    }
                    // console.log(`중복 여부: ${response.data ? '중복' : '고유'}`);
                } catch (error) {
                    console.error("nickname 중복확인 오류: " + error);
                }
                console.log(`isNicknameUnique: ${this.isNicknameUnique}`);
            } else {
                console.log(`닉네임 유지: ${this.isNicknameUnique}`);
            }
        }
        ,
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
        }
        ,
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

.userInfoEdit_form {
    display: flex;
    flex-direction: row;
    min-width: 500px;
}

.my_page_profile_image {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-right: 40px;
}

.rounded-circle {
    border: 1px solid #ccc;
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
    margin: 40px auto 30px;
    width: 198px;
    height: 42px;
    display: inline-block;
    background-color: #99C7FF;
    font-size: 17px;
    font-weight: bold;
    color: #FFF;
    cursor: pointer;
}

</style>