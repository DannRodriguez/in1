package mx.ine.procprimerinsa.dto;

public class DTOEstatusParticipacion implements java.io.Serializable {

	private static final long serialVersionUID = -220038673780605659L;
	
	private Integer idEstatusProceso;
	private String descripcionEstatusProceso;
	private String codigoColorEstatus;
	private String mostrarMapa;
	private Integer participacion;
	private Float porcentaje;
	
	public DTOEstatusParticipacion() {
		super();
		participacion = 0;
		porcentaje = (float) 0.0;
	}

	public Integer getIdEstatusProceso() {
		return idEstatusProceso;
	}

	public void setIdEstatusProceso(Integer idEstatusProceso) {
		this.idEstatusProceso = idEstatusProceso;
	}

	public String getDescripcionEstatusProceso() {
		return descripcionEstatusProceso;
	}

	public void setDescripcionEstatusProceso(String descripcionEstatusProceso) {
		this.descripcionEstatusProceso = descripcionEstatusProceso;
	}

	public String getCodigoColorEstatus() {
		return codigoColorEstatus;
	}

	public void setCodigoColorEstatus(String codigoColorEstatus) {
		this.codigoColorEstatus = codigoColorEstatus;
	}

	public String getMostrarMapa() {
		return mostrarMapa;
	}

	public void setMostrarMapa(String mostrarMapa) {
		this.mostrarMapa = mostrarMapa;
	}

	public Integer getParticipacion() {
		return participacion;
	}

	public void setParticipacion(Integer participacion) {
		this.participacion = participacion;
	}

	public Float getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Float porcentaje) {
		this.porcentaje = porcentaje;
	}

	@Override
	public String toString() {
		return "DTOEstatusParticipacion [idEstatusProceso=" + idEstatusProceso + ", descripcionEstatusProceso="
				+ descripcionEstatusProceso + ", codigoColorEstatus=" + codigoColorEstatus + ", mostrarMapa="
				+ mostrarMapa + ", participacion=" + participacion + ", porcentaje=" + porcentaje + "]";
	}
	
}
