<template>
  <el-row :gutter="10" style="height: 80%">
    <el-col :span="20" style="height: 100%;">
      <div id="chat">
      </div>
      <el-button :icon="button_icon" :type="button_type" @click="button_click" class="join_button">{{button_text}}</el-button>
    </el-col>
    <!--    //展示在线人员-->
    <el-col :span="4" id="online-user">
      <el-table :data="administrator">
        <el-table-column prop="username" label="管理员"></el-table-column>
      </el-table>
      <el-table :data="user">
        <el-table-column prop="username" label="用户"></el-table-column>
      </el-table>
    </el-col>
  </el-row>
</template>

<script>
import {getAllUserOnline} from '@/components/axios/request';
import Recorder from 'recorder-js';
export default {
  name: "voicechat",
  data() {
    return {
      administrator: [],
      user: [],
      isJoin: true,
      button_text: "加入语音频道",
      button_icon: "el-icon-phone",
      button_type: "primary",
      recorder:null,
      isRecording:false,
      stream:null,
      Started:false,
    }
  },
  methods:{
    showError(err) {
      this.$message.error(err);
    },
    button_click(){
      if(!this.isJoin){
        console.log("exit")
        this.exit();
        this.button_text = "加入语音频道";
        this.button_icon = "el-icon-phone";
        this.button_type = "primary";
        this.isJoin = true;
      }else{
        console.log("join")
        this.join();
        this.button_text = "退出语音频道";
        this.button_icon = "el-icon-phone-outline";
        this.button_type = "danger";
        this.isJoin = false;
      }
    },
    getVoiceData(data){
      for (let d of data.data) {
        console.log(d);
      }
    },
    join(){
      if(!this.Started) {
        const audioContext = new (window.AudioContext || window.webkitAudioContext)();

        this.recorder = new Recorder(audioContext, {
          // An array of 255 Numbers
          // You can use this to visualize the audio stream
          // If you use react, check out react-wave-stream
          onAnalysed: data => {
            if (this.isRecording) {
              this.getVoiceData(data);
            }
          },
        });
        this.Started = true;
        navigator.mediaDevices.getUserMedia({audio: true})
          .then((stream) => {
            this.stream = stream;
            this.isRecording = true;
            this.recorder.init(this.stream)
          })
          .catch(err => {
            this.showError("在尝试启动语音时失败，原因：" + err);this.Started = false
          });
      }
    },
    exit(){
      this.isRecording = false;
    },
    getOnlineUser() {//获取所有在线用户并将其加入表中
      let administrator = [];
      let user = []
      getAllUserOnline().then(res => {
        res.data.data.forEach(function (item) {
          let auth = item.auth;
          let username = item.username;
          if (auth === "admin") {
            administrator.push({"username": username})
          } else {
            user.push({"username": username})
          }
        });
        this.administrator = administrator;
        this.user = user;
      }).catch(err => this.showError);
    },
  },
  created:function (){
    setInterval(this.getOnlineUser,5000);
  },
}
</script>

<style scoped>
#chat{
  background: #C4C4C4;
  width: 100%;
  height: 100%;
  border-radius: 30px;
  margin-bottom: 20px;
}
.join_button{
  display: block;
  margin: 0 auto;
}
</style>
