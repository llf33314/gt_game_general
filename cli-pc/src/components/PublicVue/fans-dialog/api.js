import axios from './../../../http' 
let base = window.BASEDOMAIN + '/app/luck' 
export const getMembers = params => { return axios.post(`${window.BASEDOMAIN}/app/member/getMemberList`, params).then(res => res.data) }
 
