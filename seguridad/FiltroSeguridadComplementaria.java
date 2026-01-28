package mx.ine.procprimerinsa.seguridad;

import java.io.IOException;
import java.util.regex.Pattern;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.ine.procprimerinsa.as.ASParametrosInterface;
import mx.ine.procprimerinsa.dto.DTOGoogleRecaptcha;
import mx.ine.procprimerinsa.dto.DTOGoogleRecaptchaResponse;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

public class FiltroSeguridadComplementaria extends UsernamePasswordAuthenticationFilter {

	private static final Logger log = Logger.getLogger(FiltroSeguridadComplementaria.class);
	
	private static final String CONT_REQUERIDA = "La contrase\u00f1a es obligatoria, favor de verificarla";
	private static final String USUARIO_REQUERIDO = "El usuario es obligatorio, favor de verificarlo";
	private static final String CODIGO_REQUERIDO = "El captcha es obligatorio, favor de verificarlo";
	private static final String CONT_USUARIO_INCORRECTO = "El usuario y/o la contrase\u00f1a son incorrectos, favor de introducirlos nuevamente.";
	private static final String CODIGO_INCORRECTO = "El captcha es incorrecto, favor de introducirlo nuevamente";
	private static final String INTENTOS_EXCEDIDOS = "Excediste el n\u00famero de intentos, espera " + RecaptchaAttemptService.getExpireTimeMinuts() + " minutos y verifica de nuevo.";
	
	private static final Integer VALIDA_CAPTCHA = 1;
	private static final Integer VALIDA_NUMERO_INTENTOS = 2;
	private static final Integer VALIDA_CAPTCHA_Y_NUMERO_INTENTOS = 3;
	
	private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");
	
	private String succesUrl = "";
	private String errorUrl = "";
	private String captchaParamName = "";
	private SimpleUrlAuthenticationSuccessHandler simpleUrlSucces = new SimpleUrlAuthenticationSuccessHandler();
	private SimpleUrlAuthenticationFailureHandler simpleUrlFailure = new SimpleUrlAuthenticationFailureHandler();
	
	@Autowired
	@Qualifier("asParametros")
	private ASParametrosInterface asParametros;
	
	@Autowired
	@Qualifier("googleRecaptcha")
	private DTOGoogleRecaptcha googleRecaptcha;
	
	@Autowired
	private RecaptchaAttemptService recaptchaAttemptService;

	public FiltroSeguridadComplementaria() {
		super();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		String password = super.obtainPassword(request);
		if (password == null || password.isEmpty()) {
			throw new BadCredentialsException(CONT_REQUERIDA);
		}
		return password;
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		String userName = super.obtainUsername(request);
		if (userName == null || userName.isEmpty()) {
			throw new BadCredentialsException(USUARIO_REQUERIDO);
		}
		return userName;
	}
	
	private Integer obtieneParametroValidacion() {
		try {
			return Integer.valueOf(asParametros.obtenerParametro(
												0, 
												0, 
												0, 
												0, 
												Constantes.PARAMETRO_CAPTCHA_SISTEMA));
		} catch (Exception e) {
			log.error("ERROR FiltroSeguridadComplementaria -obtieneParametroValidacion: ", e);
			return VALIDA_CAPTCHA;
		}
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		Authentication auth;
		String ipUsuario = Utilidades.getClientIP(request);
		Integer parametroValidacion = obtieneParametroValidacion();
		
		if ((parametroValidacion.equals(VALIDA_NUMERO_INTENTOS)
			|| parametroValidacion.equals(VALIDA_CAPTCHA_Y_NUMERO_INTENTOS))
			&& recaptchaAttemptService.isBlocked(ipUsuario)) {
            throw new BadCredentialsException(INTENTOS_EXCEDIDOS);
        }

		if (parametroValidacion.equals(VALIDA_CAPTCHA)
			|| parametroValidacion.equals(VALIDA_CAPTCHA_Y_NUMERO_INTENTOS)) {
			String codigoRecaptcha = request.getParameter(this.captchaParamName);
			
			if (!StringUtils.hasLength(codigoRecaptcha)
	        	|| !RESPONSE_PATTERN.matcher(codigoRecaptcha).matches()) {
	            throw new BadCredentialsException(CODIGO_REQUERIDO);
	        }
			
			String serviceResponse = HttpsConnector.getResponse(googleRecaptcha.getUrl(), 
																googleRecaptcha.getRequest() + codigoRecaptcha);
			
			if(serviceResponse == null
				|| serviceResponse.isBlank()) {
				throw new BadCredentialsException(CODIGO_INCORRECTO);
			}
			
			DTOGoogleRecaptchaResponse googleRecaptchaResponse = new Gson().fromJson(serviceResponse, 
																					DTOGoogleRecaptchaResponse.class);
			
			if(!googleRecaptchaResponse.isSuccess()) {
				if (googleRecaptchaResponse.hasClientError()) {
                    recaptchaAttemptService.reCaptchaFailed(ipUsuario);
                }
				throw new BadCredentialsException(CODIGO_INCORRECTO);
			}
		}
		
		try {
			auth = super.attemptAuthentication(request, response);
			if (!auth.isAuthenticated()) {
				throw new BadCredentialsException(CONT_USUARIO_INCORRECTO);
			}
		} catch (BadCredentialsException e) {
			if (e.getMessage().equalsIgnoreCase("Bad credentials"))
				throw new BadCredentialsException(CONT_USUARIO_INCORRECTO);
			else
				throw new BadCredentialsException(e.getLocalizedMessage());
		}

		return auth;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		log.info("La autentificacion fue exitosa" );
		recaptchaAttemptService.reCaptchaSucceeded(Utilidades.getClientIP(request));
		simpleUrlSucces.setDefaultTargetUrl(succesUrl);
		simpleUrlSucces.setAlwaysUseDefaultTargetUrl(true);
		this.setAuthenticationSuccessHandler(simpleUrlSucces);
		response.setHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("Content-Type", "text/html"); 
        
		response.setHeader("Cache-Control","no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache"); 
        response.setHeader("Expires", "0");
        
		super.successfulAuthentication(request, response, chain, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
			AuthenticationException failed) throws IOException, ServletException {
		log.info("La autentificacion fue errónea");
		recaptchaAttemptService.reCaptchaFailed(Utilidades.getClientIP(request));
		simpleUrlFailure.setDefaultFailureUrl(errorUrl);
		this.setAuthenticationFailureHandler(simpleUrlFailure);
		super.unsuccessfulAuthentication(request, response, failed);
	}

	public String getSuccesUrl() {
		return succesUrl;
	}

	public void setSuccesUrl(String succesUrl) {
		this.succesUrl = succesUrl;
	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

	public String getCaptchaParamName() {
		return captchaParamName;
	}

	public void setCaptchaParamName(String captchaParamName) {
		this.captchaParamName = captchaParamName;
	}
}
