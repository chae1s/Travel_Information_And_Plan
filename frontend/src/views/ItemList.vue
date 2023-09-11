<template>
    <div class="item_detail_info">
        <div class="content_header">
            <div>여행지 리스트</div>
        </div>
        <LocationCheckbox @checkedClick="handleLocationChange" :homeChecked="homeChecked"></LocationCheckbox>
        <ul class="item_list">
            <li class="item" v-for="(item, index) in items" :key=childChecked[0]>
                <router-link :to="'/item-detail/read/' + item.id">
                <img :src="item.firstImage" alt="">
                <div class="item_text">
                    <div>{{item.id}}</div>
                    <div class="item_title">{{ item.title }}</div>
                    <p class="item_fullAddress">{{ item.fullAddress }}</p>
                </div>
                </router-link>
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
</template>

<script>
import LocationCheckbox from "@/components/LocationCheckbox.vue";
import axios from "axios";
export default {
    name: "itemList",
    components: {
        LocationCheckbox
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
            },
            items: [{}],
            itemSido:'',
            totalPages:0,
            page:1,
            size:4,
        }
    },
    methods: {
        fetchItems(checked, page) {
            this.childChecked = checked
            if (this.childChecked.length > 1) {
                this.childChecked.shift()
            }
            this.itemSido = this.childChecked[0];
            const apiUrl = `/item-list/${this.itemSido}?page=${page}&pageSize=${this.size}`;
            // const apiUrl = '/item-list/' + this.itemSido;

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
                    }));
                    this.totalPages = response.data.totalPages;
                    this.itemId = this.items[0].id;
                })
                .catch(error => {
                    console.error('API 호출 오류', error);
                });
        },
        handleLocationChange(checked) {
            this.page = 1
            this.fetchItems(checked, this.page); // 페이지를 1로 설정하여 새로운 지역 선택 시 1페이지로 리셋
        },
        goToPage(newPage) {
            // 페이지 번호를 변경하면 해당 페이지의 아이템을 가져오도록 메서드 설정
            if (newPage >= 1 && newPage <= this.totalPages) {
                //this.item = {};
                this.page = newPage;
                this.fetchItems(this.childChecked, this.page);
            }
        },
        toggleBookmark(itemId) {
            axios.post(`item-list/add`, this.item[itemId])
                .then(response => {
                    if (response.data.message === "Success") {
                        // 즐겨찾기 추가 완료 메시지를 표시하거나 다른 처리를 수행
                        alert("해당 여행지를 즐겨찾기에 추가했습니다.");
                    }
                });
        }

    }
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

    .item_label_list {
        display: flex;
        flex-wrap: wrap;
        margin-top: 10px;
        height: 25px;
    }

    .item_label {
        padding: 4px 8px;
        margin-right: 8px;
        border-radius: 4px;
        font-size: 13px;
        background-color: #C4DFFF;
    }

    .__label {
        background-color: #FFE866;
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