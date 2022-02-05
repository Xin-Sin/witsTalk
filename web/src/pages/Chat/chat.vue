<template>
  <el-row :gutter="10" style="height: 80%">
    <el-col :span="20" style="height: 100%;">
<!--      //展示消息数据-->
      <div id="message1">
        <message v-for="(item) in someMessage" v-bind:data="item" :key="someMessage.id"></message>
      </div>
      <t-textarea v-model="sender" placeholder="请输入要发送的内容" :maxcharacter="200"></t-textarea>
      <el-button type="primary" style="float:right;" plain>发送消息</el-button>
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
import {getAllUserOnline, getMessageCount, getSomeMessage, getUserHeading} from '@/components/axios/request';
import {sendMessage} from "../../components/axios/request";
//使用组件式重构
import message from "./message";

export default {
  name: "chat",
  components:{
    message
  },
  data() {
    return {
      administrator: [],
      user: [],
      sender: "",
      headingTable: {},
      username: window.localStorage.getItem("username"),
      wsConnect: null,
      messageCount: null,
      token: window.localStorage.getItem("token"),
      someMessage:null,
    }
  },
  methods: {
    showError(err) {
      this.$message.error(err);
    },
    initWebSocket() { //初始化websocket
      this.websock = new WebSocket("ws://127.0.0.1:8080/chat");
      this.websock.onmessage = this.webSocketGetData;
      this.websock.onopen = this.webSocketConnection;
      this.websock.onerror = this.webSocketError;
      this.websock.onclose = this.webSocketClose;
    },

    webSocketError() {//连接建立失败重连
      this.initWebSocket();
    },

  },
  created: function () {
    getSomeMessage(0, 11).then(result => {
      let data = result.data.data;
      console.log(data);
      // for (let i = 0; i < data.length; i++) {
      //   this.someMessage = data[i].id;
      // }
      this.someMessage = result.data.data;
      console.log(this.someMessage);
    });
    this.initWebSocket();//初始化websocket
  },
}
</script>

<style scoped>
#message1{
  background: #C4C4C4;
  width: 100%;
  height: 100%;
  border-radius: 30px;
  margin-bottom: 20px;
}
</style>
