
package org.xmlactions.db.actions;


import java.util.ArrayList;
import java.util.List;

import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Date;
import org.xmlactions.db.actions.DateTime;
import org.xmlactions.db.actions.FK;
import org.xmlactions.db.actions.Image;
import org.xmlactions.db.actions.Int;
import org.xmlactions.db.actions.Link;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Password;
import org.xmlactions.db.actions.Text;
import org.xmlactions.db.actions.TextArea;
import org.xmlactions.db.actions.TimeStamp;



/**
 * The field list container.
 * <p>
 * Provides a list of all fields in a table. Used by the XML loader to populate
 * the different field types into a single list<StorageField>
 * </p>
 * 
 * @author mike
 * 
 */
public abstract class Fields extends CommonStorageField
{

	private List<CommonStorageField> fields = new ArrayList<CommonStorageField>();

	public void setField(CommonStorageField field)
	{

		fields.add(field);
	}

	public CommonStorageField getField()
	{

		return fields.get(fields.size() - 1);
	}

	public List<CommonStorageField> getFields()
	{

		return fields;
	}

	public void setPk(PK field)
	{

		setField(field);
	}

	public void setFk(FK field)
	{

		setField(field);
	}

	public void setImage(Image field)
	{

		setField(field);
	}

	public void setLink(Link field)
	{

		setField(field);
	}

	public void setPassword(Password field)
	{

		setField(field);
	}

	public void setText(Text field)
	{

		setField(field);
	}

	public void setTextarea(TextArea field)
	{

		setField(field);
	}

	public void setDatetime(DateTime field)
	{

		setField(field);
	}

	public void setDate(Date field)
	{

		setField(field);
	}

	public void setTimestamp(TimeStamp field)
	{

		setField(field);
	}

	public void setTimeofday(TimeOfDay field)
	{

		setField(field);
	}

	public void setInt(Int field)
	{

		setField(field);
	}

	public void setBinary(Binary binary)
	{

		setField(binary);
	}

	public void setSelect(Select field)
	{

		setField(field);
	}
	public String validate(String value)
	{

		String error = null;
		return buildErrorString(error);
	}
}
