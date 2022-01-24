<template>
  <el-row :gutter="10" style="height: 80%">
    <el-col :span="20" style="height: 100%;">
      <div id="message">
        {{init}}
      </div>
      <t-textarea
        v-model="sender"
        placeholder="请输入要发送的内容"
        :maxcharacter="200"
      ></t-textarea>
      <el-button type="primary" style="float:right;" plain @click="submitMessage">发送消息</el-button>
    </el-col>
    <el-col :span="4" id="online-user">
      <el-table
        :data="administrator">
        <el-table-column
          prop="username"
          label="管理员">
        </el-table-column>
      </el-table>
      <el-table
        :data="user">
        <el-table-column
          prop="username"
          label="用户">
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>
</template>

<script>
import {getAllUserOnline, getMessageCount, getSomeMessage, getUserHeading} from '@/components/axios/request';
import {sendMessage} from "../../components/axios/request";

export default {
  name: "chat",
  data(){
    return{
      administrator: [],
      user: [],
      sender:"",
      headingimg:{},
      init:"",
      username:window.localStorage.getItem("username"),
    }
  },
  methods: {
    showError(err) {
      this.$message.error(err);
    },
    async getSomeMessageCallback(res) {
      let d = res.data.data;
      console.log(d);
      let jpgBase64 = "data:image/jpg;base64,";
      const data = {};
      const times = [];
      for (let item of d) {
        await getUserHeading(item.sender).then(res => {data[item.sendtime] = [res.data.data,item];times.push(item.sendtime)});
      }
      let html = ''
      for (let time of times) {
        let item = data[time][1];
        let headBase64 = data[time][0];
        let head = jpgBase64 + headBase64;
        let add = ''
        if(item.type === "text"){
          add = '<div class="t-comment__inner"><div class="t-comment__avatar"><img src="headimg" alt="" class="t-comment__avatar-image"></div><div class="t-comment__content"><div class="t-comment__author"><span class="t-comment__name">author1</span><span class="t-comment__time">TIME1</span></div><div class="t-comment__detail">content1</div></div></div>';
          add = add.replaceAll("author1", item.sender).replaceAll("content1", item.content).replaceAll("headimg",head).replaceAll("TIME1",item.sendtime);
        }else{
          let img = jpgBase64 + item.content
          add = '<div class="t-comment__inner"><div class="t-comment__avatar"><img src="headimg" alt="" class="t-comment__avatar-image"></div><div class="t-comment__content"><div class="t-comment__author"><span class="t-comment__name">author1</span><span class="t-comment__time">TIME1</span></div><div class="t-comment__detail"><img src="content1"></div></div></div>';
          add = add.replaceAll("author1", item.sender).replaceAll("content1", img).replaceAll("headimg",head).replaceAll("TIME1",item.sendtime);;
        }
        html += add;
      }
      this.init = html;
    },
    getMessageCountCallBack(res) {
      let num = res.data.data;
      let min = 0;
      console.log(num)
      if (num >= 10) {
        min = num - 10;
      }
      console.log(min);
      getSomeMessage(min, num).then(this.getSomeMessageCallback)
    },
    getMessages() {
      getMessageCount().then(res => {
        this.getMessageCountCallBack(res);
      });
    },
    getOnlineUser() {
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
    submitMessage(){
      console.log(this.username);
      sendMessage(this.sender,this.username).then(res => {
        console.log(res); this.sender = '';this.getMessages()}).catch(err => {this.showError(err)});
    }
  },
  created()
  {
    this.getOnlineUser();
    this.getMessages();
    setInterval(this.getMessages,5000);
  },
}
</script>

<style scoped>
#message{
  background: #C4C4C4;
  width: 100%;
  height: 100%;
  border-radius: 30px;
  margin-bottom: 20px;
}
</style>
