package org.xmlactions.pager.actions.form.templates;


public class HtmlIFrame extends HtmlEvents
{

	public HtmlIFrame()
	{

		super(HtmlEnum.label_iframe.getAttributeName());
		setContent("");	// need this to force </iframe>
	}
	/**
	 * Specifies the alignment of an iframe according to surrounding elements
	 * @deprecated. Use styles instead.
	 * <p>
	 * left</br>
	 * right</br>
	 * top</br>
	 * middle</br>
	 * bottom</br>
	 * </p>
	 */
	private String align;
	
	/**
	 * Specifies whether or not to display a border around an iframe
	 * 1 or 0 
	 */
	private String frameborder;
	
	/**
	 * Specifies the height of an iframe
	 * pixels
	 */
	private String height;
	
	/**
	 * Specifies a page that contains a long description of the content of an iframe
	 */
	private String longdesc;

	/**
	 * Specifies the top and bottom margins of an iframe
	 * pixels
	 */ 
	private String marginheight;
	
	/** 
	 * Specifies the left and right margins of an iframe
	 * pixels
	 */ 
	private String marginwidth;

	/**
	 * Specifies the name of an iframe
	 * name
	 */
	private String name;


	/**
	 * Specifies whether or not to display scrollbars in an iframe
	 * yes
	 * no
	 * auto
	 */
	private String scrolling;
	
	/**
	 * Specifies the URL of the document to show in an iframe
	 */
	private String src;

	/**
	 * Specifies the width of an iframe
	 * pixels
	 */
	private String width;
	
	private String allowtransparency;

	public void setAlign(String value)
	{

		put(HtmlEnum.align.getAttributeName(), value);
	}

	public void setFrameborder(String value)
	{

		put(HtmlEnum.frameborder.getAttributeName(), value);
	}

	public void setHeight(String value)
	{

		put(HtmlEnum.height.getAttributeName(), value);
	}

	public void setLongdesc(String value)
	{

		put(HtmlEnum.longdesc.getAttributeName(), value);
	}

	public void setMarginheight(String value)
	{

		put(HtmlEnum.marginheight.getAttributeName(), value);
	}

	public void setMarginwidth(String value)
	{

		put(HtmlEnum.marginwidth.getAttributeName(), value);
	}

	public void setName(String value)
	{

		put(HtmlEnum.name.getAttributeName(), value);
	}

	public void setScrolling(String value)
	{

		put(HtmlEnum.scrolling.getAttributeName(), value);
	}

	public void setSrc(String value)
	{

		put(HtmlEnum.src.getAttributeName(), value);
	}

	public void setWidth(String value)
	{

		put(HtmlEnum.width.getAttributeName(), value);
	}

	public void setAllowtransparency(String allowtransparency) {
		this.allowtransparency = allowtransparency;
	}

	public String getAllowtransparency() {
		return allowtransparency;
	}


}
