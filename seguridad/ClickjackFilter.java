package mx.ine.procprimerinsa.seguridad;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.Filter;

public class ClickjackFilter implements Filter {

	private String mode = "DENY";
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest)request;
        res.setHeader("X-FRAME-OPTIONS", mode);
        res.setHeader("X-XSS-Protection", "1; mode=block");
        res.setHeader("X-Content-Type-Options", "nosniff");
        
        res.setHeader("Cache-Control","no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
        res.setHeader("Pragma", "no-cache");
        res.setHeader("Expires", "0");

        chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) {
		String configMode = filterConfig.getInitParameter("mode");
		if (configMode != null) {
			mode = configMode;
		}
	}

}
