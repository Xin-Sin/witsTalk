<template>
  <el-container>
    <el-header>
      <el-button
          type="primary"
          round
          @click="dialogFormAddRouteVisible = true;
          addRouteBefore()"
      >添加新路由</el-button>
    </el-header>
    <el-main>
      <el-table :data="tableData" stripe border style="width: 100%">
        <el-table-column :fixed="true" prop="id" label="#" width="50px"/>
        <el-table-column prop="path" label="路由路径" width="150px"/>
        <el-table-column prop="name" label="路由名称" width="100px"/>
        <el-table-column prop="parentId" label="父组件名称" width="100px"/>
        <el-table-column prop="component" label="组件路径" width="250px"/>
        <el-table-column prop="title" label="标题" width="200px"/>
        <el-table-column prop="auth" label="权限" width="70px"/>
        <el-table-column label="状态" width="100px">
          <template #default="scope">
            <el-switch
                v-model="scope.row.status"
                active-color="#13ce66"
                inactive-color="#ff4949"
                @change="modifyEnable(scope.row.id,!scope.row.status)"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" width="200px"/>
        <el-table-column prop="createId" label="创建者" width="100px"/>
        <el-table-column prop="createTime" label="创建时间" width="200px"/>
        <el-table-column prop="updateId" label="更新者" width="100px"/>
        <el-table-column prop="updateTime" label="更新时间" width="200px"/>
        <el-table-column fixed="'right'" label="操作" width="120">
          <template #default="scope">
            <el-button
                link
                type="primary"
                size="small"
                @click="dialogFormModifyRoute = true;
                modifyRouteBefore(scope.row)"
            >修改</el-button>
            <el-button
                link
                type="primary"
                size="small"
                @click="dialogDeleteRoute = true;
                deleteRouteBefore(scope.row.id)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          @current-change="change" 
      />
    </el-main>
    <el-dialog v-model="dialogDeleteRoute" title="删除路由">
      <div style="text-align: center">
        <el-tag type="warning">确定要删除该路由吗?(注意该操作是不可逆的)</el-tag>
      </div>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogDeleteRoute = false">取消删除路由</el-button>
        <el-button
            type="primary"
            @click="dialogDeleteRoute = false;
            deleteRouteMethod()"
        >确认删除路由</el-button>
      </span>
      </template>
    </el-dialog>
    <el-dialog v-model="dialogFormModifyRoute" title="修改新路由">
      <el-form :model="modifyRouteForm">
        <el-form-item label="路由名" :label-width="formLabelWidth">
          <el-input v-model="modifyRouteForm.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="路由路径" :label-width="formLabelWidth">
          <el-input v-model="modifyRouteForm.path" autocomplete="off" />
        </el-form-item>
        <el-form-item label="父组件名" :label-width="formLabelWidth">
          <el-select 
              v-model="modifyRouteForm.parentId"
              placeholder="请选择父组件名"
              size="large">
            <el-option
                v-for="item in routeName"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="组件路径" :label-width="formLabelWidth">
          <el-input v-model="modifyRouteForm.component" autocomplete="off" />
        </el-form-item>
        <el-form-item label="标题" :label-width="formLabelWidth">
          <el-input v-model="modifyRouteForm.title" autocomplete="off" />
        </el-form-item>
        <el-form-item label="权限组" :label-width="formLabelWidth">
          <el-select
              v-model="modifyRouteForm.auth"
              placeholder="请选择权限"
              size="large">
            <el-option value="user" label="user"/>
            <el-option value="admin" label="admin"/>
            <el-option value="guest" label="guest"/>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="modifyRouteForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormModifyRoute = false">取消修改路由</el-button>
        <el-button
            type="primary"
            @click="dialogFormModifyRoute = false;
            modifyRouteMethod()"
        >确认修改路由</el-button>
      </span>
      </template>
    </el-dialog>
    <el-dialog v-model="dialogFormAddRouteVisible" title="添加新路由">
      <el-form :model="addRouteForm">
        <el-form-item label="路由名" :label-width="formLabelWidth">
          <el-input v-model="addRouteForm.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="路由路径" :label-width="formLabelWidth">
          <el-input v-model="addRouteForm.path" autocomplete="off" />
        </el-form-item>
        <el-form-item label="父组件名(没有不选)" :label-width="formLabelWidth">
          <el-select
              v-model="addRouteForm.parentId"
              placeholder="请选择父组件名"
              size="large">
            <el-option
                v-for="item in routeName"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="组件路径" :label-width="formLabelWidth">
          <el-input v-model="addRouteForm.component" autocomplete="off" />
        </el-form-item>
        <el-form-item label="标题" :label-width="formLabelWidth">
          <el-input v-model="addRouteForm.title" autocomplete="off" />
        </el-form-item>
        <el-form-item label="权限组" :label-width="formLabelWidth">
          <el-select
              v-model="addRouteForm.auth"
              placeholder="请选择权限"
              size="large">
            <el-option value="user" label="user"/>
            <el-option value="admin" label="admin"/>
            <el-option value="guest" label="guest"/>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="addRouteForm.remark" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button
            @click="dialogFormAddRouteVisible = false"
        >取消添加新路由</el-button>
        <el-button
            type="primary"
            @click="dialogFormAddRouteVisible = false;
            addRouteMethod()"
        >确认添加新路由</el-button>
      </span>
      </template>
    </el-dialog>
  </el-container>
</template>

<script lang="ts" setup>
import {
  getRouteName,
  getRoutes,
  addRoute,
  updateStatus,
  updateRoute
} from "../../../api/Manager";
import {nextTick, onMounted, reactive, ref} from "vue";
import {useStore} from "../../../store";
import {ElMessage} from "element-plus";
import {Route} from "../../../entities/Route";

let store = useStore();
const tableData = ref<Array<Route>>([
]);
const total = ref<number>(0);
const page = ref<number>(0);
const pageNum = ref<number>(10);
const dialogFormAddRouteVisible = ref<boolean>(false);
const dialogFormModifyRoute = ref<boolean>(false);
const dialogDeleteRoute = ref<boolean>(false);
const addRouteForm =
    reactive<Route>(
        {path: '',
          name: '',
          parentId: '',
          component: '',
          title: '',
          auth: '',
          status: 0,
          remark: '',
          updateId: -1,
          createId: store.userinfo?.id as number,
          createTime: '',
          updateTime: '',
          id: -1
        }
    );
const formLabelWidth = '140px';
const routeName = ref<Array<{id: number, name: string}>>([
]);
const modifyRouteForm =
    reactive<Route>(
        {path: '',
          name: '',
          parentId: '',
          component: '',
          title: '',
          auth: '',
          status: 0,
          remark: '',
          updateId: -1,
          createId: store.userinfo?.id as number,
          createTime: '',
          updateTime: '',
          id: -1
        }
    );
const deleteRouteId = ref(0);

const deleteRouteBefore = (id:number) => {
  deleteRouteId.value = id;
};
const deleteRouteMethod = () => {
  ElMessage.info("暂不提供删除方法，请禁用它");
};
const modifyRouteBefore = (row: Route) => {
  modifyRouteForm.id = row.id;
  modifyRouteForm.path = row.path;
  modifyRouteForm.name = row.name;
  modifyRouteForm.auth = row.auth;
  modifyRouteForm.status = row.status;
  modifyRouteForm.parentId = row.parentId;
  modifyRouteForm.component = row.component;
  modifyRouteForm.title = row.title;
  modifyRouteForm.createId = row.createId;
  modifyRouteForm.createTime = row.createTime;
  modifyRouteForm.updateId = row.updateId;
  modifyRouteForm.updateTime = row.updateTime;
  modifyRouteForm.remark = row.remark;
  addRouteBefore();
};
const modifyRouteMethod = async () => {
  let data = modifyRouteForm;
  if (!Number.isFinite(data.parentId)){
    routeName.value;
    let route = routeName.value.filter((e) => e.name === data.parentId);
    data.parentId = route[0].id;
  }
  if (data.status){
    data.status = 0;
  }
else{
    data.status = 1;
  }
  data.updateId = store.userinfo?.id as number;
  await updateRoute(data).then((res) => {
    if (res.data.status === 200){
      ElMessage.success("修改成功");
      nextTick(() => update(page.value, pageNum.value));
    }
  });
};
const modifyEnable = async (id:number, enableId:boolean) => {
  let status = 0;
  if (enableId){
    status = 1;
  }
  await updateStatus(id, status).then((res) => {
    if (res.data.status === 200){
      if (status === 1){
        ElMessage.success("禁用该路由成功");
      }
else{
        ElMessage.success("启用该路由成功");
      }
    }
  });
};
const addRouteMethod = async () => {
  let data = addRouteForm;
  if (!Number.isFinite(data.parentId)){
    data.parentId = 0;
  }
  await addRoute(data).then((res) => {
    if (res.data.status === 200){
      ElMessage.success("添加路由成功");
      update(page.value, pageNum.value);
    }
else{
      ElMessage.error("添加失败，请联系服务器管理员");
    }
  });
};
const addRouteBefore = async () => {
  await getRouteName().then((res) => {
    routeName.value = res.data.data;
  });
};
const change = (id:number) => {
  page.value = (id - 1) * 10;
  update(page.value, pageNum.value);
};
const update = async (page:number, pageNum:number) => {
  await getRoutes(page, pageNum).then((res) => {
    tableData.value = res.data.data.routes;
    total.value = res.data.data.total;
    for (let i = 0; i < tableData.value.length; i++) {
      tableData.value[i].status = tableData.value[i].status === 0;
    }
  });
};
onMounted(() => {
  update(page.value, pageNum.value);
});
</script>

<style scoped>

</style>