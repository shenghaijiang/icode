{
    code: 1,
    msg: null,
    data: {
        data: [

            {
<#list model.columns as c>
                "${c.code?uncap_first}": <#switch c.dataType?lower_case>
        <#case "varchar">"string",
            <#break>
        <#case "int">1,
            <#break>
        <#case "decimal">1 ,
            <#break>
        <#case "bit">false ,
            <#break>
        <#case "datetime">"2017-12-08 11:11:11" ,
            <#break>
        <#default>"other",
    </#switch>
</#list>
            }
        ]
    }
}
