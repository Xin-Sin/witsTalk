<template>
  <div :class="div_class">
    <el-avatar :size="52" :src='"data:image/png;base64," + props.data.base64' style="margin-top: 15px"/>
    <div :class="in_div_class">
      <el-tag v-if="show" :type="tag_type">{{ props.data.sender }}</el-tag>
      <div :style="message_box_style"><img v-if="isSelf" alt="" class="angle" src="src/assets/95ec69.svg"/><img v-else
                                                                                                                alt=""
                                                                                                                class="angle"
                                                                                                                src="src/assets/ffffff.svg">
        <div :class="message_div">
          <div style="border: 5px solid rgba(255,255,255,0%);">{{ props.data.content }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {MessageData} from "./MessageData";
import {ref} from "vue";

const props = defineProps({data: MessageData})
const tag_type = ref<string>("success");
const div_class = ref<string>("message-out-div");
const in_div_class = ref<string>("message-in-div");
const message_div = ref<string>("message-div");
const message_box_style = ref<string>("display:flex; flex-direction: row;");
const show = ref<boolean>(false);
let isSelf = props.data?.isSelf()
if (isSelf) {
  tag_type.value = "danger"
  div_class.value = "message-out-div self";
  in_div_class.value = "message-in-div self-in";
  message_div.value = "message-div message-self"
  message_box_style.value = "display:flex; flex-direction: row-reverse;"
}
show.value = true;
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
}

div.message-self {
  display: flex;
  flex-direction: row-reverse;
  border-radius: 4px 0 4px 4px;
  background-color: #71c346;
}

img.angle {
  width: 16px;
  height: 16px;
  margin-top: 10px;
}
</style>