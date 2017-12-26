import axios from './../../../http' 
let base = window.BASEDOMAIN
/*短连接*/
export const requestgetShortUrl = params => { return axios.get(`${base}/app/link/getShorUrl?url=${params}`).then(res => res.data) }
