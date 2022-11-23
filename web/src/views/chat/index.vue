<template>
    <div ref="messageDiv" class="messages">
      <Message v-for="(item,index) in messageData" :key="index" :data="item"/>
    </div>
    <el-input
        v-model="sendContent"
        :disabled="!hasConnection"
        class="input-area"
        maxlength="500"
        placeholder=""
        rows="4"
        show-word-limit
        type="textarea"
    />
    <div class="button-div">
      <el-button :disabled="!hasConnection" class="send-button" plain type="warning" @click="sendMessage">发送</el-button>
    </div>
  </template>
  
  <script lang="ts" setup>
  import {nextTick, onMounted, onUnmounted, reactive, ref} from "vue";
  import {ElLoading, ElMessage} from "element-plus";
  import Message from "../../components/Message.vue";
  import {MessageData, MessageTypes} from "../../util/MessageData";
  import {LoadingInstance} from "element-plus/es/components/loading/src/loading";

  const ws = ref<WebSocket>();
  const sendContent = ref<string>();
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
  const websocketError = function (this: WebSocket, evt: Event): any {
    console.log(evt);
    ElMessage.error("与服务器沟通出现错误！");
  }
  const websocketClose = function (this: WebSocket, _: Event): any {
    if (window.location.hash === "#/main") {
      ElMessage.error("与服务器连接断开，尝试重连！");
      for (let i = 0; i < messageData.length; i++) {
        messageData.pop();
      }
      initWebsocket();
      if (!loadingInstance.value) {
        loadingInstance.value = ElLoading.service({"target": messageDiv.value})
      }
    }
    hasConnection.value = false;
  }
  const loadingInstance = ref<LoadingInstance | null>();
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
            if (loadingInstance.value) {
              loadingInstance.value.close();
              loadingInstance.value = null;
              document.addEventListener("keydown", (evt) => {
                if (evt.key === "Enter" && evt.ctrlKey) {
                  sendMessage()
                }
              });
            }
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
            if (op === undefined) {
              messageData.push(new MessageData(jsonData.id,
                  jsonData.content,
                  jsonData.sender,
                  jsonData.recall,
                  jsonData.sendtime,
                  jsonData.base64,
                  jsonData.type === "text" ? MessageTypes.text : MessageTypes.img
              ));
            }
          }
        } catch (e) {
          ElMessage.error("出现错误，请尝试刷新！");
          console.log(e);
        }
      }
    }
  }
  const sendMessage = function () {
    if (!sendContent.value) {
      ElMessage.error("请输入要发送的内容！");
      return;
    }
    if (!ws.value) {
      ElMessage.error("出现错误，与服务器未连接")
      return
    }
    const username = window.sessionStorage.getItem("username");
    if (username) {
      ws.value.send(JSON.stringify({
        "op": "send",
        "args": {"sender": username, "content": sendContent.value, "type": "text"}
      }))
      ElMessage.success("发送成功！")
      sendContent.value = "";
    }
  }
  const initWebsocket = function () {
    ws.value = new WebSocket("ws://wzpmc.cn:16384/chat");
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
  }
  onMounted(() => {
    loadingInstance.value = ElLoading.service({"target": messageDiv.value})
    initWebsocket();
  });
  onUnmounted(() => {
    ws.value?.close();
    window.clearTimeout(pongId.value);
  })
  </script>
  
  <style scoped>
  div.messages {
    background: rgba(245, 245, 245);
    width: 100%;
    height: 80%;
    border: 2px dashed var(--el-border-color);
    border-radius: 20px;
    box-shadow: var(--el-box-shadow-lighter);
    display: flex;
    flex-direction: column;
    overflow: hidden;
    overflow-y: scroll;
  }
  
  .input-area {
    margin-top: 20px;
  }
  
  .send-button {
    margin-top: 10px;
  }
  
  .button-div {
    display: flex;
    flex-direction: row-reverse;
  }
  </style>