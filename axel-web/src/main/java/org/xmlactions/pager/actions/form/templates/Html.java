package org.xmlactions.pager.actions.form.templates;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ThemeConst;



public class Html extends HashMap<String, String> {

	private static final Logger logger = LoggerFactory.getLogger(Html.class);
	
	private List<Html> children = new ArrayList<Html>();

	private String content;

	private String label;

	public Html(String label)
	{

		this.label = label;
	}
	
	public Html add(String label) {
		Html html = new Html(label);
		this.addChild(html);
		return html;
	}

	public void setChildren(List<Html> children)
	{

		this.children = children;
	}

	public List<Html> getChildren()
	{

		return children;
	}

	public void addChild(Html child)
	{

		getChildren().add(child);
	}

	public void setClazz(String value)
	{

		put(HtmlEnum.clazz.getAttributeName(), value);
	}

	public void setDir(String value)
	{

		put(HtmlEnum.dir.getAttributeName(), value);
	}


	public void setId(String value)
	{

		put(HtmlEnum.id.getAttributeName(), value);
	}

    public void setLang(String value) {

        put(HtmlEnum.lang.getAttributeName(), value);
    }

    /**
     * Might want to use readonly instead.
     * @param value
     */
    public void setDisabled(String value) {

        put(HtmlEnum.disabled.getAttributeName(), value);
    }

    public void setPattern(String value) {

        put(HtmlEnum.pattern.getAttributeName(), value);
    }

    public void setPlaceholder(String value) {

        put(HtmlEnum.placeholder.getAttributeName(), value);
    }

    public void setReadonly(String value) {

        put(HtmlEnum.readonly.getAttributeName(), value);
    }

    public void setRequired(String value) {

        put(HtmlEnum.required.getAttributeName(), value);
    }

	public void setStyle(String value)
	{
		put(HtmlEnum.style.getAttributeName(), value);
	}

	public String getStyle()
	{
		return get(HtmlEnum.style.getAttributeName());
	}

	public void setTitle(String value)
	{

		put(HtmlEnum.title.getAttributeName(), value);
	}

	public void setTarget(String value)
	{

		put(HtmlEnum.target.getAttributeName(), value);
	}

	public void setXml_lang(String value)
	{

		put(HtmlEnum.xml_lang.getAttributeName(), value);
	}


	public String toString()
	{

		StringBuilder sb = new StringBuilder();
        if (label != null && label.length() > 0) {
            sb.append("<" + label + " ");
            for (String key : this.keySet()) {
            	Object o = this.get(key);
            	if (o != null) {
            		sb.append(key + "=\"" + this.get(key) + "\" ");
            	}
            }
            if (getChildren().size() > 0 || content != null) {
                sb.append(">");
                if (content != null) {
                    sb.append(getContent());
                }
                for (Html html : getChildren()) {
                	if (html != null) {
                		sb.append(html.toString());
                	} else {
                		logger.debug("null Html");
                	}
                	
                }
                sb.append("</" + label + ">");
            } else {
                //sb.append(">");
                //sb.append("</" + label + ">");
            	// Special case for a div. Can't close a div with a "/>" must use "></div>"
            	if (this instanceof HtmlDiv && content == null) {
            		sb.append("></" + label + ">");
            	} else {
            		sb.append("/>");
            	}
            }
        } else {
            if (content != null) {
                sb.append(getContent());
            }
		}
		return sb.toString();
	}

	public void setContent(String content)
	{

		this.content = content;
	}

	public String getContent()
	{

		return content;
	}

	public HtmlTable addTable()
	{

		HtmlTable table = new HtmlTable();
		addChild(table);
		return table;
	}

	public HtmlTable addTable(Theme theme)
	{
		HtmlTable table = addTable();
		table.setClazz(theme.getValue(ThemeConst.TABLE.toString()));
		return table;
	}

    public HtmlTable addTable(Theme theme, ThemeConst themeConst) {
        HtmlTable table = addTable();
        table.setClazz(theme.getValue(themeConst.toString()));
        return table;
    }

    public HtmlTable addTable(Theme theme, String themeKey) {
        HtmlTable table = addTable();
        table.setClazz(theme.getValue(themeKey));
        return table;
    }

	public HtmlTr addTr()
	{
		HtmlTr tr = new HtmlTr();
		addChild(tr);
		return tr;
	}

    public HtmlTr addTr(Theme theme, ThemeConst themeConst)
 {
        HtmlTr tr = addTr();
        tr.setClazz(theme.getValue(themeConst.toString()));
        return tr;
    }

    public HtmlTr addTr(Theme theme) {
        HtmlTr tr = addTr();
        tr.setClazz(theme.getValue(ThemeConst.ROW.toString()));
        return tr;
    }

	public HtmlTr addTr(Theme theme, String themeKey)
	{
		HtmlTr tr = addTr();
		tr.setClazz(theme.getValue(themeKey));
		return tr;
	}

	public HtmlTh addTh()
	{
		HtmlTh th = new HtmlTh();
		addChild(th);
		return th;
	}

	public HtmlTh addTh(Theme theme)
	{
		HtmlTh th = addTh();
		th.setClazz(theme.getValue(ThemeConst.HEADER.toString()));
		return th;
	}
	public HtmlTh addTh(Theme theme, String themeKey)
	{
		HtmlTh th = addTh();
		th.setClazz(theme.getValue(themeKey));
		return th;
	}


	public HtmlTd addTd()
	{
		HtmlTd td = new HtmlTd();
		addChild(td);
		return td;
	}

	public HtmlTd addTd(Theme theme)
	{
		HtmlTd td = addTd();
		td.setClazz(theme.getValue(ThemeConst.TD.toString()));
		return td;
	}

    public HtmlTd addTd(Theme theme, ThemeConst themeConst) {
        HtmlTd td = addTd();
        td.setClazz(theme.getValue(themeConst.toString()));
        return td;
    }

    public HtmlTd addTd(Theme theme, String themeKey) {
        HtmlTd td = addTd();
        td.setClazz(theme.getValue(themeKey));
        return td;
    }

	public HtmlPre addPre()
	{
		HtmlPre pre = new HtmlPre();
		addChild(pre);
		return pre;
	}
	public HtmlPre addPre(Theme theme, String themeKey)
	{
		HtmlPre pre = addPre();
		pre.setClazz(theme.getValue(themeKey));
		return pre;
	}

	public HtmlInput addInput(Theme theme)
	{
		HtmlInput input = new HtmlInput(theme);
		addChild(input);
		return input;
	}

	public HtmlDiv addDiv(Theme theme)
	{
		HtmlDiv div = new HtmlDiv(theme);
		addChild(div);
		return div;
	}

	public HtmlSpan addSpan()
	{
		HtmlSpan html = new HtmlSpan();
		addChild(html);
		return html;
	}

	public HtmlTextArea addTextArea(Theme theme)
	{
		HtmlTextArea textArea = new HtmlTextArea(theme);
		addChild(textArea);
		return textArea;
	}

}
