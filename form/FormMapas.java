package mx.ine.procprimerinsa.form;

import java.io.Serializable;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTOEstatusParticipacion;
import mx.ine.procprimerinsa.dto.DTOGrupoHorarioInsaculacion;
import mx.ine.procprimerinsa.dto.db.DTOMapas;

public class FormMapas implements Serializable {
	
	private static final long serialVersionUID = 8764957693695100037L;
  	
  	private List<DTOEstatusParticipacion> estatusTabla;
  	private DTOEstatusParticipacion estatusSelecionado;
  	private List<DTOGrupoHorarioInsaculacion> grupos;
  	private List<DTOMapas> baseGrafica;
  	
  	private Boolean mostrarCategorias;
  	private Integer idProceso;
  	private Integer idDetalle;
  	private String nombreProceso;
  	private Integer idEstado;
  	private String nombreEstado;
  	
  	private String parametro;
  	private String mapaActual;
  	private Integer tiempoPoll;
  	
  	private boolean procesoValido;
  	private String mensaje;
  	
	public List<DTOEstatusParticipacion> getEstatusTabla() {
		return estatusTabla;
	}

	public void setEstatusTabla(List<DTOEstatusParticipacion> estatusTabla) {
		this.estatusTabla = estatusTabla;
	}

	public DTOEstatusParticipacion getEstatusSelecionado() {
		return estatusSelecionado;
	}

	public void setEstatusSelecionado(DTOEstatusParticipacion estatusSelecionado) {
		this.estatusSelecionado = estatusSelecionado;
	}

	public List<DTOGrupoHorarioInsaculacion> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<DTOGrupoHorarioInsaculacion> grupos) {
		this.grupos = grupos;
	}

	public List<DTOMapas> getBaseGrafica() {
		return baseGrafica;
	}

	public void setBaseGrafica(List<DTOMapas> baseGrafica) {
		this.baseGrafica = baseGrafica;
	}

	public Boolean getMostrarCategorias() {
		return mostrarCategorias;
	}

	public void setMostrarCategorias(Boolean mostrarCategorias) {
		this.mostrarCategorias = mostrarCategorias;
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

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
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

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public String getMapaActual() {
		return mapaActual;
	}

	public void setMapaActual(String mapaActual) {
		this.mapaActual = mapaActual;
	}

	public Integer getTiempoPoll() {
		return tiempoPoll;
	}

	public void setTiempoPoll(Integer tiempoPoll) {
		this.tiempoPoll = tiempoPoll;
	}

	public boolean isProcesoValido() {
		return procesoValido;
	}

	public void setProcesoValido(boolean procesoValido) {
		this.procesoValido = procesoValido;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}