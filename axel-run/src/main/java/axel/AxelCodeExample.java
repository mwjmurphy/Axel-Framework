package axel;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.web.RequestExecContext;

public class AxelCodeExample {
	
	public String hello(String name) {
		return "Hello " + name;
	}
	
	public String getProjectTitle() {
		IExecContext execContext = RequestExecContext.get();
		return execContext.getString("project.title");
	}
	
	public String getListOf10Beans() {
		ApplicationContext applicationContext = AxelApplication.getApplicationContext();
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for (String beanName : beanNames) {
			sb.append(beanName + "<br/>");
			count++;
			if (count > 10) {
				break;
			}
		}
		return sb.toString();
	}

}
