package ${model.packageName}.service;
<#if model.listName != "">
import ${model.packageName}.dto.${model.code}Dto;
</#if>
import ${model.packageName}.entity.${model.code};
import ${model.packageName}.entity.${model.code}Example;
import java.util.List;

/**
 * Created by Generator ${.now?string('yyyy-MM-dd hh:mm:ss')}
 */
public interface ${model.code}Service {

    int deleteByPrimaryKey(Integer id);

    int insert(${model.code} record);

    List<${model.code}> listByExample(${model.code}Example example);

    ${model.code} getByPrimaryKey(Integer id);

    int updateByPrimaryKey(${model.code} record);

    int updateByPrimaryKeySelective(${model.code} record);

<#if model.listName != "">
    ${model.code}Dto insertWithList(${model.code}Dto record);

    ${model.code}Dto updateWithList(${model.code}Dto record);

    int deleteWithList(${model.code} record);
</#if>
}