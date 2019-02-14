package cn.xtits.icode.core;

import cn.xtits.icode.dto.*;
import cn.xtits.xtf.common.db.DbConn;
import cn.xtits.xtf.common.utils.JsonUtil;
import com.google.common.base.CaseFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Run {

    private static String url;

    private static String username;

    private static String password;

    @Value("${spring.datasource.url}")
    public void setUrl(String url) {
        Run.url = url;
    }

    @Value("${spring.datasource.username}")
    public void setUsername(String username) {
        Run.username = username;
    }

    @Value("${spring.datasource.password}")
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 项目路径
     */
    public static String PROJECT_PATH;

    /**
     * freemaker 配置
     */
    private static Configuration cfg;

    private static String getTableSql = "SELECT * FROM information_schema.`TABLES` t WHERE t.TABLE_SCHEMA=''{0}'' ORDER BY t.TABLE_NAME";

    private static String getColumn = "SELECT * FROM information_schema.`COLUMNS` t WHERE t.TABLE_SCHEMA = ''{0}'' AND t.TABLE_NAME = LOWER(''{1}'')";

    private static String getProject = "SELECT * FROM project where code =''{0}''";

    private static DbConn getConn() throws Exception {
        ClassLoader loder = Thread.currentThread().getContextClassLoader();
        String dbUrl = url;
        String driverClassName = "com.mysql.jdbc.Driver";
        //String username = conn.getUser();
        //String password = conn.getPassword();
        String connStr = MessageFormat.format("url={0}|username={1}|password={2}|driverClassName={3}|maxWait=5000", dbUrl, username, password, driverClassName);
        DbConn myconn = DbConn.createConn(connStr);
        return myconn;
    }

    private static DbConn getProjectConn(Project project) throws Exception {
        String projectDbUrl = "jdbc:mysql://" + project.getDbHost() + "/" + project.getDbName() + "?ssl=false";
        String driverClassName = "com.mysql.jdbc.Driver";
        String username = project.getDbUser();
        String password = project.getDbPassword();
        String connStr = MessageFormat.format("url={0}|username={1}|password={2}|driverClassName={3}|maxWait=5000", projectDbUrl, username, password, driverClassName);
        DbConn myconn = DbConn.createConn(connStr);
        return myconn;
    }

    public static Project initProject(String projectName) {
        Project project = new Project();
        String sql = MessageFormat.format(getProject, projectName);

        List<Map<String, Object>> list;
        try (DbConn conn = getConn()) {
            list = conn.executeList(sql);

            for (Map<String, Object> map : list) {
                project.setCode(map.get("Code").toString());
                project.setName(map.get("Name").toString());
                project.setPackageName(map.get("PackageName").toString());
                project.setDbHost(map.get("DbHost").toString());
                project.setDbName(map.get("DbName").toString());
                project.setDbUser(map.get("DbUser").toString());
                project.setDbPassword(map.get("DbPassword").toString());
                break;
            }
            return project;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<TableModel> listTable(String projectName) {

        Project project = initProject(projectName);
        List<TableModel> tableList = new ArrayList<>();
        List<Map<String, Object>> table = getTable(project);
        for (Map<String, Object> item1 : table) {
            TableModel model = new TableModel();
            model.setPackageName(project.getPackageName());
            model.setTableSchema(item1.get("TABLE_SCHEMA").toString());
            model.setTableName(item1.get("TABLE_NAME").toString());
            model.setName(item1.get("TABLE_NAME").toString());
            //model.setTableComment(item1.get("TABLE_COMMENT").toString());
            model.setTableJson(item1.get("TABLE_COMMENT").toString());
            tableList.add(model);
        }
        for (TableModel tableModel : tableList) {

            TableExtend tableExtend;
            try {
                tableExtend = JsonUtil.fromJson(tableModel.getTableJson(), TableExtend.class);
                if (tableExtend == null) {
                    tableExtend = new TableExtend();
                    tableExtend.setGenerated(true);
                    tableExtend.setRemark(tableModel.getTableJson());
                    tableExtend.setCode(tableModel.getTableJson());
                    tableExtend.setName(tableModel.getTableJson());
                }
            } catch (Exception e) {
                tableExtend = new TableExtend();
                tableExtend.setGenerated(true);
                tableExtend.setRemark(tableModel.getTableJson());
                tableExtend.setCode(tableModel.getTableJson());
                tableExtend.setName(tableModel.getTableJson());
            }

            tableModel.setTableAlias(tableExtend.getCode() == null ? "" : tableExtend.getCode());
            tableModel.setKebabCode(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, tableModel.getTableAlias()));
            tableModel.setGenerated(tableExtend.isGenerated());
            tableModel.setListName(tableExtend.getListName() == null ? "" : tableExtend.getListName());
            tableModel.setMainName(tableExtend.getMainName() == null ? "" : tableExtend.getMainName());
            tableModel.setTableComment(tableExtend.getRemark() == null ? "" : tableExtend.getRemark());
            tableModel.setRemark(tableExtend.getRemark() == null ? "" : tableExtend.getRemark());
            tableModel.setName(tableExtend.getName() == null ? "" : tableExtend.getName());
            tableModel.setCode(tableExtend.getCode() == null ? "" : tableExtend.getCode());
            tableModel.setRemark(tableExtend.getRemark() == null ? "" : tableExtend.getRemark());
        }
        return tableList;
    }

    public static Project getProject(String projectName) {

        Project project = initProject(projectName);
        List<TableModel> tableList = new ArrayList<>();
        List<Map<String, Object>> table = getTable(project);
        for (Map<String, Object> item1 : table) {
            TableModel model = new TableModel();
            model.setPackageName(project.getPackageName());
            model.setTableSchema(item1.get("TABLE_SCHEMA").toString());
            model.setTableName(item1.get("TABLE_NAME").toString());
            model.setName(item1.get("TABLE_NAME").toString());
            //model.setTableComment(item1.get("TABLE_COMMENT").toString());
            model.setTableJson(item1.get("TABLE_COMMENT").toString());
            List<ColumnModel> columnList = new ArrayList<>();
            List<Map<String, Object>> column = getColumn(project, model.getName());
            for (Map<String, Object> map : column) {
                ColumnModel columnModel = new ColumnModel();
                columnModel.setTableSchema(map.get("TABLE_SCHEMA").toString());
                columnModel.setTableName(map.get("TABLE_NAME").toString());
                columnModel.setCode(map.get("COLUMN_NAME").toString());
                columnModel.setColumnJson(map.get("COLUMN_COMMENT").toString());
                columnModel.setIsNullable(map.get("IS_NULLABLE").toString());
                columnModel.setDataType(map.get("DATA_TYPE").toString());
                columnModel.setCharacterMaximumLength(map.get("CHARACTER_MAXIMUM_LENGTH") == null ? "0" : map.get("CHARACTER_MAXIMUM_LENGTH").toString());
                columnModel.setColumnType(map.get("COLUMN_TYPE").toString());
                columnModel.setColumnKey(map.get("COLUMN_KEY").toString());
                ColumnExtend columnExtend;
                try {
                    columnExtend = JsonUtil.fromJson(columnModel.getColumnJson(), ColumnExtend.class);
                    if (columnExtend == null) {
                        columnExtend = new ColumnExtend();
                        columnExtend.setName(columnModel.getColumnJson());
                        columnExtend.setRemark(columnModel.getColumnJson());
                        columnExtend.setRequired(false);
                        columnExtend.setGenerated(true);
                    }
                } catch (Exception e) {
                    columnExtend = new ColumnExtend();
                    columnExtend.setName(columnModel.getColumnJson());
                    columnExtend.setRemark(columnModel.getColumnJson());
                    columnExtend.setRequired(false);
                    columnExtend.setGenerated(true);
                }
                columnModel.setName(columnExtend.getName() == null ? "" : columnExtend.getName());
                columnModel.setRemark(columnExtend.getRemark() == null ? "" : columnExtend.getRemark());
                columnModel.setGenerated(columnExtend.isGenerated());
                columnModel.setRequired(columnExtend.isRequired());
                columnList.add(columnModel);
            }
            model.setColumnModelList(columnList);
            model.setColumns(columnList);
            tableList.add(model);
        }
        for (TableModel tableModel : tableList) {

            TableExtend tableExtend;
            try {
                tableExtend = JsonUtil.fromJson(tableModel.getTableJson(), TableExtend.class);
                if (tableExtend == null) {
                    tableExtend = new TableExtend();
                    tableExtend.setGenerated(true);
                    tableExtend.setRemark(tableModel.getTableJson());
                    tableExtend.setCode(tableModel.getTableJson());
                    tableExtend.setName(tableModel.getTableJson());
                }
            } catch (Exception e) {
                tableExtend = new TableExtend();
                tableExtend.setGenerated(true);
                tableExtend.setRemark(tableModel.getTableJson());
                tableExtend.setCode(tableModel.getTableJson());
                tableExtend.setName(tableModel.getTableJson());
            }

            tableModel.setTableAlias(tableExtend.getCode() == null ? "" : tableExtend.getCode());
            tableModel.setKebabCode(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, tableModel.getTableAlias()));
            tableModel.setGenerated(tableExtend.isGenerated());
            tableModel.setListName(tableExtend.getListName() == null ? "" : tableExtend.getListName());
            tableModel.setMainName(tableExtend.getMainName() == null ? "" : tableExtend.getMainName());
            tableModel.setTableComment(tableExtend.getRemark() == null ? "" : tableExtend.getRemark());
            tableModel.setRemark(tableExtend.getRemark() == null ? "" : tableExtend.getRemark());
            tableModel.setName(tableExtend.getName() == null ? "" : tableExtend.getName());
            tableModel.setCode(tableExtend.getCode() == null ? "" : tableExtend.getCode());
            tableModel.setRemark(tableExtend.getRemark() == null ? "" : tableExtend.getRemark());
        }

        for (TableModel item1 : tableList) {
            if (item1.getListName() != "") {
                for (TableModel item2 : tableList) {
                    if (item1.getListName().equals(item2.getCode())) {
                        item1.setListTable(item2);
                    }
                }
            }
        }
        for (TableModel item1 : tableList) {
            if (item1.getMainName() != "") {
                for (TableModel item2 : tableList) {
                    if (item1.getListName().equals(item2.getCode())) {
                        item1.setMainTable(item2);
                    }
                }
            }
        }

        project.setTables(tableList);
        return project;
    }

    /**
     * 保存到文件
     *
     * @return
     * @throws Exception
     */
    public static void generateFile(Object model, String templatePath, String templateName, String path,
                                    String fileName) throws Exception {

        cfg = ConfigurationHelper
                .getConfiguration(templatePath);
        // 装载model数据
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("model", model);
        // 获得模板对象
        Template template = cfg.getTemplate(templateName,"UTF-8");
        // 创建生成类的存放路径
        path = path.replaceAll("\\.", "/");
        FileUtils.forceMkdir(new File(PROJECT_PATH + File.separator + path));
        File output = new File(PROJECT_PATH + File.separator + path, fileName);
        FileOutputStream fos = new FileOutputStream(output);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw = new BufferedWriter(osw, 1024);
        // 开始创建
        template.process(data, bw);
        fos.close();
        osw.close();
        bw.close();
    }

    public static void generateFile(Object model, String templatePath, String destCode) throws Exception {

        String templateDir = templatePath.substring(0,templatePath.lastIndexOf(File.separator));
        String templateFile = templatePath.substring(templatePath.lastIndexOf(File.separator));
        String  destPath = destCode.substring(0,destCode.lastIndexOf(File.separator));
        cfg = ConfigurationHelper
                .getConfiguration(templateDir);
        // 装载model数据
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("model", model);
        // 获得模板对象
        Template template = cfg.getTemplate(templateFile,"UTF-8");
        // 创建生成类的存放路径
        //dest = dest.replaceAll("\\.", "/");
        FileUtils.forceMkdir(new File(destPath));
        File output = new File(destCode);
        output.createNewFile();
        FileOutputStream fos = new FileOutputStream(output);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw = new BufferedWriter(osw, 1024);
        // 开始创建
        template.process(data, bw);
        fos.close();
        osw.close();
        bw.close();
    }

    //TODO "/"问题
    public static void copyFile(String src, String dest) throws Exception {
        File srcFile = new File(src);
        File destFile = new File(dest);
        FileUtils.copyFile(srcFile, destFile);
    }

    public static List<Map<String, Object>> getColumn(Project project, String tableName) {

        String sql = MessageFormat.format(getColumn, project.getDbName(), tableName);

        List<Map<String, Object>> list;
        try (DbConn conn = getProjectConn(project)) {
            list = conn.executeList(sql);
            if (list == null) {
                list = new ArrayList<>();
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Map<String, Object>> getTable(Project project) {

        String sql = MessageFormat.format(getTableSql, project.getDbName());

        List<Map<String, Object>> list;
        try (DbConn conn = getProjectConn(project)) {
            list = conn.executeList(sql);
            if (list == null) {
                list = new ArrayList<>();
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }


    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

}
