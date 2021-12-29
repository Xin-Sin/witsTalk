<template>
  <div id="background">
    <div id="chatdiv">
      <div id="chat" ref="chat">

      </div>
    </div>
    <div id="users">
    </div>
    <div id="lists">
      <div id="list1" ref="list1">

      </div>
    </div>
    <div id="searchbox">
    </div>
  </div>
</template>
<script>
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#background{
  /* 底图 */


  position: absolute;
  width: 1920px;
  height: 1080px;

  background: #1C1A1D;
  border: 1px solid #000000;
  box-sizing: border-box;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 25px;
}


#chatdiv{
  /* 聊天基本功能 */


  /* Auto Layout */

  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 0px;

  position: relative;
  width: 1215px;
  height: 901.87px;

  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
#chat{
  /* 聊天窗口 */


  position: static;
  width: 1215px;
  height: 795px;
  left: 0px;
  top: 0px;

  background: #36393F;
  border-radius: 15px;

  /* Inside Auto Layout */

  flex: none;
  order: 0;
  flex-grow: 0;
  margin: 1px 0px;
}


#users{
  position: relative;
  width: 315px;
  height: 795px;

  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
#lists{
  /* 列表综合 */


  position: relative;
  width: 294px;
  height: 677px;

  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
#list1{
  /* 列表 - 1 */


  position: absolute;
  width: 281px;
  height: 61px;
  left: 0px;
  top: 0px;

  background: #282B30;
  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
}

#searchbox{
  /* 搜索框 */


  position: relative;
  width: 826px;
  height: 65px;

  box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
</style>
