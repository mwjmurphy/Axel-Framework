package org.xmlactions.pager.actions.form;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.common.util.RioStringUtils;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.DBConfigException;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.exception.DBSQLException;
import org.xmlactions.db.sql.BuildSelect;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.templates.HtmlSelect;
import org.xmlactions.pager.drawing.IDrawField;
import org.xmlactions.pager.drawing.html.DrawHtmlField;
import org.xmlactions.web.PagerWebConst;


public abstract class SelectForm extends CommonFormFields {
	
	private static final Logger logger = LoggerFactory.getLogger(SelectForm.class);
	
	private IExecContext execContext;

	private StorageConfig storageConfig;

	private Database database;

	private Table table;
	
	
	public static HtmlSelect buildSelectionPresentation(IExecContext execContext, CommonStorageField storageField,
					String labelPosition, String value, Theme theme, XMLObject xo, 
					String idAttributeName, String valueAttributeName, String selectionIdentifier, String selectedValue)
	{
		String [] ids = new String[xo.getChildCount()+1];
		String [] values = new String [xo.getChildCount()+1];
		String [] tooltips = new String [xo.getChildCount()+1];
        ids[0] = "";
        values[0] = "";
		for (int index = 1 ; index < ids.length; index++) {
			XMLObject child = xo.getChild(index-1);
            if (StringUtils.isEmpty(valueAttributeName)) {
                if (child.getAttributeCount() > 2) {
                    ids[index] = "" + child.getAttributeAsString(0);
                    values[index] = "" + child.getAttributeAsString(1);
                    tooltips[index] = "" + child.getAttributeAsString(2);
                } else if (child.getAttributeCount() > 1) {
                	ids[index] = "" + child.getAttributeAsString(0);
                	values[index] = "" + child.getAttributeAsString(1);
                	tooltips[index] = "";
                } else if (child.getAttributeCount() > 0) {
                    ids[index] = "" + child.getAttributeAsString(0);
                    values[index] = "" + child.getAttributeAsString(0);
                    tooltips[index] = "";
                }

            } else {
                values[index] = "" + child.getAttributeValue(valueAttributeName);
                ids[index] = "" + child.getAttributeValue(idAttributeName);
            }
			logger.debug("child[" + index + "[" + values[index] + "]]:" + child.mapXMLObject2XML(child));
		}

		HtmlSelect htmlSelect = null;
		if (storageField instanceof IDrawField) {
			htmlSelect = DrawHtmlField.buildSelect(theme, storageField.getTooltip(execContext),selectionIdentifier, ids, values, tooltips, selectedValue);
		} else {
			throw new IllegalArgumentException("Unknown Storage Field Type [" + storageField.getClass().getName()
					+ "], unable to build presentation.");
		}
		return htmlSelect;
	}
	
    protected HtmlSelect buildSearchPresentationFromField(IExecContext execContext, Connection connection,
            Storage storage, Field field, Theme theme, String label_position, String parentTableAndPkName)
            throws DBConfigException, SQLException, DBSQLException {
		CommonStorageField storageField;
		if (Table.isTableAndFieldName(field.getName())) {
			storageField = database.getStorageField(field.getName());
		} else {
			storageField = table.getFieldFromTableAndFieldName(Table.buildTableAndFieldName(getTable_name(),
					field.getName()));
		}
		String keyPreviousValue = PagerWebConst.buildRequestKey(field.getName());
		keyPreviousValue = RioStringUtils.convertToString(execContext.get(keyPreviousValue));

		String selectIdentifier; 
		String idAttributeName, valueAttributeName;

		java.util.List<BaseAction> fields = new ArrayList<BaseAction>();
		fields.add(field);
        java.util.List<SqlField> sqlFields = FormUtils.buildTableAndFieldNamesAsList(table, fields);
		
		if (Table.isTableAndFieldName(field.getName())) {
			String tableName = Table.getTableName(field.getName());
			Table table = database.getTable(tableName);
			PK pk = table.getPk();
			if (pk == null) {
				throw new IllegalArgumentException("Table [" + tableName + "] does not have a PK. A PK is required when using the Select option from a Search using the field ["+field.getName()+"]" );
			}
			selectIdentifier = Table.buildTableAndFieldName(tableName, pk.getName());
            sqlFields.add(new SqlField(Table.buildTableAndFieldName(tableName, pk.getName())));
            valueAttributeName = tableName + Table.TABLE_FIELD_SEPERATOR + Table.getFieldName(field.getName());
            idAttributeName = tableName + Table.TABLE_FIELD_SEPERATOR + pk.getName();
		} else {
			PK pk = table.getPk();
			if (pk == null) {
				throw new IllegalArgumentException("Table [" + table.getName() + "] does not have a PK. A PK is required when using the Select option from a Search using the field ["+field.getName()+"]" );
			}
			selectIdentifier = Table.buildTableAndFieldName(table.getName(), pk.getName());
            sqlFields.add(new SqlField(Table.buildTableAndFieldName(table.getName(), pk.getName())));
            valueAttributeName = table.getName() + Table.TABLE_FIELD_SEPERATOR + field.getName();
            idAttributeName = table.getName() + Table.TABLE_FIELD_SEPERATOR + pk.getName();
		}
		if (StringUtils.isNotEmpty(parentTableAndPkName)) {
            sqlFields.add(0, new SqlField(parentTableAndPkName));
		}
		String selectedValue = PagerWebConst.buildRequestKey(selectIdentifier);
		selectedValue = RioStringUtils.convertToString(execContext.get(selectedValue));
		SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage,
				storageConfig.getDatabaseName(),
				getTable_name(),
                sqlFields, storageConfig.getDbSpecificName());
		if (StringUtils.isNotEmpty(parentTableAndPkName)) {
            sqlFields.add(0, new SqlField(parentTableAndPkName));
			String key = PagerWebConst.buildRequestKey(parentTableAndPkName);
			String value = RioStringUtils.convertToString(execContext.get(key));
			if (StringUtils.isNotEmpty(value)) {
				sqlSelectInputs.addWhereClause(parentTableAndPkName + "=" + value);
			}
		}
		//if (StringUtils.isNotEmpty(whereClause)) {
		//	sqlSelectInputs.addWhereClause(whereClause);
		//}
        ISqlSelectBuildQuery builder = storageConfig.getSqlBuilder();
        String sqlQuery = builder.buildSelectQuery(execContext, sqlSelectInputs, null);
		logger.debug("sqlQuery:" + sqlQuery);
		DBSQL dbSQL = new DBSQL();
		XMLObject xo = dbSQL.query2XMLObject(connection, sqlQuery, "root", null, null, null);
		logger.debug("\nkeyPreviousValue:" + keyPreviousValue + " sqlQuery:" + sqlQuery + "\nxo:" + xo.mapXMLObject2XML(xo));

		HtmlSelect htmlSelect = buildSelectionPresentation(execContext, storageField, label_position, keyPreviousValue, theme, xo, idAttributeName, valueAttributeName, selectIdentifier, selectIdentifier);
		return htmlSelect;
	}
	
	public void setExecContext(IExecContext execContext) {
		this.execContext = execContext;
	}

	public StorageConfig getStorageConfig() {
		return storageConfig;
	}

	public void setStorageConfig(StorageConfig storageConfig) {
		this.storageConfig = storageConfig;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}


}
