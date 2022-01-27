<template>
  <el-container>
    <el-aside width="50%">
      <el-table height="800px" :data="fileDirectory" highlight-current-row @current-change="handleCurrentChange" border style="width: 100%" stripe :default-sort = "{prop: 'size', order: 'descending'}" v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">
        <el-table-column prop="name" sortable label="文件名" width="392%"> </el-table-column>
        <el-table-column prop="size" sortable label="大小" width="150%"> </el-table-column>
        <el-table-column prop="uploadTime" sortable label="上传时间" width="150%"> </el-table-column>
        <el-table-column prop="uploadName" sortable label="上传者" width="150%"> </el-table-column>
        <el-table-column prop="md5" label="检验值" width="400%"></el-table-column>
      </el-table>
    </el-aside>
    <el-container>
      <el-header>
        <span style="width: 20%;" ref="fileName" class="el-tag el-tag--light">您还未选择文件哦!!</span>
        <div class="button_right">
          <el-button type="primary" plain @click="download_file_button">下载</el-button>
        </div>
      </el-header>
      <el-main >
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import {getAllFileNames} from '@/components/axios/request';
import {download} from '@/components/axios/request';
export default {
  name: "download",
  data(){
    return{
      fileDirectory:[],
      loading: true,
      currentRow: null,
      file_md5:null,
      file_name:null,
      baseURL:"http://localhost:8080/file",
      token:window.localStorage.getItem("token"),
    }
  },
  mounted() {
    getAllFileNames().then(res =>{
      let data = [];
      for (let i = 0; i < res.data["data"].length; i++) {
        let mapp = {
          "name":res.data["data"][i].name,
          "size":res.data["data"][i].size /1000,
          "uploadName":res.data["data"][i].uploadName,
          "uploadTime":res.data["data"][i].uploadTime,
          "md5":res.data["data"][i].md5
        };
        data.push(mapp);
      }
      this.loading = false;
      this.fileDirectory = data;
      data = null;
    })
  },
  methods:{
    download(md5,filename){
      let url = this.baseURL +"/api/downloadFile?md5="+md5+"&filename="+filename + "&token=" + this.token;
      window.open(url,"_blank")
    },
    handleCurrentChange(val) {
      this.currentRow = val;
      this.$refs.fileName.innerText = val["name"];
      this.file_name = val["name"];
      this.file_md5 = val["md5"];
    },
    download_file_button(){
      if (this.file_name === null & this.file_md5 === null){
        this.showError("对不起,您还没选择文件");
        return;
      }
      this.download(this.file_md5,this.file_name)
    },
    showError(err) {
      this.$message.error(err);
    },
  }
}
</script>

<style scoped>
.button_right{
  float: right;
}
</style>
