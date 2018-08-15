
package org.xmlactions.db.template;


import java.sql.SQLException;

import org.xmlactions.db.actions.BaseStorageField;



public interface ICreateField
{

	public String createFieldSQL(BaseStorageField field) throws SQLException;

}
