<#list model.tables as table>
<#if table.code!="">
import ${table.code?cap_first} from "./${table.kebabCode}/${table.kebabCode}.vue";
</#if>
</#list>

export const components = [
<#list model.tables as table>
  <#if table.code!="">
        {title: "<#if table.name!="">${table.name}<#else>${table.code}</#if>", name: "${table.code?cap_first}", component: ${table.code?cap_first}, path: "/${table.kebabCode}"}<#if table?has_next&&table.code!="">,</#if>
  </#if>
</#list>
    ];

