<template>
    <!-- 내가 댓글을 작성한 게시글 출력 -->
    <div class="sidebar_main">
        <div class="main_title">내가 쓴 댓글</div>
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
export default {
    name: "MyCommentList"
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