<template>

</template>

<script lang="ts" setup>
import {MessageData} from "./MessageData";

const props = defineProps({data: MessageData})
console.log(props.data)
</script>

<style scoped>

</style>