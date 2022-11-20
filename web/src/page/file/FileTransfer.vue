<template>
  <div style="width: 57%">
    <el-table :data="files" style="width: 100%;height: auto">
      <el-table-column :fixed="true" label="#" prop="id" show-overflow-tooltip width="70"/>
      <el-table-column label="名称" prop="name" show-overflow-tooltip width="200"/>
      <el-table-column label="大小" prop="size" show-overflow-tooltip width="100"/>
      <el-table-column label="上传时间" prop="uploadTime" show-overflow-tooltip width="120"/>
      <el-table-column label="上传者" prop="uploaderName" show-overflow-tooltip width="120"/>
      <el-table-column label="MD5" prop="md5" show-overflow-tooltip width="300"/>
      <el-table-column :fixed="'right'" label="操作" width="120">
        <template #default="scope">
          <el-button
              link
              size="small"
              type="primary"
              @click.prevent="deleteRow(scope.$index)"
          >
            Remove
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        :layout="'prev, pager, next'"
        :total="pageCount"
        @current-change="changePage"
    />
  </div>
</template>

<script lang="ts" setup>
import {onMounted, ref} from "vue";
import {getFiles} from "../../components/api/requester";

const files = ref([]);
const pageCount = ref<number>(1)
const getFile = function (id: number) {
  getFiles(id).then((response) => {
    pageCount.value = response.data.data.total;
    files.value = response.data.data.files
  })
}
onMounted(() => {
  getFile(0);
})
const changePage = function (id: number) {
  getFile((id - 1) * 10);
}
</script>

<style scoped>

</style>