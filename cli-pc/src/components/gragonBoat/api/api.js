import axios from './../../../http' 
// let base = window.BASEDOMAIN + '/app/Dragonboat'
let base = 'http://192.168.3.68:8080'+ '/app/Dragonboat'

/*新增端午赛龙舟活动*/
export const saveAct  = params => { return axios.post(`${base}/addDragonboat`, params).then(res => res.data) }

/*删除端午赛龙舟活动*/
export const delDragonboat  = params => { return axios.post(`${base}/delDragonboat`, params).then(res => res.data) }

/*批量删除核销授权*/
export const delDragonboatAuthority  = params => { return axios.post(`${base}/delDragonboatAuthority`, params).then(res => res.data) }

/*批量删除中奖记录*/
export const delDragonboatWinning  = params => { return axios.post(`${base}/delDragonboatWinning`, params).then(res => res.data) }

/*中奖记录发放奖品*/
export const editDragonboatApply  = params => { return axios.post(`${base}/editDragonboatApply`, params).then(res => res.data) }

/*导出中奖记录*/
export const exports = params => { return axios.post(`${base}/exportDragonboat`, params).then(res => res.data) }

/*获取新增授权链接*/
export const getAuthorityUrl = params => { return axios.post(`${base}/getAuthorityUrl`, params).then(res => res.data) }

/*分页获取核销授权列表*/
export const getDragonboatAuthorityList = params => { return axios.post(`${base}/getDragonboatAuthorityList`, params).then(res => res.data) }

/*获取端午赛龙舟活动数量*/
export const getDragonboatCount  = params => { return axios.post(`${base}/getDragonboatCount`, params).then(res => res.data) }

/*分页端午赛龙舟获取活动列表*/
export const getDragonboatList = params => { return axios.post(`${base}/getDragonboatList`, params).then(res => res.data) }

/*获取奖品类型列表*/
export const getPrizeType = params => { return axios.post(`${base}/getDragonboatPrizeType`, params).then(res => res.data) }

/*获取手机端链接*/
export const getMobileUrl = params => { return axios.post(`${base}/getMobileUrl`, params).then(res => res.data) }

/*连接*/
export const getShortUrl = params => { return axios.get(`${window.BASEDOMAIN}/app/link/getShorUrl?url=${params}`).then(res => res.data) }

/*分页获取端午赛龙舟中奖记录列表*/
export const getWinningList = params => { return axios.post(`${base}/getWinningList`, params).then(res => res.data) }

/*编辑*/
export const getAct  = params => { return axios.post(`${base}/modfiyDragonboat`, params).then(res => res.data) }

//通过活动id查询幸运九宫格活动
export const getActivityById  = params => { return axios.post(`${base}/getActivityById`, params).then(res => res.data) }
