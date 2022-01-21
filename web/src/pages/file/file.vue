<template>
  <el-container>
    <el-header style="padding:0px;">
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect" default-active="/main/file/download" router>
        <el-menu-item index="/main/file/download">文件下载</el-menu-item>
        <el-menu-item index="/main/file/upload">文件上传</el-menu-item>
      </el-menu>
    </el-header>
    <el-main>
      <router-view/>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "file",
  data() {
    return {
      activeIndex: '1',
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>

</style>
