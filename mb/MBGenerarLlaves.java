package mx.ine.procprimerinsa.mb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import mx.ine.procprimerinsa.as.ASWSRegistraBitacoraInterface;
import mx.ine.procprimerinsa.bsd.BSDGenerarLlavesInterface;
import mx.ine.procprimerinsa.configuration.GeneraEstructuraProcesos;
import mx.ine.procprimerinsa.dto.DTOEstadoGeneral;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.form.FormGenerarLlaves;
import mx.ine.procprimerinsa.form.FormGenerarLlaves.Nodo;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.ServletContextUtils;
import mx.ine.procprimerinsa.util.Utilidades;

import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("mbGenerarLlaves")
@Scope("session")
public class MBGenerarLlaves implements Serializable{

	private static final long serialVersionUID = -3537654175611006030L;
	private static final Logger logger = Logger.getLogger(MBGenerarLlaves.class);
	private static final String MENSAJE_ALERT = "mensajeAlert";
	
	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdGenerarLlaves")
	private BSDGenerarLlavesInterface bsdGenerarLlaves;
	
	@Autowired
	@Qualifier("estructuraProcesos")
	private GeneraEstructuraProcesos estructuraProcesos;
	
	@Autowired
	@Qualifier("wsRegistraBitacora")
	private transient ASWSRegistraBitacoraInterface wsRegistraBitacora;
	
	private FormGenerarLlaves formGenerarLlaves;
	
	private static StreamedContent archivo;
	
	public void iniciaPantalla(){
		
		try{
			
			formGenerarLlaves = new FormGenerarLlaves();
			mbAdmin.getAdminData().setIdModulo(Constantes.ID_MODULO_GENERACION_DE_LLAVES);
			
			String menuValido = mbAdmin.verificaElementosMenu(true, false, false);
			
			if (!menuValido.isEmpty()) {
				formGenerarLlaves.setProcesoValido(false);
				formGenerarLlaves.setMensaje(menuValido);
				return;
			}
			
			wsRegistraBitacora.solicitaRegistro(mbAdmin.getAdminData().getUsuario(), 
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
											Constantes.SERVICIO_BITACORA_LLAVES,
											Constantes.EJECUTA);
			
			formGenerarLlaves.setDescargaActiva(false);
			formGenerarLlaves.setReinicioActivo(false);
			formGenerarLlaves.setGeneracionActiva(false);
			formGenerarLlaves.setRuta("");
			
			formGenerarLlaves.setIdProcesoElectoral(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral());
			formGenerarLlaves.setIdDetalleProceso(mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso());
			
			bsdGenerarLlaves.obtenerModoEjecucion(formGenerarLlaves);
			
			creaArbol();
			
			formGenerarLlaves.setProcesoValido(true);
			
		} catch (Exception e) {
			formGenerarLlaves.setProcesoValido(false);
			formGenerarLlaves.setMensaje(Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo"));
			logger.error("ERROR MBGenerarLlaves -iniciaPantalla: ", e);
		}
	}
	
	private void creaArbol() {
		
		formGenerarLlaves.inicializaArbol(mbAdmin.getAdminData().getProcesoSeleccionado().getDescripcionDetalle());
		
		try {
			
			Map<Integer, String[]> llaves = bsdGenerarLlaves.obtenerLlavesProceso(
												formGenerarLlaves.getIdDetalleProceso(),
												formGenerarLlaves.getModoEjecucion());
			
			for(DTOEstadoGeneral estado : estructuraProcesos.getProcesos()
														.get(formGenerarLlaves.getIdDetalleProceso())
														.getEstados().values()) {
				
				if(estado.getIdEstado().equals(0)) {
					continue;
				}
				
				TreeNode<Nodo> nodoEstado = formGenerarLlaves.agregaEstado(estado);
				
				if(estado.getParticipaciones() == null 
					|| estado.getParticipaciones().isEmpty()) {
					continue;
				}
				
				for(DTOParticipacionGeneral participacion : estado.getParticipaciones().values()) {
					String llaveVE = null;
					String llaveVCEYEC = null;
					
					if(!llaves.isEmpty()
						&& llaves.containsKey(participacion.getIdParticipacion())) {
						llaveVE = llaves.get(participacion.getIdParticipacion())[0];
						llaveVCEYEC = llaves.get(participacion.getIdParticipacion())[1];
					}
					
					formGenerarLlaves.agregaParticipacion(participacion, 
														llaveVE,
														llaveVCEYEC,
														nodoEstado);
				}
			}
		} catch(Exception e){
			logger.error("ERROR MBGenerarLlaves -creaArbol: ", e);
		}
	}
	
	public void generarLlaves() {
		FacesContext context = FacesContext.getCurrentInstance();
		String nivel = descripcionNivel();
		
		try {
			bsdGenerarLlaves.generarLlaves(mbAdmin.getAdminData().getProcesoSeleccionado().getNombreProceso(),
											estructuraProcesos.getProcesos()
														.get(formGenerarLlaves.getIdDetalleProceso())
														.getEstados(),
											formGenerarLlaves);
			actualizaArbol();
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
					"Las llaves para el " + nivel + " se generaron con éxito"));
			
		} catch (Exception e) {
			logger.error("ERROR MBGenerarLlaves -generarLlaves: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"Ocurrió un falla durante la generación de las llaves de " + nivel));
		}
	}
	
	public void reiniciarLlaves(){
		FacesContext context = FacesContext.getCurrentInstance();
		String nivel = descripcionNivel();
		
		try{
			bsdGenerarLlaves.reiniciarLlaves(mbAdmin.getAdminData().getProcesoSeleccionado().getNombreProceso(),
											estructuraProcesos.getProcesos()
														.get(formGenerarLlaves.getIdDetalleProceso())
														.getEstados(),
											formGenerarLlaves);
			actualizaArbol();
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
					"Las llaves de " + nivel + " se reiniciaron exitosamente"));
		} catch(Exception e){
			logger.error("ERROR MBGenerarLLaves -reiniciarLlaves: ", e);
			context.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"Ocurrió una falla durante el reinicio de las llaves de " + nivel));
		}
	}
	
	public void descargarLlaves() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		String nivel = descripcionNivel();
		
		try {
			ByteArrayOutputStream baos = bsdGenerarLlaves.descargarLlaves(estructuraProcesos.getProcesos()
																					.get(formGenerarLlaves.getIdDetalleProceso())
																					.getEstados()
																					.get(formGenerarLlaves.getIdEstado()),
																		formGenerarLlaves.getIdEstado() != 0 ? 
																				estructuraProcesos.getProcesos()
																					.get(formGenerarLlaves.getIdDetalleProceso())
																					.getEstados()
																					.get(formGenerarLlaves.getIdEstado())
																					.getParticipaciones()
																					.get(formGenerarLlaves.getIdParticipacion())
																				: null,
																		formGenerarLlaves);
			ec.responseReset();
			ec.setResponseContentType("application/octet-stream");
			ec.setResponseHeader("Expires", "0");
			ec.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0, no-cache, no-store");
			ec.setResponseHeader("Pragma", "no-cache");
			
			
			StringBuilder responseHeader = new StringBuilder()
							.append("attachment; filename=")
							.append("LLAVES")
							.append(formGenerarLlaves.getModoEjecucion().equals(0) ? "_SIMULACRO_" : "_")
							.append(nivel.toUpperCase())
							.append(".zip");
			
			ec.setResponseHeader("Content-Disposition", responseHeader.toString());
			ec.setResponseContentLength(baos.size());
			OutputStream output = ec.getResponseOutputStream();
			baos.writeTo(output);
			
			ec.responseFlushBuffer();
			
			output.close();
			baos.close();
			fc.responseComplete();	
			
		} catch(Exception e){
			logger.error("ERROR MBGenerarLlaves -descargarLlaves: ", e);
			fc.addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
					"Ocurrió un falla durante la descarga de las llaves de " + nivel));
		}
	}
	
	public void actualizaNodoSeleccionado(NodeSelectEvent event) throws IOException {
		formGenerarLlaves.setRuta(Constantes.RUTA_LOCAL_FS 
								+ formGenerarLlaves.actualizaNodoSeleccionado());
		
		if(formGenerarLlaves.getRuta() == null || formGenerarLlaves.getRuta().isEmpty()) {
			formGenerarLlaves.setRuta(ServletContextUtils.getRealResourcesPath("pdf") 
										+ File.separator 
										+ "nodisponible.pdf");
			formGenerarLlaves.setVisorActivo(false);
		}

		try{
			File pdf = new File(formGenerarLlaves.getRuta());
			InputStream targetStream = FileUtils.openInputStream(pdf);
			DefaultStreamedContent salida = DefaultStreamedContent.builder()
										.contentType("application/pdf")
										.stream(() -> targetStream)
										.build();
			archivo = salida;
			formGenerarLlaves.setVisorActivo(true);
		} catch(Exception e){
			formGenerarLlaves.setRuta(ServletContextUtils.getRealResourcesPath("pdf") 
								+ File.separator 
								+ "nodisponible.pdf");
			File pdf = new File(formGenerarLlaves.getRuta());
			InputStream targetStream = FileUtils.openInputStream(pdf);
			DefaultStreamedContent salida = DefaultStreamedContent.builder()
											.contentType("application/pdf")
											.stream(() -> targetStream)
											.build();
			archivo = salida;
			formGenerarLlaves.setVisorActivo(false);
		}
	}
	
	private String descripcionNivel() {
		if(formGenerarLlaves.getIdEstado().equals(0))
			return mbAdmin.getAdminData().getProcesoSeleccionado().getNombreProceso();
		else if(formGenerarLlaves.getIdParticipacion().equals(0))
			return estructuraProcesos.getProcesos()
					.get(formGenerarLlaves.getIdDetalleProceso())
					.getEstados()
					.get(formGenerarLlaves.getIdEstado())
					.getNombreEstado();
		else
			return String.format("%02d", estructuraProcesos.getProcesos()
															.get(formGenerarLlaves.getIdDetalleProceso())
															.getEstados()
															.get(formGenerarLlaves.getIdEstado())
															.getParticipaciones()
															.get(formGenerarLlaves.getIdParticipacion())
															.getId()) 
										+ " - " 
										+ estructuraProcesos.getProcesos()
															.get(formGenerarLlaves.getIdDetalleProceso())
															.getEstados()
															.get(formGenerarLlaves.getIdEstado())
															.getParticipaciones()
															.get(formGenerarLlaves.getIdParticipacion())
															.getNombre();
	}

	private void actualizaArbol(){
		formGenerarLlaves.limpiaArbol();
		creaArbol();
		formGenerarLlaves.setVisorActivo(false);
	}
	
	public FormGenerarLlaves getFormGenerarLlaves() {
		return formGenerarLlaves;
	}
	
	public StreamedContent getArchivo(){
		return archivo;
    }
	
}
