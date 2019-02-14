import {Message} from 'element-ui';
import Vue from 'vue'

var SIGN_REGEXP = /([yMdhsm])(\1*)/g;
var DEFAULT_PATTERN = 'yyyy-MM-dd';

function padding(s, len) {
  var len = len - (s + '').length;
  for (var i = 0; i < len; i++) {
    s = '0' + s;
  }
  return s;
};

export default {
  getQueryStringByName: function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    var context = "";
    if (r != null)
      context = r[2];
    reg = null;
    r = null;
    return context == null || context == "" || context == "undefined" ? "" : context;
  },
  formatDate: {
    format: function (date, pattern) {
      pattern = pattern || DEFAULT_PATTERN;
      return pattern.replace(SIGN_REGEXP, function ($0) {
        switch ($0.charAt(0)) {
          case 'y':
            return padding(date.getFullYear(), $0.length);
          case 'M':
            return padding(date.getMonth() + 1, $0.length);
          case 'd':
            return padding(date.getDate(), $0.length);
          case 'w':
            return date.getDay() + 1;
          case 'h':
            return padding(date.getHours(), $0.length);
          case 'm':
            return padding(date.getMinutes(), $0.length);
          case 's':
            return padding(date.getSeconds(), $0.length);
        }
      });
    },
    parse: function (dateString, pattern) {
      var matchs1 = pattern.match(SIGN_REGEXP);
      var matchs2 = dateString.match(/(\d)+/g);
      if (matchs1.length == matchs2.length) {
        var _date = new Date(1970, 0, 1);
        for (var i = 0; i < matchs1.length; i++) {
          var _int = parseInt(matchs2[i]);
          var sign = matchs1[i];
          switch (sign.charAt(0)) {
            case 'y':
              _date.setFullYear(_int);
              break;
            case 'M':
              _date.setMonth(_int - 1);
              break;
            case 'd':
              _date.setDate(_int);
              break;
            case 'h':
              _date.setHours(_int);
              break;
            case 'm':
              _date.setMinutes(_int);
              break;
            case 's':
              _date.setSeconds(_int);
              break;
          }
        }
        return _date;
      }
      return null;
    }
  }

};

export const ArrayFun = Array.prototype.remove = function (val) {
  var index = this.indexOf(val);
  if (index > -1) {
    this.splice(index, 1);
  }
}

export const DateFun = Date.prototype.Format = function (fmt) { //author: meizz
  var o = {
    "M+": this.getMonth() + 1, //月份
    "d+": this.getDate(), //日
    "h+": this.getHours(), //小时
    "m+": this.getMinutes(), //分
    "s+": this.getSeconds(), //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    "S": this.getMilliseconds() //毫秒
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}

export const DateCal = function (date) {
  if (this instanceof DateCal) {
    let now = new Date(date); //当前日期
    let nowDayOfWeek = now.getDay(); //今天本周的第几天
    let nowDay = now.getDate(); //当前日
    let nowMonth = now.getMonth(); //当前月
    let nowYear = now.getYear(); //当前年
    nowYear += (nowYear < 2000) ? 1900 : 0;
    this.toMyDay = function (todate) {
      var weekStartDate = new Date(nowYear, nowMonth, nowDay + todate);
      return weekStartDate.Format('yyyy-MM-dd')
    };
  } else {
    return new DateCal(date);
  }
}

export const CheckExp = {
  isTel(tel) {
    if (!(/^1[34578]\d{9}$/.test(tel))) return false
    return true
  },
  isQQ(qq) {
    var re = /[1-9][0-9]{4,}/;
    if (!re.test(qq)) return false;
    return true
  }

}

export const MessageBox = {
  codeMessage(code, message, fn) {
    return new Promise((resolve, reject) => {
      let errorCode = [{code: 1, msg: '成功'}, {code: 0, msg: '逻辑错误'}, {code: -1, msg: '服务器错误'}, {
        code: -2,
        msg: '记录已存在'
      }, {code: -3, msg: '存在关联数据'}]

    })
  },
  messageBox(res) {
    this.codeMessage(res.data.code).then(({message}) => {
      message = (res.data.msg != null && res.data.msg != 'undefined') ? res.data.msg : message;
      Message({
        message: message,
        type: 'error'
      });
    })
  }
}


export const Compares = {
  compareAscending: function (sortname) {
    return function (object1, object2) {
      var value1 = object1[sortname];
      var value2 = object2[sortname];
      if (value1 < value2) {
        return -1;
      } else if (value1 > value2) {
        return 1;
      } else {
        return 0;
      }
    }
  }

};
export const vueHub = new Vue();


export const debounce = function (fn, delay) {
  var timer = null;
  return function () {
    let context = this,
      args = arguments;
    clearTimeout(timer);
    timer = setTimeout(function () {
      fn.apply(context, args);
    }, delay);
  }
};

//字符串处理
export const StrUtils = {
  initialsCapital(str) {
    //首字母大写
    if (str != undefined && str != null && str.length > 0) {
      str = str.substring(0, 1).toUpperCase() + str.substring(1, str.length);
    }
    return str;
  }, initialsLowerCase(str) {
    //首字母小写
    if (str != undefined && str != null && str.length > 0) {
      str = str.substring(0, 1).toLowerCase() + str.substring(1, str.length);
    }
    return str;
  }, replaceStr(str) {
    //所有首字母大写
    var reg = /\b(\w)|\s(\w)/g; //  \b判断边界\s判断空格
    return str.replace(reg, function (m) {
      return m.toUpperCase()
    });
  }, isStrNotNull(str) {
    if (str != null && str.trim().length > 0) {
      return true;
    }
    return false;
  }, spliceStr(params) {
    //拼接地址栏参数
    if (!params) {
      params = {};
    }
    let ret = '?'
    for (let it in params) {
      if (params[it].trim()) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(params[it]) + '&'
      }
    }
    return ret;
  }

}
