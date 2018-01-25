import axios from './../../../http'
let base = window.BASEDOMAIN + '/app/scratch'

//新增刮刮乐活动/
export const saveAct = params => {
  return axios.post(`${base}/addScratch`, params).then(res => res.data)
}

//统计刮刮乐活动总数/
export const getActCount = params => {
  return axios.post(`${base}/countScratch`, params).then(res => res.data)
}

//批量删除刮刮乐活动
export const delAct = params => {
  return axios.post(`${base}/delScratch`, params).then(res => res.data)
}

//批量删除刮刮乐活动中奖记录
export const delScratchWinning = params => {
  return axios.post(`${base}/delScratchWinning`, params).then(res => res.data)
}

//中奖记录发放奖品
export const editDragonboatApply = params => {
  return axios.post(`${base}/editScratchApply`, params).then(res => res.data)
}

/*导出中奖记录*/
export const exports = params => {
  return axios.post(`${base}/exportScratch`, params).then(res => res.data)
}

// //通过活动id查询刮刮乐活动/
// export const getActivityById = params => {
//   return axios.post(`${base}/getActivityById`, params).then(res => res.data)
// }

/*获取手机端链接*/
export const getShortUrl = params => {
  return axios.get(`${window.BASEDOMAIN}/app/link/getShorUrl?url=${params}`).then(res => res.data)
}
export const getMobileUrl = params => {
  return axios.post(`${base}/getMobileUrl`, params).then(res => res.data)
}

//分页获取刮刮乐活动列表/
export const getActList = params => {
  return axios.post(`${base}/getScratchList`, params).then(res => res.data)
}

// //获取奖品类型列表/
// export const getPrizeType = params => {
//   return axios.post(`${base}/getScratchPrizeType`, params).then(res => res.data)
// }

//分页获取刮刮乐中奖记录列表/
export const getWinningList = params => {
  return axios.post(`${base}/getWinningList`, params).then(res => res.data)
}

//编辑刮刮乐活动设置/
export const modfiyScratch = params => {
  return axios.post(`${base}/modfiyScratch`, params).then(res => res.data)
}

//刮刮乐开始/暂停活动/
export const stopLuck = params => {
  return axios.post(`${base}/stopScratch`, params).then(res => res.data)
}




/* web_liuyaowei */
function post(url, params) {
  return axios.post(url, params)
    .then(res => {
        return res.data
    })
    .catch(error => {
      return {code: 400}
    })
}

function get(url, params) {
  return axios.get(url, params)
    .then(res => {
        return res.data
    })
    .catch(error => {
      return {code: 400}
    })
}

// 新增活动 
export const addActivity = params => {
    return post(`${base}/addScratch`, params)    
}
// 获取奖品类型列表 
export const getPrizeType = params => {
  return post(`${base}/getScratchPrizeType`, params)    
}
// 编辑元宵点灯 
export const modfiyActivity = params => { 
  return post(`${base}/modfiyScratch`, params)  
 }
// 通过活动id查询活动 
export const getActivityById = params => { 
  return post(`${base}/getActivityById`, params) 
}
