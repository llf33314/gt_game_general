import Vue from 'vue'
import App from './App'
import axios from './http/index'
import * as filters from './filters'
import router from './router'
import isJs from 'is_js'
import valid from './assets/js/validate'
import commonApi from './http/commonApi'
//加载全局方法
import  util  from './assets/js/util'
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
});
//加载全局组件
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
Vue.use(ElementUI)
const GtNullData = ()=>import('./components/PublicVue/null-data/nullData')
const GtCopyUrl = ()=>import('./components/PublicVue/copy-url/copy-url')
const GtCopyUrld = ()=>import('./components/PublicVue/copy-url/copy-url-d')
const GtMaterial = ()=>import('./components/PublicVue/material/material')
const GtRegionChoose = ()=>import('./components/PublicVue/region-choose/RegionChoose')
const GtAudio = ()=>import('./components/PublicVue/GtAudio/GtAudio')
const GtEnquire = ()=>import('./components/PublicVue/enquire/Enquire')
const GtVideoBtn = ()=>import('./components/PublicVue/video-button/video-button')
const GtDelTip = ()=>import('./components/PublicVue/del-tip/del-tip')
const GtDialog = ()=>import('./components/PublicVue/dialog/dialog')
const GtPrizeDetail = ()=>import('components/PublicVue/prizeDetail/prizeDetail')
const GtFansData = ()=>import('components/PublicVue/fans-dialog/fans')//指定中奖人
//加载全局样式 
require('./assets/css/public.less')
require('./assets/css/public.css')
require('./assets/css/common.less')
//加载全局阿里iconfont 
require('./assets/iconfont/iconfont.css')
//挂载全局axsio方法
Vue.prototype.$axios = axios
Vue.prototype.$api =  commonApi
//在启动时生成生产提示
Vue.config.productionTip = true
Vue.config.devtools = true
//挂载全局方法 
Vue.prototype.$util = util 
Vue.prototype.$valid = valid
Vue.prototype.$baseURL = 'http://192.168.3.10:9037'
Vue.prototype.$baseImgURL = 'http://maint.deeptel.com.cn/upload'
Vue.prototype.$is = isJs                         // 第三方检验方法   参考地址：https://github.com/arasatasaygin/is.js
// 注册全局组件 
Vue.component('gt-null-data',GtNullData)         // 暂无数据提示
Vue.component('gt-copy-url',GtCopyUrl)           // 链接弹窗
Vue.component('gt-copy-url-d',GtCopyUrld)        // 链接弹窗d         
Vue.component('gt-material',GtMaterial)          // 获取素材库单张图片url
Vue.component('gt-region-choose',GtRegionChoose) // 选择省市区
Vue.component('gt-audio',GtAudio)                // 音频播放
Vue.component('gt-enquire',GtEnquire)
Vue.component('gt-video-btn',GtVideoBtn)         // 视频播放按钮
Vue.component('gt-del-tip',GtDelTip)             // 删除提示
Vue.component('el-dialog',GtDialog)                // 弹出框
Vue.component('gt-prize-detail',GtPrizeDetail)   // 中奖记录详情  
Vue.component('gt-Fans-detail',GtFansData)      // 指定中奖人            
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  axios,
  template: '<App/>',
  components: { App }
})
