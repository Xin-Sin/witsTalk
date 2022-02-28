<template>
  <el-row :gutter="10" style="height: 80%">
    <el-col :span="20" style="height: 100%;">
      <!--      //展示消息数据-->
      <div id="message1">

      </div>
      <el-button :type="button_type" :icon="button_icon" ref="je" class="jb" @click="join">{{button_text}}</el-button>
    </el-col>
    <!--    //展示在线人员-->
    <el-col :span="4" id="online-user">
      <el-table :data="administrator">
        <el-table-column prop="username" label="管理员"></el-table-column>
      </el-table>
      <el-table :data="user">
        <el-table-column prop="username" label="用户"></el-table-column>
      </el-table>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "voicechat",
  data(){
    return {
      Isjoin:false,
      button_icon:"el-icon-phone",
      button_text:"加入语音聊天",
      button_type:"primary",
      user:[],
      administrator:[]
    }
  },
  methods:{
    join(){
      if(this.Isjoin === false){
        this.button_icon = "el-icon-phone-outline";
        this.button_type = "danger";
        this.button_text = "退出语音通话"
        this.Isjoin = true;
      }else{
        this.button_icon = "el-icon-phone";
        this.button_type = "primary";
        this.button_text = "加入语音通话"
        this.Isjoin = false;
      }
    },
    join_voicechat(){

    },
    exit_voicechat(){

    },
  },
}
</script>

<style scoped>
#message1{
  background: #C4C4C4;
  width: 100%;
  height: 100%;
  border-radius: 30px;
  margin-bottom: 20px;
}
.jb{
  display: block;
  margin: 0 auto;
}
</style>
