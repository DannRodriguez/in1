package mx.ine.procprimerinsa.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class DTODetalleParticipaciones implements Serializable {
	
	private static final long serialVersionUID = -6345590730120428343L;
	
	private Integer idProceso;
	private String nombreProceso;
	private Integer idDetalle;
	private String nombreDetalle;
	
	private Map<Integer, DTOParticipacionGeneral> participaciones;

	public DTODetalleParticipaciones(Integer idProceso, String nombreProceso, Integer idDetalle, String nombreDetalle) {
		super();
		this.idProceso = idProceso;
		this.nombreProceso = nombreProceso;
		this.idDetalle = idDetalle;
		this.nombreDetalle = nombreDetalle;
		this.participaciones = new LinkedHashMap<>();
	}

	public Integer getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Integer idProceso) {
		this.idProceso = idProceso;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public Integer getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}

	public String getNombreDetalle() {
		return nombreDetalle;
	}

	public void setNombreDetalle(String nombreDetalle) {
		this.nombreDetalle = nombreDetalle;
	}

	public Map<Integer, DTOParticipacionGeneral> getParticipaciones() {
		return participaciones;
	}

	public void setParticipaciones(Map<Integer, DTOParticipacionGeneral> participaciones) {
		this.participaciones = participaciones;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idDetalle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTODetalleParticipaciones other = (DTODetalleParticipaciones) obj;
		return Objects.equals(idDetalle, other.idDetalle);
	}

	@Override
	public String toString() {
		return "DTODetalle [idProceso=" + idProceso + ", nombreProceso=" + nombreProceso + ", idDetalle="
				+ idDetalle + ", nombreDetalle=" + nombreDetalle + ", participaciones=" + participaciones + "]";
	}
}
