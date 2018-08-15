
package org.xmlactions.action.actions;


import java.util.ArrayList;
import java.util.List;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.config.IExecContext;


public abstract class BaseAction
{

	private static Logger log = LoggerFactory.getLogger(BaseAction.class);
	
	private static final boolean ignoreErrors = true;

	private BaseAction parent;

	/** setter / getter name = content */
	public static final String CONTENT = "content";

	/** setter / getter name = child */
	public static final String CHILD = "child";

	/**
	 * Default option is true. Will be set to false if action is used as another
	 * actions property excluding a "child" property.
	 */
	private boolean usedForDisplay = true;

	/**
	 * Executes an action
	 * 
	 * @param execContext
	 *            the application execution context
	 * @return a String for replacement and to execute any child actions, else
	 *         null to skip over child actions.
	 * @throws Exception
	 */
	public abstract String execute(IExecContext execContext) throws Exception;

	/** actions can be IPageAction or any XML */
	private List<BaseAction> actions = new ArrayList<BaseAction>();

	private String content = "";

	private String replacementContent = null;

	/**
	 * This is the ReplacementMarker that this action was created from.
	 */
	ReplacementMarker replacementMarker;

	public List<BaseAction> getActions()
	{

		return actions;
	}

	public void setActions(List<BaseAction> actions)
	{

		this.actions = actions;
	}

	public void setAction(BaseAction action)
	{

		setChild(action);
	}

	public void setChild(BaseAction action)
	{

		actions.add(action);
	}

	public void addChild(BaseAction action)
	{

		log.debug("add:" + action.toString());
		actions.add(action);
	}

	/**
	 * Get the last child in the list or null if there are no actions/elements
	 * in the list.
	 * 
	 * @return last child or null if the list is empty.
	 */
	public BaseAction getChild()
	{

		if (actions.size() == 0)
			return null;
		return actions.get(actions.size() - 1);
	}

	public void setContent(String content)
	{

		// this.content += content;
		this.content = content;
	}

	public String getContent()
	{

		return content;
	}

	public String getReplacementContent()
	{

		return replacementContent;
	}

	public void setReplacementContent(String replacementContent)
	{

		this.replacementContent = replacementContent;
	}

	public ReplacementMarker getReplacementMarker()
	{

		return replacementMarker;
	}

	public void setReplacementMarker(ReplacementMarker replacementMarker)
	{

		this.replacementMarker = replacementMarker;
	}

	public void processAction(String pageContent, BaseAction action, IExecContext execContext) throws Exception
	{

		String result = null;
		try {
			result = action.execute(execContext);
		} catch (Exception ex) {
			if (ignoreErrors == true) {
				log.error("Log Exception", ex);
				result = "";
			} else {
				throw ex;
			}
		}
		if (result != null) {
			action.setReplacementContent(result);
			for (BaseAction child : action.getActions()) {
				try {
					processAction(pageContent, child, execContext);
				} catch (Exception ex) {
					if (ignoreErrors == true) {
						log.error("Log Exception", ex);
						result = "";
					} else {
						throw ex;
					}
				}
			}
		}
	
	}

	public StringBuilder doReplace(String srcContent)
	{

		StringBuilder p1 = new StringBuilder();
		if (srcContent.length() == 0) {
			p1.append(content == null ? "" : content);
		} else {
			p1.append(srcContent.substring(0, getReplacementMarker().getStart()));
			p1.append(content == null ? "" : content);
			p1.append(srcContent.substring(getReplacementMarker().getEnd()));
		}
		return (p1);
	}

	public String doReplace(String srcContent, String replacementContent, int start, int end)
	{

		StringBuilder p1 = new StringBuilder();
		// if (replacementContent == null || replacementContent.equals("null")){
		// p1.append(srcContent);
		// }
		if (srcContent == null || srcContent.equals("null")) {
			p1.append(replacementContent);
		} else if (srcContent.length() == 0) {
			p1.append(replacementContent);
		} else {
			if ((replacementContent == null || replacementContent.equals("null"))
					&& (start >= srcContent.length() || end >= srcContent.length())) {
				p1.append(srcContent);
			} else {
				// Validate.isTrue(start <= srcContent.length(), "start [" +
				// start + "] out of range for [" + srcContent + "]");
				// Validate.isTrue(end <= srcContent.length(), "end [" + end +
				// "] out of range for [" + srcContent + "]");
				try {
					p1.append(srcContent.substring(0, start));
					p1.append(replacementContent == null ? "" : replacementContent);
					if (end < srcContent.length()) {
						p1.append(srcContent.substring(end));
					}
				} catch (Exception ex) {
					log.error("srcContent.len:" + srcContent.length() + " start:" + start + " end:" + end
							+ "\nsrcContent:" + srcContent + "\nreplacementContent:" + replacementContent + "\n"
							+ ex.getMessage(), ex);
				}
			}
		}
		return (p1.toString());
	}

	public void setUsedForDisplay(boolean usedForDisplay)
	{

		this.usedForDisplay = usedForDisplay;
	}

	/**
	 * @return true if this action should be replaced on to the page
	 */
	public boolean isUsedForDisplay()
	{

		return usedForDisplay;
	}

	public void setParent(BaseAction parent)
	{

		this.parent = parent;
	}

	public BaseAction getParent()
	{

		return parent;
	}

	/**
	 * Gets the first value found for the named property starting with this.getParent().
	 * <p>
	 * Returns when it finds a baseAction with the named property and it's value
	 * is not empty. Will continue the search upwards through each parent until
	 * it reaches a null parent.
	 * </p>
	 * 
	 * @param propertyName
	 * @return a not empty value or null if not found.
	 */
	static int inCount = 0;
	public String getFirstValueFound(String propertyName)
	{
		inCount++;
		if (log.isDebugEnabled()) {
			log.debug("inCount:" + inCount + " propertyName:" + propertyName + " class:" + this.getClass().getSimpleName());
		}
		if (this.getParent() != null) {
			BaseAction baseAction = this.getParent();
		
			while (baseAction != null) {
				try {
					String value = BeanUtils.getProperty(baseAction, propertyName);
					if (!StringUtils.isEmpty(value)) {
						inCount--;
						return value;
					}
				} catch (Exception ex) {
					// ignore, just keep going
				}
				baseAction = baseAction.getParent();
			}
		}
		inCount--;
		return null;
	}
}
