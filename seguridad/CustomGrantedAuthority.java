package mx.ine.procprimerinsa.seguridad;

import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = -3537793685802328649L;
	
	private String role;

	public CustomGrantedAuthority(String role) {
		this.role = role;
	}
	
	@Override
	public String getAuthority() {
		return role;
	}

}
