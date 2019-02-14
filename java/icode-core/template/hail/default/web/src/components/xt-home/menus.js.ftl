// side-nav menus
/* @freemark */

const modules = [
<#list model.tables as table>
    {sort: ${table?index+1}, name: "<#if table.name!="">${table.name}<#else>${table.code}</#if>", icon: "fa fa-th-large", path: "/${table.kebabCode}", children: [], displayFlag: true}<#if table?has_next>,</#if>
</#list>
],
// 菜单栏
menus = [
  {sort: 2, name: "基础模块", path: "modules", icon: "fa fa-database", displayFlag: true, children: modules}
];

export default menus;
