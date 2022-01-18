<template>
  <div style="width: 100%;height: 100%">
    <el-container id="container">
      <el-aside id="side" width="10%">
        <h5 style="color:#b9b9b9; text-align:center;">频道选择</h5>
        <el-menu
          default-active="1"
          style="width: 100%; border-right: 0;">
          <el-menu-item-group style="background-color:#2a3042;width: 100%;">
            <el-menu-item index="1" style="width: 100%;">文字频道</el-menu-item>
            <el-menu-item index="2" style="width: 100%;">语音频道</el-menu-item>
            <el-menu-item index="3" style="width: 100%;">文件频道</el-menu-item>
            <el-menu-item index="4" style="width: 100%;">小♂游戏</el-menu-item>
          </el-menu-item-group>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header id="header">
          <el-row>
            <el-col :span="4"><h3 id="username">User Name</h3></el-col>
            <el-col :span="4" style="float:right;"><el-avatar style="float:right; margin-top: 10px;" id="headPortrait" src="#"/></el-col>
          </el-row>
        </el-header>
        <el-main id="main">
          <el-row :gutter="10" style="height: 80%">
            <el-col :span="20" style="height: 100%;">
              <div id="message"></div>
              <el-input
                type="textarea"
                :rows="4"
                placeholder="请输入要发送的内容"
                style="margin-bottom: 5px"
                v-model = "sender">
              </el-input>
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
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-menu-item.is-active{
  color:#919191;
}
.el-menu-item{
  color:#5e5e5e;
}
#online-user{

}
#container{
  height: 100%;
  width: 100%;
  padding: 0;
  margin: 0;
  position: absolute;
  left: 0;
  top: 0;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
}
#header{
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  line-height: 70px;
  background: #2C2C2C;
}
#main{
  height: 100%;
  width: 100%;
}
#side{
  background: #383F51;
}
#username{
  font-family: "Helvetica Neue",Arial,sans-serif;
  color:#c1c7d0;
}
#message{
  background: #C4C4C4;
  width: 100%;
  height: 100%;
  border-radius: 30px;
  margin-bottom: 20px;
}
</style>
<script>
import {getAllMessage,sendMessage,getAllUserOnline} from "@/components/axios/request"

export default {
  name:"Chat",
  data() {
    return {
      administrator: [],
      user: [],
      sender:"",
      token:''
    }
  },
  created() {
    this.token = window.localStorage.getItem("token");
    console.log(this.token);
    let administrator = [];
    let user = []
    getAllUserOnline().then(res=>{
      res.data.data.forEach(function (item){
        let auth = item.auth;
        let username = item.username;
        if (auth === "admin"){
          administrator.push({"username":username})
        }else{
          user.push({"username":username})
        }
      });
      this.administrator = administrator;
      this.user = user;
    }).catch(err=>{
      this.$message.error(err)
    });
    //let message = getAllMessage(this.token)
    //{"msg":"OK","data":[{"auth":"admin","username":"wzpMC"}],"status":200}

  }
}
</script>

