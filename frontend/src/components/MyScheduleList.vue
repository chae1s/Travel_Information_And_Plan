<template>
    <div class="sidebar_main">
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
</template>

<script>
import {readAllMySchedules} from "@/api/index";
import locations from "@/assets/locations";
import dayjs from "dayjs";

export default {
    name: "MyScheduleList",
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
            this.$router.push(`/my-page/my-post/schedules/${id}`);
            this.showSchedulePost = true
        },
        async fetchSchedules() {
            try {
                const {data} = await readAllMySchedules()
                console.log(data)
                this.scheduleList = data.content.map(schedule => ({
                    id: schedule.id,
                    title: schedule.title,
                    sido: locations[parseInt(schedule.sido)].name,
                    writer: schedule.userResponse.nickname,
                    startDate: dayjs(schedule.startDate).format("YYYY.MM.DD"),
                    endDate: dayjs(schedule.endDate).format("YYYY.MM.DD")
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
    .schedule_tourDate {
        width: 215px;
    }

    .view_list {
        display: none;
    }
</style>