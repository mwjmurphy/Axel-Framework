package org.xmlactions.action.config;


import java.util.Map;
import java.util.Properties;




import org.apache.commons.configuration.ConfigurationException;
import org.springframework.context.ApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.common.text.Html;
import org.xmlactions.common.theme.Theme;



/**
 * A Manager for an Application Context.
 * <p>
 * This interface is used extensively throughout the framework code. It's purpose is to manage and maintain a set of static data and 
 * also session and request data.
 * </p>
 * @author mike.murphy
 *
 */
public interface IExecContext extends Map<String, Object> {

	/** use this to check if a Replacement string starts with "lang:" */
	public final static String PERSISTENCE_MAP = "persistence";

	/** List of stacked pages - used for pager:insert_into action */
	public static final String MAP_STACKED_PAGES = "stacked_pages";

	/** use this to check if a Replacement string starts with "lang:" */
	public final static String DEFAULT_ACTION_MAP = "default_action_map";

	/** This is the name of the Map that we use to store themes. The map is stored in the namedMaps. */
	public final static String DEFAULT_THEME_MAP = "default_theme_map";
	
	/** Use this to get a default_theme_name setting from the configuration. */
	public final static String DEFAULT_THEME_NAME = "default_theme_name";

	/** Use this to get a selected_theme_name setting from the configuration. */
	public final static String SELECTED_THEME_NAME = "selected_theme_name";

	/** Use this to get a default_theme_name setting from the configuration. */
	public final static String DEFAULT_STORAGE_CONFIG_REF = "default_storage_config_ref";

	/** use this to check if a Replacement string starts with "lang:" */
	public final static String LANG_REF = "lang";

	/** use this to check if a Replacement string starts with "replace:" */
	public final static String REPLACE_REF = "replace";

	/** use this to check if a Replacement string starts with "theme:" */
	public final static String THEME_REF = "theme";

	/** use this to check if a Replacement string starts with "spring_ac:" */
	public final static String APPLICATIONCONTEXT_REF = "spring_ac";

	/** use this to get an java environment variable */
	public final static String ENV_REF = "env";

	/** use this to get an java property variable */
	public final static String PROP_REF = "prop";

    /** This is the default identifier when getting a key value. If we have a default value it will be returned when null is found*/
	public final static String DEFAULT_ID = "|";

    /** This is the key to the default locale file */
    public final static String DEFAULT_LOCALE_FILE = "default_locale_file";

    /** This is the key to the default rows for a list presentation */
    public final static String DEFAULT_ROWS = "default_rows";

    /** This is the key to the default locale file */
    public final static String DEBUG_LEVEL = "debug_level";

    /** Content Types that we may return to browser.  These are set in the HttpPager class */
    public final static String	CONTENT_TYPE_KEY = Html.CONTENT_TYPE_KEY,
    							CONTENT_TYPE_HTML = Html.CONTENT_TYPE_HTML,
    							CONTENT_TYPE_XML = Html.CONTENT_TYPE_XML,
    							CONTENT_TYPE_JSON = Html.CONTENT_TYPE_JSON;

	//public Object get(String key);
	//public void set(String key, Object value);

	public String getString(String key);
	
	/** Try and get a value for the key, if none found than return the key untouched */
	public String getStringQuietly(String key);
	
	public String getLocalizedString(String resource, String key);

	/**
	 * Calls StrSubstutitor to replace any ${...} replacement markers using the execContent for the replacement map.
	 * @param content
	 * @return the content with any replacement markers replaced.
	 */
	public String replace(String content);

	
	/** Append this xml resource to rootMap */
	public void addXmlConfig(String xmlResourceName) throws ConfigurationException;

	/** Append this map to rootMap */
	public void addProperties(Properties props);



	/** Append this map to rootMap */
	public void addMap(Map map);

	/** Append this properties to default actions */
	public void addActions(Properties props);
	
	/** Append this properties to actions as a named map */
	public void addNamedActions(String actionMapName, Properties props);

	/** Append this map to default actions */
	public void addActions(Map map);
	
	/** Append this map to actions as a named */
	public void addNamedActions(String actionMapName, Map map);

	/** Add this map to namedMaos */
	public void addNamedMap(String mapName, Map<String, Object> map);
	
	/** Get a Named Map */
	public Map<String,Object> getNamedMap(String mapName);
	
	/**
	 * Persist a value to permanent storage
	 * <p>
	 * The permanent storage may be a HttpSession or database entry. Depends on how the persistence is managed.
	 * </p>
	 */
	public void persist(String key, Object value);

	/**
	 * Get a persisted value from permanent storage
	 * <p>
	 * The permanent storage may be a HttpSession or database entry. Depends on how the persistence is managed.
	 * </p>
	 */
	public Object getPersisted(String key);

    public Map<String, Object> getPersistenceMap();

	public void loadFromPersistence();

	public void saveToPersistence();

    public void reset();

	public void setApplicationContext(ApplicationContext applicationContext);

	public ApplicationContext getApplicationContext();
	
	/**
	 * Get the class name associated with the action key
	 * @param actionMapName
	 * @param actionKey
	 * @return the class name i.e. org.xmlactions.pager.actions.Echo
	 */
	public String getAction(String actionMapName, String actionKey);

	public BaseAction getActionClass(String actionMapName, String actionKey) throws ClassNotFoundException, InstantiationException, IllegalAccessException;
	
	public Object getClassAsObject(String actionMapName, String actionKey) throws ClassNotFoundException, InstantiationException, IllegalAccessException;
	
	/**
	 * @return Root theme containing all loaded child themes
	 */
	public Theme getThemes();
	
	/**
	 * @return Selected or default theme. May be null
	 */
	public Theme getSelectedTheme();
	
	/**
	 * 
	 * @param key used to select the theme css value 
	 * @return the theme css value. May be empty if no theme has 
	 * 		   been selected or there is no matching key found in the theme.
	 */
    public String getThemeValueQuietly(String key);


    public void addNotice(String msg);

    public void clearNotices();

    public void copyTo(IExecContext execContext);

    public Map<String, Object> getRootMap();

    public void setRootMap(Map<String, Object> rootMap);

    public Map<String, Map<String, Object>> getNamedMaps();

    public void setNamedMaps(Map<String, Map<String, Object>> namedMaps);

    public Map<String, Map<String, Object>> getActionMaps();

    public void setActionMaps(Map<String, Map<String, Object>> actionMaps);
    
    public String show();

}
