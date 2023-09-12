<template>
    <div class="items_map" id="map">

    </div>
    <div class="item_detail_info">
        <SidoAndSigunguCheckbox @checkedClick="handleLocationChange" :homeChecked="homeChecked"></SidoAndSigunguCheckbox>
        <ul class="item_list">
            <li class="item" v-for="(item, index) in items" :key="item.id ">
                    <img :src="item.firstImage" alt="" @click="showItemOnMap(item.id)">
                    <div class="item_text" @click="showItemOnMap(item.id)">
                        <div>{{item.id}}</div>
                        <div class="item_title">{{ item.title }}</div>
                        <p class="item_fullAddress">{{ item.fullAddress }}</p>
                        <p class="item_contentType">{{ getAddressText(item.contentTypeIds) }}</p>
                    </div>
                <ul class="item_count">
                    <li @click="toggleBookmark(item.id)">관심등록</li>
                </ul>
            </li>
        </ul>
        <div class="pagination">
            <button @click="goToPage(page - 1)" :disabled="page === 1">이전</button>
            <span>{{ page }} / {{ totalPages }}</span>
            <button @click="goToPage(page + 1)" :disabled="page === totalPages">다음</button>
        </div>

    </div>
<!--    <item-list></item-list>-->
</template>

<script>
import SidoAndSigunguCheckbox from "@/components/SidoAndSigunguCheckbox.vue";
import locations from "@/assets/locations";
import axios, {put} from "axios";
import ItemList from "@/views/ItemList.vue";
import {map} from "core-js/internals/array-iteration";
import {bookmarkItem} from "@/api";

export default {
    name: "TravelMap",
    components:{
        ItemList,
        SidoAndSigunguCheckbox
    },
    data() {
        return {
            childChecked: [],
            homeChecked: true,
            itemId:'',
            item: {
                id:'',
                contentTypeId: '',
                contentId:'',
                title: '',
                firstImage: '',
                sido: '',
                fullAddress:'',
                latitude:'',
                longitude:'',
                cat:'',

            },
            itemLocation:{
                latitude:'',
                longitude:'',
                title:'',
                fullAddress: '',
                id:'',
            },
            items: [{},{},{},{},{}],
            itemsLocation: [{}],
            itemSido:'',
            itemSigungu:'',
            totalPages:0,
            page:1,
            size:4,
            zoom: 11,
            markers: [], // 마커 정보를 담는 배열
            infoWindows: [], // 정보창을 담는 배열
            map: null,
        }

    },
    methods: {
        async fetchItemsWithMap(checked, page = this.page) {
            this.childChecked = checked
            console.log("checked:",checked)

            if (this.childChecked.length > 1) {
                this.childChecked.shift()
            }
            console.log("childChecked",this.childChecked)
            this.itemSido = this.childChecked.sidoCode;
            this.itemSigungu = this.childChecked.sigunguCode;
            console.log("itemSido:",this.itemSido)
            console.log("itemSigungu:",this.itemSigungu)
            const apiUrl = `/item-list/${this.itemSido}/${this.itemSigungu}?page=${page}&pageSize=${this.size}`;
            //const apiUrl = `/item-list/${this.itemSido}`;

            axios.get(apiUrl)
                .then(response => {
                    this.items = response.data.content.map(item => ({
                        id: item.itemId,
                        contentTypeId: item.contentTypeId,
                        contentId: item.contentId,
                        title: item.title,
                        firstImage: item.firstImage,
                        sido: item.sido,
                        fullAddress: item.fullAddress,
                        latitude: item.latitude,
                        longitude: item.longitude,
                        cat: item.cat
                    }));
                    this.totalPages = response.data.totalPages;
                    this.itemId = this.items[0].id;
                    if (page === 1) {
                        // 현재 페이지가 1페이지일 때만 지도를 생성
                        this.createMap(this.itemSido);
                    }
                    console.log("items", this.items)

                })
                .catch(error => {
                    console.error('API 호출 오류', error);
                });
        },
        handleLocationChange(checked) {
            this.page = 1
            this.fetchItemsWithMap(checked, this.page); // 페이지를 1로 설정하여 새로운 지역 선택 시 1페이지로 리셋
        },
        goToPage(newPage) {
            // 페이지 번호를 변경하면 해당 페이지의 아이템을 가져오도록 메서드 설정
            if (newPage >= 1 && newPage <= this.totalPages) {
                //this.item = {};
                this.page = newPage;
                this.fetchItemsWithMap(this.childChecked, this.page);
            }
        },
        async toggleBookmark(itemId) {
            const {data} = await bookmarkItem(itemId);
        },
        createMap(sido) {
            const script = document.createElement('script')
            script.src = "https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=y14z3lfrdm"

            script.async = true
            script.defer = true
            document.head.appendChild(script)

            const apiUrl = `/item-list/${this.itemSido}/${this.itemSigungu}/all`;

            axios.get(apiUrl)
                .then(response => {
                    this.itemsLocation = response.data.map(item => ({
                        latitude: item.latitude,
                        longitude: item.longitude,
                        title: item.title,
                        fullAddress: item.fullAddress,
                        id: item.itemId
                    }));
                    this.itemId = this.items[0].id;
                    console.log("item:",response.data)
                })
                .catch(error => {
                    console.error('API 호출 오류', error);
                });

            if (this.item.sido === '0') this.zoom = 8

            this.markers = []; // 마커 정보를 담는 배열
            this.infoWindows = []; // 정보창을 담는 배열
                script.onload = () => {
                    this.map = new window.naver.maps.Map("map", {
                        center: new window.naver.maps.LatLng(locations[sido].lat, locations[sido].lng),
                        zoom: this.zoom
                    });
                    for (let i = 0; i < this.itemsLocation.length; i++) {
                        const itemLocation = this.itemsLocation[i];
                        const title = this.itemsLocation[i].title; // Get the title
                        const id = this.itemsLocation[i].id;
                        const fullAddress = this.itemsLocation[i].fullAddress;

                        const marker = new window.naver.maps.Marker({
                            position: new window.naver.maps.LatLng(itemLocation.longitude, itemLocation.latitude),
                            map: this.map
                        });

                        this.markers.push(marker)

                        const contentString = `
                                <div style="width:200px;text-align:center;padding:10px;">
                                    <b><a href="/item-detail/read/${id}" target="_blank" style="text-decoration: none; color: #000;">${title}</a></b><br>
                                    ${fullAddress}
                                </div>`;
                        var infowindow = new naver.maps.InfoWindow({
                            content: contentString
                        });
                        this.infoWindows.push(infowindow)

                        const getClickHandler = (seq) => {
                            return (e) => {  // 마커를 클릭하는 부분
                                //console.log("seq:",seq)
                                const marker = this.markers[seq]; // 클릭한 마커의 시퀀스로 찾는다.
                                //console.log("marker",marker)
                                const infoWindow = this.infoWindows[seq]; // 클릭한 마커의 시퀀스로 찾는다
                                //console.log("infoWindow",infoWindow)
                                infoWindow.open(this.map, marker);

                            }
                        };
                        for (var j=0, ii=this.markers.length; j<ii; j++) {
                            naver.maps.Event.addListener(this.markers[j], 'click', getClickHandler(j)); // 클릭한 마커 핸들러
                        }

                            infowindow.open(this.map, marker);
                    }

            }
        },
        showItemOnMap(itemId) {
            // 전체 아이템 배열에서 클릭한 아이템을 찾아서 markerIndex를 설정
            const markerIndex = this.itemsLocation.findIndex(
                (markerItem) =>
                    markerItem.id === itemId
            );
            if (markerIndex !== -1) {
                const marker = this.markers[markerIndex];
                const infoWindow = this.infoWindows[markerIndex];
                this.$nextTick(() => {
                    infoWindow.open(this.map, marker);
                });
            }
        },
        getAddressText(itemTypeId) {
            switch (itemTypeId) {
                case '12':
                    return '관광지';
                case '14':
                    return '문화시설';
                case '15':
                    return '축제/공연/행사';
                case '25':
                    return '여행코스';
                case '28':
                    return '레포츠';
                case '32':
                    return '숙박';
                case '38':
                    return '쇼핑';
                case '39':
                    return '음식점';
                default:
                    return '알 수 없음';
            }
        },



    },

}
</script>

<style scoped>
.ad_image_section {
    height: 450px;
    margin-bottom: 32px;
}

.ad_image {
    width: 100%;
    height: 450px;
}

.item_recommend_section {
    padding-bottom: 50px;
}

.item_detail_info {
    padding: 17px 0 50px;
}

.content_header {
    padding: 40px 0 20px;
    display: flex;
    justify-content: space-between;
}
.items_map {
    height: 626px;
    width: 770px;
    background-color: #99C7FF;
}
.content_header div {
    font-size: 22px;
    font-weight: 700;
}

.content_header a {
    font-size: 14px;
    line-height: 27px;
}


.item_list {
    display: flex;
    flex-wrap: wrap;
    margin: 0 -10px;
}

.item {
    width: 25%;
    padding: 0 10px;
    box-sizing: border-box;
}

.item img {
    width: 100%;
    height: 155px;
    border-radius: 8px;
}

.item_info {
    padding: 0 10px;
}

.item_text {
    margin-top: 14px;
    box-sizing: border-box;
}

.item_title {
    overflow: hidden;
    text-overflow: ellipsis;
    font-size: 20px;
    font-weight: 600;
    text-align: left;
}

.item_fullAddress {
    text-overflow: ellipsis;
    word-wrap: normal;
    margin-top: 8px;
    font-size: 16px;
    text-align: left;
}

.item_count {
    display: flex;
    flex-wrap: wrap;
    margin-top: 10px;
    font-size: 14px;
}

.item_count li {
    margin-right: 10px;
}

.search input[type="text"] {
    flex: 1;
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.search button {
    padding: 5px 10px;
    margin-left: 10px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.item_detail_info:deep(.location_checkbox) {
    display: flex;
    justify-content: space-between;
    box-sizing: border-box;
    padding-bottom: 10px;
    border-bottom: #DADADA 1px solid;
    margin-bottom: 20px;
}

.item_detail_info:deep(.form_checkbox_btn input[type=checkbox]) {
    display: none;
}

.item_detail_info:deep(.form_checkbox_btn label) {
    width: 44px;
    border-radius: 14px;
    padding: 5px 8px;
    cursor: pointer;
}

.item_detail_info:deep(.form_checkbox_btn input[type=checkbox]:checked + label) {
    background-color: #C4DFFF;
}

.board_section {
    display: flex;
    justify-content: space-between;
}

.notice_board, .popular_board {
    width: 560px;
    height: 250px;
}

.board_section .content_header {
    border-bottom: 1px solid #DADADA;
    padding-bottom: 13px;
    margin-bottom: 11px;
}

.board_list {
    margin-left: 7px;
    display: flex;
    flex-direction: column;
    gap: 13px;
    text-align: left;
}
</style>