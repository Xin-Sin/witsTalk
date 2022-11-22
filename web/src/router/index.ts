import {createRouter, createWebHashHistory} from 'vue-router'
import { defineAsyncComponent } from 'vue'

const router = createRouter({ 
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            name: 'login',
            component: () => import(`../views/login/index.vue`),
            meta: {
                title: '登陆',
            },
        },
        {
            path: '/404',
            name: '404',
            component : () => import(`../components/NotFound.vue`),
            meta: {
                title: '哎呦，页面找不到了',
            }
        },
        {
            path: '/home',
            name: 'home',
            component: () => import(`../views/home/index.vue`),
            meta: {
                title: '首页',
            },
            redirect: '/home/chat',
            children: [
                {
                    path: 'chat',
                    name:'chat',
                    component: () => import(`../views/chat/index.vue`),
                    meta: {
                        title: '聊天频道',
                    }
                },
                {
                    path: 'file',
                    name:'file',
                    component: () => import(`../views/file/index.vue`),
                    meta: {
                        title: '文件分享',
                    }
                },
                {
                    path: 'voice',
                    name:'voice',
                    component: () => import(`../views/voice/index.vue`),
                    meta: {
                        title: '语音频道',
                    }
                },
                {
                    path: 'setting',
                    name:'setting',
                    component: () => import(`../views/setting/index.vue`),
                    meta: {
                        title: '个人设置',
                    }
                }
            ]
        },
        {
            path: '/*',
            redirect: '/404'
        }
    ]
})
// 全局路由守卫
router.beforeEach((to, from, next)=>{
    if (to.meta.title) {
        document.title =  "智慧语音-" + `${to.meta.title}`;
    }
    next()
})

router.afterEach((to, from)=>{
    console.log(to,from)
})

export default router