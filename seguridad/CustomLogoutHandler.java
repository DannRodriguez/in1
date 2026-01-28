package mx.ine.procprimerinsa.seguridad;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class CustomLogoutHandler extends SimpleUrlLogoutSuccessHandler {
	
	private String logoutSuccessUrl;
    private String invalidSessionUrl;
    
    public CustomLogoutHandler() {
    	logoutSuccessUrl = "";
    	invalidSessionUrl = "";
    }
    
    @Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
    	Authentication authentication) throws IOException, ServletException {
    	String url;
    	
		if (authentication == null) {
			url = invalidSessionUrl;
			logger.debug("Logout sin autenticar - Redireccionando al usuario a " + url);
		} else {
			url = logoutSuccessUrl;
			logger.debug("Logout autenticado - Redireccionando al usuario a " + url);
		}
		
		setDefaultTargetUrl(url);
		super.onLogoutSuccess(request, response, authentication);
    }
	
	public void setLogoutSuccessUrl(String logoutSuccessUrl) {
		this.logoutSuccessUrl = logoutSuccessUrl;
	}
	
	public void setInvalidSessionUrl(String invalidSessionUrl) {
		this.invalidSessionUrl = invalidSessionUrl;
	}
	
	public String getInvalidSessionUrl() {
		return this.invalidSessionUrl;
	}
}
