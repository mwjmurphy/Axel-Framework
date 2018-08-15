package org.xmlactions.pager.actions.form;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.SelfDraw;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.drawing.IDrawParams;


public class FieldRaw extends CommonFormFields implements IDrawParams, SelfDraw {

	private static final Logger log = LoggerFactory.getLogger(FieldCode.class);

	@Override
	public String execute(IExecContext execContext) throws Exception {
		return getContent();
	}

	public Html drawHtml(IExecContext execContext) {
		HtmlDiv div = new HtmlDiv();
		div.setContent(execContext.replace(getContent()));
		return div;
	}

	public String drawHeader(IExecContext execContext) {
		return getHeader_name();
	}

}
