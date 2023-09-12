<template>

  <div class="chatRoom-container">
    <!--  채팅방 윗부분  -->
    <div class="chatRoom-top-container">
      <div class="chatRoom-top-data-container">
        <div>
          <router-link to="/chat-room-list">
            <v-img src="@/assets/images/icons/to-room-list.png" width="20px" height="20px" class="to-room-list" :inline="true" />
          </router-link>
        </div>
        <div class="chatRoom-data-image"></div>
        <div class="chatRoom-data-text">
          <div class="room-name">{{ roomData.roomName }}</div>
          <div class="room-member-count-container"></div>
          <v-img src="@/assets/images/icons/member-count.png" width="15px" height="15px" class="room-count" :inline="true" />
          <span class="member-count">{{roomData.count}}</span>
        </div>
      </div>
    </div>
    <!--  채팅방 중간 부분  -->
    <div class="chatRoom-chatting-container" ref="chatContainer">
      <!-- 날짜 표시 -->
      <div v-for="(message, index) in messages" :key="index">
        <template v-if="message.dateChanged">
          <div class="date-change">{{ message.date }}</div>
        </template>
        <div v-if="!message.empty" class="chat-message" :class="{ 'sent-message': message.sender === '나' || message.sender === this.nickname}">
          <div class="message-sender">{{ message.sender }}</div>
          <div class="message-text">{{ message.message }}</div>
          <div class="message-time">{{ message.time ? formatTime(message.time) : '' }}</div>
        </div>
      </div>
    </div>
    <!--  채팅방 전송 부분  -->
    <div class="chatRoom-send-container">
      <form @submit.prevent="sendMessage">
        <textarea :value="newMessage" @input="updateMessage" @keydown.enter="sendMessageOnEnter" placeholder="메시지를 입력하세요"></textarea>
        <button type="submit">전송</button>
      </form>
    </div>
  </div>
</template>


<script>
import {getChatRoomData,getChatMessages,sendChatMessage,readUserInfo} from "@/api/index";
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client'

export default {
  data(){
    return{
      roomData:{},
      stompClient: null,
      messages: [],
      newMessage:'', // 입력한 새 메시지
      nickname:'',
      currentDate: '',
    };
  },
  props: {
    id: {
      type: String, // Long형의 경우 Number로 타입 지정
      required: true // 필수 prop으로 지정 (옵션)
    }
  },

  methods:{
    async getRoomData() {
      try{
        const response = await getChatRoomData(this.id);
        this.roomData =  response.data;
      }catch (error){
        console.error("채팅방 정보 불러오기 오류:", error);
      }
    },
    async loadChatMessages() {
      let previousDate = '';
      try {
        const response = await getChatMessages(this.id);
        this.messages = response.data;

        this.messages.forEach((message, index) => {
          const messageDate = new Date(message.time).toLocaleDateString();
          if (messageDate !== previousDate) {
            this.messages.splice(index, 0, {
              dateChanged: true,
              date: messageDate,
            });
            previousDate = messageDate;
          }
          // 메시지를 보낸 사용자가 '나'일 경우 닉네임으로 변경
          // if (message.sender === this.nickname) {
          //   message.sender = '나';
          // }
          // 빈 메시지일 경우 플래그 설정
          if (!message.message) {
            message.empty = true;
          }
        });
      } catch (error) {
        console.error("채팅 메시지 불러오기 오류:", error);
      }
    },

    sendMessage() {
      if (this.newMessage.trim() === '') {
        return; // 빈 메시지는 보내지 않음
      }
      // 서버로 메시지 전송
      const messageData = {
        message: this.newMessage,
      }
      // sendChatMessage(this.id, messageData);
      this.stompClient.send(`/app/chat/${this.id}`, JSON.stringify(messageData));

      // 로컬에 메시지 추가
      this.messages.push({
        sender: this.nickname,
        message: this.newMessage,
        time: new Date(),
        // time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) // 시간과 분만 포맷

      });

      // 입력창 초기화
      this.newMessage = '';

      this.$nextTick(() => {
        let chatContainer = this.$refs.chatContainer;

        chatContainer.scrollTo({ top: chatContainer.scrollHeight, behavior: 'smooth' });
      });
    },

    async loadUserInfo(){
      try{
        const userInfo = await readUserInfo();
        this.nickname = userInfo.data.nickname;
      }catch(error){
        console.error("사용자 정보 불러오기 오류:", error);
      }
    },

    updateMessage(event) {
      this.newMessage = event.target.value;
    },
    formatTime(timeString) {
      const date = new Date(timeString);
      const hours = date.getHours();
      const minutes = date.getMinutes();

      const period = hours < 12 ? "오전" : "오후";
      const formattedHours = hours % 12 === 0 ? 12 : hours % 12;
      const formattedMinutes = minutes < 10 ? `0${minutes}` : minutes;

      return `${period} ${formattedHours}:${formattedMinutes}`;
    },
    sendMessageOnEnter(event) {
      if (event.key === 'Enter' && !event.shiftKey) {
        event.preventDefault(); // Enter 키 기본 동작 방지 (새 줄 추가)
        this.sendMessage(); // sendMessage 함수 호출
      }
    },

    // WebSocket 연결
    connectWebSocket() {
      console.log("연결 시작")
      const socket = new SockJS("http://localhost:8080/chatting"); // WebSocket 엔드포인트 URL
      this.stompClient = Stomp.over(socket);
      console.log("연결중")

      this.stompClient.connect({}, frame => {
        // 연결 성공 시 처리
        console.log("WebSocket 연결 성공",frame);

        // 구독 설정
        this.stompClient.subscribe(`/topic/chat/${this.id}`, (message) => {
          console.log("message.body="+message.body);
          // 메시지를 받았을 때 처리
          const receivedMessage = JSON.parse(message.body);
          this.messages.push({
            sender: receivedMessage.sender,
            message: receivedMessage.text,
            time: new Date(),
          });
        });
      });
    },

  },
  watch: {
    messages() {
      // 화면에 추가된 후 동작하도록
      this.$nextTick(() => {
        let chatContainer = this.$refs.chatContainer;

        chatContainer.scrollTo({ top: chatContainer.scrollHeight, behavior: 'smooth' });
      });
    },
  },

  mounted() {
    this.loadUserInfo();
    this.loadChatMessages();
    this.getRoomData();
  },
  created() {
    this.connectWebSocket();
  }
};

</script>



<style>
.chatRoom-container {
  margin-left: 100px; /* 위치 수정 예정*/
  width: 370px;
  height: 626px;
  border-radius: 10px;
  background-color: #E5F1FF;
  position: relative; /* 부모 컨테이너를 기준으로 절대 위치 설정 */
}
/* 채팅방 윗부분 */
.chatRoom-top-container{
  width: 100%;
  height: 100px;
  border-radius: 10px;
  background-color: #E5F1FF;
}
.to-room-list{
  margin-right: 10px;
}
.chatRoom-top-data-container{
  padding: 20px;
  display: flex;
}
.chatRoom-data-image {
  width: 57px;
  height: 57px;
  border-radius: 25px;
  background-color: #FAED7D;
}
.chatRoom-data-text{
  flex: 1;
  margin-left: 16px;
  text-align: left;
}
.room-name {
  font-weight: bold;
  font-size: 20px;
}
.room-member-count-container{
  margin-top: 7px;
}
.member-count{
  margin-left: 5px;
  font-size: 17px;
}
/* 채팅방 중간 부분 (채팅메시지) */
.chatRoom-chatting-container {
  flex: 1; /* 중간 부분이 남은 공간을 모두 차지하도록 설정 */
  height: 430px;
  overflow-y: scroll;
  margin-bottom: 10px;
}

.chatRoom-chatting-container::-webkit-scrollbar {
  width: 10px;
}

.chatRoom-chatting-container::-webkit-scrollbar-thumb {
  background-color: #DADADA;
  border-radius: 10px;
}
.chatRoom-chatting-container::-webkit-scrollbar-track {
  background-color: #E5F1FF;
  border-radius: 10px;
}

.date-change {
  text-align: center;
  font-size: 14px;
  color: #999;
  margin: 10px 0;
}
.chat-message{
  text-align: left;
  margin-left: 20px;
  margin-right: 15px;
  margin-bottom: 10px; /* 각 메시지 사이에 간격을 줍니다. */
}
.sent-message {
  text-align: right;
}
.message-sender {
  font-weight: bold;
}
.message-time{
  font-size: 13px;
  color: #999;
}
/* 채팅방 맨 아래 부분 */
.chatRoom-send-container {
  position: absolute; /* 절대 위치 설정 */
  bottom: 0; /* 아래로부터 0px 떨어져 있도록 설정 */
  left:0;
  width:100%;
  height: 80px;
  background-color: #F1F7FF;
}
textarea {
  width: 80%;
  height: 100%; /* 부모의 100% 높이를 차지하도록 설정 */
  padding: 10px; /* 텍스트 영역 주변 여백 조절 */
  box-sizing: border-box; /* 패딩과 경계 상자 크기 포함 */
  border: none; /* 테두리 제거 */
  resize: none; /* 크기 조절 비활성화 */
  font-size: 14px; /* 원하는 글꼴 크기 설정 */
  outline: none;
}

button {
  height: 100%; /* 부모의 100% 높이를 차지하도록 설정 */
  background-color: #99C7FF; /* 원하는 버튼 배경색 설정 */
  color: white; /* 버튼 텍스트 색상 설정 */
  border: none;
  border-radius: 10px;
  padding: 10px 20px; /* 텍스트 주변 여백 조절 */
  font-size: 14px; /* 원하는 글꼴 크기 설정 */
}

</style>