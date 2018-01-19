import axios from './../../../http' 
let base = window.BASEDOMAIN + '/app/lantern' 

// let base = 'http://192.168.3.68:8080'

/*连接*/
export const getShortUrl = params => { return axios.get(`${window.BASEDOMAIN}/app/link/getShorUrl?url=${params}`).then(res => res.data) }
export const getMobileUrl   = params => { return axios.post(`${base}/getMobileUrl`, params).then(res => res.data) }

/*获取奖品类型*/
export const getPrizeType     = params => { return axios.post(`${base}/getNewYearPrizeType`, params).then(res => res.data) }

/*index*/
export const getActList      = params => { return axios.post(`${base}/getNewYearList`, params).then(res => res.data) }
export const delAct          = params => { return axios.post(`${base}/removeNewYear`, params).then(res => res.data) }
export const getActCount     = params => { return axios.post(`${base}/getNewYearCount`, params).then(res => res.data) }

/*核销*/
export const getVerifierList = params => { return axios.post(`${base}/getNewYearAuthorityList`, params).then(res => res.data) }
export const delVerifier     = params => { return axios.post(`${base}/removeNewYearAuthority`, params).then(res => res.data) } 
export const getVerifierUrl     = params => { return axios.post(`${base}/getAuthorityUrl`, params).then(res => res.data) }

/*中奖*/
export const getPrizeList     = params => { return axios.post(`${base}/getNewYearApplyList`, params).then(res => res.data) }
export const givePrize        = params => { return axios.post(`${base}/editNewYearApply`, params).then(res => res.data) }

/*新增，编辑*/
export const saveAct          = params => { return axios.post(`${base}/addLantern`, params).then(res => res.data) }
export const getActData       = params => { return axios.get(`${base}/getNewYear?id=${params}`).then(res => res.data) }

function post(url, params) {
    return axios.post(url, params)
      .then(res => {
          return res.data
      })
      .catch(error => {
        return {code: 400}
      })
  }
  
  function get(url, params) {
    return axios.get(url, params)
      .then(res => {
          return res.data
      })
      .catch(error => {
        return {code: 400}
      })
  }
  
export default {
       // 分页获取元宵点灯活动列表  1
       getLanternList(params) {
           return post(`${base}/getLanternList`, params)
       },
       // 删除元宵点灯活动 2
       delLantern(params) {
           return post(`${base}/delLantern`, params) 
       }, 
       // 获取手机端链接 3
       getMobileUrl(params) {
           return post(`${base}/getMobileUrl`, params)   
       },
       // 获取奖品类型列表 4
       getLanternPrizeType(params) {
           return post(`${base}/getLanternPrizeType`, params)    
       },
       // 新增元宵点灯活动 5
       addLantern(params) {
           return post(`${base}/addLantern`, params)    
       },
       // 通过活动id查询元宵点灯活动 6
       getActivityById(params) {
        return post(`${base}/getActivityById`, params) 
       },
       // 编辑元宵点灯活动基础设置  7
       modfiyBasicsLantern(params) {
        return post(`${base}/modfiyBasicsLantern`, params) 
       },
       // 编辑元宵点灯活动规则设置  8
       modfiyRuleLantern(params) {
        return post(`${base}/modfiyRuleLantern`, params) 
       },
       // 编辑元宵点灯活动兑奖设置 9
       modfiyExpiryLantern(params) {
        return post(`${base}/modfiyExpiryLantern`, params) 
       },
       // 编辑元宵点灯活动奖项设置  10
       modfiyAwardsLantern(params) {
        return post(`${base}/modfiyAwardsLantern`, params) 
       }, 
       // 分页获取核销授权列表 11
       getLanternAuthorityList(params) {
        return post(`${base}/getLanternAuthorityList`, params) 
       }, 
       // 批量删除核销授权 12 
       delLanternAuthority(params) {
        return post(`${base}/delLanternAuthority`, params) 
       },
       // 分页获取元宵点灯中奖记录列表 13 
       getWinningList(params) {
        return post(`${base}/getWinningList`, params) 
       }
}


