<template>
<!--  임시로 여기서 작업 -->
  <div>
    <UserResearcher></UserResearcher>
  </div>
  <div>
<!--    <InvitationList></InvitationList>-->
  </div>
</template>
<script>
import InvitationList from "@/components/InvitationList.vue";
import UserResearcher from '@/components/UserResearcher.vue';
export default {
  components: {
    UserResearcher,
    InvitationList
  },
  // ...
}

</script>