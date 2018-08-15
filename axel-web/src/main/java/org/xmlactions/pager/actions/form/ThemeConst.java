
package org.xmlactions.pager.actions.form;

public enum ThemeConst
{
	EMPTY("empty"),
	BODY("body"),
	BORDER("border"),
	HEADER("header"),
	TITLE("title"),
	LEGEND("legend"),
	BOX("box"),
	BAR("bar"),
	ROW("row_odd"),
	ROW_ODD("row_odd"),
	ROW_EVEN("row_even"),
	LINK("link"),
	BUTTON("button"),
	MENU("menu"),
	TABLE("table"),
	TD("td"),
	SPACING("spacing"),
	INPUT_TEXT("input.text"),
	INPUT_TEXTAREA("input.textarea"),
	INPUT_BUTTON("input.button"),
	INPUT_CHECK("input.check"),
	INPUT_SELECT("input.select"),
	INPUT_LINK("input.link"),
	INPUT_LINK_BUTTON("input.link_button"),
    INPUT_LINK_MENU("input.link_menu"),
    VIEW_LABEL("view_label"),
    VIEW_CONTENT("view_content"),
    FILE_VIEWER_HEADER("file_viewer.header"),
    FILE_VIEWER_LINE_NO("file_viewer.line_no"),
    FILE_VIEWER_CONTENT("file_viewer.content"),
    WIN_MINMAX("win_minmax"),
    WIN_SEARCH("win_search"),
    MENUBAR("menubar"),
    MENU_TABLE("menutable"),
    MENU_FONT("menu_font"),
    MENU_DOWN_ARROW("menu_down_arrow"),
    MENU_RIGHT_ARROW("menu_right_arrow"),
    SUBMENU("submenu"),
    SUBMENU_FONT("submenu_font"),
    SUBMENUITEM("submenuitem"),
    NAVBAR("navbar"),
    NAV_TABLE("navtable"),
    NAV_FONT("nav_font"),
    NAV_DOWN_ARROW("nav_down_arrow"),
    NAV_RIGHT_ARROW("nav_right_arrow"),
    NAVCHILD("navchild"),
    NAVCHILD_FONT("navchild_font"),
    NAVOPTION("navoption"),
    NAVOPTION_BORDER("navoption_border"),
    NAVOPTION_FONT("navoption_font"),
    NAVSUBCHILD("navsubchild"),
    NAVSUBCHILD_FONT("navsubchild_font"),
	
	//TR("tr"), // gone
	//TH("th"),	// gone
	//TH_BOLD("th.bold"),	// gone
	IMG("img"),
	PAGE_EDIT("input.page_edit"),
	TITLE_BAR("title.bar"),
	SEARCH_SELECT("search_select"),
    
	LIST_BORDER("list_border"),
    LIST_TABLE("list_table"),
    LIST_TITLE("list_title"),
    LIST_ROW("list_row"),
    LIST_ROW_ODD("list_row_odd"),
	LIST_ROW_EVEN("list_row_even"),
    LIST_HEADER("list_th"),
    LIST_TH("list_th"),
	LIST_TD("list_td"),
    
    LISTCP_BORDER("listcp_border"),
	LISTCP_TABLE("listcp_table"),
    LISTCP_TITLE("listcp_title"),
    LISTCP_TR("listcp_tr"),
    LISTCP_TD("listcp_td"),
    
    MEDIA_BACKGROUND("media_background"),
    MEDIA_FOREGROUND("media_foreground"),
    MEDIA_START("media_start"),
    MEDIA_BORDER("media_border"),
    MEDIA_REVERSE("media_reverse"),
    MEDIA_FORWARD("media_forward"),
    MEDIA_END("media_end"),
    MEDIA_GO("media_go"),
    
    
    SEARCH_BORDER("search_border"),
    SEARCH_TABLE("search_table"),
    SEARCH_TITLE("search_title"),
    SEARCH_HEADER("search_header"),
    SEARCH_TR("search_tr"),
    SEARCH_TD("search_td"),

    ADDEDIT_BORDER("addedit_border"),
    ADDEDIT_TITLE("addedit_title"),
    
    EMAIL_BORDER("email_border"),
    EMAIL_TABLE("email_table"),
    EMAIL_TITLE("email_title"),
    EMAIL_HEADER("email_header"),
    EMAIL_TR("email_tr"),
    EMAIL_TD("email_td"),

    HIGHLIGHT_XML_ELEMENT("highlight.xml.element"),
    HIGHLIGHT_XML_ATTRIBUTE("highlight.xml.attribute"),
    HIGHLIGHT_XML_CONTENT("highlight.xml.content"),
    
    POPUP_FRAME("popup_frame"),
    
    FLOAT_LEFT("float_left"),
    FLOAT_RIGHT("float_right"),
    FLOAT_CLEAR("float_clear"),

	;

	private String theme;

	ThemeConst(String ref)
	{

		this.theme = ref;
	}

	public String toString()
	{

		return theme;
	}
}
