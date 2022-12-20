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

const prop = defineProps<{mediaStream: MediaStream, username: string, webSocket: WebSocket}>();
import {IceServer} from "../config/GlobalConfig";
import {ElMessage} from "element-plus";
let connection: RTCPeerConnection;
const audio = ref<HTMLAudioElement>();
const volume = ref<number>(100);
const onVolumeInput = function (val: number) {
  if (audio.value) {
    audio.value.volume = val / 100;
  }
}
/**
 * 处理Offer事件
 * @param offer 数据
 */
const handlerOffer = function (offer: RTCSessionDescriptionInit) {
  // console.log(prop.username + "-handlerOffer")
  //设置远端描述为传过来的描述
  connection.setRemoteDescription(new RTCSessionDescription(offer));
  //创建应答数据并广播
  connection.createAnswer().then((answer) => {
    //设置本地描述
    connection.setLocalDescription(answer);
    prop.webSocket.send(JSON.stringify({"op": "answer", "data": answer, "to": prop.username}));
  }).catch((err) => ElMessage.error("出现错误" + err))
}
/**
 * 处理远端应答事件
 * @param answer 数据
 */
const handlerAnswer = function (answer: RTCSessionDescriptionInit) {
  //console.log(prop.username + "-handlerAnswer")
  //设置远端描述为发过来的
  connection.setRemoteDescription(new RTCSessionDescription(answer));
}
/**
 * 处理远端STUN服务器选择事件
 * @param candidate 数据
 */
const handlerCandidate = function (candidate: RTCIceCandidate) {
  //console.log(prop.username + "-handlerCandidate")
  //将远端的STUN服务器选项加入备选名单中
  connection.addIceCandidate(new RTCIceCandidate(candidate));
}
/**
 * 处理STUN服务器选择事件
 * @param event 事件
 */
const handlerIceServerIceCandidate = function (event: RTCPeerConnectionIceEvent) {
  if (event.candidate) {
    //console.log("sendCandidate")
    //广播STUN服务器
    prop.webSocket.send(JSON.stringify({"op": "candidate", "data": event.candidate, "to": prop.username}));
  }
};
const handlerIceServerTrace = function (evt: RTCTrackEvent) {
  //获取远端音频流
  let audioStream = evt.streams[0];
  //添加audio元素至div
  if (audio.value) {
    audio.value.srcObject = audioStream;
  }

}
onMounted(() => {
  connection = new RTCPeerConnection({"iceServers": [IceServer]});
  for (let track of prop.mediaStream.getTracks()) {
    connection.addTrack(track, prop.mediaStream)
  }
  //初始化STUN服务器
  connection.ontrack = handlerIceServerTrace;
  connection.onicecandidate = handlerIceServerIceCandidate;
  //创建Offer并广播
})
const createOffer = function(){
  //console.log("createOffer-" + prop.username);
  connection.createOffer()
      .then((offer) => {
        prop.webSocket.send(JSON.stringify({"op": "offer", "data": offer, "to": prop.username}));
        connection.setLocalDescription(offer);
      }).catch((err) => console.log(err));
}
const getName = function () {
  return prop.username;
}
const close = () => {
  connection.close();
}
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