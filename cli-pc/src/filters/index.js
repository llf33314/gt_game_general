function pluralize(time, label) {
  if (time === 1) {
    return time + label
  }
  return time + label + 's'
}

export function timeAgo(time) {
  const between = Date.now() / 1000 - Number(time);
  if (between < 3600) {
    return pluralize(~~(between / 60), ' minute')
  } else if (between < 86400) {
    return pluralize(~~(between / 3600), ' hour')
  } else {
    return pluralize(~~(between / 86400), ' day')
  }
}

export function parseTime(time, cFormat) {
  if (arguments[0] == null || arguments[0] == undefined || arguments.length === 0) {
    return null;
  }

  if ((time + '').length === 10) {
    time = +time * 1000
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}';
  let date;
  if (typeof time == 'object') {
    date = time;
  } else {
    date = new Date(parseInt(time));
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  };
  const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key];
    if (key === 'a') return ['一', '二', '三', '四', '五', '六', '日'][value - 1];
    if (result.length > 0 && value < 10) {
      value = '0' + value;
    }
    return value || 0;
  });
  return time_str;
}

export function formatTime(time, option) {
  time = +time * 1000;
  const d = new Date(time);
  const now = Date.now();

  const diff = (now - d) / 1000;

  if (diff < 30) {
    return '刚刚'
  } else if (diff < 3600) { // less 1 hour
    return Math.ceil(diff / 60) + '分钟前'
  } else if (diff < 3600 * 24) {
    return Math.ceil(diff / 3600) + '小时前'
  } else if (diff < 3600 * 24 * 2) {
    return '1天前'
  }
  if (option) {
    return parseTime(time, option)
  } else {
    return d.getMonth() + 1 + '月' + d.getDate() + '日' + d.getHours() + '时' + d.getMinutes() + '分'
  }
}

/* 数字 格式化*/
export function nFormatter(num, digits) {
  const si = [
    {value: 1E18, symbol: 'E'},
    {value: 1E15, symbol: 'P'},
    {value: 1E12, symbol: 'T'},
    {value: 1E9, symbol: 'G'},
    {value: 1E6, symbol: 'M'},
    {value: 1E3, symbol: 'k'}
  ];
  for (let i = 0; i < si.length; i++) {
    if (num >= si[i].value) {
      return (num / si[i].value + 0.1).toFixed(digits).replace(/\.0+$|(\.[0-9]*[1-9])0+$/, '$1') + si[i].symbol;
    }
  }
  return num.toString();
}
//转小数点
export function toFixed(num, digits) {
  if (!isNaN(num)) {
    return num.toFixed(digits)
  }
  return num;
}
export function html2Text(val) {
  const div = document.createElement('div');
  div.innerHTML = val;
  return div.textContent || div.innerText;
}
export function toThousandslsFilter(num) {
  return (+num || 0).toString().replace(/^-?\d+/g, m => m.replace(/(?=(?!\b)(\d{3})+$)/g, ','));
}
export function displayType(key) {
  switch (key) {
    case 0:
      return '固定不变';
      break;
    case 1:
      return '定时刷新';
      break;
    case 2:
      return '一扫一换';
      break;
  }
}
export function signState(key) {//扫码签到状态
  switch (key) {
    case 0:
      return '正常';
      break;
    case 1:
      return '不正常';
      break;
  }
}
export function applyState(key) {//扫码签到兑奖
  switch (key) {
    case 0:
      return '已兑奖';
      break;
    case 1:
      return '未兑奖';
      break;
  }
}
export function wgpapplyState(key) {//微购票--订单管理--支付状态
  switch (key) {
    case 1:
      return '未支付';
      break;
    case 2:
      return '已支付';
      break;
  }
}
//转换下来状态
export function aaaa(key) {
  switch (key) {
    case 0 :
      return '审核中';
      break;
    case 1 :
      return '通过';
      break;
    case -1 :
      return '不通过';
      break;
  }


}

/* * 微预约--订单--转换下来状态 -----------------------------------------------------------------------* */
export function chioceName(key) {
  switch (key) {
    case -1 :
      return '全部';
      break;
    case 0 :
      return '等待确定';
      break;
    case 1 :
      return '确定预约';
      break;
    case 2 :
      return '取消预约';
      break;
    case 3 :
      return '完成预约';
      break;
  }
}

// 中奖记录 - 奖品类型
export function prizeType (val) {
  switch (val) {
    case 1 :
      return '粉币';
      break;
    case 2 :
      return '手机流量';
      break;
    case 3 :
      return '手机话费';
      break;
    case 4 :
      return '实体物品';
      break;
    case 5 :
      return '谢谢参与';
      break;
    case 6 :
      return '积分';
      break;
    case 7 :
      return '优惠券';
      break;
  }
}

// 中奖记录 - 兑奖状态
export function prizeStatus (val) {
  switch (val) {
    case 1 :
      return '未兑奖';
      break;
    case 2 :
      return '已兑奖';
      break;
    case 3 :
      return '已提交';
      break;
  }
}

// 中奖记录 - 领取方式
export function drawingMethod(val) {
  switch (val) {
    case 1 :
      return '到店领取';
      break;
    case 2 :
      return '邮寄';
      break;
    case 3 :
      return '直接兑奖';
      break;
  }

}

// 游戏 - 活动状态 
export function activityStatus(val) {
  switch (val) {
    case 0 :
      return '未开始';
      break;
    case 1 :
      return '进行中';
      break;
    case 2 :
      return '已暂停';
      break;
    case 3 :
      return '已结束';
      break;
  }

}

// 格式话时间戳
// @dome01 DateFormat('yyyy/MM/dd hh:mm:ss')  => 2017/10/09 09:50:00
// @dome02 DateFormat('yyyy-MM-dd hh:mm:ss')  => 2017-10-09 09:50:00
// @dome03 DateFormat('yyyy.MM.dd , hh-mm-ss') => 2017.10.09 , 09-50-00

export function DateFormat(date, fmt) {
 if(!(date && true)) {
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
 if(/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
 for(var k in o) {
   if(new RegExp('(' + k + ')').test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
 }
 return fmt
}