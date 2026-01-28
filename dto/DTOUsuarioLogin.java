package mx.ine.procprimerinsa.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class DTOUsuarioLogin extends User implements Serializable {

	private static final long serialVersionUID = -5309365608458104948L;

	private String usuario;
    private String nombreUsuario;
    private Integer idEstado;
	private String nombreEstado;
    private Integer idDistrito;
	private String nombreDistrito;
	private Integer idMunicipio;
	private String ambitoUsuario;
	private String ubicacion;
    private String rolUsuario;
    private String nivelRol;
    private Integer idSistema;
    private String ipUsuario;
    private Integer idBitacoraAcceso;
    
    private List<String> rolesLdap;
    
	public DTOUsuarioLogin(String username, String password, boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
    }

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public Integer getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}

	public String getNombreDistrito() {
		return nombreDistrito;
	}

	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}

	public Integer getIdSistema() {
		return idSistema;
	}

	public void setIdSistema(Integer idSistema) {
		this.idSistema = idSistema;
	}

	public String getIpUsuario() {
		return ipUsuario;
	}

	public void setIpUsuario(String ipUsuario) {
		this.ipUsuario = ipUsuario;
	}

	public Integer getIdBitacoraAcceso() {
		return idBitacoraAcceso;
	}

	public void setIdBitacoraAcceso(Integer idBitacoraAcceso) {
		this.idBitacoraAcceso = idBitacoraAcceso;
	}

	public List<String> getRolesLdap() {
		return rolesLdap;
	}

	public void setRolesLdap(List<String> rolesLdap) {
		this.rolesLdap = rolesLdap;
	}

	public String getNivelRol() {
		return nivelRol;
	}

	public void setNivelRol(String nivelRol) {
		this.nivelRol = nivelRol;
	}

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getAmbitoUsuario() {
		return ambitoUsuario;
	}

	public void setAmbitoUsuario(String ambitoUsuario) {
		this.ambitoUsuario = ambitoUsuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOUsuarioLogin other = (DTOUsuarioLogin) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTOUsuarioLogin [usuario=" + usuario + ", nombreUsuario=" + nombreUsuario + ", idEstado=" + idEstado
				+ ", nombreEstado=" + nombreEstado + ", idDistrito=" + idDistrito + ", nombreDistrito=" + nombreDistrito
				+ ", ubicacion=" + ubicacion + ", rolUsuario=" + rolUsuario + ", idSistema=" + idSistema
				+ ", ipUsuario=" + ipUsuario + ", idBitacoraAcceso=" + idBitacoraAcceso + ", rolesLdap=" + rolesLdap
				+ ", nivelRol=" + nivelRol + ", idMunicipio=" + idMunicipio + ", ambitoUsuario=" + ambitoUsuario + "]";
	}
	
}
