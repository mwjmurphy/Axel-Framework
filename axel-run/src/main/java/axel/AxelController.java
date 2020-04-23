package axel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.xmlactions.mapping.json.GsonUtils;
import org.xmlactions.web.PagerServlet;
import org.xmlactions.web.conceal.HttpPager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

@RestController
public class AxelController implements ServletContextAware, ServletConfigAware {

	private static Logger logger = LoggerFactory.getLogger(AxelController.class);

	private ServletContext context;
	private ServletConfig config;

	@Override
	public void setServletConfig(ServletConfig servletConfig) {
		this.config = servletConfig;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}

	/** Handle all files from root path */
	@RequestMapping(value = "/{filename}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void getFileAsByteArray(@PathVariable("filename") String filename, HttpServletResponse response) throws IOException {
		String fn = filename;
		InputStream in = new FileInputStream(fn);
	    IOUtils.copy(in, response.getOutputStream());
	}

	/** Handle all files from one folder */
	@RequestMapping(value = "/{folder}/{filename}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void getFile2AsByteArray(@PathVariable("folder") String folder, @PathVariable("filename") String filename, HttpServletResponse response) throws IOException {
		String fn = folder + "/" + filename;
		InputStream in = new FileInputStream(fn);
	    IOUtils.copy(in, response.getOutputStream());
	}
}
