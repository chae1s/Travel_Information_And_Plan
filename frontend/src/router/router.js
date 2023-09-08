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
import LogoutView from "@/views/LogoutView.vue";
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
            path: '/myPage',
            name: 'MyPage',
            component: MyPage
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