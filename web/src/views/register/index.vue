<template>
  <div class="login clearfix">
    <div class="login-wrap">
      <el-row type="flex" justify="center">
        <el-form ref="loginForm" status-icon label-width="80px">
          <h3>注册</h3>
          <hr>
          <el-form-item prop="username" label="用户名">
            <el-input v-model="username" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item prop="password" label="设置密码">
            <el-input v-model="password" show-password placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="register()">注册账号</el-button>
          </el-form-item>
        </el-form>
      </el-row>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {ref} from "vue";
import {ElMessage} from "element-plus";
import {registerUser} from "../../api/user";
const username = ref<string>("");
const password = ref<string>("");
const register = () => {
  if (username.value === "" || password.value === ""){
    ElMessage.error("用户名密码不能为空");
    return;
  }
  registerUser(username.value, password.value).then((_) => {
    ElMessage.success("注册成功");
    username.value = "";
    password.value = "";
  }).catch((err) => {
    ElMessage.error(err.response.data.msg);
  })
};

</script>

<style scoped>
.login {
  width: 100%;
  height: 100vh;
  background-image: linear-gradient(to right top, #d16ba5, #c777b9, #ba83ca, #aa8fd8, #9a9ae1, #8aa7ec, #79b3f4, #69bff8, #52cffe, #41dfff, #46eefa, #5ffbf1);
  background-size: cover;
  overflow: hidden;
}
.login-wrap {
  width: 500px;
  height: 400px;
  margin: 215px auto;
  overflow: hidden;
  padding-top: 100px;
  line-height: 20px;
}
h3 {
  color: #e8f0fe;
  font-size: 24px;
}
hr {
  background-color: #444;
  margin: 20px auto;
}

</style>
