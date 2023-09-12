import {createWebHistory, createRouter} from "vue-router";
import MakeSchedule from "@/views/MakeSchedule.vue";
import Home from "@/views/Home.vue";
import MakeScheduleDetail from "@/views/MakeScheduleDetail.vue";
import SignUp from "@/views/SignUp.vue";
import LoginView from "@/views/LoginView.vue";
import BoardCreate from "@/views/BoardCreate.vue";
import ItemList from "@/views/ItemList.vue";
import ItemDetail from "@/views/ItemDetail.vue";
import LogoutView from "@/components/Logout.vue";
import InvitationList from "@/components/InvitationList.vue";
import MyPageMain from "@/views/MyPageMain.vue";
import Password from "@/components/Password.vue";
import MyPageDelete from "@/components/UserDelete.vue";
import UserDelete from "@/components/UserDelete.vue";
import BoardList from "@/views/BoardList.vue";
import BoardDetail from "@/views/BoardDetail.vue";
import ItemReview from "@/views/ItemReview.vue";
import TravelMap from "@/views/TravelMap.vue";
import UserInfoPw from "@/components/UserInfoPw.vue";
import UserInfoEdit from "@/components/UserInfoEdit.vue";
const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Home',
            component: Home
        },
        {
            path: '/schedules/write',
            name: 'MakeSchedule',
            component: MakeSchedule
        },
        {
            path: '/schedules/:id/schedule-items',
            name: 'MakeScheduleDetail',
            component: MakeScheduleDetail
        },
        {
            path: '/sign-up',
            name: 'SignUp',
            component: SignUp
        },
        {
            path: '/logout',
            name: 'logout',
            component: LogoutView
        },
        {
            path: '/items-list',
            name: "ItemList",
            component: ItemList
        },
        {
            path: '/item-detail/read/:id',
            name: "ItemDetail",
            component: ItemDetail,
            props: true
        },
        {
            path: '/login',
            name: 'Login',
            component: LoginView
        },
        {
            path: '/board-create',
            name: 'BoardCreate',
            component: BoardCreate
        },
        {
            path: '/mypage',
            name: 'MyPage',
            component: MyPage,
        },
        {
            path: '/my-info',
            name: 'MyInfoView',
            component: UserInfoView,
            children: [
                {path: 'update', name: 'update', component: UserInfo},
            ]
        },
        // { // 나중에 메이트 보기 리스트 있으면 이렇게
        //     path: '/mate',
        //     name: 'mage',
        //     component: Mate,
        //     children: [
        //         {path: 'mate-invitation', name: 'InvitationList', component: InvitationList},
        //     ]
        // },
        {
            path: '/mate-invitation', name: 'InvitationList', component: InvitationList
        },
        {
            path: '/myPage/main',
            name: 'MyPageMain',
            component: MyPageMain,
            children: [
                {path: 'edit', name: 'ProfileEdit', component: ProfileEdit},
                {path: 'password', name: 'Password', component: Password},
                {path: 'delete', name: 'UserDelete', component: UserDelete}
            ]
        },
    ]
})

export default router
router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.auth)) {
        if (this.$store.state.token != '') {
            next();
        } else {
            alert("로그인이 필요한 페이지입니다.")
            router.push({path: '/login'})
        }
    } else {
        next();
    }
})