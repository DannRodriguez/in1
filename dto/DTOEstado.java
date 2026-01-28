package mx.ine.procprimerinsa.dto;

import java.io.Serializable;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class DTOEstado implements Serializable {

	private static final long serialVersionUID = -9080499117795108940L;
	
	@SerializedName("idEstado")
	private Integer idEstado;
	
	@SerializedName("nombreEstado")
	private String nombreEstado;

	public DTOEstado() {
		super();
	}

	public DTOEstado(Integer idEstado, String nombreEstado) {
		super();
		this.idEstado = idEstado;
		this.nombreEstado = nombreEstado;
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
		DTOEstado other = (DTOEstado) obj;
		return Objects.equals(idEstado, other.idEstado);
	}

	@Override
	public String toString() {
		return "DTOEstado [idEstado=" + idEstado + ", nombreEstado=" + nombreEstado + "]";
	}

}
