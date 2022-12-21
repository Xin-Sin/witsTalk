<template>
  <div ref="messageDiv" class="messages">
    <Message v-for="(item,index) in messages" :key="index" :data="item" @remove="removeMessage"/>
    <!--div class="new-message-counter">
      TODO 新消息提醒
    </div-->
  </div>
  <el-input
      v-model="content"
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
  import {h, nextTick, onMounted, onUnmounted, ref} from "vue"
  import {ElMessage, ElNotification} from "element-plus"
  import {MessageData, MessageTypes} from "../../entities/MessageData"
  import {useStore} from "../../store";

  const messageDiv = ref<HTMLDivElement>();
  const messages = ref<Array<MessageData>>([])
  const webSocketConnection = ref<WebSocket>()
  const content = ref<string>("");
  const hasConnection = ref<boolean>(true);
  const idMin = ref<number>(0);
  const idMax = ref<number>(0);
  const isGetting = ref<boolean>(false);
  const handlerId = ref<number>();
  const store = useStore();
  let temp = {};
  const sendData = (operator: string, data: any = {}) => {
    data.op = operator
    if(webSocketConnection.value){
      webSocketConnection.value!.send(JSON.stringify(data))
    }
  }
  const handlerWebSocketConnectionOpen = (_: Event) => {
    sendData("login", {"token": window.localStorage.getItem("token")})
  }
  const handlerWebSocketConnectionClose = (event: CloseEvent) => {
    if (event.code !== 1005){
      ElMessage.error("与服务器断开连接")
    }
    messages.value = [];
    hasConnection.value = false;
  }
  const handlerWebSocketConnectionError = (_: Event) => {
    ElMessage.error("与服务器沟通出现错误，请刷新重试！")
  }
  const removeMessage = (id: number) => {
    sendData("remove", {"id": id})
    const messages_temp = messages.value.filter((data) => data.id != id)
    messages.value = []
    nextTick(() => {
      messages.value = messages_temp
    })
  }
  const handlerWebSocketConnectionMessage = (event: MessageEvent<string>) => {
    const object = JSON.parse(event.data);
    let operator: string = object.op
    const firstLetter = operator.at(0);
    operator = operator.replace(firstLetter!,firstLetter!.toUpperCase())
    let exec = operator + "Callback(";
    temp = object.data
    if(object.data instanceof Array){
      exec += "temp"
    }else if(object.data instanceof Object){
      for (let key in object.data) {
        exec += "temp['" + key + "'],";
      }
    }else{
      exec += "temp"
    }
    exec += ")"
    eval(exec);
  }
  const LoginCallback = (success: boolean) => {
    if(!success) {
      ElMessage.error("登陆失败，请重新登陆！")
    }else{
      sendData("count")
      sendData("ping")
    }
  }
  const GetCallback = (message: Array<MessageData>) => {
    messages.value = message
    messages.value.sort((a, b) => a.id! - b.id!)
    idMax.value = messages.value[0].id! - 1;
    idMin.value = 0;
    if(idMax.value >= 10){
      idMin.value = idMax.value - 10;
    }
    nextTick(() => {
      messageDiv.value!.scrollTop = messageDiv.value!.scrollWidth
    })
  }
  const CountCallback = (count: number) => {
    let idSent = 1;
    if(count > 10){
      idSent = count - 10
    }
    sendData("get", {"minId": idSent, "count": 10})
  }

  const sendMessage = () => {
    if(content.value.replaceAll(" ","").replaceAll("\n","") === ""){
      ElMessage.error("消息不能为空！")
      return;
    }
    sendData("send",{
      content: content.value,
      sender: store.userinfo!.username,
      type: MessageTypes.text})
    content.value = ""
  }
  const closeConnection = () => {
    ElMessage.error("延迟过高，请刷新重试，连接已断开！")
    webSocketConnection.value!.close()
  }
  const PongCallback = (time: number) => {
    if(handlerId.value){
      window.clearTimeout(handlerId.value!);
    }
    const date = new Date();
    if (date.getTime() - time > 10 * 1000) {
      ElMessage.error("延迟过高，请刷新重试，连接已断开！")
      webSocketConnection.value?.close();
      return;
    }
    handlerId.value = setTimeout(closeConnection,50 * 1000)
    setTimeout(sendData, 30 * 1000, "ping")
  }
  const SendCallback = (message: MessageData) => {
    messages.value.push(message);
    nextTick(() => {
      messageDiv.value!.scrollTop = messageDiv.value!.scrollWidth
    })
  }
  const RecallCallback = (id: number) => {
    const messages_temp = messages.value.filter((data) => data.id != id)
    messages.value = []
    nextTick(() => {
      messages.value = messages_temp
    })
  }
  const AddCallback = (message: MessageData) => {
    messages.value.push(message);
    let message_content = message.content!;
    if(message_content.length >= 99){
      message_content += message_content.substring(0,98) + "...";
    }
    ElNotification({
      title: message.sender,
      message: h('i', {}, message_content),
    })
  }
  const messageDivOnScroll = (_: Event) => {
    const scrollTop = messageDiv.value!.scrollTop;
    if(scrollTop <= 50){
      if(!isGetting.value){
        sendData("getNew", {"minId": idMin.value,"maxId": idMax.value, "count": 10})
        isGetting.value = true;
      }
    }
  }
  const GetNewCallback = (data: Array<MessageData>) => {
    isGetting.value = false;
    for (let message of messages.value) {
      data.push(message);
    }
    messages.value = []
    const now_max = messageDiv.value!.scrollHeight;
    nextTick(() => {
      messages.value = data
      messages.value.sort((a, b) => a.id! - b.id!)
      idMax.value = messages.value[0].id! - 1;
      idMin.value = 0;
      if(idMax.value >= 10){
        idMin.value = idMax.value - 10;
      }
      nextTick(() => {
        messageDiv.value!.scrollTop += messageDiv.value!.scrollHeight - now_max;
      })
    })
  }
  const mount = () => {
    webSocketConnection.value = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/chat")
    webSocketConnection.value.onopen = handlerWebSocketConnectionOpen
    webSocketConnection.value.onclose = handlerWebSocketConnectionClose
    webSocketConnection.value.onerror = handlerWebSocketConnectionError
    webSocketConnection.value.onmessage = handlerWebSocketConnectionMessage
    messageDiv.value!.onscroll = messageDivOnScroll
    window.addEventListener("keypress", (evt) => {if(evt.key === "Enter" && evt.altKey){sendMessage();}})
  }
  const unmount = () => {
    webSocketConnection.value!.close();
  }
  onMounted(mount)
  onUnmounted(unmount)
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
    float: left;
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
  .new-message-counter {
    float: right;
    background: #0061AB;
    width: 64px;
    height: 64px;
    z-index: 9999;
  }
</style>