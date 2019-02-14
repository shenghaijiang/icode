package cn.xtits.icode.controller;

import cn.xtits.icode.entity.TableInfo;
import cn.xtits.icode.entity.TableInfoExample;
import cn.xtits.icode.query.Pagination;
import cn.xtits.icode.service.TableInfoService;
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
 * @fileName: TableInfo}Controller.java
 * @author: Dan
 * @createDate:
 * @description:
 */
@RestController
@RequestMapping("/tableInfo")
public class TableInfoController extends BaseController {

    @Autowired
    private TableInfoService service;

    @RequestMapping(value = "insertTableInfo")
    public AjaxResult insertTableInfo(
            @RequestParam(value = "data", required = false) String data) {

        TableInfo record = JsonUtil.fromJson(data, TableInfo.class);
        Date dt = getDateNow();
        record.setCreateDate(dt);
        record.setMakeBillMan(getUserName());
        record.setModifier(getUserName());
        record.setModifyDate(dt);
        record.setDeleteFlag(false);
        int row = service.insert(record);
        return new AjaxResult(row);
    }

    @RequestMapping(value = "deleteTableInfo")
    public AjaxResult deleteTableInfo(
            @RequestParam(value = "id", required = false) int id) {
        TableInfo record = new TableInfo();
        record.setId(id);
        record.setDeleteFlag(true);
        record.setModifier(getUserName());
        record.setModifyDate(getDateNow());
        int row = service.updateByPrimaryKeySelective(record);
        return new AjaxResult(row);
    }

    @RequestMapping(value = "updateTableInfo")
    public AjaxResult updateTableInfo(
            @RequestParam(value = "data", required = false) String data) {
        TableInfo record = JsonUtil.fromJson(data, TableInfo.class);
        record.setCreateDate(null);
        record.setMakeBillMan(null);
        record.setModifyDate(getDateNow());
        record.setModifier(getUserName());
        record.setDeleteFlag(false);
        int row = service.updateByPrimaryKeySelective(record);
        return new AjaxResult(row);
    }

    @RequestMapping(value = "listTableInfo")
    public AjaxResult listTableInfo(
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "orderByName", required = false) String orderByName,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        TableInfoExample example = new TableInfoExample();
        example.setPageIndex(pageIndex);
        example.setPageSize(pageSize);
        if (StringUtils.isNotBlank(orderByName)) {
            example.setOrderByClause(orderByName);
        }
        TableInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo(false);
        List<TableInfo> list = service.listByExample(example);
        Pagination<TableInfo> pList = new Pagination<>(example, list, example.getCount());
        return new AjaxResult(pList);
    }

    @RequestMapping(value = "getTableInfo")
    public AjaxResult getTableInfo(@RequestParam(value = "id", required = false) Integer id) {
        TableInfo res = service.getByPrimaryKey(id);
        return new AjaxResult(res);
    }
}