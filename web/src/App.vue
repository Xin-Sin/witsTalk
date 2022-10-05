<script lang="ts" setup>
import {computed, ref} from 'vue'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import NotFound from './components/NotFound.vue'

const routes: Map<string, any> = new Map<string, any>()
routes.set('/', Login)
routes.set('/register', Register)
const currentPath = ref(window.location.hash)
window.addEventListener('hashchange', () => {
  currentPath.value = window.location.hash
})
const currentView = computed(() => {
  return routes.get(currentPath.value.slice(1) || '/') || NotFound
})
</script>

<template>
  <component :is="currentView"/>
</template>

<style scoped>
</style>
