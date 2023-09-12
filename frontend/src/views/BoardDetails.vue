<template>
    <v-container>
        <!-- Board 정보 표시 -->
        <v-card>
            <v-card-title>
                <h2>{{ board.title }}</h2>
            </v-card-title>
            <v-card-subtitle>
                <v-row>
                    <v-col cols="6">Username: {{ board.username }}</v-col>
                    <v-col cols="6" class="text-right">Created At: {{ board.createdAt }}</v-col>
                </v-row>
            </v-card-subtitle>
            <v-card-text v-html="board.content"></v-card-text>
        </v-card>

        <!-- Comment 정보 표시 -->
        <v-card v-if="board.comments && board.comments.content.length > 0">
            <v-card-title>
                <h5>Comments</h5>
            </v-card-title>
            <v-card-text>
                <v-list>
                    <v-list-item-group>
                        <v-list-item v-for="comment in board.comments.content" :key="comment.id">
                            <v-list-item-content>
                                <v-list-item-title class="headline">
                                    <v-icon v-if="comment.parentId !== null">mdi-reply</v-icon> <!-- 대댓글 아이콘 -->
                                    {{ comment.content }}
                                </v-list-item-title>
                                <v-list-item-subtitle>Username: {{ comment.username }} | Created At: {{ comment.createdAt }}</v-list-item-subtitle>
                                <v-btn @click="toggleReplyForm(comment.id)">Reply</v-btn>
                                <v-card v-if="showReplyForm === comment.id">
                                    <v-card-title>
                                        <h5>Write a Reply</h5>
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
                                        <v-btn @click="submitReply(comment.id)">Submit</v-btn>
                                    </v-card-actions>
                                </v-card>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list-item-group>
                </v-list>
            </v-card-text>
        </v-card>
        <v-card v-else>
            <v-card-title>
                <h5>No comments available</h5>
            </v-card-title>
        </v-card>
        <v-card>
            <v-card-title>
                <h5>Write a Comment</h5>
            </v-card-title>
            <v-card-text>
                <v-textarea
                    v-model="newComment.content"
                    label="Comment"
                    outlined
                    rows="3"
                ></v-textarea>
            </v-card-text>
            <v-card-actions>
                <v-btn @click="submitComment">Submit</v-btn>
            </v-card-actions>
        </v-card>
    </v-container>
</template>

<script>
import { readBoard, deleteBoard, createComment, updateBoard, deleteComment } from "@/api";

export default {
    name: 'BoardDetails',
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
        }
    },
    methods: {
        async fetchBoard() {
            try {
                const response = await readBoard(this.$store.state.boardId);
                this.board = response.data;
                console.log(response.data);
            } catch (error) {
                console.error("Error fetching board:", error);
            }
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

                // 받아온 댓글을 board 데이터에 추가
                this.board.comments.content.push(newComment);

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