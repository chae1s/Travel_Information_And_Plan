<template>
    <main>
        <div class="content">
            <div class="make_schedule_content">
                <div class="content_title">일정 만들기</div>
                <form class="schedule_input_section">
                    <div class="schedule_name_input">
                        <label>
                            <input type="text" v-model="scheduleTitle" class="schedule_text_input" placeholder="일정의 제목을 입력하세요.">
                        </label>
                    </div>
                    <div class="schedule_description_input">
                        <label>
                            <input type="text" v-model="scheduleDescription" class="schedule_text_input" placeholder="일정을 설명해주세요.">
                        </label>
                    </div>
                    <div class="schedule_date_input">
                        <div class="schedule_input_label">일정 날짜</div>
                        <div class="schedule_date">
                            <span class="start_date">{{ startDate }}</span>
                            <span> ~ </span>
                            <span class="end_date">{{ endDate }}</span>
                        </div>
                        <div class="schedule_date_calendar">
                            <div class="month_calendar">
                                <div class="calendar_header">
                                    <button class="month_button" type="button" @click="handlePrevButton" >
                                        <v-img src="../assets/images/icons/chevron-left.png" alt="" v-if="firstCalendar.month > thisDayMonth.month || firstCalendar.year > thisDayMonth.year"/>
                                    </button>
                                    <span class="calendar_year_month">
                                        <span class="calendar_year" @click="">{{firstCalendar.year}}</span>년
                                        <span class="calendar_month">{{ firstCalendar.month }}</span>월
                                    </span>
                                    <button class="month_button" type="button" >
                                        <v-img src="../assets/images/icons/chevron-right.png" alt="" style="display: none"/>
                                    </button>
                                </div>
                                <div class="calendar_main">
                                    <ul class="calendar_weeks">
                                        <li v-for="week in weeks" :key="week">
                                            {{week}}
                                        </li>
                                    </ul>
                                    <ul class="calendar_days">
                                        <li
                                                v-for="(d, index) in firstCalendar.date" :key="index"
                                                :class="{
                                                'selected': selectedDate.indexOf(firstCalendar.year + '-' + firstCalendar.month.toString().padStart(2, '0') + '-' + d.toString().padStart(2, '0')) !== -1}"
                                                @click="selectDate(firstCalendar.year, firstCalendar.month, d)">
                                            {{d}}
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="month_calendar">
                                <div class="calendar_header">
                                    <button class="month_button" type="button" >
                                        <v-img src="../assets/images/icons/chevron-left.png" alt="" style="display: none"/>
                                    </button>
                                    <span class="calendar_year_month">
                                        <span class="calendar_year" @click="">{{secondCalendar.year}}</span>년
                                        <span class="calendar_month">{{ secondCalendar.month }}</span>월
                                    </span>
                                    <button class="month_button" type="button" @click="handleNextButton">
                                        <v-img src="../assets/images/icons/chevron-right.png" alt=""/>
                                    </button>
                                </div>
                                <div class="calendar_main">
                                    <ul class="calendar_weeks">
                                        <li v-for="week in weeks" :key="week">
                                            {{week}}
                                        </li>
                                    </ul>
                                    <ul class="calendar_days">
                                        <li
                                                v-for="(d, index) in secondCalendar.date" :key="index"
                                                :class="{
                                                'selected': selectedDate.indexOf(secondCalendar.year + '-' + secondCalendar.month.toString().padStart(2, '0') + '-' + d.toString().padStart(2, '0')) !== -1}"
                                                @click="selectDate(secondCalendar.year, secondCalendar.month, d)">
                                            {{d}}
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="schedule_location_input">
                        <div class="schedule_input_label">
                            여행 지역
                        </div>
                        <LocationCheckbox @checkedClick="clickLocationChecked" :checkedSido="scheduleSido"></LocationCheckbox>
                    </div>
                    <div class="schedule_button_wrap">
                        <button @click="createSchedule" class="schedule_button" type="button">일정 생성</button>
                    </div>
                </form>
            </div>
        </div>
    </main>
</template>

<script>
import LocationCheckbox from "@/components/LocationCheckbox.vue";
import dayjs from 'dayjs'
import Calendar from "@/components/Calendar.vue";
import {readSchedule, updateSchedule} from '@/api/index'
export default {
    name: "UpdateSchedule",
    components: {
        dayjs,
        LocationCheckbox,
        Calendar
    },
    data() {
        return {
            scheduleId : this.$route.params.id,
            scheduleTitle: '',
            scheduleDescription: '',
            startDate: null,
            endDate: null,
            scheduleSido: '',
            childChecked: [],
            firstCalendar: {
                year: new Date().getFullYear(),
                month: (new Date().getMonth() + 1),
                date: []
            },
            secondCalendar: {
                year: new Date().getFullYear(),
                month: (new Date().getMonth() + 2),
                date: []
            },
            weeks: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
            thisDayMonth: {
                year: new Date().getFullYear(),
                month: (new Date().getMonth() + 1),
            },
            selectedDate: [],
        }
    },
    created() {
        this.readSchedule(this.scheduleId)
        this.startDate = new Date().getFullYear() + '-' + (new Date().getMonth() + 1).toString().padStart(2, '0') + '-' + new Date().getDate().toString().padStart(2, '0')
        this.endDate = new Date().getFullYear() + '-' + (new Date().getMonth() + 1).toString().padStart(2, '0') + '-' + new Date().getDate().toString().padStart(2, '0')
    },
    mounted() {
        this.makeCalendarDate(this.firstCalendar.year, this.firstCalendar.month, true)
        this.makeCalendarDate(this.secondCalendar.year, this.secondCalendar.month, false)
    },
    methods: {
        async readSchedule(id) {
            try {
                const { data } = await readSchedule(id)
                console.log(data)
                this.scheduleTitle = data.title
                this.scheduleDescription = data.description
                this.scheduleSido = data.sido
                this.startDate = dayjs(data.startDate).format("YYYY-MM-DD")
                this.endDate = dayjs(data.endDate).format("YYYY-MM-DD")

                this.selectedDate = [this.startDate, this.endDate]
                console.log(this.scheduleSido)
            } catch (error) {
                console.log(error)
            }
        },
        selectDate(year, month, date) {
            let selected = year + '-' + month.toString().padStart(2, '0') + '-' + date.toString().padStart(2, '0');
            let today = new Date().getFullYear() + '-' + (new Date().getMonth() + 1).toString().padStart(2, '0') + '-' + new Date().getDate().toString().padStart(2, '0')
            if (selected < today) {
                alert("오늘 이후의 날짜를 선택해주세요.")
            } else {
                let index = this.selectedDate.indexOf(selected);
                if (index === -1) {
                    if (this.selectedDate.length === 2) this.selectedDate = []
                    this.selectedDate.push(selected)

                } else if (index !== -1){
                    this.selectedDate.splice(index, 1)

                }
                this.checkDateInSelectedDate()
            }


        },
        checkDateInSelectedDate() {
            if (this.selectedDate.length === 0) {
                this.startDate = new Date().getFullYear() + '-' + (new Date().getMonth() + 1).toString().padStart(2, '0') + '-' + new Date().getDate().toString().padStart(2, '0')
                this.endDate = new Date().getFullYear() + '-' + (new Date().getMonth() + 1).toString().padStart(2, '0') + '-' + new Date().getDate().toString().padStart(2, '0')

            } else if (this.selectedDate.length === 1) {
                this.startDate = this.selectedDate[0];
                this.endDate = this.selectedDate[0];
            } else {
                if (this.selectedDate[0] > this.selectedDate[1]) {
                    this.startDate = this.selectedDate[1];
                    this.endDate = this.selectedDate[0];
                } else {
                    this.startDate = this.selectedDate[0];
                    this.endDate = this.selectedDate[1];
                }

                this.checkDatePeriod()
            }

        },
        checkDatePeriod() {
            const startDate = new Date(this.startDate)
            const endDate = new Date(this.endDate)

            let diff = Math.abs(endDate.getTime() - startDate.getTime())
            diff = Math.ceil(diff / (1000 * 60 * 60 * 24)) + 1;
            console.log(diff)
            if (diff > 5) {
                alert("총 일수가 5일을 넘을 수 없습니다.")
                this.selectedDate = []
                this.startDate = new Date().getFullYear() + '-' + (new Date().getMonth() + 1).toString().padStart(2, '0') + '-' + new Date().getDate().toString().padStart(2, '0')
                this.endDate = new Date().getFullYear() + '-' + (new Date().getMonth() + 1).toString().padStart(2, '0') + '-' + new Date().getDate().toString().padStart(2, '0')
            }
        },
        handlePrevButton() {
            this.firstCalendar.month--
            this.secondCalendar.month--

            this.makeCalendarDate(this.firstCalendar.year, this.firstCalendar.month, true)
            this.makeCalendarDate(this.secondCalendar.year, this.secondCalendar.month, false)
        },
        handleNextButton() {
            this.firstCalendar.month++
            this.secondCalendar.month++

            this.makeCalendarDate(this.firstCalendar.year, this.firstCalendar.month, true)
            this.makeCalendarDate(this.secondCalendar.year, this.secondCalendar.month, false)
        },
        makeCalendarDate(year, month, checked) {
            let newDate = []
            if (month > 12) {
                month = 1
                year++
            } else if (month < 1) {
                month = 12
                year--
            }
            let firstDayOfThisMonth = new Date(year, month - 1, 1).getDay();

            let lastDateOfThisMonth = new Date(year, month, 0).getDate()


            for (let i = 0; i < firstDayOfThisMonth; i++) {
                newDate.push('')
            }
            for (let i = 1; i <= lastDateOfThisMonth; i++) {
                newDate.push(i)
            }
            if (checked) {
                this.firstCalendar.year= year
                this.firstCalendar.month = month
                this.firstCalendar.date = newDate
            } else {
                this.secondCalendar.year = year
                this.secondCalendar.month = month
                this.secondCalendar.date = newDate
            }

        },
        clickLocationChecked(checked) {
            this.childChecked = checked
            if (this.childChecked.length > 1) {
                this.childChecked.shift()
            }
            this.scheduleSido = this.childChecked[0]
            console.log(this.scheduleSido)
        },
        moveSchedule(id) {
            this.$router.push('/schedules/'+id+'/schedule-items')
        },
        async createSchedule() {
            try {
                const scheduleData = {
                    title: this.scheduleTitle,
                    description: this.scheduleDescription,
                    startDate: this.startDate,
                    endDate: this.endDate,
                    sido: this.scheduleSido
                };

                if (this.scheduleTitle === '') alert("일정의 제목을 입력해주세요.")
                else if (this.scheduleDescription === '') alert("일정을 설명해주세요.")
                else if (this.scheduleSido === '') alert("여행갈 도시를 선택해주세요.")
                else {
                    const { data } = await updateSchedule(this.scheduleId, scheduleData)
                    alert('일정이 수정되었습니다. 세부 일정을 수정해주세요.')
                    this.moveSchedule(data)
                }
            } catch (error) {
                console.log(error.response.data)
            }


        }
    }
}
</script>

<style scoped>

.content_title {
    font-size: 22px;
    font-weight: 700;
    text-align: left;
    padding-bottom: 20px;
}

.schedule_input_section {
    width: 970px;
    margin: 0 auto;
    text-align: left;
}

.schedule_name_input {
    padding-bottom: 10px;
}

.schedule_description_input {
    padding-bottom: 8px;
    margin-bottom: 13px;
    border-bottom: 1px solid #DADADA;
}

.schedule_date_input {
    padding-bottom: 20px;
    margin-bottom: 13px;
    border-bottom: 1px solid #DADADA;
}

.month_calendar {
    width: 334px;
    height: 304px;
}

.calendar_header {
    width: 294px;
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
    width: 294px;
    margin: 8px auto 0;
    user-select: none;
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
    width: 40px;
    position: relative;
    margin: 0 1px;
}

.calendar_days li {
    height: 40px;
    line-height: 40px;
    z-index: 1;
    cursor: pointer;
    margin: 0 1px;
    width: 40px;
}

.selected {
    border-radius: 50%;
    background-color: #C4DFFF;
}

.schedule_name_input label, .schedule_description_input label {
    height: 29px;
    display: inline-block;
    width: 100%;
}

.schedule_input_label {
    font-size: 18px;
    font-weight: 600;
    height: 100%;
    line-height: 29px;
    display: inline-block;
}

.schedule_text_input {
    padding: 2px 0 8px;
    font-size: 16px;
    border: none;
    height: 25px;
    width: 100%;
    display: inline-block;
}

.schedule_date {
    padding: 6px 0;
    font-size: 14px;
}

.schedule_date_calendar {
    width: 668px;
    height: 320px;
    margin: 0 auto;
    display: flex;
}

.schedule_location_input:deep(.location_checkbox) {
    padding: 6px 0;
    display: flex;
    flex-wrap: wrap;
    gap: 11.5px;
    user-select: none;
}

.schedule_location_input:deep(.form_checkbox_btn input[type=checkbox]) {
    display: none;
}

.schedule_location_input:deep(.form_checkbox_btn label) {
    width: 64px;
    height: 42px;
    padding: 9px 16px;
    display: inline-block;
    border-radius: 15px;
    line-height: 23px;
    cursor: pointer;
    text-align: center;
}

.schedule_location_input:deep(.form_checkbox_btn input[type=checkbox]:checked + label) {
    background-color: #C4DFFF;
}

.schedule_button_wrap {
    text-align: center;
}

.schedule_button {
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