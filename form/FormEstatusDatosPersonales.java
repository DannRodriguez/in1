package mx.ine.procprimerinsa.form;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;

import mx.ine.procprimerinsa.dto.DTOArchivoDatosPersonales;
import mx.ine.procprimerinsa.dto.DTODocumento;

public class FormEstatusDatosPersonales implements Serializable {
	
	private static final long serialVersionUID = -7415993563532070635L;
	
	private int tipoModulo;
		
	private boolean procesoValido;
	private String mensaje;
	
	private String rutaDirectorioPrincipal;
	private String rutaDirectorioDerfe;
		
	private Integer threadPoolSize;
	
	private List<Character> catalogoEtapas;
	
	private int tipoEjecucion;
	private List<DTOArchivoDatosPersonales> listEstatus;
	private List<DTOArchivoDatosPersonales> listEstatusFiltrada;
	private Queue<DTOArchivoDatosPersonales> queueEstadosEjecutando;
	
	private transient List<UploadedFile> listaArchivos;
	private TreeNode<DTODocumento> arbolDirectorio;
	private TreeNode<DTODocumento>[] nodosSeleccionados;

	private String encodingArchivoCarga;
	private String patternToValidate;

	public int getTipoModulo() {
		return tipoModulo;
	}

	public void setTipoModulo(int tipoModulo) {
		this.tipoModulo = tipoModulo;
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

	public String getRutaDirectorioPrincipal() {
		return rutaDirectorioPrincipal;
	}

	public void setRutaDirectorioPrincipal(String rutaDirectorioPrincipal) {
		this.rutaDirectorioPrincipal = rutaDirectorioPrincipal;
	}

	public String getRutaDirectorioDerfe() {
		return rutaDirectorioDerfe;
	}

	public void setRutaDirectorioDerfe(String rutaDirectorioDerfe) {
		this.rutaDirectorioDerfe = rutaDirectorioDerfe;
	}

	public Integer getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(Integer threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public List<Character> getCatalogoEtapas() {
		return catalogoEtapas;
	}

	public void setCatalogoEtapas(List<Character> catalogoEtapas) {
		this.catalogoEtapas = catalogoEtapas;
	}

	public int getTipoEjecucion() {
		return tipoEjecucion;
	}

	public void setTipoEjecucion(int tipoEjecucion) {
		this.tipoEjecucion = tipoEjecucion;
	}

	public List<DTOArchivoDatosPersonales> getListEstatus() {
		return listEstatus;
	}

	public void setListEstatus(List<DTOArchivoDatosPersonales> listEstatus) {
		this.listEstatus = listEstatus;
	}

	public List<DTOArchivoDatosPersonales> getListEstatusFiltrada() {
		return listEstatusFiltrada;
	}

	public void setListEstatusFiltrada(List<DTOArchivoDatosPersonales> listEstatusFiltrada) {
		this.listEstatusFiltrada = listEstatusFiltrada;
	}

	public Queue<DTOArchivoDatosPersonales> getQueueEstadosEjecutando() {
		return queueEstadosEjecutando;
	}

	public void setQueueEstadosEjecutando(Queue<DTOArchivoDatosPersonales> queueEstadosEjecutando) {
		this.queueEstadosEjecutando = queueEstadosEjecutando;
	}

	public List<UploadedFile> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<UploadedFile> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public TreeNode<DTODocumento> getArbolDirectorio() {
		return arbolDirectorio;
	}

	public void setArbolDirectorio(TreeNode<DTODocumento> arbolDirectorio) {
		this.arbolDirectorio = arbolDirectorio;
	}

	public TreeNode<DTODocumento>[] getNodosSeleccionados() {
		return nodosSeleccionados;
	}

	public void setNodosSeleccionados(TreeNode<DTODocumento>[] nodosSeleccionados) {
		this.nodosSeleccionados = nodosSeleccionados;
	}

	public String getEncodingArchivoCarga() {
		return encodingArchivoCarga;
	}

	public void setEncodingArchivoCarga(String encodingArchivoCarga) {
		this.encodingArchivoCarga = encodingArchivoCarga;
	}

	public String getPatternToValidate() {
		return patternToValidate;
	}

	public void setPatternToValidate(String patternToValidate) {
		this.patternToValidate = patternToValidate;
	}

	@Override
	public String toString() {
		return "FormEstatusDatosPersonales [procesoValido=" + procesoValido + ", mensaje=" + mensaje
				+ ", rutaDirectorioPrincipal=" + rutaDirectorioPrincipal + ", rutaDirectorioDerfe="
				+ rutaDirectorioDerfe + ", threadPoolSize=" + threadPoolSize + ", catalogoEtapas=" + catalogoEtapas
				+ ", tipoEjecucion=" + tipoEjecucion + ", listEstatus=" + listEstatus + ", listEstatusFiltrada="
				+ listEstatusFiltrada + ", queueEstadosEjecutando=" + queueEstadosEjecutando + ", arbolDirectorio="
				+ arbolDirectorio + ", nodosSeleccionados=" + Arrays.toString(nodosSeleccionados)
				+ ", encodingArchivoCarga=" + encodingArchivoCarga + ", patternToValidate=" + patternToValidate + "]";
	}
	
}
