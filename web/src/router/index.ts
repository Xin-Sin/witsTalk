import {createRouter, createWebHashHistory, RouteRecordRaw} from 'vue-router'
import {useStore} from "../store";
import {ElMessage} from "element-plus";
import HomePage from "../views/home/index.vue";
import chatPage from "../views/chat/index.vue";
import filePage from "../views/file/index.vue";
import voicePage from "../views/voice/index.vue";
import settingPage from "../views/setting/index.vue";
import {getRouter} from "../api/user";

const router = createRouter({ 
    history: createWebHashHistory(),
    routes: [
        {
            path:"/",
            name:"login",
            component: () =>import("../views/login/index.vue"),
            meta:{
                title:'登陆'
            }
        },
        {
            path: "/reg",
            name: 'register',
            component: () => import("../views/register/index.vue"),
            meta:{
                title: "注册"
            }
        },
        {
            path: "/404",
            name: '404',
            component: () => import("../components/NotFound.vue"),
            meta: {
                title: "哎呦! 你要找的页面被外星人抓走啦"
            }
        }
    ]
})
// 全局路由守卫
router.beforeEach(async (to, from)=>{
    // 设置网页标题
    document.title =  "智慧语音-" + `${to.meta.title}`;
    // 获取状态管理器
    let store = useStore();
    let routers = router.getRoutes();
    // 用户刷新页面时重新加载路由
    if (routers.length === 3 && store.userinfo !== null){
        for (const datum of store.userRoute!) {
            if (datum.parentId === null){
                router.addRoute({path: datum.path, name: datum.name, component: () => import(datum.component), meta: {title: datum.title}})
            }else{
                router.addRoute(datum.parentId, {path: datum.path, name: datum.name, component: () => import(datum.component), meta: {title: datum.title}})
            }
        }
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