package mx.ine.procprimerinsa.form;

import java.io.Serializable;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import mx.ine.procprimerinsa.dto.DTOEstadoGeneral;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;

public class FormGenerarLlaves implements Serializable {

	private static final long serialVersionUID = 4238803666922641848L;	
	
	private Integer idProcesoElectoral;
	private Integer idDetalleProceso;
	
	private Integer modoEjecucion;
	
	private Integer idEstado;
	private Integer idParticipacion;
	
	private boolean procesoValido;
	private String mensaje;
	
	private TreeNode<Nodo> arbol;
	private TreeNode<Nodo> nodoSeleccionado;
	
	private String ruta;
	
	private String llaveVE;
	private String llaveVCEyEC;
	
	private boolean descargaActiva;
	private boolean reinicioActivo;
	private boolean generacionActiva;
	private boolean exitoLlaves;
	private boolean visorActivo;
	
	public FormGenerarLlaves(){
		limpiaArbol();
		exitoLlaves = false;
		visorActivo = false;
	}
	
	public void limpiaArbol() {
		arbol = new DefaultTreeNode<>(new Nodo("Raiz", 0, 0, ""), null);
	}

	public void inicializaArbol(String proceso) {
		new DefaultTreeNode<>(new Nodo(proceso, 0, 0, ""), this.arbol);
	}
	
	public TreeNode<Nodo> agregaEstado(DTOEstadoGeneral estado) {
		return new DefaultTreeNode<>(new Nodo(estado.getNombreEstado(), 
												estado.getIdEstado(),
												0, 
												""),
									getArbol().getChildren().get(0));
	}
	
	public void agregaParticipacion(DTOParticipacionGeneral participacion, String rutaVE, String rutaVCEYEC, 
			TreeNode<Nodo> nodoEstado) {
			
		TreeNode<Nodo> nodoParticipacion = new DefaultTreeNode<>(
				new Nodo(String.format("%02d", participacion.getId()) + " - " + participacion.getNombre(), 
						nodoEstado.getData().getIdEstado(), 
						participacion.getId(),
						""), 
				nodoEstado);
		
		if(rutaVE != null && rutaVCEYEC != null) {
			new DefaultTreeNode<>("llave", 
					new Nodo("Llave VE", 
							nodoEstado.getData().getIdEstado(), 
							participacion.getId(), 
							rutaVE), 
					nodoParticipacion);
			new DefaultTreeNode<>("llave", 
					new Nodo("Llave VCEyEC", 
							nodoEstado.getData().getIdEstado(), 
							participacion.getId(), 
							rutaVCEYEC), 
					nodoParticipacion);
		} else {
			nodoParticipacion.getData().setLleno(false);
			nodoEstado.getData().setLleno(false);
			nodoEstado.getParent().getData().setLleno(false);
		}
	}
	
	public String actualizaNodoSeleccionado(){
		Nodo nodo = nodoSeleccionado.getData();
		idEstado = nodo.getIdEstado();
		idParticipacion = nodo.getIdNivelCaptura();
		
		descargaActiva = nodo.isLleno();
		reinicioActivo = nodo.isLleno();
		generacionActiva = !nodo.isLleno();
		
		return nodo.getRuta();
	}
	
	public Integer getIdProcesoElectoral() {
		return idProcesoElectoral;
	}

	public void setIdProcesoElectoral(Integer idProcesoElectoral) {
		this.idProcesoElectoral = idProcesoElectoral;
	}

	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}

	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}

	public Integer getModoEjecucion() {
		return modoEjecucion;
	}

	public void setModoEjecucion(Integer modoEjecucion) {
		this.modoEjecucion = modoEjecucion;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Integer getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
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

	public TreeNode<Nodo> getArbol() {
		return arbol;
	}

	public void setArbol(TreeNode<Nodo> arbol) {
		this.arbol = arbol;
	}
	
	public TreeNode<Nodo> getNodoSeleccionado() {
		return nodoSeleccionado;
	}
	
	public void setNodoSeleccionado(TreeNode<Nodo> nodoSeleccionado) {
		this.nodoSeleccionado = nodoSeleccionado;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getLlaveVE() {
		return llaveVE;
	}

	public void setLlaveVE(String llaveVE) {
		this.llaveVE = llaveVE;
	}

	public String getLlaveVCEyEC() {
		return llaveVCEyEC;
	}

	public void setLlaveVCEyEC(String llaveVCEyEC) {
		this.llaveVCEyEC = llaveVCEyEC;
	}

	public boolean isDescargaActiva() {
		return descargaActiva;
	}

	public void setDescargaActiva(boolean descargaActiva) {
		this.descargaActiva = descargaActiva;
	}

	public boolean isReinicioActivo() {
		return reinicioActivo;
	}

	public void setReinicioActivo(boolean reinicioActivo) {
		this.reinicioActivo = reinicioActivo;
	}

	public boolean isGeneracionActiva() {
		return generacionActiva;
	}

	public void setGeneracionActiva(boolean generacionActiva) {
		this.generacionActiva = generacionActiva;
	}

	public boolean isExitoLlaves() {
		return exitoLlaves;
	}

	public void setExitoLlaves(boolean exitoLlaves) {
		this.exitoLlaves = exitoLlaves;
	}

	public boolean isVisorActivo() {
		return visorActivo;
	}

	public void setVisorActivo(boolean visorActivo) {
		this.visorActivo = visorActivo;
	}


	public class Nodo implements Serializable, Comparable<Nodo> {
		
		private static final long serialVersionUID = -733182876439614844L;
		
		private String nombreNodo;
		private Integer idEstado;
		private Integer idNivelCaptura;
		private String ruta;
		private boolean lleno;
		
		public Nodo(String nombreNodo, Integer idEstado, Integer idNivelCaptura, String ruta){
			this.nombreNodo = nombreNodo;
			this.idEstado = idEstado;
			this.idNivelCaptura = idNivelCaptura;
			this.ruta =  ruta;
			lleno = true;
		}

		public String getNombreNodo() {
			return nombreNodo;
		}

		public void setNombreNodo(String nombreNodo) {
			this.nombreNodo = nombreNodo;
		}

		public Integer getIdEstado() {
			return idEstado;
		}

		public void setIdEstado(Integer idEstado) {
			this.idEstado = idEstado;
		}

		public Integer getIdNivelCaptura() {
			return idNivelCaptura;
		}

		public void setIdNivelCaptura(Integer idNivelCaptura) {
			this.idNivelCaptura = idNivelCaptura;
		}

		public String getRuta() {
			return ruta;
		}

		public void setRuta(String ruta) {
			this.ruta = ruta;
		}

		public boolean isLleno() {
			return lleno;
		}

		public void setLleno(boolean lleno) {
			this.lleno = lleno;
		}
		
		@Override
		public int compareTo(Nodo nodo) {
			return this.getNombreNodo().compareTo(nodo.getNombreNodo());
		}

		@Override
		public String toString(){
			return nombreNodo;
		}
	}
}
