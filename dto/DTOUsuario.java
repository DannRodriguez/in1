package mx.ine.procprimerinsa.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class DTOUsuario extends User implements Serializable {

	private static final long serialVersionUID = -1517083855216258041L;

	/**
	 * Llave primaria del sistema en el que esta logueado, en este caso "Mecanismos"
	 */
	private Integer idSistema;

	/**
	 * Roles del sistema de mecanismos que tiene el usuario
	 */
	private List<String> roles;

	/**
	 * Atributo que contiene la ubicación/area a la que pertenece el usuario
	 */
	private String ubicacion;

	/**
	 * Atributo que contiene el nombre de la persona que se encuentra logueada
	 */
	private String nombreUsuario;

	/**
	 * Atributo que contiene el apellido de la persona que se encuentra logueada
	 */
	private String apellidoUsuario;

	/**
	 * Atributo que contiene el identificador del estado al que pertenece el usuario
	 */
	private Integer idEstadoUsuario;
	
	/**
	 * Atributo que contiene el identificador del distrito al que pertenece el usuario
	 */
	private Integer idDistritoUsuario;
	
	/**
	 * Atributo que contiene el estado que sea seleccionado en caso de estar navegando
	 * a traves de las juntas
	 */
	private Integer idEstadoSeleccionado;

	/**
	 * Atributo que contiene el distrito que sea seleccionado en caso de estar navegando
	 * a traves de las juntas
	 */
	private Integer idDistritoSeleccionado;
	/**
	 * Atributo que contiene el identificador del partido al que pertenece el usuario.
	 */
	private Integer idAsociacion;
	
	/**
	 * Atributo que contiene el estatus que tiene el usuari en caso de que sea externo
	 */
	private Integer estatusUsuario;
	
	public DTOUsuario(String username, String password, boolean enabled, boolean accountNonExpired, 
			boolean credentialsNonExpired, boolean accountNonLocked, 
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}

	public Integer getIdSistema() {
		return idSistema;
	}

	public void setIdSistema(Integer idSistema) {
		this.idSistema = idSistema;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getApellidoUsuario() {
		return apellidoUsuario;
	}

	public void setApellidoUsuario(String apellidoUsuario) {
		this.apellidoUsuario = apellidoUsuario;
	}

	public Integer getIdEstadoUsuario() {
		return idEstadoUsuario;
	}

	public void setIdEstadoUsuario(Integer idEstadoUsuario) {
		this.idEstadoUsuario = idEstadoUsuario;
	}

	public Integer getIdDistritoUsuario() {
		return idDistritoUsuario;
	}

	public void setIdDistritoUsuario(Integer idDistritoUsuario) {
		this.idDistritoUsuario = idDistritoUsuario;
	}

	public Integer getIdEstadoSeleccionado() {
		return idEstadoSeleccionado;
	}

	public void setIdEstadoSeleccionado(Integer idEstadoSeleccionado) {
		this.idEstadoSeleccionado = idEstadoSeleccionado;
	}

	public Integer getIdDistritoSeleccionado() {
		return idDistritoSeleccionado;
	}

	public void setIdDistritoSeleccionado(Integer idDistritoSeleccionado) {
		this.idDistritoSeleccionado = idDistritoSeleccionado;
	}

	public Integer getIdAsociacion() {
		return idAsociacion;
	}

	public void setIdAsociacion(Integer idAsociacion) {
		this.idAsociacion = idAsociacion;
	}

	public Integer getEstatusUsuario() {
		return estatusUsuario;
	}

	public void setEstatusUsuario(Integer estatusUsuario) {
		this.estatusUsuario = estatusUsuario;
	}
	
}
