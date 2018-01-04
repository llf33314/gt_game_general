import axios from './../../../http'  

let base  = window.BASEDOMAIN 
/*短连接*/
export const requestgetShortUrl = params => { return axios.get(`${base}/app/link/getShorUrl?url=${params}`).then(res => res.data) }

export const getDemolitionList = params => { return axios.post(`${base}/app/demolition/getDemolitionList`, params).then(res => res.data) }

export const getMobileUrl = params => { return axios.post(`${base}/app/demolition/getMobileUrl`, params).then(res => res.data) }

export const getDemolitionCount = params => { return axios.post(`${base}/app/demolition/getDemolitionCount`, params).then(res => res.data) }

export const getDemolitionApplyList = params => { return axios.post(`${base}/app/demolition/getDemolitionApplyList`, params).then(res => res.data) }
export const removeDemolition = params => { return axios.post(`${base}/app/demolition/removeDemolition`, params).then(res => res.data) }


export const editDemolitionApply = params => { return axios.post(`${base}/app/demolition/editDemolitionApply`, params).then(res => res.data) }
export const getDemolitionAuthorityList = params => { return axios.post(`${base}/app/demolition/getDemolitionAuthorityList`, params).then(res => res.data) }

export const getAuthorityUrl = params => { return axios.post(`${base}/app/demolition/getAuthorityUrl`, params).then(res => res.data) }

export const removeDemolitionAuthority = params => { return axios.post(`${base}/app/demolition/removeDemolitionAuthority`, params).then(res => res.data) }
