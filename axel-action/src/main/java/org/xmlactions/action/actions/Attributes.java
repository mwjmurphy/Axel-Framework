package org.xmlactions.action.actions;


import java.util.List;

import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.XMLAttribute;

/**
 * All attributes for this element are stored in the XMLObject.
 * <p>
 * Use this to store any attribute of any name and value./
 * </p>
 * @author mike.murphy
 *
 */
public class Attributes extends BaseAction {

	@Override
	public String execute(IExecContext execContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<XMLAttribute> getAttributes() {
		return getReplacementMarker().getXMLObject().getAttributes();
	}

}
