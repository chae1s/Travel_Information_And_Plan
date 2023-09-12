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
import MyScheduleList from "@/components/MyScheduleList.vue";
import MySchedulePost from "@/components/MySchedulePost.vue";
import LikedItemList from "@/components/LikedItemList.vue";
import MyBoardList from "@/components/MyBoardList.vue";
import MyItemReviewList from "@/components/MyItemReviewList.vue";
import MyCommentList from "@/components/MyCommentList.vue";
import ScheduleBoardList from "@/views/ScheduleBoardList.vue";
import ScheduleBoardPost from "@/views/ScheduleBoardPost.vue";
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
            path: '/my-page',
            name: 'MyPageMain',
            component: MyPageMain,
            children: [
                {path: 'my-info', name: 'MyInformation', children: [
                        {path: 'edit', name: 'UserInfoEdit', component: UserInfoEdit},
                        {path: 'password', name: 'Password', component: Password},
                        {path: 'delete', name: 'UserDelete', component: UserDelete},
                    ]
                },
                {path: 'my-trip', name: 'MyTrip', children: [
                        {path: 'mate-invitation', name: 'InvitationList', component: InvitationList},
                        // {path: 'schedules', name: 'ScheduleList', component: ScheduleList}
                    ]
                }

            ]
        },
        {
            path: '/map',
            name:'TravelMap',
            component: TravelMap
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