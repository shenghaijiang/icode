package cn.xtits.icode.dto;

import java.util.List;

/**
 * 实体类Model
 */
public class TableModel extends TableExtend {

    private String kebabCode;

    private String tableName;

    private String packageName;

    private String tableSchema;

    private String tableJson;

    private String tableComment;

    private String tableAlias;

    private TableModel listTable;

    private TableModel mainTable;

    private List<ColumnModel> columnModelList;

    private List<ColumnModel> columns;


    public String getKebabCode() {
        return kebabCode;
    }

    public void setKebabCode(String kebabCode) {
        this.kebabCode = kebabCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableJson() {
        return tableJson;
    }

    public void setTableJson(String tableJson) {
        this.tableJson = tableJson;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public TableModel getListTable() {
        return listTable;
    }

    public void setListTable(TableModel listTable) {
        this.listTable = listTable;
    }

    public TableModel getMainTable() {
        return mainTable;
    }

    public void setMainTable(TableModel mainTable) {
        this.mainTable = mainTable;
    }

    public List<ColumnModel> getColumnModelList() {
        return columnModelList;
    }

    public void setColumnModelList(List<ColumnModel> columnModelList) {
        this.columnModelList = columnModelList;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }
}
