<template>
    <div class="location_checkbox">
        <div class="form_checkbox_btn" v-for="code in sidoCode" :key="code">
            <input :id="locations[code].name" type="checkbox" v-model="checkedSido" @change="clickFunc" name="sido" :value="code">
            <label :for="locations[code].name">{{ locations[code].name }}</label>
            <div class="form_checkbox_btn" v-for="sigungu in locations[code].sigunguCode" :key="sigungu.sigungu">
                <input :id="sigungu.name" type="checkbox" v-model="checkedSigungu" @change="clickSigunguFunc(checkedSido)" name="sigungu" :value="sigungu.sigungu">
                <label :for="sigungu.name">{{ sigungu.name }}</label>
            </div>
        </div>
        <!-- 콘텐츠 ID 선택 체크박스 -->
        <div class="form_checkbox_btn" v-for="contentTypeId in contentTypeIdList" :key="contentTypeId">
            <input :id="contentTypeIds[contentTypeId].name" type="checkbox" v-model="checkedContentTypeIds" @change="clickContentTypeFunc" name="contentTypeId" :value="contentTypeId"/>
            <label :for="contentTypeIds[contentTypeId].name">{{ contentTypeIds[contentTypeId].name }}</label>
        </div>
    </div>
</template>

<script>
import locations from "@/assets/locations";
import contentTypeIds  from "@/assets/contentTypeIds";

export default {
    name: "SidoAndSigunguAndCatCheckbox",
    computed: {
        contentTypeIds() {
            return contentTypeIds
        },
        locations() {
            return locations
        },
        contentTypeId() {
            return contentTypeIds
        },
        contentTypeIdList() {
            return Object.keys(contentTypeIds); // 콘텐츠 ID 목록 추출
        }
    },
    props: [
        'homeChecked'
    ],
    data() {
        return {
            checkedSido : [],
            checkedSigungu: [],
            checkedContentTypeIds: [],
            sidoCode: [0, 1, 2, 3, 4, 5, 6, 7, 8, 31, 32, 33, 34, 35, 36, 37, 38, 39],
            contentTypeId:[12, 14, 32, 39],
            sigunguCodes: [],
        }
    },
    components: {
        locations
    },
    mounted() {
        this.initValue()
    },
    methods: {
        clickFunc() {
            if (this.checkedSido.length > 1) {
                this.checkedSido.shift();
            }
            this.checkedSigungu = [];
            this.checkedContentTypeIds = [];
            this.$emit('checkedClick', {
                sidoCode: this.checkedSido,
            });
            this.getSigunguCode(this.checkedSido);
        },
        clickSigunguFunc(checkedSido) {
            if (this.checkedSigungu.length > 1) {
                this.checkedSigungu.shift();
            }
            this.checkedContentTypeIds = [];
            this.$emit("checkedClick", {
                sidoCode: checkedSido,
                sigunguCode: this.checkedSigungu,
            });
        },
        clickContentTypeFunc() {
            if (this.checkedContentTypeIds.length > 1) {
                this.checkedContentTypeIds.shift();
            }
            this.$emit('checkedClick', {
                sidoCode: this.checkedSido,
                sigunguCode: this.checkedSigungu,
                contentTypeId: this.checkedContentTypeIds, // 선택한 콘텐츠 ID를 이벤트로 전달
            });
        },
        initValue() {
            if (this.homeChecked) {
                //this.checkedSido = ["0"]
                //this.getSigunguCode()
            }
        },
        getSigunguCode() {
            this.sigunguCodes = this.checkedSido
                .map((code) => locations[code].sigunguCode.map((sigungu) => sigungu.sigungu))
                .flat();
            console.log("sigunguCodes",this.sigunguCodes)
        },

    }
}
</script>

<style scoped>

</style>