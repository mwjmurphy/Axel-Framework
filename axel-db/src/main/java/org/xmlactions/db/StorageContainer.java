
package org.xmlactions.db;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Map;






import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xmlactions.action.Action;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.collections.MapUtils;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.config.StorageValidator;


/**
 * A Storage Container manages a Storage class.
 * <p>
 * A Storage class describes one or more data sources such as a database / xml file.
 * </p>
 * <p>
 * This class manages that Storage and provides methods to load and access a Storage class, including Spring loading.
 * </p>
 * 
 * @author mike
 * 
 */
public class StorageContainer
{

	private static final Logger log = LoggerFactory.getLogger(StorageContainer.class);

	/* Will contain the Storage class after the definition file has been loaded. */
	private Storage storage;

	/** This is the file name of the storage definition file */
	private String definitionResourceName;

	/**
	 * Create the Storage definition.
	 * <p>
	 * Loads the definition and creates the Storage classes.
	 * </p>
	 * 
	 * @param definitionResourceName
	 *            the Definition Resource Name. The resource must be in the
	 *            classpath.
	 * 
	 * @param execContext
	 *            the pre=populated map containing the actions required for the
	 *            Storage construction.
	 * @throws Exception 
	 */
	public StorageContainer(URL definitionResource, IExecContext execContext) throws Exception
	{

		this.setDefinitionResourceName(definitionResource.getPath());
		// log.info("Storage Resource Name [" + definitionResource.getPath() + "]");
		// load action properties
		// load definition
		String xml = IOUtils.toString(definitionResource.openStream());

		Action action = new Action();
		BaseAction[] actions = action.processXML(execContext, xml);
		processInserts(execContext, actions);
		validateStorage(actions, xml);
		setStorage((Storage) actions[0]);
	}
	
	/**
	 * Create the Storage definition.
	 * <p>
	 * Loads the definition and creates the Storage classes.
	 * </p>
	 * 
	 * @param definitionResourceName
	 *            the Definition Resource Name. The resource must be in the
	 *            classpath.
	 * 
	 * @param execContext
	 *            the pre=populated map containing the actions required for the
	 *            Storage construction.
	 * @throws Exception 
	 */
	public StorageContainer(String definitionResourceName, IExecContext execContext) throws Exception
	{
		this.setDefinitionResourceName(definitionResourceName);
		
		URL resource = ResourceUtils.getResourceURL(execContext.getApplicationContext(), definitionResourceName, this.getClass());

		String xml = IOUtils.toString(resource.openStream());

		Action action = new Action();
		BaseAction[] actions = action.processXML(execContext, xml);
		processInserts(execContext, actions);
		validateStorage(actions, xml);
		setStorage((Storage) actions[0]);
	}
	
	/**
	 * A database may contain DBInserts that are used to insert databases from other files. This
	 * method calls the insertion process.
	 * @param execContext
	 * @param actions
	 * @throws Exception
	 */
	private void processInserts(IExecContext execContext, BaseAction[] actions) throws Exception {
		for (BaseAction storage : actions) {
			if (storage instanceof Storage) {
				((Storage)storage).processInserts(execContext);
			}
		}
	}

	/**
	 * Create the Storage definition.
	 * <p>
	 * Loads the definition and creates the Storage classes.
	 * </p>
	 * 
	 * @param definitionResourceName
	 *            the Definition Resource Name. The resource must be in the
	 *            classpath.
	 * 
	 * @param propertiesFileName
	 *            the properties used to create a map containing the actions
	 *            required for the Storage construction.
	 * @throws Exception 
	 */
	public StorageContainer(String definitionResourceName, String propertiesFileName)
			throws Exception
	{

		this.setDefinitionResourceName(definitionResourceName);

		// log.info("Storage Resource Name [" + ResourceUtils.getResourceURL(definitionResourceName).getFile() + "]");
		// log.info("Storage Properties File [" + ResourceUtils.getResourceURL(propertiesFileName).getFile() + "]");

		// load properties to a map
		Map<String, Object> map = MapUtils.loadMapFromProperties(propertiesFileName);
		ExecContext execContext = new NoPersistenceExecContext(null, null);
        // execContext.addNamedActions("http://www.riostl.com/pager_db", map);
        execContext.addNamedActions("http://www.xmlactions.org/storage", map);

        StorageValidator storageValidator= new StorageValidator();
        storageValidator.validate(definitionResourceName, execContext);

        // load action properties
		// load definition
		String xml = IOUtils.toString(ResourceUtils.getResourceURL(definitionResourceName).openStream());

		Action action = new Action();
		BaseAction[] actions = action.processXML(execContext, xml);
		processInserts(execContext, actions);
		validateStorage(actions, xml);
		setStorage((Storage) actions[0]);
	}
	
	/**
	 * Create the Storage definition.
	 * <p>
	 * Loads the definition and creates the Storage classes.
	 * </p>
	 * 
	 * @param definitionResource
	 *            the Definition Resource Name. The resource must be in the
	 *            classpath.
	 * 
	 * @param propertiesResource
	 *            the properties used to create a map containing the actions
	 *            required for the Storage construction.
	 * @throws Exception 
	 */
	public StorageContainer(URL definitionResource, URL propertiesResource)
			throws Exception
	{

		this.setDefinitionResourceName(definitionResource.getPath());

		// log.info("Storage Resource Name [" + ResourceUtils.getResourceURL(definitionResourceName).getFile() + "]");
		// log.info("Storage Properties File [" + ResourceUtils.getResourceURL(propertiesFileName).getFile() + "]");

		// load properties to a map
		Map<String, Object> map = MapUtils.loadMapFromProperties(propertiesResource);
		ExecContext execContext = new NoPersistenceExecContext(null, null);
        // execContext.addNamedActions("http://www.riostl.com/pager_db", map);
        execContext.addNamedActions("http://www.xmlactions.org/storage", map);

        StorageValidator storageValidator= new StorageValidator();
        storageValidator.validate(definitionResourceName, execContext);

        // load action properties
		// load definition
		String xml = IOUtils.toString(ResourceUtils.getResourceURL(definitionResourceName).openStream());

		Action action = new Action();
		BaseAction[] actions = action.processXML(execContext, xml);
		processInserts(execContext, actions);
		validateStorage(actions, xml);
		setStorage((Storage) actions[0]);
	}
	
	/**
	 * Create the Storage definition.
	 * <p>
	 * Loads the definition and creates the Storage classes.
	 * </p>
	 * 
	 * @param definitionResourceName
	 *            the Definition Resource Name. The resource must be in the
	 *            classpath.
	 * 
	 * @param properties
	 *            the properties used to create a map containing the actions
	 *            required for the Storage construction.
	 * @throws Exception 
	 */
	public StorageContainer(URL definitionResource, Properties properties) throws Exception {

		this.setDefinitionResourceName(definitionResource.getPath());

		//log.info("Storage Resource Name [" + definitionResource.getPath() + "]");

		// load properties to a map
		Map<String, Object> map = MapUtils.propertiesToMap(properties);
		ExecContext execContext = new NoPersistenceExecContext(null, null);
        // execContext.addNamedActions("http://www.riostl.com/pager_db", map);
        execContext.addNamedActions("http://www.xmlactions.org/storage", map);

        StorageValidator storageValidator= new StorageValidator();
        storageValidator.validate(definitionResource, execContext);

        // load action properties
		// load definition
		String xml = IOUtils.toString(definitionResource.openStream());

		Action action = new Action();
		BaseAction[] actions = action.processXML(execContext, xml);
		processInserts(execContext, actions);
		validateStorage(actions, xml);
		setStorage((Storage) actions[0]);
	}
	
	private void validateStorage(BaseAction [] actions, String xml) {
		if (actions.length == 0) {
			throw new InvalidParameterException("A Storage Resource XML must contain one root storage element. Resource [" + definitionResourceName
						+ "]. Root count [" + actions.length + "]\nxml:" + xml);
		}
		if (actions.length >  1) {
			throw new InvalidParameterException("A Storage Resource XML may only contain one root storage element. Resource [" + definitionResourceName
						+ "]. Root count [" + actions.length + "]\nxml:" + xml);
		}
		Validate.isTrue(actions[0] instanceof Storage, "The root element must be a storage action. Resource ["
				+ definitionResourceName + "]");
	
	}

	public void setStorage(Storage storage)
	{

		this.storage = storage;
	}

	public Storage getStorage()
	{

		return storage;
	}

	public void setDefinitionResourceName(String definitionResourceName)
	{

		this.definitionResourceName = definitionResourceName;
	}

	public String getDefinitionResourceName()
	{

		return definitionResourceName;
	}

}
