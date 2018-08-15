package org.xmlactions.action.config;


import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.xmlactions.action.ActionConsts;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.common.locale.LocaleUtils;
import org.xmlactions.common.theme.Theme;


@SuppressWarnings("serial")
//public abstract class ExecContext extends HashMap<String, Object> implements IExecContext {
public abstract class ExecContext implements IExecContext, Serializable {

	private final static Logger log = LoggerFactory.getLogger(ExecContext.class);

	private ApplicationContext applicationContext;

	/** list of maps accessible using xxx:key where xxx reference maps.key */
    private Map<String, Map<String, Object>> namedMaps = createMaps();

	private Map<String, Object> rootMap = new HashMap<String, Object>();

	private Map<String, Map<String, Object>> actionMaps = createActionMaps();
	
	/**
	 * @deprecated - use spring bean scope="session" or "request"
	 */
	public void copyTo(IExecContext dest) {
		dest.setApplicationContext(getApplicationContext());
        // dest.setRootMap(getRootMap()); // why copy the root map
        dest.setActionMaps(getActionMaps());
        Map<String, Object> map = dest.getActionMaps().get("request");
        if (map != null) {
        	map.clear();
        }
        map = dest.getActionMaps().get(PERSISTENCE_MAP);
        if (map != null) {
        	map.clear();
        }
        dest.setNamedMaps(getNamedMaps());
        Map<String, Map<String, Object>> maps = dest.getNamedMaps();
        for (String key : maps.keySet()) {
			if (log.isDebugEnabled()) {
				log.debug("namedMaps key:" + key);
			}
        }
		Set<Entry<String,Object>> set = entrySet();
		Iterator<Entry<String,Object>> iterator = set.iterator();
		while(iterator.hasNext()) {
			Entry<String,Object> entry = iterator.next(); 
			dest.put(entry.getKey(), entry.getValue());
			if (log.isDebugEnabled()) {
				log.debug("key:" + entry.getKey());
			}
		}
		set = rootMap.entrySet();
		iterator = set.iterator();
		while(iterator.hasNext()) {
			Entry<String,Object> entry = iterator.next();
			dest.put(entry.getKey(), entry.getValue());
			if (log.isDebugEnabled()) {
				log.debug("key:" + entry.getKey());
			}
		}
	}
	
	private static Map<String,Map<String,Object>> createMaps() {
		Map<String, Map<String, Object>> maps = new HashMap<String, Map<String, Object>>();
		maps.put(PERSISTENCE_MAP, new HashMap<String, Object>());
		return maps;
	}

	private static Map<String,Map<String,Object>> createActionMaps() {
		Map<String, Map<String, Object>> maps = new HashMap<String, Map<String, Object>>();
		maps.put(DEFAULT_ACTION_MAP, new HashMap<String, Object>());
		return maps;
	}


	public ExecContext(List<Object> actionMaps, List<Object> localMaps, List<Object> themes ) {
		if (actionMaps != null) {
			for (Object object : actionMaps) {
				if (object instanceof Map) {
					Map<String,String> map = (Map<String, String>) object;
					this.actionMaps.get(DEFAULT_ACTION_MAP).putAll(map);
				} else if (object instanceof Properties) {
					Properties props = (Properties)object;
					Enumeration enumeration = props.keys();
					while (enumeration.hasMoreElements()) {
						String key = (String)enumeration.nextElement();
						this.actionMaps.get(DEFAULT_ACTION_MAP).put(key, props.getProperty(key));
					}
				}
			}
		}
		if (localMaps != null) {
			for (Object object : localMaps) {
				if (object instanceof Map) {
					Map<String,String> map = (Map<String, String>) object;
					this.putAll(map);
				} else if (object instanceof Properties) {
					Properties props = (Properties)object;
					Enumeration enumeration = props.keys();
					while (enumeration.hasMoreElements()) {
						String key = (String)enumeration.nextElement();
						this.put(key, props.getProperty(key));
					}
				} else if (object instanceof XMLConfiguration) {
					addXmlConfiguration((XMLConfiguration)object);
				}
			}
		}
		if (themes != null) {
			for (Object object : themes) {
				if (object instanceof Theme) {
					addThemes((Theme)object);
				} else if (object instanceof Properties) {
					Properties props = (Properties)object;
					addThemes(props);
				}
			}
		}
	}

	/**
	 * @deprecated - replaced with constructor ExecContext(List<Object> actionMaps, List<Object> localMaps, List<Object> themes)
	 * @param actionMaps
	 * @param localMaps
	 */
	public ExecContext(List<Object> actionMaps, List<Object> localMaps) {
		if (actionMaps != null) {
			for (Object object : actionMaps) {
				if (object instanceof Map) {
					Map<String,String> map = (Map<String, String>) object;
					this.actionMaps.get(DEFAULT_ACTION_MAP).putAll(map);
				} else if (object instanceof Properties) {
					Properties props = (Properties)object;
					Enumeration enumeration = props.keys();
					while (enumeration.hasMoreElements()) {
						String key = (String)enumeration.nextElement();
						this.actionMaps.get(DEFAULT_ACTION_MAP).put(key, props.getProperty(key));
					}
				}
			}
		}
		if (localMaps != null) {
			for (Object object : localMaps) {
				if (object instanceof Map) {
					Map<String,String> map = (Map<String, String>) object;
					this.putAll(map);
				} else if (object instanceof Properties) {
					Properties props = (Properties)object;
					Enumeration enumeration = props.keys();
					while (enumeration.hasMoreElements()) {
						String key = (String)enumeration.nextElement();
						this.put(key, props.getProperty(key));
					}
				}
			}
		}
	}


	public Object put(String key, Object value)
	{

		int index;
		if ((index = key.indexOf(':')) > 0 && index < key.length() - 1) {
			String mapKey = key.substring(0, index);
			Map<String, Object> selectedMap = getMap(mapKey);
			if (selectedMap==null) {
				selectedMap = new Hashtable<String,Object>();
				namedMaps.put(mapKey, selectedMap);
			}
			if (selectedMap != null) {
				return selectedMap.put(key.substring(index + 1), value);
			}
		}
		return rootMap.put(key, value);
	}

	public String getString(String key) {
		return (String)get((Object)key);
	}
	public String getStringQuietly(String key) {
		String value = getString(key);
		if (value == null) return key;
		return (value);
	}

	/**
	 * get a value for key.
	 * <p>
	 * if key has : as in xxx:key then find the value in a map assigned to the xxx in maps.<br/>
	 * e.g. <code>get("session:id")</code> returns the value of <code>maps.get("session").get("id");</code>
	 * </p>
	 * <p>
	 * if the key has "lang:key:resouces:language:country:variant"
	 * <br/>or "lang:key" uses the default resource file and the default locale
	 * <br/>or "lang:key:resource" uses the resource file and the default locale
	 * <br/>or "lang:key::language" uses the default resource file and the language locale
	 * <br/>...
	 * </p>
	 * <p>
	 * if the key has "replace:value:regex:replacement" perform a String.replaceAll
	 * <p>
	 * The replacementPattern format is "replace:value:regex:replacement"
	 * <br/>"replace" replacement instruction
	 * <br/>"value" the string to perform the replacement on
	 * <br/>"regex" the expression for the replacement
	 * <br/>"replacement" the replacement value
	 * </p>
	 * </p>
	 * 
	 * @return the value for key or null if not found.
	 * 
	 */
	public Object get(Object key)
	{

		int index;
		String orDefault = null;
		String k = (String) key;
		if ((index = k.indexOf(DEFAULT_ID)) > 0) {
			orDefault = k.substring(index+DEFAULT_ID.length());
			k = k.substring(0,index);
			key = k;
		}
		Validate.notEmpty(k, "Empty key passed as parameter for call to get(key)");
		if ((index = k.indexOf(':')) > 0 && index < k.length() - 1) {
			if (index == LANG_REF.length() && k.startsWith(LANG_REF)) {
				return new LanguageLocale().getLang(this,k);
			} else if (index == REPLACE_REF.length() && k.startsWith(REPLACE_REF)) {
				return org.xmlactions.action.utils.StringUtils.replace(this, replace(k));
			} else if (index == THEME_REF.length() && k.startsWith(THEME_REF)) {
				return getThemeValueQuietly(k.substring(index + 1));
			} else if (index == APPLICATIONCONTEXT_REF.length() && k.startsWith(APPLICATIONCONTEXT_REF)) {
				return getApplicationContext().getBean(k.substring(index + 1));
			} else {
				Map<String, Object> map = getMap(k.substring(0, index));
				if (map != null) {
					String [] keys = k.substring(index + 1).split("/");
					for (int keyIndex = 0 ; keyIndex < keys.length; keyIndex++ ) {
						k = keys[keyIndex];
						Object obj = map.get(k);
						if (obj != null && obj instanceof String) {
	                        String s = (String)obj;
	                        if (s.startsWith("${") && s.indexOf(k) == 2) {
	                            obj = "[" + k + "]";
	                        } else {
	                            obj = replace((String)obj);
	                        }
						} else if (obj == null){
							obj = orDefault;
						}
						if(keyIndex+1 >= keys.length) {
							return obj;
						}
						if (obj instanceof Map) {
							map = (Map)obj;
						} else {
							return obj;
						}
					}
				}
				return orDefault;
			}
		} else {
			String [] keys = k.split("/");
			Object obj = null;
			Map map = null;
			for (int keyIndex = 0 ; keyIndex < keys.length; keyIndex++ ) {
				k = keys[keyIndex];

				if (keyIndex == 0) {
					if (rootMap.containsKey(k)) {
						obj = rootMap.get(k);
					}
					else {
						try {
							if (applicationContext != null) {
								obj = applicationContext.getBean((String)k);
							}
						} catch (Throwable t) {
		                    log.info(t.getMessage());
						}
					}
				} else {
					if (map.containsKey(k)) {
						obj = map.get(k);
					}
				}
				if (obj != null && obj instanceof String) {
					obj = replace((String)obj);
				} else if (obj == null){
					obj = orDefault;
				}
				if(keyIndex+1 >= keys.length) {
					return obj;
				}
				if (obj instanceof Map) {
					map = (Map)obj;
				} else {
					return obj;
				}
			}
			return orDefault;
		}
	}


	/**
	 * Looks for a named map in 2 places. 1) the applicationContext and 2) the namedMaps
	 * @param key - used to find the map
	 * @return the found map or null
	 */
	private Map<String, Object> getMap(String key) {
		Map<String,Object> map = null;

		if (applicationContext != null && applicationContext.containsBean(key)) {
			map = (Map<String, Object>)applicationContext.getBean(key);
		}
		if (map == null && namedMaps.containsKey(key)) {
			map = namedMaps.get(key);
		}
		return map;
	}

	public String replace(String content) {
        return StrSubstitutor.replace(content, this);
	}

	public String getLocalizedString(String resource, String key)
	{
		Validate.notEmpty(resource, "No Resource (locale file) has been set for call to getLocalIzedString(resource, ["+key+"]);");
		try {
			ResourceBundle res = ResourceBundle.getBundle(resource);
			return res.getString(key);
		} catch (Exception ex) {
			// ignore the exception
			return "[" + key + "] " + "error[" + ex.getMessage() + "]";
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Stores a "key - value" into the persistence map.
	 * <p>
	 * if the Key contains a named map it will insert it into the named map instead.
	 * </p>
	 * <p>
	 * Values may be retrieved using 'persistence:xxx' where persistence is the persistent named map.
	 * </p>
	 */
	public void persist(String key, Object value)
	{
		getPersistenceMap().put(key, value);
	}
	
	/**
	 * Get a persisted value from permanent storage
	 * <p>
	 * The permanent storage may be a HttpSession or database entry. Depends on how the persistence is managed.
	 * </p>
	 */
	public Object getPersisted(String key) {
		return getPersistenceMap().get(key);
	}

	

	public Map<String, Object> getPersistenceMap() {
		return getNamedMap(PERSISTENCE_MAP);
	}


	public Map<String, Object> getNamedMap(String mapName) {
		return namedMaps.get(mapName);
	}
	
	public void addNamedMap(String mapName, Map<String,Object>map) {
		namedMaps.put(mapName, map);
	}
	
	
	
	public void addThemes(Properties props) {
		// String themeName = Theme.getThemeName(props);
		Theme theme = new Theme(props);
		// theme.setName(themeName);
		addThemes(theme);
	}
	
	public void addThemes(Theme newTheme) {
        if (get(DEFAULT_THEME_MAP) == null) {
        	put(DEFAULT_THEME_MAP, newTheme);
        } else {
        	Theme theme = (Theme)get(DEFAULT_THEME_MAP);
        	theme.appendTheme(newTheme);
		}
	}
	
	public Theme getThemes() {
        if (get(DEFAULT_THEME_MAP) == null) {
            String error = LocaleUtils.getLocalizedString(ActionConsts.UXML_ACTION_LANG_FILE_NAME,
                    ActionConsts.LANG_KEY_NO_THEME_SET);
            error = String.format(error, DEFAULT_THEME_MAP);
            throw new IllegalArgumentException(error);
        }
		return (Theme)get(DEFAULT_THEME_MAP);
	}

	public BaseAction getActionClass(String actionMapName, String actionKey) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		BaseAction action = null;
		String className = getAction(actionMapName, actionKey);
		if (StringUtils.isNotEmpty(className)) {
			Class<?> clas = ClassUtils.getClass(className);
			Object bean = clas.newInstance();
			if (!(bean instanceof BaseAction)) {
				throw new InstantiationException("class [" + bean.getClass().getName() + " must extend ["
						+ BaseAction.class.getName() + "]");
			}
			action = (BaseAction) bean;
		} else {
            log.info("no action class found for [" + actionMapName + "][" + actionKey + "]");
            // throw new IllegalArgumentException("no action class found for [" + actionMapName + "][" + actionKey + "]");
		}
		return action;
	}

	/**
     * Returns a class that matches the xml action name. This differs from
     * the getActionClass in that it does not require the class to extend BaseAction.
     * @param actionMapName
     * @param actionKey
     * @return matching class
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object getClassAsObject(String actionMapName, String actionKey) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
                Object bean = null;
                String className = getAction(actionMapName, actionKey);
                if (StringUtils.isNotEmpty(className)) {
                            Class<?> clas = ClassUtils.getClass(className);
                            bean = clas.newInstance();
                } else {
                            throw new IllegalArgumentException("no action class found for [" + actionMapName + "][" + actionKey + "]");
                }
                return bean;
    }

	public String getAction(String actionMapName, String actionKey) {
		String className = null;
		Map<String, Object> map;
		try {
			if (actionMapName == null) {
				map = actionMaps.get(DEFAULT_ACTION_MAP);
			} else {
				map = actionMaps.get(actionMapName);
				if (map == null) {
					return getAction(null, actionKey);
				}
			}
			if (map != null) {
				className = (String) map.get(actionKey);
				if (StringUtils.isEmpty(className)){
					if (StringUtils.isNotEmpty(actionMapName)) {
						return getAction(null, actionKey);
					}
				}
			}
			return className;
		} catch (ClassCastException ex) {
			throw new IllegalArgumentException(ex.getMessage() + " - actionMapName[" +actionMapName + "] actionKey [" + actionKey + "]", ex);
		}
	}

	public void setDefaultLocaleFileName(String defaultLocaleFileName) {
		put(DEFAULT_LOCALE_FILE, defaultLocaleFileName);
	}

    /**
     * Remove any persistence settings before reloading session or cookie
     * persistence.
     * 
    public void reset()
	{
        Map<String, Object> map = getPersistenceMap();
        map.clear();
        return;
	}
     */

    /**
     * @deprecated does nothing don't make this call
     */
	public void clear() {
        reset();
	}

	public boolean containsKey(Object key) {
		return rootMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return rootMap.containsValue(value);
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return rootMap.entrySet();
	}

	public boolean isEmpty() {
		return rootMap.isEmpty();
	}

	public Set<String> keySet() {
		return rootMap.keySet();
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		rootMap.putAll(m);
	}

	public Object remove(Object key) {
		return rootMap.remove(key);
	}

	public int size() {
		return rootMap.size();
	}

	public Collection<Object> values() {
		return rootMap.values();
	}

	private Map<String, Object> convertPropertiesToMap(Properties props) {
		Map<String, Object>map = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			map.put((String)key, props.get(key));
		}
		return map;
	}

	public void addXmlConfig(String xmlResourceName) throws ConfigurationException {
        // log.debug("xmlResourceName:" + xmlResourceName);
		URL url = PropertyContainer.class.getResource(xmlResourceName);
		Validate.notNull(url, "Missing xml configuration file [" + xmlResourceName + "]");
		XMLConfiguration config = new XMLConfiguration(url);
		addXmlConfiguration(config);
	}

	private void addXmlConfiguration(XMLConfiguration config) {
		Iterator <String>iterator = config.getKeys();
		while (iterator.hasNext()) {
			String key = iterator.next();
            // log.debug("key:" + key);
			this.put(key, config.getProperty(key));
		}
	}

	public void addProperties(Properties props) {
		Map<String, Object> map = convertPropertiesToMap(props);
		putAll(map);
	}
	public void addMap(Map map) {
		putAll(map);
	}

	public void addActions(Properties props) {
		Map<String, Object> map = convertPropertiesToMap(props);
		this.actionMaps.get(DEFAULT_ACTION_MAP).putAll(map);
	}
	public void addActions(Map map) {
		this.actionMaps.get(DEFAULT_ACTION_MAP).putAll(map);
	}
	public void addNamedActions(String actionMapName, Properties props) {
		Map<String, Object> map = convertPropertiesToMap(props);
		Map<String, Object> actionMap = actionMaps.get(actionMapName);
		if (actionMap == null) {
			actionMap = new HashMap<String,Object>();
			actionMaps.put(actionMapName, actionMap);
		}
		actionMap.putAll(map);
	}
	public void addNamedActions(String actionMapName, Map map) {
		Map<String, Object> actionMap = actionMaps.get(actionMapName);
		if (actionMap == null) {
			actionMap = new HashMap<String,Object>();
			actionMaps.put(actionMapName, actionMap);
		}
		actionMap.putAll(map);
	}

    private List<String> notices = new ArrayList<String>();

    public void addNotice(String msg) {
        this.notices.add(msg);
    }

    public void clearNotices() {
        this.notices = new ArrayList<String>();
    }

    public Map<String, Object> getRootMap() {
        return rootMap;
    }

    public void setRootMap(Map<String, Object> rootMap) {
        this.rootMap = rootMap;
    }

    public Map<String, Map<String, Object>> getNamedMaps() {
        return namedMaps;
    }

    public void setNamedMaps(Map<String, Map<String, Object>> namedMaps) {
        this.namedMaps = namedMaps;
    }

    public Map<String, Map<String, Object>> getActionMaps() {
        return actionMaps;
    }

    public void setActionMaps(Map<String, Map<String, Object>> actionMaps) {
        this.actionMaps = actionMaps;
    }

    public String getThemeValueQuietly(String key) {
    	Theme theme = getSelectedTheme();
    	if (theme != null) {
    		String cssValue = theme.getValue(key);
    		if (cssValue != null) {
    			return cssValue;
    		}
    	}
    	return key;
    }
    
    public Theme getSelectedTheme() {
    	String selectedThemeName = getString(SELECTED_THEME_NAME);
    	Theme theme = null; 
    	if (selectedThemeName != null) {
        	theme = getThemes().getTheme(selectedThemeName);
    	}
    	if (theme == null) {
    		selectedThemeName = getString(DEFAULT_THEME_NAME);
        	theme = getThemes().getTheme(selectedThemeName);
    	}
    	return theme;
    }
    


    public String show() {
    	StringBuilder sb = new StringBuilder("\n");
        Map<String, Map<String, Object>> maps = getActionMaps();
        for (String key : maps.keySet()) {
    		sb.append("actionMaps:key[" + key + "]\n");
    		Map<String, Object>map = maps.get(key);
            for (String innerkey : map.keySet()) {
        		sb.append("   " + key + ":key[" + innerkey + "] value[" + map.get(innerkey) + "]\n");
            }
        }
        maps = getNamedMaps();
        for (String key : maps.keySet()) {
    		sb.append("namedMaps:key[" + key + "]\n");
    		Map<String, Object>map = maps.get(key);
            for (String innerkey : map.keySet()) {
        		sb.append("   " + key + ":key[" + innerkey + "] value[" + map.get(innerkey) + "]\n");
            }
        }
		Set<Entry<String,Object>> set = entrySet();
		Iterator<Entry<String,Object>> iterator = set.iterator();
		while(iterator.hasNext()) {
			Entry<String,Object> entry = iterator.next(); 
    		sb.append("key[" + entry.getKey() + "]\n");
		}
		set = rootMap.entrySet();
		iterator = set.iterator();
		while(iterator.hasNext()) {
			Entry<String,Object> entry = iterator.next();
    		sb.append("key[" + entry.getKey() + "]\n");
		}
    	
    	return sb.toString();
    	
    }
    
    
}
