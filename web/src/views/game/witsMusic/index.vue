<template>
  <div ref="div" class="getter">
    <canvas
        :width="total_width"
        :height="total_height"
        class="main-content"
        ref="canvas"
    />
  </div>
</template>

<script lang="ts" setup>
  import {onMounted, ref} from "vue";
  import {Line} from "../../../entities/Line";

  const canvas = ref<HTMLCanvasElement>();
  const div = ref<HTMLDivElement>();
  const total_width = ref<number>(1920);
  const total_height = ref<number>(1080);
  const onReSize = (_event: UIEvent) => {
    if (div.value){
      total_width.value = div.value.offsetWidth;
      total_height.value = div.value.offsetHeight;
    }
  };
  const mounted = () => {
    if (div.value && canvas.value){
      total_width.value = div.value.offsetWidth;
      total_height.value = div.value.offsetHeight;
      window.onresize = onReSize;
      const width = canvas.value.width;
      const height = canvas.value.height;
      const context: CanvasRenderingContext2D =
          canvas.value?.getContext("2d") as CanvasRenderingContext2D;
      context.fillStyle = "#FEFFA9";
      context.fillRect(0, 0, width, height);
      context.globalCompositeOperation = "destination-in";
      new Line(100, 0).draw(context);
    }
  };
  onMounted(mounted);
</script>

<style scoped>
.main-content{
  width: 100%;
  height: 100%;
}
.getter{
  width: 100%;
  height: 100%;
}
</style>