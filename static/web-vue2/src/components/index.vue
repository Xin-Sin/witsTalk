<template>
  <div class="container-fluid">
    <div class="logindiv">
      <div class="input-group input-group-sm">
        <span class="input-group-addon" id="usernamediv" >用户名:</span>
        <input type="text" class="form-control" placeholder="用户名" aria-describedby="usernamediv" v-model="username" id="username" onfocus="$('#errorUsername')[0].innerText=''" >
	            <span class="input-group-addon" id="errorUsername" ></span>
      </div>
      <br>
      <div class="input-group input-group-sm">
        <span class="input-group-addon" id="passworddiv">密码:</span>
        <input type="password" class="form-control" placeholder="密码" aria-describedby="passworddiv" v-model="password" id="password" onfocus="$('#errorPassword')[0].innerText=''" >
        <span class="input-group-addon" id="errorPassword"></span>
      </div>
      <br>
      <div class="input-group input-group-sm">
        <span class="input-group-addon" id="captchadiv">验证码:</span>
        <input type="text" class="form-control" placeholder="验证码" aria-describedby="captchadiv" id="captcha" onfocus="$('#errorCaptcha')[0].innerText=''" >
        <span class="input-group-addon" id="errorCaptcha"></span>
      </div>
      <br>
      <div class="col-xs-6 col-md-3">
        <a href="#" class="thumbnail">
          <img id="captcha_img" onclick="getcaptcha()">
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
export default {
    name:'Login',
      data(){
        return{
          username:'',
          password:''
        }
      },
      methods:{
        login(){
          console.log(this.username)
          console.log(this.password)
        }
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
    #errorUsername,#errorPassword,#errorCaptcha{
      color: red;
    }
</style>
