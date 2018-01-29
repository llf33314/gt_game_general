import axios from './../../../http'

let base = window.BASEDOMAIN + '/app/prize'

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
    // 获取奖品类型列表 全部的 7个
    getPrizeType(params) {
        return post(`${base}/addScratch`, params) 
    },
    // 获取奖品类型列表（去掉谢谢参与和手机话费） 5个 
    getPrizeTypeOne(params) {
        return post(`${base}/getPrizeTypeOne`, params) 
    },
    // 获取奖品类型列表(去掉优惠劵) 6个
    getPrizeTypeThree(params) {
        return post(`${base}/getPrizeTypeThree`, params) 
    },
    // 获取奖品类型列表(去掉谢谢参与、手机话费、优惠劵)  4个
    getPrizeTypeTow(params) {
        return post(`${base}/getPrizeTypeTow`, params) 
    },
    // 获取优惠券列表
    getCardReceviceList(params){
        return post(`${base}/getCardReceviceList`, params)
    },
    // 分页获取手机列表
    getMemberList(params) {
        return post(`${base}/getMemberList`, params)
    }
}
