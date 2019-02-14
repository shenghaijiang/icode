package ${model.packageName}.dto;
import ${model.packageName}.entity.${model.code};
<#if model.listName != "">
import ${model.packageName}.entity.${model.listName};
</#if>
import java.util.List;

public class ${model.code}Dto extends ${model.code} {
<#if model.listName != "">
    List<${model.listName}> ${model.listName?uncap_first}s;

    public List<${model.listName}> get${model.listName?cap_first}s() {
    return ${model.listName?uncap_first}s;
    }

    public void set${model.listName?cap_first}s(List<${model.listName}> ${model.listName?uncap_first}s) {
    this.${model.listName?uncap_first}s = ${model.listName?uncap_first}s;
    }
</#if>
}