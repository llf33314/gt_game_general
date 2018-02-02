import axios from './../../../http'
let base = window.BASEDOMAIN + '/app/goldtree'



/*连接*/
export const getShortUrl = params => {
  return axios.get(`${window.BASEDOMAIN}/app/link/getShorUrl?url=${params}`).then(res => res.data)
}
export const getMobileUrl = params => {
  return axios.post(`${base}/getMobileUrl`, params).then(res => res.data)
}

/*中奖记录发放奖品*/
export const givePrize = params => {
  return axios.post(`${base}/editGoldtreeApply`, params).then(res => res.data)
}

/*导出中奖记录*/
export const exports = params => {
  return axios.post(`${base}/exports`, params).then(res => res.data)
}

/*获取新增授权链接*/
export const getVerifierUrl = params => {
  return axios.post(`${base}/getAuthorityUrl`, params).then(res => res.data)
}

/*分页获取中奖记录列表*/
export const getPrizeList = params => {
  return axios.post(`${base}/getGoldtreeApplyList`, params).then(res => res.data)
}

/*分页获取核销授权列表*/
export const getVerifierList = params => {
  return axios.post(`${base}/getGoldtreeAuthorityList`, params).then(res => res.data)
}

/*获取活动数量*/
export const getActCount = params => {
  return axios.post(`${base}/getGoldtreeCount`, params).then(res => res.data)
}

/*分页获取活动列表*/
export const getActList = params => {
  return axios.post(`${base}/getGoldtreeList`, params).then(res => res.data)
}

/*获取奖品类型列表*/
// export const getPrizeType = params => { return axios.post(`${base}/getGoldtreePrizeType`, params).then(res => res.data) }
export const getPrizeType = params => {
  return axios.post(`${window.BASEDOMAIN}/app/prize/getPrizeTypeOne`, params).then(res => res.data)
}

/*获取公众号消息模板列表*/
export const getTemplateList = params => {
  return axios.post(`${base}/getTemplateList`, params).then(res => res.data)
}

/*删除活动*/
export const delAct = params => {
  return axios.post(`${base}/removeGoldtree`, params).then(res => res.data)
}

/*删除核销授权*/
export const delVerifier = params => {
  return axios.post(`${base}/removeGoldtreeAuthority`, params).then(res => res.data)
}


/*新增，编辑*/
export const saveAct = params => {
  return axios.post(`${base}/saveGoldtree`, params).then(res => res.data)
}
export const getAct = params => {
  return axios.get(`${base}/getGoldtree?id=${params}`).then(res => res.data)
}
