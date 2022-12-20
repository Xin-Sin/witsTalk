import {createRouter, createWebHashHistory} from 'vue-router'
import {useStore} from "../store";
import {ElMessage} from "element-plus";
import HomePage from "../views/home/index.vue";
import chatPage from "../views/chat/index.vue";
import filePage from "../views/file/index.vue";
import voicePage from "../views/voice/index.vue";
import settingPage from "../views/setting/index.vue";

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
            path: '/reg',
            name: 'register',
            component: () => import(`../views/register/index.vue`),
            meta: {
                title: '注册',
            },
        },
        {
            path: '/404',
            name: '404',
            component: () => import(`../components/NotFound.vue`),
            meta: {
                title: '哎呦，页面找不到了',
            }
        }
    ]
})
// 全局路由守卫
router.beforeEach((to, from)=>{
    // 设置网页标题
    document.title =  "智慧语音-" + `${to.meta.title}`;
    // 获取状态管理器
    let store = useStore();
    let routers = router.getRoutes();
    // 判断用户是否已登陆，并且刷新页面
    if (store.userinfo !== null && routers.length === 3){
        router.addRoute({path: '/home', name: 'home', component: HomePage, meta: {title: '首页'}});
        router.addRoute("home",{path: 'chat', name:'chat', component: chatPage, meta: {title: '聊天频道'}})
        router.addRoute("home",{path: 'file', name:'file', component: filePage, meta: {title: '文件分享'}})
        router.addRoute("home",{path: 'voice', name:'voice', component: voicePage, meta: {title: '语音频道'}})
        router.addRoute("home",{path: 'setting', name:'setting', component: settingPage, meta: {title: '个人设置'}})
        return to.fullPath;
    }
    // 验证用户是否输入了正确的地址
    // 如果当前路由没有该地址，则跳转至404
    let num = 0;
    for (let i = 0; i < routers.length; i++) {
        if (routers[i].name === to.name){
            continue;
        }else{
            num++;
        }
        if (num === routers.length && to.name !== "404"){
            return {name:'404'}
        }
    }
    // 验证用户是否未登陆进入了其他页面
    if (to.name !== "login" && to.name !== "register" && to.name !== "404"){
        if (store.userinfo === null){
            ElMessage.warning("您还未登录,请进行登陆后重试");
            return {name:'login'}
        }
    }
})

export default router