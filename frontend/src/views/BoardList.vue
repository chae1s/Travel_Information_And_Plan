<template>
    <div>
        <ul>
            <li v-for="post in paginatedPosts" :key="post.id">
                <!-- 게시글 내용 출력 -->
            </li>
        </ul>

        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li class="page-item" :class="{ 'disabled': currentPage === 1 }">
                    <a class="page-link" href="#" @click="setCurrentPage(currentPage - 1)" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li v-for="page in totalPages" :key="page" class="page-item" :class="{ 'active': page === currentPage }">
                    <a class="page-link" href="#" @click="setCurrentPage(page)">{{ page }}</a>
                </li>
                <li class="page-item" :class="{ 'disabled': currentPage === totalPages }">
                    <a class="page-link" href="#" @click="setCurrentPage(currentPage + 1)" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</template>

<script>
import {readBoards} from "@/api";
export default {
    data() {
        return {
            posts: [
                // 게시글 데이터
            ],
            currentPage: 1,
            postsPerPage: 10,
        };
    },
    computed: {
        totalPages() {
            return Math.ceil(this.posts.length / this.postsPerPage);
        },
        paginatedPosts() {
            const start = (this.currentPage - 1) * this.postsPerPage;
            const end = start + this.postsPerPage;
            return this.posts.slice(start, end);
        },
    },
    methods: {
        setCurrentPage(page) {
            if (page >= 1 && page <= this.totalPages) {
                this.currentPage = page;
            }
        },
        async setPosts() {
            try {
                const {data} = await readBoards(this.currentPage);
                console.log(data);
                this.posts = data.content;
            } catch (error) {
                console.log(error.response.data);
                this.logMessage = error.response.data;
            }
        }
    },
    created() {
        this.setPosts(this.page);
    },
};
</script>