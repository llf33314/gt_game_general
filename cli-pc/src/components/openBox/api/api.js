import axios from './../../../http' 
let base = window.BASEDOMAIN + '/app/demolition'

/*连接*/
export const getShortUrl = params => { return axios.get(`${window.BASEDOMAIN}/app/link/getShorUrl?url=${params}`).then(res => res.data) }
export const getMobileUrl   = params => { return axios.post(`${base}/getMobileUrl`, params).then(res => res.data) }

/*获取奖品类型*/
export const getPrizeType     = params => { return axios.post(`${base}/getDemolitionPrizeType`, params).then(res => res.data) }

/*index*/
export const getActList      = params => { return axios.post(`${base}/getDemolitionList`, params).then(res => res.data) }
export const delAct          = params => { return axios.post(`${base}/removeDemolition`, params).then(res => res.data) }
export const getActCount     = params => { return axios.post(`${base}/getDemolitionCount`, params).then(res => res.data) }

/*核销*/
export const getVerifierList = params => { return axios.post(`${base}/getDemolitionAuthorityList`, params).then(res => res.data) }
export const delVerifier     = params => { return axios.post(`${base}/removeDemolitionAuthority`, params).then(res => res.data) } 
export const getVerifierUrl     = params => { return axios.post(`${base}/getAuthorityUrl`, params).then(res => res.data) }

/*中奖*/
export const getPrizeList     = params => { return axios.post(`${base}/getDemolitionApplyList`, params).then(res => res.data) }
export const givePrize        = params => { return axios.post(`${base}/editDemolitionApply`, params).then(res => res.data) }

/*新增，编辑*/
export const saveAct      = params => { return axios.post(`${base}/saveDemolition`, params).then(res => res.data) }
export const getAct       = params => { return axios.get(`${base}/getDemolition?id=${params}`).then(res => res.data) }
