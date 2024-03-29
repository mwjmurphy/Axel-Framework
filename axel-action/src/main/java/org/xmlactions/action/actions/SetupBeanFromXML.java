
package org.xmlactions.action.actions;


import java.lang.reflect.InvocationTargetException;


import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.XMLAttribute;
import org.xmlactions.common.xml.XMLObject;

public class SetupBeanFromXML
{
	
	private static final Logger log = LoggerFactory.getLogger(SetupBeanFromXML.class);

	public static BaseAction createAction(XMLObject xo, String innerContent, IExecContext context,
			ReplacementMarker marker, String actionMapName)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException
	{

		
		BaseAction action = context.getActionClass(actionMapName, xo.getElementName());
        if (action != null) {
            for (XMLAttribute att : xo.getAttributes()) {
       			setProperty(action, att.getKey(), att.getValue());
            }

            action.setContent(innerContent);
            action.setReplacementMarker(marker);
		}
		return action;
	}

	public static void setProperty(BaseAction action, String name, Object value)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{

		// log.debug("setProperty action:" + action.getClass().getName() +
		// " name:" + name + " value:" + value);
		try {
			BeanUtils.setProperty(action, name, value);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Unable to set property [" + name + "] on bean ["
					+ action.getClass().getName() + "] with value of [" + value + "] error:" + ex.getMessage(), ex);
		}
		try {
			Object obj = BeanUtils.getProperty(action, name);
			if (obj != null && !value.toString().equals(obj.toString())) {
				throw new IllegalArgumentException("Unable to get matching value for [" + name
						+ "] which has just been set in [" + action.getClass().getName() + "], instead got [" + obj
						+ "] when expected [" + value + "]");
			}
			if (value instanceof BaseAction && !name.equals(BaseAction.CHILD)) {
				((BaseAction) value).setUsedForDisplay(false);
			}
		} catch (Exception ex) {
			try {
				// log.warn(ex.getMessage());
				setProperty(action, BaseAction.CHILD, value);
			} catch (Exception ex2) {
				log.warn("Unable to set property [" + name + "] on bean [" + action.getClass().getName()
						+ "] with value of [" + value + "] error:" + ex.getMessage());	// , ex);
			}
		}
	}

	private Object getProperty(BaseAction action, String name)
	{

		try {
			return BeanUtils.getProperty(action, name);
		} catch (Throwable t) {
			return null;
		}
	}
}
