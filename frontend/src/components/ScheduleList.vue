<template>
<<<<<<< HEAD
    <div class="sidebar_main">
        <div class="main_title">여행 일정 게시판</div>
        <table class="schedule_table">
            <thead>
            <tr>
                <th class="schedule_head_id"></th>
                <th class="schedule_head_title">일정 제목</th>
                <th class="schedule_head_mates">여행 메이트</th>
                <th class="schedule_head_tourDate">여행 날짜</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="schedule in scheduleList" :key="schedule">
                <td class="schedule_id" >{{ schedule.id }}</td>
                <td class="schedule_title" @click="moveSchedulePost(schedule.id)">
                    <span>[{{ schedule.sido }}]</span>
                    {{ schedule.title }}
                </td>
                <td class="schedule_mates">
                    <div>{{ schedule.writer }}</div>
                </td>
                <td class="schedule_tourDate">
                    <span>{{ schedule.startDate }}</span> - <span>{{ schedule.endDate }}</span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import {readAllSchedules} from "@/api";
import locations from "@/assets/locations";
import dayjs from "dayjs";

export default {
    name: "ScheduleList",
=======
    <div class="sidebar_main" v-if="!showSchedulePost">
        <div class="main_title">내가 쓴 일정</div>
        <table class="schedule_table">
            <thead>
                <tr>
                    <th class="schedule_head_id"></th>
                    <th class="schedule_head_title">일정 제목</th>
                    <th class="schedule_head_mates">여행 메이트</th>
                    <th class="schedule_head_tourDate">여행 날짜</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="schedule in scheduleList" :key="schedule">
                    <td class="schedule_id" >{{ schedule.id }}</td>
                    <td class="schedule_title" @click="moveSchedulePost(schedule.id)">
                        <span>[{{ schedule.sido }}]</span>
                        {{ schedule.title }}
                    </td>
                    <td class="schedule_mates">
                        <div>{{ schedule.writer }}</div>
                    </td>
                    <td class="schedule_tourDate">
                        <span>{{ schedule.startDate }}</span> - <span>{{ schedule.endDate }}</span>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <router-view v-if="showSchedulePost"></router-view>
</template>

<script>
import {readAllSchedules} from "@/api/index";
import locations from "@/assets/locations";

export default {
    name: "MyScheduleList",
>>>>>>> 20b278c (feat : 작성한 일정 리스트 내용 출력)
    data() {
        return {
            showSchedulePost: false,
            schedule: {
                id: '',
                title: '',
                sido: '',
                writer: '',
                startDate: '',
                endDate: ''
            },
            scheduleList: [{}]
        }
    },
    mounted() {
        this.fetchSchedules()
    },
    methods: {
        moveSchedulePost(id) {
<<<<<<< HEAD
            this.$router.push(`/schedule-details/${id}`);
=======
            this.$router.push(`/my-page/my-post/schedules/${id}`);
            this.showSchedulePost = true
>>>>>>> 20b278c (feat : 작성한 일정 리스트 내용 출력)
        },
        async fetchSchedules() {
            try {
                const {data} = await readAllSchedules()
<<<<<<< HEAD
                console.log(data)
=======
>>>>>>> 20b278c (feat : 작성한 일정 리스트 내용 출력)
                this.scheduleList = data.content.map(schedule => ({
                    id: schedule.id,
                    title: schedule.title,
                    sido: locations[parseInt(schedule.sido)].name,
                    writer: schedule.userResponse.nickname,
<<<<<<< HEAD
                    startDate: dayjs(schedule.startDate).format("YYYY.MM.DD"),
                    endDate: dayjs(schedule.endDate).format("YYYY.MM.DD")
=======
                    startDate: schedule.startDate,
                    endDate: schedule.endDate
>>>>>>> 20b278c (feat : 작성한 일정 리스트 내용 출력)
                }))

            } catch (error) {
                console.log(error)
            }
        }
    },
}
</script>

<style scoped>
    .sidebar_main {
        width: 937px;
    }

    .main_title {
        font-size: 22px;
        font-weight: 700;
        text-align: left;
        padding-bottom: 20px;
    }

    .schedule_table {
        width: 100%;
        border-top: 1px solid #DADADA;
        border-collapse: collapse;
        cursor: default;
    }

    .schedule_table thead {
        font-size: 16px;
    }

    .schedule_table thead th {
        padding: 7px 10px;
        border-bottom: 1px solid #DADADA;
    }

    .schedule_table tbody td {
        border-bottom: 1px solid #DADADA;
    }

    .schedule_id {
        width: 55px;
    }

    .schedule_title {
        width: 470px;
        text-align: left;
        padding: 9px 5px;
        cursor: pointer;
    }

    .schedule_title span {
        color: #959595;
    }
<<<<<<< HEAD
    /*
        메이트 같이 표시
        .schedule_mates {
            width: 297px;
            white-space: normal;
            text-align: left;
        }

        .schedule_mates div {
            font-size: 14px;
            padding: 4px 10px;
            display: inline-block;
        }
    */
=======
/*
    메이트 같이 표시
    .schedule_mates {
        width: 297px;
        white-space: normal;
        text-align: left;
    }

    .schedule_mates div {
        font-size: 14px;
        padding: 4px 10px;
        display: inline-block;
    }
*/
>>>>>>> 20b278c (feat : 작성한 일정 리스트 내용 출력)
    .schedule_tourDate {
        width: 215px;
    }

    .view_list {
        display: none;
    }
</style>