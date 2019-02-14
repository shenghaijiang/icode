import { ${model.packageName?keep_after_last(".")?upper_case} } from "../../api";
import { Message } from "element-ui";

const ${model.code?cap_first} = function() {
  return {
<#list model.columns as column>
<#if column.code!='null'&&column.code?lower_case!='makebillman'&&column.code?lower_case!='createdate'&&column.code?lower_case!='modifydate'&&column.code?lower_case!='modifier'&&column.code?lower_case!='deleteflag'>
    ${column.code?uncap_first}: <#if column.code?uncap_first=="id">0<#else>null</#if><#list model.columns as p><#if p?index gt column?index ><#if p.code!='null'&&p.code?lower_case!='makebillman'&&p.code?lower_case!='createdate'&&p.code?lower_case!='modifydate'&&p.code?lower_case!='modifier'&&p.code?lower_case!='deleteflag'>,<#break></#if></#if></#list>
</#if></#list>
  };
},
${model.code?cap_first}API = {
  ${model.code?uncap_first}: new ${model.code?cap_first}(),
  get: () => ${model.code?cap_first}API.${model.code?uncap_first},
  set: (value) => {
    ${model.code?cap_first}API.${model.code?uncap_first} = Object.assign(${model.code?cap_first}API.${model.code?uncap_first}, value);
  },
  init: () => new ${model.code?cap_first}(),
  get${model.code?cap_first}(params) {
    return new Promise((resolve) => {
      ${model.packageName?keep_after_last(".")?upper_case}.${model.code?uncap_first}.get${model.code?cap_first}(params).then(({data, res}) => {
        let item = {};
        if (data.code === 1) {item = data.data;}
        resolve({ data, item, res });
        });
    });
  },
  list${model.code?cap_first}(params) {
    return new Promise((resolve) => {
      ${model.packageName?keep_after_last(".")?upper_case}.${model.code?uncap_first}.list${model.code?cap_first}(params).then(({data, res}) => {
        let list = [];
        if (data.code === 1) {list = data.data.data;}
        resolve({ data, list, res });
        });
    });
  },
  insert${model.code?cap_first}(params) {
    return new Promise((resolve) => {
      ${model.packageName?keep_after_last(".")?upper_case}.${model.code?uncap_first}.insert${model.code?cap_first}(params).then(({data, res}) => {
        if (data.code === 1) {
          Message({
            message: "新增成功",
            type: "success"
          });
        }
        resolve({data, res});
        });
    });
  },
  update${model.code?cap_first}(params) {
    return new Promise((resolve) => {
      ${model.packageName?keep_after_last(".")?upper_case}.${model.code?uncap_first}.update${model.code?cap_first}(params).then(({data, res}) => {
        if (data.code === 1) {
          Message({
            message: "修改成功",
            type: "success"
          });
        }
        resolve({data, res});
        });
    });
  },
  delete${model.code?cap_first}(params) {
    return new Promise((resolve) => {
      ${model.packageName?keep_after_last(".")?upper_case}.${model.code?uncap_first}.delete${model.code?cap_first}(params).then(({data, res}) => {
        if (data.code === 1) {
          Message({
            message: "删除成功",
            type: "success"
          });
        }
        resolve({data, res});
        });
    });
  }
};

export { ${model.code?cap_first}API };
