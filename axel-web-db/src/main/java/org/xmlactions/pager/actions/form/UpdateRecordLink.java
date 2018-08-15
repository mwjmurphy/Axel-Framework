

package org.xmlactions.pager.actions.form;


import java.util.ArrayList;




import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.text.Text;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.pager.actions.form.Link;
import org.xmlactions.pager.actions.form.StorageBaseFormAction;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlA;
import org.xmlactions.pager.actions.form.templates.HtmlBlank;


/**
 * Builds an update record presentation that allows the user to enter details for the record.
 * <p>
 * The update fields come from the database schema.
 * </p>
 * <p>
 * parameters / attributes will build the html attributes needed for display.
 * </p>
 * 
 * @author mike
 * 
 */
public class UpdateRecordLink extends StorageBaseFormAction implements ILink
{

	private static final Logger log = LoggerFactory.getLogger(UpdateRecordLink.class);

	private java.util.List<Field> fields = new ArrayList<Field>();

	private String presentation_name;

	private String id;

	private String header_name;

	private String tooltip;

	private String href; // where the link goes to

	private String display_as; // can be either link or button, default = link.
	
	private String pk_value;	// use this to build the where clause.

    private String is_allowed;	// if not set true than dont show.

	// ==> Image attributes start
	private String image;		// can also display an image as part of the link
	
	private String image_pos;	// if there is text this can be used to set the position of the image on the left or the right of the text.
	
	private String border;		// width of border if using an image
	
	private String image_width;	// may want to specify the width of the image
	
	private String image_height;	// may want to specify the height image
	// <== Image attributes end

	public String execute(IExecContext execContext)
	{

		return null;
	}

	public Html draw(IExecContext execContext, Theme theme)
	{

		return buildLink(execContext, theme, getId()); // + "_" + row.getAttributeValueAsString("index"));
	}

	public Html buildLink(IExecContext execContext, Theme theme, String rowId)
	{

		String value = execContext.replace(getIs_allowed());
    	if (Text.isFalse(value)) {
    		return new HtmlBlank();
    	}


		Link link = Link.newLink(this, getPresentation_name());

		link.setUri(getHref());
		link.setAction(null);
		Html html = link.draw(execContext, theme);
		if (html instanceof HtmlA) {
			((HtmlA)html).setOnClick("return(showValidationErrors(updateRecord('" + rowId + "','"
					+ buildFieldList(execContext) + "')));");
		}
		return html;
		
	}

	private String buildFieldList(IExecContext execContext)
	{

		StringBuilder sb = new StringBuilder();

		// only used for validation
		sb.append(buildParam(ClientParamNames.TABLE_NAME_MAP_ENTRY, getTable_name()));
		sb.append(buildParam("&" + ClientParamNames.PK_VALUE, getPk_value()));
        sb.append(buildParam("&" + ClientParamNames.STORAGE_CONFIG_REF, getStorage_config_ref()));

		for (Field field : getFields()) {
			String content = StrSubstitutor.replace(field.getContent(), execContext);
			log.debug("field.content:" + field.getContent() + " content:" + content);

			sb.append("&" + buildParam(field.getName(), content));
		}
		return sb.toString();
	}

	private String buildParam(String key, String value)
	{

		return key + "=" + value;
	}

	public void setHeader_name(String header_name)
	{

		this.header_name = header_name;
	}

	public String getHeader_name()
	{

		return header_name;
	}

	public void setTooltip(String tooltip)
	{

		this.tooltip = tooltip;
	}

	public String getTooltip()
	{

		return tooltip;
	}

	public void setUri(String uri)
	{

		this.href = uri;
	}

	public String getUri()
	{

		return href;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}


	public void setDisplay_as(String display_as)
	{

		this.display_as = display_as;
	}

	public String getDisplay_as()
	{

		return display_as;
	}

	public void setField(Field field)
	{

		fields.add(field);
	}

	public Field getField()
	{

		if (fields.size() > 0) {
			return fields.get(fields.size() - 1);
		}
		return null;
	}

	public java.util.List<Field> getFields()
	{

		return fields;
	}

	public String getId()
	{

		if (StringUtils.isEmpty(id)) {
			return this.getFirstValueFound("id");
		}
		return id;
	}

	public void setId(String id)
	{

		this.id = id;
	}

	public void setPresentation_name(String presentation_name)
	{

		this.presentation_name = presentation_name;
	}

	public String getPresentation_name()
	{

		return presentation_name;
	}

	public void setPk_value(String pk_value) {
		this.pk_value = pk_value;
	}

	public String getPk_value() {
		return pk_value;
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

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getBorder() {
		return border;
	}

	public void setImage_pos(String image_pos) {
		this.image_pos = image_pos;
	}

	public String getImage_pos() {
		return image_pos;
	}

	public void setImage_width(String image_width) {
		this.image_width = image_width;
	}

	public String getImage_width() {
		return image_width;
	}

	public void setImage_height(String image_height) {
		this.image_height = image_height;
	}

	public String getImage_height() {
		return image_height;
	}

}