package mx.ine.procprimerinsa.dto;

import java.io.Serializable;
import java.util.Date;

public class DTOArchivoDatosPersonales implements Serializable {

	private static final long serialVersionUID = -6160134406440257060L;

	private Integer idProceso;
	private Integer idDetalle;
	private Integer idEstado;
	private String nombreEstado;
	private Integer idHorario;
	private Character idAccion;
	private String estatus;
	private Integer ciudadanosDescarga;
	private Integer ciudadanosCarga;
	private String usuario;
	private Date fechaHora;
	
	private Boolean finalizoProcesoInsaculacion;
	
	private Boolean existeArchivo;

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

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public Integer getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(Integer idHorario) {
		this.idHorario = idHorario;
	}

	public Character getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(Character idAccion) {
		this.idAccion = idAccion;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Integer getCiudadanosDescarga() {
		return ciudadanosDescarga;
	}

	public void setCiudadanosDescarga(Integer ciudadanosDescarga) {
		this.ciudadanosDescarga = ciudadanosDescarga;
	}

	public Integer getCiudadanosCarga() {
		return ciudadanosCarga;
	}

	public void setCiudadanosCarga(Integer ciudadanosCarga) {
		this.ciudadanosCarga = ciudadanosCarga;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Boolean getFinalizoProcesoInsaculacion() {
		return finalizoProcesoInsaculacion;
	}

	public void setFinalizoProcesoInsaculacion(Boolean finalizoProcesoInsaculacion) {
		this.finalizoProcesoInsaculacion = finalizoProcesoInsaculacion;
	}

	public Boolean getExisteArchivo() {
		return existeArchivo;
	}

	public void setExisteArchivo(Boolean existeArchivo) {
		this.existeArchivo = existeArchivo;
	}

	@Override
	public String toString() {
		return "DTOArchivoDatosPersonales [idProceso=" + idProceso + ", idDetalle=" + idDetalle + ", idEstado="
				+ idEstado + ", nombreEstado=" + nombreEstado + ", idHorario=" + idHorario + ", idAccion=" + idAccion
				+ ", estatus=" + estatus + ", ciudadanosDescarga=" + ciudadanosDescarga + ", ciudadanosCarga="
				+ ciudadanosCarga + ", usuario=" + usuario + ", fechaHora=" + fechaHora
				+ ", finalizoProcesoInsaculacion=" + finalizoProcesoInsaculacion + ", existeArchivo=" + existeArchivo
				+ "]";
	}
	
}
