<template>
  <el-container>
    <el-main>
      <el-row :gutter="10" type="flex">
        <el-col :xs="16" :sm="16" :md="16" :lg="16" :xl="16" class="hidden-lg-and-down" >
          <div class="grid-content bg-purple-light">
            <img src="@/assets/img.png" alt="这是一张图片" width="984" height="901" style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); border-radius: 30px">
          </div>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="8" :xl="8">
          <div class="grid-content bg-purple-light">
            <label ref="texttitle" id="texttitle"> <!---AutoWrite---> </label> <br>
            <label class="input">账号</label> <br>
            <el-input v-model="username" placeholder="请输入账号" ref="username"></el-input> <br>
            <label class="input">密码</label> <br>
            <el-input placeholder="请输入密码" v-model="password" show-password ref="password"></el-input> <br>
            <label class="input">验证</label> <br>
            <el-button style="margin-left: 7%;width: 73%" type="primary" @click="dialogFormVisible = true">{{message}}</el-button>
            <div class="register">
              <el-button type="text">注册</el-button>
              <el-button class="forgotPassword" type="text">忘记密码</el-button>
            </div>
            <div class="login">
              <el-button type="primary" id="loginbutton" @click="login">登录</el-button>
            </div>
            <span ref="info"></span>
          </div>
        </el-col>
      </el-row>
    </el-main>
    <el-dialog :visible.sync="dialogFormVisible" width="18%">
      <slide-verify  :l="42" :r="10" :w="310" :h="155" slider-text="向右滑动" @success="onSuccess" @fail="onFail" @refresh="onRefresh"></slide-verify>
    </el-dialog>
  </el-container>
</template>

<script>
import {getHitokoto} from '@/components/axios/request'
import {Login} from '@/components/axios/request'
import hex_md5 from 'js-md5'
export default {
  name:'Login',
  data(){
    return{
      username:'',
      password:'',
      msg: false,
      dialogFormVisible: false,
      message:"点击进行验证",
    }
  },
  methods:{
    onSuccess(){
      this.dialogFormVisible = false;
      this.message = "验证成功";
      this.msg=true;
      if (!(this.username === "" & this.password === "")){
        this.$message.success("验证成功,正在进行登录");
        setTimeout(this.login,500);
      }
    },
    onFail(){
      this.$message.error("验证失败")
    },
    onRefresh(){
      this.$message.info("刷新")
    },
    login(){
      if (this.msg == true){
        this.$message.info("正在登录,请稍后");
        Login({"username" : this.username, "password" : hex_md5(this.password)}).then(result => {
          console.log("login");
          if(result.data.data.canLogin){
            window.localStorage.setItem("username",this.username);
            this.$message.success("登录成功，正在跳转");
            this.$router.push("main");
          }else{
            this.$message.error("用户名或密码错误");
          }
        });
      }else{
        this.$message.warning("请先进行人机验证");
        this.dialogFormVisible = true;
      }
    },
  },
  mounted() {
    getHitokoto().then(res=>{
      this.$refs.texttitle.innerText = res.data["hitokoto"];
    })
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.tags{
  margin-right: 10px;
}
#loginbutton{
  margin-left: -7%;
  width: 80%;
}
.login{
  text-align: center;
  align-content: center;
  align-items: center;
  align-self: center;
  width: 93%;
}
.register{
  margin-left: 30px;
}

.forgotPassword{
  margin-left: 60% !important;
}

.el-input-group__append, .el-input-group__prepend{
  padding: 0;
}
.el-input{
  padding-left: 6.5%;
  width: 80%;
}
.input {
  padding-left: 6%;
}
#texttitle{
  padding-left: 2%;
  font-family: Helvetica;
  font-weight: bolder;
  font-size: 20px;
}
.el-main {
  background-color: #DCDFE6;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  color: #333;
  /*text-align: center;*/
  line-height: 40px;
  margin-left: 10%;
  margin-right: 10%;
  border-radius: 20px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
.el-col {
  border-radius: 4px;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

</style>
