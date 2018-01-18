import axios from './../../../http' 
let base = window.BASEDOMAIN + '/app/stand'

/*获取奖品类型*/
export const getPrizeType     = params => { return axios.post(`${base}/getStandPrizeType`, params).then(res => res.data) }

/*连接*/
export const getShortUrl = params => { return axios.get(`${window.BASEDOMAIN}/app/link/getShorUrl?url=${params}`).then(res => res.data) }
export const getMobileUrl   = params => { return axios.post(`${base}/getMobileUrl`, params).then(res => res.data) }

/*index*/
export const getActList      = params => { return axios.post(`${base}/getStandList`, params).then(res => res.data) }
export const delAct          = params => { return axios.post(`${base}/removeStand`, params).then(res => res.data) }
export const getActCount     = params => { return axios.post(`${base}/getStandCount`, params).then(res => res.data) }

/*核销*/
export const getVerifierList = params => { return axios.post(`${base}/getStandAuthorityList`, params).then(res => res.data) }
export const delVerifier     = params => { return axios.post(`${base}/removeStandAuthority`, params).then(res => res.data) } 
export const getVerifierUrl     = params => { return axios.post(`${base}/getAuthorityUrl`, params).then(res => res.data) }

/*中奖*/
export const getPrizeList     = params => { return axios.post(`${base}/getStandApplyList`, params).then(res => res.data) }
export const givePrize        = params => { return axios.post(`${base}/editSeagoldApply`, params).then(res => res.data) }


/*用户信息*/
export const getUserList     = params => { return axios.post(`${base}/getStandJoinRecord`, params).then(res => res.data) }
export const getUserDetail     = params => { return axios.post(`${base}/getStandJoinDetail`, params).then(res => res.data) }
export const delUser     = params => { return axios.post(`${base}/getStandJoinDetail`, params).then(res => res.data) } 

/*新增，编辑*/
export const saveAct      = params => { return axios.post(`${base}/saveStand`, params).then(res => res.data) }
export const getAct           = params => { return axios.get(`${base}/getStand?id=${params}`).then(res => res.data) }

/*获取题库列表*/
export const getQuesbank     = params => { return axios.post(`${base}/getStandQuesbankList`, params).then(res => res.data) }
//保存题库
export const saveQuesbank    = params => { return axios.post(`${base}/saveStandQuesbank`, params).then(res => res.data) }
//保存题目
export const saveStandQuestion    = params => { return axios.post(`${base}/saveStandQuestion`, params).then(res => res.data) }
