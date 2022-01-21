<template>
  <el-container>
    <el-aside width="20%">
        <el-tag type="info" style="width: 30%; font-size: 20px">文件目录</el-tag>
        <el-skeleton/>
    </el-aside>
    <el-container>
      <el-header>
        <el-tag style="width: 20%;"></el-tag>
        <div class="button_right">
          <el-button type="primary" plain>下载</el-button>
          <el-button type="primary" plain>分享</el-button>
        </div>
      </el-header>
      <el-main>

      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "download"
}
</script>

<style scoped>

.button_right{
  float: right;
}
</style>
