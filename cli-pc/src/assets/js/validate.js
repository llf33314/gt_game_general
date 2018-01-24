
import $util from './util'

export default {
    elemIsQQrequired(rule, value, callback) {
        if (!value) {
           callback(new Error('请输入QQ号码'))
        } else if (!$util.isQQ(value)) {
           callback(new Error('请输入正确的QQ号码'))
        } else {
           callback()
        }
    },
    elemIsQQ(rule, value, callback) {
        if (!value) {
           callback()
        }else if (value && !$util.isQQ(value)) {
           callback(new Error('请输入正确的QQ号码'))
        } else {
           callback()
        }
    },
    // 检验必须输入的正整数
    elemIsInteger(rule, value, callback) {
        let reg = /^[0-9]*[1-9][0-9]*$/
        if (!value) {
            callback(new Error('请输入正整数'))
        } else if(!reg.test(value)) {
            callback(new Error('请输入正整数'))
        } else {
            callback()
        }
    }
   
}