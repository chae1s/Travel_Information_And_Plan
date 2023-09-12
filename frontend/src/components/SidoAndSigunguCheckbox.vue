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
        <br>
    </div>
</template>

<script>
import locations from "@/assets/locations";

export default {
    name: "SidoAndSigunguCheckbox",
    computed: {
        locations() {
            return locations
        }
    },
    props: [
        'homeChecked'
    ],
    data() {
        return {
            checkedSido : [],
            checkedSigungu: [],
            sidoCode: [0, 1, 2, 3, 4, 5, 6, 7, 8, 31, 32, 33, 34, 35, 36, 37, 38, 39],
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
            this.checkedSigungu = [];
            this.$emit('checkedClick', this.checkedSido);
            console.log("checkedSido:",this.checkedSido)
            //this.getSigunguCode(this.checkedSido);
        },
        clickSigunguFunc(checkedSido) {
            if (this.checkedSigungu.length > 1) {
                this.checkedSigungu.shift()
            }
            this.$emit("checkedClick", {
                sidoCode: checkedSido,
                sigunguCode: this.checkedSigungu,
            });
            console.log("sidoCode:",this.checkedSido)
            console.log("checkedSigungu:",this.checkedSigungu)
        },
        initValue() {
            if (this.homeChecked) {
                //this.checkedSido = ["1"]
                this.getSigunguCode()
            }
        },
        getSigunguCode() {
            this.sigunguCodes = this.checkedSido
                .map(code => locations[code].sigunguCode.map(sigungu => sigungu.sigungu))
                .flat();
            console.log("sigunguCodes",this.sigunguCodes)
        },

    }
}
</script>

<style scoped>

</style>