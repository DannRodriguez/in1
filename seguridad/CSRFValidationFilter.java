package mx.ine.procprimerinsa.seguridad;

import java.io.IOException;
import java.net.URL;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;
import org.jboss.logging.Logger;

/**
 * Filter in charge of validating each incoming HTTP request about Headers and
 * CSRF token. It is called for all requests to backend destination. We use the
 * approach in which: - The CSRF token is changed after each valid HTTP exchange
 * - The custom Header name for the CSRF token transmission is fixed - A CSRF
 * token is associated to a backend service URI in order to enable the support
 * for multiple parallel Ajax request from the same application - The CSRF
 * cookie name is the backend service name prefixed with a fixed prefix Here for
 * the POC we show the "access denied" reason in the response but in production
 * code only return a generic message !!!
 * 
 * @see "https://www.owasp.org/index.php/Cross-Site_Request_Forgery_(CSRF)_Prevention_Cheat_Sheet"
 * @see "https://wiki.mozilla.org/Security/Origin"
 * @see "https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Set-Cookie"
 * @see "https://chloe.re/2016/04/13/goodbye-csrf-samesite-to-the-rescue/"
 */
public class CSRFValidationFilter implements Filter {

	/**
	 * JVM param name used to define the target origin
	 */
	public static final String TARGET_ORIGIN_JVM_PARAM_NAME = "target.origin";
	
	/**
	 * Logger
	 */
	private static final Logger logger = Logger.getLogger(CSRFValidationFilter.class);

	/**
	 * Application expected deployment domain: named "Target Origin" in OWASP
	 * CSRF article
	 */
	private URL targetOrigin;

	/**
	 * Para saber si la url origen de la última petición POST proviene de un
	 * origen confiable.
	 */
	private boolean esConfiable = false;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		
		httpResp.setHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
		httpResp.setHeader("X-XSS-Protection", "1; mode=block");
		httpResp.setHeader("X-Content-Type-Options", "nosniff");
		httpResp.setHeader("Content-Type", "text/html"); 
		
		httpResp.setHeader("Cache-Control","no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
		httpResp.setHeader("Pragma", "no-cache"); 
		httpResp.setHeader("Expires", "0");
                
		String accessDeniedReason;

		if (httpReq.getMethod().equals("POST")) {

			/* STEP 1: Verifying Same Origin with Standard Headers */
			// Try to get the source from the "Origin" header
			String source = StringEscapeUtils.escapeHtml4(httpReq.getHeader("Origin"));
			
			if (this.isBlank(source)) {
				// If empty then fallback on "Referer" header
				source = StringEscapeUtils.escapeHtml4(httpReq.getHeader("Referer"));
				// If this one is empty too then we trace the event and we block
				// the request (recommendation of the article)...
				if (this.isBlank(source)) {
					accessDeniedReason = "CSRFValidationFilter: ORIGIN and REFERER request headers are both absent/empty so we block the request !";
					logger.warn(accessDeniedReason);
					httpResp.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedReason);
					return;
				}
			}
			
			URL sourceURL = new URL(source);
			
			asignaOrigenConfiable(sourceURL);

			if (esConfiable) {
				// Compare the source against the expected target origin
				
				if (!this.targetOrigin.getProtocol().equals(sourceURL.getProtocol())
						|| !this.targetOrigin.getHost().equals(sourceURL.getHost())
						|| (this.targetOrigin.getPort() != -1
							&& this.targetOrigin.getPort() != sourceURL.getPort())) {
					// One the part do not match so we trace the event and we
					// block the request
					accessDeniedReason = String.format("CSRFValidationFilter: Protocol/Host/Port do not fully matches so we block the request! (%s != %s) ",
									this.targetOrigin, sourceURL);
					logger.warn(accessDeniedReason);
					httpResp.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedReason);
					return;
				}
				chain.doFilter(request, httpResp);

			} else {
				accessDeniedReason = "CSRFValidationFilter: No se pudo asignar una URL de origen confiable en este ambiente.";
				logger.warn(accessDeniedReason);
				httpResp.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedReason);
			}
			
		} else {
			chain.doFilter(request, httpResp);
		}
	}

	/**
	 * Método que asigna la url confiable dependiendo del source
	 * 
	 * @param source
	 *            . Origen del request.
	 */
	public void asignaOrigenConfiable(URL sourceURL) {
		
		try {
			this.targetOrigin = sourceURL;
			esConfiable = true;
			
		} catch (Exception e) {
			logger.error("ERROR CSRFValidationFilter -asignaOrigen: ", e);
		}

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.targetOrigin = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() {
		logger.info("CSRFValidationFilter: Filter shutdown");
	}

	/**
	 * Check if a string is null or empty (including containing only spaces)
	 * 
	 * @param s
	 *            Source string
	 * @return TRUE if source string is null or empty (including containing only
	 *         spaces)
	 */
	private boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}

	
}