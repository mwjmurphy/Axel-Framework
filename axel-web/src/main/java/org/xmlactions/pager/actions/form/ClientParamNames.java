
package org.xmlactions.pager.actions.form;

public class ClientParamNames
{

	/** This is the key to getting the table name from the HttpRequestMap */
	public final static String TABLE_NAME_MAP_ENTRY = "table.name";

	/** This is the key to getting the field name from the HttpRequestMap */
	public final static String FIELD_NAME_MAP_ENTRY = "field.name";

	/** This is the key to getting the theme name from the HttpRequestMap */
	public final static String THEME_NAME_MAP_ENTRY = "theme.name";

	/** This is the key to getting the label position from the HttpRequestMap */
	public final static String LABEL_POSITION_MAP_ENTRY = "label.position";

    /** This is the key to getting the storage reference from the HttpRequestMap */
    public final static String STORAGE_CONFIG_REF = "storage.config.ref";

    /** This is the key to getting the email reference from the HttpRequestMap */
    public final static String EMAIL_CONFIG_REF = "email.config.ref";
    
    /** This is the key to getting ant Table and Field Names from the HttpRequestMap */
    public final static String TABLE_FIELD_NAME = "table.field.name";

    /** This is the key to getting ant Table and Field Names from the HttpRequestMap */
    public final static String ENFORCE_CONCURRENCY = "enforce.concurrency";
    
    

    /**
     * This is the key to getting the CallbackPhoneAction callback.name
     * reference from the HttpRequestMap
     */
    public final static String CALLBACK_NAME = "callback.name";

    /**
     * This is the key to getting the CallbackPhoneAction callback.phone
     * reference from the HttpRequestMap
     */
    public final static String CALLBACK_PHONE = "callback.phone";

    /**
     * This is the key to getting the CallbackPhoneAction callback.email
     * reference from the HttpRequestMap
     */
    public final static String CALLBACK_EMAIL = "callback.email";

    /**
     * This is the key to getting the CallbackPhoneAction callback.email
     * reference from the HttpRequestMap
     */
    public final static String CALLBACK_MESSAGE = "callback.message";

	/** This is the key to getting the success key pair from the HttpRequestMap */
	public final static String ON_SUCCESS = "on_success";

	/**
	 * This is the key to getting the primary key value (if we doing an update)
	 * from the HttpRequestMap
	 */
	public final static String PK_VALUE = "pk.value";

	/**
	 * This is the key to getting the unique id for the form/action.
	 */
	public final static String UNIQUE_ID = "unique.id";

	/**
	 * When we are editing a page this contains the page name.
	 */
	public final static String PAGE_NAME = "page.name";

	/**
	 * When we are editing a page this contains the page content.
	 */
	public final static String PAGE_CONTENT = "page.content";
	
	/** This is the key to getting a parent table pk name (if used) from the HttpRequestMap */
	public final static String PARENT_TABLE_AND_PK_NAME = "parent.table.and.pk.name";
	
    /** if we want to call a pre processor this value will be set */
    public final static String PRE_PROCESSOR = "pre.processor";

    /** if we want to call a post processor this value will be set */
    public final static String POST_PROCESSOR = "post.processor";

    /** if we're passing a code.call as a parameter use this name */
    public final static String CODE_CALL = "call";

    /** if we're passing a code.call as a parameter use this name */
    public final static String PAGE = "page";

}
