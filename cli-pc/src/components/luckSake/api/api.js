import axios from './../../../http' 
let base = window.BASEDOMAIN + '/app/shakeluck'

/*连接*/
export const getShortUrl = params => { return axios.get(`${window.BASEDOMAIN}/app/link/getShorUrl?url=${params}`).then(res => res.data) }
export const getMobileUrl= params => { return axios.post(`${base}/getMobileUrl`, params).then(res => res.data) }


/*获取奖品类型*/
export const getPrizeType    = params => { return axios.post(`${window.BASEDOMAIN}/app/prize/getPrizeTypeOne`, params).then(res => res.data) }
/*获取优惠劵列表*/
export const getMemberType    = params => { return axios.post(`${window.BASEDOMAIN}/app/member/getCardReceviceList`, params).then(res => res.data) }


/*index*/
export const getActList      = params => { return axios.post(`${base}/getShakeluckList`, params).then(res => res.data) }
export const delAct          = params => { return axios.post(`${base}/removeShakeluck`, params).then(res => res.data) }
export const getActCount     = params => { return axios.post(`${base}/getShakeluckCount`, params).then(res => res.data) }

/*核销*/
export const getVerifierList = params => { return axios.post(`${base}/getShakeluckAuthorityList`, params).then(res => res.data) }
export const delVerifier     = params => { return axios.post(`${base}/removeShakeluckAuthority`, params).then(res => res.data) } 
export const getVerifierUrl  = params => { return axios.post(`${base}/getAuthorityUrl`, params).then(res => res.data) }

/*中奖*/
export const getPrizeList     = params => { return axios.post(`${base}/getShakeluckApplyList`, params).then(res => res.data) }
export const givePrize        = params => { return axios.post(`${base}/editShakeluckApply`, params).then(res => res.data) }

/*新增，编辑*/
export const saveAct      = params => { return axios.post(`${base}/saveShakeluck`, params).then(res => res.data) }
export const getAct       = params => { return axios.get(`${base}/getShakeluck?id=${params}`).then(res => res.data) }
