import axios from './../../../http' 
let base = window.BASEDOMAIN + '/app/scratch'

//新增刮刮乐活动/
export const saveAct = params => { return axios.post(`${base}/addScratch`, params).then(res => res.data) }

//统计刮刮乐活动总数/
export const getActCount = params => { return axios.post(`${base}/countScratch`, params).then(res => res.data) }

//通过活动id查询刮刮乐活动/
export const getActivityById= params => { return axios.post(`${base}/getActivityById`, params).then(res => res.data) }

/*获取手机端链接*/
export const getShortUrl = params => { return axios.get(`${window.BASEDOMAIN}/app/link/getShorUrl?url=${params}`).then(res => res.data) }
export const getMobileUrl= params => { return axios.post(`${base}/getMobileUrl`, params).then(res => res.data) }

//分页获取刮刮乐活动列表/
export const getActList = params => { return axios.post(`${base}/getScratchList`, params).then(res => res.data) }