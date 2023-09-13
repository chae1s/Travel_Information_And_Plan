<template>
    <div class="item_reviews">
        <h3>후기 목록</h3>
        <ul>
            <li v-for="(review, index) in itemReviews" :key="index" >
                <!-- 후기 내용과 평점 등을 표시할 HTML 작성 -->
                <p>내용: {{ review.content }}</p>
                <p>평점: {{ review.rating }}</p>
                <p>작성자: {{review.nickname}}</p>
                <!-- 수정하기 버튼 -->
                <button @click="editReview(review)">수정하기</button>
                <br>

                <!-- 삭제하기 버튼 -->
                <button @click="deleteReview(review.id)">삭제하기</button>

                <!-- 리뷰 업데이트 폼 -->
                <form v-show="review.id === editingReviewId" @submit.prevent="updateReview(review.id)">
                    <input v-model="editedReviewContent" type="text" placeholder="리뷰 내용">
                    <button type="submit">리뷰 업데이트</button>
                </form>
                <p>----------------</p>
            </li>
        </ul>
    </div>
</template>
<script>
import axios from "axios";
import { updateReview, deleteReview } from "@/api";
export default {
  name: "item-reviews",
  data() {
      return {
          reviewText: '',
          currentRating: 0,
          itemReviews: [],
          itemId: this.$route.params.id,
          itemReview: {
              id: 0,
              content: "",
              isDeleted:"",
          },
          editingReviewId: null,
          editedReviewContent: '',
      };
  },
    mounted() {
        this.fetchItemReviews(this.itemId);
    },
  methods: {
      fetchItemReviews(itemId) {
          // 아이템의 후기 목록을 불러오는 API 요청을 보내고,
          // 응답을 받으면 itemReviews 데이터에 저장
          const apiUrl = `/item-detail/read/${itemId}/reviews`;

          axios.get(apiUrl)
              .then(res => {
                  this.itemReviews = res.data.filter(review => !review.isDeleted);
                  console.log('reviewData:', this.itemReviews)
              })
              .catch(error => {
                  console.error('후기 목록을 불러오는 중 오류 발생', error);
              });

      },
      editReview(review) {
          // 수정하기 버튼 클릭 시 해당 리뷰의 ID를 저장하고 수정 폼을 나타냅니다.
          this.editingReviewId = review.id;
          this.editedReviewContent = review.content;
      },
      async updateReview(reviewId) {
          try {
              console.log('reviewId: ', reviewId)
              await updateReview(this.itemId, reviewId, this.editedReviewContent);
              // 업데이트 성공 시 처리
              console.log('리뷰 업데이트 성공');
              alert('리뷰가 수정되었습니다.');
              this.editingReviewId = null; // 수정 폼 숨김
              this.fetchItemReviews(this.itemId);
          } catch (error) {
              console.error('리뷰 업데이트 중 오류 발생', error);
          }
      },
      async deleteReview(reviewId) {
          try {
              console.log('reviewId: ',reviewId)
              await deleteReview(this.itemId, reviewId);
              const deletedReview = this.itemReviews.find(review => review.id === reviewId);
              if (deletedReview) {
                  deletedReview.isDeleted = true;
              }
              // 삭제 성공 시 처리
              console.log('리뷰 삭제 성공');
              alert('리뷰가 삭제되었습니다.');
              this.fetchItemReviews(this.itemId);
          } catch (error) {
              console.error('리뷰 삭제 중 오류 발생', error);
          }
      },
  }
}
</script>


<style scoped>

</style>