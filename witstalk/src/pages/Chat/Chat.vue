<template>
  <div id="Group245">
    <div id="Admin_Panel">
      <!--发送消息的按钮-->
      <div id="Rectangle178" onclick="sendmessage();">
        发送消息
      </div>
      <!--聊天框-->
      <div id="Rectangle177" ref="Chat">

      </div>
      <!--头像-->
      <div id="Rectangle79" ref="head" />
      <!--左侧工具栏-->
      <ul id="Frame296">
        <li class="single">
          <div>
            <img src="./assets/head.png" alt="#">
            <a href="#"><span>主页面</span></a>
          </div>
        </li>
        <li class="single">
          <div>
            <img src="./assets/head.png" alt="#">
            <a href="#"><span>聊天</span></a>
          </div>
        </li>
        <li class="single">
          <div>
            <img src="./assets/head.png" alt="#">
            <a href="#"><span>文件</span></a>
          </div>
        </li>
        <li class="single">
          <div>
            <img src="./assets/head.png" alt="#">
            <a href="#"><span>个人</span></a>
          </div>
        </li>
        <li class="single">
          <div>
            <img src="./assets/head.png" alt="#">
            <a href="#"><span>退出</span></a>
          </div>
        </li>
      </ul>
      <!--beautiful-->
      <div id="Rectangle33" />
      <!--beautiful-->
      <div id="Rectangle1" />
      <!--用户名-->
      <div id="h4heading" ref="username">
        Username
      </div>
      <!--页面顶部-->
      <div id="Rectangle2" />
    </div>
  </div>
</template>
<script>
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  #Group245{
    position: absolute;
    width: 100%;
    height: 100%;
  }
  #Admin_Panel{
    /* Admin Panel */


    position: absolute;
    width: 100%;
    height: 100%;

    background: #FFFFFF;
    border-radius: 30px;
    overflow: hidden;
  }
  #Rectangle1{
    /* Rectangle 1 */

    /*width 宽度 height 高度*/
    position: absolute;
    width: 13.9%;
    height: 100%;
    left: 0;
    top: 0;

    background: #383F51;
  }
  #Rectangle2{
    /* Rectangle 2 */


    position: absolute;
    width: 86%;
    height: 6.8%;
    left: 13.9%;
    top: 0;

    background: #FFFFFF;
    /* Regular */

    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
  }
  #Rectangle33{
    /* Rectangle 33 */


    position: absolute;
    width: 13.9%;
    height: 7.4%;
    left: 0;
    top: 0;

    /* Light/White */

    background: #FFFFFF;
    opacity: 0.03;

    z-index: 9999;
  }
  #Rectangle79{
    /* Rectangle 79 */


    position: absolute;
    width: 36px;
    height: 36px;
    left: calc(50% - 1.8%/2 + 48%);
    top: calc(50% - 3.3%/2 - 46.7%);

    background: url(./assets/head.png);
    /* Light/White */

    border: 1px solid #FFFFFF;
    box-sizing: border-box;
    filter: drop-shadow(0 2px 2px rgba(0, 0, 0, 0.1));
    border-radius: 20px;

    z-index: 9999;
  }
  #Rectangle177{
    /* Rectangle 177 */


    position: absolute;
    width: 81.4%;
    height: 66.6%;
    left: 16%;
    top: 10.6%;

    background: #C4C4C4;
    border-radius: 40px;
  }
  #Rectangle178{
    /* Rectangle 178 */


    position: absolute;
    width: 6.1%;
    height: 3.2%;
    left: 92%;
    top: 95%;

    /*flex 布局*/
    display: flex;
    /*实现垂直居中*/
    align-items: center;
    /*实现水平居中*/
    justify-content: center;
    background: #C4C4C4;
  }
  #Rectangle178:hover{
    cursor: pointer;
  }
  #h4heading{
    /* h4 heading */


    position: absolute;
    width: 5.2%;
    height: 2.2%;
    left: 15%;
    top: 2.1%;

    /* Heading/H5 heading */

    font-family: Roboto;
    font-style: normal;
    font-weight: 500;
    font-size: 20px;
    line-height: 24px;
    /* identical to box height, or 120% */

    display: flex;
    align-items: flex-end;

    /* Light/Dark */

    color: #343A40;
    z-index: 9999;
  }
  #Frame296{
    /* Frame 296 */


    /* Auto Layout */

    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 0;

    position: absolute;
    width: 13.9%;
    height: 68.5%;
    left: 0;
    top: 7.4%;
  }

  .single{
    /* Menu multi */
    position: static;
    width: 300px;
    height: 50px;
    left: 0px;
    top: 40px;
    z-index: 9998;
    margin-bottom: 13px;
  }
  .single:hover{
    cursor: pointer;
  }
  .single div{
    width: 250px;
    background-color: #C4C4C4;
    border-radius: 0px 30px 30px 0px;
  }
  .single div span{
    font-size: 20px;
    font-weight: bolder;
  }
  .single div img{
    height: 64px;
    width: 64px;
  }
  #Frame296{
    line-height: 50px;
  }
</style>
