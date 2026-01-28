package mx.ine.procprimerinsa.dto;

import java.io.Serializable;

public class DTOConfiguracionParametros implements Serializable {

	private static final long serialVersionUID = 2296890890310917357L;
	
	private Integer idParametro;
	private String descripcionParametro;
	private String descripcionValores;
	private Integer idProceso;
	private Integer idDetalle;
	private Integer idEstado;
	private String estado;
	private Integer idDistrito;
	private String cabeceraDistrital;
	private String tipoJunta;
	private String valorParametro;

	public Integer getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(Integer idParametro) {
		this.idParametro = idParametro;
	}

	public String getDescripcionParametro() {
		return descripcionParametro;
	}

	public void setDescripcionParametro(String descripcionParametro) {
		this.descripcionParametro = descripcionParametro;
	}

	public String getDescripcionValores() {
		return descripcionValores;
	}

	public void setDescripcionValores(String descripcionValores) {
		this.descripcionValores = descripcionValores;
	}

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

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}

	public String getCabeceraDistrital() {
		return cabeceraDistrital;
	}

	public void setCabeceraDistrital(String cabeceraDistrital) {
		this.cabeceraDistrital = cabeceraDistrital;
	}

	public String getTipoJunta() {
		return tipoJunta;
	}

	public void setTipoJunta(String tipoJunta) {
		this.tipoJunta = tipoJunta;
	}

	public String getValorParametro() {
		return valorParametro;
	}

	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
	}

	@Override
	public String toString() {
		return "DTOConfiguracionParametros [idParametro=" + idParametro + ", descripcionParametro="
				+ descripcionParametro + ", descripcionValores=" + descripcionValores + ", idProceso=" + idProceso
				+ ", idDetalle=" + idDetalle + ", idEstado=" + idEstado + ", estado=" + estado + ", idDistrito="
				+ idDistrito + ", cabeceraDistrital=" + cabeceraDistrital + ", tipoJunta=" + tipoJunta
				+ ", valorParametro=" + valorParametro + "]";
	}
	
}
