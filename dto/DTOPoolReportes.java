package mx.ine.procprimerinsa.dto;

import java.io.Serializable;

public class DTOPoolReportes implements Serializable {
	
	private static final long serialVersionUID = 1193178712276062949L;
	
	private String nombreEstado;
	private Integer idDistrito;
	private String cabeceraDistrital;
	private Integer seccionesInsaculadas;
	private Integer ciudadanosInsaculados;
	private String horaInicio;
	private String horaFin;
	private String tiempoEjecucion;
	
	public String getNombreEstado() {
		return nombreEstado;
	}
	
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
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
	
	public Integer getSeccionesInsaculadas() {
		return seccionesInsaculadas;
	}
	
	public void setSeccionesInsaculadas(Integer seccionesInsaculadas) {
		this.seccionesInsaculadas = seccionesInsaculadas;
	}
	
	public Integer getCiudadanosInsaculados() {
		return ciudadanosInsaculados;
	}
	
	public void setCiudadanosInsaculados(Integer ciudadanosInsaculados) {
		this.ciudadanosInsaculados = ciudadanosInsaculados;
	}
	
	public String getHoraInicio() {
		return horaInicio;
	}
	
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	
	public String getHoraFin() {
		return horaFin;
	}
	
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	
	public String getTiempoEjecucion() {
		return tiempoEjecucion;
	}
	
	public void setTiempoEjecucion(String tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}
	
}
