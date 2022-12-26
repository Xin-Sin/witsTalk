<template>
  <div class="message">
    <div :class="div_class">
      <el-avatar :size="52" :src='"data:image/png;base64," + props.data.base64' style="margin-top: 15px"/>
      <div :class="in_div_class">
        <div>
          <el-tag v-if="show" :type="tag_type">{{ props.data.sender }}</el-tag>
          <el-tag v-if="show" :type="tag_type">发送时间 : {{ props.data.sendtime }}</el-tag>
        </div>
        <div :style="message_box_style"><img v-if="self" alt="" class="angle" src="src/assets/95ec69.svg"/>
          <img v-else alt="" class="angle" src="src/assets/ffffff.svg">
          <div :class="message_div">
            <el-popover :visible="visible" placement="top" :width="160">
              <el-link type="info" @click="recall">撤回</el-link>
              <template #reference>
                <div style="border: 5px solid rgba(255,255,255,0%);" @click="visibleMethod(props.data.sender)">{{ props.data.content }}</div>
              </template>
            </el-popover>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {isSelf, MessageData} from "../entities/MessageData";
import {ref} from "vue";
import {useStore} from "../store";

const store = useStore();

const visibleMethod = (sender:string) =>{
  let name = null;
  if (store.userinfo != null){
    name = store.userinfo.username;
  }
  if (name === sender){
    visible.value = visible.value != true;
  }
}
const visible = ref(false)
const props = defineProps<{data: MessageData}>()
const emit = defineEmits(["remove"])
const tag_type = ref<string>("success");
const div_class = ref<string>("message-out-div");
const in_div_class = ref<string>("message-in-div");
const message_div = ref<string>("message-div");
const message_box_style = ref<string>("display:flex; flex-direction: row;");
const show = ref<boolean>(false);
let self = isSelf(props.data);
if (self) {
  tag_type.value = "danger"
  div_class.value = "message-out-div self";
  in_div_class.value = "message-in-div self-in";
  message_div.value = "message-div message-self"
  message_box_style.value = "display:flex; flex-direction: row-reverse;"
}
show.value = true;
const recall = () => {
  emit("remove", props.data.id);
  visible.value = false;
}
</script>

<style scoped>
div.message-out-div {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  margin-top: 20px;
  margin-left: 5px;
  align-items: flex-start;
  width: 100%;
}

div.message-in-div {
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
  margin-left: 7px;
  align-items: flex-start;
  width: 90%;
}

div.self {
  flex-direction: row-reverse;
  margin-left: 0;
  margin-right: 7px;
}

div.self-in {
  flex-direction: column;
  margin-left: 0;
  margin-right: 5px;
  align-items: flex-end;
}

div.message-div {
  margin-top: 10px;
  font-size: 2vh;
  border-radius: 0 4px 4px 4px;
  background-color: #FFFFFF;
  word-wrap: break-word;
  word-break: break-all;
  overflow: hidden;
}

div.message-self {
  display: flex;
  flex-direction: row-reverse;
  border-radius: 4px 0 4px 4px;
  background-color: #71c346;
  word-wrap: break-word;
  word-break: break-all;
  overflow: hidden;
}

img.angle {
  width: 16px;
  height: 16px;
  margin-top: 10px;
}
.message {
  margin-bottom: 10px;
}
</style>