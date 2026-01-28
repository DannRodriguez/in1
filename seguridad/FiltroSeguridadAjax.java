package mx.ine.procprimerinsa.seguridad;

import java.io.IOException;

import org.owasp.encoder.Encode;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.util.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class FiltroSeguridadAjax implements InvalidSessionStrategy {
	
	private static final String FACES_REQUEST_HEADER = "faces-request";
	private String invalidSessionUrl;
	
	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException { 
		boolean ajaxRedirect = "partial/ajax".equals(request.getHeader(FACES_REQUEST_HEADER));
		if (ajaxRedirect) {
			String contextPath = request.getContextPath();
			String redirectUrl = contextPath + this.invalidSessionUrl;
			String ajaxRedirectXml = createAjaxRedirectXml(redirectUrl);
			response.setContentType("text/xml");
			response.getWriter().write(ajaxRedirectXml);
		} else {
			String requestURI = getRequestUrl(request);
			
			HttpSession session = request.getSession(false);
			if(session != null) session.invalidate();
			
			request.getSession();
			
			response.sendRedirect(requestURI);
		}
	}
	
	private String getRequestUrl(HttpServletRequest request) {
		StringBuilder requestURL = new StringBuilder();
		requestURL.append(request.getRequestURL());
		String queryString = request.getQueryString();
		if (StringUtils.hasText(queryString)) {
			requestURL.append("?")
					.append(queryString);
		}
		
		return Encode.forJava(requestURL.toString());
	}
	
	private String createAjaxRedirectXml(String redirectUrl) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
				+ "<partial-response><redirect url=\"" 
				+ redirectUrl + "\"></redirect></partial-response>";
	}
	
	public void setInvalidSessionUrl(String invalidSessionUrl) {
		this.invalidSessionUrl = invalidSessionUrl;
	}
	
}
