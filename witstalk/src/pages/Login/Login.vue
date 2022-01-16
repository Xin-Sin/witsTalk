<template>
  <el-container>
    <el-main>
      <el-row :gutter="10" type="flex">
          <el-col :xs="16" :sm="16" :md="16" :lg="16" :xl="16" class="hidden-lg-and-down" >
            <div class="grid-content bg-purple-light">
              <img src="./assets/img.png" alt="这是一张图片" width="984" height="901" style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); border-radius: 30px">
            </div>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="8" :xl="8">
            <div class="grid-content bg-purple-light">
              <label id="texttitle">欢 迎 来 到 登 录 页 面</label> <br>
              <label class="input">账号</label> <br>
              <el-input v-model="username" placeholder="请输入账号" ref="username"></el-input> <br>
              <label class="input">密码</label> <br>
              <el-input placeholder="请输入密码" v-model="password" show-password ref="password"></el-input> <br>
              <label class="input">验证码</label> <br>
              <div>
                <el-input placeholder="请输入验证码" v-model="captchaa" ref="code">
<!--                <el-input placeholder="请输入验证码" v-model="captcha" ref="code">-->
                  <template slot="append"><img ref="codeimg" @click="gettingCaptcha" width="100px" height="36px"></template>
                </el-input>
              </div>
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
  </el-container>
</template>

<script>
import { getverificationcode} from './static/login'  // 导入 封装的请求函数
import {Login} from './static/login'
import {getDeviceType} from './static/device'
import hex_md5 from 'js-md5'
export default {
    name:'Login',
      data(){
        return{
          getverificationcode:{},
          captchaa:'',
          // captcha:'',
          username:'',
          password:''
        }
      },
      methods:{
        login(){
          if(this.captchaa.toUpperCase() != this.captchaa.toUpperCase()){
          // if(this.captcha.toUpperCase() != this.captchaa.toUpperCase()){
            this.$message.error("验证码错误");
            this.gettingCaptcha();
          }else{
            this.$refs.info.innerHTML = "";
            Login({"username":this.username,"password":hex_md5(this.password)}).then(res=>{
              window.localStorage.setItem("token",res.headers.token)
              console.log("login");
              if(res.data.data.canLogin){
                this.$message({"message":"登录成功，正在跳转",type:"success"})
              }else{
                this.$message.error("用户名或密码错误");
                this.gettingCaptcha();
              }
            }).catch(err=>{
              console.log(err);
            });
          }
        },
        gettingCaptcha(){
          getverificationcode().then(res=>{
            this.getCaptcha(res.data);
          }).catch(err=>{

          });
        },
        getCaptcha(data){
          let a = "data:image/ico;base64,";
          const b64data = data.split(",")[0];
          //分离取出
          let capt = data.split(",")[1];
          this.captchaa = capt;
          a += b64data;
          this.$refs.codeimg.src = a;
        }
      },
      created() {
        getverificationcode().then(res=>{
          this.getCaptcha(res.data);
        }).catch(err=>{

        });

      }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
#loginbutton{
  margin-left: -7%;
  width: 80%;
}
.login{
  text-align: center;
  align-content: center;
  align-items: center;
  align-self: center;
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
