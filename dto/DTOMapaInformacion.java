package mx.ine.procprimerinsa.dto;

import java.io.Serializable;

public class DTOMapaInformacion implements Serializable{

	private static final long serialVersionUID = 3099653396837516545L;
	
	private Integer idEstado;
	private String nombreEstado;
	private Integer idDistrito;
	private Integer idParticipacion;
	private String cabecera;
	private String abreviatura;
	private String hora;
	private String color;
	private Integer estatus;
	private String descripcion;
	private String fechaHoraInicioProceso;
	private Double porcentajeAvance;
	private String fechaHoraFinProceso;
	
	
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
	
	public Integer getIdDistrito() {
		return idDistrito;
	}
	
	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}
	
	public String getCabecera() {
		return idDistrito + " - " + cabecera;
	}
	
	public String getTrueCabecera(){
		return cabecera;
	}
	
	public void setCabecera(String cabecera) {
		this.cabecera = cabecera;
	}
	
	public String getAbreviatura() {
		String digitosCaptura;
		if(idDistrito < 10){
			digitosCaptura = "00" + idDistrito;
		} else if(idDistrito < 100){
			digitosCaptura = "0" + idDistrito;
		} else {
			digitosCaptura = idDistrito.toString();
		}
		return "E"+((idEstado<10)?("0"+idEstado):(idEstado))+abreviatura+digitosCaptura;
	}
	
	public String getTrueAbreviatura() {
		return abreviatura;
	}
	
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	
	public String getHora() {
		return hora;
	}
	
	public void setHora(String hora) {
		this.hora = hora;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public Integer getEstatus() {
		return estatus;
	}
	
	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getFechaHoraInicioProceso() {
		return fechaHoraInicioProceso;
	}
	
	public void setFechaHoraInicioProceso(String fechaHoraInicioProceso) {
		this.fechaHoraInicioProceso = fechaHoraInicioProceso;
	}
	
	public Double getPorcentajeAvance() {
		return porcentajeAvance;
	}

	public void setPorcentajeAvance(Double porcentajeAvance) {
		this.porcentajeAvance = porcentajeAvance;
	}

	public String getFechaHoraFinProceso() {
		return fechaHoraFinProceso;
	}
	
	public void setFechaHoraFinProceso(String fechaHoraFinProceso) {
		this.fechaHoraFinProceso = fechaHoraFinProceso;
	}
	
	public Integer getIdParticipacion() {
		return idParticipacion;
	}
	
	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}

	@Override
	public String toString() {
		return "DTOMapaInformacion [idEstado=" + idEstado + ", nombreEstado=" + nombreEstado + ", idDistrito="
				+ idDistrito + ", idParticipacion=" + idParticipacion + ", cabecera=" + cabecera + ", abreviatura="
				+ abreviatura + ", hora=" + hora + ", color=" + color + ", estatus=" + estatus + ", descripcion="
				+ descripcion + ", fechaHoraInicioProceso=" + fechaHoraInicioProceso + ", porcentajeAvance="
				+ porcentajeAvance + ", fechaHoraFinProceso=" + fechaHoraFinProceso + "]";
	}
	
}
