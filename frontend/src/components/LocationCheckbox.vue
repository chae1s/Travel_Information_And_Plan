<template>
    <div class="location_checkbox">
        <div class="form_checkbox_btn" v-for="code in sidoCode" :key="code">
            <input :id="locations[code].name" type="checkbox" v-model="checked" @change="clickFunc"  name="sido" :value="code">
            <label :for="locations[code].name">{{ locations[code].name }}</label>
        </div>
    </div>
</template>

<script>
import locations from "@/assets/locations";

export default {
    name: "LocationCheckbox",
    computed: {
        locations() {
            return locations
        }
    },
    props: [
        'homeChecked', 'checkedSido'
    ],
    data() {
        return {
            checked : [],
            sidoCode: [0, 1, 2, 3, 4, 5, 6, 7, 8, 31, 32, 33, 34, 35, 36, 37, 38, 39]
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
            this.$emit('checkedClick', this.checked)
        },
        initValue() {
            if (this.homeChecked) {
                //this.checked = ["1"]
            }
        },
    },
    watch: {
        checkedSido(newValue) {
            this.checked.push(newValue)
        },
    }
}
</script>

<style scoped>

</style>