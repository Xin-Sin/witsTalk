<template>
    <el-dialog v-model="showSlideWindow" :width="440" style="background-color: rgba(255,255,255,75%);" title="滑动验证">
      <slide-verify ref="slider" :accuracy="5" :h="150" :w="400" slider-text="向右滑动验证" :imgs="imgVerify" @success="onSlideSuccess"/>
    </el-dialog>
    <div class="background">
      <div id="window" class="login-window">
        <div class="inline-div right">
          <div ref="title" class="login-window-child title" style="margin-top: 5vh;font-size: 20px;overflow: hidden"></div>
  
          <div class="login-window-child input" style="margin-top: 30px;margin-bottom: 30px">
            <el-input v-model="username" placeholder="请在此输入昵称">
              <template #prepend>昵称</template>
            </el-input>
          </div>
          <div class="login-window-child input" style="margin-bottom: 30px">
            <el-input v-model="password" placeholder="请在此输入密码" show-password type="password">
              <template #prepend>密码</template>
            </el-input>
          </div>
          <div class="login-window-child login-button">
            <el-button plain size="large" style="width: 10vh" type="primary" @click="Login">登录</el-button>
          </div>
        </div>
        <div class="inline-div left img"/>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import {onMounted, ref} from "vue";
  import SlideVerify, {SlideVerifyInstance} from "vue3-slide-verify";
  import "vue3-slide-verify/dist/style.css";
  import {login, getHitokoto} from '../../api/user'
  import {ElMessage} from "element-plus";
  
  const imgVerify = ref<Array<string>>(['/static/verify/02617ba25be35ff28689995f5821d6ae.jpg','/static/verify/ad7d7c80f9505796ab3e2465e00c858e.jpg','/static/verify/f0ddf46430d752898b2c46a340de3fd5.jpg','/static/verify/d6d19b44fd9b56f8a5223c605634d1da.jpg','/static/verify/22af7b742d345eb796ce674e6d361380.jpg','/static/verify/c47a5f40ee82590286671257b919731b.jpg','/static/verify/3816a03ec25f53d3b7a3e33154a903b2.jpg','/static/verify/c7b21ed210925d148e964c38849c3a4d.jpg','/static/verify/4270f5260dca57d79d3582dc2d5c836b.jpg','/static/verify/390d7533f19c5e30b25f807fe23beac9.jpg','/static/verify/dec0262729e957cf8ef57ba61d29a89f.jpg','/static/verify/f846386254ae59b5af12d86a84db0fab.jpg','/static/verify/360877981c0a5d9ba1d0acf472a68915.jpg','/static/verify/11e8042b6d8f575b96591507ab5e4a88.jpg','/static/verify/f9a994c3c31b5ecebc31887c6344eee7.jpg','/static/verify/9312caaaa8c05cb1a2fe53cfcbbad548.jpg','/static/verify/854a0868a7425edabb112726d511844d.jpg','/static/verify/233ae109c1aa5f2aaeffdf42411c01ad.jpg','/static/verify/c88a182c2d0850f7b96fdb36c2710905.jpg','/static/verify/69987215f5e25c69987c502255cc0913.jpg','/static/verify/dac3f67eb7f95f28b54991e0200304f4.jpg','/static/verify/014ab90f547e50658baa369bf025f0d2.jpg','/static/verify/b97ff9e2b1e1506aab69bd3f0ef7cdcc.jpg','/static/verify/078269643e5a54379a7d451862a0c592.jpg','/static/verify/9911ea72c55554eb9e1b212a40e0c669.jpg','/static/verify/782524b7fc595cc4bb4c39368bf7e457.jpg','/static/verify/07ed176ace0b52b5b0945a8377f715c3.jpg','/static/verify/1d211f4e950a5d3f8b2a1505abb027eb.jpg','/static/verify/dcdb182c378053a3be596613c68c5169.jpg','/static/verify/d2f841cfb89256c4af25263b84ad7f7b.jpg','/static/verify/ed1104e6782f5cc290b7d77db6a07c78.jpg','/static/verify/facf57a84191525b98d1849e0ef4da1c.jpg','/static/verify/8fb9d56cbc98558d9ee156cdd936d013.jpg','/static/verify/914cfe208dcb5244a2e0176082f27f62.jpg','/static/verify/a9a2d74a4c7859509f8c5b508e947466.jpg','/static/verify/5c9586dbf7235b7d935e3f42570a1353.jpg','/static/verify/c2478f2557d8564a95ee26666bc8f3c5.jpg','/static/verify/5388021c454c5390b811e9be5b9adc54.jpg','/static/verify/ac32c6127ab35dfc93e0d610caac9d66.jpg','/static/verify/4e624b9210765c41a1970925de52927b.jpg','/static/verify/9ca5c7fb2da457d28a46c32e5057005b.jpg','/static/verify/2cc36492d02356aebad5f7ecf077a70a.jpg','/static/verify/b6c25268e3525f878dfd6f7b5533b8aa.jpg','/static/verify/66efbada1df65bb394e9c152133b1d32.jpg','/static/verify/792e0ffcec85508bbb70a5caecec7f75.jpg','/static/verify/516fa62dbf9c583493f963ab60b2c8b5.jpg','/static/verify/fb11b0594fea5b7a8cf4dc17e2a5b19f.jpg','/static/verify/dbdfffb42755508e956d595c98047f6d.jpg','/static/verify/99fa72f1224454cc91d59c0075281259.jpg','/static/verify/70ffb1a7705358d693fc39729489f670.jpg','/static/verify/b3c4b61e9d9257839058696e3d7ae360.jpg','/static/verify/f7cc4d1aea445d9da146c322ba655ca6.jpg','/static/verify/0367b9092bf35dd6993b7f70a750cf3e.jpg','/static/verify/819d5a4e4a80553eaf95147d8fda67f8.jpg','/static/verify/d26dc6ba7eb45008809ebfa4cbc52443.jpg','/static/verify/abaa28bbd29d53d295696f0fa3a4303e.jpg','/static/verify/892b3074fe8251a69c1b3508820b0421.jpg','/static/verify/a44a83bef8ea5d439cc78160f077ee87.jpg','/static/verify/51dd13be04e058ae8a8c05116f938763.jpg','/static/verify/03c9b1640f1951b8b3a3f52e0888551e.jpg','/static/verify/a5f418f0a53d51828fa0eaffaa5d20d1.jpg','/static/verify/bb1f33c1bbc65b48a27a020fc1f1b5b1.jpg','/static/verify/e9875fe427aa5680a44a44fb4f9a2bc8.jpg','/static/verify/277eb95c7bb6550d83b1309c5c9359f1.jpg','/static/verify/0cbf1fd6a0a2580cbbcb18244fffc7d7.jpg','/static/verify/bf26bf4390eb5da5bca53f92fbf24356.jpg','/static/verify/d27219b949ad50e9b69d2d2e3b12bed8.jpg','/static/verify/248d57f0ebc35898b66390eb7b548b74.jpg','/static/verify/9daccf59b56d536aa8ad0411b37b7bd5.jpg','/static/verify/080b3e9f4f8954aca7bb6d6925536911.jpg']);
  const username = ref<string>();
  const password = ref<string>();
  const showSlideWindow = ref<boolean>();
  const slider = ref<SlideVerifyInstance>();
  const title = ref<HTMLDivElement>();
  const Login = function () {
    if (username.value !== '' && password.value !== '') {
      showSlideWindow.value = true;
      slider.value?.refresh();
    } else {
      ElMessage.error("请输入账号密码！")
    }
  }
  onMounted(async () => {
    await getHitokoto().then(async (data) => {
      if (title.value) {
        title.value.innerText = data.data.hitokoto;
      }
    });
    document.addEventListener("keydown", ev => {
      if ((ev.key) === 'Enter') {
        Login()
      }
    })
  })
  const onSlideSuccess = function () {
    login(username.value as string, password.value as string).then((data) => {
      showSlideWindow.value = false;
      let canLogin: boolean = data.data.data.canLogin;
      if (canLogin) {
        ElMessage.success("登录成功！");
        window.sessionStorage.setItem("username", username.value as string);
        window.sessionStorage.setItem("headimg", data.data.data.base64);
        window.sessionStorage.setItem("auth", data.data.data.auth);
        if(window.innerWidth > 800){
          window.location.hash = "/home";
        }else{
            
        }
      } else {
        ElMessage.error("用户名或密码错误！");
      }
    })
  }
  </script>
  
  <style scoped>
  .login-window {
    border: 2px var(--el-border-color);
    border-radius: var(--el-border-radius-round);
    box-shadow: var(--el-box-shadow-dark);
    width: 60%;
    height: 40%;
    min-height: 300px;
    min-width: 270px;
    left: 50%;
    top: 50%;
    margin-left: -30%;
    margin-top: -15%;
    position: absolute;
  }
  
  .login-window-child {
    position: relative;
    margin-top: 10px;
  }
  
  .input {
    margin-left: 10%;
    width: 70%;
  }
  
  .login-button {
    margin-left: 40%;
  }
  
  .inline-div {
    display: inline;
  }
  
  .left {
    position: absolute;
    left: 0;
    width: 50%;
    height: 100%;
  }
  
  .right {
    position: absolute;
    right: 0;
    width: 50%;
    height: 100%;
    background-color: rgba(255, 255, 255, 30%);
    backdrop-filter: blur(6px);
    border-right: 2px var(--el-border-color);
    border-top-right-radius: var(--el-border-radius-round);
    border-bottom-right-radius: var(--el-border-radius-round);
  }
  
  .img {
    background-repeat: no-repeat;
    background-image: url("../../assets/img.png");
    background-size: 100% 100%;
    border-left: 2px var(--el-border-color);
    border-top-left-radius: var(--el-border-radius-round);
    border-bottom-left-radius: var(--el-border-radius-round);
  }
  
  .title {
    font-size: 2vw;
    font-family: "Helvetica Neue", sans-serif;
    text-align: center;
  }
  
  @media screen and (max-width: 1000px) {
    .img {
      display: none;
    }
  
    .right {
      width: 100%;
      border-left: 2px var(--el-border-color);
      border-top-left-radius: var(--el-border-radius-round);
      border-bottom-left-radius: var(--el-border-radius-round);
    }
  
    .title {
      font-size: 4vw;
    }
  
    .login-button {
      margin-left: 35%;
    }
  }
  
  /*背景图层*/
  div.background {
    width: 100%;
    height: 100vh;
    background-repeat: no-repeat;
    background-image: url("../../assets/img.png");
    background-size: 100% 100%;
  }
  </style>