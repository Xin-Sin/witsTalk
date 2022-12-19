<template>
  <audio ref="audio" autoplay style="display: none" />
</template>

<script lang="ts" setup>
import {onMounted, ref} from "vue";

const prop = defineProps<{mediaStream: MediaStream, username: string, webSocket: WebSocket}>();
import {IceServer} from "../config/GlobalConfig";
import {ElMessage} from "element-plus";
let connection: RTCPeerConnection = new RTCPeerConnection({"iceServers": [IceServer]});
const audio = ref<HTMLAudioElement>();
const remoteStream = new MediaStream();
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
    prop.webSocket.send(JSON.stringify({"op": "answer", "data": answer}));
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
 * 处理STUN服务器选择事件
 * @param event 事件
 */
const handlerIceServerIceCandidate = function (event: RTCPeerConnectionIceEvent) {
  if (event.candidate) {
    //广播STUN服务器
    prop.webSocket.send(JSON.stringify({"op": "candidate", "data": event.candidate}));
  }
};
/**
 * 处理RTCTrackEvent
 * @param evt 事件
 */
const handlerIceServerTrace = function (evt: RTCTrackEvent) {
  //获取远端音频流
  let audioStream = evt.streams[0];
  //添加audio元素至div
  if (audio.value) {
    audio.value.srcObject = audioStream;
  }
}
onMounted(() => {
  for (let track of prop.mediaStream.getTracks()) {
    connection.addTrack(track, prop.mediaStream)
  }
  //初始化STUN服务器
  connection.ontrack = handlerIceServerTrace;
  connection.onicecandidate = handlerIceServerIceCandidate;
  //创建Offer并广播
  connection.createOffer()
      .then((offer) => {
        prop.webSocket.send(JSON.stringify({"op": "offer", "data": offer}));
        connection.setLocalDescription(offer);
      }).catch((err) => ElMessage.error("出现错误！" + err));
})
</script>

<style scoped>

</style>