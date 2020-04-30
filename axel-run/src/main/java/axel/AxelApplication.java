package axel;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;
import org.xmlactions.web.conceal.HttpPager;

@SpringBootApplication
public class AxelApplication {

    private static final Logger log = LoggerFactory.getLogger(AxelApplication.class);
    
	private static ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		applicationContext  = SpringApplication.run(AxelApplication.class, args);
		
		if(args.length > 0) {
			HttpPager.setRealPath(args[0]);
		} else {
			HttpPager.setRealPath(".");
		}
		


//		String[] beanNames = applicationContext.getBeanDefinitionNames();
//		Arrays.sort(beanNames);
//		for (String beanName : beanNames) {
//			System.out.println(beanName);
//		}
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	
}