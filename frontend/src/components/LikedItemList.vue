<template>
    <div class="sidebar_main">
        <div class="main_title">관심등록 여행지</div>
        <ul class="item_list">
            <li class="item" v-for="(item, itemIndex) in likedItemList" :key="itemIndex">
                <div class="item_info">
                    <router-link :to="'/item-detail/read/' + item.id">
                    <v-img :src="item.firstImage" alt="" cover class="rounded-lg" height="126px" />
                    <div class="my_liked_item_id" style="display: none">{{ item.id }}</div>
                    <div class="my_liked_item_name">{{ item.name }}</div>
                    <div class="my_liked_item_address">{{ item.fullAddress }}</div>
                    <div class="my_liked_item_category">{{ item.category }}</div>
                    </router-link>
                </div>
            </li>
            <li v-if="likedItemList.length === 0" class="empty_liked_item">관심등록한 여행지가 없습니다.</li>
        </ul>
    </div>
</template>

<script>
import {readUserLikedItem} from "@/api";

export default {
    name: "LikedItemList",
    data() {
        return {
            likedItemList: [],
            item: {
                id:'',
                contentTypeId: '',
                contentId:'',
                title: '',
                firstImage: '',
                sido: '',
                fullAddress:'',
            },
        }
    },
    mounted() {
        this.readUserLikedItem();
    },
    methods: {
         async readUserLikedItem() {
             try {
                 const response = await readUserLikedItem();
                 this.likedItemList = response.data;
                 console.log("data:", response.data)
                 console.log('관심등록한 여행지 불러오기 성공');
             } catch (error) {
                 console.error('관심등록한 여행지 불러오는 중 오류 발생', error)
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

    .item_list {
        display: flex;
        flex-wrap: wrap;
        margin: 0 -20px;
    }

    .item {
        width: 20%;
        padding: 0 20px 23px;
        box-sizing: border-box;
    }

    .item img {
        width: 100%;
        height: 126px;
        border-radius: 8px;
    }

    .item_info {
        margin-top: 3px;
    }

    .item_text {
        box-sizing: border-box;
    }

    .item_title {
        overflow: hidden;
        text-overflow: ellipsis;
        font-size: 18px;
        font-weight: 600;
        text-align: left;
    }

    .item_fullAddress {
        text-overflow: ellipsis;
        word-wrap: normal;
        font-size: 16px;
        text-align: left;
    }

</style>