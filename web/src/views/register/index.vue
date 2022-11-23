<template>
  <el-input v-model="username" placeholder="用户名">
    <template #prepend>用户名</template>
  </el-input>
  <el-input v-model="password" placeholder="密码" show-password>
    <template #prepend>密码</template>
  </el-input>
  <el-button type="success" @click="register">注册</el-button>
</template>

<script lang="ts" setup>
import {ref} from "vue";
import {registerUser} from "../../api/user";
import {ElMessage} from "element-plus";

const username = ref<string>();
const password = ref<string>();
const register = function () {
  registerUser(username.value as string, password.value as string).then(_ => ElMessage.success("成功")).catch(_ => ElMessage.error("失败"))
}
</script>

<style scoped>

</style>