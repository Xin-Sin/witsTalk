import {createApp, toRaw} from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import router from './router'
import { createPinia, PiniaPluginContext } from 'pinia'

const app = createApp(App)

// 别问 element-ui告诉我这么写的的:wzp
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

const piniaPlugin = (context: PiniaPluginContext) => {
    const { store } = context;
    // $subscribe state值发生变化时会执行传入的回调
    store.$subscribe(() => {
        // 每次修改值的时候更新localStorage数据
        sessionStorage.setItem(`pinia-${store.$id}`, JSON.stringify(toRaw(store.$state)))
    })
    // 每次构建项目的时候从本地存储取值
    const SData = sessionStorage.getItem(`pinia-${store.$id}`)
    const data = SData ? JSON.parse(SData) : {}
    // 并将取的值赋给state
    return {
        ...data
    }
}

const store = createPinia();
store.use(piniaPlugin)
app.use(ElementPlus)
    .use(router)
    .use(store)
    .mount('#app')
