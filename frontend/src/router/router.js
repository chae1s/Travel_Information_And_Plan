import {createWebHistory, createRouter} from "vue-router";
import MakeSchedule from "@/views/MakeSchedule.vue";
import Home from "@/views/Home.vue";
import MakeScheduleDetail from "@/views/MakeScheduleDetail.vue";
import SignUp from "@/views/SignUp.vue";
import LoginView from "@/views/LoginView.vue";
import BoardCreate from "@/views/BoardCreate.vue";
import MyPage from "@/views/MyPage.vue";
import ItemList from "@/views/ItemList.vue";
import ItemDetail from "@/views/ItemDetail.vue";
import LogoutView from "@/components/Logout.vue";
import InvitationList from "@/components/InvitationList.vue";
import UserInfo from "@/components/UseInfo.vue";
import UserInfoView from "@/views/UserInfoView.vue";
import MyPageMain from "@/views/MyPageMain.vue";
import MyPageEdit from "@/views/MyPageEdit.vue";
import MyPagePassword from "@/views/MyPagePassword.vue";
import MyPageDelete from "@/views/MyPageDelete.vue";
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
            component: UserInfoView, // myPage/main
            children: [
                {path: 'update', name: 'update', component: UserInfo},      // myPage edit
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
            component: MyPageMain
        },
        {
            path: '/myPage/edit',
            name: 'MyPageEdit',
            component: MyPageEdit
        },
        {
            path: '/myPage/password',
            name: 'MyPagePassword',
            component: MyPagePassword
        },
        {
            path: '/myPage/delete',
            name: 'MyPageDelete',
            component: MyPageDelete
        }
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