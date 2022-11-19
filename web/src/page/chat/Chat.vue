<template>
  <div ref="messageDiv" class="messages">
    <Message v-for="(item,index) in messageData" :key="index" :data="item"></Message>
  </div>
</template>

<script lang="ts" setup>
import {nextTick, onMounted, onUnmounted, reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import Message from "./Message.vue";
import {MessageData, MessageTypes} from "./MessageData";

const ws = ref<WebSocket>();
const waitingPong = function () {
  if (!ws.value?.CLOSED) {
    ElMessage.error("当前网络质量不佳，请更换网络后重试！")
    ws.value?.close();
  }
}
const pongId = ref<number>();
const hasConnection = ref<boolean>(false);
const messageDiv = ref<HTMLDivElement>();
let messageData: Array<MessageData> = reactive([]);
const lastId = ref<number>(0);
const heartBeat = function () {
  ws.value?.send(JSON.stringify({"op": "heartCheck"}));
  pongId.value = window.setTimeout(waitingPong, 1000 * 50);
}
const websocketOpen = function (this: WebSocket, _: Event): any {
  this.send(JSON.stringify({"op": "login", "args": {"token": window.sessionStorage.getItem("token")}}));
}
const websocketError = function (this: WebSocket, _: Event): any {
  ElMessage.error("与服务器沟通出现错误！");
}
const websocketClose = function (this: WebSocket, _: Event): any {
  if (window.location.hash === "#/main") {
    ElMessage.error("与服务器连接断开！");
  }
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
    count >= 10 ? lastId.value = count - 10 : lastId.value = 0;
    ws.value?.send(JSON.stringify({"op": "get", "args": {"min": lastId.value, "count": 10}}))
  } else {
    if (data.indexOf("connected") != -1) {
    } else {
      try {
        let jsonData = JSON.parse(data);
        if (Array.isArray(jsonData)) {
          jsonData = jsonData.sort((a, b) => {
            return a.id !== undefined && b.id !== undefined ? a.id - b.id : 0
          });
          for (let i = 0; i < 10; i++) {
            messageData.pop()
          }
          for (let jsonDataKey of jsonData) {
            messageData.push(new MessageData(jsonDataKey.id,
                jsonDataKey.content,
                jsonDataKey.sender,
                jsonDataKey.recall,
                jsonDataKey.sendtime,
                jsonDataKey.base64,
                jsonDataKey.type === "text" ? MessageTypes.text : MessageTypes.img
            ));
          }
          nextTick(() => {
            if (messageDiv.value) {
              messageDiv.value.scrollTop = messageDiv.value.scrollHeight
            }
          })
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
  if (messageDiv.value) {
    messageDiv.value.onscroll = function (_: Event) {
      if (messageDiv.value?.scrollTop === 0) {
        if (lastId.value > 0) {
          if (lastId.value >= 10) {
            lastId.value -= 10;
          }
          lastId.value = 0;
        }

        ws.value?.send(JSON.stringify({"op": "get", "args": {"min": lastId.value, "count": 10}}))
      }
    }
  }
});
onUnmounted(() => {
  ws.value?.close();
  window.clearTimeout(pongId.value);
})
</script>

<style scoped>
div.messages {
  background: rgba(245, 245, 245);
  width: 90%;
  height: 80%;
  border: 2px dashed var(--el-border-color);
  border-radius: 20px;
  box-shadow: var(--el-box-shadow-lighter);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  overflow-y: scroll;
}
</style>