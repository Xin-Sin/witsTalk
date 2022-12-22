<template>
    <el-container>
        <el-aside width="1030px">
          <el-table v-loading="loading" :data="files" stripe border style="width: 100%;height: auto" highlight-current-row @current-change="handleCurrentChange">
        <el-table-column :fixed="true" label="#" prop="id" show-overflow-tooltip width="70"/>
        <el-table-column label="名称" prop="name" show-overflow-tooltip width="200"/>
        <el-table-column label="大小" prop="size" show-overflow-tooltip width="100"/>
        <el-table-column label="上传时间" prop="uploadTime" show-overflow-tooltip width="120"/>
        <el-table-column label="上传者" prop="uploaderName" show-overflow-tooltip width="120"/>
        <el-table-column label="MD5" prop="md5" show-overflow-tooltip width="300"/>
        <el-table-column :fixed="'right'" width="140">
          <template #header>
            <el-button type="info" size="small" @click="reload">刷新</el-button>
            <el-button type="info" size="small" @click="dialogFileUploader = true">上传</el-button>
          </template>
          <template #default="scope">
            <el-button
                link
                size="small"
                type="primary"
                @click.prevent="deleteRow(scope.row.md5,scope.row.id)"
                v-if="auth === 'admin'"
            >
              删除
            </el-button>
            <el-button
                link
                size="small"
                type="primary"
                @click.prevent="downloadFile(scope.row.md5,scope.row.name)"
            >
              下载
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          :page-size="17"
          :layout="'prev, pager, next'"
          :total="pageCount"
          @current-change="changePage"
      />
        </el-aside>
        <el-main>
          <el-empty v-if="selectFile == null" description="当前未选择文件" />
          <div v-else>
            <el-tag style="float: left"><span>文件名称: </span>{{selectFile.name}} <span style="color: #71c346">大小: {{selectFile.size}}</span></el-tag>
            <el-button style="margin-left: 40px" type="success" round size="small" @click="downloadFile(selectFile.md5,selectFile.name)">文件下载</el-button>
            <el-tag style="float: right"><span>MD5校验值: </span>{{selectFile.md5}}</el-tag>
            <el-divider/>
            <el-image v-if="showImg !== '' && !showAudio" style="width: 750px; height: 670px" :src="showImg" fit="fill" />
            <el-input
            v-if="showImg == '' && !showAudio"
              v-model="textarea"
              :rows="32"
              type="textarea"
              placeholder="show data"
              disabled
            />
            <audio v-if="showAudio" controls height="100" width="750" style="width:750px">
              <source :src="showAudioSrc" type="audio/mp3">
            </audio>
          </div>
        </el-main>
      <el-dialog v-model="dialogFileUploader" title="文件上传窗口">
        <el-upload
            class="upload-demo"
            drag
            action="http://localhost:8080/file/api/fileUpload"
            multiple
            :headers="headers"
            :on-success="fileUploadSuccess"
            :before-upload="fileUploadBefore"
            :on-progress="fileUploadProgress"
        >
          <el-icon class="el-icon--upload"><upload-filled /></el-icon>
          <div class="el-upload__text">
            将文件拖到此处或者 <em>点击来上传</em>
          </div>
          <template #tip>
            <div class="el-upload__tip">
              不要上传超过20G的文件
            </div>
          </template>
        </el-upload>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFileUploader = false">关闭</el-button>
          </span>
        </template>
      </el-dialog>
      </el-container>
  </template>
  
  <script lang="ts" setup>
  import {onMounted, reactive, ref} from "vue";
  import {fileDelete, getFiles, getShowFile} from "../../api/file";
  import {useStore} from "../../store";
  import {ElMessage, UploadProgressEvent} from "element-plus";
  import {MessageHandler} from "element-plus/es/components/message/src/message";
  
  const files = ref([]);
  const pageCount = ref<number>(1)
  const pageSize = ref<number>(17)
  const auth = ref<string>();
  const selectFile = ref()
  const textarea = ref<string>()
  const showImg = ref<string>()
  const showAudioSrc = ref<string>()
  const showAudio = ref<boolean>(false)
  const loading = ref<boolean>(true)
  const page = ref<number>(0);
  const dialogFileUploader = ref<boolean>(false);
  const headers = reactive<Object>({"Access-Token":window.localStorage.getItem("token")})
  const messageHandler = ref<MessageHandler | null>(null);
  const messageHandler1 = ref<MessageHandler | null>(null);

  const store = useStore();

  // 文件上传时回调方法
  const fileUploadProgress = (evt: UploadProgressEvent) => {
    if (evt.percent === 100){
      messageHandler1.value! = ElMessage.warning({"message":"文件已交由后端进行处理,请耐心等待,","duration":0})
    }
    dialogFileUploader.value = true;
  }
  // 文件上传前回调方法
  // 不做文件相关处理，对用户进行响应提示
  const fileUploadBefore = () => {
     messageHandler.value! = ElMessage.warning({"message":"文件上传中,请不要关闭该窗口","duration":0})
  }
  // 文件上传成功回调方法
  const fileUploadSuccess = (response: any) => {
    if (response.status === 200){
      ElMessage.success("上传成功,文件MD5值为:" + response.data + ",即将为您关闭该窗口")
    }else{
      ElMessage.success(response.data)
    }
    messageHandler.value!.close();
    messageHandler1.value!.close();
    dialogFileUploader.value = false;
    setTimeout(getFile,100,page.value)
  }
  // 刷新页面
  const reload = () => {
    getFile(page.value);
  }
  // 创建文件url进行访问或者在线获取文件二进制
  const connectedFileUrl = (md5:string, fileName:string) =>{
    return "http://localhost:8080/file/api/downloadFile?md5=" + md5 + "&filename=" + fileName + "&token=" + window.localStorage.getItem("token");
  }
  // 在线展示文件内容
  const handleCurrentChange = (val: object | undefined) =>{
    showAudio.value = false;
    selectFile.value = val
    // 判断文件后缀，进行相对应的文件格式解析
    let a = selectFile.value?.name.split(".");
    if(a[a.length-1] === 'jpg' || a[a.length-1] === 'png'){
      showImg.value = connectedFileUrl(selectFile.value?.md5, selectFile.value?.name)
    }else if(a[a.length-1] === 'mp3' || a[a.length-1] === 'ogg' || a[a.length-1] === 'wav'){
      showAudioSrc.value = connectedFileUrl(selectFile.value?.md5, selectFile.value?.name)
      showAudio.value = true;
    }else{
      // 获取文件内容
      getShowFile(selectFile.value?.md5, selectFile.value?.name,localStorage.getItem("token") as string).then(res =>{
        showImg.value = ''
        if(res.data instanceof Object){
          textarea.value = JSON.stringify(res.data)
        }else{
          textarea.value = res.data;
        }
      }).catch(_ =>{
        ElMessage.error("获取文件时出现错误")
      })
    }
  }
  // 下载文件
  const downloadFile = (md5:string,name:string) =>{
    window.open(connectedFileUrl(md5,name),"_blank");
  }
  // 文件删除
  const deleteRow = (md5:string,id:number) =>{
    console.log(md5,id)
    fileDelete(md5,id).then(res =>{
      if (res.data.status === 200){
        ElMessage.success(res.data.data)
        getFile(page.value);
      }else{
        ElMessage.error(res.data.data)
      }
    })
  }
  // 获取文件列表
  const getFile = (id: number) =>{
    loading.value = true;
    getFiles(id,pageSize.value).then((response) => {
      pageCount.value = response.data.data.total;
      files.value = response.data.data.files
      loading.value = false;
    })
  }
  onMounted(() => {
    getFile(page.value);
    // 将用户权限赋值到本页面
    if (store.userinfo !== null){
      auth.value = store.userinfo.auth;
    }
  })
  // 分页钩子
  const changePage = (id: number) =>{
    page.value = (id - 1) * 17;
    getFile(pageSize.value);
  }
  </script>
  
  <style scoped>
  
  </style>