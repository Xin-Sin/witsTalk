<template>
    <div ref="audiosDiv">
      <User v-for="" :media-stream="localMediaStream" :web-socket="webSocketConnection" />
    </div>
  </template>
  
  <script lang="ts" setup>
  import {onMounted, ref} from "vue";
  import {ElMessage} from "element-plus";
  const users = ref<string>();
  //初始化websocket
  let webSocketConnection: WebSocket;
  //获取div
  const audiosDiv = ref<HTMLDivElement>();
  //获取RTC连接
  /**
   * 在websocket通道上发送消息
   * @param data 数据
   */
  const sendWebSocket = function (data: object) {
    if (webSocketConnection) {
      // 将object转为JSON发送到后端
      webSocketConnection.send(JSON.stringify(data));
    }
  }
  // 音频流id和用户名的对照表
  let usernames: Map<string, string> = new Map<string, string>();
  //本地音频流
  let localMediaStream: MediaStream;
  //远端音频流
  const remoteStream = new MediaStream();
  /**
   * 处理WebSocket连接事件
   * @param _ 事件
   */
  const handlerWebSocketConnect = async function (_: Event) {
    // 获取用户音频流
    await navigator.mediaDevices.getUserMedia({"audio": true})
        .then((mediaStream) => localMediaStream = mediaStream)
        .catch((_) => console.log(_))//ElMessage.error("无法获取用户麦克风，请检查麦克风权限是否给予"))
    ElMessage.success("连接到语音服务器，开始鉴权")
    //登录鉴权
    sendWebSocket({"op": "login", "token": sessionStorage.getItem("token"), "media": localMediaStream.id})
  }
  /**
   * 用户退出事件
   * @param jsonData 数据
   */
  const handlerUserLeave = function (jsonData: any) {
    //将用户从mediaId和username的表中删除
    usernames.delete(jsonData.user);
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
    ElMessage.success("鉴权成功！");
    usernames.set(localMediaStream.id, jsonData.data.username);

  }
  /**
   * 处理用户连接事件
   * @param jsonData 数据
   */
  const handlerUserAdd = function (jsonData: any) {
    //将媒体流id和用户名放入表中
    usernames.set(jsonData.data.id, jsonData.data.name);
  }

  /**
   * 处理websocket消息事件
   * @param evt 事件
   */
  const handlerWebSocketMessage = function (evt: MessageEvent) {
    let jsonData = JSON.parse(evt.data);
    console.log(jsonData);
    //获取操作并执行相关方法
    let operator = jsonData.op;
    switch (operator) {
      case "login":
        handlerUserLoginSuccessCallback(jsonData);
        break;
      case "uadd":
        handlerUserAdd(jsonData);
        break;
      case "offer":
        handlerOffer(jsonData.data);
        break;
      case "answer":
        handlerAnswer(jsonData.data);
        break;
      case "candidate":
        handlerCandidate(jsonData.data);
        break;
      case "leave":
        handlerUserLeave(jsonData);
        break;
      default:
        break;
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
    webSocketConnection = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/voice");
    webSocketConnection.onmessage = handlerWebSocketMessage;
    webSocketConnection.onopen = handlerWebSocketConnect;
    webSocketConnection.onclose = handlerWebSocketClose;
    webSocketConnection.onerror = handlerWebSocketError;
  })
  </script>
  
  <style scoped>
  
  </style>