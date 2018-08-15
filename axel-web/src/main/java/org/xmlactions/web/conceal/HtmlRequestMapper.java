
package org.xmlactions.web.conceal;


import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.web.HttpParam;

/**
 * Maps the parameters from a HttpRequest (post or get) into a standard HashMap.
 * 
 * @author MichaelMurphy
 * 
 */
public class HtmlRequestMapper
{

	private static final Logger log = LoggerFactory.getLogger(HtmlRequestMapper.class);

	public static final String UPLOAD_FILE_NAME = "upload_file_name";

	public static final String UPLOAD_FILE_SIZE = "upload_file_size";

	public static final String UPLOAD_FILE_DATA = "file";

	public static final String UPLOAD_FILE_PATH = "path";
	
	public static final String UPLOAD_STORAGE_CONFIG_REF = "config_storage_ref";
	
	public static final String UPLOAD_TABLE_NAME = "table_name";
	
	public static final String UPLOAD_FIELD_NAME = "field_name";
	public static final String UPLOAD_FIELD_FILE_NAME = "field_file_name";
	public static final String UPLOAD_FIELD_FK_REF = "field_fk_ref";
	public static final String UPLOAD_FIELD_FK_REF_VALUE = "field_fk_ref_value";

	int fileUploadMaxSize;

	/**
	 * 
	 * @param fileUploadMaxSize
	 *            the maximum file size for an upload.
	 */
	HtmlRequestMapper(int fileUploadMaxSize)
	{

		this.fileUploadMaxSize = fileUploadMaxSize;
	}

    /**
     * Get the request parameters. If this is a 'post' page the parameters come
     * from the body else they come from the request.getParameter().
     * 
     * @param request
     *            the HttpServletRequest
     * @return an array of KeyPairs with the parameters.
     * @throws IOException
     * @throws FileUploadException
     * @throws java.lang.Exception
     */
    public Map<String, Object> getRequestParamsAsMap(HttpServletRequest request) throws IOException,
            FileUploadException {
        Map<String, Object> map = new HashMap<String, Object>();

        //
        // Get all parameters.
        //
        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            String [] values = request.getParameterValues(key);
            if (values != null) {
            	if (values.length == 1) {
                    map.put(key, values[0]);
            	} else {
                    map.put(key, values);
            	}
            }
        }
        if (this.is_multipart_form_data(request) == true) {

            DiskFileItemFactory factory = new DiskFileItemFactory();

            // maximum size that will be stored in memory
            // factory.setSizeThreshold(fileUploadMaxSize);

            ServletFileUpload upload = new ServletFileUpload(factory);
            // maximum size before a FileUploadException will be thrown
            if (fileUploadMaxSize > 0) {
                upload.setSizeMax(fileUploadMaxSize);
            }

            List<FileItem> list = upload.parseRequest(request);
            for (FileItem fi : list) {
                if (fi.getContentType() != null) {
                    byte buffer[] = new byte[fi.getInputStream().available()];
                    fi.getInputStream().read(buffer);
                    // TODO emm, need to keys for the buffer for a file upload
                    // parameters.add(new KeyPair(fi.getFieldName(),
                    // fi.getName(), buffer));
                    // log.debug(fi.getFieldName() + " = " + fi.getName());
                    map.put(UPLOAD_FILE_NAME, fi.getName());
                    map.put(UPLOAD_FILE_SIZE, fi.getSize());
                    // map.put(fi.getFieldName(), buffer);
                    map.put(UPLOAD_FILE_DATA, buffer);
                    if (log.isDebugEnabled()) {
                        if (buffer.length > 100) {
                            log.debug(fi.getFieldName() + " = " + new String(buffer).substring(0, 100));
                        } else {
                            log.debug(fi.getFieldName() + " = " + new String(buffer));
                        }
                    }
                } else {
                    log.debug(fi.getFieldName() + " = " + fi.getString());
                    map.put(fi.getFieldName(), fi.getString());
                }
            }
        }
        return (map);
    }

    /**
     * Get the request parameters. If this is a 'post' page the parameters come
     * from the body else they come from the request.getParameter().
     * 
     * @param request
     *            the HttpServletRequest
     * @return an List of HttpParam created from the incoming request.
     * @throws IOException
     * @throws FileUploadException
     * @throws java.lang.Exception
     */
    public List<HttpParam> getRequestParamsAsVector(HttpServletRequest request) throws IOException,
            FileUploadException {

        List<HttpParam> paramList = new ArrayList<HttpParam>();

        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            String [] values = request.getParameterValues(key);
            if (values != null) {
            	for (String value : values) {
                    paramList.add(new HttpParam(key, value));
                	if (log.isDebugEnabled()) {
                		log.debug(key + " = " + value);
                	}
            	}
            }
        }

        if (this.is_multipart_form_data(request) == true) {

            DiskFileItemFactory factory = new DiskFileItemFactory();

            // maximum size that will be stored in memory
            // factory.setSizeThreshold(fileUploadMaxSize);

            ServletFileUpload upload = new ServletFileUpload(factory);
            // maximum size before a FileUploadException will be thrown
            if (fileUploadMaxSize > 0) {
                upload.setSizeMax(fileUploadMaxSize);
            }

            List<FileItem> list = upload.parseRequest(request);
            for (FileItem fi : list) {
                if (fi.getContentType() != null) {
                    byte buffer[] = new byte[fi.getInputStream().available()];
                    fi.getInputStream().read(buffer);
                    // TODO emm, need to keys for the buffer for a file upload
                    // parameters.add(new KeyPair(fi.getFieldName(),
                    // fi.getName(), buffer));
                    // log.debug(fi.getFieldName() + " = " + fi.getName());
                    paramList.add(new HttpParam(UPLOAD_FILE_NAME, fi.getName()));
                    paramList.add(new HttpParam(UPLOAD_FILE_SIZE, fi.getSize()));
                    // map.put(fi.getFieldName(), buffer);
                    paramList.add(new HttpParam(UPLOAD_FILE_DATA, buffer));
                    if (log.isDebugEnabled()) {
                        if (buffer.length > 100) {
                            log.debug(fi.getFieldName() + " = " + new String(buffer).substring(0, 100));
                        } else {
                            log.debug(fi.getFieldName() + " = " + new String(buffer));
                        }
                    }
                } else {
                    log.debug(fi.getFieldName() + " = " + fi.getString());
                    paramList.add(new HttpParam(fi.getFieldName(), fi.getString()));
                }
            }
        }
        return (paramList);
    }

	/**
	 * Checks if the HttpRequest is a post containing multipart upload data
	 * 
	 * @param request
	 *            the HttpServletRequest
	 * @return true if contains upload data else false
	 */
	public boolean is_multipart_form_data(HttpServletRequest request)
	{

		try {
			if (StringUtils.isEmpty(request.getContentType())) {
				return (false);
			}
			if ("multipart/form-data".equalsIgnoreCase(request.getContentType()
					.substring(0, "multipart/form-data".length())) == true) {
				return (true);
			}
			if ("application/octet-stream".equalsIgnoreCase(request.getContentType().substring(0,
					"application/octet-stream".length())) == true) {
				return (true);
			}
		} catch (Exception ex) {
			log.info(ex.getMessage());
		}
		return (false);
	}

}
