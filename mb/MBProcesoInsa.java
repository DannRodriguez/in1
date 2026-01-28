package mx.ine.procprimerinsa.mb;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.procprimerinsa.as.ASWSRegistraBitacoraInterface;
import mx.ine.procprimerinsa.bsd.BSDArchivosInterface;
import mx.ine.procprimerinsa.bsd.BSDProcesoInsaInterface;
import mx.ine.procprimerinsa.dto.db.DTOArchivos;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.form.FormProcesoInsa;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

@Qualifier("mbProcesoInsa")
@RequestScoped
public class MBProcesoInsa implements Serializable {

	private static final long serialVersionUID = 8607928336082989658L;
	private static final Logger logger = Logger.getLogger(MBProcesoInsa.class);
	
	private static final String MENSAJE_ALERT = "mensajeAlert";
	private static final String APPLICATION_TXT = "application/txt";
	private static final String APPLICATION_PDF = "application/pdf";
	private static final String ARCHIVO_NO_DISPONIBLE = "El archivo no se encuentra disponible para su descarga";
	private static final String ERROR_PREPARACION_DATOS = "Ocurrió un error durante la ejecución del proceso de preparación.";
	private static final String ERROR_GENERACION_ARCHIVOS = "Los archivos no se generaron correctamente.";
	private static final String ERROR_FINALIZA_PROCESO = "Ocurrió un error al realizar el almacenamiento de resultados del proceso.";
	
	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdProcesoInsa")
	private BSDProcesoInsaInterface bsdProcesoInsa;
	
	@Autowired
	@Qualifier("bsdArchivos")
	private BSDArchivosInterface bsdArchivos;
	
	@Autowired
	@Qualifier("wsRegistraBitacora")
	private transient ASWSRegistraBitacoraInterface wsRegistraBitacora;
	
	private FormProcesoInsa formProceso;
	
	public void iniciaPantalla() {
		
		formProceso = new FormProcesoInsa();

		try {
			
			mbAdmin.getAdminData().setIdModulo(Constantes.ID_MODULO_PROCESO_INSACULACION);
			
			String menuValido = mbAdmin.verificaElementosMenu(true, true, true);
			
			if (!menuValido.isEmpty()) {
				formProceso.setProcesoValido(false);
				formProceso.setMensaje(menuValido);
				return;
			}
			
			wsRegistraBitacora.solicitaRegistro(mbAdmin.getAdminData().getUsuario(), 
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
											Constantes.SERVICIO_BITACORA_PROCESO,
											Constantes.EJECUTA);
			
			formProceso.setIdProceso(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral());
			formProceso.setIdDetalle(mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso());
			formProceso.setIdParticipacion(mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion());
			formProceso.setIdEstado(mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado());
			formProceso.setIdDistrito(mbAdmin.getAdminData().getIdParticipacionSeleccionada());
			formProceso.setNombreEstado(mbAdmin.getAdminData().getEstadoSeleccionado().getNombreEstado());
			formProceso.setNombreParticipacion(mbAdmin.getAdminData().getParticipacionSeleccionada().getNombre());
			
			if(validaProcesoEjecutando()) {
				formProceso.setProcesoValido(false);
				formProceso.setMensaje(Utilidades.mensajeProperties("04E_mensaje_ejecucionOtroEquipo"));
				return;
			}
			
			formProceso.setStatusActual(bsdProcesoInsa.obtenerIdEstatusActual(formProceso.getIdDetalle(),
																			formProceso.getIdParticipacion()));

			formProceso.setIdCorteLN(bsdProcesoInsa.getCorteLNActivo(formProceso.getIdProceso(),
																	formProceso.getIdDetalle()));	
			if (formProceso.getIdCorteLN() == null 
				|| formProceso.getStatusActual() < Constantes.ESTATUS_PREPARANDO_DG_F) {
				formProceso.setProcesoValido(false);
				formProceso.setMensaje(Utilidades.mensajeProperties("04E_mensaje_listaNominalNoDisponible"));
				return;
			}
			
			formProceso.setmTipoVoto(bsdProcesoInsa.obtieneTiposVotoPorParticipacion(formProceso.getIdDetalle(),
																	formProceso.getIdParticipacion()));
			
			if(formProceso.getmTipoVoto().isEmpty()) {
				formProceso.setProcesoValido(false);
				formProceso.setMensaje("No se obtuvieron tipos de voto para realizar la insaculación.");
				return;
			}

			validaParametros();
			validaProcesoFinalizado();

			formProceso.setProcesoValido(!formProceso.isAdvertenciaParametros());

			if (formProceso.getStatusActual().intValue() >= Constantes.ESTATUS_GUARDANDO_BD_I) {
				formProceso.setPasoActual(Constantes.PASO_PROCESO_FINALIZADO);
				formProceso.setNavegacionPermitida(false);
				return;
			}

			formProceso.setPasoActual(Constantes.PASO_PARAMETROS);
			formProceso.setNavegacionPermitida(true);
			
		} catch (Exception e) {
			formProceso.setProcesoValido(false);
			formProceso.setMensaje(Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo"));
			logger.error("ERROR MBProcesoInsa -iniciaPantalla: ", e);
		}
	}
	
	public boolean validaProcesoEjecutando() {
		try {
			Integer status = bsdProcesoInsa.obtenerIdEstatusActual(formProceso.getIdDetalle(),
																	formProceso.getIdParticipacion());

			if (status.intValue() == Constantes.ESTATUS_PERMISOS_VALIDOS
					|| status.intValue() == Constantes.ESTATUS_EJECUTANDO_PROCESO_I
					|| status.intValue() == Constantes.ESTATUS_EJECUTANDO_PROCESO_F
					|| status.intValue() == Constantes.ESTATUS_GENERANDO_ARCHIVOS
					|| status.intValue() == Constantes.ESTATUS_DESPLIEGUE_RESULTADOS)
				return true;
				
		} catch (Exception e) {
			logger.error("ERROR MBProcesoInsa - validaProcesoEjecutando: ", e);
		}
		
		return false;
	}

	public void validaParametros() {
		
		try {
			if (bsdProcesoInsa.obtenerParametrosInsaculacion(formProceso)) {
				formProceso.setAdvertenciaParametros(false);
				return;
			}
		} catch (Exception e) {
			formProceso.setMensaje("Ocurrió un error al obtener los parámetros.");
			logger.error("ERROR MBProcesoInsa -inicializaParametros(): ", e);
		}
		
		formProceso.setAdvertenciaParametros(true);
		formProceso.setNavegacionPermitida(false);
	}
	
	public void validaProcesoFinalizado() {

		try {
			DTOArchivos archivo = new DTOArchivos();
			archivo.setIdProcesoElectoral(formProceso.getIdProceso());
			archivo.setIdDetalleProceso(formProceso.getIdDetalle());
			archivo.setIdParticipacion(formProceso.getIdParticipacion());
			
			formProceso.setArchivos(bsdArchivos.obtenerListadoArchivosProceso(archivo));
			
			formProceso.setArchivosGenerados(formProceso.getArchivos() != null 
											&& !formProceso.getArchivos().isEmpty());
			
		} catch (Exception e) {
			logger.error("ERROR MBProcesoInsa -validaProcesoFinalizado: ", e);
		}
	}

	public String flujoProceso(FlowEvent event) {
		
		// Se valida que no se pueda acceder al paso de resultados, si el proceso no se
		// ha ejecutado completamente
		if (event.getNewStep().equals(Constantes.PASO_EJECUCION) 
			&& !formProceso.isNavegacionPermitida()) {
			return event.getOldStep();
		}
		
		else if(event.getNewStep().equals(Constantes.PASO_VALIDACION)) {
			formProceso.setNavegacionPermitida(false);
		}
		
		// Si el paso a seguir es la ejecución, se deshabilita la navegación y se cambia
		// el contenido del botón de navegación
		else if (event.getNewStep().equals(Constantes.PASO_EJECUCION)) {
			formProceso.setNavegacionPermitida(false);
			formProceso.setMensajeNavegacion("Procesar");
			if (formProceso.getStatusActual().equals(Constantes.ESTATUS_PERMISOS_VALIDOS)
					&& !formProceso.isAdvertenciaParametros()) {
				formProceso.setPermitirEjecucion(true);
			}
		}
		
		else if (event.getNewStep().equals(Constantes.PASO_RESULTADOS)) {
			try {
				// Se actualiza el estatus a 9 - COMIENZA DESPLIEGUE
				formProceso.setStatusActual(bsdProcesoInsa.actualizaEstatus(
						formProceso.getIdDetalle(), 
						formProceso.getIdParticipacion(),
						formProceso.getStatusActual()));
				
				// Se actualiza el status a 10 - EN DESUSO
				formProceso.setStatusActual(bsdProcesoInsa.actualizaEstatus(
						formProceso.getIdDetalle(), 
						formProceso.getIdParticipacion(),
						formProceso.getStatusActual()));
			} catch(Exception e) {
				logger.error("ERROR MBProcesoInsa -flujoProceso: ", e);
			}
		}

		return event.getNewStep();
	}
	
	public void validarLlaves() {
		if (bsdProcesoInsa.validarLlaves(formProceso)) {
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
											new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
													formProceso.getMensaje()));
		}
		
	}

	public void ejecutarProceso() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			if (bsdProcesoInsa.ejecutarProceso(formProceso)) {
				formProceso.setDetenDespliegue(false);
			} else {
				context.addMessage(MENSAJE_ALERT, 
							new FacesMessage(FacesMessage.SEVERITY_FATAL, " ", ERROR_PREPARACION_DATOS));
			}
		} catch (Exception e) {
			logger.error("ERROR MBProcesoInsa -ejecutarProceso: ", e);
			context.addMessage(MENSAJE_ALERT, 
							new FacesMessage(FacesMessage.SEVERITY_FATAL, " ", ERROR_PREPARACION_DATOS));
		}
	}
	
	public void actualizaMalla() {
		if (formProceso.isNavegacionPermitida())
			PrimeFaces.current().executeScript("PF('progressPreparando').unblock();");
	}
	
	public void ejecutaDespliegueResultados() {
		formProceso.setNavegacionPermitida(false);

		try {
			bsdProcesoInsa.actualizarResultadosInsaculacion(formProceso);
		} catch (Exception e) {
			logger.error("ERROR MBProcesoInsa -ejecutaDespliegueResultados: ", e);
		}
	}
	
	public void finalizaDespliegue() {
		FacesContext context = FacesContext.getCurrentInstance();
		formProceso.setVentanaExitoFinal(false);

		if(!formProceso.getStatusActual().equals(Constantes.ESTATUS_DESPLIEGUE_RESULTADOS)) {
			return;
		}
		
		try {
			if (bsdProcesoInsa.finalizaDespliegue(formProceso)) {
				context.addMessage(MENSAJE_ALERT, 
								new FacesMessage(FacesMessage.SEVERITY_INFO, " ", "El proceso de almacenamiento de resultados, se ejecutó correctamente."));
			} else {
				context.addMessage(MENSAJE_ALERT, 
								new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", ERROR_FINALIZA_PROCESO));
			}

		} catch (Exception e) {
			logger.error("ERROR MBProcesoInsa -finalizaDespliegue: ", e);
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_FATAL, " ", ERROR_FINALIZA_PROCESO));
		}
	}

	public void iniciaDespliegue() {
		formProceso.setVentanaExito(false);
	}

	public void generarArchivos() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			Integer status = bsdProcesoInsa.obtenerIdEstatusActual(formProceso.getIdDetalle(), 
																formProceso.getIdParticipacion());
			
			if (status.equals(Constantes.ESTATUS_GUARDANDO_BD_I)
					&& bsdProcesoInsa.generaListadosBatch(formProceso,
											mbAdmin.getAdminData().getProcesoSeleccionado().getNombreProceso(),
											mbAdmin.getAdminData().getProcesoSeleccionado().getDescripcionDetalle())) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", "Los archivos se generaron con éxito."));
			} else {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", ERROR_GENERACION_ARCHIVOS));
			}
		} catch (Exception e) {
			logger.error("ERROR MBProcesoInsa -generarArchivos: ", e);
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", ERROR_GENERACION_ARCHIVOS));
		}

		validaProcesoFinalizado();
	}
	
	public StreamedContent descargarArchivo(int tipoVoto, int tipo) {
		StreamedContent archivoGDescarga = null;

		try {
			String key = tipoVoto + "-" + tipo;
			
			if(!formProceso.getArchivos().containsKey(key)) {
				logger.error("ERROR MBProcesoInsa -descargarArchivo(no existe el archivo en BD): " + key + ". Archivos: " + formProceso.getArchivos());
				FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", ARCHIVO_NO_DISPONIBLE));
				return null;
			}
			
			String rutaArchivo = formProceso.getArchivos().get(key).getRutaArchivo();
			String context = rutaArchivo.contains(".txt") ? APPLICATION_TXT : APPLICATION_PDF;
			File file = new File(Constantes.RUTA_LOCAL_FS + rutaArchivo);
			
			if (!file.exists()) {
				logger.error("ERROR MBProcesoInsa -descargarArchivo(no se puede acceder al archivo en el servidor): " + rutaArchivo);
				FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", ARCHIVO_NO_DISPONIBLE));
				return null;
			}
			
			InputStream targetStream = FileUtils.openInputStream(file);
			archivoGDescarga = DefaultStreamedContent.builder()
													.contentType(context)
													.name(file.getName())
													.stream(() -> targetStream)
													.build();

		} catch (Exception e) {
			logger.error("ERROR MBProcesoInsa -descargarArchivo: ", e);
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
											new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", ARCHIVO_NO_DISPONIBLE));
		}
		
		return archivoGDescarga;
	}
	
	public FormProcesoInsa getFormProceso() {
		return formProceso;
	}
	
	public Map<Integer, DTOCTipoVoto> obtieneTiposVoto(){
		return formProceso.getmTipoVoto();
	}
	
}
