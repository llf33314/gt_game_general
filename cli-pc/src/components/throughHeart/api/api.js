import axios from './../../../http' 
let base = window.BASEDOMAIN + '/app/lovearrow'

/*连接*/
export const getShortUrl = params => { return axios.get(`${window.BASEDOMAIN}/app/link/getShorUrl?url=${params}`).then(res => res.data) }
export const getMobileUrl   = params => { return axios.post(`${base}/getMobileUrl`, params).then(res => res.data) }

/*获取奖品类型*/
export const getPrizeType    = params => { return axios.post(`${window.BASEDOMAIN}/app/prize/getPrizeTypeOne`, params).then(res => res.data) }

/*index*/
export const getActList      = params => { return axios.post(`${base}/getLoveArrowList`, params).then(res => res.data) }
export const delAct          = params => { return axios.post(`${base}/removeLoveArrow`, params).then(res => res.data) }
export const getActCount     = params => { return axios.post(`${base}/getLoveArrowCount`, params).then(res => res.data) }

/*核销*/
export const getVerifierList = params => { return axios.post(`${base}/getLoveArrowAuthorityList`, params).then(res => res.data) }
export const delVerifier     = params => { return axios.post(`${base}/removeLoveArrowAuthority`, params).then(res => res.data) } 
export const getVerifierUrl     = params => { return axios.post(`${base}/getAuthorityUrl`, params).then(res => res.data) }

/*中奖*/
export const getPrizeList     = params => { return axios.post(`${base}/getLoveArrowApplyList`, params).then(res => res.data) }
export const givePrize        = params => { return axios.post(`${base}/editLoveArrowApply`, params).then(res => res.data) }

/*新增，编辑*/
export const saveAct     = params => { return axios.post(`${base}/saveLoveArrow`, params).then(res => res.data) }
export const getAct      = params => { return axios.get(`${base}/getLoveArrow?id=${params}`).then(res => res.data) }
