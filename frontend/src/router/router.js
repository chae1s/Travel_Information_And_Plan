import {createWebHistory, createRouter} from "vue-router";
import MakeSchedule from "@/views/MakeSchedule.vue";
import Home from "@/views/Home.vue";
import MakeScheduleDetail from "@/views/MakeScheduleDetail.vue";
import LoginView from '@/views/LoginView.vue';
import BoardCreate from '@/views/BoardCreate.vue'
import ItemList from "@/views/ItemList.vue";

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
            path: '/items-list',
            name: "ItemList",
            component: ItemList
        },
        {
            path: '/login',
            name: 'LoginView',
            component: LoginView
        },
        {
            path: '/board-create',
            name: 'BoardCreate',
            component: BoardCreate
        }
    ]
})

export default router