
package org.xmlactions.action;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;







import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.text.StrSubstitutor;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.actions.SetupBeanFromXML;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceCommon;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.text.Html;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.common.xml.XMLUtils;
import org.xmlactions.action.stackedpages.StackedPage;

/**
 * Parses pages pulling out the IPageAction tags, which are used to call java
 * code, insert data and other such actions.
 * 
 * @author MichaelMurphy
 * 
 */
public class Action
{

	private static Logger log = LoggerFactory.getLogger(Action.class);

	// location of root folder
	private String rootPath;

	// page name to process, may contain addition path info
	private String pageName;
	
	public static char[][] EMPTY_NAMESPACE = {"".toCharArray()};
	
	// preceding name space for action, e.g <pager:code...> where pager is the
	// nameSpace
	public static char [][] nameSpaces = new char[0][];
	


	public Action()
	{
	}

	/**
	 * 
	 * @param nameSpace the nameSpace preceeding the action, e.g <pager:code...> where pager is the nameSpace
	 */
	public Action(String nameSpace)
	{

		this.rootPath = null;
		this.pageName = null;
		nameSpaces = new char[0][];
		addNameSpace(nameSpace);
	}

	/**
	 * 
	 * @param rootPath
	 *            the root path of the site
	 * @param pageName
	 *            the name of the page to load, may include path elements
	 * @param nameSpace
	 *            the nameSpace preceeding the action, e.g <pager:code...> where
	 *            pager is the nameSpace
	 */
	public Action(String rootPath, String pageName, String nameSpace)
	{
		this.rootPath = rootPath;
		this.pageName = pageName;
		nameSpaces = new char[0][];
		addNameSpace(nameSpace);
	}

	/**
	 * 
	 * @param rootPath
	 *            the root path of the site
	 * @param pageName
	 *            the name of the page to load, may include path elements
	 * @param nameSpace
	 *            the nameSpace preceeding the action, e.g <pager:code...> where
	 *            pager is the nameSpace
	 */
	public Action(String rootPath, String pageName, char [][] nameSpaces)
	{
		this.rootPath = rootPath;
		this.pageName = pageName;
		this.nameSpaces = new char[0][];
		addNameSpaces(nameSpaces);
	}

    /**
     * Process the page replacing any actions and / or markers. TODO add the
     * markers replacement code. e.g. add code to replace ${...};
     * 
     * @throws IOException
     *             , NestedPagerException, ClassNotFoundException,
     *             InstantiationException, IllegalAccessException,
     *             InvocationTargetException, NoSuchMethodException
     */
    public String processPage(IExecContext execContext) throws IOException, NestedActionException,
            ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, BadXMLException {

        String pageName = StrSubstitutor.replace(getPageName(), execContext);

        String loadedPage = loadPage(getRootPath(), pageName);

        String page = processPage(execContext, loadedPage);
        
		// ===
		// Handle stacked page insert intos.
		// ===
		boolean continueProcessing = true;
		while(continueProcessing == true) {
			StackedPage stackedPage = StackedPage.getAndRemoveFirstStackedPage(execContext);
			if (stackedPage != null) {
				if (stackedPage.isRemove_html()) {
					page = Html.removeOuterHtml(page);
				}
				execContext.put(stackedPage.getKey(), page);
				Action action = new Action(stackedPage.getPath(), stackedPage.getPage(), stackedPage.getNamespace());
				page = action.processPage(execContext);
			} else {
				continueProcessing = false;
			}
		}
		
		Html html = new Html();
		page = html.removeOuterJsonOrXml(page);
		if (html.getContentType() != null) {
			execContext.put(ExecContext.CONTENT_TYPE_KEY, html.getContentType());
		}
		
		
		return page;
    }


    /*
     * Process the page replacing any actions and / or markers. TODO add the
     * markers replacement code. e.g. add code to replace ${...};
     * 
     * @throws IOException, NestedPagerException, ClassNotFoundException,
     * InstantiationException, IllegalAccessException,
     * InvocationTargetException, NoSuchMethodException
     */
    public String processPage(IExecContext execContext, String page) throws IOException, NestedActionException,
            ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, BadXMLException {
    	if (StringUtils.isNotBlank(page)) {
    		
	        Map<String, Object> map = XMLUtils.findNameSpaces(page);
	        List<String> pageNameSpaces = (List<String>) map.get(XMLUtils.MAP_KEY_URIS);
	        addNameSpaces(pageNameSpaces);
	        String actionMapName = (String) map.get(XMLUtils.MAP_KEY_XMLNS);
	
	        List<ReplacementMarker> markers = findMarkers(page, getNameSpaces(), execContext, actionMapName);
	
	        String newPage = replaceMarkers(execContext, page, markers);
	
	        Object noStrSubst = execContext.get(ActionConst.NO_STR_SUBST);
	        if (noStrSubst != null) {
	            //execContext.put(ActionConst.NO_STR_SUBST, (Object) null);
	            newPage = newPage.toString();
	        } else {
	            newPage = StrSubstitutor.replace(newPage.toString(), execContext);
	        }
	        
			Html html = new Html();
			newPage = html.removeOuterJsonOrXml(newPage);
			if (html.getContentType() != null) {
				execContext.put(ExecContext.CONTENT_TYPE_KEY, html.getContentType());
			}
			return newPage;
    	}
    	return page;
    }

	/**
	 * Process an XML
	 * 
	 * @return an array of root actions, if size is 0 then no actions found.
	 * 
	 * @throws IOException
	 *             , NestedPagerException, ClassNotFoundException,
	 *             InstantiationException, IllegalAccessException,
	 *             InvocationTargetException, NoSuchMethodException
	 */
	public BaseAction[] processXML(IExecContext execContext, String page)
			throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

        // log.debug("page:" + (page.length() > 300 ?
        // page.substring(0,300)+"\n..." : page));
		Map<String, Object> map = XMLUtils.findNameSpaces(page);
		List<String> pageNameSpaces = (List<String>)map.get(XMLUtils.MAP_KEY_URIS);
		addNameSpaces(pageNameSpaces);
		String actionMapName = (String)map.get(XMLUtils.MAP_KEY_XMLNS);

		List<ReplacementMarker> markers;
		if (getNameSpaces().length > 0) {
			markers = findMarkers(page, getNameSpaces(), execContext, actionMapName);
		} else {
			markers = findMarkers(page, execContext, actionMapName);
		}

		// is assumed that the first marker contains the root xml element action
		BaseAction[] rootActions = new BaseAction[markers.size()];
		for (int i = 0; i < markers.size(); i++) {
			rootActions[i] = markers.get(i).getAction();
		}
		return rootActions;
	}

	/**
	 * 
	 * @param rootPath
	 *            the root path of the site
	 * @param pageName
	 *            the name of the page to load, may include path elements
	 * @throws IOException
	 */
	public static String loadPage(String rootPath, String pageName) throws IOException
	{

		// remove any data after ? which would be a parameter list in http language
		String pn = pageName;
		int indexOf = pageName.indexOf('?');
		if (indexOf >= 0 ) {
			pn = pageName.substring(0, indexOf);
		}
			
		String page;
        // log.debug("rootPath:" + rootPath + " pageName:" + pageName);
		String name = ResourceCommon.buildFileName(rootPath, pn);
		File file = new File(name);
		if (!file.exists()) {
			name = ResourceCommon.buildFileName(rootPath, pageName, "/");
			URL url = null;
			//url = Action.class.getResource(name);
			url = ResourceUtils.getResourceURL(name);
			// Validate.notNull(url, "Missing file [" + name + "]");
			page = IOUtils.toString(url.openStream());
		} else {
			page = FileUtils.readFileToString(file);
		}
		return page;
	}

	/**
	 * Find and return a list of all the markers on the page that begin with the
	 * nameSpace.
	 * 
	 * @param page
	 *            the web page
	 * @param nameSpaces
	 *            the name space of the markers we want to find.
	 * @param execContext
	 * @param actionMapName
	 * @return a list of ReplaceMarkers for each node found that uses the
	 *         nameSpace
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws NestedActionException
	 * @throws BadXMLException
	 * @throws DocumentException
	 */
	public List<ReplacementMarker> findMarkers(String page, char[][] nameSpaces, IExecContext execContext, String actionMapName)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, NestedActionException, BadXMLException
	{
		Validate.notNull(nameSpaces, "The nameSpace value is not set, try setting this to \"pager\" before calling.");
		ActionMarkers actionMarkers = new ActionMarkers();
		List<ReplacementMarker> markers = actionMarkers.getReplacementList(page, nameSpaces, execContext, actionMapName);
		createActionHierarchy(null, markers);

		processMarkers(page, markers, execContext);

		return markers;
	}

	/**
	 * Find and return a list of all the xml nodes on the page.
	 * 
	 * @param page
	 *            the web page
	 * @return a list of ReplaceMarkers for each node found that uses the
	 *         nameSpace
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws NestedActionException
	 * @throws BadXMLException
	 * @throws DocumentException
	 */
	public List<ReplacementMarker> findMarkers(String page, IExecContext execContext, String actionMapName)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, NestedActionException, BadXMLException
	{

		ActionMarkers actionMarkers = new ActionMarkers();
		List<ReplacementMarker> markers = actionMarkers.getReplacementList(page, execContext, actionMapName);
		createActionHierarchy(null, markers);

		processMarkers(page, markers, execContext);

		return markers;
	}

	/**
	 * Associates child actions with their parents.
	 * <p>
	 * The markers contain the actions and the hierarchy order.
	 * </p>
	 * 
	 * @param parent
	 *            the parent action, will be null if the markers list is root.
	 * @param markers
	 *            the list of markers and their children used to provide the
	 *            hierarchy list for the actions.
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private void createActionHierarchy(BaseAction parent, List<ReplacementMarker> markers)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{

		for (ReplacementMarker marker : markers) {
			if (parent != null) {
				// parent.addChild(marker.getAction());
				SetupBeanFromXML.setProperty(parent, marker.getXMLObject().getElementName(), marker.getAction());
				marker.getAction().setParent(parent);
			}
			createActionHierarchy(marker.getAction(), marker.getNestedMarkers());
		}
	}

	private void processMarkers(String pageContent, List<ReplacementMarker> markers, IExecContext execContext)
			throws NestedActionException
	{

		for (ReplacementMarker marker : markers) {
			BaseAction action = marker.getAction();
			try {
				action.processAction(pageContent, action, execContext);
			} catch (Exception ex) {
				log.error(ex.getMessage()+"\n" + pageContent, ex);
				throw new NestedActionException("Error Processing page ["
						+ StrSubstitutor.replace(getPageName(), execContext) + "]", ex);
			}
		}
	}

	public String replaceMarkers(IExecContext execContext, String pageContent, List<ReplacementMarker> markers) throws NestedActionException
	{

		for (int iLoop = markers.size() - 1; iLoop >= 0; iLoop--) {
			ReplacementMarker marker = markers.get(iLoop);
			if (marker.getAction().isUsedForDisplay()) {
				String replacement = replaceMarkers(execContext, marker.getAction().getReplacementContent(), marker
						.getNestedMarkers());
				if (replacement != null) {
					if (marker.getAction() instanceof ReplacementHandlerAction) {
						replacement = (String) ((ReplacementHandlerAction)marker.getAction()).getReplacementData(execContext, replacement);
					}
					pageContent = marker.getAction()
							.doReplace(pageContent, replacement, marker.getStart(), marker.getEnd());
				}
			}
		}
		return (pageContent);
	}

	public String getRootPath()
	{

		return rootPath;
	}

	public void setRootPath(String rootPath)
	{

		this.rootPath = rootPath;
	}

	public String getPageName()
	{

		return pageName;
	}

	public void setPageName(String pageName)
	{

		this.pageName = pageName;
	}

	public char [][] getNameSpaces()
	{

		return nameSpaces;
	}

	public void setNameSpaces(char [][] nameSpaces)
	{

		this.nameSpaces = nameSpaces;
	}

	public void addNameSpaces(List<String> newNameSpaces) {
		List <String> list = new ArrayList<String>();
		for (int i = 0 ; i < nameSpaces.length; i++) {
			String ns = new String(nameSpaces[i]);
			//if (StringUtils.isNotEmpty(ns)) {
				addNewNameSpace(list, ns);
			//}
		}
		int i = 0;
		for (String name : newNameSpaces) {
			//if (StringUtils.isNotEmpty(name)) {
				addNewNameSpace(list, name);
			//}
		}
		// nameSpaces = new char[list.size()][];
		i = 0;
		for (String name : list) {
			addNameSpace(name);
		}
	}

	public void addNameSpaces(char [][] newNameSpaces) {
		for (int i = 0 ; i < newNameSpaces.length; i++) {
			addNameSpace(newNameSpaces[i]);
		}
	}
	
	private void addNewNameSpace(List <String>list, String name) {
		for (String exists : list) {
			if (exists.equals(name)) {
				return;
			}
		}
        // log.debug("NAMESPACE.ADD:" + name);
		list.add(name); 
	}
	public void addEmptyNameSpace() {
		if (getNameSpaces().length == 0) {
			setNameSpaces(EMPTY_NAMESPACE);	
		} else { 
			for (char [] n : getNameSpaces()) {
				if (n.length == 0) {
					return;	// we already have an empty namespace
				}
			}
			char [] [] ns = new char[getNameSpaces().length+1][];
			int index = 0;
			for (char [] n : getNameSpaces()) {
				ns[index++] = n;
			}
			ns[index] = "".toCharArray();
		}
		
	}

	/**
	 * Add this namespace to the static list of namespaces [] []
	 * 
	 * If this namespace already exists in the list then it wont be added.
	 * @param namespace - the namespace to add
	 */
	public void addNameSpace(String nameSpace) {
		addNameSpace(nameSpace.toCharArray());
	}

	/**
	 * Add this namespace to the static list of namespaces [] []
	 * 
	 * If this namespace already exists in the list then it wont be added.
	 * @param nameSpace - the namespace to add
	 */
	public void addNameSpace(char [] nameSpace) {
		
		char [][] newNameSpaces = new char[nameSpaces.length+1][];
		for (int i = 0 ; i < nameSpaces.length; i++) {
			char [] ns = nameSpaces[i];
			if (nameSpace.toString().equals(ns.toString())) {
				return;		// already exists so don't add it
			}
			newNameSpaces[i] = ns;
		}
		// add new nameSpace here
		newNameSpaces[newNameSpaces.length-1] = nameSpace;
		nameSpaces = newNameSpaces;
	}

}
