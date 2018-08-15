
package org.xmlactions.pager.actions.form;


import java.util.ArrayList;



import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.XMLAttribute;
import org.xmlactions.db.actions.BaseStorageField;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.pager.drawing.html.DrawDBHTMLHelper;


public class FormUtils
{

	private static final Logger log = LoggerFactory.getLogger(FormUtils.class);

	/**
	 * Validates and gets the StorageConfig from the ExecContext
	 * 
	 * @param storageConfigRef
	 *            the reference name for the storageConfig in the execContext
	 * @param execContext
	 *            the map of key value pairs
	 * @return the found StorageConfig
	 */
	public static StorageConfig getStorageConfig(String storageConfigRef, IExecContext execContext)
	{

		Validate.notEmpty(storageConfigRef,
				"The storage_config_ref value is not set, this must be configured in the action");
		StorageConfig storageConfig = (StorageConfig) execContext.get(storageConfigRef);
		Validate.notNull(storageConfig, "StorageConfig [" + storageConfigRef + "] not found in ExecContext");
		return storageConfig;
	}

	/**
	 * Builds an array of field names from the fields list
	 * 
	 * @param tableName
	 *            that the fields belong to
	 * @param fields
	 *            the list of fields
	 * @return
	 */
    public static java.util.List<SqlField> buildFieldNames(Table table, java.util.List<BaseAction> fields)
	{
        return buildTableAndFieldNamesAsList(table, fields);
	}

	/**
	 * Builds an array of field names from the fields list
	 * 
	 * @param tableName
	 *            that the fields belong to
	 * @param fields
	 *            the list of fields
	 * @return
	 */
    public static java.util.List<SqlField> buildTableAndFieldNamesAsList(Table table,
			java.util.List<BaseAction> fields)
	{

        java.util.List<SqlField> sqlFields = new ArrayList<SqlField>();
		int i = 0;
		for (BaseAction baseAction : fields) {
			if (baseAction instanceof Field) {
				Field field = (Field) baseAction;
				SqlField sqlField;
				if (StringUtils.isNotBlank(field.getSql())) {
	                sqlField = new SqlField(field.getName());
	                sqlField.setSql(field.getSql());
				} else {
					boolean applyTableAlias = false;
	                CommonStorageField dbField;
					String name = null, aliasName;
					if (Table.isTableAndFieldName(field.getName())) {
						aliasName = Table.buildTableAndFieldName(Table.getTableName(field.getName()), Table.getFieldName(field.getName()));
	                    Table fieldTable = ((Database) table.getParent()).getTable(Table.getTableName(field.getName()));
	                    dbField = fieldTable.getField(Table.getFieldName(field.getName()));
						name = Table.buildTableAndFieldName(Table.getTableName(field.getName()), dbField.getName());
	                    applyTableAlias = true;
					} else {
	                    aliasName = Table.buildTableAndFieldName(table.getAlias(), field.getName());
	                    dbField = table.getField(field.getName());
						name = Table.buildTableAndFieldName(table.getAlias(), field.getName());
					}
					// must used this mechanism for creating the SqlField so it considers a foreign_table_name
	                sqlField = new SqlField(name, aliasName, dbField.getFunction_ref(), dbField, dbField.getQuery_sql());
				}
                // SqlField sqlField = new SqlField(dbField, applyTableAlias);
                sqlFields.add(sqlField);
			}
			if (baseAction instanceof FieldHide) {
				FieldHide field = (FieldHide) baseAction;
                CommonStorageField dbField;
				String name;
				if (Table.isTableAndFieldName(field.getName())) {
					name = Table.buildTableAndFieldName(Table.getTableName(field.getName()), Table.getFieldName(field
							.getName()));
                    Table fieldTable = ((Database) table.getParent()).getTable(Table.getTableName(field.getName()));
                    dbField = fieldTable.getField(Table.getFieldName(field.getName()));
				} else {
                    name = Table.buildTableAndFieldName(table.getAlias(), field.getName());
                    dbField = table.getField(field.getName());
				}
                SqlField sqlField = new SqlField(name, name, dbField.getFunction_ref(), dbField, dbField.getQuery_sql());
                sqlFields.add(sqlField);
			}
		}
        return sqlFields;
	}

	/**
	 * Finds the (drawing)field from the table that matches the attr.getKey()
	 * 
	 * @param table
	 *            the table containing the fields
	 * @param attr
	 *            the XMLAttribute containing the key to the field we want
	 * @return the field or null if not found
	 */
	public static BaseStorageField findMatchingField(Table table, XMLAttribute attr)
	{

		return findMatchingField(table, DrawDBHTMLHelper.getFieldName(attr.getKey()));
	}

	/**
	 * Finds the (drawing)field from the table that matches the attr.getKey()
	 * 
	 * @param table
	 *            the table containing the fields
	 * @param fieldName
	 *            the name of the field we want from the table.
	 * @return the field or null if not found
	 */
	public static BaseStorageField findMatchingField(Table table, String fieldName)
	{

		int index = fieldName.indexOf('.');
		String name;
		if (index > 0) {
			name = fieldName.substring(index + 1);
		} else {
			name = fieldName;
		}
		BaseStorageField foundField = null;
		if (name != null) {
			for (BaseStorageField field : table.getFields()) {
				if (field.getName().equals(name)) {
					foundField = field;
					break;
				}
			}
		}
		if (foundField == null) {
			throw new IllegalArgumentException("No field found for [" + fieldName + "] in table [" + table.getName()
					+ "]");
		}
		return foundField;
	}

	/**
	 * Finds the (drawing)field from the table that matches the attr.getKey()
	 * 
	 * @param table
	 *            the table containing the fields
	 * @param fieldName
	 *            the name of the field we want from the table.
	 * @return the field or null if not found
	 */
	public static BaseStorageField findMatchingField(Database database, String tableName, String fieldName)
	{

		Table table = database.getTable(tableName);
		BaseStorageField foundField = null;
		for (BaseStorageField field : table.getFields()) {
			if (field.getName().equals(fieldName)) {
				foundField = field;
				break;
			}
		}
		if (foundField == null) {
			throw new IllegalArgumentException("No field found for [" + fieldName + "] in table [" + tableName + "]");
		}
		return foundField;
	}

	/**
	 * Finds the (drawing)field from the table that matches the attr.getKey()
	 * 
	 * @param table
	 *            the table containing the fields
	 * @param key
	 *            the key to the field we want
	 * @return the field or null if not found
	 */
	public static Field findMatchingDrawingField(java.util.List<BaseAction> fields, String key)
	{

		Field foundField = null;
		for (BaseAction baseAction : fields) {
			if (baseAction instanceof Field) {
				Field field = (Field) baseAction;
				if (field.getName().equals(key)) {
					foundField = field;
					break;
				}
			}
		}
		if (foundField == null) {
			String fieldName = DrawDBHTMLHelper.getFieldName(key);
			if (fieldName != null) {
				for (BaseAction baseAction : fields) {
					if (baseAction instanceof Field) {
						Field field = (Field) baseAction;
						if (field.getName().equals(fieldName)) {
							foundField = field;
							break;
						}
					}
				}
			}
		}
		return foundField;
	}

	/**
	 * Builds a URI parameter string for the PK Key Value. i.e. tb_name.id=1
	 * 
	 * @param attrs
	 *            list of attributes returned for one query row
	 * @param table
	 *            that contains the list of fields, we want the PK.
	 * @return
	 */
	public static String getPKKeyValue(java.util.List<XMLAttribute> attrs, Table table)
	{

		for (XMLAttribute attr : attrs) {
			BaseStorageField field = findMatchingField(table, attr);
			if (field != null && field instanceof PK) {
				return Table.buildTableAndFieldName(table.getName(), field.getName()) + "=" + attr.getValue();
			}
		}
		throw new IllegalArgumentException("No Primary Key found in Field list for table [" + table.getName() + "]");
	}
}
