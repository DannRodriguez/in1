package mx.ine.procprimerinsa.form;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import mx.ine.procprimerinsa.dto.DTOCombo;
import mx.ine.procprimerinsa.dto.DTOConfiguracionParametros;
import mx.ine.procprimerinsa.dto.DTOEstadoGeneral;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;

public class FormConfiguracionParametros implements Serializable{
	
	private static final long serialVersionUID = -7332133265394994532L;
	
	private List<DTOConfiguracionParametros> lParametros;
	private List<DTOConfiguracionParametros> lParametrosFiltrados;
	private DTOConfiguracionParametros parametroSeleccionado;
	
	private List<DTOEstadoGeneral> lEstados;
	private List<DTOParticipacionGeneral> lDistritos;
	private DTOEstadoGeneral estadoSeleccionado;
	private DTOParticipacionGeneral distritoSeleccionado;
	
	private Map<String, DTOCombo> cParametros;
	private Integer idParametroSeleccionado;
	
	private Integer idCorte;
	private String tipoCapturaSistema;
	
	private String descripcionParametro;
	private String descripcionValores;
	private Integer idProceso;
	private Integer idDetalle;
	private String tipoJunta;
	private String valorParametro;

	public List<DTOConfiguracionParametros> getlParametros() {
		return lParametros;
	}

	public void setlParametros(List<DTOConfiguracionParametros> lParametros) {
		this.lParametros = lParametros;
	}

	public List<DTOConfiguracionParametros> getlParametrosFiltrados() {
		return lParametrosFiltrados;
	}

	public void setlParametrosFiltrados(List<DTOConfiguracionParametros> lParametrosFiltrados) {
		this.lParametrosFiltrados = lParametrosFiltrados;
	}

	public DTOConfiguracionParametros getParametroSeleccionado() {
		return parametroSeleccionado;
	}

	public void setParametroSeleccionado(DTOConfiguracionParametros parametroSeleccionado) {
		this.parametroSeleccionado = parametroSeleccionado;
	}

	public List<DTOEstadoGeneral> getlEstados() {
		return lEstados;
	}

	public void setlEstados(List<DTOEstadoGeneral> lEstados) {
		this.lEstados = lEstados;
	}

	public List<DTOParticipacionGeneral> getlDistritos() {
		return lDistritos;
	}

	public void setlDistritos(List<DTOParticipacionGeneral> lDistritos) {
		this.lDistritos = lDistritos;
	}

	public DTOEstadoGeneral getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	public void setEstadoSeleccionado(DTOEstadoGeneral estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	public DTOParticipacionGeneral getDistritoSeleccionado() {
		return distritoSeleccionado;
	}

	public void setDistritoSeleccionado(DTOParticipacionGeneral distritoSeleccionado) {
		this.distritoSeleccionado = distritoSeleccionado;
	}

	public Map<String, DTOCombo> getcParametros() {
		return cParametros;
	}

	public void setcParametros(Map<String, DTOCombo> cParametros) {
		this.cParametros = cParametros;
	}

	public Integer getIdParametroSeleccionado() {
		return idParametroSeleccionado;
	}

	public void setIdParametroSeleccionado(Integer idParametroSeleccionado) {
		this.idParametroSeleccionado = idParametroSeleccionado;
	}
	
	public Integer getIdCorte() {
		return idCorte;
	}

	public void setIdCorte(Integer idCorte) {
		this.idCorte = idCorte;
	}

	public String getTipoCapturaSistema() {
		return tipoCapturaSistema;
	}

	public void setTipoCapturaSistema(String tipoCapturaSistema) {
		this.tipoCapturaSistema = tipoCapturaSistema;
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
		return "FormConfiguracionParametros [lParametros=" + lParametros + ", lParametrosFiltrados="
				+ lParametrosFiltrados + ", parametroSeleccionado=" + parametroSeleccionado + ", lEstados=" + lEstados
				+ ", lDistritos=" + lDistritos + ", estadoSeleccionado=" + estadoSeleccionado
				+ ", distritoSeleccionado=" + distritoSeleccionado + ", cParametros=" + cParametros
				+ ", idParametroSeleccionado=" + idParametroSeleccionado + ", idCorte=" + idCorte
				+ ", tipoCapturaSistema=" + tipoCapturaSistema + ", descripcionParametro=" + descripcionParametro
				+ ", descripcionValores=" + descripcionValores + ", idProceso=" + idProceso + ", idDetalle=" + idDetalle
				+ ", tipoJunta=" + tipoJunta + ", valorParametro=" + valorParametro + "]";
	}
	
}
