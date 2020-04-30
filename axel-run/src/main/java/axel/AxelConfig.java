package axel;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.xmlactions.web.PagerServlet;
import org.xmlactions.web.conceal.HttpPager;

@Configuration
@ImportResource({"classpath:spring-axel.xml"})
public class AxelConfig {

    private static final Logger log = LoggerFactory.getLogger(AxelConfig.class);

	@Bean	
	public ServletRegistrationBean<HttpServlet> axelServlet() {
		ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
		HttpPager httpPager = new HttpPager();
		servRegBean.setServlet(new PagerServlet(httpPager));
		servRegBean.addInitParameter("pager.namespace", "axel");
		servRegBean.addUrlMappings("*.html","*.json", "*.csv", "*.js", "*.css"); //  "*.png", "*.jpg");
		servRegBean.setLoadOnStartup(1);
		return servRegBean;
   }
}
