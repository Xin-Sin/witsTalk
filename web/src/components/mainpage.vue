<template>
    <t-layout class="mainlayout">
      <t-header height="60px">
        <t-row>
          <t-col :span="1" style="margin: 0">
            <h3 id="username">{{username}}</h3>
          </t-col>
          <t-col :span="10">
            <t-addon append="搜索" style="width:70%;padding-left: 30%">
              <t-input placeholder="请输入聊天记录"  @enter="search()"/>
            </t-addon>
          </t-col>
          <t-col :span="1" style="float:right;margin-top: 1%">
            <t-avatar  style="float:right; margin-right: 10px;" :image="hdimg" ></t-avatar>
          </t-col>
        </t-row>
      </t-header>
      <t-layout>
        <t-aside style="border-top: 1px solid var(--component-border);">
          <t-menu theme="light" :value="this.$route.path.replace('/download','').replace('/upload','')">
            <t-menu-item to="/main/chat" value="/main/chat"><icon slot="icon" name="chat"/>聊天</t-menu-item>
            <t-menu-item to="/main/voiceChat" value="/main/voiceChat"><icon slot="icon" name="service"/>语音聊天</t-menu-item>
            <t-menu-item to="/main/file" value="/main/file"><icon slot="icon" name="cloud-upload"/>文件传输</t-menu-item>
            <t-menu-item value="game"><icon slot="icon" name="app"/>小♂游戏</t-menu-item>
            <t-menu-item value="settings"><icon slot="icon" name="setting"/>个人设置</t-menu-item>
          </t-menu>
        </t-aside>
        <t-layout>
          <t-content class="maincontent">
            <router-view />
          </t-content>
        </t-layout>
      </t-layout>
    </t-layout>
</template>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.active-mainPages{
  font-size: 17px;
  font-weight: bold;
  color: #C4C4C4;
}
#username{
  margin: 0;
  font-family: "Helvetica Neue",Arial,sans-serif;
  font-size: 30px;
  color: hotpink;
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
import Search from "./search";
import {SEARCH} from "../mixin/mixin";
import {SearchIcon} from "tdesign-icons-vue";
export default {
  components: {Search, Icon },
  name:"mainpage",
  mixins:[SEARCH],
  data(){
    return{
      username:sessionStorage.getItem("username"),
      hdimg:""
    }
  },
  methods:{
    renderIcon() {
      return <SearchIcon />;
    },
    search(){
      console.log("1322312");
    }
  },
  created() {
    // 设置暗色模式
    //document.documentElement.setAttribute('theme-mode', 'dark');
  },
  mounted() {
    getHeadImg(this.username).then((data) => {
      let base64 = data.data.data;
      this.hdimg = "data:image/png;base64," + base64;
    });
  }

}
</script>

