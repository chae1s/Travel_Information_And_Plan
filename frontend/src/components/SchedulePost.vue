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
            <div class="show_schedule" v-if="!scheduleData.isDisplay">공유하기</div>
        </div>
        <div class="schedule_contents">
            <div id="map" class="schedule_map">

            </div>
            <div class="schedule_description">{{ scheduleData.description }}</div>
            <div class="schedule_item_header">
                <span v-for="i in scheduleData.period" @click="changedDefaultDate(i)" :class="{'selected':checkedDefaultDate(i)}">Day {{ i }}</span>
            </div>
            <div class="schedule_item_images" v-if="defaultDate !== 0">
                <div class="image_list">
                    <div v-for="item in tourRouteList[defaultDate - 1]" :key="item">
                        <v-img :src="item.firstImage" width="112px" height="112px" cover="true" class="rounded-lg"/>
                    </div>
                </div>
            </div>
            <div class="schedule_destination_list" v-if="defaultDate !== 0">
                <ul>
                    <li v-for="(item, i) in tourRouteList[defaultDate - 1]" :key="item" class="schedule_item">
                        <div>
                            <v-img :src="require('@/assets/images/icons/day/route_' + (i + 1) + '.png')" width="24px" height="24px" inline="true"/>
                        </div>
                        <div>
                            <div class="destination_title">{{ item.name }}</div>
                            <div class="destination_address">{{ item.fullAddress }}</div>
                            <div class="destination_information">{{ item.category }}</div>
                            <div v-if="i < itemPathList[defaultDate - 1].length" class="schedule_route_info">
                                <div>이동시간 : {{ itemPathList[defaultDate - 1][i].duration }}</div>
                                <div>이동거리 : {{ itemPathList[defaultDate - 1][i].distance }}</div>
                            </div>
                            <div v-if="i === itemPathList[defaultDate - 1].length" class="schedule_route_info">
                                <div>총 이동시간 : {{ totalDuration[defaultDate - 1] }}</div>
                                <div>총 이동거리 : {{ totalDistance[defaultDate - 1] }}</div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
import locations from "@/assets/locations";
import {readBoardSchedule} from "@/api/index";
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
                isDisplay: '',
                users: [{}]
            },
            sidoCode: '',
            tourRouteList: [],
            itemPathList: [],
            defaultDate: 0,
            totalDuration: [],
            totalDistance: [],
            zoom: 11,
            polylineHex: ['#C4DFFF', '#FFE866', '#72D3B6', '#FFC7C2', '#B3B9FF']
        }
    },
    mounted() {
        this.readSchedule(this.scheduleId)
    },
    methods: {
        async readSchedule(id) {
            try {
                const {data} = await readBoardSchedule(id)
                this.scheduleData.title = data.title
                this.scheduleData.description = data.description
                this.scheduleData.sido = locations[data.sido].name
                this.sidoCode = data.sido
                this.scheduleData.startDate = dayjs(data.startDate).format("YYYY.MM.DD")
                this.scheduleData.endDate = dayjs(data.endDate).format("YYYY.MM.DD")
                this.scheduleData.period = data.period
                this.scheduleData.isDisplay = data.isDisplay
                this.scheduleData.users = data.userResponses

                let scheduleItems = data.scheduleItemResponses

                this.createMap(data.sido)

                for (let i = 0; i < scheduleItems.length; i++) {

                    this.tourRouteList.push(scheduleItems[i].itemListResponses)
                    this.itemPathList.push(scheduleItems[i].itemPaths)
                    this.totalDurationAndDistance(scheduleItems[i].itemPaths)
                }


            } catch (error) {
                console.log(error)
            }
        },
        createMap(sido) {
            const script = document.createElement('script')
            script.src = "https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=y14z3lfrdm"

            script.async = true
            script.defer = true
            document.head.appendChild(script)

            if (this.scheduleData.sido === '0') this.zoom = 8

            script.onload = () => {
                new window.naver.maps.Map("map", {
                    center: new window.naver.maps.LatLng(locations[sido].lat, locations[sido].lng),
                    zoom: this.zoom
                })
            }
        },
        changedDefaultDate(i) {
            this.defaultDate = i
            this.createItemMarkerAndPolyline()
        },
        checkedDefaultDate(i) {
            return this.defaultDate === i
        },
        totalDurationAndDistance(itemPaths) {
            let dateTotalDistance = 0
            let dateTotalDuration = 0
            for (let i = 0; i < itemPaths.length; i++) {
                dateTotalDistance += itemPaths[i].distance
                dateTotalDuration += itemPaths[i].duration
            }

            this.totalDuration.push(dateTotalDuration)
            this.totalDistance.push(dateTotalDistance)
        },
        createItemMarkerAndPolyline() {
            const map = new naver.maps.Map("map", {
                center: new window.naver.maps.LatLng(locations[this.sidoCode].lat, locations[this.sidoCode].lng),
                zoom: this.zoom
            })

            let polylinePath = []
            this.tourRouteList[this.defaultDate - 1].forEach((destination) => {
                polylinePath.push(new naver.maps.LatLng(parseFloat(destination.longitude), parseFloat(destination.latitude)))
            })

            const polyline = new naver.maps.Polyline({
                map : map,
                path: polylinePath,
                strokeColor : this.polylineHex[this.defaultDate - 1],
                strokeOpacity: 0.8,
                strokeWeight: 6
            })


            this.tourRouteList[this.defaultDate - 1].forEach((destination, index) => {
                const marker = new naver.maps.Marker({
                    position: new naver.maps.LatLng(parseFloat(destination.longitude), parseFloat(destination.latitude)),
                    map: map,
                    icon: {
                        url: require('@/assets/images/icons/day' + (this.defaultDate) + '/course_pin_' + (index + 1) + '.png'),
                        scaledSize: new naver.maps.Size(40, 40)
                    }
                })
            })
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
    margin: 20px auto 80px;
}

.schedule_item {
    display: flex;
    gap: 9px;
    margin-bottom: 10px;
    text-align: left;
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

.selected {
    font-weight: bold;
    color: #99C7FF;
}

.schedule_route_info {
    min-width: 210px;
    font-size: 14px;
    margin: 8px 0;
}

.schedule_route_info div {
    display: inline-block;
    width: 50%;
    text-align: left;
}

</style>