package mx.ine.procprimerinsa.seguridad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import mx.ine.procprimerinsa.dto.DTOUsuarioLogin;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;
import mx.ine.serviceldap.controller.AuthenticationController;
import mx.ine.serviceldap.dto.DTOUsuario;
import mx.ine.serviceldap.exception.ServiceLdapException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetailsService {
	
	private static final Logger logger = Logger.getLogger(CustomUserDetailsService.class);
	private static final String USUARIO_SIN_PERMISOS = "El usuario no tiene permisos para este sistema";

	@Autowired
	@Qualifier("authenticationController")
	private AuthenticationController authenticationController;
	
	public UserDetails cargaUsuario(Integer idSistema, String userName, String password) {
		DTOUsuarioLogin user;
		
		try {
			DTOUsuario resultado = authenticationController.authenticate(Constantes.ROLES_SISTEMA, userName, password);
			
			if (resultado != null
					&& resultado.getRoles() != null
					&& !resultado.getRoles().isEmpty()) {
				Set<GrantedAuthority> authorities = obtenAuthorities(resultado.getRoles());
				user = new DTOUsuarioLogin(userName, "", true, true, true, true, authorities);
				user.setUsuario(userName);
				user.setNombreUsuario(resultado.getCn());
				user.setIdSistema(idSistema);
				user.setUbicacion(resultado.getOu());
				user.setIdEstado(resultado.getIdEstado() == null ? 0 : resultado.getIdEstado());
				user.setIdDistrito(resultado.getIdDistrito() == null ? 0 : resultado.getIdDistrito());
				user.setIdMunicipio(resultado.getIdMunicipio() == null ? 0 : resultado.getIdMunicipio());
				
				if(resultado.getRoles().get(0).contains("_EXT.OPL.")){
					resultado.setAmbito("L");
				}
				
				user.setAmbitoUsuario(resultado.getAmbito() == null ? "F" : resultado.getAmbito());

				obtenerRoles(user, authorities);
			} else {
				throw new BadCredentialsException(USUARIO_SIN_PERMISOS);
			}
		} catch (ServiceLdapException e) {
			throw new BadCredentialsException(e.getMessage());
		} catch (Exception e) {
			logger.error("ERROR CustomUserDetailsService -cargaUsuario: ", e);
			throw new BadCredentialsException(USUARIO_SIN_PERMISOS);
		}

		if (user.getRolUsuario() != null) {
			obtenerVersion(user, user.getIdEstado(), user.getIdDistrito());
		} else {
			throw new BadCredentialsException(USUARIO_SIN_PERMISOS);
		}

		return user;
	}
	
	private Set<GrantedAuthority> obtenAuthorities(List<String> roles) {
		Set<GrantedAuthority> authorities = new LinkedHashSet<>();
		for (String rol : roles) {
			StringBuilder sb = new StringBuilder();
			sb.append("ROLE_").append(rol);
			GrantedAuthority authority = new CustomGrantedAuthority(sb.toString());
			authorities.add(authority);
		}
		return authorities;
	}

	private void obtenerRoles(DTOUsuarioLogin user, Collection<? extends GrantedAuthority> authorities) {
		List<String> roles = new ArrayList<>();
		if (authorities != null) {
			roles = localizaRoles(authorities);
		}
		user.setRolesLdap(roles);
		
		if(!roles.isEmpty()) {
			user.setRolUsuario(roles.get(0).replace("ROLE_", ""));
		}
	}

	private List<String> localizaRoles(Collection<? extends GrantedAuthority> authorities) {
		List<String> roles = new ArrayList<>();
		for (GrantedAuthority auth : authorities) {
			String rol = auth.getAuthority();
			if (rol.toUpperCase().contains(Utilidades.mensajeProperties("constante_roles_sistema"))) {
				roles.add(rol);
			}
		}
		
		return roles;
	}

	private void obtenerVersion(DTOUsuarioLogin user, Integer idEstado, Integer idDistrito) {
		String[] arr = user.getRolUsuario().split("\\.");
		String version = arr[arr.length - 1];
		user.setNivelRol(version);
		if (version.equalsIgnoreCase(Utilidades.mensajeProperties("constante_version_rol_oc"))) {
			user.setIdEstado(0);
			user.setIdDistrito(0);
		} else if (version.equalsIgnoreCase(Utilidades.mensajeProperties("constante_version_rol_jl"))) {
			user.setIdEstado(idEstado);
			user.setIdDistrito(0);
		} else {
			user.setIdEstado(idEstado);
			user.setIdDistrito(idDistrito);
		}
	}

}
