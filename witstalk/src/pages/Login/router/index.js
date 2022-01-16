import Vue from 'vue'
import Router from 'vue-router'
import Login from '../Login.vue'
import index from '../../index/index.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: index
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    }
  ]
})
