<!-- video-button.vue组件 -->
<template>
  <el-button type="warning"  videoId="" @click="linkToVideo" class="PublicVue-video-button">
    <i class="iconfont gt-play"></i>视频教程
  </el-button>
</template>

<script>
export default {
  props: {
    videoId: {
      default: ""
    }
  },
  methods: {
    linkToVideo() {
      const url = this.$baseURL + "/app/link/" + this.videoId + "/getVideourl";
      window.open(url);
    }
  }
};
</script>

<style lang="less" type="text/css" scoped>
.PublicVue-video-button {
  .gt-play {
    padding-right: 5px;
    font-size: 13px;
  }
}
</style>
