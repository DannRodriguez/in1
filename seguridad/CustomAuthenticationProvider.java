package mx.ine.procprimerinsa.seguridad;

import mx.ine.procprimerinsa.util.Utilidades;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
    private CustomUserDetailsService userDetailsService;

	@Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.cargaUsuario(Integer.valueOf(Utilidades.mensajeProperties("application_id")),
                									auth.getName(),
                									auth.getCredentials().toString());
        
        if(userDetails != null) {
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("El usuario no tiene permisos para este sistema");
        }
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
