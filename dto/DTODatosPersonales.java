package mx.ine.procprimerinsa.dto;

import java.io.Serializable;

public class DTODatosPersonales implements Serializable {
	
	private static final long serialVersionUID = -2810166202156188105L;
	
	private Integer idProceso;
	private Integer idDetalle;
	private Integer idParticipacion;
	private Integer idEstado;
	private Integer idDistrito;
	private String claveElector;
	private String calle;
	private String numeroExterior;
	private String numeroInterior;
	private String colonia;
	private String codigoPostal;
	
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

	public Integer getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Integer getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}

	public String getClaveElector() {
		return claveElector;
	}

	public void setClaveElector(String claveElector) {
		this.claveElector = claveElector;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumeroExterior() {
		return numeroExterior;
	}

	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}

	public String getNumeroInterior() {
		return numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	@Override
	public String toString() {
		return "DTODatosPersonales [idProceso=" + idProceso + ", idDetalle=" + idDetalle + ", idParticipacion="
				+ idParticipacion + ", idEstado=" + idEstado + ", idDistrito=" + idDistrito + ", claveElector="
				+ claveElector + ", calle=" + calle + ", numeroExterior=" + numeroExterior + ", numeroInterior="
				+ numeroInterior + ", colonia=" + colonia + ", codigoPostal=" + codigoPostal + "]";
	}
	
}
