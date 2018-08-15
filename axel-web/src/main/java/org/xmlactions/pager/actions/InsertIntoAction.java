
package org.xmlactions.pager.actions;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.stackedpages.StackedPage;

public class InsertIntoAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(InsertIntoAction.class);
	
	private static String pagerAction = "insert_into";

	/** Name of file we want to load */
	private String page;

	/** Where the web pages are stored */
	private String path;

	/** Namespace used for pager actions. eg. &lt;pager:action...&gt; */
	private String namespace;

	/** When adding pages we may want to remove the outer html element. */
	private boolean remove_html = true;	// true or false, yes or no.

	
	/** The key is the replacement marker that is set in the page. */ 
	private String key;


	public String execute(IExecContext execContext) throws Exception
	{

		if (page == null) {
			throw new IllegalArgumentException("The attribute 'page' is missing from the action '" + pagerAction + "'");
		}
		if (key == null) {
			throw new IllegalArgumentException("The attribute 'key' is missing from the action '" + pagerAction + "'");
		}
		
		if (path == null) {
			path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
		}
		if (namespace == null) {
			namespace = (String) execContext.get(ActionConst.PAGE_NAMESPACE_BEAN_REF);
			if (namespace == null || namespace.trim().length() == 0) {
				namespace = new String(ActionConst.DEFAULT_PAGER_NAMESPACE[0]);
			}
		}
		
		StackedPage stackedPage = new StackedPage(getPage(), getKey(), getPath(), getNamespace(), isRemove_html());
		StackedPage.addStackedPage(execContext, stackedPage);
		return "";
	}

	public String getPage()
	{

		return page;
	}

	public void setPage(String page)
	{

		this.page = page;
	}

	public String getNamespace()
	{

		return namespace;
	}

	public void setNamespace(String namespace)
	{

		this.namespace = namespace;
	}

	public String getPath()
	{

		return path;
	}

	public void setPath(String path)
	{

		this.path = path;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isRemove_html() {
		return remove_html;
	}

	public void setRemove_html(boolean remove_html) {
		this.remove_html = remove_html;
	}
}
