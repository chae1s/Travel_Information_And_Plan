<template>
    <div class="pagination">
        <v-icon @click="goToPage(1)" :disabled="currentPage === 1">mdi-chevron-double-left</v-icon>
        <v-icon @click="previousPage" :disabled="currentPage === 1">mdi-chevron-left</v-icon>

        <button v-for="page in displayedPages" :key="page" @click="goToPage(page)" :class="{ active: page === currentPage }">
            {{ page }}
        </button>

        <v-icon @click="nextPage" :disabled="currentPage === totalPages">mdi-chevron-right</v-icon>
        <v-icon @click="goToPage(totalPages)" :disabled="currentPage === totalPages">mdi-chevron-double-right</v-icon>
    </div>
</template>

<script>
export default {
    name: 'Pagination',
    props: {
        totalPages: Number, // 상위 컴포넌트에서 받아온 totalPages
    },
    data() {
        return {
            currentPage: 1,
            pagesToShow: 5, // 보여줄 페이지 버튼 수
        };
    },
    computed: {
        displayedPages() {
            const start = Math.max(1, this.currentPage - Math.floor(this.pagesToShow / 2));
            const end = Math.min(this.totalPages, start + this.pagesToShow - 1);
            return Array.from({ length: end - start + 1 }, (_, i) => start + i);
        },
    },
    methods: {
        goToPage(page) {
            if (page >= 1 && page <= this.totalPages) {
                this.currentPage = page;
                this.$emit('page-changed', page);
            }
        },
        previousPage() {
            this.goToPage(this.currentPage - 1);
        },
        nextPage() {
            this.goToPage(this.currentPage + 1);
        },
    },
};
</script>

<style scoped>
.pagination {
    display: flex;
    list-style: none;
    justify-content: center;
    padding: 0;
}

.pagination button {
    margin: 0 2px;
    cursor: pointer;
}

.pagination button.active {
    font-weight: bold;
}
</style>