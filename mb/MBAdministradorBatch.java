package mx.ine.procprimerinsa.mb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import org.apache.commons.lang3.time.DateUtils;
import org.jboss.logging.Logger;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.procprimerinsa.as.ASArchivosInterface;
import mx.ine.procprimerinsa.as.ASBitacoraProcesosInterface;
import mx.ine.procprimerinsa.as.ASParametrosInterface;
import mx.ine.procprimerinsa.as.ASProcesoInsaInterface;
import mx.ine.procprimerinsa.bsd.BSDAdmistradorBatchInterface;
import mx.ine.procprimerinsa.bsd.BSDBitacoraInterface;
import mx.ine.procprimerinsa.bsd.BSDMapasInterface;
import mx.ine.procprimerinsa.bsd.BSDReinicioProcesoInterface;
import mx.ine.procprimerinsa.dto.DTOAdministradorBatch;
import mx.ine.procprimerinsa.dto.DTOTiempoEjecucionProceso;
import mx.ine.procprimerinsa.dto.db.DTOBitacoraAcceso;
import mx.ine.procprimerinsa.dto.db.DTOBitacoraProcesos;
import mx.ine.procprimerinsa.form.FormAdministradorBatch;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

@Qualifier("mbAdminBatch")
@RequestScoped
public class MBAdministradorBatch implements Serializable {

	private static final long serialVersionUID = -1371498600223118565L;
	private static final Logger logger = Logger.getLogger(MBAdministradorBatch.class);
	private static final String MENSAJE_ALERT = "mensajeAlert";
	private static final String MENSAJE_ALERT_TABLA = "mensajeAlertTabla";
	private static final String EJECUCION_SIMULACRO = "S";
	private static final String EJECUCION_JORNADA = "J";
	private static final String ARCHIVO_BITACORA_ACCESO = "bitacoraAcceso";
	private static final String ARCHIVO_BITACORA_PROCESO = "bitacoraProcesos";
	private static final String ARCHIVO_TIEMPO_EJECUCION = "tiempoEjecucionProceso";
	private static final String[] HEADER_BITACORA_ACCESO = new String[] {
															"Id bitácora acceso",
															"Id sistema",
															"Usuario",
															"Rol usuario",
															"Fecha inicio",
															"Fecha fin",
															"Ip usuario",
															"Ip nodo"};
	private static final String[] HEADER_BITACORA_PROCESO = new String[] {
															"Id proceso electoral",
															"Id detalle proceso",
															"Id participación",
															"Ejecuciones",
															"Reinicios",
															"Id de estatus proceso",
															"Usuario",
															"Fecha",
															"Job execution id",
															"Id bitácora de procesos"};
	private static final String[] HEADER_TIEMPO_EJECUCION = new String[] {"Id detalle proceso",
															"Id participación",
															"Estado",
															"Distrito",
															"Id estatus proceso",
															"Ejecuciones",
															"Reinicios",
															"Ciudadanos",
															"Inicio insaculación",
															"Fin insaculación",
															"Tiempo de insaculación",
															"Inicio del proceso",
															"Fin del proceso",
															"Tiempo del proceso",
															"Inicio archivos",
															"Fin archivos",
															"Tiempo de archivos"};
	
	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdAdminBatch")
	private transient BSDAdmistradorBatchInterface bsdAdminBatch;
	
	@Autowired
	@Qualifier("bsdReinicioProceso")
	private BSDReinicioProcesoInterface bsdReinicioProceso;
	
	@Autowired
	@Qualifier("bsdMapas")
	private BSDMapasInterface bsdMapas;
	
	@Autowired
	@Qualifier("bsdBitacora")
	private transient BSDBitacoraInterface bsdBitacora;
	
	@Autowired
	@Qualifier("asBitacoraProcesos")
	private ASBitacoraProcesosInterface asBitacoraProcesos;
	
	@Autowired
	@Qualifier("asProcesoInsa")
	private ASProcesoInsaInterface asProcesoInsa;
	
	@Autowired
	@Qualifier("asParametros")
	private ASParametrosInterface asParametros;
	
	@Autowired
	@Qualifier("asArchivos")
	private ASArchivosInterface asArchivos;

	private static final AtomicInteger distritosTotales = new AtomicInteger(0);
	private static final AtomicInteger distritosEjecutando = new AtomicInteger(0);
	private static Boolean isPolling = false;
	private FormAdministradorBatch form;

	public void init() {
		try {
			
			form =  new FormAdministradorBatch();
			form.setFechaInicioBitacoraAcceso(new Date());
			
			String menuValido = mbAdmin.verificaElementosMenu(true, false, false);
			
			if (!menuValido.isEmpty()) {
				form.setProcesoValido(false);
				form.setMensaje(menuValido);
				return;
			}
			
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
			
			form.setDetalles(bsdMapas.getDetallesProceso(
										mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
										mbAdmin.getAdminData().getTipoProceso()));

			form.setmEstatusProceso(bsdAdminBatch.obtenerCEstatusProceso(
										mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso()));
			
			form.setIdCorteLN(asProcesoInsa.getCorteLNActivo(
										mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
										mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso()));
			
			if(obtenerTipoEjecucion()) obtenerParametrosEjecucion();
			
			consultaEstatusBatch(false);
			
			form.setProcesoValido(true);
		} catch(Exception e) {
			form.setProcesoValido(false);
			form.setMensaje(Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo"));
			logger.error("ERROR MBAdministradorBatch -init: ", e);
		}
	}
	
	private boolean obtenerTipoEjecucion() {
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaActual = format.parse(format.format(new Date()));
			
			Date fechaProceso = format.parse(asParametros.obtenerParametro(
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
													mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado(),
													0, 
													Constantes.PARAMETRO_FECHA_EJECUCION_PROCESO));
			
			if(DateUtils.isSameDay(fechaProceso, fechaActual)) {
				form.setTipoProceso(EJECUCION_JORNADA);
				return true;
			}
			
			Date fechaInicio = format.parse(asParametros.obtenerParametro(
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
													mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado(),
													0, 
													Constantes.PARAMETRO_INICIO_PERIODO_SIMULACRO));
			
			Date fechaFin = format.parse(asParametros.obtenerParametro(
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
													mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado(),
													0, 
													Constantes.PARAMETRO_FIN_PERIODO_SIMULACRO));

			if(fechaActual.compareTo(fechaInicio)  >= 0 && fechaActual.compareTo(fechaFin) <= 0) {
				form.setTipoProceso(EJECUCION_SIMULACRO);
				return true;
			}
			
			form.setTipoProceso("La fecha actual no se encuentra dentro del periodo válido para simulacros o ejecución del Proceso de Primera Insaculación.");
			
		} catch(Exception e) {
			logger.error("ERROR MBAdministradorBatch -obtenerTipoEjecucion: ", e);
			form.setTipoProceso("Error al obtener el tipo de ejecución");
		}
		
		return false;
	}

	private void obtenerParametrosEjecucion() {
		try {
			
			if(form.getTipoProceso().equals(EJECUCION_JORNADA)) {
				form.setLetraSorteada(asParametros.obtenerParametro(
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
													mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado(),
													0, 
													Constantes.PARAMETRO_LETRA_A_INSACULAR));
				
				form.setMesSorteado(Integer.valueOf(asParametros.obtenerParametro(
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
													mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado(),
													0, 
													Constantes.PARAMETRO_MES_A_INSACULAR)));
			} else if(form.getTipoProceso().equals(EJECUCION_SIMULACRO)) {
				form.setLetraSorteada(asParametros.obtenerParametro(
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
													mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado(),
													0, 
													Constantes.PARAMETRO_LETRA_A_INSACULAR_SIMULACRO));
				
				form.setMesSorteado(Integer.valueOf(asParametros.obtenerParametro(
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
													mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado(),
													0, 
													Constantes.PARAMETRO_MES_A_INSACULAR_SIMULACRO)));
			}
			
			form.setIdCorteLNAActualizar(Integer.valueOf(asParametros.obtenerParametro(
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
													mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado(),
													0,
													Constantes.PARAMETRO_CORTE_LN_A_ACTUALIZAR)));

			form.setValidaYaEsInsaculado(Integer.valueOf(asParametros.obtenerParametro(
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
													mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado(),
													0,
													Constantes.PARAMETRO_VALIDAR_YA_ES_INSACULADO)));
			
			form.setMesesYaSorteados(asParametros.obtenerParametro(
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
													mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado(),
													0,
													Constantes.PARAMETRO_MESES_YA_SORTEADOS));
			
			form.setConsideraExtraordinarias(Integer.valueOf(asParametros.obtenerParametro(
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
													mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado(),
													0,
													Constantes.PARAMETRO_CONSIDERA_EXTRAORDINARIAS)));
		} catch (Exception e) {
			logger.error("ERROR MBAdministradorBatch -obtenerTipoEjecucion: ", e);
			form.setLetraSorteada("Error al obtener los parámetros de ejecución");
			form.setMesSorteado(null);
			form.setIdCorteLNAActualizar(null);
			form.setValidaYaEsInsaculado(null);
			form.setMesesYaSorteados(null);
			form.setConsideraExtraordinarias(null);
		}
	}
	
	public double getPorcentajeAvance() {
		return ((double)(obtieneDistritosTotales() - obtieneDistritosEjecutando())/obtieneDistritosTotales()) * 100;
	}
	
	public void consultaEstatusBatch(boolean isFromPolling) {
		try {
			if (mbAdmin.getAdminData().getIdEstadoSeleccionado() != null
					&& mbAdmin.getAdminData().getIdEstadoSeleccionado() != 0) {
				form.setListaJobs(bsdAdminBatch.obtenerJobsPorEstado(
						mbAdmin.getAdminData().getIdDetalleProcesoSeleccionado(),
						mbAdmin.getAdminData().getIdEstadoSeleccionado(),
						mbAdmin.getAdminData().getProcesoSeleccionado().getCorte()));
				if(form.getListaJobsFiltrada() != null) PrimeFaces.current().executeScript("PF('estatusTable').filter();");
				
				if(isFromPolling && obtieneDistritosEjecutando() == 0) {
					setIsPolling(false);
				}
			} else {
				form.setListaJobs(Collections.emptyList());
				if(form.getListaJobsFiltrada() != null) PrimeFaces.current().executeScript("PF('estatusTable').filter();");
			}
		} catch (Exception e) {
			logger.error("ERROR MBAdministradorBatch -consultaEstatusBatch: ", e);
		}
	}

	public void muestraDetalleJob(Integer idJobExecution) {
		form.setJobSeleccionado(bsdAdminBatch.obtenerJobPorId(idJobExecution));
	}
	
	public void descargaTxtBitacoraAcceso() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			List<DTOBitacoraAcceso> listaBitacoraAcceso = bsdBitacora.obtenerBitacoraAcceso(
					format.parse(format.format(form.getFechaInicioBitacoraAcceso())));
			Utilidades.descargaArchivoCSV(HEADER_BITACORA_ACCESO, 
										listaBitacoraAcceso, 
										ARCHIVO_BITACORA_ACCESO);
		} catch (Exception e) {
			logger.error("ERROR MBAdministracionBatch - descargaTxtBitacoraAcceso: ", e);
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al descargar la bitácora de acceso"));
		}
	}

	public void descargaTxtBitacoraProceso() {
		try {
			List<DTOBitacoraProcesos> listaBitacoraProceso = asBitacoraProcesos.obtenerBitacoraProcesosRegistros(
																form.getDetalles());
			Utilidades.descargaArchivoCSV(HEADER_BITACORA_PROCESO, 
										listaBitacoraProceso, 
										ARCHIVO_BITACORA_PROCESO);
		} catch (Exception e) {
			logger.error("ERROR MBAdministracionBatch - descargaTxtBitacoraProceso: ", e);
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al descargar la bitácora de proceso"));
		}
	}
	
	public void descargaTxtTiempoEjecucion() {
		try {
			List<DTOTiempoEjecucionProceso> listaTiempoEjecucionProceso = bsdAdminBatch.obtenerTiempoEjecucionProceso(
										form.getDetalles(),
										mbAdmin.getAdminData().getProcesoSeleccionado().getCorte());
			Utilidades.descargaArchivoCSV(HEADER_TIEMPO_EJECUCION, 
										listaTiempoEjecucionProceso, 
										ARCHIVO_TIEMPO_EJECUCION);
		} catch (Exception e) {
			logger.error("ERROR MBAdministracionBatch -descargaTxtTiempoEjecucion: ", e);
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al descargar el archivo de tiempo de ejecución"));
		}
	}
	
	private boolean validaEjecucionesEnProceso(FacesContext context, String clientId) {
		if(isDisableToExecute()) {
			context.addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"No se puede iniciar una nueva acción hasta que finalice el proceso concurrente"));
			return false;
		}
		return true;
	}
	
	private boolean validaEjecucion(FacesContext context) {
		
		if(!validaEjecucionesEnProceso(context, MENSAJE_ALERT_TABLA)) return false;
		
		if(form.getIdCorteLN() == null
			|| (!form.getTipoProceso().equals(EJECUCION_SIMULACRO) 
				&& !form.getTipoProceso().equals(EJECUCION_JORNADA))
			|| (form.getLetraSorteada() == null 
				|| form.getLetraSorteada().isBlank() 
				|| form.getLetraSorteada().length() != 1)
			|| form.getMesSorteado() == null
			|| form.getIdCorteLNAActualizar() == null
			|| (!form.getIdCorteLNAActualizar().equals(0)
				&& form.getIdCorteLN().equals(form.getIdCorteLNAActualizar()))
			|| form.getValidaYaEsInsaculado() == null
			|| form.getConsideraExtraordinarias() == null) {
			context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"Los parámetros no se encuentran especificados o son inválidos."));
			return false;
		}
		
		if(form.getTipoEjecucion() != Constantes.TIPO_EJECUCION_PROCESO_PRIMERA_INSA
			&& form.getTipoEjecucion() != Constantes.TIPO_EJECUCION_GENERACION_ARCHIVOS
					&& form.getTipoEjecucion() != Constantes.TIPO_EJECUCION_PROCESO_PRIMERA_INSA_COMPLETO
			&& form.getTipoEjecucion() != Constantes.TIPO_EJECUCION_REINICIO) {
			context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"El tipo de ejecución seleccionado para la carga no es válido"));
			return false;
		}
		
		return true;
	}
	
	public void ejecutaCargaPool(Integer tipoEjecucion) {
		FacesContext context = FacesContext.getCurrentInstance();
		form.setTipoEjecucion(tipoEjecucion);
		
		try {
			
			if(!validaEjecucion(context)) {
				return;
			}
			
			if(form.getListaJobsFiltrada() == null) {
				form.setQueueDistritosEjecutando(
						new ConcurrentLinkedQueue<>(form.getListaJobs()
													.stream()
													.collect(Collectors.toList())));
			} else if(form.getListaJobsFiltrada().isEmpty()){
				context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
						"No se encontraron distritos a ejecutar con los parámetros seleccionados"));
				return;
			} else {
				form.setQueueDistritosEjecutando(
						new ConcurrentLinkedQueue<>(form.getListaJobsFiltrada()
													.stream()
													.collect(Collectors.toList())));
			}
			
			if(form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_PROCESO_PRIMERA_INSA
				|| form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_PROCESO_PRIMERA_INSA_COMPLETO) {
				String distritosNoReiniciados = form.getQueueDistritosEjecutando()
										.stream()
										.filter(d -> d.getEstatus() != Constantes.ESTATUS_PREPARANDO_DG_F)
										.map(d -> d.getDescripcionParticipacion())
										.collect(Collectors.joining(", "));
				
				if(!distritosNoReiniciados.isEmpty()) {
					String mensajeError = distritosNoReiniciados.contains(",") ? 
							"Los distritos de "  + distritosNoReiniciados  + " tienen que ser reiniciados previamente"
							: distritosNoReiniciados + " no ha sido reiniciado";
							
					context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
								mensajeError));
					
					return;
				}
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
			logger.error("ERROR MBAdministradorBatch -ejecutaCargaPool: ", e);
		}
	}
	
	public Boolean ejecucionCargaPoolDistrito() {
    	try {
			
    		DTOAdministradorBatch distrito = form.getQueueDistritosEjecutando().poll();
    		
    		if((form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_PROCESO_PRIMERA_INSA
    			|| form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_PROCESO_PRIMERA_INSA_COMPLETO)
    			&& !ejecutaProceso(distrito)) {
    			decrementDistritosEjecutando();
    			return false;
    		}
    		
    		if(form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_GENERACION_ARCHIVOS
    			|| form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_PROCESO_PRIMERA_INSA_COMPLETO) {
    			generaArchivos(distrito);
    			decrementDistritosEjecutando();
    			return true;
    		}
    		    		
			if(form.getTipoEjecucion() == Constantes.TIPO_EJECUCION_REINICIO) {
				reiniciarProceso(distrito, false);
				decrementDistritosEjecutando();
				return true;
			}
			
		} catch(Exception e) {
			logger.error("ERROR MBAdministradorBatch -ejecutaCargaDistrito: ", e);
			decrementDistritosEjecutando();
			return false;
		}
		
    	decrementDistritosEjecutando();
		return true;
    }

	private boolean ejecutaProceso(DTOAdministradorBatch distrito) throws Exception {	
		// 6 - llaves válidas
		Integer estatus = bsdReinicioProceso.actualizaEstatus(
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
											distrito.getIdParticipacion(),
											Constantes.ESTATUS_PREPARANDO_DG_F);
		
		// 7 ejecutando
		estatus = bsdReinicioProceso.actualizaEstatus(
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
											distrito.getIdParticipacion(),
											estatus);
		
		if (!asProcesoInsa.ejecutaCalculoInsaculados(
					form.getIdCorteLN(),
					mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
					mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
					mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado(),
					distrito.getIdParticipacion(), 
					distrito.getIdGeograficoParticipacion(), 
					form.getMesSorteado(), 
					form.getLetraSorteada(),
					form.getIdCorteLNAActualizar(),
					form.getValidaYaEsInsaculado(),
					form.getMesesYaSorteados(),
					form.getConsideraExtraordinarias(),
					asProcesoInsa.obtenerIdReinicio(
							mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
							distrito.getIdParticipacion()))) {
			return false;
		}
		
		// 8 ejecutando fin
		estatus = bsdReinicioProceso.actualizaEstatus(
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
											distrito.getIdParticipacion(),
											estatus);
					
		// 9 - despliegue
		estatus = bsdReinicioProceso.actualizaEstatus(
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
											distrito.getIdParticipacion(),
											estatus);
		
		// 10
		estatus = bsdReinicioProceso.actualizaEstatus(
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
											distrito.getIdParticipacion(),
											estatus);
		
		// 11 despliegue fin
		bsdReinicioProceso.actualizaEstatus(
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
											distrito.getIdParticipacion(),
											estatus);
		
		return true;
	}
	
	private void generaArchivos(DTOAdministradorBatch distrito) throws Exception {		
		asArchivos.ejecutaGeneracionArchivos(
				mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
				mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
				mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado(),
				distrito.getIdGeograficoParticipacion(),
				distrito.getIdParticipacion(),
				mbAdmin.getAdminData().getProcesoSeleccionado().getNombreProceso(),
				mbAdmin.getAdminData().getProcesoSeleccionado().getDescripcionDetalle(),
				mbAdmin.getAdminData().getEstadoSeleccionado().getNombreEstado(),
				distrito.getDescripcionParticipacion().split("-")[1].trim(),
				form.getTipoProceso(),
				form.getLetraSorteada());
	}
	
	private void reiniciarProceso(DTOAdministradorBatch distrito, boolean isGeneral) throws Exception {
		Integer idDetalle = isGeneral ? distrito.getIdDetalleProceso() 
								: mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso();
		
		Integer estatusReinicio = bsdReinicioProceso.actualizaEstatus(
										idDetalle, 
										distrito.getIdParticipacion(),
										Constantes.ESTATUS_PROCESO_FINALIZADO);
		
		if (bsdReinicioProceso.ejecutaReinicio(
										mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
										idDetalle,
										distrito.getIdParticipacion())) {
			bsdReinicioProceso.actualizaEstatus(
										idDetalle,
										distrito.getIdParticipacion(),
										estatusReinicio);
		}
	}
	
	public void eliminaDatosInsaculados(DTOAdministradorBatch distrito) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			if(!validaEjecucionesEnProceso(context, MENSAJE_ALERT)) return;
						
			if (bsdReinicioProceso.eliminaDatosInsaculados(
								mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
								distrito.getIdParticipacion())) {
				context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, " ", "La partición de DI se truncó correctamente"));
			} else {
				context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al truncar la partición de DI"));
			}
			
		} catch(Exception e) {
			logger.error("ERROR MBAdministracionBatch -eliminaDatosInsaculados: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al truncar la partición de DI"));
		}
		
		consultaEstatusBatch(false);
	}
	
	public void eliminaResultadosInsa(DTOAdministradorBatch distrito) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			if(!validaEjecucionesEnProceso(context, MENSAJE_ALERT)) return;
			
			if (bsdReinicioProceso.eliminaResultadosInsa(
								mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
								distrito.getIdParticipacion())){
				context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, " ", "La información de RESULTADOS_INSA1 se eliminó correctamente"));
			} else {
				context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al eliminar la información de RESULTADOS_INSA1"));
			}
			
		} catch(Exception e) {
			logger.error("ERROR MBAdministracionBatch -eliminaResultadosInsa: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al eliminar la información de RESULTADOS_INSA1"));
		}
		
		consultaEstatusBatch(false);
	}
	
	public void setJobExecutionIdDefaultPorParticipacion(DTOAdministradorBatch distrito) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			if(!validaEjecucionesEnProceso(context, MENSAJE_ALERT)) return;
			
			if (bsdReinicioProceso.setJobExecutionIdDefaultPorParticipacion(
								mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
								distrito.getIdParticipacion())){
				context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, " ", "El JobExecutionId se actualizó correctamente"));
			} else {
				context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al actualizar el JobExecutionId"));
			}
			
		} catch(Exception e) {
			logger.error("ERROR MBAdministracionBatch -setJobExecutionIdDefaultPorParticipacion: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al actualizar el JobExecutionId"));
		}
		
		consultaEstatusBatch(false);
	}
	
	public void eliminaArchivosPorParticipacion(DTOAdministradorBatch distrito) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			if(!validaEjecucionesEnProceso(context, MENSAJE_ALERT)) return;
			
			if (bsdReinicioProceso.eliminaRegistroArchivos(
								mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
								mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
								distrito.getIdParticipacion())){
				context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, " ", "Los archivos se eliminaron correctamente"));
			} else {
				context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al eliminar los archivos"));
			}
			
		} catch(Exception e) {
			logger.error("ERROR MBAdministracionBatch -eliminaArchivosPorParticipacion: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al eliminar los archivos"));
		}
		
		consultaEstatusBatch(false);
	}
	
	public void reiniciarEstatusProcesoPorParticipacion(DTOAdministradorBatch distrito) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			if(!validaEjecucionesEnProceso(context, MENSAJE_ALERT)) return;
			
			Integer estatusReinicio = bsdReinicioProceso.actualizaEstatus(
														mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
														distrito.getIdParticipacion(),
														Constantes.ESTATUS_PROCESO_FINALIZADO);
			bsdReinicioProceso.actualizaEstatus(
										mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
										distrito.getIdParticipacion(),
										estatusReinicio);
			context.addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, " ", "El estatus del proceso se reinició correctamente"));
		} catch(Exception e) {
			logger.error("ERROR MBAdministracionBatch -reiniciarEstatusProcesoPorParticipacion: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al reiniciar el estatus del proceso"));
		}
		
		consultaEstatusBatch(false);
	}
	
	public void actualizarEstatusProcesoPorParticipacion(DTOAdministradorBatch distrito) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(!validaEjecucionesEnProceso(context, MENSAJE_ALERT_TABLA)) return;
		
		if(distrito.getNuevoEstatus() == null
				|| distrito.getNuevoEstatus() == 0) {
			context.addMessage(MENSAJE_ALERT_TABLA, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Se debe de seleccionar un estatus"));
			return;
		}
		
		if(distrito.getNuevoEstatus().equals(distrito.getEstatus())) {
			context.addMessage(MENSAJE_ALERT_TABLA, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "El estatus a actualizar es el mismo que el estatus actual"));
			return;
		}
		
		if(!form.getmEstatusProceso().containsKey(distrito.getNuevoEstatus())) {
			context.addMessage(MENSAJE_ALERT_TABLA, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "El estatus seleccionado no existe dentro del catálogo"));
			return;
		}
		
		try {
			asProcesoInsa.actualizaEstatus(
					mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
					distrito.getIdParticipacion(),
					distrito.getNuevoEstatus() - 1,
					Constantes.DEFAULT_JOB_EXECUTION_ID);
			context.addMessage(MENSAJE_ALERT_TABLA, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, " ", "El estatus del proceso se actualizó correctamente"));			
		} catch(Exception e) {
			logger.error("ERROR MBAdministradorBatch -actualizarEstatusProcesoPorParticipacion: ", e);
			context.addMessage(MENSAJE_ALERT_TABLA, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al actualizar el estatus del proceso"));
		}
		consultaEstatusBatch(false);
	}
	
	public void reinicioGeneral() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			if(!validaEjecucionesEnProceso(context, MENSAJE_ALERT)) return;
			
			List<DTOAdministradorBatch> procesosEjecutados = bsdAdminBatch.obtenerProcesosEjecutados(
					form.getDetalles(),
					mbAdmin.getAdminData().getProcesoSeleccionado().getCorte());

			for (DTOAdministradorBatch dtoAdminBatch : procesosEjecutados) {
				reiniciarProceso(dtoAdminBatch, true);
			}
			
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, " ", "El proceso de reinicio general finalizó"));			
		} catch (Exception e) {
			logger.error("ERROR MBAdministradorBatch -reinicioGeneral: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al realizar el reinicio general "));
		}	
		consultaEstatusBatch(false);
	}
	
	public void actualizaEjecuciones() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(!validaEjecucionesEnProceso(context, MENSAJE_ALERT)) return;
		
		if(bsdAdminBatch.actualizaEjecuciones(form.getDetalles())) {
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
							"Éxito al actualizar el número de ejecuciones y reinicios"));
		} else {
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al actualizar el número de ejecuciones y reinicios"));
		}
	}
	
	public void reiniciaBatch() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(!validaEjecucionesEnProceso(context, MENSAJE_ALERT)) return;
		
		if(bsdAdminBatch.reiniciaBatch()) {
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
							"Éxito al reiniciar las tablas de batch"));
		} else {
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al reiniciar las tablas de batch"));
		}
	}
	
	public void reiniciaBitacoraProceso() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(!validaEjecucionesEnProceso(context, MENSAJE_ALERT)) return;
		
		if(bsdAdminBatch.reiniciaBitacoraProceso(form.getDetalles())) {
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
							"Éxito al reiniciar la bitácora de proceso"));
		} else {
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al reiniciar la bitácora de proceso"));
		}
	}
	
	public void reiniciaEstatusDatosPersonales() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(!validaEjecucionesEnProceso(context, MENSAJE_ALERT)) return;
		
		if(bsdAdminBatch.reiniciaEstatusDatosPersonales(
									form.getDetalles(), 
									mbAdmin.getAdminData().getUsuario().getUsername())) {
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
							"Éxito al reiniciar el estatus de los datos personales"));
		} else {
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al reiniciar el estatus de los datos personales"));
		}
	}
	
	public boolean isDisableToExecute() {
		return obtieneDistritosEjecutando() > 0;
	}

	public FormAdministradorBatch getForm() {
		return form;
	}
	
	private static synchronized void setDistritosTotales(int distritosTotales) {
		MBAdministradorBatch.distritosTotales.set(distritosTotales);
	}
	
	public static synchronized int obtieneDistritosTotales() {
		return distritosTotales.get();
	}
	
	private static synchronized void setDistritosEjecutando(int distritosEjecutando) {
		MBAdministradorBatch.distritosEjecutando.set(distritosEjecutando);
	}
	
	private static synchronized int decrementDistritosEjecutando() {
		return distritosEjecutando.decrementAndGet();
	}

	public static synchronized int obtieneDistritosEjecutando() {
		return distritosEjecutando.get();
	}
	
	private static synchronized void setIsPolling(Boolean isPolling) {
		MBAdministradorBatch.isPolling = isPolling;
	}

	public static synchronized Boolean getIspolling() {
		return isPolling;
	}
	
}
