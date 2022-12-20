<template>
  <div class="main">
    <div class="audios-div">
      <User ref="userComponents" v-for="(username, index) in users" :key="index" :username="username" :media-stream="localMediaStream" :web-socket="webSocketConnection" />
    </div>
    <div class="control-panel">
      <div class="control-panel-settings">
        <div class="control-panel-item">
          <el-switch @change="onNoiseSuppressionChange" v-model="noiseSuppression" />
          <div class="control-panel-item-text">噪声消除</div>
        </div>
        <div class="control-panel-item">
          <el-switch @change="onEchoCancellationChange" v-model="echoCancellation" />
          <div class="control-panel-item-text">回声消除</div>
        </div>
      </div>
      <div class="control-panel-settings">
        <div class="control-panel-item-text">麦克风选择：</div>
        <el-select @change="onMicrophoneChange" v-model="microphone" class="m-2" placeholder="Select" size="large">
          <el-option
              v-for="item in microphones"
              :key="item.deviceId"
              :label="item.label"
              :value="item.deviceId"
          />
        </el-select>
      </div>
      <div class="control-panel-join">
        <el-button class="control-panel-join-button" v-if="join" type="danger" :icon="PhoneFilled" @click="leaveVoiceChat">退出语音</el-button>
        <el-button class="control-panel-join-button" v-else type="primary" :icon="Phone" @click="joinVoiceChat">加入语音</el-button>
      </div>
    </div>
  </div>
  </template>
  
  <script lang="ts" setup>
  //导入外部库
  import {nextTick, onMounted, onUnmounted, reactive, ref} from "vue";
  import {ElMessage} from "element-plus";
  import User from "../../components/User.vue"
  import {Phone, PhoneFilled} from "@element-plus/icons-vue";
  import {useStore} from "../../store";
  import {storeToRefs} from "pinia";
  //所有用户的用户名
  const users = ref<Array<string>>([]);
  //噪声消除是否启用
  const noiseSuppression = ref<boolean>(true);
  //回声消除是否启用
  const echoCancellation = ref<boolean>(true);
  //是否加入
  const join = ref<boolean>(false);
  //存储器
  const store = useStore();
  //初始化websocket变量
  const webSocketConnection = ref<WebSocket>();
  //获取语音的所有用户的实例
  const userComponents = ref<Array<InstanceType<typeof User>>>([]);
  //本地音频流
  const localMediaStream = ref<MediaStream>();
  //所有麦克风
  const microphones = reactive<Array<MediaDeviceInfo>>([]);
  const microphone = ref<string>();
  /**
   * 处理WebSocket连接事件
   * @param _ 事件
   */
  const handlerWebSocketConnect = async function (_: Event) {
    //若navigator.mediaDevices为空，则说明网站未使用https或浏览器不支持WebRTC
    if(!navigator.mediaDevices){
      //进行提示
      ElMessage.error("emm，你使用的网站似乎没有使用https，请在chrome://flags/#unsafely-treat-insecure-origin-as-secure将unsafely-treat-insecure-origin-as-secure功能启用并将此网站域名（" + window.location.protocol + "//" + window.location.hostname + ":" + window.location.port + "）加入信任列表")
      //关闭WebSocket连接
      webSocketConnection.value?.close()
      return;
    }
    //构造器
    const constraints: MediaStreamConstraints = {audio: {deviceId: microphone.value, echoCancellation: echoCancellation.value, noiseSuppression: noiseSuppression.value}}
    //获取用户音频流
    await navigator.mediaDevices.getUserMedia(constraints)
        //将获取到的MediaStream赋值给ref并传递
        .then((mediaStream) => localMediaStream.value = mediaStream)
        .catch((_) => console.log(_))//ElMessage.error("无法获取用户麦克风，请检查麦克风权限是否给予"))
    ElMessage.success("连接到语音服务器，开始鉴权")
    //登录鉴权
    webSocketConnection.value!.send(JSON.stringify({"op": "login", "token": window.localStorage.getItem("token"), "media": localMediaStream.value?.id}));
  }
  /**
   * 用户退出事件
   * @param jsonData 数据
   */
  const handlerUserLeave = (jsonData: any) => {
    //将用户从组件中移除
    users.value = users.value.filter((data) => data !== jsonData.username)
  }
  /**
   * 处理用户登录成功事件
   * @param jsonData 数据
   */
  const handlerUserLoginSuccessCallback = async (jsonData: any) => {
    //是否登录成功
    if (!jsonData.data.success) {
      ElMessage.error("鉴权失败，请重新登录后重试！")
      return;
    }
    //jsonData.data.data为已经加入的用户列表
    for (let i in jsonData.data.data) {
      //for循环取出mediaId（此ID每个设备唯一）
      //添加到组件表中
      users.value.push(i);
    }
    ElMessage.success("鉴权成功！");
  }
  /**
   * 处理用户连接事件
   * @param jsonData 数据l
   */
  const handlerUserAdd = (jsonData: any) => {
    //将此用户加入组件表中
    users.value.push(jsonData.username);
    //等待渲染后
    nextTick(() => {
      //取出最后加入语音的用户并交换SDP（Session Description Protocol，即两机器的编码器类型和解码器类型）
      userComponents.value[userComponents.value.length - 1].createOffer();
    })
  }

  /**
   * 处理websocket消息事件
   * @param evt 事件
   */
  const handlerWebSocketMessage = (evt: MessageEvent) => {
    let jsonData = JSON.parse(evt.data);
    //获取操作并执行相关方法
    let operator = jsonData.op;
    switch (operator) {
      case "login":
        //处理用户登陆成功
        handlerUserLoginSuccessCallback(jsonData);
        break;
      case "uadd":
        //处理用户增加
        handlerUserAdd(jsonData);
        break;
      case "leave":
        //处理用户离开
        handlerUserLeave(jsonData);
        break;
      case "offer":
        //处理信令服务器（即做客户端与客户端之间信息交换的服务端，常作房间管理、登陆验证等功能）（其他用户）发送的SDP信息
        handlerOffer(jsonData);
        break;
      case "candidate":
        //处理信令服务器（其他用户）发送的Candidate（即两机器的网络信息）信息
        handlerCandidate(jsonData);
        break;
      case "answer":
        //处理信令服务器（其他用户）发送的Answer（即对于SDP的回应）信息
        handlerAnswer(jsonData);
        break;
      default:
        break;
    }
  }
  /**
   * 处理信令服务器（其他用户）发送的SDP信息
   * @param jsonData 消息内容，格式为{"type": "offer", "data": Object格式SDP数据, "from": 发送者用户名}
   */
  const handlerOffer = (jsonData: any) => {
    for (let i of userComponents.value) {
      //循环查找发送的用户
      //比对用户名
      if(i.getName() == jsonData.from){
        //交由此用户的组件处理
        i.handlerOffer(jsonData.data);
        return;
      }
    }
  }
  /**
   * 处理信令服务器（其他用户）发送的SDP信息
   * @param jsonData 消息内容，格式为{"type": "candidate", "data": Object格式Candidate数据, "from": 发送者用户名}
   */
  const handlerCandidate = (jsonData: any) => {
    for (let i of userComponents.value) {
      //循环查找发送的用户
      //比对用户名
      if(i.getName() == jsonData.from){
        //交由此用户的组件处理
        i.handlerCandidate(jsonData.data);
        return;
      }
    }
  }
  /**
   * 处理信令服务器（其他用户）发送的Answer信息
   * @param jsonData 消息内容，格式为{"type": "answer", "data": Object格式Answer数据, "from": 发送者用户名}
   */
  const handlerAnswer = (jsonData: any) => {
    for (let i of userComponents.value) {
      //循环查找发送的用户
      //比对用户名
      if(i.getName() == jsonData.from){
        //交由此用户的组件处理
        i.handlerAnswer(jsonData.data);
        return;
      }
    }
  }
  /**
   * 处理远端WebSocket连接丢失事件
   * @param event 事件
   */
  const handlerWebSocketClose = (event: CloseEvent) => {
    //比对event断开连接时的代码，正常断开为1005
    if(event.code != 1005){
      ElMessage.error("WebSocket连接断开！")
    }
    //清空组件表
    users.value = [];
  }
  /**
   * 处理远端WebSocket连接错误事件
   * @param err
   */
  const handlerWebSocketError = (err: Event) => {
    ElMessage.error("WebSocket连接错误！" + err)
  }
  /**
   * 当加载
   */
  const mount = () => {
    //提取出数据并向ref中绑定
    let {settings} = storeToRefs(store);
    noiseSuppression.value = settings.value.noiseSuppression;
    echoCancellation.value = settings.value.echoCancellation;
    microphone.value = settings.value.mediaDevice;
    //若navigator.mediaDevices为空，则说明网站未使用https或浏览器不支持WebRTC
    if(!navigator.mediaDevices) {
      //进行提示
      microphone.value = ""
      ElMessage.error("emm，你使用的网站似乎没有使用https，请在chrome://flags/#unsafely-treat-insecure-origin-as-secure将unsafely-treat-insecure-origin-as-secure功能启用并将此网站域名（" + window.location.protocol + "//" + window.location.hostname + ":" + window.location.port + "）加入信任列表")
      //关闭WebSocket连接
      return;
    }
    //获取所有可用麦克风
    navigator.mediaDevices.enumerateDevices().then((devices) => {
      //是否有设备判断
      let hasDevice: boolean = false
      for (let device of devices) {
        //for循环取设备信息
        //判断为音频输入设备（MediaDeviceInfo.kind有三种类型，videoinput即视频输入, audioinput即音频输入 audiooutput即音频输出）
        if (device.kind === "audioinput"){
          //添加到麦克风表中
          microphones.push(device);
          //若有设备
          if(device.deviceId === microphone.value){
            //有设备设置为真
            hasDevice = true;
          }
        }
      }
      //若没有设备
      if(!hasDevice){
        //设置为默认设备（若deviceId为default则使用默认设备）
        microphone.value = "default";
        store.$patch({settings: {mediaDevice: "default"}})
      }
    })
  }
  /**
   * 当卸载
   */
  const unmounted = () => {
    //退出语音
    leaveVoiceChat();
  }
  /**
   * 用于刷新连接
   */
  const refreshConnection = () => {
      ElMessage.success("成功修改，正在重连")
      //退出语音
      leaveVoiceChat(false)
      //若在退出前加入了，则加入语音
    if(join.value){
      joinVoiceChat(false)
    }
  }
  /**
   * 当噪声消除的开关状态被改变
   * @param val 新状态
   */
  const onNoiseSuppressionChange = (val: boolean) => {
    //写入存储
    store.$patch({settings: {noiseSuppression: val}})
    //刷新连接
    refreshConnection()
  }
  /**
   * 当回声消除的开关状态被改变
   * @param val 新状态
   */
  const onEchoCancellationChange = (val: boolean) => {
    //写入存储
    store.$patch({settings: {echoCancellation: val}})
    //刷新连接
    refreshConnection()
  }
  /**
   * 当麦克风被改变
   * @param val 新麦克风
   */
  const onMicrophoneChange = (val: string) => {
    //写入存储
    store.$patch({settings: {mediaDevice: val}})
    //刷新连接
    refreshConnection()
  }
  /**
   * 加入语音
   * @param changeStatus 是否改变加入状态
   */
  const joinVoiceChat = (changeStatus: boolean = true) => {
    //初始化WebSocket连接
    webSocketConnection.value = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/voice");
    //绑定事件
    webSocketConnection.value.onmessage = handlerWebSocketMessage;
    webSocketConnection.value.onopen = handlerWebSocketConnect;
    webSocketConnection.value.onclose = handlerWebSocketClose;
    webSocketConnection.value.onerror = handlerWebSocketError;
    if(changeStatus){
      join.value = true;
    }
    ElMessage.success("加入成功")
  }
  /**
   * 退出语音
   * @param changeStatus 是否改变加入状态
   */
  const leaveVoiceChat = (changeStatus: boolean = true) => {
    //关闭WebSocket连接
    if (webSocketConnection.value) {
      webSocketConnection.value.close()
    }
    //关闭每一个用户的WebRTC连接
    userComponents.value.forEach((c) => {
      c.close();
    })
    if(changeStatus){
      join.value = false;
    }
    ElMessage.success("成功退出")
  }
  //注册当被挂载的事件
  onMounted(mount)
  //注册当被卸载的事件
  onUnmounted(unmounted)
  </script>
  
  <style scoped>
    .audios-div{
      height: 90%;
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
    }
    .main{
      display: flex;
      flex-direction: column;
      justify-content: center;
      width: 100%;
      height: 100%;
    }
    .control-panel{
      display: flex;
      justify-content: flex-start;
      width: 100%;
      height: 80px;
      border-width: 1px;
      border-style: solid;
      border-color: #409eff;
    }
    .control-panel-item{
      display: flex;
      flex-direction: row;
      width: 100%;
      color: var(--el-text-color-primary);
      font-size: 16px;
      margin-left: 15px;
    }
    .control-panel-item-text{
      margin-left: 10px;
      margin-top: 5px;
    }
    .control-panel-settings{
      display: flex;
      flex-direction: column;
      width: 20%;
      color: var(--el-text-color-primary);
      font-size: 16px;
      margin: 10px 0;
    }
    .control-panel-join{
      display: flex;
      width: 60%;
      justify-content: flex-end;
      margin-left: -25px;
    }
    .control-panel-join-button{
      transform: translate(0,60%);
    }
  </style>