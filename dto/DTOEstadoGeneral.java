package mx.ine.procprimerinsa.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class DTOEstadoGeneral implements Serializable {

    private static final long serialVersionUID = -5946268140970394613L;

	private Integer idEstado;
    private String nombreEstado;

    private Map<Integer, DTOParticipacionGeneral> participaciones;

    public DTOEstadoGeneral() {
    	super();
    }

	public DTOEstadoGeneral(Integer idEstado, String nombreEstado) {
		super();
		this.idEstado = idEstado;
		this.nombreEstado = nombreEstado;
	}

	public DTOEstadoGeneral(Integer idEstado, String nombreEstado,
			Map<Integer, DTOParticipacionGeneral> participaciones) {
		super();
		this.idEstado = idEstado;
		this.nombreEstado = nombreEstado;
		this.participaciones = participaciones;
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

	public Map<Integer, DTOParticipacionGeneral> getParticipaciones() {
		return participaciones;
	}

	public void setParticipaciones(Map<Integer, DTOParticipacionGeneral> participaciones) {
		this.participaciones = participaciones;
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
		DTOEstadoGeneral other = (DTOEstadoGeneral) obj;
		return Objects.equals(idEstado, other.idEstado);
	}

	@Override
	public String toString() {
		return "DTOEstadoGeneral [idEstado=" + idEstado + ", nombreEstado=" + nombreEstado + ", participaciones="
				+ participaciones + "]";
	}
	
}
