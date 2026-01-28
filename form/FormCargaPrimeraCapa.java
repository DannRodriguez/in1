package mx.ine.procprimerinsa.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import mx.ine.procprimerinsa.dto.DTOEstatusCargaPrimeraCapaDistrito;

public class FormCargaPrimeraCapa implements Serializable {
	
	private static final long serialVersionUID = -7415993563532070635L;
	
	private List<Integer> detalles;
	private boolean procesoValido;
	private String mensaje;
		
	private Integer threadPoolSize;
	
	private List<Integer> catalogoEtapas;
	
	private String rutaArchivoServidoresPublicos;
	private String rutaArchivoAreasSecciones;
	private String rutaArchivoSeccionesCompartidas;
	private String rutaArchivoOrdenada;
	private String rutaArchivoOrdenadaCarga;
	
	private Integer conteoServidoresPublicos;
	private Integer idCorteServidoresPublicos;
	private Integer conteoAreasSecciones;
	private Date fechaActualizacionAreasSecciones;
	private Integer conteoSeccionesCompartidas;
	private Date fechaActualizacionSeccionesCompartidas;
	private Integer conteoOrdenada;
	private Date fechaActualizacionOrdenada;
	
	private List<String> estatusIndices;
	private List<String> estatusTriggers;
	
	private String llave;
	
	private int tipoEjecucion;
	private List<DTOEstatusCargaPrimeraCapaDistrito> listEstatus;
	private List<DTOEstatusCargaPrimeraCapaDistrito> listEstatusFiltrada;
	private Queue<DTOEstatusCargaPrimeraCapaDistrito> queueDistritosEjecutando;

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

	public Integer getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(Integer threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public List<Integer> getCatalogoEtapas() {
		return catalogoEtapas;
	}

	public void setCatalogoEtapas(List<Integer> catalogoEtapas) {
		this.catalogoEtapas = catalogoEtapas;
	}

	public Integer getConteoServidoresPublicos() {
		return conteoServidoresPublicos;
	}

	public void setConteoServidoresPublicos(Integer conteoServidoresPublicos) {
		this.conteoServidoresPublicos = conteoServidoresPublicos;
	}

	public Integer getIdCorteServidoresPublicos() {
		return idCorteServidoresPublicos;
	}

	public void setIdCorteServidoresPublicos(Integer idCorteServidoresPublicos) {
		this.idCorteServidoresPublicos = idCorteServidoresPublicos;
	}

	public Integer getConteoAreasSecciones() {
		return conteoAreasSecciones;
	}

	public void setConteoAreasSecciones(Integer conteoAreasSecciones) {
		this.conteoAreasSecciones = conteoAreasSecciones;
	}

	public Date getFechaActualizacionAreasSecciones() {
		return fechaActualizacionAreasSecciones;
	}

	public void setFechaActualizacionAreasSecciones(Date fechaActualizacionAreasSecciones) {
		this.fechaActualizacionAreasSecciones = fechaActualizacionAreasSecciones;
	}

	public String getRutaArchivoServidoresPublicos() {
		return rutaArchivoServidoresPublicos;
	}

	public void setRutaArchivoServidoresPublicos(String rutaArchivoServidoresPublicos) {
		this.rutaArchivoServidoresPublicos = rutaArchivoServidoresPublicos;
	}

	public String getRutaArchivoAreasSecciones() {
		return rutaArchivoAreasSecciones;
	}

	public void setRutaArchivoAreasSecciones(String rutaArchivoAreasSecciones) {
		this.rutaArchivoAreasSecciones = rutaArchivoAreasSecciones;
	}

	public Integer getConteoSeccionesCompartidas() {
		return conteoSeccionesCompartidas;
	}

	public void setConteoSeccionesCompartidas(Integer conteoSeccionesCompartidas) {
		this.conteoSeccionesCompartidas = conteoSeccionesCompartidas;
	}

	public Date getFechaActualizacionSeccionesCompartidas() {
		return fechaActualizacionSeccionesCompartidas;
	}

	public void setFechaActualizacionSeccionesCompartidas(Date fechaActualizacionSeccionesCompartidas) {
		this.fechaActualizacionSeccionesCompartidas = fechaActualizacionSeccionesCompartidas;
	}

	public String getRutaArchivoSeccionesCompartidas() {
		return rutaArchivoSeccionesCompartidas;
	}

	public void setRutaArchivoSeccionesCompartidas(String rutaArchivoSeccionesCompartidas) {
		this.rutaArchivoSeccionesCompartidas = rutaArchivoSeccionesCompartidas;
	}

	public Integer getConteoOrdenada() {
		return conteoOrdenada;
	}

	public void setConteoOrdenada(Integer conteoOrdenada) {
		this.conteoOrdenada = conteoOrdenada;
	}

	public Date getFechaActualizacionOrdenada() {
		return fechaActualizacionOrdenada;
	}

	public void setFechaActualizacionOrdenada(Date fechaActualizacionOrdenada) {
		this.fechaActualizacionOrdenada = fechaActualizacionOrdenada;
	}

	public String getRutaArchivoOrdenada() {
		return rutaArchivoOrdenada;
	}

	public void setRutaArchivoOrdenada(String rutaArchivoOrdenada) {
		this.rutaArchivoOrdenada = rutaArchivoOrdenada;
	}

	public String getRutaArchivoOrdenadaCarga() {
		return rutaArchivoOrdenadaCarga;
	}

	public void setRutaArchivoOrdenadaCarga(String rutaArchivoOrdenadaCarga) {
		this.rutaArchivoOrdenadaCarga = rutaArchivoOrdenadaCarga;
	}

	public List<String> getEstatusIndices() {
		return estatusIndices;
	}

	public void setEstatusIndices(List<String> estatusIndices) {
		this.estatusIndices = estatusIndices;
	}

	public List<String> getEstatusTriggers() {
		return estatusTriggers;
	}

	public void setEstatusTriggers(List<String> estatusTriggers) {
		this.estatusTriggers = estatusTriggers;
	}

	public String getLlave() {
		return llave;
	}

	public void setLlave(String llave) {
		this.llave = llave;
	}

	public int getTipoEjecucion() {
		return tipoEjecucion;
	}

	public void setTipoEjecucion(int tipoEjecucion) {
		this.tipoEjecucion = tipoEjecucion;
	}

	public List<DTOEstatusCargaPrimeraCapaDistrito> getListEstatus() {
		return listEstatus;
	}

	public void setListEstatus(List<DTOEstatusCargaPrimeraCapaDistrito> listEstatus) {
		this.listEstatus = listEstatus;
	}

	public List<DTOEstatusCargaPrimeraCapaDistrito> getListEstatusFiltrada() {
		return listEstatusFiltrada;
	}

	public void setListEstatusFiltrada(List<DTOEstatusCargaPrimeraCapaDistrito> listEstatusFiltrada) {
		this.listEstatusFiltrada = listEstatusFiltrada;
	}

	public Queue<DTOEstatusCargaPrimeraCapaDistrito> getQueueDistritosEjecutando() {
		return queueDistritosEjecutando;
	}

	public void setQueueDistritosEjecutando(Queue<DTOEstatusCargaPrimeraCapaDistrito> queueDistritosEjecutando) {
		this.queueDistritosEjecutando = queueDistritosEjecutando;
	}

	@Override
	public String toString() {
		return "FormCargaPrimeraCapa [detalles=" + detalles + ", procesoValido=" + procesoValido + ", mensaje="
				+ mensaje + ", threadPoolSize=" + threadPoolSize + ", catalogoEtapas=" + catalogoEtapas
				+ ", rutaArchivoServidoresPublicos=" + rutaArchivoServidoresPublicos + ", rutaArchivoAreasSecciones="
				+ rutaArchivoAreasSecciones + ", rutaArchivoSeccionesCompartidas=" + rutaArchivoSeccionesCompartidas
				+ ", rutaArchivoOrdenada=" + rutaArchivoOrdenada + ", rutaArchivoOrdenadaCarga="
				+ rutaArchivoOrdenadaCarga + ", conteoServidoresPublicos=" + conteoServidoresPublicos
				+ ", idCorteServidoresPublicos=" + idCorteServidoresPublicos + ", conteoAreasSecciones=" + conteoAreasSecciones
				+ ", fechaActualizacionAreasSecciones=" + fechaActualizacionAreasSecciones
				+ ", conteoSeccionesCompartidas=" + conteoSeccionesCompartidas
				+ ", fechaActualizacionSeccionesCompartidas=" + fechaActualizacionSeccionesCompartidas
				+ ", conteoOrdenada=" + conteoOrdenada + ", fechaActualizacionOrdenada=" + fechaActualizacionOrdenada
				+ ", estatusIndices=" + estatusIndices + ", estatusTriggers=" + estatusTriggers + ", llave=" + llave
				+ ", tipoEjecucion=" + tipoEjecucion + ", listEstatus=" + listEstatus + ", listEstatusFiltrada="
				+ listEstatusFiltrada + ", queueDistritosEjecutando=" + queueDistritosEjecutando + "]";
	}

}
