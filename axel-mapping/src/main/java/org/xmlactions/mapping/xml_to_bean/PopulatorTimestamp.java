package org.xmlactions.mapping.xml_to_bean;


import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;


import org.apache.commons.beanutils.BeanUtils;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.mapping.KeyValue;
import org.xmlactions.mapping.MappingConstants;

public class PopulatorTimestamp extends AbstractPopulateClassFromXml {

    // yyyy-MM-dd HH:mm:ss.SSS

    public void performAction(List<KeyValue> keyvalues, Object object, String propertyName, Object value, XMLObject xo)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException,
            IllegalArgumentException, NoSuchFieldException {

        String format = getKeyValue(keyvalues, MappingConstants.TIME_FORMAT);
        Timestamp ts = new Timestamp(buildTime(format, "" + value));
        BeanUtils.setProperty(object, propertyName, ts);
	}

    private long buildTime(String format, String date) {
        try {
            return new SimpleDateFormat(format).parse(date).getTime();
        } catch (Exception ex) {
            throw new IllegalArgumentException("The [" + format + "] is incorrect for [" + date + "]", ex);
        }
    }
	
}
