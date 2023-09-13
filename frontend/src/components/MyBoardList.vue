<template>
<!-- 내가 작성한 게시글 출력 -->
    <div class="sidebar_main">
        <div class="main_title">내가 쓴 게시글</div>
        <table class="board_table">
            <thead>
            <tr>
                <th class="board_head_id"></th>
                <th class="board_head_title">게시글 제목</th>
                <th class="board_head_created">작성일</th>
                <th class="board_head_liked">좋아요</th>
                <th class="board_head_view_count">조회</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="i in 10">
                <td class="board_id" >{{ i }}</td>
                <td class="board_title" @click="">
                    <span>커뮤니티 게시글 제목</span>
                </td>
                <td class="board_created">2023-00-00</td>
                <td class="board_liked">0</td>
                <td class="board_view_count">0</td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import Pagination from "@/components/Pagination.vue";
import {readBoards} from "@/api";
export default {
    name: "MyBoardList",
    components: {
        Pagination,
    },
    data() {
        return {
            boards: [], // 게시판 목록을 담을 배열 추가
            totalPages: 1, // 초기값 설정 (예시로 10을 사용)
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

    .board_table {
        width: 100%;
        border-top: 1px solid #DADADA;
        border-collapse: collapse;
        cursor: default;
    }

    .board_table thead {
        font-size: 16px;
    }

    .board_table thead th {
        padding: 7px 10px;
        border-bottom: 1px solid #DADADA;
    }

    .board_table tbody td {
        border-bottom: 1px solid #DADADA;
    }

    .board_id {
        width: 55px;
    }

    .board_title {
        text-align: left;
        padding: 9px 5px;
    }

    .board_title span {
        cursor: pointer;
    }

    .board_created {
        width: 150px;
    }

    .board_liked, .board_view_count {
        width: 70px;
    }
</style>