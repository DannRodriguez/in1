package mx.ine.procprimerinsa.mb;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.itextpdf.text.Document;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.PdfWriter;

import mx.ine.procprimerinsa.as.ASWSRegistraBitacoraInterface;
import mx.ine.procprimerinsa.bsd.BSDImpresionCNInterface;
import mx.ine.procprimerinsa.dto.DTOSeccionesLocalidades;

import mx.ine.procprimerinsa.form.FormImpresionCartas;
import mx.ine.procprimerinsa.helper.impl.HLPPDFImpresionCarta;
import mx.ine.procprimerinsa.helper.impl.HLPValidadorImpresionCartaNotificacion;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

@Qualifier("mbICNotif")
@RequestScoped
public class MBImpresionCartaNotificacion implements Serializable {	
	private static final long serialVersionUID = 2237408387216242850L;
	private static final Logger logger = Logger.getLogger(MBImpresionCartaNotificacion.class);
	private static final String MENSAJE_ALERT = "mensajeAlert";

	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdImpresionCN")
	private BSDImpresionCNInterface bsdImpresionCN;
	
	@Autowired
	@Qualifier("wsRegistraBitacora")
	private transient ASWSRegistraBitacoraInterface wsRegistraBitacora;
		
	private FormImpresionCartas form;
	private HLPValidadorImpresionCartaNotificacion validador;
	
	public void init() {
		try {
			form = new FormImpresionCartas();
			validador = new HLPValidadorImpresionCartaNotificacion(form);
			form.inicia();
			
			String menuValido = mbAdmin.verificaElementosMenu(true, true, true);
			
			if (!menuValido.isEmpty()) {
				form.setProcesoValido(false);
				form.setMensaje(menuValido);
				return;
			}
			
			wsRegistraBitacora.solicitaRegistro(mbAdmin.getAdminData().getUsuario(), 
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
											Constantes.SERVICIO_BITACORA_IMPRESION,
											Constantes.EJECUTA);
        	
        	form.setMapVotos(bsdImpresionCN.obtieneTiposVotoPorParticipacion(
						        			mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
						        			mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion()));
        	
			cambiaFiltro();
			
			// Variable para el atributo tipo de las participaciones
			String tipo = obtenerTipoParticipacion(mbAdmin.getAdminData().getAmbitoCaptura(),
											mbAdmin.getAdminData().getProcesoSeleccionado().getAmbitoSistema());
			
			//se consultan los ares
			form.setlARES(bsdImpresionCN.obtenerARES(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
											mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion()));
						
			form.setlSecciones(bsdImpresionCN.obtenerSecciones(mbAdmin.getAdminData().getIdEstadoSeleccionado(), 
					mbAdmin.getAdminData().getProcesoSeleccionado().getCorte(),
					tipo.equals("DistritoFederal")?mbAdmin.getAdminData().getParticipacionSeleccionada().getId():null, 
					tipo.equals("DistritoLocal")?mbAdmin.getAdminData().getParticipacionSeleccionada().getId():null,
					tipo.equals("municipio")?mbAdmin.getAdminData().getParticipacionSeleccionada().getId():null));
			
			//se obtienen los datos de la junta distrital
			form.setDtoJuntaDistrital(bsdImpresionCN.obtenerDatosJuntaDistrital(
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
											mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado(),
											mbAdmin.getAdminData().getEstadoSeleccionado().getNombreEstado(),
											mbAdmin.getAdminData().getParticipacionSeleccionada().getId(),
											mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion()));
			form.getDtoJuntaDistrital().setNameEstado(mbAdmin.getAdminData().getEstadoSeleccionado().getNombreEstado());
			form.getDtoJuntaDistrital().setName("Junta Distrital Ejecutiva " 
									+ String.format("%02d", mbAdmin.getAdminData().getParticipacionSeleccionada().getId()) 
									+ " "
									+ mbAdmin.getAdminData().getParticipacionSeleccionada().getNombre()
									+ " en " 
									+ mbAdmin.getAdminData().getEstadoSeleccionado().getNombreEstado());
						
			form.setExisteFirma(form.getDtoJuntaDistrital().getFirma() != null);
			form.setExisteDomicilioJunta(form.getDtoJuntaDistrital().getDomicilio() != null 
									&& !form.getDtoJuntaDistrital().getDomicilio().isEmpty());			
			
			form.setProcesoValido(true);
			
		} catch (Exception e) {
			form.setProcesoValido(false);
			form.setMensaje(Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo"));
			logger.error("ERROR MBImpresionCartasNotificacion -init: ", e);
		}
	}

	public String obtenerTipoParticipacion(String ambitoCaptura, String ambitoParticipacion) {
		switch (ambitoCaptura) {
			case "D":
				return ambitoParticipacion.equals("F") ? 
						"DistritoFederal"
						: "DistritoLocal";
			case "M":
				return "Municipio";
			case "R":
				return "Regiduria";
			case "L":
				return "Localidad";
			case "C":
				return "Comunidad";
			default:
				return "";
		}
	}

	public void cambiaFiltro() {
		form.setIdAreaResponsabilidad(null);
		form.setSeccion(null);
		form.setFiltroSeccion(null);
		form.getlOrdenVisita().clear();
		form.setOrdenVisitaInicio(null);
		form.setOrdenVisitaFinal(null);
		form.setIdLocalidad(null);
		form.getlLocalidades().clear();
		form.getlManzanas().clear();
		form.setManzana(null);
		form.setFiltroIndividual(1);
		form.setNumeroCredencialElector(null);
		form.setFolio(null);
		form.setOrdenamiento(1);
	}
	
	public void cambiaAreaResponsabilidad() {
		form.setOrdenVisitaInicio(null);
		form.setOrdenVisitaFinal(null);
		form.getlOrdenVisita().clear();
		if(form.getIdAreaResponsabilidad() != null 
				&& form.getIdAreaResponsabilidad() != 0) {
			form.setlOrdenVisita(bsdImpresionCN.obtenerOrdenVisita(
										mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
										mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
										mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion(), 
										form.getIdAreaResponsabilidad(), 
										null));
		}
	}
	
	public void cambiaSeccion() {
		form.getlLocalidades().clear();
		form.getlOrdenVisita().clear();
		form.setFiltroSeccion(null);
		cambiaFiltroSeccion();
		
		if(form.getSeccion() != null
				&& form.getSeccion() != 0) {
			form.setlOrdenVisita(bsdImpresionCN.obtenerOrdenVisita(
										mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
										mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
										mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion(), 
										null, 
										form.getSeccion()));
			
			for(DTOSeccionesLocalidades seccion: form.getlSecciones()) {
				if(seccion.getSec().equals(form.getSeccion())) {
					form.getlLocalidades().addAll(seccion.getLocalidades().values());
					break;
				}
			}
		}
	}
	
	public void cambiaFiltroSeccion() {
		form.setOrdenVisitaInicio(null);
		form.setOrdenVisitaFinal(null);
		form.setIdLocalidad(null);
		cambiaLocalidad();
	}
	
	public void cambiaLocalidad() {
		form.getlManzanas().clear();
		form.setManzana(null);
		
		if(form.getIdLocalidad() != null
				&& form.getIdLocalidad() != 0) {
			form.setlManzanas(bsdImpresionCN.obtenerManzanas(mbAdmin.getAdminData().getIdEstadoSeleccionado(), 
															mbAdmin.getAdminData().getProcesoSeleccionado().getCorte(), 
															form.getSeccion(), 
															form.getIdLocalidad()));
		}
	}
	
	public void cambiaFiltroIndividual() {
		form.setNumeroCredencialElector(null);
		form.setFolio(null);
	}

	public void descargarArchivoFrente() {
		form.setTabActivo(2);
		HLPPDFImpresionCarta hlpPDFImpresionCarta = null;
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(!validador.validaDatos()) {
			return;
		}
		
		switch(form.getFiltro()) {
			case 1:
				 hlpPDFImpresionCarta = bsdImpresionCN.imprimirCartas(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
											mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion(), 
											form.getIdTipoVoto(),
											mbAdmin.getAdminData().getIdEstadoSeleccionado(), 
											mbAdmin.getAdminData().getParticipacionSeleccionada().getId(), 
											mbAdmin.getAdminData().getEstadoSeleccionado().getNombreEstado(), 
											form.getIdAreaResponsabilidad(), 
											form.getOrdenVisitaInicio() != null && form.getOrdenVisitaInicio() != 0 ? form.getOrdenVisitaInicio() : null, 
											form.getOrdenVisitaFinal() != null && form.getOrdenVisitaFinal() != 0 ? form.getOrdenVisitaFinal() : null, 
											null, null, null, null, null, 
											form.getOrdenamiento(), 
											form.getlSecciones(),
											form.getLogo() != null && form.getLogo());
				break;
			case 2:
				hlpPDFImpresionCarta = bsdImpresionCN.imprimirCartas(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
											mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion(),
											form.getIdTipoVoto(),
											mbAdmin.getAdminData().getIdEstadoSeleccionado(), 
											mbAdmin.getAdminData().getParticipacionSeleccionada().getId(), 
											mbAdmin.getAdminData().getEstadoSeleccionado().getNombreEstado(), 
											null,
											form.getOrdenVisitaInicio() != null && form.getOrdenVisitaInicio() != 0 ? form.getOrdenVisitaInicio() : null, 
											form.getOrdenVisitaFinal() != null && form.getOrdenVisitaFinal() != 0 ? form.getOrdenVisitaFinal() : null, 
											form.getSeccion(),
											form.getIdLocalidad() != null && form.getIdLocalidad() != 0 ? form.getIdLocalidad() : null, 
											form.getManzana() != null && form.getManzana() != 0 ? form.getManzana() : null, 
											null, null,
											form.getOrdenamiento(),
											form.getlSecciones(),
											form.getLogo() != null && form.getLogo());
				break;
			case 3:
				hlpPDFImpresionCarta = bsdImpresionCN.imprimirCartas(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
											mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion(), 
											form.getIdTipoVoto(),
											mbAdmin.getAdminData().getIdEstadoSeleccionado(), 
											mbAdmin.getAdminData().getParticipacionSeleccionada().getId(), 
											mbAdmin.getAdminData().getEstadoSeleccionado().getNombreEstado(), 
											null, null, null, null, null, null, 
											form.getNumeroCredencialElector() != null && !form.getNumeroCredencialElector().isEmpty() ? form.getNumeroCredencialElector().toUpperCase() : null, 
											form.getFolio() != null && form.getFolio() != 0 ? form.getFolio() : null, 
											form.getOrdenamiento(), 
											form.getlSecciones(),
											form.getLogo() != null && form.getLogo());
				break;
			default:
			
		}
		
		if(hlpPDFImpresionCarta != null) {
			obtenerArchivo(hlpPDFImpresionCarta);
		} else {
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_WARN, " ", 
							Utilidades.mensajeProperties("04E_noResultados")));
		}
		
	}
	
	public void descargarArchivoReverso() {
		form.setTabActivo(1);
		HLPPDFImpresionCarta hlpPDFImpresionCarta = null;
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(!validador.validaDatos()) {
			return;
		}
		
		form.getDtoJuntaDistrital().setTelefono(form.getTelefonoJunta());
		hlpPDFImpresionCarta = bsdImpresionCN.imprimirReverso(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
													mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(), 
													mbAdmin.getAdminData().getIdEstadoSeleccionado(), 
													mbAdmin.getAdminData().getParticipacionSeleccionada().getId(), 
													form.getDtoJuntaDistrital(), 
													form.getFirma() != null && form.getFirma(),
													form.getLogo() != null && form.getLogo(), 
													form.getDomicilioJunta() != null && form.getDomicilioJunta());
		
		if(hlpPDFImpresionCarta != null) {
			obtenerArchivo(hlpPDFImpresionCarta);
		} else {
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_WARN, " ", 
															Utilidades.mensajeProperties("04E_noResultados")));
		}
		
	}

	private void obtenerArchivo(HLPPDFImpresionCarta hlppdfImpresionCarta) {
		PdfWriter writer;
		Document document;
		OutputStream os;

		try(ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			String nombreArchivo;
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			document = new Document();
			document.setPageSize(
					new RectangleReadOnly(Utilidades.cmtoPuntos(hlppdfImpresionCarta.getParametros().getdAncho()),
							Utilidades.cmtoPuntos(hlppdfImpresionCarta.getParametros().getdLargo())));
			document.setMargins(0f, 0f, 0f, 0f);
			writer = PdfWriter.getInstance(document, buffer);
			writer.setPageEvent(hlppdfImpresionCarta);
			document.open();
			if (hlppdfImpresionCarta.getFrenteReverso().equals("F")) {
				nombreArchivo = "CartaNotificacion_";
				for (int i = 0; i < (hlppdfImpresionCarta.getCiudadanos().size() - 1); i++) {
					document.newPage();
				} 
			} else {
				nombreArchivo = "reversoCartaNotificacion_";
			}
			document.close();
			ec.setResponseContentType("text/pdf");
			ec.setResponseContentLength(buffer.size());
			ec.setResponseHeader("Content-Disposition", ("attachment;filename=\"" + nombreArchivo + fechaArchivo() + ".pdf\""));
			os = ec.getResponseOutputStream();
			os.write(buffer.toByteArray());
			fc.responseComplete();
			
			writer.flush();
			writer.close();
			buffer.flush();
			os.flush();
			os.close();
		} catch (Exception e) {
			logger.error("ERROR MBImpresionCartaNotificacion -obtenerArchivo:" , e);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(MENSAJE_ALERT, new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error, ocurrió una falla durante la descarga del archivo"));
		} finally {
			os = null;
			writer = null;
		}

	}
	
	private String fechaArchivo() {
		return new SimpleDateFormat("ddMM_yyyy_hh_mm_ss").format(new Date());
	}

	public FormImpresionCartas getForm() {
		return form;
	}
	
}