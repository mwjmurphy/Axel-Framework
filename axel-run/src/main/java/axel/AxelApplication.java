package axel;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.web.PagerServlet;
import org.xmlactions.web.RequestExecContext;
import org.xmlactions.web.conceal.HttpPager;

@SpringBootApplication
@RestController
public class AxelApplication {

	private static ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		applicationContext  = SpringApplication.run(AxelApplication.class, args);

		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = applicationContext.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	
}