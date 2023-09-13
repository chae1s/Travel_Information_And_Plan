<template>
    <v-container>
        <!-- Board 정보 표시 -->
        <v-card>
            <v-card-title>
                <h2>{{ board.title }}</h2>
            </v-card-title>
            <v-card-subtitle>
                <v-row>
                    <v-col cols="6">작성자: {{ board.username }}</v-col>
                    <v-col cols="6" class="text-right">작성시간: {{ board.createdAt }}</v-col>
                </v-row>
            </v-card-subtitle>
            <v-card-text v-html="board.content"></v-card-text>
        </v-card>

        <!-- Comment 정보 표시 -->
        <v-card v-if="board.comments && board.comments.content.length > 0">
            <v-card-title>
                <h5>댓글</h5>
            </v-card-title>
            <v-card-text>
                <v-list>
                    <v-list-item-group>
                        <v-list-item v-for="comment in board.comments.content" :key="comment.id">
                            <v-list-item-content>
                                <v-list-item-title class="headline text-sm-left">
                                    <v-icon v-if="comment.parentId !== null">mdi-subdirectory-arrow-right</v-icon> <!-- 대댓글 아이콘 -->
                                    {{ comment.content }}
                                </v-list-item-title>
                                <v-list-item-subtitle class="text-sm-left">작성자: {{ comment.username }} | 작성시간: {{ comment.createdAt }}</v-list-item-subtitle>
                                <v-btn class="text-sm-left" @click="toggleReplyForm(comment.parentId === null ? comment.id : comment.parentId)">답글</v-btn>
                                <v-icon class="text-sm-right">mdi-comment-edit-outline</v-icon>
                                <v-icon class="text-sm-right">mdi-trash-can-outline</v-icon>

                                <v-card v-if="showReplyForm === comment.id">
                                    <v-card-title>
                                        <h5>댓글 쓰기</h5>
                                    </v-card-title>
                                    <v-card-text>
                                        <v-textarea
                                            v-model="newReply.content"
                                            label="Reply"
                                            outlined
                                            rows="3"
                                        ></v-textarea>
                                    </v-card-text>
                                    <v-card-actions>
                                        <v-btn @click="submitReply(comment.id)">댓글 쓰기</v-btn>
                                    </v-card-actions>
                                </v-card>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list-item-group>
                </v-list>
                <Pagination
                    :totalPages="totalPages"
                    @page-changed="handlePageChange"
                />
            </v-card-text>
        </v-card>
        <v-card v-else>
            <v-card-title>
                <h5>작성된 댓글이 없습니다.</h5>
            </v-card-title>
        </v-card>
        <v-card>
            <v-card-title>
                <h5>댓글 쓰기</h5>
            </v-card-title>
            <v-card-text>
                <v-textarea
                    v-model="newComment.content"
                    label="Comment"
                    outlined
                    rows="3"
                ></v-textarea>
                <v-btn @click="submitComment">작성</v-btn>
            </v-card-text>
        </v-card>
    </v-container>
</template>

<script>
import { readBoard, deleteBoard, createComment, updateBoard, deleteComment } from "@/api";
import Pagination from "@/components/Pagination.vue";
export default {
    name: 'BoardDetails',
    components: {
        Pagination
    },
    data() {
        return {
            board: {},
            newComment: {
                content: "",
            },
            showReplyForm: null,
            newReply: {
                content: "",
                parentId: null,
            },
            totalPages: 1,
        }
    },
    methods: {
        async fetchBoard(page) {
            try {
                const response = await readBoard(this.$store.state.boardId, page);
                this.board = response.data;
                this.totalPages = response.data.comments.lastPage;
            } catch (error) {
                console.error("Error fetching board:", error);
            }
        },
        async handlePageChange(page) {
            console.log(`페이지 변경: ${page}`);
            this.currentPage = page;
            await this.fetchBoard(page); // 페이지가 변경될 때마다 게시글 목록을 가져옴
        },
        async submitComment() {
            try {
                // 새로운 댓글 데이터를 서버로 보냄
                const boardId = this.$store.state.boardId; // 보드 ID를 가져옴
                const content = this.newComment.content; // 댓글 내용을 가져옴

                // 새로운 댓글 데이터를 서버로 보냄
                const response = await createComment(boardId, {
                    content: content,
                    parentId: null, // 또는 다른 값으로 설정 (선택적인 경우)
                });

                // 성공적으로 작성된 댓글을 받아옴
                const newComment = response.data;

                // 받아온 댓글을 board 데이터에 추가
                this.board.comments.content.push(newComment);

                // 입력란 초기화
                this.newComment.content = "";

            } catch (error) {
                console.error("Error submitting comment:", error);
            }
        },
        toggleReplyForm(commentId) {
            this.showReplyForm = this.showReplyForm === commentId ? null : commentId;
            this.newReply.parentId = commentId;
        },

        async submitReply(commentId) {
            try {
                const response = await createComment(this.$store.state.boardId, this.newReply);

                const newComment = response.data;

                if (this.newReply.parentId !== null) {
                    const targetComment = this.board.comments.content.find(comment => comment.id === this.newReply.parentId);
                    if (targetComment) {
                        if (!targetComment.comments) {
                            targetComment.comments = { content: [] };
                        }

                        // Find the index of the target comment
                        const targetIndex = this.board.comments.content.indexOf(targetComment);

                        // Find the index of the first comment after the target comment with parentId=null
                        const nextNullIndex = this.board.comments.content.findIndex((comment, index) => {
                            return index > targetIndex && comment.parentId === null;
                        });

                        // Insert the new comment before the comment with parentId=null
                        this.board.comments.content.splice(nextNullIndex, 0, newComment);
                    }
                } else {
                    this.board.comments.content.push(newComment);
                }

                this.newReply.content = "";
                this.showReplyForm = null;
            } catch (error) {
                console.error("Error submitting reply:", error);
            }
        },
    },
    created() {
        this.fetchBoard();
    },
};
</script>