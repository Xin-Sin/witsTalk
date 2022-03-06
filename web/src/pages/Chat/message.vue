<template>
  <div id="message">
    <div class="Recall_button" :style="Bstyle">
      <el-button class="rb">撤回这条消息</el-button>
    </div>
    <div class="t-comment__inner">
      <div class="t-comment__avatar">
        <img :src="'data:image/jpg;base64,' + Mdata.base64" alt="" class="t-comment__avatar-image" style="margin-left:10%">
      </div>
      <div class="t-comment__content">
        <div class="t-comment__author">
          <span class="t-comment__name">{{Mdata.sender}}</span>
          <span class="t-comment__time">{{Mdata.sendtime}}</span>
        </div>
        <div class="t-comment__detail">{{Mdata.content}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "message",
  props:['Mdata'],
  data(){
    return {
      Bstyle:"",
      username:this.$route.query.a,
    }
  },
  created() {

    if (this.Mdata.sender !==  this.username){
      this.Bstyle = "display:none;"
    }
  },
}
</script>

<style >
  #message:hover{
    background: rgba(196,196,196,0.6);
    border-color: rgba(196,196,196,1);
    box-shadow: rgba(196,196,196,1) 5px 5px 10px inset;
  }
  .t-comment__inner{
    /* border: solid sandybrown 1px; */
    line-height: 200%;
  }
  .Recall_button{
    float:right;
    size:64px 64px;
    margin-top: 10px;
    margin-right: 10px;
  }
  .rb{
    margin:0 auto;
  }
</style>
