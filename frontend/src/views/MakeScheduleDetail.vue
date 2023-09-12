<template>
    <main>
        <div class="content">
            <div class="make_schedule_content">
                <div class="content_title">일정 만들기</div>
                <div class="schedule_input_section">
                    <div class="schedule_data_header">
                        <div class="title_and_mates">
                            <div class="schedule_data_name">{{scheduleData.title}}</div>
                            <div class="schedule_data_mates">
                                <v-img :src="user.profileImage" cover alt="" v-for="(user, i) in scheduleData.users" :key="user" inline="true" width="33px" height="33px" class="rounded-circle"/>
                                <v-img src="@/assets/images/icons/mates_add.png" width="25px" height="25px" class="rounded-circle my-auto mx-0" inline="true"  @click="showMatesInvitation"/>
                            </div>
                        </div>
                        <div class="schedule_data_description">{{scheduleData.description}}</div>
                        <div class="schedule_data_tour_date">{{scheduleData.startDate}} ~ {{scheduleData.endDate}}</div>
                    </div>
                    <!--   메이트 추가     /-->
                    <div v-if="showMatesInvitationComponent">
                        <br>
                        <MatesResearcher v-bind:scheduleId="scheduleId"></MatesResearcher>
                    </div>
                    <div class="my_liked_items_sido">
                        <v-img src="@/assets/images/icons/chevron-left-circle.png" alt="" width="24" height="24" inline class="mx-0 my-auto liked_icon_button" @click="prevPageLikedItem"/>
                        <ul class="my_liked_item_list">
                            <li v-if="likedItemList.length === 0" class="empty_liked_item">관심등록한 여행지가 없습니다.</li>
                            <li v-for="(item, itemIndex) in likedItemList" :class="{'my_liked_item': true, 'selectedItem': checkedSelectedListInItemIndex(item)}" @click="selectedLikedItem(item, itemIndex, $event)">
                                <v-img :src="item.firstImage" alt="" cover class="rounded-lg" height="80"/>
                                <div style="display: none" class="my_liked_item_id">{{ item.id }}</div>
                                <div class="my_liked_item_name">{{ item.name }}</div>
                                <div style="display: none" class="my_liked_item_address">{{ item.fullAddress }}</div>
                                <div class="my_liked_item_category">{{ item.category }}</div>
                            </li>
                        </ul>
                        <div class="selected_tour_date" v-if="clickItem" :style="{top: selectedPosition.top, left: selectedPosition.left}" >
                            <div v-for="dayIndex in scheduleData.period" @click="addItemToTourRoute(dayIndex - 1, this.clickItem, this.clickItemIndex)">
                                Day{{dayIndex}} {{tourRouteList[dayIndex - 1].tourDateWithoutYear}}
                            </div>
                        </div>
                        <v-img src="@/assets/images/icons/chevron-right-circle.png" alt="" width="24" height="24" inline class="mx-0 my-auto liked_icon_button" @click="nextPageLikedItem"/>
                    </div>
                    <div class="schedule_view">
                        <div class="schedule_map" id="map">

                        </div>
                        <div class="schedule_chat">

                        </div>
                    </div>
                    <div class="schedule_route">
                        <div v-for="(tourRoute, dayIndex) in tourRouteList" :key="dayIndex"  class="schedule_daily_route" @click="createRouteList(dayIndex)">
                            <div class="daily_route_header">
                                <div class="tour_day_num" v-if="dayIndex<scheduleData.period">Day{{dayIndex + 1}}</div>
                                <div class="tour_date">{{tourRoute.tourDateWithoutYear}}</div>
                            </div>
                            <ul class="schedule_daily_item_list" @dragover.prevent="onDragOver($event, dayIndex, -1)" @drop="onDrop($event, dayIndex, -1)">
                                <li v-if="tourRoute.tourDestination.length === 0" class="empty_item_list">일정을 채워주세요</li>
                                <li v-for="(destination, itemIndex) in tourRoute.tourDestination" :key="itemIndex" class="schedule_daily_item" draggable="true" @dragstart="onDragStart($event, dayIndex, itemIndex)">
                                    <v-img :src="require('@/assets/images/icons/day' + (dayIndex + 1) + '/course_pin_' + (itemIndex + 1) + '.png')" alt="" width="26" height="26" inline class="my-auto mr-7"/>
                                    <div class="daily_item_info">
                                        <div class="daily_item_info_name">{{ destination.name }}</div>
                                        <v-img src="@/assets/images/icons/u_multiply.png" alt="" @click="removeDestination(dayIndex, itemIndex)" inline width="12" height="12" class="ml-3 remove_item"/>
                                        <div class="daily_item_info_address">{{ destination.fullAddress}}</div>
                                    </div>
                                    <div v-if="tourRoute.tourDestination.length > 1">
                                        <div v-if="itemPath[dayIndex].tourPaths.length > 1 && itemIndex < tourRoute.tourDestination.length - 1" class="schedule_daily_route_info">
                                            <div>이동시간 : {{itemPath[dayIndex].tourPaths[itemIndex].duration}}분</div>
                                            <div>이동거리 : {{itemPath[dayIndex].tourPaths[itemIndex].distance}}km</div>
                                        </div>
                                        <div v-if="itemPath[dayIndex].tourPaths.length > 1 && itemIndex === tourRoute.tourDestination.length - 1" class="schedule_daily_route_info">
                                            <div>총 이동시간 : {{ itemPath[dayIndex].tourPaths[itemPath[dayIndex].tourPaths.length - 1].duration }}분</div>
                                            <div>총 이동거리 : {{ itemPath[dayIndex].tourPaths[itemPath[dayIndex].tourPaths.length - 1].distance }}km</div>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="schedule_button_wrap">
                        <button class="schedule_button" type="button" @click="createScheduleItems" v-if="!isUpdate">일정 생성</button>
                        <button class="schedule_button" type="button" @click="updateScheduleItems" v-else>일정 수정</button>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>

<script>
import Calendar from "@/components/Calendar.vue";
import LocationCheckbox from "@/components/LocationCheckbox.vue";
import locations from "@/assets/locations";
import MatesResearcher from '@/components/MatesResearcher.vue';
import dayjs from 'dayjs';
import {
    readSchedule,
    readLikedItemBySido,
    createRouteList,
    createScheduleItems,
    updateScheduleItems
} from "@/api/index";
export default {
    name: "MakeScheduleDetail",
    components: {LocationCheckbox, Calendar, MatesResearcher},
    data() {
        return {
            scheduleId : this.$route.params.id,
            scheduleData: {
                title: '',
                description: '',
                sido: 0,
                startDate: '',
                endDate: '',
                period: 0,
                users: [{}]
            },
            tourRouteList: [],
            likedItemList: [],
            selectedItemList: [],
            clickItem: null,
            clickItemIndex: null,
            selectedPosition: {top: 0, left: 0},
            itemPath: [],
            polylineHex: ['#C4DFFF', '#FFE866', '#72D3B6', '#FFC7C2', '#B3B9FF'],
            zoom: 11,
            likedItemPage: 1,
            likedItemTotalPage: 0,
            showMatesInvitationComponent: false,
            dragData: {},
            isUpdate: false
        }
    },
    mounted() {
        this.readSchedule(this.scheduleId)
    },
    methods: {
        async readSchedule(id) {
            try {
                const { data } = await readSchedule(id)
                console.log(data)
                this.scheduleData.title = data.title
                this.scheduleData.description = data.description
                this.scheduleData.sido = data.sido
                this.scheduleData.startDate = dayjs(data.startDate).format("YYYY.MM.DD")
                this.scheduleData.endDate = dayjs(data.endDate).format("YYYY.MM.DD")
                this.scheduleData.period = data.period
                this.scheduleData.users = data.userResponses

                let scheduleItems = data.scheduleItemResponses

                this.createMap(data.sido)
                this.readLikedItem(this.scheduleData.sido, this.likedItemPage)

                this.createTourDate()


                if (scheduleItems) {
                    console.log('수정페이지')
                    this.isUpdate = true
                    this.InsertListTourDateAndPath(scheduleItems);
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

            if (this.scheduleData.sido === '0') this.zoom = 7
            else if (this.scheduleData.sido === '2') this.zoom = 10

            script.onload = () => {
                new window.naver.maps.Map("map", {
                    center: new window.naver.maps.LatLng(locations[sido].lat, locations[sido].lng),
                    zoom: this.zoom
                })
            }
        },
        async readLikedItem(sido, page) {
            try {
                const { data } = await readLikedItemBySido(sido, page)

                this.likedItemList = data.content
                this.likedItemTotalPage = data.totalPages
            } catch (error) {
                console.log(error)
            }

        },
        prevPageLikedItem() {
            if (this.likedItemPage > 1) {
                this.likedItemPage--
                this.readLikedItem(this.scheduleData.sido, this.likedItemPage)
            }
        },
        nextPageLikedItem() {
            if (this.likedItemPage < this.likedItemTotalPage) {
                this.likedItemPage++
                this.readLikedItem(this.scheduleData.sido, this.likedItemPage)
            }
        },
        createTourDate() {
            let firstDate = new Date(this.scheduleData.startDate)
            for (let i = 0; i < this.scheduleData.period; i++) {
                let dateWithoutYear = dayjs(firstDate.toDateString()).format("MM.DD")
                let date = dayjs(firstDate.toDateString()).format("YYYY-MM-DD")

                this.tourRouteList.push({
                    tourDate: date,
                    tourDateWithoutYear: dateWithoutYear,
                    tourDestination: []
                })

                this.itemPath.push({
                    tourDate: date,
                    tourPaths: []
                })


                firstDate.setDate(firstDate.getDate() + 1)
            }
        },
        InsertListTourDateAndPath(scheduleItems) {
            for (let i = 0; i < scheduleItems.length; i++) {
                if (i < this.scheduleData.period) {
                    this.tourRouteList[i].tourDestination = scheduleItems[i].itemListResponses
                    this.itemPath[i].tourPaths = scheduleItems[i].itemPaths
                } else {
                    this.tourRouteList.push({
                        tourDate: '',
                        tourDateWithoutYear: '',
                        tourDestination: scheduleItems[i].itemListResponses
                    })
                    this.itemPath.push({
                        tourDate: '',
                        tourPaths: scheduleItems[i].itemPaths
                    })
                }

                this.InsertSelectedLikeItem(scheduleItems[i].itemListResponses)
                this.totalDurationAndDistance(scheduleItems[i].itemPaths, i)
            }

            console.log(this.selectedItemList)
        },
        InsertSelectedLikeItem(itemList) {
            for (let i = 0; i < itemList.length; i++) {
                this.selectedItemList.push(itemList[i].id)
            }
        },
        totalDurationAndDistance(itemPaths, index) {
            let dateTotalDistance = 0
            let dateTotalDuration = 0
            for (let i = 0; i < itemPaths.length; i++) {
                dateTotalDistance += itemPaths[i].distance
                dateTotalDuration += itemPaths[i].duration
            }

            this.itemPath[index].tourPaths.push({distance: dateTotalDistance, duration: dateTotalDuration})
        },
        selectedLikedItem(item, itemIndex, event) {
            this.clickItem = item
            this.clickItemIndex = itemIndex

            this.selectedPosition = {
                top: event.clientY + 'px',
                left: event.clientX + 'px'
            }

        },
        addItemToTourRoute(dayIndex, item, itemIndex) {
            if (this.selectedItemList.indexOf(item.id) < 0) {
                if (this.tourRouteList[dayIndex].tourDestination.length === 7) {
                    alert("여행지는 최대 7개까지만 추가가 가능합니다.")
                }
                this.tourRouteList[dayIndex].tourDestination.push(item);
                this.selectedItemList.push(item.id)

                this.createRouteList(dayIndex)
            }
            this.clickItem = null
            this.clickItemIndex = null
        },
        removeDestination(dayIndex, itemIndex) {
            const deleteItem = this.tourRouteList[dayIndex].tourDestination[itemIndex]
            const deleteItemIndex = this.selectedItemList.indexOf(deleteItem.id)

            this.tourRouteList[dayIndex].tourDestination.splice(itemIndex, 1)

            if (this.itemPath[dayIndex].tourPaths.length === 2) {
                this.itemPath[dayIndex].tourPaths = []
            } else {
                this.itemPath[dayIndex].tourPaths.splice(itemIndex, 1)
            }

            this.selectedItemList.splice(deleteItemIndex, 1)
            this.createRouteList(dayIndex)
        },
        checkedSelectedListInItemIndex(item) {
            return this.selectedItemList.indexOf(item.id) >= 0
        },
        onDragStart(event, dayIndex, itemIndex) {
            this.dragData = {dayIndex, itemIndex}
        },
        onDragOver(event, dayIndex, itemIndex) {
            event.preventDefault()
        },
        onDrop(event, toDayIndex, toItemIndex) {
            if (!this.dragData) return


            const {dayIndex: fromDayIndex, itemIndex: fromItemIndex} = this.dragData
            if (fromDayIndex === toDayIndex && fromItemIndex === toItemIndex) {
                this.dragData = {}
                return
            }

            const draggedItem = this.tourRouteList[fromDayIndex].tourDestination[fromItemIndex]

            this.tourRouteList[fromDayIndex].tourDestination.splice(fromItemIndex, 1)
            this.tourRouteList[toDayIndex].tourDestination.splice(toItemIndex, 0, draggedItem)

            this.dragData = {}

            if (this.tourRouteList[fromDayIndex].tourDestination.length <= 1) {
                this.createItemMarkerAndPolyline(fromDayIndex)
            } else {
                this.createRouteList(fromDayIndex)
            }

            if (this.tourRouteList[toDayIndex].tourDestination.length <= 1) {
                this.createItemMarkerAndPolyline(toDayIndex)
            } else {
                this.createRouteList(toDayIndex)
            }
        },
        async createRouteList(i) {
            try {
                if (this.tourRouteList[i].tourDestination.length <= 1) {
                    this.createItemMarkerAndPolyline(i);
                } else {
                    const { data } = await createRouteList(this.scheduleId, this.tourRouteList[i])
                    this.createItemMarkerAndPolyline(i)
                    this.itemPath[i].tourPaths = data
                }
            } catch (error) {
                console.log(error)
            }
        },
        createItemMarkerAndPolyline(i) {
            const map = new naver.maps.Map("map", {
                center: new window.naver.maps.LatLng(locations[this.scheduleData.sido].lat, locations[this.scheduleData.sido].lng),
                zoom: this.zoom
            })

            let polylinePath = []
            this.tourRouteList[i].tourDestination.forEach((destination) => {
                polylinePath.push(new naver.maps.LatLng(parseFloat(destination.longitude), parseFloat(destination.latitude)))
            })

            const polyline = new naver.maps.Polyline({
                map : map,
                path: polylinePath,
                strokeColor : this.polylineHex[i],
                strokeOpacity: 0.8,
                strokeWeight: 6
            })

            this.tourRouteList[i].tourDestination.forEach((destination, index) => {
                const marker = new naver.maps.Marker({
                    position: new naver.maps.LatLng(parseFloat(destination.longitude), parseFloat(destination.latitude)),
                    map: map,
                    icon: {
                    url: require('@/assets/images/icons/day' + (i + 1) + '/course_pin_' + (index + 1) + '.png'),
                        scaledSize: new naver.maps.Size(40, 40)
                }
                })
            })

        },
        checkedEmptyDestination() {
            let checked = true
            for (let i = 0; i < this.tourRouteList.length; i++) {
                checked = false
                if (this.tourRouteList[i].tourDestination.length === 0) {
                    alert(this.tourRouteList[i].tourDateWithoutYear + '에 갈 여행지 두 곳 이상을 골라주세요.')
                    break
                } else {
                    checked = true
                }
            }

            return checked
        },
        moveSchedulePosts(id) {
            this.$router.push('/my-page/my-post/schedules/'+id)
        },
        async createScheduleItems() {
            try {
                if (this.checkedEmptyDestination()) {
                    const { data } = await createScheduleItems(this.scheduleId);
                    alert("여행 계획이 모두 작성되었습니다.")
                    this.moveSchedulePosts(this.scheduleId)
                }

            } catch (error) {
                console.log(error)
            }
        },
        showMatesInvitation() {
            this.showMatesInvitationComponent = !this.showMatesInvitationComponent;
        },
        async updateScheduleItems() {
            try {
                const { data } = await updateScheduleItems(this.scheduleId)
                alert("여행 계획이 모두 수정되었습니다.")
                this.moveSchedulePosts(this.scheduleId)
            } catch (error) {
                console.log(error)
            }
        },
    },
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
        text-align: left;
    }

    .title_and_mates {
        display: flex;
        gap: 10px;
    }

    .schedule_data_name {
        font-size: 22px;
        font-weight: bold;
        line-height: 33px;
    }

    .schedule_data_mates {
        display: inline-flex;
        gap: 5px;
        height: 100%;
    }

    .schedule_data_mates img {
        width: 25px;
        height: 25px;
        border-radius: 50%;
        margin-top: 2px;
    }

    .schedule_data_description {
        margin-top: 5px;
        font-size: 18px;
    }

    .schedule_data_tour_date {
        margin-top: 5px;
        font-size: 14px;
    }

    .my_liked_items_sido {
        padding-bottom: 9px;
        border-bottom: 1px solid #DADADA;
        display: flex;
        justify-content: space-between;
        margin-top: 9px;
    }

    .liked_icon_button {
        margin-top: 52px;
        cursor: pointer;
    }

    .my_liked_item_list {
        width: 1104px;
        display: flex;
        height: 128px;
    }

    .selectedItem {
        font-weight: bolder;
        color: #99C7FF;
    }

    .empty_liked_item {
        width: 100%;
        text-align: center;
        font-size: 16px;
        font-weight: bold;
        line-height: 128px;
    }

    .my_liked_item {
        margin: 0 10px;
        width: calc(964px / 7);
        cursor: pointer;
        user-select: none;
    }

    .my_liked_item img {
        width: 100%;
        height: 80px;
        border-radius: 8px;
    }

    .my_liked_item_name {
        margin: 3px 0;
        font-size: 15px;
        font-weight: bold;
        overflow: hidden;
    }

    .my_liked_item_category {
        font-size: 14px;
        margin-bottom: 4px;
    }

    .schedule_view {
        display: flex;
        justify-content: space-between;
        margin-top: 24px;
    }

    .schedule_map {
        height: 626px;
        width: 770px;
        background-color: #99C7FF;
    }

    .schedule_chat {
        width: 370px;
        height: 626px;
        border-radius: 10px;
        background-color: #E5F1FF;
    }


    .schedule_route {
        width: 100%;
        margin-top: 24px;
        overflow-x: auto;
        white-space: nowrap;
    }

    .schedule_route::-webkit-scrollbar {
        height: 8px;
    }

    .schedule_route::-webkit-scrollbar-thumb {
        width: 30%;
        background: #99C7FF;
        border-radius: 10px;
    }

    .schedule_route::-webkit-scrollbar-track {
        background: rgba(33, 122, 244, .1)
    }

    .schedule_daily_route {
        display: inline-block;
        margin: 0 0 44px 42px;
        width: 360.67px;
        vertical-align: top;
    }

    .schedule_daily_route:first-child {
        margin-left: 0;
    }

    .daily_route_header {
        height: 22px;
    }

    .tour_day_num {
        font-size: 18px;
        line-height: 22px;
        font-weight: bold;
        display: inline-block;
    }

    .tour_date {
        font-size: 16px;
        line-height: 22px;
        margin-left: 10px;
        display: inline-block;
    }

    .schedule_daily_item_list {
        margin: 12px 0 0 13px;
        display: flex;
        flex-direction: column;
        min-height: 400px;
    }

    .empty_item_list {
        text-align: center;
        font-size: 16px;
        font-weight: bold;
        line-height: 40px;
        height: 40px;
    }

    .schedule_daily_item {
        margin-bottom: 15px;

    }

    .schedule_daily_item img {
        width: 26px;
        height: 26px;
        margin: auto 27px auto 0;
    }

    .daily_item_info {
        display: inline-block;
        width: 294.66px;
        background: #F5F5F5;
        border-radius: 8px;
        padding: 9px 10px;
        user-select: none;
    }

    .daily_item_info img {
        width: 12px;
        height: 12px;
        margin-left: 12px;
        cursor: pointer;
    }

    .daily_item_info_name {
        font-size: 16px;
        display: inline-block;
        font-weight: bold;
    }

    .remove_item {
        cursor: pointer;
    }

    .daily_item_info_address {
        font-size: 14px;
    }

    .schedule_daily_route_info {
        width: 274.66px;
        font-size: 14px;
        float: right;
        margin: 15px 0 0 15px;
    }

    .schedule_daily_route_info div {
        display: inline-block;
        width: 50%;
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

    .selected_tour_date {
        position: absolute;
        background-color: #FFF;
        border: 1px solid #DADADA;
        padding: 5px;
        z-index: 1000;
        cursor: pointer;
    }

    .selected_tour_date div {
        height: 27px;
        line-height: 27px;
        font-size: 15px;
    }


</style>