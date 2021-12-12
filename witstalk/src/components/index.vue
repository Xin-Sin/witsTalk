<template>
  <div class="container-fluid">
    <div class="logindiv">
      <div class="input-group input-group-sm">
        <span class="input-group-addon" id="usernamediv" >用户名:</span>
        <input type="text" class="form-control" placeholder="用户名" aria-describedby="usernamediv" v-model="username" ref="username" @blur="onUsernameVerify" @focus="onUsernameVerify1">
        <span class="input-group-addon error" ref="errorUsername" ></span>
      </div>
      <br>
      <div class="input-group input-group-sm">
        <span class="input-group-addon" id="passworddiv">密码:</span>
        <input type="password" class="form-control" placeholder="密码" aria-describedby="passworddiv" v-model="password" ref="password" @blur="onUsernameVerify" @focus="onUsernameVerify1">
        <span class="input-group-addon error" ref="errorPassword"></span>
      </div>
      <br>
      <div class="input-group input-group-sm">
        <span class="input-group-addon" id="captchadiv">验证码:</span>
        <input type="text" class="form-control" placeholder="验证码" aria-describedby="captchadiv" v-model="captchaa" >
        <span class="input-group-addon error" id="errorCaptcha"></span>
      </div>
      <br>
      <div class="col-xs-6 col-md-3">
        <a href="#" class="thumbnail">
          <img id="captcha_img" @click="gettingCaptcha">
        </a>
        <small>看不清？点图片换一张</small>
      </div>
      <button type="button" class="btn btn-default loginbutton" aria-label="Left Align" @click="login">
        <span class="glyphicon glyphicon glyphicon-ok" aria-hidden="true"></span>
      </button>
    </div>
  </div>
</template>

<script>
import qs from 'qs' //引入 node中自带的qs模块（对application/x-www-form-urlencoded数据格式转换）
import { getverificationcode} from '@/api'  // 导入 封装的请求函数
export default {
    name:'Login',
      data(){
        return{
          getverificationcode:{},
          username:'',
          password:'',
          captchaa:''
        }
      },
      methods:{
        login(){
          console.log(this.username)
          console.log(this.password)
          console.log(this.captchaa)
        },
        onUsernameVerify(){
          if (this.$refs.username.value === ""){
            this.$refs.errorUsername.innerText = "账号不能为空";
          }
        },
        onUsernameVerify1(){
          this.$refs.errorUsername.innerText = "";
        },
        onPasswordVerify(){

        },
        gettingCaptcha(){
          getverificationcode().then(res=>{
            console.log("ds");
            this.getCaptcha(res.data);
          }).catch(err=>{

          });
        },
        getCaptcha(data){
          let a = "data:image/jpg;base64,";
          const b64data = data.split(",")[0];
          //分离取出
          let capt = data.split(",")[1];
          console.log(capt);
          a += b64data;
          $("#captcha_img")[0].src = a;
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
<style scoped>
.logindiv{
  margin:0 auto;
  border:1px;
  height: 30%;
  width: 30%;
  text-align: center;
}
.loginbutton{
  margin: 0 auto;
  height: 20%;
  width: 20%;

}
body{

}

</style>
