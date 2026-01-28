package mx.ine.procprimerinsa.seguridad;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

public class XSSPreventionFilter implements Filter {
	
	private Logger logger = Logger.getLogger(XSSPreventionFilter.class);
	
	class XSSRequestWrapper extends HttpServletRequestWrapper {
		
		private Map<String, String[]> sanitizedQueryString;
		
		public XSSRequestWrapper(HttpServletRequest request) {
			super(request);
		}
		
		@Override
	    public String getParameter(String parameter) {
	        String value = super.getParameter(parameter);
	        return stripXSS(value);
	    }
		
		
		public String[] getParameterValues(String parameter) {
		    String[] values = super.getParameterValues(parameter);
		    
		    if (values == null) {
		        return null;
		    }
		    
		    int count = values.length;
		    String[] encodedValues = new String[count];
		    for (int i = 0; i < count; i++) {
		        encodedValues[i] = stripXSS(values[i]);
		    }
		    return encodedValues;
		}
		
		@Override
		public Enumeration<String> getParameterNames() {
			return Collections.enumeration(getParameterMap().keySet());
		}
		
		@Override
		public Map<String,String[]> getParameterMap() {
			if(sanitizedQueryString == null) {
				Map<String, String[]> res = new HashMap<>();
				Map<String, String[]> originalQueryString = super.getParameterMap();
				if(originalQueryString!=null) {
					for (String key : originalQueryString.keySet()) {
						String[] rawVals = originalQueryString.get(key);
						String[] snzVals = new String[rawVals.length];
						for (int i=0; i < rawVals.length; i++) {
							snzVals[i] = stripXSS(rawVals[i]);
						}
						res.put(stripXSS(key), snzVals);
					}
				}
				sanitizedQueryString = res;
			}
			return sanitizedQueryString;
		}
		 
	    @Override
	    public String getHeader(String name) {
	        String value = super.getHeader(name);
	        return stripXSS(value);
	    }
		    
		/**
		 * Removes all the potentially malicious characters from a string
		 * @param value the raw string
		 * @return the sanitized string
		 */
		private String stripXSS(String value) {
			String cleanValue = null;
			if (value != null) {
//				cleanValue = Normalizer.normalize(value, Normalizer.Form.NFD);
				cleanValue = value;
 
				// Avoid null characters
				cleanValue = cleanValue.replaceAll("\0", "");
				
				// Avoid anything between script tags
				Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid anything in a src='...' type of expression
				scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
 
				scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Remove any lonesome </script> tag
				scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
 
				// Remove any lonesome <script ...> tag
				scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
 
				// Avoid eval(...) expressions
				scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid expression(...) expressions
				scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid javascript:... expressions
				scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid vbscript:... expressions
				scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid onload= expressions
				scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid scriptalert expressions
				scriptPattern = Pattern.compile("<scriptalert(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				//Avoid script expressions
				scriptPattern = Pattern.compile("script", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				//Avoid script expressions
				scriptPattern = Pattern.compile("alert", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				//scriptPattern = Pattern.compile("[.*]/", Pattern.CASE_INSENSITIVE);
				//cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				scriptPattern = Pattern.compile(";", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");					
				
			}
			return cleanValue;
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("XSSPreventionFilter: init()");
	}
 
	@Override
	public void destroy() {
		logger.debug("XSSPreventionFilter: destroy()");
	}

	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse)response;
		res.setHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
		res.setHeader("X-XSS-Protection", "1; mode=block");
		res.setHeader("X-Content-Type-Options", "nosniff");
		res.setHeader("Content-Type", "text/html");
		 
		res.setHeader("Cache-Control","no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
        res.setHeader("Pragma", "no-cache"); 
        res.setHeader("Expires", "0");
        
		XSSRequestWrapper wrapper = new XSSRequestWrapper((HttpServletRequest)request);	
        String uriAux = wrapper.getRequestURI();
        String uri = wrapper.stripXSS(uriAux);
        if(uriAux != null 
        		&& uri != null
        		&& !uriAux.equalsIgnoreCase(uri)){
    		try {
    			if(!res.isCommitted()){
    				res.resetBuffer();
        			res.setStatus(400);
            		PrintWriter out = res.getWriter();
        		    res.setContentType("application/json");
        		    out.println("{\"Error\": \"Ocurri\u00F3 un error inesperado\",");
        		    out.println("\"Mensaje\": \"Se encontraron simbolos no permitidos.\"}");
        		    out.flush();
        		    out.close();
        		    res.setContentType(out.toString());  
    			}
			} catch (Exception e) {
				logger.error("ERROR XSSPreventionFilter -doFilter: ", e);
			}
        }
		chain.doFilter(wrapper, res);
	}
	
}