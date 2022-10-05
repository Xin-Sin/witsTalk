<template>
  <div id="message">
    <div class="t-comment__inner">
      <div class="t-comment__avatar">
        <img :src="'data:image/jpg;base64,' + Mdata.base64" alt="" class="t-comment__avatar-image" style="margin-left:10%">
      </div>
      <div class="t-comment__content">
        <div class="t-comment__author">
          <span class="t-comment__name">{{Mdata.sender}}</span>
          <span class="t-comment__time">{{Mdata.sendtime}}</span>
        </div>
        <div class="t-comment__detail">
          <el-popover
            placement="top"
            width="500">
            <el-button @click="recall">撤回</el-button>
            <el-button>设为精华信息</el-button>
            <el-button slot="reference" type="text" class="content">{{Mdata.content}}</el-button>
          </el-popover>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "message",
  props:['Mdata','ws'],
  data(){
    return {
      Bstyle:"",
      username:sessionStorage.getItem("username"),
      visible:false
    }
  },
  methods:{
    recall(){
      console.log(this.Mdata.id)
      this.ws.send(JSON.stringify({"op":"recall","args":{"id":this.Mdata.id}}))
    }
  },
  created() {
    if (this.Mdata.sender !==  this.username){
      this.Bstyle = "display:none;"
    }
  },
}
</script>

<style scoped>
  .content{
    color: rgba(0, 0, 0, 0.99);
    font-size: 15px;
    padding: 0 !important;
    font-family: "JetBrains Mono",sans-serif;
  }
  #message{
    margin: 10px;
  }
  #message:hover{
    background: rgba(196,196,196,0.6);
    border-color: rgba(196,196,196,1);
    box-shadow: rgba(196,196,196,1) 5px 5px 10px inset;
  }
  .t-comment__inner{
    /* border: solid sandybrown 1px; */
    line-height: 200%;
  }
</style>
