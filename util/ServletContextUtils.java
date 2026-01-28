package mx.ine.procprimerinsa.util;

import java.io.File;

import jakarta.servlet.ServletContext;

import org.jboss.logging.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component("servletContextUtils")
@Scope("singleton")
public class ServletContextUtils implements ServletContextAware {

	private static ServletContext servletContext; 
	protected static final Logger LOGGER = Logger.getLogger(ServletContextUtils.class);
	
	@Override
	public void setServletContext(@NonNull ServletContext servletContext) {
		ServletContextUtils.servletContext = servletContext;
	}

	public static ServletContext getServletContext() {
		return servletContext;

	}
	
	public static String getRealRootPath() {
		return servletContext.getRealPath(File.separator);

	}
	
	public static String getRealPath(String path) {
		return servletContext.getRealPath(path);

	}
	
	public static String getRealWEBINFPath() {
		return servletContext.getRealPath("WEB-INF");

	}
	
	public static String getRealWEBINFPath(String path) {
		return servletContext.getRealPath("WEB-INF"+File.separator+path);

	}
	
	public static String getRealResourcesPath() {
		return servletContext.getRealPath("resources");

	}
	
	public static String getRealResourcesPath(String path) {
		return servletContext.getRealPath("resources"+File.separator+path);

	}
	
}
