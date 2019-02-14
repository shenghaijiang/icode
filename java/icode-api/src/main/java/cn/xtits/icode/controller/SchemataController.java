package cn.xtits.icode.controller;

import cn.xtits.icode.entity.Schemata;
import cn.xtits.icode.entity.SchemataExample;
import cn.xtits.icode.query.Pagination;
import cn.xtits.icode.service.SchemataService;
import cn.xtits.xtf.common.utils.JsonUtil;
import cn.xtits.xtf.common.web.AjaxResult;
import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @fileName: Schemata}Controller.java
 * @author: Dan
 * @createDate:
 * @description:
 */
@RestController
@RequestMapping("/schemata")
public class SchemataController extends BaseController {

    @Autowired
    private SchemataService service;

    @RequestMapping(value = "insertSchemata")
    public AjaxResult insertSchemata(
            @RequestParam(value = "data", required = false) String data) {
        Schemata record = JsonUtil.fromJson(data, Schemata.class);
        Date dt = getDateNow();
        record.setCreateDate(dt);
        record.setMakeBillMan(getUserName());
        record.setModifier(getUserName());
        record.setModifyDate(dt);
        record.setDeleteFlag(false);
        int row = service.insert(record);
        return new AjaxResult(row);
    }

    @RequestMapping(value = "deleteSchemata")
    public AjaxResult deleteSchemata(
            @RequestParam(value = "id", required = false) int id) {
        Schemata record = new Schemata();
        record.setId(id);
        record.setDeleteFlag(true);
        record.setModifier(getUserName());
        record.setModifyDate(getDateNow());
        int row = service.updateByPrimaryKeySelective(record);
        return new AjaxResult(row);
    }

    @RequestMapping(value = "updateSchemata")
    public AjaxResult updateSchemata(
            @RequestParam(value = "data", required = false) String data) {
        Schemata record = JsonUtil.fromJson(data, Schemata.class);
        record.setCreateDate(null);
        record.setMakeBillMan(null);
        record.setModifyDate(getDateNow());
        record.setModifier(getUserName());
        record.setDeleteFlag(false);
        int row = service.updateByPrimaryKeySelective(record);
        return new AjaxResult(row);
    }

    @RequestMapping(value = "listSchemata")
    public AjaxResult listSchemata(
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "orderByName", required = false) String orderByName,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        SchemataExample example = new SchemataExample();
        example.setPageIndex(pageIndex);
        example.setPageSize(pageSize);
        if (StringUtils.isNotBlank(orderByName)) {
            example.setOrderByClause(orderByName);
        }
        SchemataExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo(false);

        List<Schemata> list = service.listByExample(example);
        Pagination<Schemata> pList = new Pagination<>(example, list, example.getCount());
        return new AjaxResult(pList);
    }

    @RequestMapping(value = "getSchemata")
    public AjaxResult getSchemata(@RequestParam(value = "id", required = false) Integer id) {
        Schemata res = service.getByPrimaryKey(id);
        return new AjaxResult(res);
    }
}