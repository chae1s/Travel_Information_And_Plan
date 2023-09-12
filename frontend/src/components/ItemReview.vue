<template>
    <div class="item_review">
        <h2>후기 작성</h2>
        <textarea v-model="reviewText" placeholder="후기를 작성하세요"></textarea>
        <div class="rating">
            <span v-for="star in 5" :key="star" @click="setRating(star)" :class="{ active: star <= currentRating }">&#9733;</span>
        </div>
        <button @click="submitReview">후기 작성</button>
    </div>
</template>

<script>
import axios from "axios";
import { itemReview } from "@/api"; // itemReview는 실제 API 호출을 처리하는 함수로 가정합니다.

export default {
    name: "item-review",
    props: {
        itemId: {
            type: String, // itemId의 데이터 형식에 따라 변경할 수 있음
            required: true, // 부모 컴포넌트에서 반드시 전달해야 함
        },
    },
    data() {
        return {
            reviewText: '',
            currentRating: 0,
            itemReviews: [],
        };
    },
    methods: {
        setRating(rating) {
            this.currentRating = rating;
            console.log('rating:', rating)
        },
        async submitReview() {
            if (!this.reviewText || this.currentRating === 0) {
                alert("후기 내용과 평점을 입력하세요.");
                return;
            }


            const ratingAsNumber = this.currentRating; // 예: ONE(1)은 1, TWO(2)는 2, ...

            const reviewData = {
                content: this.reviewText,
                rating: ratingAsNumber, // 변환된 숫자를 전달
            };
            console.log('Data:', reviewData)

            try {
                const { data } = await itemReview(this.itemId, reviewData);
                alert("후기가 성공적으로 제출되었습니다.");
            } catch (error) {
                console.error("후기 제출 오류:", error);
                alert("후기를 제출하는 중에 오류가 발생했습니다.");
            }
        },
    },
};
</script>

<style scoped>
/* 필요한 스타일을 추가하세요. */
</style>
