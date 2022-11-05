<template>
  <el-dialog v-model="showSlideWindow" :width="440" style="background-color: rgba(255,255,255,75%);" title="滑动验证">
    <slide-verify ref="slider" :accuracy="5" :h="150" :w="400" slider-text="向右滑动验证" @success="onSlideSuccess"/>
  </el-dialog>
  <div class="background">
    <div id="window" class="login-window">
      <div class="inline-div right">
        <div class="login-window-child title" style="margin-top: 5vh">登录</div>
        <div class="login-window-child input" style="margin-top: 25px">
          <el-input v-model="username" placeholder="请在此输入昵称">
            <template #prepend>昵称</template>
          </el-input>
        </div>
        <div class="login-window-child input">
          <el-input v-model="password" placeholder="请在此输入密码" show-password type="password">
            <template #prepend>密码</template>
          </el-input>
        </div>
        <div class="login-window-child login-button">
          <el-button plain type="primary" @click="Login">登录</el-button>
        </div>
      </div>
      <div class="inline-div left img"/>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  name: "Login",
}
</script>
<script lang="ts" setup>
import {ref} from "vue";
import SlideVerify, {SlideVerifyInstance} from "vue3-slide-verify";
import "vue3-slide-verify/dist/style.css";
import {login} from './Requester'
import {ElMessage} from "element-plus";

const username = ref<string>();
const password = ref<string>();
const showSlideWindow = ref<boolean>();
const slider = ref<SlideVerifyInstance>();
const Login = function () {
  showSlideWindow.value = true;
  slider.value?.refresh();
}
const onSlideSuccess = function () {
  login(username.value as string, password.value as string).then((data) => {
    showSlideWindow.value = false;
    let canLogin: boolean = data.data.data.canLogin;
    if (canLogin) {
      ElMessage.success("登录成功！");
      window.sessionStorage.setItem("username", username.value as string);
      window.sessionStorage.setItem("headimg", data.data.data.base64);
      window.location.hash = "/main";
    } else {
      ElMessage.error("用户名或密码错误！");
    }
  })
}
</script>

<style scoped>
.login-window {
  border: 2px var(--el-border-color);
  border-radius: var(--el-border-radius-round);
  box-shadow: var(--el-box-shadow-dark);
  width: 60%;
  height: 40%;
  min-height: 300px;
  min-width: 270px;
  left: 50%;
  top: 50%;
  margin-left: -30%;
  margin-top: -15%;
  position: absolute;
}

.login-window-child {
  position: relative;
  margin-top: 10px;
}

.input {
  margin-left: 10%;
  width: 70%;
}

.login-button {
  margin-left: 45%;
}

.inline-div {
  display: inline;
}

.left {
  position: absolute;
  left: 0;
  width: 50%;
  height: 100%;
}

.right {
  position: absolute;
  right: 0;
  width: 50%;
  height: 100%;
  background-color: rgba(255, 255, 255, 30%);
  backdrop-filter: blur(6px);
  border-right: 2px var(--el-border-color);
  border-top-right-radius: var(--el-border-radius-round);
  border-bottom-right-radius: var(--el-border-radius-round);
}

.img {
  background-repeat: no-repeat;
  background-image: url("./src/assets/img.png");
  background-size: 100% 100%;
  border-left: 2px var(--el-border-color);
  border-top-left-radius: var(--el-border-radius-round);
  border-bottom-left-radius: var(--el-border-radius-round);
}

.title {
  font-size: 2vw;
  font-family: "Helvetica Neue", sans-serif;
  text-align: center;
}

@media screen and (max-width: 900px) {
  .img {
    display: none;
  }

  .right {
    width: 100%;
    border-left: 2px var(--el-border-color);
    border-top-left-radius: var(--el-border-radius-round);
    border-bottom-left-radius: var(--el-border-radius-round);
  }

  .login-window {

  }

  .title {
    font-size: 4vw;
  }

  .login-button {
    margin-left: 35%;
  }
}

/*背景图层*/
div.background {
  width: 100%;
  height: 100vh;
  background-repeat: no-repeat;
  background-image: url("./src/assets/background2.jpeg");
  background-size: 100% 100%;
}
</style>