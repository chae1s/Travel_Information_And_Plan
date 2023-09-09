<template>
  <div class="schedule_chat">
    <h2>일정 채팅룸</h2>
    <div>
      <ul>
        <li v-for="(room, index) in chatRooms" :key="room.id">
          <router-link :to="'/chat/' + room.id">
            <div class="chatRoom-data">
              <div class="chatRoom-data-image"></div>
              <div class="chatRoom-data-text">
                <span class="room-name">{{ room.roomName }}</span>
                <span class="latest-message">{{ room.latestMessage }}</span>
              </div>
            </div>
          </router-link>
          <!-- 채팅방 사이에 구분선 추가 -->
          <hr v-if="index < chatRooms.length - 1">
        </li>
      </ul>
    </div>
  </div>
</template>


<script>
import {getChatRooms} from "@/api/index";
export default {
  data() {
    return {
      chatRooms: [], // API에서 받아온 채팅방 리스트 저장
    };
  },
  mounted() {
    this.loadChatRooms(); // 컴포넌트가 마운트될 때 채팅방 리스트 불러오기
  },
  methods: {
    async loadChatRooms() {
      try {
        const response = await getChatRooms();

        if (response.status === 200) {
          const data = await response.data;
          this.chatRooms = data;
        } else {
          console.error("HTTP 응답 상태 코드가 200이 아닙니다.");
        }
      } catch (error) {
        console.error("채팅방 리스트 불러오기 오류:", error);
      }
    },
  },
};
</script>

<style scoped>
h2 {
  padding: 30px;
  margin-left: 10px;
  text-align: left;
}
.schedule_chat {
  margin-left: 100px; /* 위치 수정 예정*/
  width: 370px;
  height: 626px;
  border-radius: 10px;
  background-color: #E5F1FF;
}
.chatRoom-data {
  display: flex;
  align-items: center;
}
.chatRoom-data-image {
  margin-left: 20px;
  width: 60px;
  height: 60px;
  border-radius: 20px;
  background-color: #FAED7D;
}
.chatRoom-data-text {
  flex: 1;
  margin-left: 20px;
  text-align: left;
}
hr {
  border: 1px solid #ccc; /* 선의 스타일을 설정합니다. */
  margin: 10px 0; /* 채팅방 항목 위아래 여백을 조절합니다. */
}
.room-name {
  font-weight: bold;
  font-size: 18px;
}
.latest-message {
  display: block; /* 블록 레벨 요소로 설정합니다. */
  font-size: 16px; /* 원하는 크기로 조절하세요. */
}
</style>