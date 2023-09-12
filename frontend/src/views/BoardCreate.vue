<template>
    <v-app>
        <v-navigation-drawer class="side_bar">
            <v-list>
                <v-list-item class="content_title">커뮤니티</v-list-item>
                <v-list-item><a @click="$router.push('/boards')">여행 후기 게시판</a></v-list-item>
                <v-list-item><a @click="$router.push('/schedule-list')">여행 일정 게시판</a></v-list-item>
            </v-list>
        </v-navigation-drawer>

        <v-main class="board">
            <ul>
                <div class="wrapper">
                    <label for="title">제목 :  </label>
                    <input id="title" type="text" v-model="title">
                </div>
                <EditorTiptap class="editor" v-model="content" :max-limit="1000" />
                <button class="submitBtn" @click="submitForm">저장</button>
            </ul>
        </v-main>
    </v-app>
</template>

<script>
import { createBoard, readBoards } from '@/api/index';
import EditorTiptap from '../components/EditerTiptap.vue';
export default {
  name: 'BoardCreate',
  components: {
    EditorTiptap
  },
  data() {
    return {
      title: '',
      content: ''
    };
  },
  methods: {
    async submitForm() {
      try {
        // 비즈니스 로직
        const boardData = {
          title: this.title,
          content: this.content,
        };
        const response = await createBoard(boardData);
        console.log(response.data);
        this.goToBoardDetails(response.data);

        } catch (error) {
          // 에러 핸들링할 코드
          console.log(error.response.data);
          this.logMessage = error.response.data;
        }
    },
    goToBoardDetails(boardId) {
      this.$store.dispatch('updateBoardId', boardId);
      this.$router.push(`/board-details`);
    }
  }
}
</script>

<style scoped>
    .editor{
        width: 900px;
    }

  .board {
      margin-top: 15px;
      margin-left: 10px;
  }

  .wrapper {
      display: grid;
      grid-template-columns: auto 2fr;
      align-items: center;
      font-size: 22px;
      font-weight: 700;
      margin-bottom: 20px;
      //column-gap: 10px; /* 라벨과 입력 사이의 간격을 조정 */
  }

  #title {
      margin-left: 10px;
      border: 1px solid #000; /* 테두리 스타일과 색상을 지정 */
      padding: 5px; /* 입력 필드 내부 여백 설정 */
      border-radius: 3px;
      width: 840px;
  }

  .submitBtn {
      margin-left: 10px;
      margin-top: 10px;
      border: 1px solid #000;
      padding: 5px;
      border-radius: 3px;
      display: block;
  }

  .content_title {
      display: inline-block;
      margin-right: 10px;
      font-size: 22px;
      font-weight: 700;
      text-align: right;
      padding-bottom: 20px;
  }

  .side_bar {
      margin-top: 130px;
  }

</style>
