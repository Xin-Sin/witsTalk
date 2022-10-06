<template>
  <el-row :gutter="10" style="height: 80%">
    <el-col :span="20" style="height: 100%;">
<!--      //展示消息数据-->
      <div id="message1">
        <message v-for="(item) in someMessage" :key="someMessage.id" ref="messages" v-bind:Mdata="item" v-bind:ws="websock" v-bind:allHead="headingTable"></message>
      </div>
      <t-textarea v-model="sender" placeholder="请输入要发送的内容" :maxcharacter="200"></t-textarea>
      <el-button type="primary" style="float:right;" plain @click="submitMessage()" >发送消息</el-button>
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
//使用组件式重构
import message from "./message";
import {SEARCH} from "@/mixin/mixin";

export default {
  name: "chat",
  mixins:[SEARCH],
  components:{
    message
  },
  data() {
    return {
      administrator: [],
      user: [],
      sender: "",
      headingTable: {},
      username: sessionStorage.getItem("username"),
      wsConnect: null,
      messageCount: null,
      token: window.localStorage.getItem("token"),
      someMessage:[],
      websock: null,
      heartCheck: null
    }
  },
  methods: {
    heartCheckPing(){
      let ping = {"op": "heartCheck", "args": {"content": "ping"}}
      this.webSocketSendData(JSON.stringify(ping));
      console.log("ping")
    },
    heartCheckStart(){
    },
    search(){
      console.log("chat");
    },
    showError(err) {
      this.$message.error(err);
    },
    webSocketSendData(Data) {//数据发送
      this.websock.send(Data);
    },
    webSocketClose(e) {  //关闭
      console.log('websocket connect close', e);
      clearInterval(this.heartCheck);
    },
    initWebSocket() { //初始化websocket
      this.websock = new WebSocket("ws://127.0.0.1:8080/chat");
      this.websock.onmessage = this.webSocketGetData;
      this.websock.onopen = this.webSocketConnection;
      this.websock.onerror = this.webSocketError;
      this.websock.onclose = this.webSocketClose;
    },
    webSocketConnection() {
      let loginData = {"op": "login", "args": {"token": this.token}};
      this.webSocketSendData(JSON.stringify(loginData))
      this.heartCheck = setInterval(this.heartCheckPing,1000 * 59)
    },
    submitMessage() {//发送消息
      console.log(this.username);
      if(this.sender !== "" && this.sender !== "\n"){
        let message = {"op": "send", "args": {"content": this.sender, "sender": this.username, "type": "text"}}
        this.webSocketSendData(JSON.stringify(message));
        this.sender = "";
      }else{
        this.showError("你不能发送空消息！");
      }
    },
    webSocketGetData(e) { //数据接收
      const data = e.data;
      if (this.in_(data, ".") && this.in_(data, "/") && this.in_(data, "connected!")) {
        /*已连接*/
      } else if (data === "true" || data === "false") {
        //登录消息返回
        console.log(data);
        if (data === "true") {
          console.log("wsLogin!");
          let sendData = {"op": "count"}
          this.webSocketSendData(JSON.stringify(sendData));
        } else {
          console.log("LoginError");
          this.showError("登录失败，请尝试重新登录");
        }
      } else if (!(isNaN(data))) {
        //count操作返回值
        let num = parseInt(data);
        let sendData = {"op": "get", "args": {"min": 0, "max": num}}
        this.webSocketSendData(JSON.stringify(sendData));
      }else if (data === undefined){
        //undefined pass
        this.showError("dataUndefined," + data)
      }else{
        try{
          let jsonData = JSON.parse(data);
          if (jsonData.heartCheck === "pong"){
            console.log("pong")
            return;
          }
          if (Array.isArray(jsonData)) {
            //全部消息
            jsonData.sort(function (a, b) {
              return a.id - b.id
            });
            this.someMessage = jsonData;
          }else{
            //单条消息
            if('op' in jsonData){
              let sm = [];
              if(jsonData.op === "recall"){
                let id = parseInt(jsonData.id);
                for (let m of this.someMessage){
                  if(m.id !== id){
                    sm.push(m);
                  }
                }
                this.someMessage = sm;
              }
            }else{
              this.someMessage.push(jsonData);
            }
          }
        } catch (SyntaxError){
          this.showError("JSON data has SyntaxError,data=" + data)
        }
      }
    },
    in_(str1, str2) {
      /**
       * 判断str1里是否含有str2
       */
      return str1.indexOf(str2) !== -1;
    },
    webSocketError() {//连接建立失败重连
      this.initWebSocket();
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
  created: function () {
    this.initWebSocket();//初始化websocket
    this.getOnlineUser();
    document.onkeydown = this.keyDown;
  },
}
</script>

<style scoped>
#message1{
  /*background: rgba(196,196,196,0.1);*/
  background-color: #f3f3f3;
  width: 100%;
  height: 95%;
  margin-bottom: 20px;
  overflow-y:scroll;
  overflow-x:scroll;
}
</style>
