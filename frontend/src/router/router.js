import {createWebHistory, createRouter} from "vue-router";
import MakeSchedule from "@/views/MakeSchedule.vue";
import Home from "@/views/Home.vue";
import MakeScheduleDetail from "@/views/MakeScheduleDetail.vue";
import SignUp from "@/views/SignUp.vue";
import LoginView from "@/views/LoginView.vue";
import ItemList from "@/views/ItemList.vue";
import ItemDetail from "@/views/ItemDetail.vue";
import LogoutView from "@/components/Logout.vue";
import InvitationList from "@/components/InvitationList.vue";
import MyPageMain from "@/views/MyPageMain.vue";
import Password from "@/components/Password.vue";
import UserDelete from "@/components/UserDelete.vue";
import BoardList from "@/views/BoardList.vue";
import BoardDetails from "@/views/BoardDetails.vue";
import BoardCreate from "@/views/BoardCreate.vue";
import TravelMap from "@/views/TravelMap.vue";
import UserInfoEdit from "@/components/UserInfoEdit.vue";
import LikedItemList from "@/components/LikedItemList.vue";
import ScheduleBoardList from "@/views/ScheduleBoardList.vue";
import ScheduleBoardPost from "@/views/ScheduleBoardPost.vue";
import UpdateSchedule from "@/views/UpdateSchedule.vue";
import MyScheduleList from "@/components/MyScheduleList.vue";
import MySchedulePost from "@/components/MySchedulePost.vue";
import MyItemReviewList from "@/components/MyItemReviewList.vue";
import MyBoardList from "@/components/MyBoardList.vue";
import MyCommentList from "@/components/MyCommentList.vue";
import UserLikedByMe from "@/components/UserLikedByMe.vue";
import UserWhoLikedMe from "@/components/UserWhoLikedMe.vue";
import UserResearcher from "@/components/UserResearcher.vue";

import ItemReviews from "@/views/itemReviews.vue";
const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Home',
            component: Home
        },
        {
            path: '/sign-up',
            name: 'SignUp',
            component: SignUp
        },
        {
            path: '/login',
            name: 'Login',
            component: LoginView
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
            path: '/item-detail/read/:id/reviews',
            name: 'ItemReviews',
            component: ItemReviews,
            props: true
        },


        {
            path: '/board-create',
            name: 'BoardCreate',
            component: BoardCreate
        },
        {
            path: '/board-list',
            name: 'BoardList',
            component: BoardList
        },
        {
            path: '/board-details',
            name: 'BoarDetails',
            component: BoardDetails
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
                {path: 'likes-user-to', name: 'LikedByMe', component: UserLikedByMe},
                {path: 'likes-user-from', name: 'LikedMe', component: UserWhoLikedMe},
                {path: 'search', name: 'SearchUser', component: UserResearcher},
                {path: 'my-trip', name: 'MyTrip', children: [
                        {path: 'mate-invitation', name: 'InvitationList', component: InvitationList},
                        // {path: 'schedules', name: 'ScheduleList', component: ScheduleList}

                        {path: 'liked-items', name: 'LikedItemList', component: LikedItemList}
                    ]
                },
                {
                    path: 'my-post',
                    name: 'MyPost',
                    children: [
                        {path: 'schedules', name: 'ScheduleList', component: MyScheduleList},
                        {path: 'schedules/:id', name: 'SchedulePost', component: MySchedulePost},
                        {path: 'boards', name: 'MyBoardList', component: MyBoardList},
                        {path: 'review', name: 'MyItemReviewList', component: MyItemReviewList},
                        {path: 'comments', name: 'MyCommentList', component: MyCommentList},
                    ]
                }

            ]
        },


        {
            path: '/map',
            name:'TravelMap',
            component: TravelMap
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
            path: '/schedule-list',
            name: 'ScheduleBoardList',
            component: ScheduleBoardList
        },
        {
            path: '/schedule-details/:id',
            name: 'ScheduleBoardPost',
            component: ScheduleBoardPost
        },
        {
            path: '/schedules/update/:id',
            name: 'ScheduleUpdate',
            component: UpdateSchedule
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