<template>
  <div>
    <div class="content-title">
      <strong>초대 리스트 조회</strong>
    </div>
    <br>
    <table class="invitation-table">
      <thead>
      <tr>
        <th></th>
        <th>호스트</th>
        <th>일정 제목</th>
        <th>초대 시각</th>
        <th>수락여부 결정하기</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(invitation, index) in invitations" :key="index" class="invitation-item">
        <td>{{ index + 1 }}</td>
        <td>{{ invitation.scheduleHost }}</td>
        <td>{{ invitation.scheduleTitle }}</td>
        <td>{{ invitation.invitedTime }}</td>
        <td>
          <button class="accept-button" @click="acceptInvitation(invitation)" v-if="!decision">수락</button>
          <button class="reject-button" @click="rejectInvitation(invitation)" v-if="!decision">거절</button>
          <div v-if="accepted && decision">수락 완료</div>
          <div v-if="!accepted && decision">거절 완료</div>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>


<script>
import axios from 'axios';
export default {
  data() {
    return {
      invitations: [], // 초대 리스트 데이터를 저장할 배열
      accepted: false,
      decision: false
    };
  },
  mounted() {
    // 페이지 로드 시 초대 리스트를 가져오는 메서드 호출
    this.fetchInvitations();
  },
  methods: {
    // 초대 리스트 가져오기
    fetchInvitations() {
      // 서버로부터 초대 리스트 데이터를 가져오는 HTTP 요청을 보냅니다.
      // Controller의 GET 엔드포인트에 해당하는 URL을 사용합니다.
      axios.get('/schedules/invited-users') // 이 부분은 실제 URL에 맞게 수정해야 합니다.
          .then((response) => {
            this.invitations = response.data;
          })
          .catch((error) => {
            console.error('초대 리스트를 불러오는 중 오류 발생:', error);
          });
    },
    // 초대 수락 처리
    acceptInvitation(invitation) {
      // 서버로 초대 수락 요청을 보냅니다.
      // Controller의 POST 엔드포인트에 해당하는 URL을 사용합니다.
      console.log("invitation.matesId=",invitation.matesId);
      console.log("invitation.scheduleId=",invitation.scheduleId);

      axios.post(`/schedules/invited-users/${invitation.scheduleId}/acceptance/${invitation.matesId}`)
          .then((response) => {
            this.accepted = true;
            this.decision = true;
          })
          .catch((error) => {
            console.error('초대 수락 중 오류 발생:', error);
          });
    },
    // 초대 거절 처리
    rejectInvitation(invitation) {
      // 서버로 초대 거절 요청을 보냅니다.
      // Controller의 POST 엔드포인트에 해당하는 URL을 사용합니다.
      axios.post(`/schedules/invited-users/${invitation.scheduleId}/rejection/${invitation.matesId}`)
          .then((response) => {
            this.accepted = false;
            this.decision = true;
          })
          .catch((error) => {
            console.error('초대 거절 중 오류 발생:', error);
          });
    },
  },
};
</script>

<style scoped>
.content-title{
  font-size: 25px;
}
.invitation-table {
  width: 50%;
  border-collapse: collapse;
  margin-left: 370px;
  margin-right: 400px;

}

th, td {
  border: 1px solid #ccc;
  padding: 8px;
  border: none;

}

th {
  //background-color: #f2f2f2;
}
td{
  //text-align: left;
}

/* 초대시간 컬럼의 간격을 줄입니다 */
td:nth-child(4) {
  /* 필요한 간격으로 조절하세요 */
  width:200px;
}
button {
  margin-right: 20px; /* 버튼 간의 간격 조절 */
  width: 50px;
  height: 30px;
  //display: inline-block;
  border-radius: 10px;
  line-height: 23px;
  cursor: pointer;
  text-align: center;
  border:none;
}
.accept-button{
  background-color: #FFE866;
  color: #616161;
}
</style>