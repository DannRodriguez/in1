package mx.ine.procprimerinsa.dto;

import java.io.Serializable;

public class DTOTiempoEjecucionProceso implements Serializable, DTOCSVPrintableInterface {

	private static final long serialVersionUID = -1616824530148202085L;
	
	private Integer idDetalle;
	private Integer idParticipacion;
	private String estado;
	private String distrito;
	private Integer idEstatusProceso;
	private Integer ejecuciones;
	private Integer reinicios;
	private Integer ciudadanos;
	private String inicioInsaculacion;
	private String finInsaculacion;
	private String tiempoInsaculacion;
	private String inicioProceso;
	private String finProceso;
	private String tiempoProceso;
	private String inicioArchivos;
	private String finArchivos;
	private String tiempoArchivos;
	
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public Integer getIdEstatusProceso() {
		return idEstatusProceso;
	}

	public void setIdEstatusProceso(Integer idEstatusProceso) {
		this.idEstatusProceso = idEstatusProceso;
	}

	public Integer getEjecuciones() {
		return ejecuciones;
	}

	public void setEjecuciones(Integer ejecuciones) {
		this.ejecuciones = ejecuciones;
	}

	public Integer getReinicios() {
		return reinicios;
	}

	public void setReinicios(Integer reinicios) {
		this.reinicios = reinicios;
	}

	public Integer getCiudadanos() {
		return ciudadanos;
	}

	public void setCiudadanos(Integer ciudadanos) {
		this.ciudadanos = ciudadanos;
	}

	public String getInicioInsaculacion() {
		return inicioInsaculacion;
	}

	public void setInicioInsaculacion(String inicioInsaculacion) {
		this.inicioInsaculacion = inicioInsaculacion;
	}

	public String getFinInsaculacion() {
		return finInsaculacion;
	}

	public void setFinInsaculacion(String finInsaculacion) {
		this.finInsaculacion = finInsaculacion;
	}

	public String getTiempoInsaculacion() {
		return tiempoInsaculacion;
	}

	public void setTiempoInsaculacion(String tiempoInsaculacion) {
		this.tiempoInsaculacion = tiempoInsaculacion;
	}

	public String getInicioProceso() {
		return inicioProceso;
	}

	public void setInicioProceso(String inicioProceso) {
		this.inicioProceso = inicioProceso;
	}

	public String getFinProceso() {
		return finProceso;
	}

	public void setFinProceso(String finProceso) {
		this.finProceso = finProceso;
	}

	public String getTiempoProceso() {
		return tiempoProceso;
	}

	public void setTiempoProceso(String tiempoProceso) {
		this.tiempoProceso = tiempoProceso;
	}

	public String getInicioArchivos() {
		return inicioArchivos;
	}

	public void setInicioArchivos(String inicioArchivos) {
		this.inicioArchivos = inicioArchivos;
	}

	public String getFinArchivos() {
		return finArchivos;
	}

	public void setFinArchivos(String finArchivos) {
		this.finArchivos = finArchivos;
	}

	public String getTiempoArchivos() {
		return tiempoArchivos;
	}

	public void setTiempoArchivos(String tiempoArchivos) {
		this.tiempoArchivos = tiempoArchivos;
	}

	@Override
	public String toString() {
		return "DTOTiempoEjecucionProceso [idDetalle=" + idDetalle + ", idParticipacion=" + idParticipacion
				+ ", estado=" + estado + ", distrito=" + distrito + ", idEstatusProceso=" + idEstatusProceso
				+ ", ejecuciones=" + ejecuciones + ", reinicios=" + reinicios + ", ciudadanos=" + ciudadanos
				+ ", inicioInsaculacion=" + inicioInsaculacion + ", finInsaculacion=" + finInsaculacion
				+ ", tiempoInsaculacion=" + tiempoInsaculacion + ", inicioProceso=" + inicioProceso + ", finProceso="
				+ finProceso + ", tiempoProceso=" + tiempoProceso + ", inicioArchivos=" + inicioArchivos
				+ ", finArchivos=" + finArchivos + ", tiempoArchivos=" + tiempoArchivos + "]";
	}

	@Override
	public Object[] getCSVPrintable() {
		return new Object[] {
				idDetalle,
				idParticipacion,
				estado,
				distrito,
				idEstatusProceso,
				ejecuciones,
				reinicios,
				ciudadanos,
				inicioInsaculacion,
				finInsaculacion,
				tiempoInsaculacion,
				inicioProceso,
				finProceso,
				tiempoProceso,
				inicioArchivos,
				finArchivos,
				tiempoArchivos
		};
	}
	
}