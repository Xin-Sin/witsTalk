<template>
    <div class="item">
      <div class="headimg" @click="changeHeadimg">
        <input
            ref="fileUploader"
            accept="image/*"
            style="display: none"
            type="file"
            @change="selectFile"
        />
        <div
            class="headimg-text">
          <span style="position:absolute;
          color: #FFFFFF;
          word-wrap: break-word;
          left: 9px;
          top: 4px"
          >
            更换 头像
          </span>
        </div>
        <div style="width: 50px;height: 50px;">
          <el-avatar
              :size="50"
              :src="headimgBase64"
              style="margin-right: 10px"/>
        </div>
      </div>
      <label style="margin: 0 20px" ref="usernameColor">{{username}}</label>
      <el-color-picker v-model="textColor" @change="updateColor" />
    </div>
    <el-upload>

    </el-upload>
  </template>
  
  <script lang="ts" setup>
  import {onMounted, ref} from "vue";
  import {ElMessage} from "element-plus";
  import {changeUserHeadImg, setUserExclusiveColor} from "../../api/user";
  import {useStore} from "../../store";
  
  const headimgBase64 = ref<string>();
  const userToken = ref<string>();
  const username = ref<string>();
  const fileUploader = ref<HTMLInputElement>();
  const textColor = ref<string>();
  const usernameColor = ref<HTMLLabelElement>();
  const store = useStore();

  // 更新用户字体颜色
  const changeColor = (color:string) => {
    if (usernameColor.value){
      usernameColor.value.style.color = color;
    }
  };
  // 上传用户字体颜色修改
  const updateColor = (color:string) => {
    if (usernameColor.value){
      usernameColor.value.style.color = color;
      // 修改用户的专属颜色接口
      setUserExclusiveColor(color as string, username.value as string);
    }
  };
  onMounted(() => {
    // 取值赋页面
    if (store.userinfo !== null){
      username.value = store.userinfo.username;
      headimgBase64.value = "data:image/png;base64," + store.userinfo.headimg;
      textColor.value = store.userinfo.exclusiveColor;
      userToken.value = window.localStorage.getItem("token") as string;
    }
    changeColor(textColor.value as string);
  });
  // 修改头像方法前缀
  const changeHeadimg = () => {
    fileUploader.value?.click();
  };
  const selectFile = (_event: Event) => {
    // 进行图片处理
    if (!fileUploader.value || !fileUploader.value?.files) {
      return;
    }
    let file = fileUploader.value.files[0];
    if (file.size >= 100 * 1024) {
      ElMessage.error("请上传小于100KB的头像！");
      return;
    }
    let fileReader = new FileReader();
    fileReader.onload = function (ev: ProgressEvent<FileReader>) {
      let data = ev.target?.result;
      if (!data) {
        return;
      }
      let img = new Image();
      img.onload = function () {
        if (img.width !== img.height) {
          ElMessage.error("请上传正方形头像！");
          return;
        }
        let base64 = img.src.replace("data:" + file.type + ";base64,", "");
        // 计算出的base64上传至服务器
        changeUserHeadImg(username.value as string, base64).then((response) => {
          if (!response.data.data.is_success) {
            ElMessage.error("更改失败，请重试！");
            return;
          }
          ElMessage.info("更改成功！");
          // 将base64进行数据更新
          if (store.userinfo !== null){
            let data = store.userinfo;
            data.headimg = base64;
            store.$patch({
              userinfo:data
            });
          }
          base64 = "data:" + file.type + ";base64," + base64;
          headimgBase64.value = base64;
        }).catch((err) => {
          ElMessage.error("更改失败，请将此报错内容发送至网站管理员！" + err);
        });
      };
      img.src = data as string;
    };
    fileReader.readAsDataURL(file);
  };
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