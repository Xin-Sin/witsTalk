import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
module.exports = {
  dev:{
    host: "0.0.0.0"
  }
}