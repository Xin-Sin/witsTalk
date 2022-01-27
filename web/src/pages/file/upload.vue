<template>
  <el-container>
    <el-header height="10%">
      <el-upload class="upload" drag action="http://localhost:8004/api/fileUpload" multiple ref="uploadRef">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">不支持上传文件夹,且不超过1G</div>
      </el-upload>
    </el-header>
  </el-container>
</template>

<script>
export default {
  name: "upload",
  data(){
    return{
      token:window.localStorage.getItem("token")
    }
  },
  methods:{

  },
  created(){
    this.$refs.uploadRef
  },
}
</script>

<style scoped>
.upload{
  align-items: center;
  align-content: center;
  text-align: center;
  align-self: center;
}
</style>
