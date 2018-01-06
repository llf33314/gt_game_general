

import Vue from 'vue'
import App from './App'
import router from './router'
import axios from './http/index'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/css/public.less'
import './assets/css/common.less'//ZHONGHUIDI
import './assets/iconfont/iconfont.css'
Vue.use(ElementUI)
Vue.config.productionTip = true
import  util  from './assets/js/util'

Vue.prototype.$util = util 
Vue.prototype.$baseURL = 'http://192.168.3.10:7073' 
Vue.prototype.$baseImgURL = 'http://maint.deeptel.com.cn/upload'

const GtNullData = ()=>import('./components/PublicVue/null-data/nullData')
const GtCopyUrl = ()=>import('./components/PublicVue/copy-url/copy-url')
const GtMaterial = ()=>import('./components/PublicVue/material/material')
const GtRegionChoose = ()=>import('./components/PublicVue/region-choose/RegionChoose')
const GtAudio = ()=>import('./components/PublicVue/GtAudio/GtAudio')
const GtEnquire = ()=>import('./components/PublicVue/enquire/Enquire')
const GtVideoBtn = ()=>import('./components/PublicVue/video-button/video-button')
const GtDelTip = ()=>import('./components/PublicVue/del-tip/del-tip')
Vue.component('gt-null-data',GtNullData)         // 暂无数据提示
Vue.component('gt-copy-url',GtCopyUrl)           // 链接弹窗
Vue.component('gt-material',GtMaterial)          // 获取素材库单张图片url
Vue.component('gt-region-choose',GtRegionChoose) // 选择省市区
Vue.component('gt-audio',GtAudio)                // 音频播放
Vue.component('gt-enquire',GtEnquire)
Vue.component('gt-video-btn',GtVideoBtn)         // 视频播放按钮
Vue.component('gt-del-tip',GtDelTip)             // 删除提示

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    axios,
    template: '<App/>',
    components: { App }
  })
  
/*
* 来源：https://clipboardjs.com/
* */
import clipboard from '../../../static/js/lib/copy/clipboard.min.js'
window.clipboard = clipboard
export default clipboard

