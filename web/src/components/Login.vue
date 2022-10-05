<template>
  <div class="background">
    <canvas id="bgCanvas"></canvas>
  </div>
  <div class="login-window">
    <div class="inline-div right">
      <div class="login-window-child title" style="margin-top: 50px">登录</div>
      <div class="login-window-child input" style="margin-top: 25px">
        <el-input v-model="username" placeholder="请在此输入用户名">
          <template #prepend>用户名</template>
        </el-input>
      </div>
      <div class="login-window-child input">
        <el-input v-model="password" placeholder="请在此输入密码" show-password type="password">
          <template #prepend>密码</template>
        </el-input>
      </div>
      <div class="login-window-child login-button">
        <el-button plain type="primary">登录</el-button>
      </div>
    </div>
    <div class="inline-div left img"/>
  </div>
</template>

<script lang="ts">
import Render from '../Render'
import Tick from '../Tick'

export default {
  name: "Login",
  mounted() {
    const canvas: HTMLCanvasElement = document.getElementById("bgCanvas") as HTMLCanvasElement;
    const render = new Render(canvas);
    let tick;

    function main() {

      // 上传贴图
      render.updateTex();

      // 开启主循环
      tick = new Tick(function (t: number) {

        // 清除缓冲器
        render.clean();

        // 渲染器更新
        render.update(t);

        // 绘制
        render.drawAllHeader();
      });

      // 开启性能监视
      // tick.useStats();
    }

    // 自适应
    window.onresize = function () {
      render.resize();
    }

    // 开启事件监听
    canvas.onmousemove = function (e) {
      render.mouseMove(e.offsetX, e.offsetY);
    }

    /**
     * 渲染器启动方法
     * @param headerUrl
     */
    function start(headerUrl: string[]) {

      // 添加头颅;
      render.addHeader(headerUrl);

      // 加载贴图
      render.loadHeader().then(main);
    }

    // 获取玩家列表
    let e = ["A__H", "BYCBZ", "Dong_Yi_feng", "Eznae", "Fearlicia", "GG_guanggao", "Im_airman", "linseng520", "Li_Zi_Shou", "maltcrafter44", "mc_xinxin", "qianxiao3333", "QianZhuo05", "qing_chen_you", "tot222", "wzpMC", "YunyunOVO", "mrkbear"];
    start(e.map((v) => "./tex/" + v + "/"));
  }
}
</script>
<script lang="ts" setup>
import {ref} from "vue";

const username = ref<string>();
const password = ref<string>();

</script>

<style scoped>
.login-window {
  border: 2px var(--el-border-color);
  border-radius: var(--el-border-radius-round);
  box-shadow: var(--el-box-shadow-dark);
  width: 60%;
  height: 40%;
  left: 50%;
  top: 50%;
  margin-left: -30%;
  margin-top: -15%;
  position: absolute;
}

.login-window-child {
  margin-top: 10px;
}

.input {
  margin-left: 90px;
  width: 70%;
}

.login-button {
  margin-left: 45%;
}

.inline-div {
  display: inline;
}

.left {
  position: absolute;
  left: 0;
  width: 50%;
  height: 100%;
}

.right {
  position: absolute;
  right: 0;
  width: 50%;
  height: 100%;
  background-color: white;
  border-right: 2px var(--el-border-color);
  border-top-right-radius: var(--el-border-radius-round);
  border-bottom-right-radius: var(--el-border-radius-round);
}

.img {
  background-repeat: no-repeat;
  background-image: url("./src/assets/img.png");
  background-size: 100% 100%;
  border-left: 2px var(--el-border-color);
  border-top-left-radius: var(--el-border-radius-round);
  border-bottom-left-radius: var(--el-border-radius-round);
}

.title {
  font-size: 1.1em;
  font-family: "Helvetica Neue", sans-serif;
  text-align: center;
}

:root {
  --cube-size: 100px;
  --color-white: #f5f8ea;
  --color-white-shadow-1: #96c6d9;
  --color-white-shadow-2: #e1eef2;
  --color-blue: #0d5299;
}

/*背景图层*/
div.background {
  height: 0;
}

div.background canvas {
  width: 100%;
  height: 100vh;
}
</style>