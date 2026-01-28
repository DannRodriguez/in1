package mx.ine.procprimerinsa.dto;

import java.io.Serializable;

public class DTOAdministradorBatch implements Serializable {

	private static final long serialVersionUID = -1267766656078034239L;
	
	private Integer idDetalleProceso;
	private Integer idGeograficoParticipacion;
	private Integer idParticipacion;
	private String descripcionParticipacion;

	private String jobCalculaInsaculados;
	private Integer idJobCalculaInsaculados;
	private String estatusJobCalculaInsaculados;

	private String jobGeneraArchivos;
	private Integer idJobGeneraArchivos;
	private String estatusJobGeneraArchivos;
	
	private Integer estatus;
	private Integer nuevoEstatus;
	private String codigoColorCategoria;

	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}

	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}
	
	public Integer getIdGeograficoParticipacion() {
		return idGeograficoParticipacion;
	}

	public void setIdGeograficoParticipacion(Integer idGeograficoParticipacion) {
		this.idGeograficoParticipacion = idGeograficoParticipacion;
	}

	public Integer getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}

	public String getDescripcionParticipacion() {
		return descripcionParticipacion;
	}

	public void setDescripcionParticipacion(String descripcionParticipacion) {
		this.descripcionParticipacion = descripcionParticipacion;
	}
	
	public String getJobCalculaInsaculados() {
		return jobCalculaInsaculados;
	}

	public void setJobCalculaInsaculados(String jobCalculaInsaculados) {
		this.jobCalculaInsaculados = jobCalculaInsaculados;
	}

	public Integer getIdJobCalculaInsaculados() {
		return idJobCalculaInsaculados;
	}

	public void setIdJobCalculaInsaculados(Integer idJobCalculaInsaculados) {
		this.idJobCalculaInsaculados = idJobCalculaInsaculados;
	}

	public String getEstatusJobCalculaInsaculados() {
		return estatusJobCalculaInsaculados;
	}

	public void setEstatusJobCalculaInsaculados(
			String estatusJobCalculaInsaculados) {
		this.estatusJobCalculaInsaculados = estatusJobCalculaInsaculados;
	}
	
	public String getJobGeneraArchivos() {
		return jobGeneraArchivos;
	}

	public void setJobGeneraArchivos(String jobGeneraArchivos) {
		this.jobGeneraArchivos = jobGeneraArchivos;
	}

	public Integer getIdJobGeneraArchivos() {
		return idJobGeneraArchivos;
	}

	public void setIdJobGeneraArchivos(Integer idJobGeneraArchivos) {
		this.idJobGeneraArchivos = idJobGeneraArchivos;
	}

	public String getEstatusJobGeneraArchivos() {
		return estatusJobGeneraArchivos;
	}

	public void setEstatusJobGeneraArchivos(String estatusJobGeneraArchivos) {
		this.estatusJobGeneraArchivos = estatusJobGeneraArchivos;
	}
	
	public Integer getIdJobFromStatus(String jobEstatus) {
		if (jobEstatus != null && jobEstatus.contains("-"))
			return Integer.parseInt(jobEstatus.split("-")[0]);
		return null;
	}

	public String getDescripcionJobFromStatus(String jobEstatus) {
		if (jobEstatus != null && jobEstatus.contains("-"))
			return jobEstatus.split("-")[1];
		return jobEstatus;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Integer getNuevoEstatus() {
		return nuevoEstatus;
	}

	public void setNuevoEstatus(Integer nuevoEstatus) {
		this.nuevoEstatus = nuevoEstatus;
	}

	public String getCodigoColorCategoria() {
		return codigoColorCategoria;
	}

	public void setCodigoColorCategoria(String codigoColorCategoria) {
		this.codigoColorCategoria = codigoColorCategoria;
	}

	@Override
	public String toString() {
		return "DTOAdministradorBatch [idDetalleProceso=" + idDetalleProceso + ", idGeograficoParticipacion="
				+ idGeograficoParticipacion + ", idParticipacion=" + idParticipacion + ", descripcionParticipacion="
				+ descripcionParticipacion + ", jobCalculaInsaculados=" + jobCalculaInsaculados
				+ ", idJobCalculaInsaculados=" + idJobCalculaInsaculados + ", estatusJobCalculaInsaculados="
				+ estatusJobCalculaInsaculados + ", jobGeneraArchivos=" + jobGeneraArchivos + ", idJobGeneraArchivos="
				+ idJobGeneraArchivos + ", estatusJobGeneraArchivos=" + estatusJobGeneraArchivos + ", estatus="
				+ estatus + ", nuevoEstatus=" + nuevoEstatus + ", codigoColorCategoria=" + codigoColorCategoria + "]";
	}

}
