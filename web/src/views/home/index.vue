<template>
    <el-container>
      <el-header class="header">
        <div class="logo">
          <el-icon><img alt="" class="logo" src="/logo.svg"/></el-icon>
        </div>
        <div class="user">
          <el-avatar :size="50" :src="headimgBase64" style="margin-right: 10px"/>
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
        <el-aside :width="max_with + 'px'" class="aside">
          <el-menu
              :collapse="isCollapse"
              :default-active="active"
              @mouseenter="onMouseEnter"
              @mouseleave="onMouseLeave"
              :collapse-transition="true"
              router
          >
            <el-menu-item index="/home/chat">
              <el-icon>
                <ChatDotRound/>
              </el-icon>
              <template #title>聊天室</template>
            </el-menu-item>
            <el-menu-item index="/home/voice">
              <el-icon>
                <Mic/>
              </el-icon>
              <template #title>语音聊天</template>
            </el-menu-item>
            <el-menu-item index="/home/file">
              <el-icon>
                <Files/>
              </el-icon>
              <template #title>文件传输</template>
            </el-menu-item>
            <el-menu-item index="/home/setting">
              <el-icon>
                <Setting/>
              </el-icon>
              <template #title>个人设置</template>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main style="width: 100%; height: 80vh; overflow-x: hidden; overflow-y: hidden;">
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </template>
  
  <script lang="ts" setup>
  import {ArrowDown, ChatDotRound, Files, Mic, Setting} from '@element-plus/icons-vue'
  import {onMounted, ref} from 'vue'
  import {ElMessage} from "element-plus";
  
  const max_with = ref<number>(64);
  const active = ref<string>("1");
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
    console.log(command)
    switch (command) {
      case "settings":
        window.location.hash = "/home/setting"
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
  const onMouseEnter = function () {
    isCollapse.value = false
    max_with.value = 200;
  }
  const onMouseLeave = function () {
    isCollapse.value = true
    max_with.value = 64;
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
  
  .aside {
    transition: width 500ms;
    overflow-x: hidden;
  }
  </style>