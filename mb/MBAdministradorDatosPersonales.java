package mx.ine.procprimerinsa.mb;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import org.jboss.logging.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASParametrosInterface;
import mx.ine.procprimerinsa.as.ASWSRegistraBitacoraInterface;
import mx.ine.procprimerinsa.bsd.BSDAdministradorDatosPersonalesInterface;
import mx.ine.procprimerinsa.bsd.BSDMapasInterface;
import mx.ine.procprimerinsa.configuration.GeneraEstructuraProcesos;
import mx.ine.procprimerinsa.dto.DTODocumento;
import mx.ine.procprimerinsa.dto.DTOArchivoDatosPersonales;
import mx.ine.procprimerinsa.dto.db.DTOEstatusDatosPersonales;
import mx.ine.procprimerinsa.form.FormEstatusDatosPersonales;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

@Component("mbAdminDatosPersonales")
@Scope("session")
public class MBAdministradorDatosPersonales implements Serializable {

	private static final long serialVersionUID = -1852333527981126740L;
	private static final Logger logger = Logger.getLogger(MBAdministradorDatosPersonales.class);
	private static final String MENSAJE_ALERT = "mensajeAlert";
	private static final String MENSAJE_ALERT_TABLA = "mensajeAlertTabla";
	private static final int TIPO_MODULO_DESCARGA = 0;
	
	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdAdministradorDatosPersonales")
	private BSDAdministradorDatosPersonalesInterface bsdDatosPersonales;
	
	@Autowired
	@Qualifier("bsdMapas")
	private BSDMapasInterface bsdMapas;
	
	@Autowired
	@Qualifier("estructuraProcesos")
	private GeneraEstructuraProcesos estructuraProcesos;
	
	@Autowired
	@Qualifier("wsRegistraBitacora")
	private transient ASWSRegistraBitacoraInterface wsRegistraBitacora;
	
	@Autowired
	@Qualifier("asParametros")
	private ASParametrosInterface asParametros;
	
	private static final AtomicInteger estadosDescargaTotales = new AtomicInteger(0);
	private static final AtomicInteger estadosDescarga = new AtomicInteger(0);
	private static Boolean isPollingDescarga = false;
	
	private static final AtomicInteger estadosCargaTotales = new AtomicInteger(0);
	private static final AtomicInteger estadosCarga = new AtomicInteger(0);
	private static Boolean isPollingCarga = false;
		
	private FormEstatusDatosPersonales form;
	
	public void init(int tipoModulo) {
		
		form = new FormEstatusDatosPersonales();
		
		try {
			
			String menuValido = mbAdmin.verificaElementosMenu(true, false, false);
			
			if (!menuValido.isEmpty()) {
				form.setProcesoValido(false);
				form.setMensaje(menuValido);
				return;
			} 
			
			wsRegistraBitacora.solicitaRegistro(mbAdmin.getAdminData().getUsuario(), 
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
											Constantes.SERVICIO_BITACORA_ADMIN_DATOS_PERSONALES,
											Constantes.EJECUTA);
			
			form.setTipoModulo(tipoModulo);
			
			String paramThreadPoolSize = asParametros.obtenerParametro(
								mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
								mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
								0, 
								0, 
								Constantes.PARAMETRO_THREAD_POOL_SIZE_CARGA_PRIMERA_CAPA);
			
			if(paramThreadPoolSize == null 
				|| paramThreadPoolSize.isBlank()) {
				form.setProcesoValido(false);
				form.setMensaje("El parámetro del tamaño del pool de ejecución no se encuentra definido");
				return;
			}
			
			form.setThreadPoolSize(Integer.parseInt(paramThreadPoolSize));
			
			form.setRutaDirectorioPrincipal(bsdDatosPersonales.generaDirectorioPrincipal(
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso()));
			
			form.setRutaDirectorioDerfe(bsdDatosPersonales.generaDirectorioDatosDerfe(
										mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
										mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso()));
			
			form.setEncodingArchivoCarga(asParametros.obtenerParametro(
								mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
								mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
								0,
								0, 
								Constantes.PARAMETRO_ENCODING_ARCHIVO_CARGA_DATOS_PERSONALES));
			
			form.setPatternToValidate(asParametros.obtenerParametro(
								mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
								mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
								0,
								0, 
								Constantes.PARAMETRO_REGEX_TO_VALIDATE_CARGA_DATOS_PERSONALES));
			
			form.setCatalogoEtapas(Constantes.ETAPA_ESTATUS_DATOS_PERSONALES
									.keySet()
									.stream()
									.toList());
			
			form.setListaArchivos(new ArrayList<>());
			mostrarArbolDirectorio();
			
			actualizaEstatusArchivos(false);
			
			form.setProcesoValido(true);
			
		} catch(Exception e) {
			form.setProcesoValido(false);
			form.setMensaje(Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo"));
			logger.error("ERROR MBAdministradorDatosPersonales -init: ", e);
		}
	}
	
	public double getPorcentajeAvance() {
		return ((double)(obtieneEstadosTotales(form.getTipoModulo()) 
						- obtieneEstadosEjecutando(form.getTipoModulo()))
					/obtieneEstadosTotales(form.getTipoModulo())) * 100;
	}
	
	public String getDescripcionEtapa(Character etapa) {
		return Constantes.ETAPA_ESTATUS_DATOS_PERSONALES.get(etapa);
	}
	
	public void actualizaEstatusArchivos(boolean isFromPolling) {
		
		try {
			form.setListEstatus(bsdDatosPersonales.obtenerListaEstatusDatosPersonales(
										mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
										mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
										mbAdmin.getAdminData().getProcesoSeleccionado().getCorte()));
			
			if(form.getListEstatusFiltrada() != null) PrimeFaces.current().executeScript("PF('estatusTable').filter();");
			
			if(isFromPolling && obtieneEstadosEjecutando(form.getTipoModulo()) == 0) {
				setIsPolling(form.getTipoModulo(), false);
			}
			
		} catch (Exception e) {
			logger.error("ERROR MDAdministradorDatosPersonales -actualizaEstatusArchivos: ", e);
		}
	}
	
	public void ejecutaCargaPool(Integer tipoEjecucion) {
		form.setTipoEjecucion(tipoEjecucion);
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			if(isDisableToExecute(form.getTipoModulo())) {
				context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"No se puede iniciar una nueva ejecución hasta que finalice la anterior"));
				return;
			}
			
			if(form.getListEstatusFiltrada() == null) {
				form.setQueueEstadosEjecutando(
						new ConcurrentLinkedQueue<>(form.getListEstatus()
													.stream()
													.collect(Collectors.toList())));
			} else if(form.getListEstatusFiltrada().isEmpty()){
				context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"No se encontraron estados a ejecutar con los parámetros seleccionados"));
				return;
			} else {
				form.setQueueEstadosEjecutando(
						new ConcurrentLinkedQueue<>(form.getListEstatusFiltrada()
													.stream()
													.collect(Collectors.toList())));
			}
			
			if(form.getTipoModulo() == TIPO_MODULO_DESCARGA) {
				String estadosNoFinalizados = form.getQueueEstadosEjecutando()
										.stream()
										.filter(e -> !e.getFinalizoProcesoInsaculacion())
										.map(e -> e.getNombreEstado())
										.collect(Collectors.joining(", "));
				
				if(!estadosNoFinalizados.isEmpty()) {
					String mensajeError = estadosNoFinalizados.contains(",") ? 
							"Los estados de "  + estadosNoFinalizados  + " no han finalizado el proceso de primera insaculación"
							: estadosNoFinalizados + " no ha finalizado el proceso de primera insaculación";
							
					context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
								mensajeError));
					
					return;
				}
			} else {
				for(DTOArchivoDatosPersonales estado : form.getQueueEstadosEjecutando()) {
					estado.setExisteArchivo(bsdDatosPersonales.compruebaExistenciaArchivo(
							form.getRutaDirectorioDerfe() + 
							bsdDatosPersonales.generarNombreArchivoCarga(estado.getIdEstado())));
				}
				
				String estadosNoExisteArchivo = form.getQueueEstadosEjecutando()
													.stream()
													.filter(e -> !e.getExisteArchivo())
													.map(e -> e.getNombreEstado())
													.collect(Collectors.joining(", "));
				
				if(!estadosNoExisteArchivo.isEmpty()) {
					String mensajeError = (estadosNoExisteArchivo.contains(",") ? 
							"Los estados de "  + estadosNoExisteArchivo  + " no tienen un archivo para "
							: estadosNoExisteArchivo + " no tiene un archivo para ") 
							+ (form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_VALIDACION_ARCHIVO ?  "validar" : "actualizar");
					
					context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							mensajeError));
					return;
				}
				
				if(form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_ACTUALIZACION_DATOS_PERSONALES) {
					String estadosNoValidados = form.getQueueEstadosEjecutando()
												.stream()
												.filter(e -> !e.getIdAccion().equals(Constantes.ESTATUS_DATOS_PERSONALES_VALIDADO))
												.map(e -> e.getNombreEstado())
												.collect(Collectors.joining(", "));
					
					if(!estadosNoValidados.isEmpty()) {
						String mensajeError = estadosNoValidados.contains(",") ? 
								"Los estados de "  + estadosNoValidados  + " no tienen un archivo validado "
								: estadosNoValidados + " no tiene un archivo validado ";
						
						context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
								mensajeError));
						return;
					}
				}
				
			}
			
			int i = form.getQueueEstadosEjecutando().size();
			
			if(i == 0) {
				context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"No se encontraron estados a ejecutar con los parámetros seleccionados"));
				return;
			}
			
			setEstadosTotales(form.getTipoModulo(), i);
			setEstados(form.getTipoModulo(), i);
			
			PrimeFaces.current().executeScript("PF('progressBar').start();");
			
			setIsPolling(form.getTipoModulo(), true);
			
			ExecutorService executorService = Executors.newFixedThreadPool(form.getThreadPoolSize());
			
			while(i>0) {
				executorService.submit(this::ejecucionCargaPoolEstado);
				i--;
			}
			
			executorService.shutdown();
						
		} catch (Exception e) {
			context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"Ocurrió un error durante la ejecución " + e.getLocalizedMessage()));
			logger.error("ERROR MBAdministradorDatosPersonales -ejecutaCargaPool: ", e);
		}
	}
	
	public Boolean ejecucionCargaPoolEstado() {
    	try {
			
    		DTOArchivoDatosPersonales estado = form.getQueueEstadosEjecutando().poll();
    		
    		if(form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_GENERACION_DATOS_MINIMOS) {
    			generarArchivosDatosPersonalesPorEstado(estado);
    			decrementEstados(form.getTipoModulo());
    			return true;
    		}
    		
    		if(form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_VALIDACION_ARCHIVO) {
    			validarArchivosDatosPersonalesPorEstado(estado);
    			decrementEstados(form.getTipoModulo());
    			return true;
    		}
    		
    		if(form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_ACTUALIZACION_DATOS_PERSONALES) {
    			actualizarDatosPersonalesPorEstado(estado);
    			decrementEstados(form.getTipoModulo());
    			return true;
    		}
			
		} catch(Exception e) {
			logger.error("ERROR MBAdministradorDatosPersonales -ejecutaCargaEstado: ", e);
			decrementEstados(form.getTipoModulo());
			return false;
		}
		
    	decrementEstados(form.getTipoModulo());
		return true;
    }
	
	private void generarArchivosDatosPersonalesPorEstado(DTOArchivoDatosPersonales estado) {		
		String estatus = bsdDatosPersonales.generarArchivoDatosMinimosPorEstado(
									estado.getIdProceso(),
									estado.getIdDetalle(),
									estado.getIdEstado(),
									mbAdmin.getAdminData().getUsuario().getUsername());
		
		if(estatus != null && !estatus.isBlank()) {
			actualizaEstatusDatosPersonales(estado, null, estatus, 0);
		}
	}
	
	private void validarArchivosDatosPersonalesPorEstado(DTOArchivoDatosPersonales estado) {		
		String rutaArchivo = form.getRutaDirectorioDerfe()
									+ bsdDatosPersonales.generarNombreArchivoCarga(estado.getIdEstado());
				
		String estatus = bsdDatosPersonales.validarArchivosDatosPersonalesPorEstado(
									estado.getIdProceso(), 
									estado.getIdDetalle(),
									estado.getIdEstado(),
									rutaArchivo,
									mbAdmin.getAdminData().getUsuario().getUsername(),
									form.getPatternToValidate(),
									form.getEncodingArchivoCarga());
		
		if(estatus != null && !estatus.isBlank()) {
			actualizaEstatusDatosPersonales(estado, Constantes.ESTATUS_DATOS_PERSONALES_NO_VALIDO, estatus, 0);			
		}
	}
	
	private void actualizarDatosPersonalesPorEstado(DTOArchivoDatosPersonales estado) {
		String rutaArchivo = form.getRutaDirectorioDerfe() 
								+ bsdDatosPersonales.generarNombreArchivoCarga(estado.getIdEstado());
		
		String estatus = bsdDatosPersonales.actualizarDatosPersonalesPorEstado(
											estado.getIdProceso(), 
											mbAdmin.getAdminData().getTipoProceso(),
											estado.getIdDetalle(),
											estado.getIdEstado(),
											rutaArchivo,
											form.getEncodingArchivoCarga());
		
		Character idAccion = estatus == null || estatus.isBlank() ? 
							Constantes.ESTATUS_DATOS_PERSONALES_ACTUALIZADO
							: Constantes.ESTATUS_DATOS_PERSONALES_NO_VALIDO;
		
		actualizaEstatusDatosPersonales(estado, idAccion, estatus, null);
	}
	
	private void actualizaEstatusDatosPersonales(DTOArchivoDatosPersonales estado, Character idAccion, String estatus,
			Integer ciudadanosCarga) {
		DTOEstatusDatosPersonales estatusDatosPersonales = bsdDatosPersonales.obtenerEstatusDatosPersonales(
					estado.getIdProceso(),
					estado.getIdDetalle(),
					estado.getIdEstado());
		if(idAccion != null) estatusDatosPersonales.setIdAccion(idAccion);
		estatusDatosPersonales.setEstatus(estatus);
		if(ciudadanosCarga != null) estatusDatosPersonales.setCiudadanosCarga(ciudadanosCarga);
		estatusDatosPersonales.setUsuario(mbAdmin.getAdminData().getUsuario().getUsername());
		estatusDatosPersonales.setFechaHora(new Date());
		
		bsdDatosPersonales.actualizarEstatusDatosPersonales(estatusDatosPersonales);
	}
	
	public void descargaZip() {
		FacesContext context = FacesContext.getCurrentInstance();
		Set<String> filesToInclude;
		
		if(form.getListEstatusFiltrada() == null) {
			filesToInclude = form.getListEstatus()
						.stream()
						.map(e -> bsdDatosPersonales.generaNombreArchivo(e.getIdEstado()) + ".gpg")
						.collect(Collectors.toSet());
		} else if(form.getListEstatusFiltrada().isEmpty()){
			context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"No se encontraron archivos a descargar con los parámetros seleccionados"));
			return;
		} else {
			filesToInclude = form.getListEstatusFiltrada()
							.stream()
							.map(e -> bsdDatosPersonales.generaNombreArchivo(e.getIdEstado()) + ".gpg")
							.collect(Collectors.toSet());
		}
		
		StringBuilder nombreZip = new StringBuilder()
									.append(Constantes.CARPETA_DATOS_PERSONALES)
									.append("_")
									.append(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral())
									.append("_")
									.append(mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso());
		
		try {
			bsdDatosPersonales.comprimirArchivos(form.getRutaDirectorioPrincipal(), 
												filesToInclude, 
												nombreZip.toString());
		} catch (Exception e) {
			logger.error("ERROR MDAdministradorDatosPersonales -descargaZip: ", e);
			context.addMessage(MENSAJE_ALERT, 
							new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
											"Ocurrió un error al generar el archivo zip"));
		}
	}
	
	private void mostrarArbolDirectorio() {
		try {
			form.setArbolDirectorio(bsdDatosPersonales.generarArbol(form.getRutaDirectorioDerfe()));
		} catch(Exception e) {
			logger.error("ERROR MDAdministradorDatosPersonales -mostrarArbolDirectorio: ", e);
		}
	}
	
	public void cargarArchivo(FileUploadEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			if(event.getFile() == null) return;
				
			UploadedFile archivo = event.getFile();
			Optional<UploadedFile> buscarArchivo = form.getListaArchivos()
								.stream()
								.filter(archivos -> archivos.getFileName().equals(archivo.getFileName()))
								.findFirst();
			
			if(buscarArchivo.isPresent()) {
				context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_WARN, " ", 
								"Los archivos seleccionados ya han sido cargados con anterioridad"));
				return;
			}
			
			form.getListaArchivos().add(archivo);
			
		} catch(Exception e) {
			logger.error("ERROR MDAdministradorDatosPersonales -cargarArchivo: ", e);
		}
	}
	
	public void quitarArchivo(UploadedFile archivo) {
		form.getListaArchivos().remove(archivo);
	}
	
	public void subirArchivos() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {			
			
			if(!bsdDatosPersonales.subirArchivos(
					mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
					mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
					estructuraProcesos.getProcesos()
									.get(mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso())
									.getEstados(),
					form.getRutaDirectorioDerfe(),
					form.getListaArchivos(),
					mbAdmin.getAdminData().getUsuario().getUsername())) {
				context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
										"El archivo no se logró cargar, revise la nomenclatura del nombre"));
			} else {
				
				actualizaEstatusArchivos(false);
								
				for(UploadedFile archivo : form.getListaArchivos()) {		
					
					if(!buscarArchivoEnNodo(form.getArbolDirectorio().getChildren(), archivo.getFileName())) {
						new DefaultTreeNode<>(DTODocumento.FILE, 
											new DTODocumento(archivo.getFileName(), 
													"-", 
													DTODocumento.FILE, 
													File.separator), 
													form.getArbolDirectorio());
					}
					
				}
				
				form.setListaArchivos(new ArrayList<>());
				context.addMessage(MENSAJE_ALERT, 
								new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
												"Los archivos se subieron con éxito"));
			}
			
		} catch(Exception e) {
			logger.error("ERROR MDAdministradorDatosPersonales -subirArchivos: ", e);
			context.addMessage(MENSAJE_ALERT, 
								new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
												"Ocurrió un error al subir los archivos"));
		}
	}
	
	public boolean buscarArchivoEnNodo(List<TreeNode<DTODocumento>> listaNodo, String nombreArchivo) {
		for(TreeNode<DTODocumento> nodo : listaNodo) {
			if(nodo.getData().getNombre().equals(nombreArchivo)) return true;
		}
		return false;
	}
	
	public void eliminarArchivos() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			
			for(TreeNode<DTODocumento> nodo : form.getNodosSeleccionados()) {
				DTODocumento datosNodo = nodo.getData();
				String ruta = form.getRutaDirectorioDerfe() + datosNodo.toString();
				
				if(!bsdDatosPersonales.bajarArchivo(ruta)) {
					context.addMessage(MENSAJE_ALERT, 
							new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
											"Ocurrió un error al eliminar"));
					return;
				}
				
				nodo.getParent().getChildren().remove(nodo);
				
				bsdDatosPersonales.actualizarEstatusDatosPersonalesEliminar(
						mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
						mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
						datosNodo.getNombre(),
						mbAdmin.getAdminData().getUsuario().getUsername());
				
				actualizaEstatusArchivos(false);
			}
			
			form.setNodosSeleccionados(null);
			
			context.addMessage(MENSAJE_ALERT, 
								new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
												"Los archivos se eliminaron con éxito"));
		} catch(Exception e) {
			logger.error("ERROR MDAdministradorDatosPersonales -eliminarArchivos: ", e);
			context.addMessage(MENSAJE_ALERT, 
								new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
												"Ocurrió un error al eliminar"));
		}
	}
	
	public FormEstatusDatosPersonales getForm() {
		return form;
	}

	public boolean isDisableToExecute(int tipoModulo) {
		return obtieneEstadosEjecutando(tipoModulo) > 0;
	}
	
	private static synchronized void setEstadosTotales(int tipoModulo, int estadosTotales) {
		if(tipoModulo == TIPO_MODULO_DESCARGA) {
			MBAdministradorDatosPersonales.estadosDescargaTotales.set(estadosTotales);
		} else {
			MBAdministradorDatosPersonales.estadosCargaTotales.set(estadosTotales);
		}
	}
	
	public static synchronized int obtieneEstadosTotales(int tipoModulo) {
		return tipoModulo == TIPO_MODULO_DESCARGA ? 
				estadosDescargaTotales.get()
				: estadosCargaTotales.get();
	}
	
	private static synchronized void setEstados(int tipoModulo, int estados) {
		if(tipoModulo == TIPO_MODULO_DESCARGA) {
			MBAdministradorDatosPersonales.estadosDescarga.set(estados);
		} else {
			MBAdministradorDatosPersonales.estadosCarga.set(estados);
		}
	}
	
	private static synchronized int decrementEstados(int tipoModulo) {
		return tipoModulo == TIPO_MODULO_DESCARGA ? 
				estadosDescarga.decrementAndGet()
				: estadosCarga.decrementAndGet();
	}

	public static synchronized int obtieneEstadosEjecutando(int tipoModulo) {
		return tipoModulo == TIPO_MODULO_DESCARGA ? 
				estadosDescarga.get()
				: estadosCarga.get();
	}
	
	private static synchronized void setIsPolling(int tipoModulo, Boolean isPolling) {
		if(tipoModulo == TIPO_MODULO_DESCARGA) {
			MBAdministradorDatosPersonales.isPollingDescarga = isPolling;
		} else {
			MBAdministradorDatosPersonales.isPollingCarga = isPolling;
		}
	}

	public static synchronized Boolean getIsPolling(int tipoModulo) {
		return tipoModulo == TIPO_MODULO_DESCARGA ? 
							isPollingDescarga
							: isPollingCarga;
	}
	
}
