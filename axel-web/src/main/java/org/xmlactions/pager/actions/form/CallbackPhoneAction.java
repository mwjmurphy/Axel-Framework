
package org.xmlactions.pager.actions.form;


import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.email.ProcessCallbackPhone;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlForm;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTextArea;
import org.xmlactions.pager.actions.form.templates.HtmlTh;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.config.PagerConstants;
import org.xmlactions.pager.drawing.html.DrawHTMLHelper;

public class CallbackPhoneAction extends CommonFormFields implements FormDrawing
{

	private static Logger log = LoggerFactory.getLogger(CallbackPhoneAction.class);

    private String email_config_ref;
    
    private IExecContext execContext;

	public String execute(IExecContext execContext) throws Exception
	{
		setExecContext(execContext);
        Validate.notNull(getId(), "Missing 'id' from the configuration for 'callback_phone'");

        Validate.notNull(getEmail_config_ref(),
                "Missing 'email_config_ref' from the configuration for 'callback_phone'");

        Theme theme = execContext.getThemes().getTheme(getTheme_name(execContext));
		
	    HtmlForm form = new HtmlForm();
        form.setAction("return showValidationErrors(processEmailCallback(captureInputsFromElement('" + getId() + "')))");
        HtmlTable table = startFrame(theme);
        form.addChild(table);

        HtmlTr tr = table.addTr();
        HtmlTd td = tr.addTd();
        HtmlTable innerTable = td.addTable(theme);
        {
	        // name 
	        tr = innerTable.addTr();
	        HtmlTh th = tr.addTh(theme);
	        th.setAlign("right");
	        th.setContent(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_CALLBACK_PHONE_NAME));
	        td = tr.addTd(theme);
	        td.setAlign("left");
	        td.setClazz(theme.getValue(ThemeConst.EMAIL_TD.toString()));
	        HtmlInput input = td.addInput(theme);
	        input.setType("text");
	        input.setSize("40");
	        input.setMaxlength("100");
	        input.setName(ClientParamNames.CALLBACK_NAME);
	
	
	        // phone
	        tr = innerTable.addTr();
	        tr.setClazz(theme.getValue(ThemeConst.EMAIL_TR.toString()));
	        th = tr.addTh(theme);
	        th.setAlign("right");
	        th.setClazz(theme.getValue(ThemeConst.EMAIL_HEADER.toString()));
	        th.setContent(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_CALLBACK_PHONE_NUMBER));
	        td = tr.addTd(theme);
	        td.setAlign("left");
	        td.setClazz(theme.getValue(ThemeConst.EMAIL_TD.toString()));
	        input = td.addInput(theme);
	        input.setType("text");
	        input.setSize("20");
	        input.setMaxlength("40");
	        input.setName(ClientParamNames.CALLBACK_PHONE);

	        // email
	        tr = innerTable.addTr();
	        tr.setClazz(theme.getValue(ThemeConst.EMAIL_TR.toString()));
	        th = tr.addTh(theme);
	        th.setAlign("right");
	        th.setClazz(theme.getValue(ThemeConst.EMAIL_HEADER.toString()));
	        th.setContent(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_CALLBACK_EMAIL_ADDRESS));
	        td = tr.addTd(theme);
	        td.setAlign("left");
	        td.setClazz(theme.getValue(ThemeConst.EMAIL_TD.toString()));
	        input = td.addInput(theme);
	        input.setType("text");
	        input.setSize("40");
	        input.setMaxlength("100");
	        input.setName(ClientParamNames.CALLBACK_EMAIL);

	        // message
	        tr = innerTable.addTr();
	        tr.setClazz(theme.getValue(ThemeConst.EMAIL_TR.toString()));
	        th = tr.addTh(theme);
	        th.setAlign("right");
	        th.setClazz(theme.getValue(ThemeConst.EMAIL_HEADER.toString()));
	        th.setContent(PagerConstants.getLocalizedString(execContext, PagerConstants.LANG_CALLBACK_MESSAGE));
	        td = tr.addTd(theme);
	        td.setAlign("left");
	        td.setClazz(theme.getValue(ThemeConst.EMAIL_TD.toString()));
	        HtmlTextArea textArea = td.addTextArea(theme);
	        textArea.setRows("10");
	        textArea.setCols("40");
	        textArea.setName(ClientParamNames.CALLBACK_MESSAGE);
	        textArea.setContent("");

        }
        
        boolean foundSubmitLink = false;
		for (Link link : getLinks()) {
			if (link.isSubmit()) {
				link.setActionScript("return showValidationErrors(processEmailCallback(captureInputsFromElement('" + getId() + "')))");
				foundSubmitLink = true;
				if (StringUtils.isEmpty(link.getUri())) {
					link.setUri("javascript:hide('" + getId() + "');");
				} else {
					form.setAction(link.getUri());
				}
			}
		}
		if (foundSubmitLink == false) {
			Validate.isTrue(false, "No 'submit' link has been set for this Callback action.");
		}
		
		table.addChild(DrawHTMLHelper.buildLinksAndButtons(execContext, this, theme, "right"));

        return form.toString();
	}

    public void setEmail_config_ref(String email_config_ref) {
        this.email_config_ref = email_config_ref;
    }

    public String getEmail_config_ref() {
        return email_config_ref;
    }

	public void setExecContext(IExecContext execContext) {
		this.execContext = execContext;
	}

	public IExecContext getExecContext() {
		return execContext;
	}

	public List<HtmlInput> getHiddenFields() {
		java.util.List<HtmlInput> inputs = new ArrayList<HtmlInput>();
		HtmlInput input = new HtmlInput();
		input.setType("hidden");
		input.setName(ClientParamNames.EMAIL_CONFIG_REF);
		input.setValue(getEmail_config_ref());
		inputs.add(input);
		return inputs;
	}


    /**
     * Starts a drawing frame (table) , adding width, any hidden fields and
     * title.
     * 
     * @param form
     * @return
     */
    private HtmlTable startFrame(Theme theme) {

        HtmlTable table = new HtmlTable();
        // ===
        // build table with headers
        // ===
        table.setId(getId());
        if (isVisible()) {
            table.setClazz(theme.getValue(ThemeConst.EMAIL_BORDER.toString()));
        } else {
            table.setClazz(theme.getValue(ThemeConst.EMAIL_BORDER.toString(), "hide"));
        }
        if (StringUtils.isNotEmpty(getWidth())) {
            table.setWidth(getWidth());
        }

        // ===
        // Add Any Hidden Fields
        // ===
        for (HtmlInput input : getHiddenFields()) {
            table.addChild(input);
        }
        // ===
        // Add Frame Title
        // ===
        if (StringUtils.isNotEmpty(getTitle())) {
            HtmlTr tr = table.addTr();
            HtmlTh th = tr.addTh();
            tr.setClazz(theme.getValue(ThemeConst.EMAIL_BORDER.toString()));
            th.setClazz(theme.getValue(ThemeConst.EMAIL_TITLE.toString()));
            th.setContent(getTitle());
        }
        return table;

    }
}
