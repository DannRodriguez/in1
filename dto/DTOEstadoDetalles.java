package mx.ine.procprimerinsa.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class DTOEstadoDetalles implements Serializable {
	
	private static final long serialVersionUID = 1320863499342137200L;
	
	private Integer idEstado;
	private String nombreEstado;
	
	private Map<Integer, DTODetalleParticipaciones> detalles;
	
	public DTOEstadoDetalles(Integer idEstado, String nombreEstado) {
		super();
		this.idEstado = idEstado;
		this.nombreEstado = nombreEstado;
		this.detalles = new LinkedHashMap<>();
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

	public Map<Integer, DTODetalleParticipaciones> getDetalles() {
		return detalles;
	}

	public void setDetalles(Map<Integer, DTODetalleParticipaciones> detalles) {
		this.detalles = detalles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idEstado);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOEstadoDetalles other = (DTOEstadoDetalles) obj;
		return Objects.equals(idEstado, other.idEstado);
	}

	@Override
	public String toString() {
		return "DTOReportesHistoricos [idEstado=" + idEstado + ", nombreEstado=" + nombreEstado + ", detalles="
				+ detalles + "]";
	}

}
