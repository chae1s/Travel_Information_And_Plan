<template>
    <main>
        <div class="ad_image_section">
            <v-img src="@/assets/images/adImage.png" alt="" class="ad_image" cover/>
        </div>
        <div class="content">
            <div class="home_content">
                <div class="item_recommend_section">
                    <div class="content_header">
                        <div>여기 어때?</div>
                    </div>
                    <ul class="item_list">
                        <li class="item" v-for="i in 4" >
                            <a @click="$router.push('/schedules')">
                                <v-img src="@/assets/images/site_1.jpg" alt="" height="155px" width="277.5" class="rounded-lg" cover/>
                                <div class="item_info">
                                    <div class="item_text">
                                        <div class="item_title">안반데기</div>
                                        <p class="item_fullAddress">주소</p>
                                    </div>
                                    <ul class="item_count">
                                        <li>관심등록</li>
                                        <li>후기 1234</li>
                                    </ul>
                                    <div class="item_label_list">
                                        <v-chip size="small" class="item_label">강원도</v-chip>
                                        <v-chip size="small" class="item_label">관광지</v-chip>
                                        <v-chip size="small" class="item_label">인기</v-chip>
                                    </div>
                                </div>
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="item_popular_section">
                    <div class="content_header">
                        <div>핫 플레이스</div>
                        <router-link to="/items-list">더보기</router-link>
                    </div>
                    <LocationCheckbox @checkedClick="fetchItems" :homeChecked="homeChecked"></LocationCheckbox>
                    <ul class="item_list">
                        <li class="item" v-for="(item, index) in items" :key=childChecked[0] >
                            <router-link :to="'/item-detail/read/' + item.id">
                                <img :src="item.firstImage" alt="">
                                <div class="item_text">
                                    <div class="item_title">{{ item.title }}</div>
                                </div>
                            </router-link>
                            <ul class="item_count">
                                <li @click="toggleBookmark(item.id)">관심등록</li>
                            </ul>
                        </li>
                    </ul>
                </div>
                <div class="board_section">
                    <div class="notice_board">
                        <div class="content_header">
                            <div>공지사항</div>
                            <a href="#">더보기</a>
                        </div>
                        <ul class="board_list">
                            <li v-for="i in 5">title</li>
                        </ul>
                    </div>
                    <div class="popular_board">
                        <div class="content_header">
                            <div>인기 게시글</div>
                            <a href="#">더보기</a>
                        </div>
                        <ul class="board_list">
                            <li v-for="i in 5">title</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>

<script>
import LocationCheckbox from "@/components/LocationCheckbox.vue";
import axios from "axios";
import {bookmarkItem} from "@/api";

export default {
    name: "Home",
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

        }
    },
    methods: {
        clickLocationChecked(checked) {
            this.childChecked = checked
            if (this.childChecked.length > 1) {
                this.childChecked.shift()
            }
            this.scheduleSido = this.childChecked[0]
            console.log(this.scheduleSido)
        },
        fetchItems(checked) {
            this.childChecked = checked
            if (this.childChecked.length > 1) {
                this.childChecked.shift()
            }
            this.itemSido = this.childChecked[0];
            //const apiUrl = `/item-list/${this.itemSido}?page=${page}&pageSize=${this.size}`;
            const apiUrl = `/item-list/${this.itemSido}`;

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
                    //this.itemId = this.items[0].id;
                })
                .catch(error => {
                    console.error('API 호출 오류', error);
                });
        },
        async toggleBookmark(itemId) {
            const {data} = await bookmarkItem(itemId);
        },
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
        margin-top: 10px;
    }

    .item_text {
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
        font-size: 16px;
        text-align: left;
    }

    .item_count {
        display: flex;
        flex-wrap: wrap;
        font-size: 14px;
    }

    .item_count li {
        margin-right: 10px;
    }

    .item_label_list {
        display: flex;
        flex-wrap: wrap;
        margin-top: 6px;
        height: 25px;
    }

    .item_label {
        margin-right: 4px;
        font-size: 13px;
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