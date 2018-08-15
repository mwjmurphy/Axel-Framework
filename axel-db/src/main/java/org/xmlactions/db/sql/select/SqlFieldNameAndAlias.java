
package org.xmlactions.db.sql.select;

public class SqlFieldNameAndAlias {
    private String fieldName;
    private String fieldAlias;

    public SqlFieldNameAndAlias(String fieldName) {
        this.setFieldName(fieldName);
        this.setFieldAlias(fieldName);
    }

    public SqlFieldNameAndAlias(String fieldName, String aliasName) {
        this.setFieldName(fieldName);
        this.setFieldAlias(aliasName);
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldAlias(String fieldAlias) {
        this.fieldAlias = fieldAlias;
    }

    public String getFieldAlias() {
        return fieldAlias;
    }

}
