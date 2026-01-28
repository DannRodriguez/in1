package mx.ine.procprimerinsa.al.impl;

import java.io.Serializable;

import jakarta.servlet.http.HttpServletRequest;

import mx.ine.procprimerinsa.dto.DTOUsuarioLogin;
import mx.ine.procprimerinsa.bsd.BSDBitacoraInterface;
import mx.ine.procprimerinsa.dto.db.DTOBitacoraAcceso;
import mx.ine.procprimerinsa.util.Utilidades;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class RegistroBitacora implements ApplicationListener<ApplicationEvent>, Serializable {
	
	private static final long serialVersionUID = 6718409739022848735L;
	private static final Logger logger = Logger.getLogger(RegistroBitacora.class);

	@Autowired
	@Qualifier("bsdBitacora")
	private transient BSDBitacoraInterface bsdBitacora;

	@Override
	public void onApplicationEvent(@NonNull ApplicationEvent event) {
		try {
			if (event instanceof InteractiveAuthenticationSuccessEvent) {
				DTOBitacoraAcceso bitacora = new DTOBitacoraAcceso();
				DTOUsuarioLogin user = (DTOUsuarioLogin) ((InteractiveAuthenticationSuccessEvent) event)
						.getAuthentication().getPrincipal();
				bitacora.setIdSistema(user.getIdSistema());
				bitacora.setUsuario(user.getUsername());
				bitacora.setRolUsuario(user.getRolUsuario());
				HttpServletRequest request = ((ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder
						.currentRequestAttributes()).getRequest();
				bitacora.setIpUsuario(Utilidades.getClientIP(request));
				bitacora.setIpNodo(request.getLocalAddr());
				bsdBitacora.regitroBitacoraAcceso(bitacora);
				
				user.setIpUsuario(bitacora.getIpUsuario());
				user.setIdBitacoraAcceso(bitacora.getIdBitacoraAcceso());
			} else if (event instanceof SessionDestroyedEvent sessionDestroyedEvent) {
				for (SecurityContext securityContext : sessionDestroyedEvent.getSecurityContexts()) {
					Authentication authentication = securityContext.getAuthentication();
					DTOUsuarioLogin user = (DTOUsuarioLogin) authentication.getPrincipal();
					bsdBitacora.regitroBitacoraCierre(user);
				}
			}
		} catch (Exception e) {
			logger.error("ERROR RegistroBitacora -onApplicationEvent: ", e);
		}
	}
}
