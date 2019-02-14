package ${model.packageName}.controller;
<#if model.listName != "">
import ${model.packageName}.dto.${model.code}Dto;
</#if>
import ${model.packageName}.entity.${model.code};
import ${model.packageName}.entity.${model.code}Example;
import ${model.packageName}.entity.Pagination;
import ${model.packageName}.service.${model.code}Service;
import cn.xtits.xtf.common.web.AjaxResult;
import cn.xtits.xtf.common.utils.JsonUtil;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
* @fileName: ${model.code}Controller.java
* @author: Dan
* @createDate: ${(.now?string("yyyy-MM-dd HH:mm:ss"))!}
* @description: ${model.remark}
*/
@RestController
@RequestMapping("/${model.code?uncap_first}")
public class ${model.code}Controller extends BaseController {

    @Autowired
    private ${model.code}Service service;

    //@RequiresPermissions("${model.code?lower_case}:insert")
    @RequestMapping(value = "insert${model.code}")
        public AjaxResult insert${model.code}(
                @RequestParam(value = "data", required = false) String data) {
        ${model.code} record = JsonUtil.fromJson(data, ${model.code}.class);
        //Date dt = getDateNow();
        record.setCreateDate(null);
        record.setMakeBillMan(getUserName());
        record.setModifier(getUserName());
        record.setModifyDate(null);
        record.setDeleteFlag(false);
        service.insert(record);
        return new AjaxResult(record);
    }

    //@RequiresPermissions("${model.code?lower_case}:delete")
    @RequestMapping(value = "delete${model.code}")
    public AjaxResult delete${model.code}(
            @RequestParam(value = "id", required = false) int id) {
        ${model.code} record = new ${model.code}();
        record.setId(id);
        record.setDeleteFlag(true);
        record.setModifier(getUserName());
        record.setModifyDate(null);
        int row = service.updateByPrimaryKeySelective(record);
        return new AjaxResult(row);
    }

    //@RequiresPermissions("${model.code?lower_case}:update")
    @RequestMapping(value = "update${model.code}")
    public AjaxResult update${model.code}(
            @RequestParam(value = "data", required = false) String data) {
        ${model.code} record = JsonUtil.fromJson(data, ${model.code}.class);
        record.setCreateDate(null);
        record.setMakeBillMan(null);
        record.setModifyDate(null);
        record.setModifier(getUserName());
        record.setDeleteFlag(false);
        service.updateByPrimaryKeySelective(record);
        return new AjaxResult(record);
    }

    @RequestMapping(value = "list${model.code}")
    public AjaxResult list${model.code}(
    <#list model.columnModelList as column>
        <#if column.code?lower_case == "modifier">
        <#elseif column.code?lower_case == "makebillman">
        <#elseif column.code?lower_case == "id">
        <#elseif column.code?lower_case == "deleteflag">
        <#elseif column.code?lower_case == "createdate">
        <#elseif column.code?lower_case == "modifydate">
        <#elseif column.code?lower_case == "modifier">
        <#elseif column.code?lower_case == "makebillman">
        <#elseif column.code?lower_case == "modifier">
        <#elseif column.dataType?lower_case == "decimal">
        <#elseif column.dataType?lower_case == "datetime">
            @RequestParam(value = "start${column.code}", required = false) String start${column.code},
            @RequestParam(value = "end${column.code}", required = false) String end${column.code},
        <#else>
            @RequestParam(value = "${column.code?uncap_first}", required = false) <#switch column.dataType?lower_case>
                <#case "varchar">String ${column.code?uncap_first},
                    <#break>
                <#case "int">String ${column.code?uncap_first},
                    <#break>
                <#case "decimal">BigDecimal ${column.code?uncap_first},
                    <#break>
                <#case "bit">Boolean ${column.code?uncap_first},
                    <#break>
                <#case "datetime">Date ${column.code?uncap_first},
                    <#break>
                <#case "text">String ${column.code?uncap_first},
                    <#break>
                <#case "char">String ${column.code?uncap_first},
                    <#break>
                <#default>Other ${column.code?uncap_first},
            </#switch>
        </#if>
    </#list>
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        ${model.code}Example example = new ${model.code}Example();
        example.setPageIndex(pageIndex);
        example.setPageSize(pageSize);
        if (StringUtils.isNotBlank(orderBy)) {
            example.setOrderByClause(orderBy);
        }
        ${model.code}Example.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo(false);

        <#list model.columnModelList as c>
                <#if c.code?lower_case=="id">
                <#elseif c.code?lower_case=="makebillman">
                <#elseif c.code?lower_case=="createdate">
        if (StringUtils.isNotBlank(startDate)) {
            criteria.andCreateDateGreaterThanOrEqualTo(DateTime.parse(startDate, format).toDate());
        }
        if (StringUtils.isNotBlank(endDate)) {

            criteria.andCreateDateLessThanOrEqualTo(DateTime.parse(endDate, format).toDate());
        }
                <#elseif c.code?lower_case=="modifier">
                <#elseif c.code?lower_case=="modifydate">
                <#elseif c.code?lower_case=="deleteflag">
                <#elseif c.dataType?lower_case=="datetime">
        if(StringUtils.isNotBlank(start${c.code})){
            criteria.and${c.code}GreaterThanOrEqualTo(DateTime.parse(start${c.code}, format).toDate());
        }
        if(StringUtils.isNotBlank(end${c.code})){
            criteria.and${c.code}LessThanOrEqualTo(DateTime.parse(end${c.code}, format).toDate());
        }
                <#elseif c.dataType?lower_case=="decimal">
                <#else>
                    <#if c.dataType?lower_case == "varchar">
        if (StringUtils.isNotBlank(${c.code?uncap_first})) {
            criteria.and${c.code?cap_first}Like(${c.code?uncap_first});
        }
                    <#elseif c.dataType?lower_case == "text">
        if (StringUtils.isNotBlank(${c.code?uncap_first})) {
            criteria.and${c.code?cap_first}Like(${c.code?uncap_first});
        }
                    <#elseif c.dataType?lower_case == "char">
        if (StringUtils.isNotBlank(${c.code?uncap_first})) {
            criteria.and${c.code?cap_first}Like(${c.code?uncap_first});
        }
                    <#elseif c.dataType?lower_case == "bit">
        if (${c.code?uncap_first} != null) {
             criteria.and${c.code?cap_first}EqualTo(${c.code?uncap_first});
        }
                    <#elseif c.dataType?lower_case == "int">
        <#--if (${c.code?uncap_first} != null) {
            criteria.and${c.code?cap_first}EqualTo(${c.code?uncap_first});
        }-->
        if (StringUtils.isNotBlank(${c.code?uncap_first})) {
            String[] split = ${c.code?uncap_first}.split(",");
            if (split.length > 1) {
                List<Integer> ${c.code?uncap_first}List = new ArrayList<>();
                for (String s : split) {
                        ${c.code?uncap_first}List.add(Integer.parseInt(s));
                }
                criteria.and${c.code?cap_first}In(${c.code?uncap_first}List);
            } else {
                criteria.and${c.code?cap_first}EqualTo(Integer.parseInt(split[0]));
            }
        }

                <#else>
        if (${c.code?uncap_first} != null && ${c.code?uncap_first} > 0) {
            criteria.and${c.code?cap_first}Like(${c.code?uncap_first});
         }
                </#if>
            </#if>
        </#list>

        List<${model.code}> list = service.listByExample(example);
        Pagination<${model.code}> pList = new Pagination<>(example,list,example.getCount());
        return new AjaxResult(pList);
    }

    @RequestMapping(value = "get${model.code}")
    public AjaxResult get${model.code}(
            @RequestParam(value = "id", required = false) Integer id) {
        ${model.code} res = service.getByPrimaryKey(id);
        return new AjaxResult(res);
    }

<#if model.listName != "">
    @RequestMapping(value = "insert${model.code}WithList")
    public AjaxResult insert${model.code}WithList(
            @RequestParam(value = "data", required = false) String data) {
        ${model.code}Dto record = JsonUtil.fromJson(data, ${model.code}Dto.class);
        //Date dt = getDateNow();
        record.setCreateDate(null);
        record.setMakeBillMan(getUserName());
        record.setModifier(getUserName());
        record.setModifyDate(null);
        record.setDeleteFlag(false);
        service.insertWithList(record);
        return new AjaxResult(record);
    }

    @RequestMapping(value = "update${model.code}WithList")
    public AjaxResult update${model.code}WithList(
            @RequestParam(value = "data", required = false) String data) {
        ${model.code}Dto record = JsonUtil.fromJson(data, ${model.code}Dto.class);
        //Date dt = getDateNow();
        record.setCreateDate(null);
        record.setMakeBillMan(null);
        record.setModifier(getUserName());
        record.setModifyDate(null);
        record.setDeleteFlag(false);
        service.updateWithList(record);
        return new AjaxResult(record);
    }

    @RequestMapping(value = "delete${model.code}WithList")
    public AjaxResult delete${model.code}WithList(
            @RequestParam(value = "id", required = false) Integer id) {
        ${model.code} record = new ${model.code}();
        record.setId(id);
        record.setDeleteFlag(true);
        record.setModifier(getUserName());
        record.setModifyDate(getDateNow());
        int row = service.deleteWithList(record);
        return new AjaxResult(row);
    }
</#if>
}