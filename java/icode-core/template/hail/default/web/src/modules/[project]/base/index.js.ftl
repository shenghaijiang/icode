<#list model.tables as table>
  <#if table.code!="">
export * from "./<#assign res = table.code?matches("[A-Z][a-z]*+")><#list res as m><#if m?has_next>${m?lower_case}-<#else>${m?lower_case}</#if></#list>.js";
  </#if>
</#list>
