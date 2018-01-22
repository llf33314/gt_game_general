import {
  Region
} from "./region.js"
import {
  Message
} from 'element-ui'
export default {
  /**
   * @Created by xiandan on 2017-10-09
   * @desc 根据时间,格式 戳返回日期
   * @param { date } - 时间戳  @param { fmt }  - 格式格式
   * @return { fmt }
   * @dome01 DateFormat(1507513800642, 'yyyy/MM/dd hh:mm:ss')  => 2017/10/09 09:50:00
   * @dome02 DateFormat(1507513800642, 'yyyy-MM-dd hh:mm:ss')  => 2017-10-09 09:50:00
   * @dome03 DateFormat(1507513800642, 'yyyy.MM.dd , hh-mm-ss') => 2017.10.09 , 09-50-00
   */
  DateFormat(date, fmt) {
    if (!(date && true)) {
      return date
    }
    date = new Date(parseInt(date))
    var o = {
      'M+': date.getMonth() + 1,
      'd+': date.getDate(),
      'h+': date.getHours(),
      'm+': date.getMinutes(),
      's+': date.getSeconds(),
      'q+': Math.floor((date.getMonth() + 3) / 3),
      'S': date.getMilliseconds()
    }
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
    for (var k in o) {
      if (new RegExp('(' + k + ')').test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
    }
    return fmt
  },
  /**
   * @Created by xiandan on 2017-10-09
   * @desc 确保输出的数据类型
   * @param { val }  需要判断的值
   * @return  返回想要的数据类型
   */
  isArray(val) {
    return Object.prototype.toString.call(val) === '[object Array]' ? val : []
  },
  isObject(val) {
    return Object.prototype.toString.call(val) === '[object Object]' ? val : {}
  },
  isString(val) {
    return Object.prototype.toString.call(val) === '[object String]' ? val : ''
  },
  openParentMask() {
    // setTimeout(function(){
    //   document.querySelectorAll('.gt-mask')[0].style.display='block'
    // },10)
    parent.window.postMessage("openMask()", "http://localhost:8099/index.html");
  },
  closeParentMask() {
    // setTimeout(function(){
    //   document.querySelectorAll('.gt-mask')[0].style.display='none'
    // },10)
    parent.window.postMessage("closeMask()", "http://localhost:8099/index.html");
  },
  /**
   * @desc 数字验证
   * @param { number }  
   * @return  { Boolean }
   */
  isNumber(val) {
    let result = true;
    let isNumber = /^\d+$/;
    if (!isNumber.test(val)) {
      result = false;
    }
    return result;
  },
  /**
   * @desc 正整数验证
   * @param { number }  
   * @return  { Boolean }
   */
  isIntger(val) {
    let result = true;
    let isIntger = /^[1-9]\d*$/;
    if (!isIntger.test(val)) {
      result = false;
    }
    return result;
  },
  /**
   * @desc QQ验证
   * @param { number }  
   * @return  { Boolean }
   */
  isQQ(aQQ) {
    var bValidate = RegExp(/^[1-9][0-9]{4,9}$/).test(aQQ);
    return bValidate;
  },
  /**
   * @desc 手机验证
   * @param { number }  
   * @return  { Boolean }
   */
  Mobilephone(obj) {
    var result = true;
    var isPhone = /^((\+?86)|(\(\+86\)))?(13[0123456789][0-9]{8}|15[0123456789][0-9]{8}|17[0123456789][0-9]{8}|18[0123456789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
    if (isPhone.test(obj)) {

    } else {
      result = false;
    }
    return result;
  },
  /**
   * @desc 固话验证
   * @param { number }  
   * @return  { Boolean }
   */
  fixedtelephone(obj) {
    var result = true;
    var isfixed = /^([0-9]{3,4})?[0-9]{7,8}$/;
    if (isfixed.test(obj)) {

    } else {
      result = false;
    }
    return result;
  },
  /**
   * @desc 固话验证&手机验证
   * @param { number }  
   * @return  { Boolean }
   */
  phone(obj) {
    var result = true;
    var isPhone = /^((\+?86)|(\(\+86\)))?(13[0123456789][0-9]{8}|15[0123456789][0-9]{8}|17[0123456789][0-9]{8}|18[0123456789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
    var isfixed = /^([0-9]{3,4})?[0-9]{7,8}$/;
    if (isPhone.test(obj) || isfixed.test(obj)) {

    } else {
      result = false;
    }
    return result;
  },
  /**
   * @desc 固话验证&手机验证
   * @param { Object }  
   * @return  { Object }
   */
  MapAddressCode(obj) {
    let addressObj = {}
    Region.forEach(function (item) {
      if (item.cityName == obj.province) {
        addressObj.province = obj.province;
        addressObj.provinceId = item.id;

        if (item.ticketCityRes) {
          item.ticketCityRes.forEach(function (child) {
            if (child.cityName == obj.city) {
              addressObj.city = obj.city;
              addressObj.cityId = child.id;
            }
            if (child.ticketCityRes) {
              child.ticketCityRes.forEach(function (child2) {
                if (child2.cityName == obj.district) {
                  addressObj.district = obj.district;
                  addressObj.districtId = child2.id;
                }
              });
            }
          });
        }
      }
    });
    return addressObj;
  },
  // 获取短信链接  
  getShortUrl(url) {
    return axios.get(window.BASEDOMAIN + '/app/link/getShorUrl?url=' + url)
      .then(res => {
        return res.data
      })
      .catch(error => {
        Message.error('获取短信链接失败')
        return {
          code: 400
        }
      })
  },
  // 控制键盘只能输入数字包含小数
  KeydownFloatNumber() {
    if (!(event.keyCode == 46) &&
      !(event.keyCode == 8) &&
      !(event.keyCode == 37) &&
      !(event.keyCode == 39) &&
      !(event.keyCode == 110)
    )
      if (!(
          (event.keyCode >= 48 && event.keyCode <= 57) ||
          (event.keyCode >= 96 && event.keyCode <= 105)
        ))
        event.returnValue = false;
  },
  // 控制键盘只能输入整数数字
  KeydownNumber() {
    if (!(event.keyCode == 46) &&
      !(event.keyCode == 8) &&
      !(event.keyCode == 37) &&
      !(event.keyCode == 39)
    )
      if (!(
          (event.keyCode >= 48 && event.keyCode <= 57) ||
          (event.keyCode >= 96 && event.keyCode <= 105)
        ))
        event.returnValue = false;
  },

}
