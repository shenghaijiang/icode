import { Message  } from 'element-ui';
import Vue from 'vue'

export const vueHub=new Vue();

export const MessageBox={
    codeMessage(code,message,fn){
        return new Promise((resolve, reject) => {
        let errorCode=[{code:1,msg:'成功'},{code:0,msg:'逻辑错误'},{code:-1,msg:'服务器错误'},{code:-2,msg:'记录已存在'},{code:-3,msg:'存在关联数据'}]
            // SystemConfigAPI.getSysConfigList({}).then((res) => {
            //     if(res.data.code==1) Object.assign(errorCode,res.data.data)
            //         let message='';
            //         message=errorCode.find(element => element.code==code).msg;
            //         resolve({message});
            // })
            let message='';
            message=errorCode.find(element => element.code==code).msg;
            resolve({message});
        })
    },
    messageBox(res){
        this.codeMessage(res.data.code).then(({message}) => {
            message=(res.data.msg!=null&&res.data.msg!='undefined')?res.data.msg:message;
            Message({
                message: message,
                type: 'error'
            });
        })
    }
}

export const ArrayFun=Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
}

export const DateFun=Date.prototype.Format=function (fmt) { //author: meizz
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

export const CheckExp={
    isTel(tel)
    {
        if ((/^1[34578]\d{9}$/.test(tel))) return true
        // if((/^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$/.test(tel))) return true
        else return false;
    },
    isQQ(qq){
        var re=/[1-9][0-9]{4,}/;
        if(!re.test(qq)) return false;
        return true
    }

}

export const GetQueryString = function (name) {
  const reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
  const params = window.location.search || (window.location.href.indexOf("?") != -1 ? window.location.href.substring(window.location.href.indexOf("?"), window.location.href.length) : "");
  const r = params.substr(1).match(reg);
  if (r != null) {
    return unescape(r[2]);
  }
  return null;
}

export const ParamsSelect = {
  getAddSelectParams: (row, list, need, field) => {
    let fieldName = Object.keys(field)[0];    //所需要的数据，搜索的数据，需要获取的字段{需要的字段名：数据里面的字段}，匹配字段(在所需要中数据里面能找到){值的字段名：匹配的字段名}
    let childrenFun = function (item) {
      if (item[field[fieldName]] == row[fieldName]) {
        Object.keys(need).map((keyItem) => {
          row[keyItem] = item[need[keyItem]];
        })
        return false;
      }
      if (item.children) {
        item.children.map((child) => {
          childrenFun(child)
        })
      }
    }
    list.map((element) => {
      childrenFun(element);
    })
  }, exitIncludeFields: (arrList, exitField, field) => {
    if (!field) {
      return true;
    }
    //默认显示-字母转成小写比较
    let find = arrList.find(d => d.fieldCode.toLocaleLowerCase() === field.toLocaleLowerCase());
    //console.log('find:', find, 'field:', field)
    let exitFlag = find ? true : false;
    //console.log('exitFlag:', exitFlag, 'exitField:', exitField)
    if (exitFlag) {
      return find[exitField];
    }
    return true;
  },
};
