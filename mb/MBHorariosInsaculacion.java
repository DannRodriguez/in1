package mx.ine.procprimerinsa.mb;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import org.jboss.logging.Logger;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.procprimerinsa.bsd.BSDHorariosInsaculacionInterface;
import mx.ine.procprimerinsa.bsd.BSDMapasInterface;
import mx.ine.procprimerinsa.dto.DTOEstado;
import mx.ine.procprimerinsa.dto.DTOGrupoHorarioInsaculacion;
import mx.ine.procprimerinsa.dto.db.DTOHorariosInsaculacion;
import mx.ine.procprimerinsa.util.Utilidades;

@RequestScoped
@Qualifier("mbHorariosInsaculacion")
public class MBHorariosInsaculacion implements Serializable {

	private static final long serialVersionUID = -4116944612674635118L;
	private static final Logger logger = Logger.getLogger(MBHorariosInsaculacion.class);
	private static final String MENSAJE_ALERT = "mensajeAlert";
	private static final String HORARIOS = "horarios";
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");   
	
	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdHorariosInsaculacion")
	private BSDHorariosInsaculacionInterface bsdHorariosInsaculacion;
	
	@Autowired
	@Qualifier("bsdMapas")
	private BSDMapasInterface bsdMapas;
	
	private List<DTOGrupoHorarioInsaculacion> listGrupoHorarioInsaculacion;
	private DTOGrupoHorarioInsaculacion grupoHorarioInsaculacion;
	private Integer idGrupoHorarioInsaculacion;
	private List<Integer> detalles;
	private Map<Integer, DTOEstado> estados;
	private DualListModel<String> estadosPickList;
	private boolean procesoValido;
	private String mensaje;
	
	public void init() {
		try {
			
			String menuValido = mbAdmin.verificaElementosMenu(true, false, false);
			
			if (!menuValido.isEmpty()) {
				procesoValido = false;
				mensaje = menuValido;
				return;
			}
			
			detalles = bsdMapas.getDetallesProceso(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
												mbAdmin.getAdminData().getTipoProceso());
			
			List<DTOHorariosInsaculacion> horarios = bsdMapas.obtenerHorarios(detalles);
			
			estados = bsdMapas.getEstados(detalles,
						 				mbAdmin.getAdminData().getProcesoSeleccionado().getCorte());
			
			listGrupoHorarioInsaculacion = bsdMapas.getGrupos(horarios,
															estados);
			
			grupoHorarioInsaculacion = null;
			idGrupoHorarioInsaculacion = null;
			procesoValido = true;
		} catch(Exception e) {
			procesoValido = false;
			mensaje = Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo");
			logger.error("ERROR MBHorariosInsaculacion -init: ", e);
		}
	}
	
	public String obtieneEtiquetaHorario(DTOGrupoHorarioInsaculacion horario) {
		StringBuilder etiqueta = new StringBuilder();
		etiqueta.append(horario.getIdGrupo());
		etiqueta.append(" - ");
		if(horario.getHoraInicio() != null) etiqueta.append(dateFormat.format(horario.getHoraInicio()));
		etiqueta.append(" a ");
		if(horario.getHoraFinal() != null) etiqueta.append(dateFormat.format(horario.getHoraFinal()));
		return etiqueta.toString();
	}
	
	public void seleccionaHorario() {
		estadosPickList = new DualListModel<>();
		
		if(idGrupoHorarioInsaculacion == null 
				|| idGrupoHorarioInsaculacion == 0) {
			grupoHorarioInsaculacion = new DTOGrupoHorarioInsaculacion();
			grupoHorarioInsaculacion.setIdGrupo(0);
			estadosPickList.setTarget(new ArrayList<>());
		} else {
			Optional<DTOGrupoHorarioInsaculacion> horario  = listGrupoHorarioInsaculacion
													.stream()
													.filter(h -> h.getIdGrupo().equals(idGrupoHorarioInsaculacion))
													.findFirst();
			if(!horario.isPresent()) { 
				FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
								"Error al seleccionar el horario "));
				return;
			}
			
			grupoHorarioInsaculacion = horario.get();
			estadosPickList.setTarget(grupoHorarioInsaculacion.getEstados()
										.values()
								        .stream()
								        .sorted((e1, e2)->e1.getIdEstado().compareTo(e2.getIdEstado()))
								        .map(e -> e.getIdEstado() + "-" + e.getNombreEstado())
								        .collect(Collectors.toList()));
		}
		
		Set<Integer> estadosAsignados = listGrupoHorarioInsaculacion.stream()
										.flatMap(h -> h.getEstados().keySet().stream())
										.collect(Collectors.toSet());
		
		estadosPickList.setSource(estados.values()
										.stream()
										.filter(e -> !estadosAsignados.contains(e.getIdEstado()))
										.sorted((e1, e2)->e1.getIdEstado().compareTo(e2.getIdEstado()))
										.map(e -> e.getIdEstado() + "-" + e.getNombreEstado())
										.collect(Collectors.toList()));
		
	}
	
	public String guardaHorario() {
		
		if(!validaForm()) return "";
		
		if(bsdHorariosInsaculacion.guardaHorario(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
												detalles, 								
												grupoHorarioInsaculacion,
												estadosPickList.getTarget(),
												mbAdmin.getAdminData().getUsuario().getUsername())) {
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, " ", 
							"Éxito al guardar el horario"));
		} else {
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Error al guardar el horario"));
		}
		
		return HORARIOS;
	}
	
	private boolean validaForm() {
		
		if(grupoHorarioInsaculacion == null) return false;
		
		if(estadosPickList == null || estadosPickList.getTarget() == null) return false;
		
		if(grupoHorarioInsaculacion.getIdGrupo() == 0
				&& estadosPickList.getTarget().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Para dar de alta el horario se requiere agregar al menos un estado"));
			return false;
		}
		
		if(!estadosPickList.getTarget().isEmpty()
				&& (grupoHorarioInsaculacion.getHoraInicio() == null
					|| grupoHorarioInsaculacion.getHoraFinal() == null)) {
			FacesContext.getCurrentInstance().addMessage(MENSAJE_ALERT, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", 
							"Para dar de alta el horario se requiere definir un horario inicial y uno final"));
			return false;
		}
		
		return true;
	}

	public List<DTOGrupoHorarioInsaculacion> getListGrupoHorarioInsaculacion() {
		return listGrupoHorarioInsaculacion;
	}

	public void setListGrupoHorarioInsaculacion(List<DTOGrupoHorarioInsaculacion> listGrupoHorarioInsaculacion) {
		this.listGrupoHorarioInsaculacion = listGrupoHorarioInsaculacion;
	}

	public DTOGrupoHorarioInsaculacion getGrupoHorarioInsaculacion() {
		return grupoHorarioInsaculacion;
	}

	public void setGrupoHorarioInsaculacion(DTOGrupoHorarioInsaculacion grupoHorarioInsaculacion) {
		this.grupoHorarioInsaculacion = grupoHorarioInsaculacion;
	}

	public Integer getIdGrupoHorarioInsaculacion() {
		return idGrupoHorarioInsaculacion;
	}

	public void setIdGrupoHorarioInsaculacion(Integer idGrupoHorarioInsaculacion) {
		this.idGrupoHorarioInsaculacion = idGrupoHorarioInsaculacion;
	}

	public DualListModel<String> getEstadosPickList() {
		return estadosPickList;
	}

	public void setEstadosPickList(DualListModel<String> estadosPickList) {
		this.estadosPickList = estadosPickList;
	}

	public boolean isProcesoValido() {
		return procesoValido;
	}

	public String getMensaje() {
		return mensaje;
	}
	
}