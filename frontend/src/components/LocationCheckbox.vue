<template>
    <div class="location_checkbox">
        <div class="form_checkbox_btn">
            <input id="국내" type="checkbox" v-model="checked" @change="clickFunc()"  name="sido" value="0" width="32" height="23">
            <label for="국내">국내</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="서울" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="1">
            <label for="서울">서울</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="인천" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="2">
            <label for="인천">인천</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="대전" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="3">
            <label for="대전">대전</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="대구" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="4">
            <label for="대구">대구</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="광주" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="5">
            <label for="광주">광주</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="부산" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="6">
            <label for="부산">부산</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="울산" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="7">
            <label for="울산">울산</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="세종" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="8">
            <label for="세종">세종</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="경기" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="31">
            <label for="경기">경기</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="강원" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="32">
            <label for="강원">강원</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="충북" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="33">
            <label for="충북">충북</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="충남" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="34">
            <label for="충남">충남</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="경북" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="35">
            <label for="경북">경북</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="경남" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="36">
            <label for="경남">경남</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="전북" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="37">
            <label for="전북">전북</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="전남" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="38">
            <label for="전남">전남</label>
        </div>
        <div class="form_checkbox_btn">
            <input id="제주" type="checkbox" v-model="checked" @change="clickFunc" name="sido" value="39">
            <label for="제주">제주</label>
        </div>
    </div>
</template>

<script>
export default {
    name: "LocationCheckbox",
    props: [
        'homeChecked'
    ],
    data() {
        return {
            checked : []
        }
    },
    mounted() {
        this.initValue()
    },
    methods: {
        clickFunc() {
            this.$emit('checkedClick', this.checked)
        },
        initValue() {
            if (this.homeChecked) {
                this.checked = ["0"]
            }
        },
    }
}
</script>

<style scoped>

</style>