import axios from './../../../http'
// let base = window.BASEDOMAIN + '/app/turntable'
let base = 'http://192.168.3.68:8080' + '/app/turntable'

//新增大转盘活动/
export const saveAct = params => {
  return axios.post(`${base}/addScratch`, params).then(res => res.data)
}

//统计大转盘活动总数/
export const getActCount = params => {
  return axios.post(`${base}/countTurntable`, params).then(res => res.data)
}

//删除大转盘活动
export const delAct = params => {
  return axios.post(`${base}/delTurntable`, params).then(res => res.data)
}

//批量删除大转盘中奖记录
export const delScratchWinning = params => {
  return axios.post(`${base}/delTurntableWinning`, params).then(res => res.data)
}

//中奖记录发放奖品
export const editDragonboatApply = params => {
  return axios.post(`${base}/editTurntableApply`, params).then(res => res.data)
}

/*导出中奖记录*/
export const exports = params => {
  return axios.post(`${base}/exportTurntable`, params).then(res => res.data)
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

//获取奖品类型列表/
export const getPrizeType = params => {
  return axios.post(`${base}/getTurntablePrizeType`, params).then(res => res.data)
}

//分页获取大转盘中奖记录列表/
export const getWinningList = params => {
  return axios.post(`${base}/getWinningList`, params).then(res => res.data)
}
//编辑大转盘活动设置/
export const modfiyScratch = params => {
  return axios.post(`${base}/getWinningList`, params).then(res => res.data)
}


//大转盘活动暂停/开始活动
export const stopLuck = params => {
  return axios.post(`${base}/stopTurntable`, params).then(res => res.data)
}
