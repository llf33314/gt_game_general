import axios from './../../../http' 
let base = window.BASEDOMAIN
/*短连接*/
export const requestgetShortUrl = params => { return axios.get(`${base}/app/link/getShorUrl?url=${params}`).then(res => res.data) }

/*中奖记录发放奖品*/
export const editGoldtreeApply  = params => { return axios.post(`${base}/app/goldtree/editGoldtreeApply`, params).then(res => res.data) }

/*导出中奖记录*/
export const exports  = params => { return axios.post(`${base}/app/goldtree/exports`, params).then(res => res.data) }

/*获取新增授权链接*/
export const getAuthorityUrl  = params => { return axios.post(`${base}/app/goldtree/getAuthorityUrl`, params).then(res => res.data) }

/*获取活动*/
export const getGoldtree  = params => { return axios.post(`${base}/app/goldtree/getGoldtree`, params).then(res => res.data) }

/*分页获取中奖记录列表*/
export const getGoldtreeApplyList  = params => { return axios.post(`${base}/app/goldtree/getGoldtreeApplyList`, params).then(res => res.data) }

/*分页获取核销授权列表*/
export const getGoldtreeAuthorityList  = params => { return axios.post(`${base}/app/goldtree/getGoldtreeAuthorityList`, params).then(res => res.data) }

/*获取活动数量*/
export const getGoldtreeCount = params => { return axios.post(`${base}/app/goldtree/getGoldtreeCount`, params).then(res => res.data) }

/*分页获取活动列表*/
export const getActList = params => { return axios.post(`${base}/app/goldtree/getGoldtreeList`, params).then(res => res.data) }

/*获取奖品类型列表*/
export const getGoldtreePrizeType = params => { return axios.post(`${base}/app/goldtree/getGoldtreePrizeType`, params).then(res => res.data) }

/*获取手机端链接*/
export const getMobileUrl = params => { return axios.post(`${base}/app/goldtree/getMobileUrl`, params).then(res => res.data) }

/*获取公众号消息模板列表*/
export const getTemplateList  = params => { return axios.post(`${base}/app/goldtree/getTemplateList`, params).then(res => res.data) }

/*删除活动*/
export const removeGoldtree = params => { return axios.post(`${base}/app/goldtree/removeGoldtree`, params).then(res => res.data) }

/*删除核销授权*/
export const removeGoldtreeAuthority = params => { return axios.post(`${base}/app/goldtree/removeGoldtreeAuthority`, params).then(res => res.data) }

/*保存活动*/
export const saveGoldtree = params => { return axios.post(`${base}/app/goldtree/saveGoldtree`, params).then(res => res.data) }


