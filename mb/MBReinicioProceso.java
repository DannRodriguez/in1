package mx.ine.procprimerinsa.mb;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.procprimerinsa.as.ASWSRegistraBitacoraInterface;
import mx.ine.procprimerinsa.bsd.BSDReinicioProcesoInterface;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.form.FormReinicio;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

@Qualifier("mbReinicioProceso")
@RequestScoped
public class MBReinicioProceso implements Serializable {

	private static final long serialVersionUID = -6941396116314261690L;
	private static final Logger logger = Logger.getLogger(MBReinicioProceso.class);
	private static final String MENSAJE_ALERT = "mensajeAlert";
	private static final String EXITO = "El proceso de reinicio: ";
	private static final String EXITO_FIN = " se realizó con éxito.";
	private static final String ERROR = "Error al reiniciar ";
	
	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdReinicioProceso")
	private BSDReinicioProcesoInterface bsdReinicioProceso;
	
	@Autowired
	@Qualifier("wsRegistraBitacora")
	private transient ASWSRegistraBitacoraInterface wsRegistraBitacora;
	
	private FormReinicio formReinicio;

	public void iniciaPantalla() {
		formReinicio = new FormReinicio();

		try {
			
			mbAdmin.getAdminData().setIdModulo(Constantes.ID_MODULO_REINICIO_PROCESO_INSACULACION);
			
			String menuValido = mbAdmin.verificaElementosMenu(true, true, false);
			
			if (!menuValido.isEmpty()) {
				formReinicio.setProcesoValido(false);
				formReinicio.setMensaje(menuValido);
				return;
			} 
			
			wsRegistraBitacora.solicitaRegistro(mbAdmin.getAdminData().getUsuario(), 
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
											Constantes.SERVICIO_BITACORA_REINICIO,
											Constantes.EJECUTA);
			
			if(!mbAdmin.validaModuloAbierto(Constantes.ID_MODULO_REINICIO_PROCESO_INSACULACION, 
										Constantes.MODULO_ACCION_CAPTURA)) {
				formReinicio.setProcesoValido(false);
				formReinicio.setMensaje(Utilidades.mensajeProperties("validacion_mensajes_generales_modulo_cerrado"));
				return;
			}
			
			formReinicio.setIdProcesoElectoral(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral());
			formReinicio.setIdDetalleProcesoElectoral(mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso());
			formReinicio.setNombreProcesoElectoral(mbAdmin.getAdminData().getProcesoSeleccionado().getNombreProceso());
			formReinicio.setIdEstado(mbAdmin.getAdminData().getIdEstadoSeleccionado());
			formReinicio.setNombreEstado(mbAdmin.getAdminData().getEstadoSeleccionado().getNombreEstado());
			formReinicio.setNivelParticipacion(mbAdmin.getAdminData().getParticipacionSeleccionada() != null
											&& mbAdmin.getAdminData().getParticipacionSeleccionada().getId() != null
											&& mbAdmin.getAdminData().getParticipacionSeleccionada().getId() != 0);
					
			formReinicio.setProcesoValido(true);
			
		} catch (Exception e) {
			formReinicio.setProcesoValido(false);
			formReinicio.setMensaje(Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo"));
			logger.error("ERROR MBReinicioProceso -iniciaPantalla: ", e);
		}
	}

	public void reinicioInsaculacionPorGeografia() {
		
		try {
			if (formReinicio.isNivelParticipacion()) {
				ejecutaReinicio(mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion(),
								mbAdmin.getAdminData().getParticipacionSeleccionada().getId(),
								mbAdmin.getAdminData().getParticipacionSeleccionada().getNombre());
			} else {
				for(DTOParticipacionGeneral participacion : mbAdmin.getAdminData().getListaParticipaciones()) {
					ejecutaReinicio(participacion.getIdParticipacion(), 
								participacion.getId(),
								participacion.getNombre());
				}
			}
		} catch (Exception e) {
			logger.error("ERROR MBReinicioProceso -reinicioInsaculacionPorGeografia: ", e);
		}
	}

	public void ejecutaReinicio(Integer idParticipacion, Integer idGeograficoParticipacion, String nombreParticipacion) {
		FacesContext context = FacesContext.getCurrentInstance();
		String participacion = formReinicio.getNombreEstado() + " "
								+ idGeograficoParticipacion + " - "
								+ nombreParticipacion;

		try {
			
			Integer estatusReinicio = bsdReinicioProceso.actualizaEstatus(formReinicio.getIdDetalleProcesoElectoral(), 
																		idParticipacion,
																		Constantes.ESTATUS_PROCESO_FINALIZADO);

			if (bsdReinicioProceso.ejecutaReinicio(formReinicio.getIdProcesoElectoral(),
													formReinicio.getIdDetalleProcesoElectoral(),
													idParticipacion)) {
				bsdReinicioProceso.actualizaEstatus(formReinicio.getIdDetalleProcesoElectoral(),
													idParticipacion,
													estatusReinicio);
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", EXITO + participacion + EXITO_FIN));
				
			} else {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", ERROR + participacion));
			}

		} catch (Exception e) {
			logger.error("ERROR MBReinicioProceso -ejecutaReinicio: ", e);
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ",  ERROR + participacion));
		}
	}

	public FormReinicio getFormReinicio() {
		return formReinicio;
	}
	
}
