<template>
    <div>
        <table class="board-table">
            <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>조회수</th>
                <th>좋아요 수</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(board, index) in boards" :key="board.title" @click="goToBoardDetails(board.boardId)">
                <td>{{ board.boardId }}</td>
                <td>{{ board.title }}</td>
                <td>{{ board.username }}</td>
                <td>{{ board.viewCnt }}</td>
                <td>{{ board.likesCnt }}</td>
            </tr>
            </tbody>
        </table>
        <v-btn @click="$router.push('/board-create')">작성</v-btn>
        <!-- Pagination 컴포넌트를 사용하면서 totalPages를 상위 컴포넌트에서 관리 -->
        <Pagination
            :totalPages="totalPages"
            @page-changed="handlePageChange"
        />
    </div>
</template>

<script>
import { readBoards } from "@/api";
import Pagination from "@/components/Pagination.vue";
export default {
    name: "BoardList",
    components: {
        Pagination,
    },
    data() {
        return {
            boards: [], // 게시판 목록을 담을 배열 추가
            totalPages: 10, // 초기값 설정 (예시로 10을 사용)
        };
    },
    methods: {
        async fetchBoards(page) {
            try {
                const response = await readBoards(page);
                const pageDto = response.data;
                this.totalPages = pageDto.lastPage; // totalPages 업데이트
                this.boards = pageDto.content; // 게시판 목록 업데이트
                console.log(pageDto);
            } catch (error) {
                console.error("Error fetching boards:", error);
            }
        },
        async handlePageChange(page) {
            console.log(`페이지 변경: ${page}`);
            this.currentPage = page;
            await this.fetchBoards(page); // 페이지가 변경될 때마다 게시글 목록을 가져옴
        },
        goToBoardDetails(boardId) {
            this.$store.dispatch('updateBoardId', boardId);
            this.$router.push(`/board-details`);
        }
    },
    created() {
        this.fetchBoards(this.currentPage); // 컴포넌트가 생성될 때 초기 게시글 목록을 가져옴
    },
};
</script>

<style scoped>
.board-table {
    width: 100%;
    border-collapse: collapse;
}

.board-table th,
.board-table td {
    border: 1px solid #ddd;
    padding: 8px;
}

.board-table th {
    background-color: #f2f2f2;
}
</style>