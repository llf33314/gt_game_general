import axios from './../../../http' 
let base = window.BASEDOMAIN + '/app/tree'

// let base = 'http://192.168.3.68:8080'
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
       /* 活动数据列表 - 接口 */
       // 获取活动列表
       getActivityList(params) {
           return post(`${base}/getTreeList`, params)
       },
       // 删除活动
       delActivity(params) {
           return post(`${base}/delTree`, params) 
       }, 
       // 获取手机端链接 
       getMobileUrl(params) {
           return post(`${base}/getMobileUrl`, params)   
       },
       // 统计活动总数 
       countActivity(params) {
        return post(`${base}/countTree`, params) 
       },

       /* 修改/编辑活动 - 接口 */ 
       // 获取奖品类型列表 
       getPrizeType(params) {
           return post(`${base}/getTreePrizeType`, params)  
       },
       // 新增活动 
       addActivity(params) {
           return post(`${base}/addTree`, params)     
       },
       // 通过活动id查询活动 
       getActivityById(params) {
        return post(`${base}/getActivityById`, params) 
       },

       // 编辑活动基础设置  
       modfiyBasicsLantern(params) {
        return post(`${base}/modfiyBasicsLantern`, params) 
       },
       // 编辑活动规则设置  
       modfiyRuleLantern(params) {
        return post(`${base}/modfiyRuleLantern`, params) 
       },
       // 编辑活动兑奖设置 
       modfiyExpiryLantern(params) {
        return post(`${base}/modfiyExpiryLantern`, params) 
       },
       // 编辑活动奖项设置  
       modfiyAwardsLantern(params) {
        return post(`${base}/modfiyAwardsLantern`, params) 
       }, 

       /* 核销授权 - 接口 */ 
       // 分页获取核销授权列表
       getAuthorityList(params) {
        return post(`${base}/getLanternAuthorityList`, params) 
       }, 
       // 批量删除核销授权
       delAuthority(params) { 
        return post(`${base}/delLanternAuthority`, params) 
       },
       // 获取授权二维码


       /* 中奖记录 - 接口 */ 
       // 分页获取中奖记录列表  
       getWinningList(params) {
        return post(`${base}/getWinningList`, params) 
       },
       // 导出中奖记录 
       exportPrizeRecord(params) {
        let url = `${base}/exportTree` 
                  + '?actId=' + params.actId 
                  + '&status=' +  params.status 
                  + '&type=' + params.type 
                  + '&snCode=' + params.snCode
        return url
       },
       // 批量删除中奖记录 
       delWinning(params) {
        return post(`${base}/delTreeWinning`, params) 
       },
       // 中奖记录发放奖品 
       sendWinning(params) {
        return post(`${base}/editTreeApply`, params) 
       },

       

}


