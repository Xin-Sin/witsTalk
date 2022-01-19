import Vue from 'vue'
import Router from 'vue-router'
import login from '@/components/login'
import mainpage from '@/components/mainpage'
import chat from '@/pages/Chat/chat'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: login
    },
    {
      path:'/main',
      name:'main',
      component:mainpage,
      children:[
        {
          path:'chat',
          component:chat,
        },
      ],
    }
  ]
})
