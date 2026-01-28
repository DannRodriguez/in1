package mx.ine.procprimerinsa.mb;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import mx.ine.procprimerinsa.bsd.BSDArchivosInterface;
import mx.ine.procprimerinsa.bsd.BSDProcesoInsaInterface;
import mx.ine.procprimerinsa.dto.db.DTOArchivos;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier("mbReportes")
@RequestScoped
public class MBReportes implements Serializable {

	private static final long serialVersionUID = -7566171556392817909L;
	private static Logger logger = Logger.getLogger(MBReportes.class);
	
	private static final String MENSAJE_ALERT = "mensajeAlert";
	private static final String APPLICATION_TXT = "application/txt";
	private static final String APPLICATION_PDF = "application/pdf";
	private static final String ARCHIVO_NO_DISPONIBLE = "El archivo no se encuentra disponible para su descarga";

	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdArchivos")
	private BSDArchivosInterface bsdArchivos;

	@Autowired
    @Qualifier("bsdProcesoInsa")
    private BSDProcesoInsaInterface bsdProcesoInsa;

	private Map<Integer, DTOCTipoVoto> mTipoVoto;
	private Map<String, DTOArchivos> archivos;
	private Integer idParticipacion;
	private boolean procesoValido;
	private String mensaje;
	
	public void init() {
		try {
			
			mbAdmin.getAdminData().setIdModulo(Constantes.ID_MODULO_REPORTES);
			
			String menuValido = mbAdmin.verificaElementosMenu(true, true, true);
			
			if (!menuValido.isEmpty()) {
				procesoValido = false;
				mensaje = menuValido;
				return;
			}
			
			idParticipacion = mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion();
			
			mTipoVoto = bsdProcesoInsa.obtieneTiposVotoPorParticipacion(mbAdmin.getAdminData().getIdDetalleProcesoSeleccionado(),
														idParticipacion);
			
			if(!ejecucionValidaProceso()) {
				procesoValido = false;
				return;
			}
			
			obtenerListadoArchivos();

			if(archivos.isEmpty()) {
				mensaje = "El Proceso de Primera Insaculación ha sido ejecutado correctamente. Sin embargo, no han sido generados los reportes";
				procesoValido = false;
				return;
			}
			
			procesoValido = true;
		    
		} catch (Exception e) {
			procesoValido = false;
			mensaje = Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo");
			logger.error("ERROR MBReportes -init: ", e);
		}
		
	}
	
	public boolean ejecucionValidaProceso() {
		
		try {
			
			Integer status = bsdProcesoInsa.obtenerIdEstatusActual(
												mbAdmin.getAdminData().getIdDetalleProcesoSeleccionado(), 
												idParticipacion);
			
			if(status.intValue() <= Constantes.ESTATUS_PREPARANDO_DG_F) {
				mensaje = Utilidades.mensajeProperties("04E_mensaje_ejecucionNoIniciada");
				return false;
			}
			
			if (status.intValue() == Constantes.ESTATUS_PERMISOS_VALIDOS
					|| status.intValue() == Constantes.ESTATUS_EJECUTANDO_PROCESO_I
					|| status.intValue() == Constantes.ESTATUS_EJECUTANDO_PROCESO_F
					|| status.intValue() == Constantes.ESTATUS_GENERANDO_ARCHIVOS
					|| status.intValue() == Constantes.ESTATUS_DESPLIEGUE_RESULTADOS) {
				mensaje = Utilidades.mensajeProperties("04E_mensaje_ejecucionOtroEquipo");
				return false;
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBReportes -ejecucionValidaProceso: ", e);
			mensaje = "Error al efectuar la validación del estatus del Proceso de Primera Insaculación";
			return false;
		}
		
		return true;
	}
	
	public void obtenerListadoArchivos() {

		try {
			DTOArchivos archivo = new DTOArchivos();
			archivo.setIdProcesoElectoral(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral());
			archivo.setIdDetalleProceso(mbAdmin.getAdminData().getIdDetalleProcesoSeleccionado());
			archivo.setIdParticipacion(idParticipacion);
			
			archivos = bsdArchivos.obtenerListadoArchivosProceso(archivo);
			
		} catch (Exception e) {
			logger.error("ERROR MBReportes -obtenerListadoArchivos: ", e);
		}
	}

	public StreamedContent descargarArchivo(int tipoVoto, int tipo) {
		StreamedContent archivoGDescarga = null;

		try {
			String key = tipoVoto + "-" + tipo;
			
			if(!archivos.containsKey(key)) {
				logger.error("ERROR MBReportes -descargarArchivo(no existe el archivo en BD): " + key + ". Archivos: " + archivos);
				FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", ARCHIVO_NO_DISPONIBLE));
				return null;
			}
			
			String rutaArchivo = archivos.get(key).getRutaArchivo();
			String context = rutaArchivo.contains(".txt") ? APPLICATION_TXT : APPLICATION_PDF;
			File file = new File(Constantes.RUTA_LOCAL_FS + rutaArchivo);
			
			if (!file.exists()) {
				logger.error("ERROR MBReportes -descargarArchivo(no se puede acceder al archivo en el servidor): " + rutaArchivo);
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
			logger.error("ERROR MBReportes -descargarArchivo: ", e);
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
											new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", ARCHIVO_NO_DISPONIBLE));
		}
		
		return archivoGDescarga;
	}
	
	public boolean isProcesoValido() {
		return procesoValido;
	}

	public String getMensaje() {
		return mensaje;
	}
	
	public Map<Integer, DTOCTipoVoto> obtieneTiposVoto() {
		return mTipoVoto;
	}

}