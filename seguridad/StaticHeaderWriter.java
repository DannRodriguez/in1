package mx.ine.procprimerinsa.seguridad;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.web.header.HeaderWriter;

public class StaticHeaderWriter implements HeaderWriter{
	
	public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
		
		response.setHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
		response.setHeader("X-XSS-Protection", "1; mode=block");
		response.setHeader("X-Content-Type-Options", "nosniff");
        
		response.setHeader("Cache-Control","no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
	}

}
