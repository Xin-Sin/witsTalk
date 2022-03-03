<template>
  <div style="width: 100%;height: 100%">
    <t-layout class="mainlayout">
      <t-header >
        <t-row>
          <t-col :span="4">
            <h3 id="username">{{username}}</h3>
          </t-col>
          <t-col :offset="4" :span="4" style="float:right;">
            <t-avatar size='64px' style="float:right; margin-right: 10px;" :image="hdimg" ></t-avatar>
          </t-col>
        </t-row>
      </t-header>
      <t-layout>
        <t-aside style="border-top: 1px solid var(--component-border);">
          <t-menu theme="light" value="dashboard">
<!--            //使用to来实现路由跳转
            //active-class 当给链接被激活时展示的样式-->
            <router-link to="/main/chat" active-class="active-mainPages"><t-menu-item value="chat"><icon slot="icon" name="chat"/>聊天</t-menu-item></router-link>
            <router-link to="/main/voicechat" active-class="active-mainPages"><t-menu-item value="voice-chat"><icon slot="icon" name="service"/>语音聊天</t-menu-item></router-link>
            <router-link to="/main/file" active-class="active-mainPages"><t-menu-item value="file"><icon slot="icon" name="cloud-upload"/>文件传输</t-menu-item></router-link>
            <router-link to="#" active-class="active-mainPages"><t-menu-item value="game"><icon slot="icon" name="app"/>小♂游戏</t-menu-item></router-link>
            <router-link to="#" active-class="active-mainPages" ><t-menu-item value="settings"><icon slot="icon" name="setting"/>个人设置</t-menu-item></router-link>
          </t-menu>
        </t-aside>
        <t-layout>
          <t-content class="maincontent">
            <router-view />
          </t-content>
        </t-layout>
      </t-layout>
    </t-layout>
  </div>
</template>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.active-mainPages{
  font-size: 17px;
  font-weight: bold;
  color: #C4C4C4;
}
#username{
  font-family: "Helvetica Neue",Arial,sans-serif;
  font-size: xxx-large;
  margin-top: 0;
  margin-left: 10px;
}
.maincontent{
  overflow-y:hidden;
  overflow-x:hidden;
}
.mainlayout{
  height: 100%;
  width: 100%;
  padding: 0;
  margin: 0;
  position: absolute;
  left: 0;
  top: 0;
}
</style>
<script>
import { Icon } from 'tdesign-icons-vue';
import {getHeadImg} from '@/components/axios/request';
export default {
  components: { Icon },
  name:"mainpage",
  data(){
    return{
      username:window.localStorage.getItem("username"),
      hdimg:""
    }
  },
  methods:{
  },
  created() {
    // 设置暗色模式
    //document.documentElement.setAttribute('theme-mode', 'dark');
    getHeadImg(this.username).then((data) => {
      let base64 = data.data.data;
      this.hdimg = "data:image/png;base64," + base64;

    });
  },

}
</script>

