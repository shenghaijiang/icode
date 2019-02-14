package cn.xtits.icode.controller;

import cn.xtits.icode.controller.BaseController;
import cn.xtits.icode.core.Run;
import cn.xtits.icode.dto.TableModel;
import cn.xtits.icode.entity.Project;
import cn.xtits.icode.entity.ProjectExample;
import cn.xtits.icode.entity.TableInfo;
import cn.xtits.icode.query.Pagination;
import cn.xtits.icode.service.ProjectService;
import cn.xtits.xtf.common.utils.JsonUtil;
import cn.xtits.xtf.common.web.AjaxResult;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @fileName: Project}Controller.java
 * @author: Dan
 * @createDate:
 * @description:
 */
@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService service;

    @RequestMapping(value = "insertProject")
    public AjaxResult insertProject(
            @RequestParam(value = "data", required = false) String data) {
        Project record = JsonUtil.fromJson(data, Project.class);
        Date dt = getDateNow();
        record.setCreateDate(dt);
        record.setMakeBillMan(getUserName());
        record.setModifier(getUserName());
        record.setModifyDate(dt);
        record.setDeleteFlag(false);
        int row = service.insert(record);
        return new AjaxResult(row);
    }

    @RequestMapping(value = "deleteProject")
    public AjaxResult deleteProject(
            @RequestParam(value = "id", required = false) int id) {
        Project record = new Project();
        record.setId(id);
        record.setDeleteFlag(true);
        record.setModifier(getUserName());
        record.setModifyDate(getDateNow());
        int row = service.updateByPrimaryKeySelective(record);
        return new AjaxResult(row);
    }

    @RequestMapping(value = "updateProject")
    public AjaxResult updateProject(
            @RequestParam(value = "data", required = false) String data) {
        Project record = JsonUtil.fromJson(data, Project.class);
        record.setCreateDate(null);
        record.setMakeBillMan(null);
        record.setModifyDate(getDateNow());
        record.setModifier(getUserName());
        record.setDeleteFlag(false);
        int row = service.updateByPrimaryKeySelective(record);
        return new AjaxResult(row);
    }

    @RequestMapping(value = "listProject")
    public AjaxResult listProject(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "packageName", required = false) String packageName,
            @RequestParam(value = "sqlType", required = false) Integer sqlType,
            @RequestParam(value = "dbHost", required = false) String dbHost,
            @RequestParam(value = "dbName", required = false) String dbName,
            @RequestParam(value = "dbUser", required = false) String dbUser,
            @RequestParam(value = "dbPassword", required = false) String dbPassword,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        ProjectExample example = new ProjectExample();
        example.setPageIndex(pageIndex);
        example.setPageSize(pageSize);
        if (StringUtils.isNotBlank(orderBy)) {
            example.setOrderByClause(orderBy);
        }
        ProjectExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo(false);
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike(code);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(name);
        }
        if (StringUtils.isNotBlank(packageName)) {
            criteria.andPackageNameLike(packageName);
        }
        if (sqlType != null && sqlType > 0) {
            criteria.andSqlTypeEqualTo(sqlType);
        }

        if (StringUtils.isNotBlank(dbHost)) {
            criteria.andDbHostLike(dbHost);
        }
        if (StringUtils.isNotBlank(dbName)) {
            criteria.andDbNameLike(dbName);
        }
        if (StringUtils.isNotBlank(dbUser)) {
            criteria.andDbUserLike(dbUser);
        }
        if (StringUtils.isNotBlank(dbPassword)) {
            criteria.andDbPasswordLike(dbPassword);
        }
        if (startDate != null && !"".equals(startDate.trim())) {
            criteria.andCreateDateGreaterThanOrEqualTo(DateTime.parse(startDate, format).toDate());
        }
        if (endDate != null && !"".equals(endDate.trim())) {

            criteria.andCreateDateLessThanOrEqualTo(DateTime.parse(endDate, format).toDate());
        }

        List<Project> list = service.listByExample(example);
        Pagination<Project> pList = new Pagination<>(example, list, example.getCount());
        return new AjaxResult(pList);
    }

    @RequestMapping(value = "listTable")
    public AjaxResult listTable(
            @RequestParam(value = "projectCode", required = false) String projectCode) {
        List<TableModel> tableModelList = Run.listTable(projectCode);
        Pagination<TableModel> pList = new Pagination<>(tableModelList);
        return new AjaxResult(pList);
    }

    @RequestMapping(value = "getProject")
    public AjaxResult getProject(@RequestParam(value = "id", required = false) Integer id) {
        Project res = service.getByPrimaryKey(id);
        return new AjaxResult(res);
    }
}