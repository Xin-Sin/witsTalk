<template>
  <div style="width: 100%;height: 100%">
    <t-layout class="mainlayout">
      <t-header >
        <t-row>
          <t-col :span="4">
            <h3 id="username">User Name</h3>
          </t-col>
          <t-col :offset="4" :span="4" style="float:right;">
            <t-avatar style="float:right;">W</t-avatar>
          </t-col>
        </t-row>
      </t-header>
      <t-layout>
        <t-aside style="border-top: 1px solid var(--component-border);">
          <t-menu theme="light" value="dashboard">
            <t-menu-item value="chat" @click="GoToChat">
              <icon slot="icon" name="chat"/>聊天
            </t-menu-item>
            <t-menu-item value="voice-chat">
              <icon slot="icon" name="service"/>语音聊天
            </t-menu-item>
            <t-menu-item value="file">
              <icon slot="icon" name="cloud-upload"/>文件传输
            </t-menu-item>
            <t-menu-item value="game">
              <icon slot="icon" name="app"/>小♂游戏
            </t-menu-item>
            <t-menu-item value="settings">
              <icon slot="icon" name="setting"/>个人设置
            </t-menu-item>
          </t-menu>
        </t-aside>
        <t-layout>
          <t-content>
            <router-view />
          </t-content>
        </t-layout>
      </t-layout>
    </t-layout>
  </div>
</template>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#username{
  font-family: "Helvetica Neue",Arial,sans-serif;
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
export default {
  components: { Icon },
  name:"mainpage",
  created() {
    // 设置暗色模式
    //document.documentElement.setAttribute('theme-mode', 'dark');
  },
  methods:{
    GoToChat(){
      this.$router.push("main/chat");
    }
  }
}
</script>

