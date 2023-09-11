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
export default {
    props: {
        itemId: {
            type: String, // itemId의 데이터 형식에 따라 변경할 수 있음
            required: true // 부모 컴포넌트에서 반드시 전달해야 함
        }
    },
    data() {
        return {
            itemId:'',
            reviewText: '',
            currentRating: 0,
        };
    },
    methods: {
        setRating(rating) {
            this.currentRating = rating;
        },
        submitReview() {
            const reviewData = {
                content: this.reviewText,
                rating: this.currentRating,
            };
            axios.post(`/item-list/read/${this.itemId}`, reviewData)
                .then(response => {
                    console.log('후기 작성완료.');

                })
                .catch(error => {
                    console.log(`/item-list/read/${this.itemId}`)
                });
        },
    },
};
</script>
<style scoped>

</style>