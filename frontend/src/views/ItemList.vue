<template>
    <div class="item_popular_section">
        <div class="content_header">
            <div>여행지 리스트</div>
        </div>
        <LocationCheckbox @checkedClick="fetchItems" :homeChecked="homeChecked"></LocationCheckbox>
        <ul class="item_list">
            <li class="item" v-for="(item, index) in items" :key=childChecked[0]>
                <img :src="item.firstImage" alt="">
                <div class="item_text">
                    <div class="item_title">{{ item.title }}</div>
                </div>
                <ul class="item_count">
                    <li>관심등록</li>
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
            item: {
                contentTypeId: '',
                title: '',
                firstImage: '',
                sido: '',
            },
            items: [{}],
            itemSido:'',
            totalPages:0,
            page:1,
            size:4,
        }
    },
    // computed: {
    //     displayedItems() {
    //         const start = (this.page -1) * this.size;
    //         const end = start + this.size;
    //         return this.items.slice(start, end);
    //     }
    // },
    methods: {
        fetchItems(checked, page = this.page) {
            this.childChecked = checked
            if (this.childChecked.length > 1) {
                this.childChecked.shift()
            }
            this.itemSido = this.childChecked[0];
            //this.page = page; // 페이지 번호를 인자로 설정
            const apiUrl = `/item-list/${this.itemSido}?page=${page}&pageSize=${this.size}`;
            // const apiUrl = '/item-list/' + this.itemSido;

            axios.get(apiUrl)
                .then(response => {
                    this.items = response.data.content.map(item => ({
                        contentTypeId: item.contentTypeId,
                        title: item.title,
                        firstImage: item.firstImage,
                        sido: item.sido,
                    }));
                    this.totalPages = response.data.totalPages;
                })
                .catch(error => {
                    console.error('API 호출 오류', error);
                });
        },
        goToPage(newPage) {
            // 페이지 번호를 변경하면 해당 페이지의 아이템을 가져오도록 메서드 설정
            if (newPage >= 1 && newPage <= this.totalPages) {
                this.item = {};
                this.page = newPage;
                this.fetchItems(this.childChecked, this.page);
            }
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

    .item_popular_section {
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

    .item_popular_section:deep(.location_checkbox) {
        display: flex;
        justify-content: space-between;
        box-sizing: border-box;
        padding-bottom: 10px;
        border-bottom: #DADADA 1px solid;
        margin-bottom: 20px;
    }

    .item_popular_section:deep(.form_checkbox_btn input[type=checkbox]) {
        display: none;
    }

    .item_popular_section:deep(.form_checkbox_btn label) {
        width: 44px;
        border-radius: 14px;
        padding: 5px 8px;
        cursor: pointer;
    }

    .item_popular_section:deep(.form_checkbox_btn input[type=checkbox]:checked + label) {
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