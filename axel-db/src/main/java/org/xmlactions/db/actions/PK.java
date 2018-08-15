
package org.xmlactions.db.actions;



import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.xmlactions.action.config.IExecContext;

public class PK extends CommonStorageField
{

    /**
     * Reference to sql that creates a PK value
     */
    private String pk_ref;
    
    public PK () {
    	this.setEditable(false);
    	this.setUnique(true);
    }

	public String execute(IExecContext execContext) throws Exception
	{

		// TODO Auto-generated method stub
		return null;
	}

	public String toString(int indent)
	{

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append(' ');
		}
		sb.append("PK:" + getName());
		return sb.toString();
	}

	public String validate(String value)
	{

		String error = null;
		return buildErrorString(error);
	}

    /**
     * Reference to sql that creates a PK value
     */
    public void setPk_ref(String pk_ref) {
        this.pk_ref = pk_ref;
    }

    /**
     * Reference to sql that creates a PK value
     */
    public String getPk_ref() {
        return pk_ref;
    }

    /**
     * @returb sql that creates a PK value
     */
    public String getPkCreateSql(Database database, String dbSpecificName) {
        if (StringUtils.isEmpty(getPk_ref())) {
            return null;
        }
        // DbSpecific dbSpecific = database.getDbSpecific(dbSpecificName);
        PkCreate pkCreate = database.getPkCreate(getPk_ref(), dbSpecificName);
        String sql = pkCreate.getSql();
        Validate.notEmpty(sql, "The sql value has now been set for PkCreate [" + getPk_ref() + "] in Database ["
                + database.getName() + "]");
        return sql;
    }
}
