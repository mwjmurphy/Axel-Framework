package axel;

import javax.servlet.http.HttpServlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.xmlactions.web.PagerServlet;
import org.xmlactions.web.conceal.HttpPager;

@Configuration
@ImportResource({"classpath:spring-axel.xml"})
public class AxelConfig {

	@Bean	
	public ServletRegistrationBean<HttpServlet> axelServlet() {
		ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
		HttpPager httpPager = new HttpPager();
		servRegBean.setServlet(new PagerServlet(httpPager));
		servRegBean.addInitParameter("pager.namespace", "axel");
		// servRegBean.addInitParameter("pager.realPath", "/axel");
		servRegBean.addUrlMappings("*.html","*.json", "*.csv");
		servRegBean.setLoadOnStartup(1);
		return servRegBean;
   }
}
