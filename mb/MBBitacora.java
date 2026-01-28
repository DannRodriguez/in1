package mx.ine.procprimerinsa.mb;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.procprimerinsa.bsd.BSDMapasInterface;
import mx.ine.procprimerinsa.bsd.BSDReporteBitacoraInterface;
import mx.ine.procprimerinsa.dto.helper.FormReporteBitacora;
import mx.ine.procprimerinsa.util.Utilidades;

@Qualifier("mbBitacora")
@RequestScoped
public class MBBitacora implements Serializable {

	private static final long serialVersionUID = 4318308367963744189L;
	private static final Logger logger = Logger.getLogger(MBBitacora.class);
	private static final String MENSAJE_ALERT = "mensajeAlert";
	
	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdReporteBitacora")
	private BSDReporteBitacoraInterface bsdReporteBitacora;
	
	@Autowired
	@Qualifier("bsdMapas")
	private BSDMapasInterface bsdMapas;
	
	private FormReporteBitacora form;
	
	private static final String[] HEADER_REPORTE_BITACORA = new String[] { "Proceso electoral", 
																			"Detalle del proceso",
																			"Sistema", 
																			"Usuario", 
																			"Rol de usuario",
																			"Nivel geográfico",
																			"Módulo", 
																			"Acción",
																			"Fecha y hora de ingreso" };
	private static final String ARCHIVO_REPORTE_BITACORA = "reporteBitacora";
		
	public void init() {
		try {
			form = new FormReporteBitacora();
			
			String menuValido = mbAdmin.verificaElementosMenu(true, false, false);
			
			if (!menuValido.isEmpty()) {
				form.setProcesoValido(false);
				form.setMensaje(menuValido);
				return;
			}
			
			form.setDetalles(bsdMapas.getDetallesProceso(
					mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
					mbAdmin.getAdminData().getTipoProceso()));
		
			obtenerRegistrosBitacora();
			
			form.setProcesoValido(true);
															
		} catch (Exception e) {
			form.setProcesoValido(false);
			form.setMensaje(Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo"));
			logger.error("ERROR MBBitacora -init:", e);
		}
	}
	
	public void cambiaFechaInicio() {
		try {
			form.setFechaFin(null);
			obtenerRegistrosBitacora();
		} catch (Exception e) {
			logger.error("ERROR MBBitacora -cambiaFechaInicio: ", e);
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al seleccionar la fecha de inicio"));
		}
	}
	
	public void obtenerRegistrosBitacora() {
		try {
			form.setlReporteBitacora(bsdReporteBitacora.obtenerRegistrosBitacora(
															form.getDetalles(),
															form.getFechaInicio(),
															form.getFechaFin()));
		} catch (Exception e) {
			logger.error("ERROR MBBitacora -obtenerRegistrosBitacora: ", e);
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al actualizar los registros de la bitácora"));
		}
	}
	
	public void generaReporte() {

		try {
			Utilidades.descargaArchivoCSV(HEADER_REPORTE_BITACORA, 
										form.getlReporteBitacora(),
										ARCHIVO_REPORTE_BITACORA);
		} catch (Exception e) {
			logger.error("ERROR MBBitacora -generaReporte: ", e);
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Error al descargar la bitácora"));
		}
	}
	
	public Integer registrosObtenidos() {
		return form.getlReporteBitacora() != null ?
				form.getlReporteBitacora().size() 
				: 0;
	}

	public FormReporteBitacora getForm() {
		return form;
	}
	
}
