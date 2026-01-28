package mx.ine.procprimerinsa.mb;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.procprimerinsa.as.ASWSRegistraBitacoraInterface;
import mx.ine.procprimerinsa.bsd.BSDFirmaInterface;
import mx.ine.procprimerinsa.form.FormFirma;
import mx.ine.procprimerinsa.util.Archivo;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

@Component("mbFirma")
@Scope("session")
public class MBFirma implements Serializable {	
	
	private static final long serialVersionUID = -5324837636790834386L;
	private static final String MENSAJE_ALERT = "mensajeAlert";
	private static final Logger logger = Logger.getLogger(MBFirma.class);
	
	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
    @Qualifier("bsdFirma")
    private BSDFirmaInterface bsdFirma;
	
	@Autowired
	@Qualifier("wsRegistraBitacora")
	private transient ASWSRegistraBitacoraInterface wsRegistraBitacora;
	
	private FormFirma form;
	private List<String> mimes;
	private transient StreamedContent archivoFirma;

	public void iniciaPantalla() {
		try {
			form = new FormFirma();
			
			String menuValido = mbAdmin.verificaElementosMenu(true, true, true);
			
			if (!menuValido.isEmpty()) {
				form.setProcesoValido(false);
				form.setMensajeError(menuValido);
				return;
			}
			
			wsRegistraBitacora.solicitaRegistro(mbAdmin.getAdminData().getUsuario(), 
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
											Constantes.SERVICIO_BITACORA_FIRMA,
											Constantes.EJECUTA);
			
			mimes = new ArrayList<>(List.of("image/png",
											"image/gif",
											"image/jpeg",
											"image/pjpeg"));
			
			archivoFirma = bsdFirma.obtenerFirma(mbAdmin.getAdminData().getIdDetalleProcesoSeleccionado(), 
												mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion());
			
			form.setMostrarFirma(archivoFirma != null);
			form.setProcesoValido(true);
			
		} catch (Exception e) {
			form.setProcesoValido(false);
			form.setMensajeError(Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo"));
			logger.error("ERROR MBFirma -iniciaPantalla: ", e);
		}
		
    }
    
    public void cargarArchivo() {
    	FacesContext context = FacesContext.getCurrentInstance();
    	form.setMostrarFirma(false);
    	archivoFirma = null;
    	    	
    	if(form.getArchivo() == null) {
			form.setMensajeError(Utilidades.mensajeProperties("04MV_seleccionarArchivo"));
    		form.setMostrarError(true);
			return;
    	}
    		
		try {
			File archivoTemp = new File(form.getArchivo().getFileName());
			FileUtils.copyInputStreamToFile(form.getArchivo().getInputStream(), archivoTemp);
			Archivo archivo = new Archivo(archivoTemp, mimes);
			
			if(archivoTemp.length() > 50000) {
				form.setMensajeError(Utilidades.mensajeProperties("04MV_maxSizeArchivoInvalido"));
	    		form.setMostrarError(true);
				archivoTemp.delete();
				return;
			}
			
			if (!archivo.isValido()) {
				form.setMensajeError(Utilidades.mensajeProperties("04MV_tipoArchivoInvalido"));
	    		form.setMostrarError(true);
				archivoTemp.delete();
				return;
			}
			
			if(bsdFirma.guardarArchivo(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
									mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
									mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion(),
									mbAdmin.getAdminData().getUsuario().getUsername(), 
									archivo)) {
				context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ",
																Utilidades.mensajeProperties("04MV_exitoSubirArchivo")));
				archivoFirma = bsdFirma.obtenerFirma(mbAdmin.getAdminData().getIdDetalleProcesoSeleccionado(), 
													mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion());
				form.setMostrarFirma(archivoFirma != null);
				form.setMostrarError(false);
			} else {
				form.setMensajeError(Utilidades.mensajeProperties("04MV_errorSubirArchivo"));
	    		form.setMostrarError(true);
	    		context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", Utilidades.mensajeProperties("04MV_errorSubirArchivo")));
			}
			
			archivoTemp.delete();
		} catch (IOException e) {
			logger.error("ERROR MBFirma -cargarArchivo: ", e);
			form.setMensajeError(Utilidades.mensajeProperties("04MV_errorSubirArchivo"));
    		form.setMostrarError(true);
    		context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", e.getMessage()));
		}
    }

	public FormFirma getForm() {
		return form;
	}

	public StreamedContent getArchivoFirma() {
		return archivoFirma;
	}

	public void setArchivoFirma(StreamedContent archivoFirma) {
		this.archivoFirma = archivoFirma;
	}

}
