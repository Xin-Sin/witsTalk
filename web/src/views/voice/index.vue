<template>
    <div ref="audiosDiv"></div>
  </template>
  
  <script lang="ts" setup>
  import {onMounted, ref} from "vue";
  import {ElMessage} from "element-plus";
  // STUN服务器地址
  const IceServer: RTCIceServer = {
    "credential": "XinSin123",
    "credentialType": "password",
    "urls": "stun:xinsin.top:3478",
    "username": "xinsin"
  }
  //初始化websocket
  let webSocketConnection: WebSocket;
  //获取div
  const audiosDiv = ref<HTMLDivElement>();
  //获取RTC连接
  let connection: RTCPeerConnection = new RTCPeerConnection({"iceServers": [IceServer]});
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
   * 处理RTCTrackEvent
   * @param evt 事件
   */
  const handlerIceServerTrace = function (evt: RTCTrackEvent) {
    //获取远端音频流
    let audioStream = evt.streams[0];
    //通过远端音频流id获取此用户用户名
    let username = usernames.get(audioStream.id);
    //将此流播放
    for (let stream of audioStream.getTracks()) {
      remoteStream.addTrack(stream);
    }
    //添加audio元素至div
    if (audiosDiv.value) {
      //外侧div
      let outDiv = document.createElement("div");
      //audio元素
      let audio = document.createElement("audio");
      //设置自动播放
      audio.autoplay = true;
      //设置可控
      audio.controls = true;
      //设置不可见
      audio.style.display = "none";
      //设置远端音频流
      audio.srcObject = audioStream;
      //用户名元素
      let name = document.createElement("span");
      //设置用户名
      name.innerText = username as string;
      //添加元素至div
      outDiv.appendChild(name);
      outDiv.appendChild(audio);
      audiosDiv.value?.appendChild(outDiv);
    }
  }
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
   * 处理STUN服务器选择事件
   * @param event 事件
   */
  const handlerIceServerIceCandidate = function (event: RTCPeerConnectionIceEvent) {
    if (event.candidate) {
      //广播STUN服务器
      sendWebSocket({"op": "candidate", "data": event.candidate});
    }
  };
  /**
   * 处理用户登录成功事件
   * @param jsonData 数据
   */
  const handlerUserLoginSuccessCallback = async function (jsonData: any) {
    //Login Success Callback
    //是否登录成功
    if (!jsonData.data.right) {
      ElMessage.error("鉴权失败，请重新登录后重试！")
      return;
    }
    //登录成功后将媒体流id和用户名放入表中
    for (let d of jsonData.data.data) {
      usernames.set(d.media, d.user);
    }
    ElMessage.success("鉴权成功！");
    usernames.set(localMediaStream.id, jsonData.data.username);
    //将本地流上传（添加至STUN服务器）
    for (let track of localMediaStream.getTracks()) {
      connection.addTrack(track, localMediaStream)
    }
    //初始化STUN服务器
    connection.ontrack = handlerIceServerTrace;
    connection.onicecandidate = handlerIceServerIceCandidate;
    //创建Offer并广播
    connection.createOffer()
        .then((offer) => {
          sendWebSocket({"op": "offer", "data": offer});
          connection.setLocalDescription(offer);
        }).catch((err) => ElMessage.error("出现错误！" + err));
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
   * 处理Offer事件
   * @param offer 数据
   */
  const handlerOffer = function (offer: RTCSessionDescriptionInit) {
    //设置远端描述为传过来的描述
    connection.setRemoteDescription(new RTCSessionDescription(offer));
    //创建应答数据并广播
    connection.createAnswer().then((answer) => {
      //设置本地描述
      connection.setLocalDescription(answer);
      sendWebSocket({"op": "answer", "data": answer});
    }).catch((err) => ElMessage.error("出现错误" + err))
  }
  /**
   * 处理远端应答事件
   * @param answer 数据
   */
  const handlerAnswer = function (answer: RTCSessionDescriptionInit) {
    //设置远端描述为发过来的
    connection.setRemoteDescription(new RTCSessionDescription(answer));
  }
  /**
   * 处理远端STUN服务器选择事件
   * @param candidate 数据
   */
  const handlerCandidate = function (candidate: object) {
    //将远端的STUN服务器选项加入备选名单中
    connection.addIceCandidate(new RTCIceCandidate(candidate));
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
      case "lcbak":
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