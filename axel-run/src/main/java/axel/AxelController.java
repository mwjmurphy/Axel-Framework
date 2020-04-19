package axel;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.xmlactions.mapping.json.GsonUtils;
import org.xmlactions.web.PagerServlet;
import org.xmlactions.web.conceal.HttpPager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

@Controller
@RequestMapping
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

//	@RequestMapping("/{page}")
//	public String index(@PathVariable("page") String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileUploadException {
//
//		RequestExecContext.set((IExecContext)AxelApplication.getApplicationContext().getBean("pager.execContext"));
//
//		String location = new File(page).getAbsolutePath();
//		StringBuilder sb = new StringBuilder();
//		sb.append("pathInfo:" + request.getContextPath() + "<br/>\n");
//		sb.append("queryString:" + request.getQueryString() + "<br/>\n");
//		sb.append("uri:" + request.getContextPath() + "<br/>\n");
//		sb.append("method:" + request.getProtocol() +"<br/>\n");
//		// sb.append("contextPath:" + _request.getContextPath()+"\n");
//		logger.debug("sb:{}", sb.toString());
//		HttpPager pager = new HttpPager();
//		pager.setNamespace("axel");
//		if (this.config != null) {
//			pager.init(this.config);
//		}
//		pager.setupExecContext(request, response);
//		pager.processPage(request, response, page);
//		
//		return "Greetings from Axel and Spring Boot! location [" + location + "]\n" + sb.toString();
//	}
	
	@Bean
	public ServletRegistrationBean axel() {
		HttpPager pager = new HttpPager();
		pager.setNamespace("axel");
		PagerServlet pagerServlet = new PagerServlet(pager);
	    return new ServletRegistrationBean(pagerServlet, "/axel/*");
	}

	private void test() {
		GsonUtils gu;
		String jsonString = "{\"name\":\"Fred\"}";
		Gson gson = new Gson();
		JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);
	}

}
