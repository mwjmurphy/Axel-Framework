
package org.xmlactions.db.mysql;

import org.xmlactions.db.actions.BaseStorageField;
import org.xmlactions.db.actions.Date;
import org.xmlactions.db.actions.DateTime;
import org.xmlactions.db.actions.FK;
import org.xmlactions.db.actions.Image;
import org.xmlactions.db.actions.Int;
import org.xmlactions.db.actions.Link;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Password;
import org.xmlactions.db.actions.Select;
import org.xmlactions.db.actions.Text;
import org.xmlactions.db.actions.TextArea;
import org.xmlactions.db.actions.TimeOfDay;
import org.xmlactions.db.actions.TimeStamp;
import org.xmlactions.db.template.ICreateField;

public class CreateField implements ICreateField
{

	public final static char MYSQL_QUOTE = '`';

	public String createFieldSQL(BaseStorageField field)
	{

		String create = "";
		if (field instanceof PK) {
			create = MYSQL_QUOTE + field.getName() + MYSQL_QUOTE + " INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT";
		} else if (field instanceof FK) {
			create = MYSQL_QUOTE + field.getName() + MYSQL_QUOTE + " INTEGER NOT NULL default 0";
		} else if (field instanceof Text) {
			create = MYSQL_QUOTE + field.getName() + MYSQL_QUOTE + " VARCHAR(" + ((Text) field).getLength() + ")";
			if (((Text) field).isMandatory()) {
				create += " NOT NULL";
			}
		} else if (field instanceof TextArea) {
			create = MYSQL_QUOTE + field.getName() + MYSQL_QUOTE + " VARCHAR(" + ((TextArea) field).getLength() + ")";
			if (((Text) field).isMandatory()) {
				create += " NOT NULL";
			}
		} else if (field instanceof Select) {
			create = MYSQL_QUOTE + field.getName() + MYSQL_QUOTE + " VARCHAR(" + ((Text) field).getLength() + ")";
			if (((Text) field).isMandatory()) {
				create += " NOT NULL";
			}
		} else if (field instanceof Int) {
			create = MYSQL_QUOTE + field.getName() + MYSQL_QUOTE + " INTEGER";
			if (((Int) field).isMandatory()) {
				create += " NOT NULL";
			}
		} else if (field instanceof Image) {
			create = MYSQL_QUOTE + field.getName() + MYSQL_QUOTE + " VARCHAR(" + ((Image) field).getLength() + ")";
			if (((Image) field).isMandatory()) {
				create += " NOT NULL";
			}
		} else if (field instanceof Link) {
			create = MYSQL_QUOTE + field.getName() + MYSQL_QUOTE + " VARCHAR(" + ((Link) field).getLength() + ")";
			if (((Link) field).isMandatory()) {
				create += " NOT NULL";
			}
		} else if (field instanceof Image) {
			create = MYSQL_QUOTE + field.getName() + MYSQL_QUOTE + " VARCHAR(" + ((Image) field).getLength() + ")";
			if (((Image) field).isMandatory()) {
				create += " NOT NULL";
			}
		} else if (field instanceof Password) {
			create = MYSQL_QUOTE + field.getName() + MYSQL_QUOTE + " VARCHAR(" + ((Password) field).getLength() + ")";
			if (((Password) field).isMandatory()) {
				create += " NOT NULL";
			}
		} else if (field instanceof DateTime) {
			create = MYSQL_QUOTE + field.getName() + MYSQL_QUOTE + " DATETIME";
			if (((DateTime) field).isMandatory()) {
				create += " NOT NULL";
			}
		} else if (field instanceof Date) {
			create = MYSQL_QUOTE + field.getName() + MYSQL_QUOTE + " DATE";
			if (((Date) field).isMandatory()) {
				create += " NOT NULL";
			}
		} else if (field instanceof TimeStamp) {
			create = MYSQL_QUOTE + field.getName() + MYSQL_QUOTE + " TIMESTAMP";
			if (((TimeStamp) field).isMandatory()) {
				create += " NOT NULL DEFAULT CURRENT_TIMESTAMP";
				// if we want to update the timestamp every time there is an
				// update to the table we need to append "ON UPDATE CURRENT_TIMESTAMP"; to the creation instruction
			}
		} else if (field instanceof TimeOfDay) {
			create = MYSQL_QUOTE + field.getName() + MYSQL_QUOTE + " TIMEOFDAY";
			if (((TimeStamp) field).isMandatory()) {
				create += " NOT NULL DEFAULT CURRENT_TIMESTAMP";
				// if we want to update the timestamp every time there is an
				// update to the table we need to append "ON UPDATE CURRENT_TIMESTAMP"; to the creation instruction
			}
		} else {
			throw new IllegalArgumentException("Invalid Field Type [" + field.getClass().getName() + "] Not Processed");
		}
		return create;
	}
}
