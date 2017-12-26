<template>
  <section>
    <el-dialog title="预览" :visible.sync="copeDatad.copyUrlVisibled" @close="$util.closeParentMask" @open="$util.openParentMask"
      size="cope-link">
      <el-form label-width="120px">
        <el-form-item label="核销二维码：">
          <img class="erwema" :src=" $baseURL + '/app/link/buildQrcode?url='+ copeDatad.url" />  
        </el-form-item>
        <div style="color:#333;font-size:12px;margin-left:125px;margin-top:-15px">成为核销员后，通过以下链接，刻进去手机核销操作页面</div>
        <br/>
        <el-form-item label="核销链接:" v-if="copeDatad.url != ''">
          <input type="text" class="el-input__inner" id="gtLongUrl" style="width: 320px;
            margin-right: 17px;" :value="copeDatad.url">
          <el-button type="primary" data-clipboard-target="#gtLongUrl" aria-label="复制成功！" @click="copy(0)" id="gtLongUrlCopy">复制</el-button>
        </el-form-item>
        <el-form-item label="短信链接:" v-if="copeDatad.shortUrl != ''">
          <input type="text" class="el-input__inner" id="gtShortUrl" style="width: 320px;
            margin-right: 17px;" :value="copeDatad.shortUrl">
          <el-button type="primary" data-clipboard-target="#gtShortUrl" aria-label="复制成功！" @click="copy(1)" id="gtShortUrlCopy">复制</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </section>
</template>

<script>
  // 加载复制js
  import Clipboard from 'clipboard'
  export default {
    props: {
      copeDatad: {
        url: {
          type: String,
          default: ''
        },
        shortUrl: {
          type: String,
          default: ''
        },
        copyUrlVisibled: {
          type: Boolean,
          default: false
        },
      }
    },
    methods: {
      handleClick(evt) {
        this.$emit('click', evt);
      },
      down() {
        window.location.href = this.$baseURL + '/app/link/downQrcode?url=' + this.copeDatad.url
      },
      /*
       * 复制链接
       * */
      copy(type) {
        var self = this
        if (type === 0) {
          var clipboard = new Clipboard("#gtLongUrlCopy");
          clipboard.on('success', function (e) {
            self.$message({
              message: e.trigger.getAttribute('aria-label'),
              type: 'success'
            });
            clipboard.destroy();
          });

        } else if (type === 1) {
          var clipboard = new Clipboard("#gtShortUrlCopy");
          clipboard.on('success', function (e) {
            self.$message({
              message: e.trigger.getAttribute('aria-label'),
              type: 'success'
            });
            clipboard.destroy();
          });
        }
      },
      // 关闭回调
      closeDialog() {
        console.log('close', 999999999)
      }
    }
  }

</script>

<style lang="less" type="text/css" scoped>


</style>
