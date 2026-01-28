package mx.ine.procprimerinsa.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.procprimerinsa.bsd.BSDConfiguracionParametrosInterface;
import mx.ine.procprimerinsa.configuration.GeneraEstructuraProcesos;
import mx.ine.procprimerinsa.dto.DTOConfiguracionParametros;
import mx.ine.procprimerinsa.form.FormConfiguracionParametros;
import mx.ine.procprimerinsa.helper.impl.HLPTransformadorConfiguracionParametros;
import mx.ine.procprimerinsa.helper.impl.HLPTransformadorMensajes.Mensaje;
import mx.ine.procprimerinsa.helper.impl.HLPValidadorConfiguracionParametros;

@RequestScoped
@Qualifier("mbConfiguracionParametros")
public class MBConfiguracionParametros implements Serializable {

	private static final long serialVersionUID = 6213609964126603239L;
	private static final Logger logger = Logger.getLogger(MBConfiguracionParametros.class);
	private static final String MENSAJE_ALERT = "mensajeAlert";
	private static final String MENSAJE_ALERT_TABLA = "mensajeAlertTabla";
	private static final String PARAMETROS = "parametros";
	
	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
		
	@Autowired
    @Qualifier("bsdConfiguracionParametros")
    private BSDConfiguracionParametrosInterface bsdConfiguracionParametros;
	
	@Autowired
	@Qualifier("estructuraProcesos")
	private GeneraEstructuraProcesos estructuraProcesos;
	
	private FormConfiguracionParametros form;
	private HLPValidadorConfiguracionParametros validador;
	private HLPTransformadorConfiguracionParametros transformador;
    
	public void init() {		
		form = new FormConfiguracionParametros();
		validador = new HLPValidadorConfiguracionParametros();
		validador.setForm(form);
		transformador = new HLPTransformadorConfiguracionParametros();
		
		if(mbAdmin.getAdminData().getProcesoSeleccionado() != null) {
			form.setIdCorte(mbAdmin.getAdminData().getProcesoSeleccionado().getCorte());
			form.setTipoCapturaSistema(mbAdmin.getAdminData().getProcesoSeleccionado().getTipoCapturaSistema());
		} else {
			form.setIdCorte(0);
			form.setTipoCapturaSistema("D");
		}
		
		form.setlParametros(bsdConfiguracionParametros.obtenerLista(form.getIdCorte()));
		form.setcParametros(bsdConfiguracionParametros.obtenerDescripcionParametros());
		
		form.setlEstados(new ArrayList<>());
		form.setlDistritos(new ArrayList<>());
		
    }
	
	public String guardar() {
		DTOConfiguracionParametros param;
		boolean agregaDescripcion = false;
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(form.getIdParametroSeleccionado() == null
				|| form.getIdParametroSeleccionado() == 0
				|| !form.getcParametros().containsKey(form.getIdParametroSeleccionado().toString())) {
			agregaDescripcion = true;
			if(!validador.validaDescripcion()) {
				return "";
			}
		}
		
		if(!validador.validaParametro()) {
			return "";
		}
		
		param = transformador.obtenerDTOConfiguracionParametros(form, agregaDescripcion);
		
		if(bsdConfiguracionParametros.agregaParametro(param, agregaDescripcion)) {
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
															Mensaje.GUARDA_OK.getClaveMensajeSede()));
		} else {
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
															Mensaje.GUARDA_NO_OK.getClaveMensajeSede()));
		}
		
		return PARAMETROS;
	}
	
	public String actualizar(DTOConfiguracionParametros parametro) {
		FacesContext context = FacesContext.getCurrentInstance();

		if(bsdConfiguracionParametros.actualizaParametro(parametro.getIdProceso(),
													parametro.getIdDetalle(), 
													parametro.getIdEstado(), 
													parametro.getIdDistrito(), 
													parametro.getTipoJunta(),
													parametro.getIdParametro(), 
													parametro.getValorParametro())) {
			context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
																	Mensaje.MODIFICA_OK.getClaveMensajeSede()));
		} else {
			context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
																	Mensaje.MODIFICA_NO_OK.getClaveMensajeSede()));
		}
		
		return PARAMETROS;
	}
	
	public String eliminar(DTOConfiguracionParametros parametro) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(bsdConfiguracionParametros.eliminaParametro(parametro.getIdProceso(),
														parametro.getIdDetalle(), 
														parametro.getIdEstado(), 
														parametro.getIdDistrito(), 
														parametro.getTipoJunta(),
														parametro.getIdParametro())) {
			context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
																	Mensaje.ELIMINA_OK.getClaveMensajeSede()));
			
		} else {
			context.addMessage(MENSAJE_ALERT_TABLA, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
																	Mensaje.ELIMINA_NO_OK.getClaveMensajeSede()));
		}
		
		form.setlParametros(bsdConfiguracionParametros.obtenerLista(form.getIdCorte()));
		if(form.getlParametrosFiltrados() != null) form.getlParametrosFiltrados().clear();
		
		return PARAMETROS;
	}
	
	public void cambiaDescripcionParametro() {
		form.setDescripcionParametro(null);
		form.setDescripcionValores(null);
		
		if(form.getIdParametroSeleccionado() != null
				&& form.getIdParametroSeleccionado() == 0) {
			form.setIdParametroSeleccionado(null);
		}
	}
	
	public void cambiaProceso() {
		form.setIdDetalle(null);
		cambiaDetalle();
	}
	
	public void cambiaDetalle() {
		form.setEstadoSeleccionado(null);
		form.getlEstados().clear();
		
		if(form.getIdProceso() != null 
				&& form.getIdProceso() != 0
				&& form.getIdDetalle() != null
				&& form.getIdDetalle() != 0) {
			try {
				form.setlEstados(estructuraProcesos.getProcesos()
												.get(form.getIdDetalle())
												.getEstados()
												.values()
												.stream()
												.collect(Collectors.toList()));
			} catch(Exception e) {
				logger.error("MBConfiguracionParametros -cambiaDetalle: (proceso) " + form.getIdProceso() 
																	+ "- (detalle) " + form.getIdDetalle());
			}
		}
		
		cambiaEstado();
	}
	
	public void cambiaEstado() {
		form.setDistritoSeleccionado(null);
		form.getlDistritos().clear();
		
		if(form.getEstadoSeleccionado() != null 
				&& form.getEstadoSeleccionado().getIdEstado() != 0) {
			try {
				form.setlDistritos(estructuraProcesos.getProcesos()
												.get(form.getIdDetalle())
												.getEstados()
												.get(form.getEstadoSeleccionado().getIdEstado())
												.getParticipaciones()
												.values()
												.stream()
												.collect(Collectors.toList()));
			} catch(Exception e) {
				logger.error("MBConfiguracionParametros -cambiaEstado: (proceso) " + form.getIdProceso() 
																	+ "- (detalle) " + form.getIdDetalle()
																	+ "- (estado) " + form.getEstadoSeleccionado());
			}
		}
	}

	public FormConfiguracionParametros getForm() {
		return form;
	}
	
}
