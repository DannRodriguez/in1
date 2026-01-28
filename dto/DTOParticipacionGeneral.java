package mx.ine.procprimerinsa.dto;

import java.io.Serializable;
import java.util.Objects;

public class DTOParticipacionGeneral implements Serializable{

	private static final long serialVersionUID = -8500118250809123660L;

	private Integer idParticipacion;
	
	private Integer id;
	private String nombre;
	
	public DTOParticipacionGeneral(Integer idParticipacion, Integer id, String nombre) {
		super();
		this.idParticipacion = idParticipacion;
		this.id = id;
		this.nombre = nombre;
	}
	
	public DTOParticipacionGeneral() {
		
	}

	public Integer getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idParticipacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOParticipacionGeneral other = (DTOParticipacionGeneral) obj;
		return Objects.equals(idParticipacion, other.idParticipacion);
	}

	@Override
	public String toString() {
		return "DTOParticipacionGeneral [idParticipacion=" + idParticipacion + ", id=" + id + ", nombre=" + nombre
				+ "]";
	}
	
}
