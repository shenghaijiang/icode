import { _axios } from "./axios.config";
import { GetUrl, HttpRequest } from "./http-request";

const ${model.packageName?keep_after_last(".")?lower_case}Url = GetUrl("${model.packageName?keep_after_last(".")?upper_case}").url,
${model.packageName?keep_after_last(".")?upper_case} = HttpRequest(${model.packageName?keep_after_last(".")?lower_case}Url, [
<#list model.tables as table>
      "${table.code?uncap_first}"<#if table?has_next>,</#if>
</#list>
    ]).api;

export {
  ${model.packageName?keep_after_last(".")?upper_case}
};
