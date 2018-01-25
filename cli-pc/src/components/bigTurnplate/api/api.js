import axios from './../../../http'
let base = window.BASEDOMAIN + '/app/turntable'

//新增大转盘活动/
export const saveAct = params => {
  return axios.post(`${base}/addScratch`, params).then(res => res.data)
}

//统计大转盘活动总数/
export const getActCount = params => {
  return axios.post(`${base}/countTurntable`, params).then(res => res.data)
}

//通过活动id查询大转盘活动/
export const getActivityById = params => {
  return axios.post(`${base}/getActivityById`, params).then(res => res.data)
}

/*获取手机端链接*/
export const getShortUrl = params => {
  return axios.get(`${window.BASEDOMAIN}/app/link/getShorUrl?url=${params}`).then(res => res.data)
}

export const getMobileUrl = params => {
  return axios.post(`${base}/getMobileUrl`, params).then(res => res.data)
}

//分页获取大转盘活动列表/
export const getActList = params => {
  return axios.post(`${base}/getTurntableList`, params).then(res => res.data)
}

//编辑大转盘活动设置/
export const modfiyScratch = params => {
  return axios.post(`${base}/modfiyTurntable`, params).then(res => res.data)
}


//大转盘活动暂停/开始活动
export const stopLuck = params => {
  return axios.post(`${base}/stopTurntable`, params).then(res => res.data)
}
