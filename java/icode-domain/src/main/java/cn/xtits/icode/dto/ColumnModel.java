package cn.xtits.icode.dto;

/**
 * 数据库字段模型

 */
public class ColumnModel extends ColumnExtend {

    private String tableSchema ;
    private String tableName;//表明
    private String columnType;//类型
    private String columnKey; //mysql = 'PRI' 为主键
    private String dataType;
    private String characterMaximumLength;
    private String isNullable;// IS_NULLABLE
    private String columnJson;


    public String getColumnJson() {
        return columnJson;
    }

    public void setColumnJson(String columnJson) {
        this.columnJson = columnJson;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }


    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCharacterMaximumLength() {
        return characterMaximumLength;
    }

    public void setCharacterMaximumLength(String characterMaximumLength) {
        this.characterMaximumLength = characterMaximumLength;
    }
}
