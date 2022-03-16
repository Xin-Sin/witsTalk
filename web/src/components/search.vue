<template>
  <el-input placeholder="请输入搜索内容" v-model="searchChatRecorded" class="input-with-select" @change="search()">
    <el-button slot="append" icon="el-icon-search"></el-button>
  </el-input>
</template>

<script>
import {SEARCH} from "@/mixin/mixin";
export default {
  name: "search",
  // props:['searchChatRecorded'],
  data(){
    return{
      searchChatRecorded:'',
    }
  },
  mixins:[SEARCH]
}
</script>

<style scoped>

</style>
