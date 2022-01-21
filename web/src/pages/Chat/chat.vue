<template>
  <el-row :gutter="10" style="height: 80%">
    <el-col :span="20" style="height: 100%;">
      <div id="message"></div>
      <t-textarea
        v-model="sender"
        placeholder="请输入要发送的内容"
        :maxcharacter="200"
      ></t-textarea>
      <el-button type="primary" style="float:right;" plain>发送消息</el-button>
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
import {getAllUserOnline,getMessageCount,getSomeMessage} from '@/components/axios/request';
export default {
  name: "chat",
  data(){
    return{
      administrator: [],
      user: [],
      sender:"",
    }
  },
  showError(err){
    this.$message.error(err);
  },
  created()
  {
    this.token = window.localStorage.getItem("token");
    let administrator = [];
    let user = []
    let num = 0;
    getAllUserOnline().then(res => {
      console.log(res);
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
    getMessageCount().then(res => {num = res.data.data}).catch(err => this.showError);
    let min = 0;
    if(num >= 10){
      min = num - 10;
    }
    getSomeMessage(min,num).then(res => {console.log(res.data.data)})
  }
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
