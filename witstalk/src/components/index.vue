<template>
  <div id="outoutout">
    <div id="outout">
      <div id="out">
        <div id="img"></div>
        <div id="frame28986">
          <div id="fram28985">
            <div id="signinform">
              <div id="signinformweb">
                <div id="frame28976">
                  <div id="frame28968">
                    <div id="frame28977">
                        <label id="texttitle">欢 迎 来 到 登 录 页 面</label>
                        <div id="inputdiv">
                          <input class="inputbox" id="username" ref="username"/>
                          <label id="inputdivtext">账号</label>
                        </div>
                        <div id="inputdiv">
                          <input class="inputbox" id="password" ref="password"/>
                          <label id="inputdivtext">密码</label>
                        </div>
                        <div id="inputdivbelow">
                          <label id="inputdivtext">验证码</label>
                          <div>
                            <input class="inputboxbelow" id="code" ref="code"/>
                            <img id="codeimg" @click="gettingCaptcha"></img>
                          </div>
                        </div>
                    </div>
                  </div>
                  <label id="text" ref="info"></label>
                </div>
              </div>
            </div>
          </div>
          <button id="loginbutton" @click="login">登录</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import qs from 'qs' //引入 node中自带的qs模块（对application/x-www-form-urlencoded数据格式转换）
import { getverificationcode} from '@/api'  // 导入 封装的请求函数
import {Login} from '@/api'
export default {
    name:'Login',
      data(){
        return{
          getverificationcode:{},
          captchaa:''
        }
      },
      methods:{
        login(){
          let captcha = this.$refs.code.value;
          let username = this.$refs.username.value;
          let password = this.$refs.password.value;
          if(captcha.toUpperCase() != this.captchaa.toUpperCase()){
            this.$refs.info.innerText = "验证码错误！";
            this.$refs.code.style.border = "1px solid red";
            this.gettingCaptcha();
          }else{
            this.$refs.info.innerText = "";
            this.$refs.code.style.border = "1px solid #E5E5E5";
            Login({"username":username,"password":hex_md5(password)}).then(res=>{
              console.log("login");
              if(res.data.canLogin){
                this.$refs.info.innerText = "";
                this.$refs.username.style.border = "1px solid #E5E5E5";
                this.$refs.password.style.border = "1px solid #E5E5E5";
                console.log("logined");
              }else{
                this.$refs.info.innerText = "用户名或密码错误";
                this.$refs.username.style.border = "1px solid red";
                this.$refs.password.style.border = "1px solid red";
                this.gettingCaptcha();
              }
            }).catch(err=>{
              console.log(err);
            });
          }
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
          this.captchaa = capt;
          a += b64data;
          $("#codeimg")[0].src = a;
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
  #outoutout{
    position: fixed;
    height: 100%;
    width: 100%;
    background: #E5E5E5;
    border-radius: 8px;
  }

  #outout{
    /* 1440x900 */


    position: relative;
    width: 1440px;
    height: 900px;
    left: 200px;
    top:1.5%;
    /* White (#FFFFFF) */
    border-radius: 8px;
    background: #FFFFFF;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    border-radius: 24px;
  }

  #out{
    /* Sign-In-Form-Desktop */


    /* Auto Layout */

    display: flex;
    flex-direction: row;
    align-items: flex-start;
    padding: 0px;

    position: absolute;
    left: 0px;
    right: 0px;
    top: -0.25px;
    bottom: -0.25px;
    border-radius: 8px;
  }

  #img{
    position: static;
    width: 984px;
    height: 900.5px;
    left: 984px;
    top: 0px;

    background: url(../../static/img.png);
    transform: matrix(-1, 0, 0, 1, 0, 0);

    /* Inside Auto Layout */

    flex: none;
    order: 0;
    align-self: stretch;
    flex-grow: 1;
    border-radius: 8px;
    margin: 0px 0px;
  }

  #maindiv{
    /* Sign-In-Form-Desktop */


    /* Auto Layout */

    display: flex;
    flex-direction: row;
    align-items: flex-start;
    padding: 0px;

    position: absolute;
    left: 0px;
    right: 0px;
    top: -0.25px;
    border-radius: 8px;
    bottom: -0.25px;

  }

  #frame28986{
    /* Frame 28986 */


    /* Auto Layout */

    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: flex-start;
    padding: 48px;

    position: static;
    width: 456px;
    height: 900.5px;
    left: 984px;
    top: 0px;

    /* White (#FFFFFF) */

    background: #FFFFFF;

    /* Inside Auto Layout */

    flex: none;
    order: 1;
    border-radius: 8px;
  }

  #frame28985{
    /* Frame 28985 */


    /* Auto Layout */

    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 0px;

    position: static;
    width: 360px;
    height: 288px;
    left: 48px;
    top: 48px;


    /* Inside Auto Layout */

    flex: none;
    order: 0;
    align-self: stretch;
    flex-grow: 0;
    margin: 228px 0px;
    border-radius: 8px;
  }

  #signinforms{
    1440x900
    Width
    360px
    Height
    288px
    Blend
    Pass through
    Fill container
    Hug contents
    /* Sign-In-Forms */


    /* Auto Layout */

    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 0px;

    position: static;
    width: 360px;
    height: 288px;
    left: 0px;
    top: 0px;


    /* Inside Auto Layout */

    flex: none;
    order: 0;
    align-self: stretch;
    flex-grow: 0;
    margin: 48px 0px;
    border-radius: 8px;
  }

  #signinformweb{
    /* Sign-In-Form-Web */


    /* Auto Layout */

    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 0px;

    position: static;
    width: 360px;
    height: 288px;
    left: 0px;
    top: 0px;

    border-radius: 8px;

    /* Inside Auto Layout */

    flex: none;
    order: 0;
    align-self: stretch;
    flex-grow: 0;
    margin: 24px 0px;
  }

  #frame28976{
    /* Frame 28976 */


    /* Auto Layout */

    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 0px;

    position: static;
    width: 360px;
    height: 288px;
    left: 0px;
    top: 0px;


    /* Inside Auto Layout */

    flex: none;
    order: 0;
    align-self: stretch;
    flex-grow: 0;
    margin: 32px 0px;
  }

  #frame28968{
    /* Frame 28968 */


    /* Auto Layout */

    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 0px;

    position: static;
    width: 360px;
    height: 236px;
    left: 0px;
    top: 52px;


    /* Inside Auto Layout */

    flex: none;
    order: 1;
    align-self: stretch;
    flex-grow: 0;
    margin: 24px 0px;
  }

  #frame28977{
    /* Frame 28977 */


    /* Auto Layout */

    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 0px;

    position: static;
    width: 360px;
    height: 236px;
    left: 0px;
    top: 0px;


    /* Inside Auto Layout */

    flex: none;
    order: 0;
    align-self: stretch;
    flex-grow: 0;
    margin: 20px 0px;
  }

  #texttitle{
    /* 48 000,00 ₽ */


    position: static;
    width: 360px;
    height: 28px;
    left: calc(50% - 360px/2);
    top: calc(50% - 28px/2 - 130px);

    /* Title */

    font-family: Poppins;
    font-style: normal;
    font-weight: 600;
    font-size: 20px;
    line-height: 28px;
    /* identical to box height, or 140% */

    display: flex;
    align-items: center;
  }

  #inputdiv{
    /* Input-Configurator */


    /* Auto Layout */

    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 0px;

    position: static;
    align-self: stretch;
    width: 360px;
    height: 68px;
    left: 0px;
    top: 168px;


    /* Inside Auto Layout */

    flex: none;
    order: 2;
    flex-grow: 0;
    margin: 16px 0px;
  }

  #inputdivbelow{
    /* Input-Configurator */


    /* Auto Layout */

    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 0px;

    position: static;
    width: 360px;
    height: 68px;
    left: 0px;
    top: 168px;


    /* Inside Auto Layout */

    flex: none;
    order: 2;
    flex-grow: 0;
    margin: 16px 0px;
  }

  #inputdivtext{
    /* Satellite-Input */


    /* Auto Layout */

    display: flex;
    flex-direction: row;
    align-items: flex-start;
    padding: 0px 16px;

    position: static;
    width: 360px;
    height: 12px;
    left: 0px;
    top: 0px;


    /* Inside Auto Layout */

    flex: none;
    order: 0;
    align-self: stretch;
    flex-grow: 0;
    margin: 8px 0px;
  }

  .inputbox{
    /* Password-Input */


    position: static;
    width: 360px;
    height: 48px;
    left: 0px;
    top: 20px;


    /* Inside Auto Layout */

    flex: none;
    order: 1;
    align-self: stretch;
    flex-grow: 0;
    margin: 8px 0px;
    /* Input-BG */

    background: #F2F2F2;
    /* Black-100 (#E5E5E5) */

    border: 0.5px solid #E5E5E5;
    box-sizing: border-box;
    border-radius: 6px;
  }

  .inputboxbelow{
    /* Password-Input */


    position: static;
    width: 360px;
    height: 48px;
    left: 0px;
    top: 20px;


    /* Inside Auto Layout */

    flex: none;
    order: 1;
    flex-grow: 0;
    margin: 8px 0px;
    /* Input-BG */

    background: #F2F2F2;
    /* Black-100 (#E5E5E5) */

    border: 0.5px solid #E5E5E5;
    box-sizing: border-box;
    border-radius: 6px;
  }

  #codeimg{
    width: 100;
    height: 50;
    margin: 17px 0px;
  }

  #frame28978{
    /* Frame 28978 */


    /* Auto Layout */

    display: flex;
    flex-direction: row;
    align-items: flex-start;
    padding: 0px;

    position: absolute;
    left: 71.46%;
    right: 3.54%;
    top: 40.29%;
    bottom: 57.49%;

  }
  #loginbutton{
    /* Primary-button-40px */


    /* Auto Layout */

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 10px 24px;

    position: absolute;
    left: 71.46%;
    right: 3.54%;
    top: 63.51%;
    bottom: 32.04%;

    /* System-Blue (#007AFF) */

    background: #007AFF;
    border-radius: 6px;
  }
  #text{
    /* info */


    position: static;
    width: 200px;
    height: 20px;
    left: 0px;
    top: 312px;

    font-family: Roboto;
    font-style: normal;
    font-weight: bold;
    font-size: 15px;
    line-height: 20px;
    /* identical to box height, or 133% */

    display: flex;
    align-items: center;
    text-align: center;
    letter-spacing: 0.3px;

    color: #FB0000;


    /* Inside Auto Layout */

    flex: none;
    order: 2;
    flex-grow: 0;
    margin: 26.5% 0px;
  }
</style>
