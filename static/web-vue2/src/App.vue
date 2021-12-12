<template>
  <div id="app">
    <index msg="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
import index from './components/index.vue'

export default {
  name: 'app',
  components: {
    index
  }
}
</script>

<style>
#app {
  margin-top: 60px;
}
</style>
