package org.xmlactions.db.actions;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.Validate;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class DbSpecific extends BaseAction {

    private List<PkCreate> pkCreates = new ArrayList<PkCreate>();

    private List<Sql> sqls = new ArrayList<Sql>();

    private List<Function> functions = new ArrayList<Function>();

    private String name;
    private String total_record_count_sql;
    private String total_record_count_field;

    public String execute(IExecContext execContext) throws Exception {

        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPk_create(PkCreate pkCreate) {
        pkCreates.add(pkCreate);
    }

    /** @return the last pkCreate added. */
    public PkCreate getPk_create() {

        return pkCreates.get(pkCreates.size() - 1);
    }

    public void setSql(Sql sql) {
        sqls.add(sql);
    }

    /** @return the last XmlCData added. */
    public Sql getSql() {

        return sqls.get(sqls.size() - 1);
    }

    public void setFunction(Function function){
        functions.add(function);
    }

    /** @return the last Function added. */
    public Function getFunction() {

        return functions.get(functions.size() - 1);
    }

    /**
     * Get the named pkCreate from the pkCreates list
     * 
     * @return the named pkCreate.
     * @throws IllegalArgumentException
     *             if not found
     */
    public PkCreate getPkCreate(String name) throws IllegalArgumentException {

        PkCreate pkCreate = getPkCreateQuietly(name);
        Validate.notNull(pkCreate, "PkCreate [" + name + "] not found in Database [" + getName() + "]");
        return pkCreate;
    }

    /**
     * Get the named pkCreate from the pkCreates list
     * 
     * @return the named pkCreate.
     * @throws IllegalArgumentException
     *             if not found
     */
    public PkCreate getPkCreateQuietly(String name) throws IllegalArgumentException {

        for (PkCreate pkCreate : getPkCreates()) {
            if (name.equals(pkCreate.getName())) {
                return pkCreate;
            }
        }
        return null;
    }

    /**
     * Get the named sql from the sqls list
     * 
     * @return the named sql.
     * @throws IllegalArgumentException
     *             if not found
     */
    public Sql getSql(String name) throws IllegalArgumentException {

        Sql sql = getSqlQuietly(name);
        Validate.notNull(sql, "Sql [" + name + "] not found in Database [" + getName() + "]");
        return sql;
    }

    /**
     * Get the named sql from the sqls list
     * 
     * @return the named sql.
     */
    public Sql getSqlQuietly(String name) throws IllegalArgumentException {

        for (Sql sql : getSqls()) {
            if (name.equals(sql.getName())) {
                return sql;
            }
        }
        return null;
    }

    /**
     * Get the named function from the functions list
     * 
     * @return the named function.
     * @throws IllegalArgumentException
     *             if not found
     */
    public Function getFunction(String name) throws IllegalArgumentException {

        Function function = getFunctionQuietly(name);
        Validate.notNull(function, "Function [" + name + "] not found in Database [" + getName() + "]");
        return function;
    }

    /**
     * Get the named function from the functions list
     * 
     * @return the named function.
     * @throws IllegalArgumentException
     *             if not found
     */
    public Function getFunctionQuietly(String name) throws IllegalArgumentException {

        for (Function function : getFunctions()) {
            if (name.equals(function.getName())) {
                return function;
            }
        }
        return null;
    }

    public List<PkCreate> getPkCreates() {

        return pkCreates;
    }

    public List<Sql> getSqls() {

        return sqls;
    }

    public List<Function> getFunctions() {

        return functions;
    }

	public String getTotal_record_count_sql() {
		return total_record_count_sql;
	}

	public void setTotal_record_count_sql(String total_record_count_sql) {
		this.total_record_count_sql = total_record_count_sql;
	}

	public String getTotal_record_count_field() {
		return total_record_count_field;
	}

	public void setTotal_record_count_field(String total_record_count_field) {
		this.total_record_count_field = total_record_count_field;
	}

}
