package org.xmlactions.pager.actions.navigator;

public class NavigatorAPI {

	Navigator navigator;
	
	public NavigatorAPI(String id, String title) {
		if (navigator == null) {
            navigator = new Navigator(id);
			NavBar root = new NavBar();
			root.setId(id);
			root.setTitle(title);
		}
	}
	
	public void addHeading() {
		
	}
	public void addItem(String headingId, SubChild item) {
		
	}
}
