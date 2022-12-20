<template>
    <div ref="audiosDiv">
      <User ref="userComponents" v-for="(username, index) in users" :key="index" :username="username" :media-stream="localMediaStream" :web-socket="webSocketConnection" />
    </div>
  </template>
  
  <script lang="ts" setup>
  import {nextTick, onMounted, onUnmounted, ref} from "vue";
  import {ElMessage} from "element-plus";
  import User from "../../components/User.vue"
  const users = ref<Array<string>>([]);
  //初始化websocket
  const webSocketConnection = ref<WebSocket>();
  //获取div
  const audiosDiv = ref<HTMLDivElement>();
  const userComponents = ref<Array<InstanceType<typeof User>>>([]);
  //获取RTC连接
  /**
   * 在websocket通道上发送消息
   * @param data 数据
   */
  const sendWebSocket = function (data: object) {
    if (webSocketConnection.value) {
      // 将object转为JSON发送到后端
      webSocketConnection.value.send(JSON.stringify(data));
    }
  }
  // 音频流id和用户名的对照表
  let usernames: Map<string, string> = new Map<string, string>();
  //本地音频流
  const localMediaStream = ref<MediaStream>();
  /**
   * 处理WebSocket连接事件
   * @param _ 事件
   */
  const handlerWebSocketConnect = async function (_: Event) {
    // 获取用户音频流
    await navigator.mediaDevices.getUserMedia({"audio": true})
        .then((mediaStream) => localMediaStream.value = mediaStream)
        .catch((_) => console.log(_))//ElMessage.error("无法获取用户麦克风，请检查麦克风权限是否给予"))
    ElMessage.success("连接到语音服务器，开始鉴权")
    //登录鉴权
    sendWebSocket({"op": "login", "token": window.localStorage.getItem("token"), "media": localMediaStream.value?.id})
  }
  /**
   * 用户退出事件
   * @param jsonData 数据
   */
  const handlerUserLeave = function (jsonData: any) {
    //将用户从mediaId和username的表中删除
    usernames.delete(jsonData.mediaId);
    users.value = users.value.filter((data) => {
      return data !== jsonData.username;
    })
  }
  /**
   * 处理用户登录成功事件
   * @param jsonData 数据
   */
  const handlerUserLoginSuccessCallback = async function (jsonData: any) {
    //Login Success Callback
    //是否登录成功
    if (!jsonData.data.success) {
      ElMessage.error("鉴权失败，请重新登录后重试！")
      return;
    }
    for (let i in jsonData.data.data) {
      usernames.set(jsonData.data.data[i], i);
      users.value.push(i);
    }
    ElMessage.success("鉴权成功！");
    usernames.set(localMediaStream.value?.id as string, jsonData.data.username);
  }
  /**
   * 处理用户连接事件
   * @param jsonData 数据
   */
  const handlerUserAdd = function (jsonData: any) {
    //将媒体流id和用户名放入表中
    usernames.set(jsonData.mediaId, jsonData.username);
    users.value.push(jsonData.username);
    nextTick(() => {
      userComponents.value[userComponents.value.length - 1].createOffer();
    })
  }

  /**
   * 处理websocket消息事件
   * @param evt 事件
   */
  const handlerWebSocketMessage = function (evt: MessageEvent) {
    let jsonData = JSON.parse(evt.data);
    //获取操作并执行相关方法
    let operator = jsonData.op;
    switch (operator) {
      case "login":
        handlerUserLoginSuccessCallback(jsonData);
        break;
      case "uadd":
        handlerUserAdd(jsonData);
        break;
      case "leave":
        handlerUserLeave(jsonData);
        break;
      case "offer":
        handlerOffer(jsonData);
        break;
      case "candidate":
        handlerCandidate(jsonData);
        break;
      case "answer":
        handlerAnswer(jsonData);
        break;
      default:
        console.log(jsonData)
        break;
    }
  }
  const handlerOffer = function (jsonData: any){
    for (let i of userComponents.value) {
      if(i.getName() == jsonData.from){
        i.handlerOffer(jsonData.data);
        return;
      }
    }
  }
  const handlerCandidate = function (jsonData: any){
    for (let i of userComponents.value) {
      if(i.getName() == jsonData.from){
        i.handlerCandidate(jsonData.data);
        return;
      }
    }
  }
  const handlerAnswer = function (jsonData: any){
    for (let i of userComponents.value) {
      if(i.getName() == jsonData.from){
        i.handlerAnswer(jsonData.data);
        return;
      }
    }
  }
  /**
   * 处理远端WebSocket连接丢失事件
   * @param _ 事件
   */
  const handlerWebSocketClose = function (_: CloseEvent) {
    ElMessage.error("WebSocket连接断开！")
  }
  /**
   * 处理远端WebSocket连接错误事件
   * @param err
   */
  const handlerWebSocketError = function (err: Event) {
    ElMessage.error("WebSocket连接错误！" + err)
  }
  onMounted(() => {
    //初始化WebSocket连接
    webSocketConnection.value = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/voice");
    webSocketConnection.value.onmessage = handlerWebSocketMessage;
    webSocketConnection.value.onopen = handlerWebSocketConnect;
    webSocketConnection.value.onclose = handlerWebSocketClose;
    webSocketConnection.value.onerror = handlerWebSocketError;
  })
  onUnmounted(() => {
    if (webSocketConnection.value) {
      webSocketConnection.value.close()
    }
    userComponents.value.forEach((c) => {
      c.close();
    })
  })
  </script>
  
  <style scoped>
  
  </style>