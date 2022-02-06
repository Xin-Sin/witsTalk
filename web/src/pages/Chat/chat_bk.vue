<template>
  <el-row :gutter="10" style="height: 80%">
    <el-col :span="20" style="height: 100%;">
<!--      //展示消息数据-->
      <message v-for></message>
      <t-textarea v-model="sender" placeholder="请输入要发送的内容" :maxcharacter="200"></t-textarea>
      <el-button type="primary" style="float:right;" plain @click="submitMessage">发送消息</el-button>
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
    }
  },
  methods: {
    showError(err) {
      this.$message.error(err);
    },

    async addingMessage(item) {
      /*
      *添加消息
      */
      if (this.messageCount < 11) {//消息总数小于11时
        let jpgBase64 = "data:image/jpg;base64,";//jpg的base64头
        let add = '';//添加的html
        let head = jpgBase64 + this.headingTable[item.sender];//获取头像
        // console.log(item);
        /*if (item.type === "text") {//文本消息
          add = '<div class="t-comment__inner"><div class="t-comment__avatar"><img src="headimg" alt="" class="t-comment__avatar-image"></div><div class="t-comment__content"><div class="t-comment__author"><span class="t-comment__name">author1</span><span class="t-comment__time">TIME1</span></div><div class="t-comment__detail">content1</div></div></div>';//添加的html（把腾讯的评论解析之后的html）
          add = add.replaceAll("author1", item.sender).replaceAll("content1", item.content).replaceAll("headimg", head).replaceAll("TIME1", item.sendtime);//替换数据
        } else {//图片消息
          let img = jpgBase64 + item.content//图片
          add = '<div class="t-comment__inner"><div class="t-comment__avatar"><img src="headimg" alt="" class="t-comment__avatar-image"></div><div class="t-comment__content"><div class="t-comment__author"><span class="t-comment__name">author1</span><span class="t-comment__time">TIME1</span></div><div class="t-comment__detail"><img src="content1"></div></div></div>';
          add = add.replaceAll("author1", item.sender).replaceAll("content1", img).replaceAll("headimg", head).replaceAll("TIME1", item.sendtime);
          ;
        }*/
        document.getElementById("message").innerHTML += add;//将message div中加入add的html
        this.messageCount++;//messageCount自增
      } else {//消息总数大于等于11时
        /**
         * 这里我不会写
         */
        let messages = document.getElementById("message");
        messages.getElementsByClassName("t-comment__inner")[0].remove();
        //messages[0].remove();
        //this.messageCount--;
        //await this.addingMessage(item);
      }
    },
    async getMessageCallBack(data) {
      for (let item of data) {
        await this.addingMessage(item);
      }
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
    },
    webSocketError() {//连接建立失败重连
      this.initWebSocket();
    },
    in_(str1, str2) {
      /**
       * 判断str1里是否含有str2
       */
      return str1.indexOf(str2) !== -1;
    },
    webSocketGetData(e) { //数据接收
      const data = e.data;
      if (this.in_(data, ".") && this.in_(data, "/") && this.in_(data, "connected!")) {
        //客户端连接广播
        console.log(data);
      } else if (data === "true" || data === "false") {
        //登录消息返回
        if (data === "true") {
          console.log("wsLogin!");
          this.wsLogin = true;
          let sendData = {"op": "count"}
          this.webSocketSendData(JSON.stringify(sendData));
        } else {
          console.log("LoginError");
          this.showError("登录失败，请尝试重新登录");
        }
      } else if (!(isNaN(data))) {
        //count操作返回值
        let num = parseInt(data);
        let min = 0;
        if (num >= 10) {
          min = num - 10;
        }
        let sendData = {"op": "get", "args": {"min": min, "max": num}}
        this.webSocketSendData(JSON.stringify(sendData));
      } else {
        //json数据结果
        let parse = JSON.parse(data);
        if (Array.isArray(parse)) {
          //getMessage返回
          this.getMessageCallBack(parse);
        } else {
          //新消息
          this.addingMessage(parse);
        }
      }
    },
    webSocketSendData(Data) {//数据发送
      this.websock.send(Data);
    },
    webSocketClose(e) {  //关闭
      console.log('websocket connect close', e);
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
    submitMessage() {//发送消息
      console.log(this.username);
      let message = {"op": "send", "args": {"content": this.sender, "sender": this.username, "type": "text"}}
      this.webSocketSendData(JSON.stringify(message));
    },
  },
  async created() {
    this.getOnlineUser();
    await getUserHeading().then(res => {
      const data = res.data.data
      for (let item of data) {
        let b64 = item.base64;
        let username = item.username;
        this.headingTable[username] = b64;
      }
    }).catch(this.showError);//下载数据库中所有用户的头像并将其加入headingTable中
    this.initWebSocket();//初始化websocket
  },
}
</script>

<style scoped>

</style>
