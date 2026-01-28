package mx.ine.procprimerinsa.dto;

import java.io.Serializable;

public class DTOWsRegistraBitacora implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -145550237775632620L;
	
	Integer idProcesoElectoral;
	Integer idDetalleProceso;
	Integer idSistema;
	Integer idEstado;
	Integer idDistrito;
	String  usuarioSistema;
	String	rolUsuario;
	String  nivelGeografico;
	String	vista;
	String	modulo;
	String  ipUsuario;
	
	public Integer getIdProcesoElectoral() {
		return idProcesoElectoral;
	}
	public void setIdProcesoElectoral(Integer idProcesoElectoral) {
		this.idProcesoElectoral = idProcesoElectoral;
	}
	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}
	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}
	public Integer getIdSistema() {
		return idSistema;
	}
	public void setIdSistema(Integer idSistema) {
		this.idSistema = idSistema;
	}
	public Integer getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
	public Integer getIdDistrito() {
		return idDistrito;
	}
	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}
	public String getUsuarioSistema() {
		return usuarioSistema;
	}
	public void setUsuarioSistema(String usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
	}
	public String getRolUsuario() {
		return rolUsuario;
	}
	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
	public String getNivelGeografico() {
		return nivelGeografico;
	}
	public void setNivelGeografico(String nivelGeografico) {
		this.nivelGeografico = nivelGeografico;
	}
	public String getVista() {
		return vista;
	}
	public void setVista(String vista) {
		this.vista = vista;
	}
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public String getIpUsuario() {
		return ipUsuario;
	}
	public void setIpUsuario(String ipUsuario) {
		this.ipUsuario = ipUsuario;
	}
	
	@Override
	public String toString() {
		return "DTOWsRegistraBitacora [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso="
				+ idDetalleProceso + ", idSistema=" + idSistema + ", idEstado=" + idEstado + ", idDistrito="
				+ idDistrito + ", usuarioSistema=" + usuarioSistema + ", rolUsuario=" + rolUsuario
				+ ", nivelGeografico=" + nivelGeografico + ", vista=" + vista + ", modulo=" + modulo + ", ipUsuario="
				+ ipUsuario + "]";
	}
	
	
	

}
