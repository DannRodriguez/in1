package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "C_ESTATUS_PROCESO", schema = "INSA1")
public class DTOCEstatusProceso implements Serializable {
	
	private static final long serialVersionUID = -2619384476306059764L;
	
	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@NotNull
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@Column(name = "ID_ESTATUS_PROCESO", nullable = false, precision = 2, scale = 0)
	private Integer idEstatusProceso;
	
	@Column(name = "DESCRIPCION_ESTATUS_PROCESO", nullable = false, length = 200)
	private String descripcionEstatusProceso;
	
	@Column(name = "CODIGO_COLOR_ESTATUS", nullable = true, length = 10)
	private String codigoColorEstatus;
	
	@Column(name = "MOSTRAR_MAPA", nullable = true, length = 1)
	private String mostrarMapa;

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

	@Override
	public int hashCode() {
		return Objects.hash(idDetalleProceso, idEstatusProceso);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOCEstatusProceso other = (DTOCEstatusProceso) obj;
		return Objects.equals(idDetalleProceso, other.idDetalleProceso)
				&& Objects.equals(idEstatusProceso, other.idEstatusProceso);
	}

	@Override
	public String toString() {
		return "DTOCEstatusProceso [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso
				+ ", idEstatusProceso=" + idEstatusProceso + ", descripcionEstatusProceso=" + descripcionEstatusProceso
				+ ", codigoColorEstatus=" + codigoColorEstatus + ", mostrarMapa=" + mostrarMapa + "]";
	}

}
