<template>
  <EditorTiptap @content-updated="updateEditorContent" />

      <!-- 상위 컴포넌트의 데이터에 에디터 내용 표시 -->
  <div v-if="editorContent">
    <h2>Editor Content:</h2>
    <div v-html="editorContent"></div>
    <button @click="submitForm">db에 저장</button>
  </div>
  <button @click="getBoards">get boards</button>
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
      body: '',
      editorContent: ''
    };
  },
  methods: {
    async submitForm() {
      try {
        // 비즈니스 로직
        const boardData = {
          title: this.title,
          content: this.editorContent,
        };
        console.log('aa')
        console.log(this.$store.state.token);
        const { data } = await createBoard(boardData);
        console.log(data)
          //this.$store.commit('setUsername', data.user.username);
          //this.$router.push('/');
        } catch (error) {
          // 에러 핸들링할 코드
          console.log(error.response.data);
          this.logMessage = error.response.data;
        }
      },
    updateEditorContent(content) {
      // 에디터 내용을 상위 컴포넌트의 데이터에 저장
      this.editorContent = content;
      console.log(content);
    },
    async getBoards() {
      try {
        console.log(this.$store.state.token);
        const { data } = await readBoards();
        console.log(data);
      } catch (error) {
        // 에러 핸들링할 코드
        console.log(error.response.data);
        this.logMessage = error.response.data;
      }
    }
  }
}
</script>

