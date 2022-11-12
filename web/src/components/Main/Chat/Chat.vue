<template>
  <div class="messages">
    <Message v-for="item in messagesData" v-if="messagesData" :data="item"></Message>
  </div>
</template>

<script lang="ts" setup>
import {onMounted, onUnmounted, ref} from "vue";
import {ElMessage} from "element-plus";
import Message from "./Message.vue";
import {MessageData} from "./MessageData";

const ws = ref<WebSocket>();
const waitingPong = function () {
  ElMessage.error("当前网络质量不佳，请更换网络后重试！")
  ws.value?.close();
}
const pongId = ref<number>();
const hasConnection = ref<boolean>(false);
let messagesData: Array<typeof MessageData> = [];
const heartBeat = function () {
  ws.value?.send(JSON.stringify({"op": "heartCheck"}));
  pongId.value = window.setTimeout(waitingPong, 1000 * 50);
}
const websocketOpen = function (this: WebSocket, event: Event): any {
  this.send(JSON.stringify({"op": "login", "args": {"token": window.sessionStorage.getItem("token")}}));
}
const websocketError = function (this: WebSocket, event: Event): any {
  ElMessage.error("与服务器沟通出现错误！");
}
const websocketClose = function (this: WebSocket, event: Event): any {
  ElMessage.error("与服务器连接断开！");
}
const websocketMessage = function (this: WebSocket, event: MessageEvent): any {
  let data: string | undefined = event.data;
  if (data === undefined) {
  } else if (data === "true") {
    heartBeat();
    ElMessage.success("成功连接聊天服务器！");
  } else if (data === "false") {
    ElMessage.error("登录失败，请尝试重新登录！");
    window.sessionStorage.removeItem("username");
    window.sessionStorage.removeItem("headimg");
    window.sessionStorage.removeItem("token");
    window.location.hash = "/"
  } else if (!isNaN(Number(data))) {
    let count = Number.parseInt(data);
    if (count >= 10) {
      ws.value?.send(JSON.stringify({"op": "get", "args": {"min": count - 10, "count": 10}}))
    } else {
      ws.value?.send(JSON.stringify({"op": "get", "args": {"min": 0, "count": 10}}));
    }
  } else {
    if (data.indexOf("connected") != -1) {
      console.log(data)
    } else {
      try {
        let jsonData = JSON.parse(data);
        if (Array.isArray(jsonData)) {
          messagesData = jsonData;
          console.log(messagesData);
        } else {
          let op = jsonData.op;
          if (op === "pong") {
            if (!hasConnection.value) {
              hasConnection.value = true;
              ws.value?.send(JSON.stringify({"op": "count"}));
            }
            window.clearTimeout(pongId.value);
            window.setTimeout(heartBeat, 1000 * 30);
          }
        }
      } catch (e) {
        ElMessage.error("出现错误，请尝试刷新！");
      }
    }
  }
}
onMounted(() => {
  ws.value = new WebSocket("ws://127.0.0.1:8080/chat");
  ws.value.onopen = websocketOpen;
  ws.value.onerror = websocketError;
  ws.value.onclose = websocketClose;
  ws.value.onmessage = websocketMessage;
});
onUnmounted(() => {
  ws.value?.close();
  window.clearTimeout(pongId.value);
})
</script>

<style scoped>
div.messages {
  background: rgba(72, 70, 70, 0.2);
  width: 90%;
  height: 80%;
  border: 2px dashed var(--el-border-color);
  border-radius: 20px;
  box-shadow: var(--el-box-shadow-lighter);
}
</style>