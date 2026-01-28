package mx.ine.procprimerinsa.mb;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import org.jboss.logging.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.bsd.BSDAdminGlusterInterface;
import mx.ine.procprimerinsa.dto.DTODocumento;
import mx.ine.procprimerinsa.util.Constantes;

@Component("mbAdminGluster")
@Scope("session")
public class MBAdminGluster implements Serializable {

	private static final long serialVersionUID = 5330627336692853564L;
	private static final Logger logger = Logger.getLogger(MBAdminGluster.class);
	private static final String MENSAJE_ALERT = "mensajeAlert";
	
	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdAdminGlusterImpl")
	private transient BSDAdminGlusterInterface bsdAdminGluster;
	
	private TreeNode<DTODocumento> arbolDirectorio;
	private TreeNode<DTODocumento> nodoSeleccionado;
	private TreeNode<DTODocumento>[] nodosSeleccionados;
	
	private String directorioPrincipal;
	private String rutaSubida;
	private String nombreCarpeta;
	
	private transient List<UploadedFile> listaArchivos;
	
	public void init() {
		inicializacionObjetos();
		mostrarArbolDirectorio();
	}
	
	public void inicializacionObjetos() {
		listaArchivos = new ArrayList<>();
		rutaSubida = File.separator;
		nodoSeleccionado = null;
		nombreCarpeta = null;
		if(mbAdmin.getAdminData().getProcesoSeleccionado() != null) {
			directorioPrincipal = bsdAdminGluster.generaDirectorioPrincipal(
								mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
								mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso()); 
		} else {
			directorioPrincipal = Constantes.RUTA_LOCAL_FS;
		}
		
	}
	
	public void mostrarArbolDirectorio() {
		try {
			arbolDirectorio = bsdAdminGluster.generarArbol(directorioPrincipal);
		} catch(Exception e) {
			logger.error("ERROR MBAdminGluster -mostrarArbolDirectorio: ", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void onNodeSelect(NodeSelectEvent event) {
		
		if(nodosSeleccionados.length > 1) {
			nodoSeleccionado = null;
		} else {
			nodoSeleccionado = event.getTreeNode();
		}
		
		if(event.getTreeNode().getChildCount() <= 0) {
			bsdAdminGluster.crearDocumentos(new File(directorioPrincipal + event.getTreeNode().getData().toString()), 
											event.getTreeNode(), 
											event.getTreeNode().getData().toString() + File.separator);
		}
		
		DTODocumento datoNodo = (DTODocumento) event.getTreeNode().getData();
		
		if(nodosSeleccionados.length == 1 && datoNodo.getTipo().equals(DTODocumento.FOLDER)) {
			rutaSubida = File.separator + event.getTreeNode().getData() + File.separator;
		} else {
			rutaSubida = File.separator;
		}
		
		event.getTreeNode().setExpanded(!event.getTreeNode().isExpanded());
		
	}
	
	public void onNodeCollapse(NodeCollapseEvent event) {
		event.getTreeNode().setExpanded(false);
	}
	
	public void onNodeExpand(NodeExpandEvent event) {
		event.getTreeNode().setExpanded(true);
	}
	
	public void onNodeUnselect(NodeUnselectEvent event) {
		rutaSubida = File.separator;
		
		if(nodosSeleccionados.length != 1) {
			nodoSeleccionado = null;
		} else {
			DTODocumento datoNodo = nodosSeleccionados[0].getData();
			
			if(datoNodo.getTipo().equals(DTODocumento.FOLDER)) {
				nodoSeleccionado = nodosSeleccionados[0];
				rutaSubida += nodosSeleccionados[0].getData() + File.separator;
			} else {
				nodoSeleccionado = null;
			}
		}
	}
	
	public void quitarArchivo(UploadedFile archivo) {
		listaArchivos.remove(archivo);
	}
	
	public void eliminarArchivos() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			for(TreeNode<DTODocumento> nodo : nodosSeleccionados) {
				bsdAdminGluster.bajarArchivo(directorioPrincipal + nodo.getData().toString());
				nodo.getParent().getChildren().remove(nodo);
			}
			nodoSeleccionado = null;
			nodosSeleccionados = null;
			rutaSubida = File.separator;
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
											"Los archivos se eliminaron con éxito"));
		} catch(Exception e) {
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
											"Ocurrió un error al eliminar"));
			logger.error("ERROR MBAdminGluster -eliminarArchivos: ", e);
		}
	}
	
	public boolean buscarArchivoEnNodo(List<TreeNode<DTODocumento>> listaNodo, String nombreArchivo) {
		for(TreeNode<DTODocumento> nodo : listaNodo) {
			DTODocumento documentoNodo = nodo.getData();
			if(documentoNodo.getNombre().equals(nombreArchivo)) return true;
		}
		return false;
	}
	
	public void subirArchivos() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {						
			
			if(!bsdAdminGluster.subirArchivos(directorioPrincipal + rutaSubida, listaArchivos)) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_FATAL, " ", 
											"El archivo no se logró cargar, revise la nomenclatura del nombre"));
			} else {				
				DTODocumento nodoDocumentoSeleccionado = nodoSeleccionado != null ? nodoSeleccionado.getData() : null;
				
				for(UploadedFile archivo : listaArchivos) {	
					
					if(nodoDocumentoSeleccionado != null && nodoDocumentoSeleccionado.getTipo().equals(DTODocumento.FOLDER)) {
						if(!buscarArchivoEnNodo(nodoSeleccionado.getChildren(), archivo.getFileName())) {
							new DefaultTreeNode<>(DTODocumento.FILE, 
												new DTODocumento(archivo.getFileName(), 
														"-", 
														DTODocumento.FILE, 
														nodoSeleccionado.getData().toString() + File.separator), 
												nodoSeleccionado);
						}
					} else {
						if(!buscarArchivoEnNodo(arbolDirectorio.getChildren(), archivo.getFileName())) {
							new DefaultTreeNode<>(DTODocumento.FILE, 
												new DTODocumento(archivo.getFileName(), 
														"-", 
														DTODocumento.FILE, 
														File.separator), 
												arbolDirectorio);
						}
					}
				}
				
				listaArchivos = new ArrayList<>();
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ",
																"Los archivos se subieron con éxito"));
			}
			
		} catch(Exception e) {
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_FATAL, " ", 
															"Ocurrió un error al subir los archivos"));
			logger.error("ERROR MBAdminGluster -subirArchivos: ", e);
		}
	}
	
	public void cargarArchivo(FileUploadEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			if(event.getFile() != null) {
				UploadedFile archivo = event.getFile();
				
				if(listaArchivos == null) {
					listaArchivos = new ArrayList<>();
				}
				
				Optional<UploadedFile> buscarArchivo = listaArchivos.stream()
														.filter(archivos -> archivos.getFileName().equals(archivo.getFileName()))
														.findFirst();
				
				if(buscarArchivo.isPresent()) {
					context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_WARN, " ", 
														"Los archivos seleccionados ya han sido cargados con anterioridad"));
					return;
				}
				
				listaArchivos.add(archivo);
			}
		} catch(Exception e) {
			logger.error("ERROR MBAdminGluster -cargarArchivo: ", e);
		}
	}
	
	public void crearCarpeta() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			if(nombreCarpeta == null) return;
			
			if(bsdAdminGluster.crearCarpeta(directorioPrincipal, rutaSubida, nombreCarpeta)) {
				DTODocumento nodoDocumentoSeleccionado = nodoSeleccionado != null ? nodoSeleccionado.getData() : null;
			
				if(nodoDocumentoSeleccionado != null 
					&& nodoDocumentoSeleccionado.getTipo().equals(DTODocumento.FOLDER)) {
					if(!buscarArchivoEnNodo(nodoSeleccionado.getChildren(), nombreCarpeta)) {
						new DefaultTreeNode<>(DTODocumento.FOLDER, 
											new DTODocumento(nombreCarpeta, 
													"-", 
													DTODocumento.FOLDER, 
													nodoSeleccionado.getData().toString() + File.separator), 
											nodoSeleccionado);
					}
				} else {
					if(!buscarArchivoEnNodo(arbolDirectorio.getChildren(), nombreCarpeta)) {
						new DefaultTreeNode<>(DTODocumento.FOLDER, 
											new DTODocumento(nombreCarpeta, 
													"-", DTODocumento.FOLDER, 
													""), 
											arbolDirectorio);
					}
				}
				
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
																"La carpeta se creó con éxito"));
				nombreCarpeta = null;
			} else {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
																"Ocurrió un error al crear la carpeta"));
			}
		} catch(Exception e) {
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
															"Ocurrió un error crear la carpeta"));
			logger.error("ERROR MBAdminGluster -crearCarpeta: ", e);
		}
	}
	
	public void descargar() {		
		DTODocumento nodoDocumentoSeleccionado = nodoSeleccionado != null ? nodoSeleccionado.getData() : null;
		
		try {
			
			if(nodoDocumentoSeleccionado != null) {
				if(nodoDocumentoSeleccionado.getTipo().equals(DTODocumento.FOLDER)) {
					bsdAdminGluster.comprimirArchivos(directorioPrincipal + rutaSubida, 
													nodoDocumentoSeleccionado.getNombre());
				} else {
					StringBuilder rutaArchivoDescarga = new StringBuilder(directorioPrincipal)
														.append(nodoDocumentoSeleccionado.getRuta())
														.append(nodoDocumentoSeleccionado.getNombre());
					bsdAdminGluster.descargarArchivo(rutaArchivoDescarga.toString());
				}
			} else {
				bsdAdminGluster.comprimirArchivos(directorioPrincipal, "INSA1");
			}
		} catch(Exception e) {
			logger.error("ERROR MBAdminGluster -descargar: ", e);
		}
	}
	
	public TreeNode<DTODocumento> getArbolDirectorio() {
		return arbolDirectorio;
	}

	public void setArbolDirectorio(TreeNode<DTODocumento> arbolDirectorio) {
		this.arbolDirectorio = arbolDirectorio;
	}

	public TreeNode<DTODocumento> getNodoSeleccionado() {
		return nodoSeleccionado;
	}

	public void setNodoSeleccionado(TreeNode<DTODocumento> nodoSeleccionado) {
		this.nodoSeleccionado = nodoSeleccionado;
	}

	public String getRutaSubida() {
		return rutaSubida;
	}

	public void setRutaSubida(String rutaSubida) {
		this.rutaSubida = rutaSubida;
	}

	public TreeNode<DTODocumento>[] getNodosSeleccionados() {
		return nodosSeleccionados;
	}

	public void setNodosSeleccionados(TreeNode<DTODocumento>[] nodosSeleccionados) {
		this.nodosSeleccionados = nodosSeleccionados;
	}

	public List<UploadedFile> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<UploadedFile> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public String getDirectorioPrincipal() {
		return directorioPrincipal;
	}

	public void setDirectorioPrincipal(String directorioPrincipal) {
		this.directorioPrincipal = directorioPrincipal;
	}

	public String getNombreCarpeta() {
		return nombreCarpeta;
	}

	public void setNombreCarpeta(String nombreCarpeta) {
		this.nombreCarpeta = nombreCarpeta;
	} 
	
}
