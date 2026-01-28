package mx.ine.procprimerinsa.dto.helper;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.ine.procprimerinsa.dto.DTOReporteBitacora;

public class FormReporteBitacora implements Serializable{

	private static final long serialVersionUID = 5581975266137489070L;

	private List<Integer> detalles;
	
	private boolean procesoValido;
	private String mensaje;
	
	private List<DTOReporteBitacora> lReporteBitacora;
	private List<DTOReporteBitacora> lReporteBitacoraFiltrados;
	
	private Date fechaInicio;
	private Date fechaFin;
	private Date fechaMax;
	
	public FormReporteBitacora() {
		super();
		this.fechaMax = new Date();
	}

	public List<Integer> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<Integer> detalles) {
		this.detalles = detalles;
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

	public List<DTOReporteBitacora> getlReporteBitacora() {
		return lReporteBitacora;
	}

	public void setlReporteBitacora(List<DTOReporteBitacora> lReporteBitacora) {
		this.lReporteBitacora = lReporteBitacora;
	}

	public List<DTOReporteBitacora> getlReporteBitacoraFiltrados() {
		return lReporteBitacoraFiltrados;
	}

	public void setlReporteBitacoraFiltrados(List<DTOReporteBitacora> lReporteBitacoraFiltrados) {
		this.lReporteBitacoraFiltrados = lReporteBitacoraFiltrados;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaMax() {
		return fechaMax;
	}

	public void setFechaMax(Date fechaMax) {
		this.fechaMax = fechaMax;
	}

	@Override
	public String toString() {
		return "FormReporteBitacora [detalles=" + detalles + ", procesoValido=" + procesoValido + ", mensaje=" + mensaje
				+ ", lReporteBitacora=" + lReporteBitacora + ", lReporteBitacoraFiltrados=" + lReporteBitacoraFiltrados
				+ ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", fechaMax=" + fechaMax + "]";
	}
	
}
