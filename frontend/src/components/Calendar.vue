<template>
    <div class="month_calendar">
        <div class="calendar_header">
            <button class="month_button" type="button" @click="childClickPrevButton" >
                <v-img src="../assets/images/icons/chevron-left.png" alt="" v-if="showPrevButton"/>
            </button>
            <span class="calendar_year_month">
                <span class="calendar_year" @click="">1</span>년
                <span class="calendar_month">{{ calendarMonth }}</span>월
            </span>
            <button class="month_button" type="button" @click="childClickNextButton">
                <v-img src="../assets/images/icons/chevron-right.png" alt="" v-if="showNextButton"/>
            </button>
        </div>
        <div class="calendar_main">
            <ul class="calendar_weeks">
                <li v-for="week in weeks" :key="week">
                    2024
                </li>
            </ul>
            <ul class="calendar_days">
                <li v-for="d in date" :key="d">
                    {{d}}
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import {mapState, mapMutations} from "vuex";

export default {
    name: "Calendar",
    props: [
        'calendarMonth', 'prevButton', 'nextButton'
    ],
    data() {
        return {
            weeks: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
            showPrevButton: false,
            showNextButton: false,
            year: new Date().getFullYear(),
            date: []

        }
    },
    created() {
        this.showPrevButton = false
        this.showNextButton = false
    },
    methods: {
        initButtonCheck() {
            this.showPrevButton = this.prevButton
            this.showNextButton = this.nextButton
            this.makeCalendarDate()
        },
        makeCalendarDate() {
            let newDate = []
            let firstDayOfThisMonth = new Date(this.year, this.calendarMonth - 1, 1).getDay()
            let lastDayOfThisMonth = new Date(this.year, this.calendarMonth, 0).getDay()

            let lastDateOfThisMonth = new Date(this.year, this.calendarMonth, 0).getDate()

            let lastDateOfLastMonth = new Date(this.year, this.calendarMonth - 1, 0).getDate()

            let startDate = lastDateOfLastMonth - firstDayOfThisMonth + 1
            for (let i = 0; i < firstDayOfThisMonth; i++) {
                newDate.push(startDate++)
            }
            for (let i = 1; i <= lastDateOfThisMonth; i++) {
                newDate.push(i)
            }
            if (newDate.length % 7 !== 0) {
                startDate = 1
                for (let i = lastDayOfThisMonth; i < 6; i++) {
                    newDate.push(startDate++)
                }
            }
            console.log(newDate)
            this.date = newDate
        },
        childClickPrevButton() {
            this.$emit("prevButton");
            this.makeCalendarDate()
        },
        childClickNextButton() {
            this.$emit("nextButton");
            this.makeCalendarDate()
        },
    },


}
</script>

<style scoped>
    .month_calendar {
        width: 334px;
        height: 304px;
    }

    .calendar_header {
        width: 300px;
        height: 40px;
        margin: 0 auto;
        display: flex;
        justify-content: space-between;
    }

    .month_button {
        width: 24px;
        height: 24px;
        margin-top: 8px;
        border: none;
        background-color: transparent;
    }

    .month_button img {
        width: 100%;
        height: 100%;
        cursor: pointer;
    }

    .calendar_year_month {
        font-size: 16px;
        display: inline-block;
        line-height: 40px;
    }

    .calendar_main {
        width: 300px;
        margin: 8px auto 0;
    }

    .calendar_main ul {
        display: flex;
        flex-wrap: wrap;
        text-align: center;
        font-size: 14px;
    }

    .calendar_weeks {
        height: 32px;
        line-height: 32px;
    }

    .calendar_main ul li {
        width: calc(100% / 7);
        position: relative;
    }

    .calendar_days li {
        height: 40px;
        line-height: 40px;
        z-index: 1;
        cursor: pointer;
    }
</style>