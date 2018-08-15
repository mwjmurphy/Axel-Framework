package org.xmlactions.db;

import org.xmlactions.db.actions.Binary;
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

public enum DBDataType {
	bool,
	clob,
	date,		// yyyy-MM-dd
	datetime,	// yyyy-MM-dd hh:mm:ss
	time,		// hh:mm:ss
	integer,
	object,
	text,
	;
	
	
	public static DBDataType getDBDataType(Object objectField) {
		
		if (objectField instanceof Date) {
			return date;
		} else if(objectField instanceof DateTime ||
			objectField instanceof TimeStamp) {
			return datetime;
		} else if(objectField instanceof TimeOfDay) {
				return time;
		} else if (objectField instanceof FK ||
				   objectField instanceof PK ||
				   objectField instanceof Int) {
			return integer;
		} else if (objectField instanceof Text ||
				   objectField instanceof TextArea ||
				   objectField instanceof Password) {
			return text;
		} else if (objectField instanceof Binary) {
			return bool;
		} else {
			return object;
		}
		//objectField instanceof Image ||
		//objectField instanceof Link ||
		//objectField instanceof Select ||
	}
	
}
