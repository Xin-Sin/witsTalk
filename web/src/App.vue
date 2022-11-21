<!-- <script lang="ts" setup>
import {computed, ref} from 'vue'
import Login from './page/login/Login.vue'
import NotFound from './components/NotFound.vue'
import MainWindow from './components/MainWindow.vue'

const routes: Map<string, any> = new Map<string, any>()
routes.set('', Login)
routes.set('main', MainWindow)
const currentPath = ref(window.location.hash)
window.addEventListener('hashchange', () => {
  currentPath.value = window.location.hash
})
const currentView = computed(() => {
  return routes.get(currentPath.value.slice(1).split("/")[1] || '') || NotFound
})
</script>

<template>
  <component :is="currentView"/>
</template>

<style scoped>
</style> -->
<template>
  <router-view/>
</template>