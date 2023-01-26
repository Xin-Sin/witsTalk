<template>
    <el-container>
      <el-header class="header">
        <div class="logo">
          <el-icon><img alt="" class="logo" src="/logo.svg"/></el-icon>
        </div>
        <div>
          <span v-if="weather.city === undefined" style="color: #f56c6c">未查询到您当地的天气信息</span>
          <span v-if="weather.city !== undefined">
            <span style="color: #FFCC33">
              {{weather.city}}
            </span>
            今日天气:
            <span style="color: #FFFFCC">
              {{weather.weather}}
            </span>
            温度:
            <span style="color: #FFFF66">
              {{weather.temperature}}度
            </span>
            风向:
            <span style="color: #9933CC">
              {{weather.winddirection}}风
              {{weather.windpower}}级
            </span>
          </span>
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
            <el-menu-item index="/home/gameWitsMusic">
              <el-icon>
                <Grid/>
              </el-icon>
              <template #title>音游</template>
            </el-menu-item>
            <el-menu-item index="/home/setting">
              <el-icon>
                <Setting/>
              </el-icon>
              <template #title>个人设置</template>
            </el-menu-item>
            <el-menu-item index="/home/adminRouter">
              <el-icon>
                <Setting/>
              </el-icon>
              <template #title>管理</template>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main style="width: 100%; height: 90vh; overflow-x: hidden; overflow-y: hidden;">
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </template>
  
  <script lang="ts" setup>
  import {ArrowDown, ChatDotRound, Files, Mic, Setting, Grid} from '@element-plus/icons-vue'
  import { onMounted, ref} from 'vue'
  import {ElMessage} from "element-plus";
  import {getWeather} from "../../api/user";
  import {useStore} from "../../store";
  import {useRouter} from "vue-router";
  import {Weather} from "../../entities/Weather";
  
  const max_with = ref<number>(64);
  const active = ref<string>("/home/chat");
  //主逻辑
  const isCollapse = ref<boolean>(true);
  const headimgBase64 = ref<string>();
  const userToken = ref<string>();
  const username = ref<string>();
  const weather = ref<Weather>({adcode: undefined, city: undefined, humidity: undefined, province: undefined, reporttime: undefined, temperature: undefined, weather: undefined, winddirection: undefined, windpower: undefined});
  const store = useStore();
  const router = useRouter();

  onMounted(() => {
    // 获取天气
    getWeather().then(res =>{
      weather.value = res.data.data.lives[0] as Weather
    }).catch(_ =>{
      ElMessage.error("获取天气时出现了错误")
    })
    // 将store中数据存入当前页面
    if (store.userinfo !== null){
      username.value = store.userinfo.username;
      headimgBase64.value = "data:image/png;base64," + store.userinfo.headimg;
      userToken.value = window.localStorage.getItem("token") as string;
    }
    active.value = window.location.hash.replace("#","")
  })
  const handlerUserSettingsCommand = (command: string) =>{
    // 处理下拉菜单的指令
    console.log(command)
    switch (command) {
      case "settings":
        // 跳转至个人设置页面
        router.push("/home/setting");
        break;
      case "exit":
        // 清空pinia中userinfo数据
        store.$reset();
        //   清空token
        window.localStorage.clear()
        ElMessage.success("成功退出登录，返回登陆页面");
        router.push('/')
        break;
      //   出现未知指令进行处理
      default:
        ElMessage.error("CNM出问题了，请将此消息内容复制并发送给网站管理员   command=" + command)
        break;
    }
  }
  // 菜单栏获取鼠标焦点进行动画展示
  const onMouseEnter = () =>{
    isCollapse.value = false
    max_with.value = 200;
  }
  const onMouseLeave = () =>{
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