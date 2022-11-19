<template>
  <div class="item">
    <div class="headimg" @click="changeHeadimg">
      <input ref="fileUploader" accept="image/*" style="display: none" type="file" @change="selectFile"/>
      <div class="headimg-text"><span style="position:absolute;color: #FFFFFF;word-wrap: break-word;left: 9px;top: 4px">更换 头像</span>
      </div>
      <div style="width: 50px;height: 50px;">
        <el-avatar :size="50" :src="headimgBase64" style="margin-right: 10px"/>
      </div>
    </div>
    <el-input v-model="username" class="username-input" placeholder="用户名"/>
  </div>
  <el-button class="item" plain type="warning" @click="changePassword">修改密码</el-button>
  <div class="end-page">
    <el-button plain type="success" @click="submit">提交</el-button>
    <el-button plain type="danger" @click="giveUp">放弃</el-button>
  </div>
  <el-upload>

  </el-upload>
</template>

<script lang="ts" setup>
import {onMounted, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {changeUserHeadImg, changeUsername, changeUserPassword} from "../../components/api/requester";

const headimgBase64 = ref<string>();
const userToken = ref<string>();
const username = ref<string>();
const fileUploader = ref<HTMLInputElement>();
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
const submit = function () {
  changeUsername(username.value as string).then((response) => {
    if (response.data) {
      ElMessage.success("成功修改！");
      window.sessionStorage.setItem("username", username.value as string);
      location.reload();
      return;
    }
    ElMessage.error("修改失败，请重试！");
  }).catch((err) => {
    ElMessage.error("更改失败，请将此报错内容发送至网站管理员！" + err);
  });
}
const giveUp = function () {
  let name = window.sessionStorage.getItem("username");
  if (name) {
    username.value = name;
  }
}
const changeHeadimg = function () {
  fileUploader.value?.click();
}
const selectFile = function (_: Event) {
  if (!fileUploader.value) {
    return;
  }
  if (!fileUploader.value.files) {
    return;
  }
  let file = fileUploader.value.files[0];
  if (file.size >= 100 * 1024) {
    ElMessage.error("请上传小于100KB的头像！")
    return;
  }
  let fileReader = new FileReader();
  fileReader.onload = function (ev: ProgressEvent<FileReader>) {
    let data = ev.target?.result;
    if (!data) {
      return;
    }
    let img = new Image()
    img.onload = function () {
      if (img.width !== img.height) {
        ElMessage.error("请上传正方形头像！")
        return;
      }
      let base64 = img.src.replace("data:" + file.type + ";base64,", "")
      changeUserHeadImg(username.value as string, base64).then((response) => {
        if (!response.data.data.is_success) {
          ElMessage.error("更改失败，请重试！");
          return;
        }
        ElMessage.info("更改成功！");
        window.sessionStorage.setItem("headimg", base64);
        base64 = "data:" + file.type + ";base64," + base64
        headimgBase64.value = base64;
      }).catch((err) => {
        ElMessage.error("更改失败，请将此报错内容发送至网站管理员！" + err);
      })
    }
    img.src = data as string;
  }
  fileReader.readAsDataURL(file);
}
const changePassword = function () {
  ElMessageBox.prompt('请输入你要修改的密码', '修改密码', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
  }).then(value => {
    if (!value.value) {
      ElMessage.error("请输入要修改的密码！");
      return;
    }
    if (value.value.length < 7) {
      ElMessage.error("请输入长度大于等于7位的密码！");
      return;
    }
    if (username.value) {
      changeUserPassword(username.value, value.value).then((response) => {
        if (response.data.data.is_success) {
          ElMessage.success("修改成功！")
          return;
        }
        ElMessage.error("修改失败，请尝试重新登录！")
      }).catch((response) => {
        ElMessage.error("错误，请将此报错发送给网站管理员！" + response)
      });
      return;
    }
    ElMessage.error("出现错误，会话存储username为空，请将此报错报告给网站管理员！")
  }).catch(reason => {
    if (reason === "cancel") {
      return;
    } else {
      console.log(reason);
    }
  })
}
</script>

<style scoped>
.item {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-top: 20px;
}

div.headimg {
  width: 50px;
  height: 50px;
  margin-right: 10px;
  position: relative;
}

div.headimg-text {
  display: none;
}

div:hover.headimg div.headimg-text {
  display: block;
  width: 50px;
  height: 50px;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 50%);
  border-radius: 30px;
  cursor: pointer;
}

.username-input {
  max-width: 100px;
}

div.end-page {
  display: flex;
  margin-top: 20px;
}
</style>