import axios from './../../../http' 
let base = window.BASEDOMAIN + '/app/lantern'

// let base = 'http://192.168.3.68:8080' + '/app/lantern'
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
           return post(`${base}/getLanternList`, params)
       },
       // 删除活动
       delActivity(params) {
           return post(`${base}/delLantern`, params) 
       }, 
       // 获取手机端链接 
       getMobileUrl(params) {
           return post(`${base}/getMobileUrl`, params)   
       },
       // 统计活动总数 
       countActivity(params) {
        return post(`${base}/countActivity`, params) 
       },

       /* 修改/编辑活动 - 接口 */ 
       // 获取奖品类型列表 
       getPrizeType(params) {
           return post(`${base}/getLanternPrizeType`, params)    
       },
       // 新增活动 
       addActivity(params) {
           return post(`${base}/addLantern`, params)    
       },
       // 通过活动id查询活动 
       getActivityById(params) {
        return post(`${base}/getActivityById`, params) 
       },

       // 编辑活动
       modfiyActivity(params) {
        return post(`${base}/modfiyLantern`, params)  
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
       // 获取新增授权链接 /app/lantern/getAuthorityUrl
       getAuthorityUrl(params) {
        return post(`${base}/getAuthorityUrl`, params)   
       }, 

       /* 中奖记录 - 接口 */ 
       // 分页获取中奖记录列表 
       getWinningList(params) {
        return post(`${base}/getWinningList`, params) 
       },
       // 导出中奖记录 
       exportPrizeRecord(params) {
        let url = `${base}/exportLantern` 
                  + '?actId=' + params.actId 
                  + '&status=' +  params.status 
                  + '&type=' + params.type 
                  + '&snCode=' + params.snCode
        return url
       },
       // 批量删除中奖记录 
       delWinning(params) {
        return post(`${base}/delLanternWinning`, params) 
       },
       // 中奖记录发放奖品
       sendWinning(params) {
        return post(`${base}/editLanternApply`, params) 
       },

       

}


