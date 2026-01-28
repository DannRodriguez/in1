package mx.ine.procprimerinsa.dto;

import java.io.Serializable;

public class DTOReporteBitacora implements Serializable, DTOCSVPrintableInterface {

	private static final long serialVersionUID = 6975946089997001536L;
	
	private Integer idProceso;
	private Integer idDetalle;
	private String nombreSistema;
	private String usuarioSistema;
	private String rolUsuario;
	private String nivelGeografico;
	private String vista;
	private String modulo;
	private String fechaIngreso;
	
	public DTOReporteBitacora() {
		super();
	}
	
	public Integer getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Integer idProceso) {
		this.idProceso = idProceso;
	}

	public Integer getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}

	public String getNombreSistema() {
		return nombreSistema;
	}

	public void setNombreSistema(String nombreSistema) {
		this.nombreSistema = nombreSistema;
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

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	@Override
	public String toString() {
		return "DTOReporteBitacora [idProceso=" + idProceso + ", idDetalle=" + idDetalle + ", nombreSistema="
				+ nombreSistema + ", usuarioSistema=" + usuarioSistema + ", rolUsuario=" + rolUsuario
				+ ", nivelGeografico=" + nivelGeografico + ", vista=" + vista + ", modulo=" + modulo + ", fechaIngreso="
				+ fechaIngreso + "]";
	}

	@Override
	public Object[] getCSVPrintable() {
		return new Object[] {
				idProceso,
				idDetalle,
				nombreSistema,
				usuarioSistema,
				rolUsuario,
				nivelGeografico,
				vista,
				modulo,
				fechaIngreso
		};
	}

}
