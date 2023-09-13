<template>
  <!-- 내가 작성한 후기 출력 -->
    <div class="sidebar_main">
        <div class="main_title">내가 쓴 후기</div>
        <table class="review_table">
            <thead>
            <tr>
                <th class="review_head_id"></th>
                <th class="review_head_content">후기 내용</th>
                <th class="review_head_created">작성일</th>
                <th class="review_head_item_name">여행지</th>
                <!-- 추가하고 싶은 요소 추가 -->
            </tr>
            </thead>
            <tbody>
            <tr v-for="(review, index) in reviews" :key="index">
                <td class="review_id" >{{ review.id }}</td>
                <td class="review_content">
                    {{ review.content }}
                </td>
                <td class="review_created">{{review.createdAt}}</td>
                <td class="review_item_name">{{review.title}}</td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import axios from "axios";
import {readUserLikedItemReview} from "@/api";

export default {
    name: "MyItemReviewList",
    data() {
        return {
            userId:'',
            reviews:[],
            review: {
                id:'',
                content:'',
                createdAt:'',
                itemId:'',
                title:'',
                isDeleted:'',
            }
        };
    },
    mounted() {
        this.readUserLikedItemReview();
    },
    methods: {
        async readUserLikedItemReview() {
            try {
                const response = await readUserLikedItemReview();
                this.reviews = response.data;
                console.log('리뷰 불러오기 성공');
            } catch (error) {
                console.error('리뷰 불러오는 중 오류 발생', error)
            }

        }
    }
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

    .review_table {
        width: 100%;
        border-top: 1px solid #DADADA;
        border-collapse: collapse;
        cursor: default;
    }

    .review_table thead {
        font-size: 16px;
    }

    .review_table thead th {
        padding: 7px 10px;
        border-bottom: 1px solid #DADADA;
    }

    .review_table tbody td {
        border-bottom: 1px solid #DADADA;
    }

    .review_id {
        width: 55px;
    }

    .review_content {
        text-align: left;
        padding: 9px 5px;
        overflow: hidden;
    }

    .review_content span {
        cursor: pointer;
    }

    .review_created {
        width: 150px;
    }

    .review_item_name {
        width: 200px;
        overflow: hidden;
    }
</style>