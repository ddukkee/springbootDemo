import { createRouter, createWebHistory } from 'vue-router'
import PageHome from '@/views/PageHome.vue'
import PageAbout from '@/views/PageAbout.vue'
import BoardList from '@/views/BoardList.vue'
import BoardDetail from '@/views/BoardDetail.vue'
import BoardModify from '@/views/BoardModify.vue'
import BoardWrite from '@/views/BoardWrite.vue'

const routes = [
    {
        path: '/',
        name: 'PageHome',
        component: PageHome
    },
    {
        path: '/about',
        name: 'About',
        component: PageAbout
    },
    {
        path: '/board/list',
        name: 'BoardList',
        component: BoardList
    },
    {
        path: '/board/detail',
        name: 'BoardDetail',
        component: BoardDetail
    },
    {
        path: '/board/modify',
        name: 'BoardModify',
        component: BoardModify
    },
    {
        path: '/board/write',
        name: 'BoardWrite',
        component: BoardWrite
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router