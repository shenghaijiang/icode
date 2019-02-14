package ${model.packageName}.service.impl;
<#if model.listName != "">
import ${model.packageName}.dto.${model.code}Dto;
import ${model.packageName}.entity.${model.listName};
import ${model.packageName}.entity.${model.listName}Example;
</#if>
import ${model.packageName}.entity.${model.code};
import ${model.packageName}.entity.${model.code}Example;
import ${model.packageName}.mapper.base.${model.code}Mapper;
<#if model.listName != "">
import ${model.packageName}.mapper.base.${model.listName}Mapper;
import ${model.packageName}.dto.${model.code}Dto;
</#if>
import ${model.packageName}.service.${model.code}Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Generator on ${.now?string('yyyy-MM-dd hh:mm:ss')}
 */
@Service
public class ${model.code}ServiceImpl implements ${model.code}Service {

    @Resource
    private ${model.code}Mapper ${model.code?uncap_first}Mapper;
<#if model.listName != "">
    @Resource
    private ${model.listName?cap_first}Mapper ${model.listName?uncap_first}Mapper;
</#if>

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return ${model.code?uncap_first}Mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(${model.code} record) {
        return ${model.code?uncap_first}Mapper.insertSelective(record);
    }

    @Override
    public List<${model.code}> listByExample(${model.code}Example example) {
        PageHelper.startPage(example.getPageIndex().intValue(), example.getPageSize().intValue());
        Page page = (Page) ${model.code?uncap_first}Mapper.selectByExample(example);
        example.setCount((int)page.getTotal());
        return page.toPageInfo().getList();
    }

    @Override
    public ${model.code} getByPrimaryKey(Integer id) {
        return ${model.code?uncap_first}Mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(${model.code} record) {
        return ${model.code?uncap_first}Mapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(${model.code} record) {
        return ${model.code?uncap_first}Mapper.updateByPrimaryKeySelective(record);
    }

    <#if model.listName != "">
    @Override
    public ${model.code}Dto insertWithList(${model.code}Dto record) {
        ${model.code?uncap_first}Mapper.insertSelective(record);
        if (record.get${model.listName}s() != null) {
            for (${model.listName?cap_first} item : record.get${model.listName?cap_first}s()) {
                item.set${model.code}Id(record.getId());
                item.setCreateDate(null);
                item.setMakeBillMan(record.getModifier());
                item.setModifier(record.getModifier());
                item.setModifyDate(null);
                item.setDeleteFlag(false);
                ${model.listName?uncap_first}Mapper.insertSelective(item);
            }
        }
        return record;
    }
    </#if>

    <#if model.listName != "">
    @Override
    public ${model.code}Dto updateWithList(${model.code}Dto record) {
    ${model.code?uncap_first}Mapper.updateByPrimaryKeySelective(record);
        if (record.get${model.listName}s() != null) {
            for (${model.listName?cap_first} item : record.get${model.listName?cap_first}s()) {
                item.set${model.code}Id(record.getId());
                item.setCreateDate(null);
                item.setMakeBillMan(null);
                item.setModifier(record.getModifier());
                item.setModifyDate(null);
                item.setDeleteFlag(false);
                if (item.getId() != null && item.getId() > 0) {
                    ${model.listName?uncap_first}Mapper.updateByPrimaryKeySelective(item);
                } else {
                    ${model.listName?uncap_first}Mapper.insertSelective(item);
                }
            }
        }
        return record;
    }
    </#if>

    <#if model.listName != "">
    @Override
    public int deleteWithList(${model.code} record) {
        int i = ${model.code?uncap_first}Mapper.updateByPrimaryKeySelective(record);

        ${model.listName}Example ${model.listName?uncap_first}Example = new ${model.listName}Example();
        ${model.listName?uncap_first}Example.setPageSize(Integer.MAX_VALUE);
        ${model.listName}Example.Criteria ${model.listName?uncap_first}ExampleCriteria = ${model.listName?uncap_first}Example.createCriteria();
        ${model.listName?uncap_first}ExampleCriteria.and${model.code?cap_first}IdEqualTo(record.getId());
        ${model.listName?uncap_first}ExampleCriteria.andDeleteFlagEqualTo(false);
        List<${model.listName?cap_first}> list = ${model.listName?uncap_first}Mapper.selectByExample(${model.listName?uncap_first}Example);

        if (list != null) {
            for (${model.listName?cap_first} item : list) {
                item.set${model.code}Id(record.getId());
                item.setCreateDate(null);
                item.setMakeBillMan(null);
                item.setModifier(record.getModifier());
                item.setModifyDate(null);
                item.setDeleteFlag(true);
                ${model.listName?uncap_first}Mapper.updateByPrimaryKeySelective(item);
            }
        }
        return i;
    }
    </#if>
}