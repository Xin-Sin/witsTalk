<template>
  <div id="message">
    <div class="t-comment__inner">
      <div class="t-comment__avatar">
        <img :src="'data:image/jpg;base64,' + data.base64" alt="" class="t-comment__avatar-image">
      </div>
      <div class="t-comment__content">
        <div class="t-comment__author">
          <span class="t-comment__name">{{data.sender}}</span>
          <span class="t-comment__time">{{data.sendtime}}</span>
        </div>
        <div class="t-comment__detail">{{data.content}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "message",
  props:['data'],
}
</script>

<style >
  .t-comment__inner{
    border: solid sandybrown 1px;
    line-height: 200%;
  }
</style>
