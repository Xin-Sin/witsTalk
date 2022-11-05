<template>
  <el-container>
    <el-header class="header">

    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu
            :collapse="isCollapse"
            default-active="1"
            @mouseenter="isCollapse = false"
            @mouseleave="isCollapse = true"
        >
          <el-menu-item index="1">
            <el-icon>
              <ChatDotRound/>
            </el-icon>
            <template #title>聊天室</template>
          </el-menu-item>
          <el-menu-item index="2">
            <el-icon>
              <Mic/>
            </el-icon>
            <template #title>语音聊天</template>
          </el-menu-item>
          <el-menu-item index="3">
            <el-icon>
              <Files/>
            </el-icon>
            <template #title>文件传输</template>
          </el-menu-item>
          <el-menu-item index="4">
            <el-icon>
              <Setting/>
            </el-icon>
            <template #title>个人设置</template>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <component :is="currentView"/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts" setup>
import {ChatDotRound, Files, Mic, Setting} from '@element-plus/icons-vue'
import {computed, onMounted, ref} from 'vue'
import NotFound from "../NotFound.vue";
import Chat from './Chat.vue'
import {ElMessage} from "element-plus";

const routes: Map<string, any> = new Map<string, any>();
routes.set('', Chat)
const currentPath = ref(window.location.hash)
window.addEventListener('hashchange', () => {
  currentPath.value = window.location.hash
})
const currentView = computed(() => {
  return routes.get(currentPath.value.slice(1).split("/")[2] || '') || NotFound
})
//主逻辑
const isCollapse = ref<boolean>(true);
const username = ref<string>();
const headimgBase64 = ref<string>();
const userToken = ref<string>();
onMounted(() => {
  let name = window.sessionStorage.getItem("username");
  let headimg = window.sessionStorage.getItem("headimg");
  let token = window.sessionStorage.getItem("token");
  if (name && headimg && token) {
    username.value = name;
    headimgBase64.value = headimg;
    userToken.value = token;
  } else {
    ElMessage.error("你还没有登录，请登陆后再试")
    window.location.hash = "/"
  }
})
</script>

<style scoped>
.header {
  background: #0061AB;
}
</style>