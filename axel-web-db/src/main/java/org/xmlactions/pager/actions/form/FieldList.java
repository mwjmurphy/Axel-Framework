
package org.xmlactions.pager.actions.form;


import java.util.ArrayList;
import java.util.List;

import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.db.DBConfigException;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Table;
import org.xmlactions.pager.actions.form.BaseFormAction;



public class FieldList extends BaseFormAction
{

	java.util.List<BaseAction> fields = new ArrayList<BaseAction>();

	/**
	 * Add a field to the list
	 * 
	 * @param field
	 */
	public void setField(BaseAction field)
	{

		this.fields.add(field);
	}

	/**
	 * @return last Field in list or null if list contains no fields
	 */
	public BaseAction getField()
	{

		if (fields.size() > 0) {
			return fields.get(fields.size() - 1);
		}
		return null;
	}

	/**
	 * @return list of fields
	 */
	public java.util.List<BaseAction> getFields()
	{

		return fields;
	}

	/**
	 * Add a add_record_link to the list
	 * 
	 * @param field
	 */
	public void setAdd_record_link(BaseAction field)
	{

		this.fields.add(field);
	}

	/**
	 * @return last add_record_link in list or null if list contains no fields
	 */
	public BaseAction getAdd_record_link()
	{

		if (fields.size() > 0) {
			return fields.get(fields.size() - 1);
		}
		return null;
	}

	/**
	 * Add a delete_record_link to the list
	 * 
	 * @param field
	 */
	public void setDelete_record_link(BaseAction field)
	{

		this.fields.add(field);
	}

	/**
	 * @return last delete_record_link in list or null if list contains no fields
	 */
	public BaseAction getDelete_record_link()
	{

		if (fields.size() > 0) {
			return fields.get(fields.size() - 1);
		}
		return null;
	}

	/**
	 * Add a update_record_link to the list
	 * 
	 * @param field
	 */
	public void setUpdate_record_link(BaseAction field)
	{

		this.fields.add(field);
	}

	/**
	 * @return last update_record_link in list or null if list contains no fields
	 */
	public BaseAction getUpdate_record_link()
	{

		if (fields.size() > 0) {
			return fields.get(fields.size() - 1);
		}
		return null;
	}

	/**
	 * Add a field_code to the list
	 * 
	 * @param field
	 */
	public void setField_code(BaseAction field)
	{

		this.fields.add(field);
	}

	/**
	 * A field_code is used to wrap a code action. It will contain additionalional
	 * information on the header_name, x, y, width height etc.
	 * 
	 * @return last field in list or null if list contains no fields
	 */
	public BaseAction getField_code()
	{
		if (fields.size() > 0) {
			return fields.get(fields.size() - 1);
		}
		return null;
	}


	/**
	 * Add a field_raw to the list
	 * 
	 * @param field
	 */
	public void setField_raw(BaseAction field)
	{

		this.fields.add(field);
	}

	/**
	 * A field_raw is used to add raw text content to a list. It will contain additionalional
	 * information on the header_name, x, y, width height etc.
	 * 
	 * @return last field in list or null if list contains no fields
	 */
	public BaseAction getField_raw()
	{
		if (fields.size() > 0) {
			return fields.get(fields.size() - 1);
		}
		return null;
	}


	/**
	 * Add a field_hide to the list
	 * 
	 * @param field_hide
	 */
	public void setField_hide(BaseAction fieldHide)
	{

		this.fields.add(fieldHide);
	}

	/**
	 * @return last field_hide in list or null if list contains no fields
	 */
	public BaseAction getField_hide()
	{

		if (fields.size() > 0) {
			return fields.get(fields.size() - 1);
		}
		return null;
	}

	/**
	 * Add a popup to the list
	 * 
	 * @param field
	 */
	public void setPopup(BaseAction field)
	{
		this.fields.add(field);
	}

	/**
	 * @return last popup in list or null if list contains no fields
	 */
	public BaseAction getPopup()
	{
		if (fields.size() > 0) {
			return fields.get(fields.size() - 1);
		}
		return null;
	}

	/**
	 * Gets a list of BaseStorageFields from either the fields or if fields is empty from the table.
	 * 
	 * @param database
	 * @param table
	 * @return a List of BaseStorageField
	 * @throws DBConfigException
	 */
	public List<CommonStorageField> getStorageFields(Database database, Table table) throws DBConfigException
	{

		List<CommonStorageField> fieldList = new ArrayList<CommonStorageField>();
		if (fields.size() > 0) {
			CommonStorageField storageField;
			for (BaseAction baseAction : fields) {
				if (baseAction instanceof Field) {
					Field field = (Field) baseAction;
					if (Table.isTableAndFieldName(field.getName())) {
						storageField = database.getStorageField(field.getName());
					} else {
						storageField = table.getFieldFromTableAndFieldName(Table.buildTableAndFieldName(
								table.getName(), field.getName()));
					}
					fieldList.add(storageField);
				}
			}
		} else {
			for (CommonStorageField storageField : table.getFields()) {
				fieldList.add(storageField);
			}
		}
		return fieldList;
	}

	public String execute(IExecContext arg0) throws Exception
	{

		// TODO Auto-generated method stub
		return null;
	}
}
