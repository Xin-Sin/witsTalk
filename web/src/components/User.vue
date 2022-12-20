<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span v-if="volume===0" style="color: red">{{prop.username}}</span>
        <span v-else>{{prop.username}}</span>
        <el-tooltip
            v-if="volume===0"
            content="本地将此用户静音"
            placement="top"
        >
          <Mute style="width: 16px;height: 16px;margin-left: 10px" />
        </el-tooltip>
      </div>
    </template>
    <div class="control-panel">
      <div class="control-panel-item">
        <div class="control-panel-item-name">音量</div>
        <div class="control-panel-item-element">
          <el-slider v-model="volume" @input="onVolumeInput" />
        </div>
      </div>

    </div>
  </el-card>
  <audio ref="audio" autoplay style="display: none" />
</template>

<script lang="ts" setup>
import {Mute} from '@element-plus/icons-vue'
import {onMounted, ref} from "vue";
import {IceServer} from "../config/GlobalConfig";
import {ElMessage} from "element-plus";

//外部参数
const prop = defineProps<{mediaStream: MediaStream, username: string, webSocket: WebSocket}>();
//WebRTC连接
let connection: RTCPeerConnection;
//audio标签
const audio = ref<HTMLAudioElement>();
//音量数值
const volume = ref<number>(100);
/**
 * 当音量条被改变
 * @param val 新数值
 */
const onVolumeInput = (val: number) => {
  //设置audio标签的音量为val的1/100
  audio.value!.volume = val / 100;
}
/**
 * 处理信令服务器（其他用户）发送的SDP数据
 * @param offer SDP数据
 */
const handlerOffer = (offer: RTCSessionDescriptionInit) => {
  // console.log(prop.username + "-handlerOffer")
  //设置远端可用解码器
  connection.setRemoteDescription(new RTCSessionDescription(offer));
  //创建Answer（我方的SDP）数据
  connection.createAnswer().then((answer) => {
    //设置本地SDP为answer
    connection.setLocalDescription(answer);
    //发送（数据解释：op为操作，此处为answer，data为数据，即要发送的answer数据，to为发送到，此处为发送到的用户的用户名）
    prop.webSocket.send(JSON.stringify({"op": "answer", "data": answer, "to": prop.username}));
  }).catch((err) => ElMessage.error("出现错误" + err))
}
/**
 * 处理信令服务器（其他用户）发送的Answer数据
 * @param answer Answer数据
 */
const handlerAnswer = (answer: RTCSessionDescriptionInit) => {
  //console.log(prop.username + "-handlerAnswer")
  //设置远端SDP数据为接受的
  connection.setRemoteDescription(new RTCSessionDescription(answer));
}
/**
 * 处理信令服务器网络交流事件
 * @param candidate Candidate数据
 */
const handlerCandidate = (candidate: RTCIceCandidate) => {
  //console.log(prop.username + "-handlerCandidate")
  //将远端的Candidate数据加入本地网络沟通方式的备选项中，并进行筛选，选出能使用（互相通信）的通道
  connection.addIceCandidate(new RTCIceCandidate(candidate));
}
/**
 * 处理Candidate数据选择事件
 * @param event 事件
 */
const handlerIceServerIceCandidate = (event: RTCPeerConnectionIceEvent) => {
  //将定好的沟通线路发送给远端
  prop.webSocket.send(JSON.stringify({"op": "candidate", "data": event.candidate, "to": prop.username}));
};
/**
 * 处理来自远端的Track（轨道，可为音频或视频轨道，还可为屏幕共享轨道）事件
 * @param evt 事件
 */
const handlerIceServerTrace = (evt: RTCTrackEvent) => {
  //添加audio元素至div
  audio.value!.srcObject = evt.streams[0];
}
const mount = () => {
  connection = new RTCPeerConnection({"iceServers": [IceServer]});
  for (let track of prop.mediaStream.getTracks()) {
    connection.addTrack(track, prop.mediaStream)
  }
  //初始化STUN服务器
  connection.ontrack = handlerIceServerTrace;
  connection.onicecandidate = handlerIceServerIceCandidate;
  //创建Offer并广播
}
/**
 * 创建本地SDP并发送（启动WebRTC连接）
 * WebRTC的沟通流程大概如下图所示：
 * 客户端A ---Offer---> 信令服务器
 * 客户端B <---Offer--- 信令服务器
 * 客户端B ---Answer---> 信令服务器
 * 客户端A <---Answer--- 信令服务器
 * 客户端A ---Candidate（A）---> 信令服务器
 * 客户端B ---Candidate（B）---> 信令服务器
 * 客户端B <---Candidate（A）--- 信令服务器
 * 客户端B ---RTC---> TURN服务器
 * 客户端A ---RTC---> TURN服务器
 * 客户端A <---（TURN服务器）P2P（RTC）---> 客户端B（若两客户端无法直接交流）
 * 客户端A <---P2P（RTC）---> 客户端B（若两客户端可以直接交流）
 */
const createOffer = () => {
  //console.log("createOffer-" + prop.username);
  //创建SDP数据
  connection.createOffer()
      .then((offer) => {
        //发送SDP数据
        prop.webSocket.send(JSON.stringify({"op": "offer", "data": offer, "to": prop.username}));
        //设置本地的SDP数据
        connection.setLocalDescription(offer);
      }).catch((err) => console.log(err));
}
/**
 * 获取此用户的用户名
 */
const getName = function () {
  return prop.username;
}
/**
 * 关闭WebRTC服务器的连接
 */
const close = () => {
  //关闭连接
  connection.close();
}
//设置挂载事件
onMounted(mount)
//将以下方法暴露：handlerAnswer，handlerCandidate，handlerOffer，getName，createOffer，close
defineExpose({handlerAnswer, handlerCandidate, handlerOffer, getName, createOffer, close})
</script>

<style scoped>
.control-panel{
  width: 100%;
}
.control-panel-item{
  display: flex;
}
.control-panel-item-name{
  width: max-content;
  margin-top: 3px;
}
.control-panel-item-element{
  width: 70%;
  margin-left: 20px;
}
</style>