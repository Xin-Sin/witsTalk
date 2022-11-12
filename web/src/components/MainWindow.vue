<template>
  <el-container>
    <el-header class="header">
      <div class="logo">
        <el-icon><img alt="" class="logo" src="../assets/logo.svg"/></el-icon>
      </div>
      <div class="user">
        <el-avatar :size="50" :src="headimgBase64" style="margin-right: 10px"/>
        <div ref="usernameDiv" class="username"></div>
        <el-dropdown @command="handlerUserSettingsCommand">
        <span class="el-dropdown-link" style="color: #000000">
          {{ username }}<el-icon class="el-icon--right"><ArrowDown/></el-icon>
        </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="settings">用户设置</el-dropdown-item>
              <el-dropdown-item command="exit">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu
            :collapse="isCollapse"
            default-active="1"
            @mouseenter="isCollapse = false"
            @mouseleave="isCollapse = true"
            :collapse-transition="true"
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
      <el-main style="width: 100%; height: 80vh">
        <component :is="currentView"/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts" setup>
import {ArrowDown, ChatDotRound, Files, Mic, Setting} from '@element-plus/icons-vue'
import {computed, onMounted, ref} from 'vue'
import NotFound from "./NotFound.vue";
import Chat from '../page/chat/Chat.vue'
import FileTransfer from '../page/file/FileTransfer.vue'
import UserSettings from '../page/setting/UserSettings.vue'
import VoiceChat from '../page/voice/VoiceChat.vue'
import {ElMessage} from "element-plus";

const routes: Map<string, any> = new Map<string, any>();
routes.set('', Chat)
routes.set('file', FileTransfer)
routes.set('settings', UserSettings)
routes.set('voice', VoiceChat)
const currentPath = ref(window.location.hash)
window.addEventListener('hashchange', () => {
  currentPath.value = window.location.hash
})
const currentView = computed(() => {
  return routes.get(currentPath.value.slice(1).split("/")[2] || '') || NotFound
})
//主逻辑
const isCollapse = ref<boolean>(true);
const headimgBase64 = ref<string>();
const userToken = ref<string>();
const username = ref<string>();
onMounted(() => {
  let name = window.sessionStorage.getItem("username");
  let headimg = window.sessionStorage.getItem("headimg");
  let token = window.sessionStorage.getItem("token");
  if (name && headimg && token) {
    username.value = name;
    headimgBase64.value = "data:image/png;base64," + headimg;
    userToken.value = token;
  } else {
    ElMessage.error("你还没有登录，请登陆后再试")
    window.location.hash = "/"
  }
})
const handlerUserSettingsCommand = function (command: string) {
  switch (command) {
    case "settings":
      ElMessage.warning("功能未实现！")
      break;
    case "exit":
      window.sessionStorage.removeItem("username");
      window.sessionStorage.removeItem("headimg");
      window.sessionStorage.removeItem("token");
      ElMessage.success("成功退出登录，返回登陆页面");
      window.location.hash = "/"
      break;
    default:
      ElMessage.error("CNM出问题了，请将此消息内容复制并发送给网站管理员   command=" + command)
      break;
  }
}
</script>

<style scoped>
.header {
  display: flex;
  background-color: rgba(0, 97, 171, 75%);
  justify-content: space-between;
  align-items: center;
}

img.logo {
  width: 40px;
  height: 40px;
}

div.logo {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

div.user {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  height: 100%;
}
</style>