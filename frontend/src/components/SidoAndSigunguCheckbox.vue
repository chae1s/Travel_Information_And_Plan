<template>
    <div class="location_checkbox">
        <div class="form_checkbox_btn" v-for="code in sidoCode" :key="code">
            <input :id="locations[code].name" type="checkbox" v-model="checkedSido" @change="clickFunc" name="sido" :value="code">
            <label :for="locations[code].name">{{ locations[code].name }}</label>
            <!-- v-if로 해당 시/도의 시/군/구를 출력 -->
            <div class="form_checkbox_btn" v-if="checkedSido.includes(code)">
                <div v-for="sigungu in locations[code].sigunguCode" :key="sigungu.sigungu">
                    <input :id="sigungu.name" type="checkbox" v-model="checkedSigungu" @change="clickSigunguFunc" name="sigungu" :value="sigungu.sigungu">
                    <label :for="sigungu.name">{{ sigungu.name }}</label>
                </div>
            </div>
        </div>
        <br>
    </div>
</template>


<script>
import locations from "@/assets/locations";

export default {
    name: "SidoAndSigunguCheckbox",
    computed: {
        locations() {
            return locations;
        }
    },
    props: [
        'homeChecked'
    ],
    data() {
        return {
            checkedSido: [],
            checkedSigungu: [],
            sidoCode: [0, 1, 2, 3, 4, 5, 6, 7, 8, 31, 32, 33, 34, 35, 36, 37, 38, 39],
            sigunguCodes: []
        };
    },
    components: {
        locations
    },
    mounted() {
        this.initValue();
    },
    methods: {
        clickFunc() {
            if (this.checkedSido.length > 1) {
                this.checkedSido.shift();
            }
            this.checkedSigungu = [];
            this.$emit('checkedClick', this.checkedSido);
            this.getSigunguCode();
            console.log("checkedSido:", this.checkedSido);
        },
        clickSigunguFunc() {
            if (this.checkedSigungu.length > 1) {
                this.checkedSigungu.shift();
            }
            // sigungu 선택이 변경될 때 호출될 함수
            this.$emit("checkedClick", {
                sidoCode: this.checkedSido,
                sigunguCode: this.checkedSigungu
            });
            console.log("sidoCode:", this.checkedSido);
            console.log("checkedSigungu:", this.checkedSigungu);
        },
        initValue() {
            if (this.homeChecked) {
                // 초기 값 설정 로직 추가
                //this.checkedSido = [1]; // 예시로 "서울" 선택
                this.getSigunguCode();
            }
        },
        getSigunguCode() {
            this.sigunguCodes = this.checkedSido.flatMap(code =>
                locations[code].sigunguCode.map(sigungu => ({
                    name: sigungu.name,
                    sigungu: sigungu.sigungu
                }))
            );
            console.log("sigunguCodes", this.sigunguCodes);
        }
    }
};
</script>

<style scoped>

</style>
