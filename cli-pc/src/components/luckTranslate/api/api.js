import axios from './../../../http' 
let base = window.BASEDOMAIN + '/app/luck'

/*连接*/
export const getShortUrl = params => { return axios.get(`${window.BASEDOMAIN}/app/link/getShorUrl?url=${params}`).then(res => res.data) }
export const getMobileUrl   = params => { return axios.post(`${base}/getMobileUrl`, params).then(res => res.data) }

/*获取奖品类型*/
export const getPrizeType     = params => { return axios.post(`${base}/getLuckPrizeType`, params).then(res => res.data) }

/*index*/
export const getActList      = params => { return axios.post(`${base}/getLuckList`, params).then(res => res.data) }
export const delAct          = params => { return axios.post(`${base}/removeLuck`, params).then(res => res.data) }
export const getActCount     = params => { return axios.post(`${base}/getLuckCount`, params).then(res => res.data) }
/*暂停*/
export const stopLuck          = params => { return axios.post(`${base}/stopLuck`, params).then(res => res.data) }


/*中奖*/
export const getPrizeList     = params => { return axios.post(`${base}/getLuckWinningList`, params).then(res => res.data) }
export const givePrize        = params => { return axios.post(`${base}/editLuckWinning`, params).then(res => res.data) }

/*新增，编辑*/
export const saveAct          = params => { return axios.post(`${base}/saveNewYear`, params).then(res => res.data) }
export const getAct           = params => { return axios.get(`${base}/getNewYear?id=${params}`).then(res => res.data) }


// /*核销*/
// export const getVerifierList = params => { return axios.post(`${base}/getNewYearAuthorityList`, params).then(res => res.data) }
// export const delVerifier     = params => { return axios.post(`${base}/removeNewYearAuthority`, params).then(res => res.data) } 
// export const getVerifierUrl  = params => { return axios.post(`${base}/getAuthorityUrl`, params).then(res => res.data) }
