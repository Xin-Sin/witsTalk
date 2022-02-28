// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'element-ui/lib/theme-chalk/display.css';
import TDesign from 'tdesign-vue';
import verify from 'vue-monoplasty-slide-verify';
// 引入组件库全局样式资源
import 'tdesign-vue/es/style/index.css';

Vue.use(ElementUI)
Vue.use(TDesign);
Vue.use(verify);
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
