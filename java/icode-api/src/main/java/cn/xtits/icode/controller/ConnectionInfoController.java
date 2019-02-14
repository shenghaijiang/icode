package cn.xtits.icode.controller;

import cn.xtits.icode.entity.ConnectionInfo;
import cn.xtits.icode.entity.ConnectionInfoExample;
import cn.xtits.icode.query.Pagination;
import cn.xtits.icode.service.ConnectionInfoService;
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
 * @fileName: ConnectionInfo}Controller.java
 * @author: Dan
 * @createDate:
 * @description:
 */
@RestController
@RequestMapping("/connectionInfo")
public class ConnectionInfoController extends BaseController {

    @Autowired
    private ConnectionInfoService service;

    @RequestMapping(value = "insertConnectionInfo")
    public AjaxResult insertConnectionInfo(
            @RequestParam(value = "data", required = false) String data) {
        ConnectionInfo record = JsonUtil.fromJson(data, ConnectionInfo.class);
        Date dt = getDateNow();
        record.setCreateDate(dt);
        record.setMakeBillMan(getUserName());
        record.setModifier(getUserName());
        record.setModifyDate(dt);
        record.setDeleteFlag(false);
        int row = service.insert(record);
        return new AjaxResult(row);
    }

    @RequestMapping(value = "deleteConnectionInfo")
    public AjaxResult deleteConnectionInfo(
            @RequestParam(value = "id", required = false) int id) {
        ConnectionInfo record = new ConnectionInfo();
        record.setId(id);
        record.setDeleteFlag(true);
        record.setModifier(getUserName());
        record.setModifyDate(getDateNow());
        int row = service.updateByPrimaryKeySelective(record);
        return new AjaxResult(row);
    }

    @RequestMapping(value = "updateConnectionInfo")
    public AjaxResult updateConnectionInfo(
            @RequestParam(value = "data", required = false) String data) {
        ConnectionInfo record = JsonUtil.fromJson(data, ConnectionInfo.class);
        record.setCreateDate(null);
        record.setMakeBillMan(null);
        record.setModifyDate(getDateNow());
        record.setModifier(getUserName());
        record.setDeleteFlag(false);
        int row = service.updateByPrimaryKeySelective(record);
        return new AjaxResult(row);
    }

    @RequestMapping(value = "listConnectionInfo")
    public AjaxResult listConnectionInfo(
            @RequestParam(value = "host", required = false) String host,
            @RequestParam(value = "user", required = false) String user,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "orderByName", required = false) String orderByName,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        ConnectionInfoExample example = new ConnectionInfoExample();
        example.setPageIndex(pageIndex);
        example.setPageSize(pageSize);
        ConnectionInfoExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(orderByName)) {
            example.setOrderByClause(orderByName);
        }
        if (StringUtils.isNotBlank(host)) {
            criteria.andHostLike(host);
        }
        if (StringUtils.isNotBlank(user)) {
            criteria.andUserLike(user);
        }
        if (StringUtils.isNotBlank(password)) {
            criteria.andPasswordLike(password);
        }

        List<ConnectionInfo> list = service.listByExample(example);
        Pagination<ConnectionInfo> pList = new Pagination<>(example, list, example.getCount());
        return new AjaxResult(pList);
    }

    @RequestMapping(value = "getConnectionInfo")
    public AjaxResult getConnectionInfo(@RequestParam(value = "id", required = false) Integer id) {
        ConnectionInfo res = service.getByPrimaryKey(id);
        return new AjaxResult(res);
    }
}