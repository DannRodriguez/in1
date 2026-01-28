package mx.ine.procprimerinsa.mb;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jboss.logging.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.procprimerinsa.as.ASParametrosInterface;
import mx.ine.procprimerinsa.bsd.BSDAdminGlusterInterface;
import mx.ine.procprimerinsa.bsd.BSDCargaPrimeraCapaInterface;
import mx.ine.procprimerinsa.bsd.BSDMapasInterface;
import mx.ine.procprimerinsa.dto.DTOEstatusCargaPrimeraCapaDistrito;
import mx.ine.procprimerinsa.form.FormCargaPrimeraCapa;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

@Qualifier("mbCargaPrimeraCapa")
@RequestScoped
public class MBCargaPrimeraCapa implements Serializable {
	
	private static final long serialVersionUID = 4926102984095410608L;
	private static final Logger logger = Logger.getLogger(MBCargaPrimeraCapa.class);
	private static final String MENSAJE_ALERT = "mensajeAlert";
	private static final String MENSAJE_ALERT_TABLA = "mensajeAlertTabla";
	private static final String ARCHIVO_ESTATUS = "estatusCargaPrimeraCapa";
	private static final String[] HEADER_ESTATUS = new String[] { "Id proceso electoral",
																"Id detalle proceso",
																"Grupo",
																"Id estado",
																"Estado",
																"Id participación", 
																"Id distrito",
																"Distrito",
																"Etapa",
																"Estatus",
																"Marcado NOMBRE_ORDEN",
																"Marcado NOMBRE_ORDEN finalizado",
																"Tiempo de marcado NOMBRE_ORDEN",
																"Reinicio YA_ES_INSACULADO",
																"Reinicio YA_ES_INSACULADO finalizado",
																"Tiempo de reinicio YA_ES_INSACULADO",
																"Marcado AREAS_SECCIONES",
																"Marcado SECCIONES_COMPARTIDAS",
																"Marcado mal referenciados",
																"Marcado finalizado",
																"Tiempo de marcado",
																"Orden geográfico",
																"Orden letra/alfabético",
																"Orden visita",
																"Orden finalizado",
																"Tiempo de ordenamiento",
																"Borrado de PRIMERA_CAPA",
																"Carga de DATOS_INSACULADOS",
																"Carga de RESULTADOS_1A_INSA",
																"Creación/reinicio de secuencias",
																"Carga finalizada",
																"Tiempo de carga",
																"Datos insaculados", 
																"Datos insaculados CAPA1",
																"Resultados 1aINSA",
																"Resultados 1aINSA CAPA1",
																"Usuario",
																"Fecha"};
	
	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdCargaPrimeraCapa")
	private BSDCargaPrimeraCapaInterface bsdCargaPrimeraCapa;
	
	@Autowired
	@Qualifier("bsdMapas")
	private BSDMapasInterface bsdMapas;
	
	@Autowired
	@Qualifier("bsdAdminGlusterImpl")
	private transient BSDAdminGlusterInterface bsdAdminGluster;
	
	@Autowired
	@Qualifier("asParametros")
	private ASParametrosInterface asParametros;
	
	private static final AtomicInteger distritosTotales = new AtomicInteger(0);
	private static final AtomicInteger distritosEjecutando = new AtomicInteger(0);
	private static Boolean isPolling = false;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private FormCargaPrimeraCapa form;
	
	public void init() {
		
		try {
			form = new FormCargaPrimeraCapa();
			
			String menuValido = mbAdmin.verificaElementosMenu(true, false, false);
			
			if (!menuValido.isEmpty()) {
				form.setProcesoValido(false);
				form.setMensaje(menuValido);
				return;
			}
			
			String directory = new StringBuilder()
										.append(Constantes.RUTA_LOCAL_FS)
										.append(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral()).append(File.separator) 
										.append(mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso()).append(File.separator) 
										.append(Constantes.CARPETA_PROC_GLUSTER_INSA1).append(File.separator)
										.append(Constantes.CARPETA_SPOOL)
										.append(File.separator).toString();
			File directorio = new File(directory);
			directorio.mkdirs();
			
			form.setRutaArchivoServidoresPublicos(directory + Constantes.TITULO_ARCHIVO_SERVIDORES_PUBLICOS);
			form.setRutaArchivoAreasSecciones(directory + Constantes.TITULO_ARCHIVO_AREAS_SECCIONES);
			form.setRutaArchivoSeccionesCompartidas(directory + Constantes.TITULO_ARCHIVO_SECCIONES_COMPARTIDAS);
			form.setRutaArchivoOrdenada(directory + Constantes.TITULO_ARCHIVO_ORDENADA);
			form.setRutaArchivoOrdenadaCarga(directory + Constantes.TITULO_ARCHIVO_ORDENADA_CARGA);
			
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
			
			form.setCatalogoEtapas(Constantes.ETAPA_CARGA_PRIMERA_CAPA
									.keySet()
									.stream()
									.sorted()
									.toList());
			
			form.setDetalles(bsdMapas.getDetallesProceso(
												mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
												mbAdmin.getAdminData().getTipoProceso()));
			
			if(!bsdCargaPrimeraCapa.obtieneEstatusServidoresPublicos(form,
												mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso())) {
				form.setProcesoValido(false);
				form.setMensaje("No se logró obtener la información de servidores públicos");
				return;
			}
			
			if(!bsdCargaPrimeraCapa.obtieneEstatusInsumos(form,
						mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral())) {
				form.setProcesoValido(false);
				form.setMensaje("No se logró obtener la información del estatus de los insumos");
				return;
			}
			
			actualizaEstatusCargaDistritos();
			
			form.setEstatusIndices(bsdCargaPrimeraCapa.obtieneEstatusIndices());
			
			form.setEstatusTriggers(bsdCargaPrimeraCapa.obtieneEstatusTriggers());
			
			form.setProcesoValido(true);
			
		} catch (Exception e) {
			form.setProcesoValido(false);
			form.setMensaje(Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo"));
			logger.error("ERROR MBCargaPrimeraCapa -init: ", e);
		}
	}
	
	public void descargaEstatus() {
		try {
			
			Utilidades.descargaArchivoCSV(HEADER_ESTATUS,
										bsdCargaPrimeraCapa.obtieneLogEstatusCargaPrimeraCapa(form.getDetalles(), 
												mbAdmin.getAdminData().getProcesoSeleccionado().getCorte()),
										ARCHIVO_ESTATUS);
		} catch (Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -descargaEstatus: ", e);
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al descargar la tabla de estatus de carga: " + e.getLocalizedMessage()));
		}
	}
	
	public double getPorcentajeAvance() {
		return ((double)(obtieneDistritosTotales() - obtieneDistritosEjecutando())/obtieneDistritosTotales()) * 100;
	}
	
	public String getDescripcionEtapa(Integer etapa) {
		return etapa + " - " + Constantes.ETAPA_CARGA_PRIMERA_CAPA.get(etapa);
	}
	
	public void reiniciarEstatusCarga() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			if(!validaProcesoVigente()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"La carga no puede ser reiniciada en procesos electorales no vigentes"));
				return;
			}
			
			if(isDisableToExecute()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"No se puede reiniciar la tabla hasta que finalice la ejecución actual"));
				return;
			}
			
			if(bsdCargaPrimeraCapa.reiniciarEstatusCarga(form.getDetalles(),
												mbAdmin.getAdminData().getUsuario().getUsername())) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
						"La tabla de estatus de carga se reinició con éxito"));
			} else {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"Error al reiniciar la tabla de estatus de carga"));
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -reiniciarEstatusCarga: ", e);
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al reiniciar la tabla de estatus de carga: " + e.getLocalizedMessage()));
		}
		
		actualizaEstatusCargaDistritos();
		
	}
	
	public void ejecutaCargaServidoresPublicos() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
		
			if(isDisableToExecute()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"No se puede ejecutar la carga de servidores públicos hasta que finalice la ejecución actual"));
				return;
			}
			
			String respuestaReinicio = bsdCargaPrimeraCapa.reiniciaCargaServidoresPublicos(
									mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso());
			
			if(!respuestaReinicio.isEmpty()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"Error al realizar el borrado previo de la tabla de servidores públicos: " + respuestaReinicio));
				return;
			}
			
			if(bsdCargaPrimeraCapa.ejecutaCargaServidoresPublicos(
							mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
							mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso())) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
						"Éxito al realizar la carga de servidores públicos"));
			} else {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"Error al realizar la carga de servidores públicos"));
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -ejecutaCargaServidoresPublicos: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al realizar la carga de servidores públicos: " + e.getLocalizedMessage()));
		}
			
		bsdCargaPrimeraCapa.obtieneEstatusServidoresPublicos(form,
												mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso());
	}
	
	public void ejecutaMarcadoServidorPublico() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
		
			if(isDisableToExecute()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"No se puede ejecutar el marcado de servidores públicos hasta que finalice la ejecución actual"));
				return;
			}
			
			String respuesta = bsdCargaPrimeraCapa.ejecutaMarcadoServidorPublico(
															mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
															mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso());
			if(respuesta == null || respuesta.isBlank()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
						"Éxito al realizar el marcado de servidores públicos"));
			} else {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"Error al realizar el marcado de servidores públicos: " + respuesta));
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -ejecutaMarcadoServidorPublico: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al realizar el marcado de servidores públicos: " + e.getLocalizedMessage()));
		}
		
	}
	
	public void ejecutaReinicioServidorPublico() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
		
			if(isDisableToExecute()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"No se puede ejecutar el reinicio del marcado de servidores públicos hasta que finalice la ejecución actual"));
				return;
			}
			
			String respuesta = bsdCargaPrimeraCapa.ejecutaReinicioServidorPublico(
															mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
															mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso());
			if(respuesta == null || respuesta.isBlank()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
						"Éxito al realizar el reinicio del marcado de servidores públicos"));
			} else {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"Error al realizar el reinicio del marcado de servidores públicos: " + respuesta));
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -ejecutaReinicioServidorPublico: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al realizar el reinicio del marcado de servidores públicos: " + e.getLocalizedMessage()));
		}
		
	}
	
	public void ejecutaCargaARES() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
		
			if(isDisableToExecute()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"No se puede realizar la carga de ARES hasta que finalice la ejecución actual"));
				return;
			}
			
			String respuesta;
			
			for(int detalle : form.getDetalles()) {
				respuesta = bsdCargaPrimeraCapa.ejecutaCargaARES(detalle,
																mbAdmin.getAdminData().getUsuario().getUsername());
				
				if(respuesta != null && !respuesta.isBlank()) {
					context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al realizar la carga de ARES: " + respuesta));
					return;
				}
			}
			
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
							"La carga de ARES se realizó con éxito"));
			
		} catch (Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -ejecutaCargaARES: ", e);
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
									"Error al ejecutar la carga de ARES: " + e.getLocalizedMessage()));
		}
		
		bsdCargaPrimeraCapa.obtieneEstatusInsumos(form,
					mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral());
	}
	
	public void cargaArchivoOrdenada(FileUploadEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			if(event.getFile() != null) {
				bsdAdminGluster.guardarArchivo(event.getFile(), form.getRutaArchivoOrdenadaCarga());
			} else {
				context.addMessage(MENSAJE_ALERT, 
								new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
										"Debe seleccionar previamente un archivo CSV"));	
				return;
			}
			
			context.addMessage(MENSAJE_ALERT, 
								new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
										"El archivo se cargó exitosamente"));
			
		} catch (Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -cargaArchivoOrdenada: ", e);
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al cargar el archivo: " + e.getLocalizedMessage()));
		}
		
	}
	
	public boolean reiniciaCargaOrdenada() {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean isExito = false;
		
		try {
			if(bsdCargaPrimeraCapa.reiniciaCargaOrdenada(
					mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
					form.getDetalles())) {
				context.addMessage(MENSAJE_ALERT,
						new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
								"Éxito al reiniciar la carga de la ordenada"));
				isExito = true;
			} else {
				context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
								"Error al reiniciar la carga de la ordenada"));
			}
		} catch(Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -reiniciaCargaOrdenada: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al reiniciar la carga de la ordenada: " + e.getLocalizedMessage()));
		}
		
		bsdCargaPrimeraCapa.obtieneEstatusInsumos(form,
					mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral());
		return isExito;
	}
	
	public void ejecutaCargaOrdenada() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			if(!bsdAdminGluster.existeArchivo(form.getRutaArchivoOrdenadaCarga())) {
				context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
								"No existe un archivo para procesar la carga"));
				return;
			}
			
			if(!reiniciaCargaOrdenada()) {
				return;
			}
			
			if(bsdCargaPrimeraCapa.ejecutaCargaOrdenada(
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
											mbAdmin.getAdminData().getTipoProceso(),
											form.getRutaArchivoOrdenadaCarga(),
											mbAdmin.getAdminData().getUsuario().getUsername())) {
				context.addMessage(MENSAJE_ALERT,
						new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
								"Éxito al ejecutar la carga de la ordenada"));
			} else {
				context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
								"Error al ejecutar la carga de la ordenada"));
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -ejecutaCargaOrdenada: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al ejecutar la carga de la ordenada: " + e.getLocalizedMessage()));
		}
		
		bsdCargaPrimeraCapa.obtieneEstatusInsumos(form,
					mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral());
	}
	
	public StreamedContent spoolServidoresPublicos() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(!bsdCargaPrimeraCapa.spoolServidoresPublicos(mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
												form.getRutaArchivoServidoresPublicos())) {
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al generar el spool de SERVIDORES_PUBLICOS"));
			return null;
		}
		
		return ejecutaSpool(context, 
					form.getRutaArchivoServidoresPublicos(), 
					Constantes.TITULO_ARCHIVO_SERVIDORES_PUBLICOS);
	}
	
	public StreamedContent spoolAreasSecciones() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(!bsdCargaPrimeraCapa.spoolAreasSecciones(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
												mbAdmin.getAdminData().getTipoProceso(),
												form.getRutaArchivoAreasSecciones())) {
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al generar el spool de AREAS_SECCIONES"));
			return null;
		}
		
		return ejecutaSpool(context, 
					form.getRutaArchivoAreasSecciones(), 
					Constantes.TITULO_ARCHIVO_AREAS_SECCIONES);
	}
	
	public StreamedContent spoolSeccionesCompartidas() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(!bsdCargaPrimeraCapa.spoolSeccionesCompartidas(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
													mbAdmin.getAdminData().getTipoProceso(),
													form.getRutaArchivoSeccionesCompartidas())) {
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al generar el spool de SECCIONES_COMPARTIDAS"));
			return null;
		}
		
		return ejecutaSpool(context, 
					form.getRutaArchivoSeccionesCompartidas(), 
					Constantes.TITULO_ARCHIVO_SECCIONES_COMPARTIDAS);
	}
	
	public StreamedContent spoolOrdenada() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(!bsdCargaPrimeraCapa.spoolOrdenada(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
											mbAdmin.getAdminData().getTipoProceso(),
											form.getRutaArchivoOrdenada())) {
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al generar el spool de la ORDENADA"));
			return null;
		}
		
		return ejecutaSpool(context, 
					form.getRutaArchivoOrdenada(), 
					Constantes.TITULO_ARCHIVO_ORDENADA);
	}
	
	public StreamedContent ejecutaSpool(FacesContext context, String rutaArchivo, String nombreArchivo) {
		StreamedContent archivoDescarga = null;
		
		try {
			
			if(!bsdAdminGluster.existeArchivo(rutaArchivo)) {
				context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
								"No se obtuvo información del archivo generado por el spool"));
				return null;
			}
			
			InputStream stream = FileUtils.openInputStream(new File(rutaArchivo));
			
			archivoDescarga = DefaultStreamedContent.builder()
											.contentType("text/plain")
									        .name(nombreArchivo)
									        .stream(() -> stream)
									        .build();
			
		} catch(Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -ejecutaSpool:", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error generar el spool: " + e.getLocalizedMessage()));
		}
		
		return archivoDescarga;
	}
	
	public void creaEliminaIndices(boolean isCrea) {
		FacesContext context = FacesContext.getCurrentInstance();
		String accion = isCrea ? "crear" : "eliminar";
		
		try {
		
			if(isDisableToExecute()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"No se pueden " + accion + " los índices hasta que finalice la ejecución actual"));
				return;
			}
			
			String respuesta = bsdCargaPrimeraCapa.creaEliminaIndices(isCrea);
			if(respuesta == null || respuesta.isBlank()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
						"Éxito al " + accion + " los índices"));
			} else {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"Error al " + accion + " los índices: " + respuesta));
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -creaEliminaIndices: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al " + accion + " los índices: " + e.getLocalizedMessage()));
		}
			
		form.setEstatusIndices(bsdCargaPrimeraCapa.obtieneEstatusIndices());
	}
	
	public void recolectarEstadisticas() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
		
			if(isDisableToExecute()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"No se pueden recolectar las estadísticas hasta que finalice la ejecución actual"));
				return;
			}
			
			String respuesta = bsdCargaPrimeraCapa.recolectarEstadisticas();
			if(respuesta == null || respuesta.isBlank()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
						"Éxito al recolectar las estadísticas"));
			} else {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"Error al recolectar las estadísticas: " + respuesta));
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -recolectarEstadisticas: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al recolectar las estadísticas: " + e.getLocalizedMessage()));
		}
			
		form.setEstatusIndices(bsdCargaPrimeraCapa.obtieneEstatusIndices());
	}
	
	public boolean habilitaDeshabilitaTriggers(boolean isHabilita) {
		FacesContext context = FacesContext.getCurrentInstance();
		String accion = isHabilita ? "habilitar" : "deshabilitar";
		boolean isExito = false;
		
		try {
		
			if(isDisableToExecute()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"No se pueden " + accion + " los triggers hasta que finalice la ejecución actual"));
			}
			
			String respuesta = bsdCargaPrimeraCapa.habilitaDeshabilitaTriggers(isHabilita);
			if(respuesta == null || respuesta.isBlank()) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
						"Éxito al " + accion + " los triggers"));
				isExito = true;
			} else {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"Error al " + accion + " los triggers: " + respuesta));
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -habilitaDeshabilitaTriggers: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al " + accion + " los triggers: " + e.getLocalizedMessage()));
		}
		
		form.setEstatusTriggers(bsdCargaPrimeraCapa.obtieneEstatusTriggers());
		return isExito;
	}
	
	private boolean validaProcesoVigente() {
		
		try {
			Date fechaActual = new Date();
			
			Date fechaEjecucion = dateFormat.parse(asParametros.obtenerParametro(
																mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
																mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
																0, 
																0, 
																Constantes.PARAMETRO_FECHA_EJECUCION_PROCESO));
			
			Date fechaInicioSimulacro = dateFormat.parse(asParametros.obtenerParametro(
																mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
																mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
																0, 
																0, 
																Constantes.PARAMETRO_INICIO_PERIODO_SIMULACRO));
			
			Date fechaFinSimulacro = dateFormat.parse(asParametros.obtenerParametro(
																mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
																mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
																0, 
																0, 
																Constantes.PARAMETRO_FIN_PERIODO_SIMULACRO));
			
			return (fechaActual.after(fechaEjecucion)
					&& fechaActual.before(DateUtils.addDays(fechaEjecucion, 2)))
				|| (fechaActual.after(fechaInicioSimulacro) 
					&& fechaActual.before(DateUtils.addDays(fechaFinSimulacro, 1)));
			
		} catch(Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -validaProcesoVigente: ", e);
			return false;
		}
	}
	
	private boolean validaNuevaEjecucion(FacesContext context) {
		
		if(form.getLlave() == null || form.getLlave().isBlank()) {
			context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"Permisos inválidos"));
			return false;
		}
		
		Integer usuario = Utilidades.validaUsuario(form.getLlave());
		if(usuario == null) {
			context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"Permisos inválidos"));
			return false;
		}
		
		if(!validaProcesoVigente()) {
			context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"La carga no puede ser realizada en procesos electorales no vigentes"));
			return false;
		}
		
		if(isDisableToExecute()) {
			context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"No se puede iniciar una nueva ejecución hasta que finalice la anterior"));
			return false;
		}
		
		if(form.getTipoEjecucion() != Constantes.TIPO_EJECUCION_MARCADO_ARE
			&& form.getTipoEjecucion() != Constantes.TIPO_EJECUCION_ORDENAMIENTOS
			&& form.getTipoEjecucion() != Constantes.TIPO_EJECUCION_CARGA_PRIMERA_CAPA
			&& form.getTipoEjecucion() != Constantes.TIPO_EJECUCION_COMPLETA
			&& form.getTipoEjecucion() != Constantes.TIPO_EJECUCION_MARCADO_NOMBRE_ORDEN
			&& form.getTipoEjecucion() != Constantes.TIPO_EJECUCION_REINICIO_YA_ES_INSACULADO) {
			context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"El tipo de ejecución seleccionado para la carga no es válido"));
			return false;
		}
		
		return true;
	}

	public void cargaTipoEjecucion(Integer tipoEjecucion) {
		form.setTipoEjecucion(tipoEjecucion);
	}
	
	public void ejecutaCargaPool() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			if(!validaNuevaEjecucion(context)) {
				return;
			}
			
			if((form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_CARGA_PRIMERA_CAPA
					|| form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_COMPLETA)
				&& !habilitaDeshabilitaTriggers(false)) {
				return;
			}
			
			if(form.getListEstatusFiltrada() == null) {
				form.setQueueDistritosEjecutando(
						new ConcurrentLinkedQueue<>(form.getListEstatus()
													.stream()
													.collect(Collectors.toList())));
			} else if(form.getListEstatusFiltrada().isEmpty()){
				context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"No se encontraron distritos a ejecutar con los parámetros seleccionados"));
				return;
			} else {
				form.setQueueDistritosEjecutando(
						new ConcurrentLinkedQueue<>(form.getListEstatusFiltrada()
													.stream()
													.collect(Collectors.toList())));
			}
			
			int i = form.getQueueDistritosEjecutando().size();
			
			if(i == 0) {
				context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"No se encontraron distritos a ejecutar con los parámetros seleccionados"));
				return;
			}
			
			setDistritosTotales(i);
			
			setDistritosEjecutando(i);
						
			PrimeFaces.current().executeScript("PF('progressBar').start();");
			
			setIsPolling(true);
			
			ExecutorService executorService = Executors.newFixedThreadPool(form.getThreadPoolSize());
			
			while(i>0) {
				executorService.submit(this::ejecucionCargaPoolDistrito);
				i--;
			}
			
			executorService.shutdown();
						
		} catch (Exception e) {
			context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"Ocurrió un error durante la ejecución " + e.getLocalizedMessage()));
			logger.error("ERROR MBCargaPrimeraCapa -ejecutaCargaPool: ", e);
		}
	}
	
	public Boolean ejecucionCargaPoolDistrito() {
    	try {
			
    		DTOEstatusCargaPrimeraCapaDistrito distrito = form.getQueueDistritosEjecutando().poll();
    		
    		if(form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_MARCADO_NOMBRE_ORDEN) {
    			ejecutaMarcadoNombreOrdenDistrito(distrito);
    			decrementDistritosEjecutando();
    			return true;
    		}
    		
    		if(form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_REINICIO_YA_ES_INSACULADO) {
    			ejecutaReinicioYaEsInsaculadoDistrito(distrito);
    			decrementDistritosEjecutando();
    			return true;
    		}
    		    		
			if((form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_MARCADO_ARE
				|| form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_COMPLETA)
				&& !ejecutaCargaDistrito(Constantes.TIPO_EJECUCION_MARCADO_ARE, distrito, null)) {
				decrementDistritosEjecutando();
				return false;
			}
			
			if(form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_ORDENAMIENTOS
				|| form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_COMPLETA) {
				String tipoEjecucionProcesoInsa = asParametros.obtenerParametro(distrito.getIdProcesoElectoral(), 
																distrito.getIdDetalleProceso(), 
																distrito.getIdEstado(), 
																distrito.getIdDistrito(), 
																Constantes.PARAMETRO_TIPO_DE_EJECUCION);
				if(tipoEjecucionProcesoInsa == null) {
					distrito.setEstatus("No se encontró el tipo de ejecución de la insaculación para obtener la letra del ordenamiento");
					decrementDistritosEjecutando();
					return false;
				}
				
				String letraInsacular = asParametros.obtenerParametro(distrito.getIdProcesoElectoral(), 
																	distrito.getIdDetalleProceso(), 
																	distrito.getIdEstado(), 
																	distrito.getIdDistrito(), 
																	tipoEjecucionProcesoInsa.equals("J") ? 
																		Constantes.PARAMETRO_LETRA_A_INSACULAR
																		: Constantes.PARAMETRO_LETRA_A_INSACULAR_SIMULACRO);
				
				if(letraInsacular == null || letraInsacular.isBlank()) {
					distrito.setEstatus("No se encontró la letra para realizar el ordenamiento");
					decrementDistritosEjecutando();
					return false;
				}
				
				if(!ejecutaCargaDistrito(Constantes.TIPO_EJECUCION_ORDENAMIENTOS, distrito, letraInsacular)) {
					decrementDistritosEjecutando();
					return false;
				}
			
			}
			
			if((form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_CARGA_PRIMERA_CAPA
				|| form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_COMPLETA)
				&& !ejecutaCargaDistrito(Constantes.TIPO_EJECUCION_CARGA_PRIMERA_CAPA, distrito, null)) {
				decrementDistritosEjecutando();
				return false;
			}
			
		} catch(Exception e) {
			logger.error("ERROR MBCargaPrimeraCapa -ejecutaCargaDistrito: ", e);
			decrementDistritosEjecutando();
			return false;
		}
		
    	decrementDistritosEjecutando();
		return true;
    }
    
	private void ejecutaMarcadoNombreOrdenDistrito(DTOEstatusCargaPrimeraCapaDistrito distrito) {
		distrito.setEtapa(96);
		distrito.setUsuario(mbAdmin.getAdminData().getUsuario().getUsername());
		distrito.setEstatus("");
		
		if(!bsdCargaPrimeraCapa.actualizaEstatusCargaPrimeraCapaDistrito(distrito)) return;
		
		String estatus = bsdCargaPrimeraCapa.ejecutaMarcadoNombreOrden(distrito);
		
		distrito.setEtapa(97);
		distrito.setEstatus(estatus);
		
		bsdCargaPrimeraCapa.actualizaEstatusCargaPrimeraCapaDistrito(distrito);		
	}
	
	private void ejecutaReinicioYaEsInsaculadoDistrito(DTOEstatusCargaPrimeraCapaDistrito distrito) {
		distrito.setEtapa(98);
		distrito.setUsuario(mbAdmin.getAdminData().getUsuario().getUsername());
		distrito.setEstatus("");
		
		if(!bsdCargaPrimeraCapa.actualizaEstatusCargaPrimeraCapaDistrito(distrito)) return;
		
		String estatus = bsdCargaPrimeraCapa.ejecutaReinicioYaEsInsaculado(distrito);
		
		distrito.setEtapa(99);
		distrito.setEstatus(estatus);
		
		bsdCargaPrimeraCapa.actualizaEstatusCargaPrimeraCapaDistrito(distrito);		
	}
	
    private boolean ejecutaCargaDistrito(int tipoEjecucion, DTOEstatusCargaPrimeraCapaDistrito distrito, String letra) {
		String estatus = bsdCargaPrimeraCapa.ejecutaCarga(tipoEjecucion,
												distrito,
												letra,
												mbAdmin.getAdminData().getUsuario().getUsername());
		
		DTOEstatusCargaPrimeraCapaDistrito newDistrito = bsdCargaPrimeraCapa.obtieneEstatusCargaPrimeraCapaDistrito(
																distrito.getIdDetalleProceso(),
																distrito.getIdParticipacion(), 
																mbAdmin.getAdminData().getProcesoSeleccionado().getCorte());
		
		if(estatus != null && !estatus.isBlank()) {
			newDistrito.setUsuario(mbAdmin.getAdminData().getUsuario().getUsername());
			newDistrito.setEstatus(estatus);
			
			bsdCargaPrimeraCapa.actualizaEstatusCargaPrimeraCapaDistrito(newDistrito);
		}
		
		return newDistrito.getEstatus() == null  
				|| newDistrito.getEstatus().isBlank();
	}
	
	public void actualizaEstatusCargaDistritos() {
		form.setListEstatus(bsdCargaPrimeraCapa.obtieneEstatusCargaPrimeraCapa(
														form.getDetalles(),
														mbAdmin.getAdminData().getProcesoSeleccionado().getCorte()));
		if(form.getListEstatusFiltrada() != null) PrimeFaces.current().executeScript("PF('estatusTable').filter();");
		
		if(obtieneDistritosEjecutando() == 0) {
			setIsPolling(false);
		}
	}
	
	public boolean isDisableToExecute() {
		return obtieneDistritosEjecutando() > 0;
	}

	public FormCargaPrimeraCapa getForm() {
		return form;
	}
	
	private static synchronized void setDistritosTotales(int distritosTotales) {
		MBCargaPrimeraCapa.distritosTotales.set(distritosTotales);
	}
	
	public static synchronized int obtieneDistritosTotales() {
		return distritosTotales.get();
	}
	
	private static synchronized void setDistritosEjecutando(int distritosEjecutando) {
		MBCargaPrimeraCapa.distritosEjecutando.set(distritosEjecutando);
	}
	
	private static synchronized int decrementDistritosEjecutando() {
		return distritosEjecutando.decrementAndGet();
	}

	public static synchronized int obtieneDistritosEjecutando() {
		return distritosEjecutando.get();
	}
	
	private static synchronized void setIsPolling(Boolean isPolling) {
		MBCargaPrimeraCapa.isPolling = isPolling;
	}

	public static synchronized Boolean getIspolling() {
		return isPolling;
	}
	
}
