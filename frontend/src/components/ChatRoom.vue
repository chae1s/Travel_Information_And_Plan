<template>

  <div class="chatRoom-container">
    <!--  채팅방 윗부분  -->
    <div class="chatRoom-top-container">
      <div class="chatRoom-top-data-container">
        <div>
          <router-link to="/chat-room-list">
            <v-img src="@/assets/images/icons/to-room-list.png" width="20px" height="20px" class="to-room-list" inline="true" />
          </router-link>
        </div>
        <div class="chatRoom-data-image"></div>
        <div class="chatRoom-data-text">
          <div class="room-name">{{ roomData.roomName }}</div>
          <div class="room-member-count-container"></div>
            <v-img src="@/assets/images/icons/member-count.png" width="15px" height="15px" class="room-count" inline="true" />
            <span class="member-count">{{roomData.count}}</span>
        </div>
      </div>
    </div>
    <!--  채팅방 중간 부분  -->
    <div class="chatRoom-chatting"></div>
    <!--  채팅방 전송 부분  -->
    <div class="chatRoom-send-container">
      <form>
        <textarea type="text" placeholder="메시지 입력"></textarea>
        <button type="submit">전송</button>
      </form>
    </div>
  </div>
</template>

// 채팅방 제목, 채팅방 멤버 수 불러오기
// UI 만들기 - 닉네임,텍스트,시간,채팅방 텍스트입력창, 전송버튼
// 채팅 진행 되도록 벡엔드 정보 받아오기

<script>
import {getChatRoomData} from "@/api/index";

export default {
  data(){
    return{
      roomData:{},
    };
  },
  props: {
    id: {
      type: Number, // Long형의 경우 Number로 타입 지정
      required: true // 필수 prop으로 지정 (옵션)
    }
  },
  mounted() {
    this.getRoomData();
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
  },
}
</script>



<style>
.chatRoom-container {
  margin-left: 100px; /* 위치 수정 예정*/
  width: 370px;
  height: 626px;
  border-radius: 10px;
  background-color: #E5F1FF;
  overflow-y: scroll;
  position: relative; /* 부모 컨테이너를 기준으로 절대 위치 설정 */
}
/* 채팅방 윗부분 */
.chatRoom-top-container{
  width: 350px;
  height: 100px;
  border-radius: 10px;
  background-color: #DADADA;
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
/* 채팅방 아래 부분 */
.chatRoom-chatting {
  /* 중간 부분 스타일 */
  flex: 1; /* 중간 부분이 남은 공간을 모두 차지하도록 설정 */

}
.chatRoom-send-container {
  position: absolute; /* 절대 위치 설정 */
  bottom: 0; /* 아래로부터 0px 떨어져 있도록 설정 */
  left:0;
  width:100%;
  height: 80px;
  background-color: #DADADA;
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