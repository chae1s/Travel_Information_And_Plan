<template>
    <v-container>
        <!-- 게시물 제목 -->
        <v-card>
            <v-card-title>
                {{ post.title }}
            </v-card-title>
            <v-card-subtitle>
                글쓴 유저: {{ post.user }}
            </v-card-subtitle>
            <v-card-actions>
                <v-avatar>
                    <v-img :src="post.userProfilePic" alt="User Profile"></v-img>
                </v-avatar>
            </v-card-actions>
            <v-card-text>
                <!-- 글 내용 (HTML 형식) -->
                <div v-html="post.content"></div>
            </v-card-text>
        </v-card>

        <!-- 댓글 입력 폼 -->
        <v-card>
            <v-card-title>
                댓글 달기
            </v-card-title>
            <v-card-text>
                <v-textarea v-model="commentText" label="댓글 작성"></v-textarea>
            </v-card-text>
            <v-card-actions>
                <v-btn @click="addComment" color="primary">댓글 달기</v-btn>
            </v-card-actions>
        </v-card>

        <!-- 댓글 목록 -->
        <v-card v-for="(comment, index) in comments" :key="index">
            <v-card-title>
                {{ comment.user }}
            </v-card-title>
            <v-card-text>
                {{ comment.text }}
            </v-card-text>
            <!-- 대댓글 입력 폼 -->
            <v-card-actions>
                <v-textarea v-model="replyText[index]" label="대댓글 작성"></v-textarea>
                <v-btn @click="addReply(index)" color="primary">대댓글 작성</v-btn>
            </v-card-actions>
            <!-- 대댓글 목록 -->
            <v-card-text v-for="(reply, rIndex) in comment.replies" :key="rIndex">
                {{ reply.user }} (대댓글): {{ reply.text }}
            </v-card-text>
        </v-card>
    </v-container>
</template>

<script>
export default {
    name: 'BoardDetail',
    data() {
        return {
            post: {
                title: "게시물 제목",
                user: "게시물 작성자",
                userProfilePic: "URL_TO_PROFILE_PIC",
                content: "<p>게시물 내용 (HTML 형식)</p>",
            },
            commentText: "",
            comments: [],
            replyText: [],
        };
    },
    methods: {
        addComment() {
            // 댓글 추가 로직을 구현하세요
            const comment = {
                user: "댓글 작성자",
                text: this.commentText,
                replies: [],
            };
            this.comments.push(comment);
            this.commentText = "";
        },
        addReply(commentIndex) {
            // 대댓글 추가 로직을 구현하세요
            const reply = {
                user: "대댓글 작성자",
                text: this.replyText[commentIndex],
            };
            this.comments[commentIndex].replies.push(reply);
            this.replyText[commentIndex] = "";
        },
    },
};
</script>
