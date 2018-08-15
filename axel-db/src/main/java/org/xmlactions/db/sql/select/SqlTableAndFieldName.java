
package org.xmlactions.db.sql.select;

public class SqlTableAndFieldName {

	private String tableName;
    private String fieldName;

	public SqlTableAndFieldName(String tableName, String fieldName)
	{
		this.setTableName(tableName);
        this.setFieldName(fieldName);
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getTableName()
	{
		return tableName;
	}

}
