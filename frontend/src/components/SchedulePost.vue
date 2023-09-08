<template>
    <div class="sidebar_main">
        <div class="schedule_header">
            <div class="schedule_title">{{ scheduleData.title }}</div>
            <div class="schedule_sido">{{ scheduleData.sido }}</div>
            <div class="schedule_tourDate">
                <span>{{ scheduleData.startDate }}</span> ~ <span>{{ scheduleData.endDate }}</span>
            </div>
            <div class="schedule_mates">
                <span v-for="user in scheduleData.users" :key="user">{{ user.nickname }}</span>
            </div>
            <div class="show_schedule">공유하기</div>
        </div>
        <div class="schedule_contents">
            <div id="map" class="schedule_map">

            </div>
            <div class="schedule_description">{{ scheduleData.description }}</div>
            <div class="schedule_item_header">
                <span v-for="i in scheduleData.period">Day {{ i }}</span>
            </div>
            <div class="schedule_item_images">
                <div class="image_list">
                    <div v-for="i in 7">
                        <v-img src="@/assets/images/site_1.jpg" width="112px" height="112px" cover="true" class="rounded-lg"/>
                    </div>
                </div>
            </div>
            <div class="schedule_destination_list">
                <ul>
                    <li v-for="i in 7" class="schedule_item">
                        <div>
                            <v-img :src="require('@/assets/images/icons/day/route_' + (i) + '.png')" width="24px" height="24px" inline="true"/>
                        </div>
                        <div>
                            <div class="destination_title">여행지 이름</div>
                            <div class="destination_address">여행지 주소</div>
                            <div class="destination_information">여행지 정보</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
import locations from "@/assets/locations";
import {readMySchedule} from "@/api/index";
import dayjs from "dayjs";

export default {
    name: "SchedulePost",
    data() {
        return {
            scheduleId: this.$route.params.id,
            scheduleData: {
                title: '',
                description: '',
                sido: '',
                startDate: '',
                endDate: '',
                period: 0,
                users: [{}]
            },
        }
    },
    mounted() {
        this.readSchedule(this.scheduleId)
    },
    methods: {
        async readSchedule(id) {
            try {
                const {data} = await readMySchedule(id)
                this.scheduleData.title = data.title
                this.scheduleData.description = data.description
                this.scheduleData.sido = locations[data.sido].name
                this.scheduleData.startDate = dayjs(data.startDate).format("YYYY.MM.DD")
                this.scheduleData.endDate = dayjs(data.endDate).format("YYYY.MM.DD")
                this.scheduleData.period = data.period
                this.scheduleData.users = data.userResponses

                console.log(data)
            } catch (error) {
                console.log(error)
            }
        },
    }
}
</script>

<style scoped>
    .sidebar_main {
        width: 937px;
    }

    .schedule_header {
        border-bottom: 1px solid #DADADA;
        height: 122px;
    }

    .schedule_title {
        font-size: 22px;
        font-weight: bold;
    }

    .schedule_sido {
        margin-top: 4px;
    }

    .schedule_mates span {
        display: inline-block;
        margin-right: 22px;
    }
    .schedule_mates span:last-child {
        margin-right: 0;
    }

    .show_schedule {
        margin-right: 14px;
        font-size: 14px;
        color: #565656;
        margin-bottom: 9px;
        margin-top: -16px;
        cursor: pointer;
        display: inline-block;
        float: right;
    }

    .schedule_map {
        width: 789px;
        height: 445px;
        margin: 19px auto;
        background-color: #C4DFFF;
    }

    .schedule_description {
        width: 789px;
        text-align: left;
        margin: 0 auto 17px;
    }

    .schedule_item_header {
        width: 789px;
        margin: 0 auto;
    }

    .schedule_item_header span {
        font-weight: bold;
        margin-right: 24px;
        cursor: pointer;
    }

    .schedule_item_header span:last-child {
        margin-right: 0;
    }

    .schedule_item_images {
        display: inline-block;
        margin: 10px auto;
    }

    .image_list {
        display: flex;
        gap: 24px;
    }

    .schedule_destination_list {
        width: 789px;
        margin: 20px auto 0;
    }

    .schedule_item {
        display: flex;
        gap: 9px;
        margin-bottom: 10px;
    }

    .destination_title {
        font-size: 16px;
        font-weight: bold;
        height: 24px;
        line-height: 24px;
    }

    .destination_information, .destination_address {
        font-size: 14px;
    }



</style>