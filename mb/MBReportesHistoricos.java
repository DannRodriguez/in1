package mx.ine.procprimerinsa.mb;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.procprimerinsa.bsd.BSDAdminGlusterInterface;
import mx.ine.procprimerinsa.bsd.BSDMapasInterface;
import mx.ine.procprimerinsa.dto.DTOEstadoDetalles;
import mx.ine.procprimerinsa.form.FormReportesHistoricos;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

@Qualifier("mbReportesHistoricos")
@RequestScoped
public class MBReportesHistoricos implements Serializable {

	private static final long serialVersionUID = -7658339747467934378L;
	private static final Logger logger = Logger.getLogger(MBReportesHistoricos.class);
	
	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdMapas")
	private BSDMapasInterface bsdMapas;
	
	@Autowired
	@Qualifier("bsdAdminGlusterImpl")
	private transient BSDAdminGlusterInterface bsdAdminGluster;
	
	private FormReportesHistoricos form;
		
	public void init() {
		try {
			form = new FormReportesHistoricos();
						
			form.setIdEstadoUsuario(mbAdmin.getAdminData().getUsuario().getIdEstado() != null ?
									mbAdmin.getAdminData().getUsuario().getIdEstado() : 0);
			form.setIdDistritoUsuario(mbAdmin.getAdminData().getUsuario().getIdDistrito() != null ?
								mbAdmin.getAdminData().getUsuario().getIdDistrito() : 0);
			
			form.setEstadosReportesHistoricos(bsdMapas.getDetallesProcesoHistoricos(
								form.getIdEstadoUsuario(), 
								form.getIdDistritoUsuario()));
			
			prefillFilterData();
			
			form.setProcesoValido(true);
		
		} catch(Exception e) {
			form.setProcesoValido(false);
			form.setMensaje(Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo"));
			logger.error("ERROR MBReportesHistoricos -init: ", e);
		}
		
	}
	
	private void prefillFilterData() {
		
		if (mbAdmin.getAdminData().getEstadoSeleccionado() != null
				&& mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado() != null
				&& !mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado().equals(0)) {
			Optional<DTOEstadoDetalles> estado = form.getEstadosReportesHistoricos()
													.stream()
													.filter(e -> e.getIdEstado()
																	.equals(mbAdmin.getAdminData().getEstadoSeleccionado().getIdEstado()))
													.findAny();
			if(estado.isPresent()) {
				form.setEstadoSeleccionado(estado.get());
			} else if(form.getEstadosReportesHistoricos().size() == 1) {
				form.setEstadoSeleccionado(form.getEstadosReportesHistoricos().get(0));
			}
			
			seleccionaEstado();
		}

		if (mbAdmin.getAdminData().getProcesoSeleccionado() != null
			&& mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral() != null
			&& !mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral().equals(0)
			&& form.getEstadoSeleccionado() != null) {
			
			Integer idDetalleMenu = mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso();
			
			if(form.getEstadoSeleccionado().getDetalles().containsKey(idDetalleMenu)) {
				form.setIdDetalleSeleccionado(idDetalleMenu);
				seleccionaProceso();
			}
		}
		
		if (mbAdmin.getAdminData().getIdParticipacionSeleccionada() != null
			&& !mbAdmin.getAdminData().getIdParticipacionSeleccionada().equals(0)
			&& form.getDetalleSeleccionado() != null) {
			
			Integer idParticipacionMenu = mbAdmin.getAdminData().getParticipacionSeleccionada().getIdParticipacion();
			
			if(form.getDetalleSeleccionado().getParticipaciones().containsKey(idParticipacionMenu)) {
				form.setIdParticipacionSeleccionada(idParticipacionMenu);
				seleccionaParticipacion();
			}
			
		}
		
	}
	
	public void seleccionaEstado() {
		form.setIdDetalleSeleccionado(null);
		
		try {
			
			if(form.getEstadoSeleccionado() != null
				&& form.getEstadoSeleccionado().getDetalles().size() == 1) {
				form.setIdDetalleSeleccionado(form.getEstadoSeleccionado()
													.getDetalles()
													.values()
													.stream()
													.findAny()
													.orElseThrow()
													.getIdDetalle());
			}
			
		} catch(Exception e) {
			logger.error("ERROR MBReportesHistoricos -seleccionaEstado: ", e);
		}
		
		seleccionaProceso();
	}
	
	public void seleccionaProceso() {
		form.setDetalleSeleccionado(null);
		form.setDirectorioPrincipal("");
		form.setIdParticipacionSeleccionada(null);
		
		try {
			
			if(form.getIdDetalleSeleccionado() != null) {
				form.setDetalleSeleccionado(form.getEstadoSeleccionado()
												.getDetalles()
												.get(form.getIdDetalleSeleccionado()));
				
				form.setDirectorioPrincipal(bsdAdminGluster.generaDirectorioPrincipal(
															form.getDetalleSeleccionado().getIdProceso(), 
															form.getDetalleSeleccionado().getIdDetalle()));
				
				if(form.getDetalleSeleccionado().getParticipaciones().size() == 1) {
					form.setIdParticipacionSeleccionada(form.getDetalleSeleccionado()
													.getParticipaciones()
													.values()
													.stream()
													.findAny()
													.orElseThrow()
													.getIdParticipacion());
				}
			}
			
		} catch(Exception e) {
			logger.error("ERROR MBReportesHistoricos -seleccionaProceso: ", e);
		}
		
		seleccionaParticipacion();
	}
	
	public void seleccionaParticipacion() {
		form.setParticipacionSeleccionada(null);
		form.setDocumentos(new ArrayList<>());
		
		try {
			
			if(form.getIdParticipacionSeleccionada() == null) return;
			
			form.setParticipacionSeleccionada(form.getDetalleSeleccionado()
													.getParticipaciones()
													.get(form.getIdParticipacionSeleccionada()));
			
			agregarDocumentos(Constantes.CARPETA_LISTADOS);
			agregarDocumentos(Constantes.CARPETA_CEDULAS);
		
		} catch(Exception e) {
			logger.error("ERROR MBReportesHistoricos -seleccionaParticipacion: ", e);
		}
		
	}
	
	private void agregarDocumentos(String carpeta) {
		String rutaDocumentos = new StringBuilder(form.getDirectorioPrincipal())
										.append(carpeta).append(File.separator)
										.append(form.getEstadoSeleccionado().getNombreEstado()).append(File.separator)
										.append(String.format("%02d", form.getParticipacionSeleccionada().getId()))
										.append("_").append(Utilidades.cleanStringForFileName(form.getParticipacionSeleccionada().getNombre()))
										.toString();
		File directorioDocumentos = new File(rutaDocumentos);
		
		if(directorioDocumentos.exists() && directorioDocumentos.isDirectory()) {
			Collections.addAll(form.getDocumentos(), directorioDocumentos.listFiles());
		}
	}
	
	public void descargar(String ruta) {
		try {
			bsdAdminGluster.descargarArchivo(ruta);
		} catch(Exception e) {
			logger.error("ERROR MBReportesHistoricos -descargar: ", e);
		}
	}

	public FormReportesHistoricos getForm() {
		return form;
	}

}
