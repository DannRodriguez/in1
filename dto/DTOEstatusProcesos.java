package mx.ine.procprimerinsa.dto;

import java.io.Serializable;

public class DTOEstatusProcesos implements Serializable {

	private static final long serialVersionUID = 6110441528600655544L;

	private Integer idProcesoElectoral;
	private Integer idDetalleProceso;
	private Integer idEstado;
	private Integer sinTerminar;

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

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Integer getSinTerminar() {
		return sinTerminar;
	}

	public void setSinTerminar(Integer sinTerminar) {
		this.sinTerminar = sinTerminar;
	}

	@Override
	public String toString() {
		return "DTOEstatusProcesos [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso
				+ ", idEstado=" + idEstado + ", sinTerminar=" + sinTerminar + "]";
	}
	
}
