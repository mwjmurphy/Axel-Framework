
package org.xmlactions.pager.actions;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.text.Text;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.Link;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlA;
import org.xmlactions.pager.actions.form.templates.HtmlBlank;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlIFrame;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTr;

public class PopupAction extends CommonFormFields implements SelfDraw
{

	private static Logger log = LoggerFactory.getLogger(PopupAction.class);
	
	private static final String DISPLAY_SELF = "self", DISPLAY_OTHER="other";
	
	private String script_before, script_after;
	
	// if this is set then its the position value for the style i.e. position:relative
	private String position;
	
    private String is_allowed;	// if not set true than dont show.

	/**
	 * How the popup should be displayed.  Either from the popup itself or referencing
	 * to an element id such as a div that exists on the page. 
	 * 
	 */
	private String display = DISPLAY_SELF;
	
	public String execute(IExecContext execContext) throws Exception
	{
		validate(execContext);
		
    	String value = execContext.replace(getIs_allowed());
    	if (Text.isFalse(value)) {
    		return null;
    	}

		setTheme(execContext.getThemes().getTheme(getTheme_name(execContext)));

		Html html =  buildPresentation(execContext);
		
		return html == null ? null : html.toString();
	}
	
	public Html drawHtml(IExecContext execContext) {

		String value = execContext.replace(getIs_allowed());
    	if (Text.isFalse(value)) {
    		return new HtmlBlank();
    	}
		
		validate(execContext);

		setTheme(execContext.getThemes().getTheme(getTheme_name(execContext)));
		
		Html html = buildPresentation(execContext);
		
		return html;
		
	}
	
	public Html drawHtmlForForm(IExecContext execContext) {

		String value = execContext.replace(getIs_allowed());
    	if (Text.isFalse(value)) {
    		return new HtmlBlank();
    	}
		
		validate(execContext);

		setTheme(execContext.getThemes().getTheme(getTheme_name(execContext)));
		
		Html html = buildPresentationForForm(execContext);
		
		return html;
		
	}
	
	private String getId(IExecContext execContext) {
		return execContext.replace(getId());
	}
	
	private Html buildPresentation(IExecContext execContext) {
	    Theme theme = getTheme(execContext);
        boolean foundSubmitLink = false;
        HtmlDiv htmlDiv = new HtmlDiv();
        
        HtmlTable table = htmlDiv.addTable();
        HtmlTr tr = table.addTr();
		for (Link link : getLinks()) {
			//***
			//* This requires the db FieldList
			//* 
			//if (getParent() != null && getParent() instanceof org.xmlactions.pager.actions.form.FieldList) {
			//	Link l = (Link)link.clone();
			//	if (l != null) {
			//		link = l;
			//	}
			//}
			//***
			if (link.isSubmit()) {
				foundSubmitLink = true;
				if ("iframe".equals(getDisplay())){
					//link.setHref(getHref());
					String actionScript = "show('"+ getId(execContext) + "');";
					if (StringUtils.isNotEmpty(link.getActionScript())) {
						actionScript += link.getActionScript();
					}
					link.setActionScript(actionScript);
					if (StringUtils.isEmpty(link.getTarget())) {
						// make the link display in the iframe
						link.setTarget(getId(execContext));
					}
				} else {
	                //link.setActionScript("populateIdFromAjaxLoad('" + getId(execContext) + "','" + link.getUri(execContext)
	                //        + "','');return false;");
	                link.setActionScript("populateIdFromAjaxLoad('" + getId(execContext) + "','" + link.getUri(execContext)
	                        + "',''," + getScript_before() + "," + getScript_after() + ");return false;");
					if (StringUtils.isEmpty(link.getUri())) {
						link.setUri("javascript:hide('" + getId(execContext) + "');");
					} else {
	                    // htmlForm.setAction(link.getUri());
					}
				}
			}
			HtmlTd td = tr.addTd();
            Html html = link.draw(execContext, theme);
            td.addChild(html);
		}
		if ("iframe".equals(getDisplay())) {
            HtmlIFrame iframe = new HtmlIFrame();
            htmlDiv.addChild(iframe);
            // iframe.setClazz(theme.getValue(ThemeConst.POPUP_FRAME.toString()));
            iframe.setAllowtransparency("true");
            iframe.setFrameborder("0");
            iframe.setId(getId(execContext));
            iframe.setName(getId(execContext));
            iframe.setSrc(getHref());
            iframe.setWidth(getWidth());
            iframe.setHeight(getHeight());
           	String style = "display:none;";
           	if (StringUtils.isNotEmpty(getPosition())) {
           		style += "position:" + getPosition() + ";";
           	} else {
           		style += "position:absolute;";
           	}
           	if (StringUtils.isNotEmpty(getX())) {
           		style+="left:" + getX() + ";";
           	}
           	if (StringUtils.isNotEmpty(getY())) {
           		style+="top:" + getY() + ";";
           	}
           	if (StringUtils.isNotEmpty(getZindex())) {
           		style+="z-index:" + getZindex() + ";";
           	}
           	if (StringUtils.isNotEmpty(style)) {
           		iframe.setStyle(style);
           	}
		} else if (!"other".equals(getDisplay())) {
            HtmlDiv div = htmlDiv.addDiv(theme);
            div.setClazz(theme.getValue(ThemeConst.POPUP_FRAME.toString()));
            div.setId(getId(execContext));
            if (StringUtils.isNotEmpty(getX()) || StringUtils.isNotEmpty(getY())) {
            	String style;
            	if (StringUtils.isNotEmpty(getPosition())) {
            		style= "position:" + getPosition() + ";";
            	} else {
            		style= "position:absolute;";
            	}
            	if (StringUtils.isNotEmpty(getX())) {
            		style+="left:" + getX() + ";";
            	}
            	if (StringUtils.isNotEmpty(getY())) {
            		style+="top:" + getY() + ";";
            	}
            	div.setStyle(style);
            }
		} else {
	        HtmlDiv div = htmlDiv.addDiv(theme);
            div.setClazz(theme.getValue(ThemeConst.POPUP_FRAME.toString()));
            div.setId(getId(execContext));
            if (StringUtils.isNotEmpty(getX()) || StringUtils.isNotEmpty(getY())) {
            	String style;
            	if (StringUtils.isNotEmpty(getPosition())) {
            		style= "position:" + getPosition() + ";";
            	} else {
            		style= "position:absolute;";
            	}
            	if (StringUtils.isNotEmpty(getX())) {
            		style+="left:" + getX() + ";";
            	}
            	if (StringUtils.isNotEmpty(getY())) {
            		style+="top:" + getY() + ";";
            	}
            	div.setStyle(style);
            }
		}
		
		if (foundSubmitLink == false) {
			Validate.isTrue(false, "No 'submit' link has been set for this popup action.");
		}
		return htmlDiv;
	}
	
	private Html buildPresentationForForm(IExecContext execContext) {
	    Theme theme = getTheme(execContext);
        boolean foundSubmitLink = false;
        HtmlDiv htmlDiv = new HtmlDiv();
        
		for (Link link : getLinks()) {
			//***
			//* This requires the db FieldList
			//* 
			//if (getParent() != null && getParent() instanceof org.xmlactions.pager.actions.form.FieldList) {
			//	Link l = (Link)link.clone();
			//	if (l != null) {
			//		link = l;
			//	}
			//}
			//***
			if (link.isSubmit()) {
				foundSubmitLink = true;
				if ("iframe".equals(getDisplay())){
					//link.setHref(getHref());
					String actionScript = "show('"+ getId(execContext) + "');";
					if (StringUtils.isNotEmpty(link.getActionScript())) {
						actionScript += link.getActionScript();
					}
					link.setActionScript(actionScript);
					if (StringUtils.isEmpty(link.getTarget())) {
						// make the link display in the iframe
						link.setTarget(getId(execContext));
					}
				} else {
	                //link.setActionScript("populateIdFromAjaxLoad('" + getId(execContext) + "','" + link.getUri(execContext)
	                //        + "','');return false;");
	                link.setActionScript("populateIdFromAjaxLoad('" + getId(execContext) + "','" + link.getUri(execContext)
	                        + "',''," + getScript_before() + "," + getScript_after() + ");return false;");
					if (StringUtils.isEmpty(link.getUri())) {
						link.setUri("javascript:hide('" + getId(execContext) + "');");
					} else {
	                    // htmlForm.setAction(link.getUri());
					}
				}
			}
            Html html = link.draw(execContext, theme);
            htmlDiv.addChild(html);
		}
		if ("iframe".equals(getDisplay())) {
            HtmlIFrame iframe = new HtmlIFrame();
            htmlDiv.addChild(iframe);
            // iframe.setClazz(theme.getValue(ThemeConst.POPUP_FRAME.toString()));
            iframe.setAllowtransparency("true");
            iframe.setFrameborder("0");
            iframe.setId(getId(execContext));
            iframe.setName(getId(execContext));
            iframe.setSrc(getHref());
            iframe.setWidth(getWidth());
            iframe.setHeight(getHeight());
           	String style = "display:none;";
           	if (StringUtils.isNotEmpty(getPosition())) {
           		style += "position:" + getPosition() + ";";
           	} else {
           		style += "position:absolute;";
           	}
           	if (StringUtils.isNotEmpty(getX())) {
           		style+="left:" + getX() + ";";
           	}
           	if (StringUtils.isNotEmpty(getY())) {
           		style+="top:" + getY() + ";";
           	}
           	if (StringUtils.isNotEmpty(getZindex())) {
           		style+="z-index:" + getZindex() + ";";
           	}
           	if (StringUtils.isNotEmpty(style)) {
           		iframe.setStyle(style);
           	}
		} else if (!"other".equals(getDisplay())) {
            HtmlDiv div = htmlDiv.addDiv(theme);
            div.setClazz(theme.getValue(ThemeConst.POPUP_FRAME.toString()));
            div.setId(getId(execContext));
            if (StringUtils.isNotEmpty(getX()) || StringUtils.isNotEmpty(getY())) {
            	String style;
            	if (StringUtils.isNotEmpty(getPosition())) {
            		style= "position:" + getPosition() + ";";
            	} else {
            		style= "position:absolute;";
            	}
            	if (StringUtils.isNotEmpty(getX())) {
            		style+="left:" + getX() + ";";
            	}
            	if (StringUtils.isNotEmpty(getY())) {
            		style+="top:" + getY() + ";";
            	}
            	div.setStyle(style);
            }
		} else {
	        HtmlDiv div = htmlDiv.addDiv(theme);
            div.setClazz(theme.getValue(ThemeConst.POPUP_FRAME.toString()));
            div.setId(getId(execContext));
            if (StringUtils.isNotEmpty(getX()) || StringUtils.isNotEmpty(getY())) {
            	String style;
            	if (StringUtils.isNotEmpty(getPosition())) {
            		style= "position:" + getPosition() + ";";
            	} else {
            		style= "position:absolute;";
            	}
            	if (StringUtils.isNotEmpty(getX())) {
            		style+="left:" + getX() + ";";
            	}
            	if (StringUtils.isNotEmpty(getY())) {
            		style+="top:" + getY() + ";";
            	}
            	div.setStyle(style);
            }
		}
		
		if (foundSubmitLink == false) {
			Validate.isTrue(false, "No 'submit' link has been set for this popup action.");
		}
		return htmlDiv;
	}

	public void validate(IExecContext execContext) {
		if (StringUtils.isEmpty(getId(execContext))) {
            throw new IllegalArgumentException("Popup is missing required id attribute. @see pager:link.");
		}
        if (getLinks()== null || getLinks().size() == 0) {
            throw new IllegalArgumentException("Popup is missing required link or links. @see pager:link.");
        }
        /*
        if (StringUtils.isEmpty(getName())) {
            throw new IllegalArgumentException("Missing name uri attribute in popup.");
        }
        */
        /*
        if (StringUtils.isNotEmpty(getXml_file_name()) && StringUtils.isNotEmpty(getXml_ref())) {
            throw new IllegalArgumentException("Both the xml_file_name and xml_ref attribute are set. Only one of these attributes must be set, not both");
        }
		if (StringUtils.isEmpty(getXslt_file_name())) {
			throw new IllegalArgumentException("Missing xslt_file_name attribute in tranform");
		}
		if (path == null) {
			path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
		}
		*/
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}

	public void setScript_before(String script_before) {
		this.script_before = script_before;
	}

	public String getScript_before() {
		return script_before;
	}

	public void setScript_after(String script_after) {
		this.script_after = script_after;
	}

	public String getScript_after() {
		return script_after;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPosition() {
		return position;
	}

	public String getIs_allowed() {
		if (is_allowed == null) {
			return "true";
		}
		return is_allowed;
	}

	public void setIs_allowed(String is_allowed) {
		this.is_allowed = is_allowed;
	}

	//@Override
	public String drawHeader(IExecContext execContext) {
		return getHeader_name();
	}

}
