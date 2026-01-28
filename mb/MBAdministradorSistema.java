package mx.ine.procprimerinsa.mb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import mx.ine.parametrizacion.model.dto.DTOMenu;
import mx.ine.parametrizacion.model.dto.DTOModulo;
import mx.ine.procprimerinsa.bsd.BDSAdminServInterface;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.DTOUsuarioLogin;
import mx.ine.procprimerinsa.dto.helper.FormAdmin;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

public class MBAdministradorSistema implements Serializable {

	private static final long serialVersionUID = -5160000859061533805L;
	private static final Logger logger = Logger.getLogger(MBAdministradorSistema.class);

	@Autowired
	@Qualifier("bsdAdminServ")
	private transient BDSAdminServInterface bsdAdminServ;
	
	private FormAdmin adminData;
	
	public MBAdministradorSistema() {

		adminData = new FormAdmin();
		
		adminData.setUsuario((DTOUsuarioLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		adminData.setRolSistema(adminData.getUsuario().getRolUsuario());
		
	}
	
	public void inicializaMenu() {
		if (adminData.getTipoSistema() != null 
				&& !adminData.getTipoSistema().trim().equals("")
				&& adminData.getTipoSistema().trim().equals("electoral")) {
				cargaEstados();
		}
	}
	
	public void cargaEstados() {
		try {
		
			if(adminData.getListaEstados() == null
				|| adminData.getListaEstados().isEmpty()) {
				adminData.setListaEstados(bsdAdminServ.obtenerEstadosConProcesosVigentes(adminData.getIdSistema(), 
																						adminData.getUsuario().getIdEstado(),
																						adminData.getUsuario().getRolUsuario()));
				if(adminData.getUsuario().getIdEstado() != 0) {
					adminData.setIdEstadoSeleccionado(adminData.getUsuario().getIdEstado());
					adminData.setDeshabilitaEstados(true);
					cambiaEstado(true);
				} else {
					cambiaMenuAcciones();
				}
				
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
			}
		} catch(Exception e) {
			logger.error("ERROR MBAdministradorSistema -cargaEstados: ", e);
		}
	}
	
	public void cambiaEstado(boolean isDefault) {

		try {
			adminData.setAmbitoCaptura("D");
			
			adminData.setEstadoSeleccionado(null);
			
			adminData.setListaProcesos(null);
			adminData.setIdDetalleProcesoSeleccionado(null);
			adminData.setProcesoSeleccionado(null);
			
			adminData.setListaParticipaciones(null);
			adminData.setIdParticipacionSeleccionada(null);
			adminData.setParticipacionSeleccionada(null);
			adminData.setDeshabilitaParticipaciones(false);
			
			adminData.setEstadoSeleccionado(adminData.getListaEstados()
													.stream()
													.filter(e -> e.getIdEstado().equals(adminData.getIdEstadoSeleccionado()))
													.findAny()
													.orElseThrow());
			
			adminData.setListaProcesos(bsdAdminServ.obtenerDetalleProcesosElectorales(adminData.getVigente(), 
																					adminData.getIdSistema(), 
																					adminData.getIdEstadoSeleccionado(), 
																					adminData.getUsuario().getIdDistrito(), 
																					adminData.getUsuario().getIdMunicipio(), 
																					adminData.getUsuario().getAmbitoUsuario()));
			
			if(adminData.getListaProcesos().size() == 1) {
				adminData.setIdDetalleProcesoSeleccionado(adminData.getListaProcesos().get(0).getIdDetalleProceso());
				cambiaProcesoElectoral(true);
			} else {
				cambiaMenuAcciones();
			}

			if (!isDefault) {
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBAdministradorSistema -cambiaEstado: ", e);
		}
	}

	public void cambiaProcesoElectoral(boolean isDefault) {
		try {
			adminData.setProcesoSeleccionado(null);
			
			adminData.setListaParticipaciones(null);
			adminData.setIdParticipacionSeleccionada(null);
			adminData.setParticipacionSeleccionada(null);
			adminData.setDeshabilitaParticipaciones(false);
			
			adminData.setProcesoSeleccionado(adminData.getListaProcesos()
													.stream()
													.filter(p -> p.getIdDetalleProceso().equals(adminData.getIdDetalleProcesoSeleccionado()))
													.findAny()
													.orElseThrow());
						
			adminData.setListaParticipaciones(adminData.getIdEstadoSeleccionado() != 0 ?
											bsdAdminServ.obtenerParticipacionesEstadoProceso(adminData.getIdDetalleProcesoSeleccionado(),
																							adminData.getIdEstadoSeleccionado(),
																							adminData.getUsuario().getIdDistrito())
											: Collections.emptyList());
			
			if (adminData.getUsuario().getIdDistrito() != 0) {
				adminData.setIdParticipacionSeleccionada(adminData.getUsuario().getIdDistrito());
				adminData.setDeshabilitaParticipaciones(true);
				cambiaDistrito(true);
			} else {
				cambiaMenuAcciones();
			}
			
			obtieneLetraMesSorteados();
			
			if (!isDefault) {
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBAdministradorSistema -cambiaProcesoElectoral: ", e);
		}
	}
	
	public void cambiaDistrito(boolean isDefault) {

		try {
			
			adminData.setParticipacionSeleccionada(null);
			
			Optional<DTOParticipacionGeneral> participacionSeleccionada = adminData.getListaParticipaciones()
																			.stream()
																			.filter(p -> p.getId().equals(adminData.getIdParticipacionSeleccionada()))
																			.findAny();
			
			if(participacionSeleccionada.isPresent()) {
				adminData.setParticipacionSeleccionada(participacionSeleccionada.get());
			}
			
			cambiaMenuAcciones();

			if (!isDefault) {
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBAdministradorSistema -cambiaDistrito: ", e);
		}
	}
	
	public void cambiaMenuAcciones() {
		String key = "";
		
		adminData.getListaModulos().clear();
		
		if(adminData.getProcesoSeleccionado() == null
			|| adminData.getProcesoSeleccionado().getIdProcesoElectoral() == null
			|| adminData.getProcesoSeleccionado().getIdProcesoElectoral().equals(0)
			|| adminData.getProcesoSeleccionado().getIdDetalleProceso() == null
			|| adminData.getProcesoSeleccionado().getIdDetalleProceso().equals(0))
			return;
		
		if(!adminData.getMenuLateralAcciones()
					.containsKey(adminData.getProcesoSeleccionado().getIdDetalleProceso())) {
			adminData.getMenuLateralAcciones()
					.put(adminData.getProcesoSeleccionado().getIdDetalleProceso(), 
						new HashMap<>());
			cargaMenuLateralAccionesGenerico();
		}
		
		if ((adminData.getIdEstadoSeleccionado() == null
				|| adminData.getIdEstadoSeleccionado() == 0)
			&& (adminData.getIdParticipacionSeleccionada() == null
				|| adminData.getIdParticipacionSeleccionada() == 0)) {
			key = "OC";
		} else if ((adminData.getIdEstadoSeleccionado() != null
					&& adminData.getIdEstadoSeleccionado() != 0)
				&& (adminData.getIdParticipacionSeleccionada() == null
					|| adminData.getIdParticipacionSeleccionada() == 0)) {
			key = "JL";
		} else if ((adminData.getIdEstadoSeleccionado() != null
				&& adminData.getIdEstadoSeleccionado() != 0)
			&& (adminData.getIdParticipacionSeleccionada() != null
				&& adminData.getIdParticipacionSeleccionada() != 0)) {
			key = "JD";
		}
		
		List<DTOMenu> menuSeleccionado = adminData.getMenuLateralAcciones()
											.get(adminData.getProcesoSeleccionado().getIdDetalleProceso())
											.get(key);
		
		if (menuSeleccionado == null
			|| menuSeleccionado.isEmpty()) {
			logger.error("No se obtuvieron datos del menú");
			return;
		}
		
		adminData.getListaModulos().addAll(menuSeleccionado
												.stream()
												.flatMap(menu -> menu.getSubMenus().stream())
												.flatMap(modulo -> modulo.getModulos().stream())
												.sorted(Comparator.comparingInt(DTOModulo::getIdModulo))
												.collect(Collectors.toList()));
	}

	private void cargaMenuLateralAccionesGenerico() {
		int entidad = 0;
		int participacion = 0;
		int peticion = 0;
		
		if (adminData.getUsuario().getNivelRol()
				.equalsIgnoreCase(Utilidades.mensajeProperties("constante_version_rol_oc"))) {
			peticion = 3;
		} else if (adminData.getUsuario().getNivelRol()
				.equalsIgnoreCase(Utilidades.mensajeProperties("constante_version_rol_jl"))) {
			entidad = adminData.getIdEstadoSeleccionado();
			peticion = 2;
		} else if (adminData.getUsuario().getNivelRol()
				.equalsIgnoreCase(Utilidades.mensajeProperties("constante_version_rol_jd"))) {
			peticion = 1;
			entidad = adminData.getIdEstadoSeleccionado();
			participacion = adminData.getIdParticipacionSeleccionada();
		}
		
		String key = "";
		List<DTOMenu> menu = null;
		for (int i = 1; i <= peticion; i++) {
			if (i == 1) {
				key = "JD";
				entidad = entidad != 0 ? entidad : 99;
				participacion = participacion != 0 ? participacion : 99;
			} else if (i == 2) {
				key = "JL";
				entidad = entidad != 0 ? entidad : 99;
				participacion = 0;
			} else if (i == 3) {
				key = "OC";
				entidad = 0;
				participacion = 0;
			}
	
			menu = bsdAdminServ.generaMenuLateral(
								adminData.getProcesoSeleccionado().getIdProcesoElectoral(),
								adminData.getProcesoSeleccionado().getIdDetalleProceso(),
								adminData.getIdSistema(), 
								entidad, 
								participacion, 
								adminData.getRolSistema(),
								adminData.getProcesoSeleccionado().getTipoCapturaSistema(),
								adminData.getProcesoSeleccionado().getPorSeccion());
			
			adminData.getMenuLateralAcciones()
					.get(adminData.getProcesoSeleccionado().getIdDetalleProceso())
					.put(key, menu);
		}
		
	}
	
	public boolean validaModuloAbierto(Integer idModulo, Integer accion) {
		
		adminData.setEstatusModulo(bsdAdminServ.validaModuloAbierto(
													adminData.getProcesoSeleccionado().getIdProcesoElectoral(),
													adminData.getProcesoSeleccionado().getIdDetalleProceso(),
													adminData.getIdEstadoSeleccionado(), 
													adminData.getIdParticipacionSeleccionada(),
													adminData.getIdSistema(),
													idModulo, 
													adminData.getRolSistema(),
													adminData.getAmbitoCaptura()));
		
		if (accion.intValue() == 1 || accion.intValue() == 3) {
			if (adminData.getEstatusModulo() != null 
				&& adminData.getEstatusModulo().trim().equals("A")) {
				return true;
			}
		} else if (accion.intValue() == 2
			&& (adminData.getEstatusModulo() != null
				&& (adminData.getEstatusModulo().trim().equals("A")
					|| adminData.getEstatusModulo().trim().equals("C")))) {
			return true;
		}

		return false;
	}
	
	public String verificaElementosMenu(boolean proceso, boolean entidad, boolean participacion) {
		
		if (entidad && (adminData.getEstadoSeleccionado() == null
				|| adminData.getEstadoSeleccionado().getIdEstado() == null
				|| adminData.getEstadoSeleccionado().getIdEstado().equals(0))) {
			return "Selecciona una Entidad, Proceso electoral y Junta Local o Distrital.";
		}
		if (proceso && (adminData.getProcesoSeleccionado() == null
						|| adminData.getProcesoSeleccionado().getIdProcesoElectoral() == null
						|| adminData.getProcesoSeleccionado().getIdProcesoElectoral().equals(0))) {
			return "Selecciona un Proceso electoral.";
		}
		if (participacion && (adminData.getParticipacionSeleccionada() == null
								|| adminData.getParticipacionSeleccionada().getId() == null
								|| adminData.getParticipacionSeleccionada().getId().equals(0))) {
			return "Selecciona una Junta.";
		}
		
		return "";
	}
	
	public String determinaHome() {
		try {
			
			if (adminData.getUsuario().getRolUsuario().equals("SCE.CONSULTA.OC")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CONSULTA.JL")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA.OC")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAU.OC")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CONSULTA_RESTRINGIDA.OC")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA.JL")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_CONSEJERO.JL")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_VE.JL")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_VECEYEC.JL")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_VCEYEC.JL")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_VERFE.JL")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_VRFE.JL")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_VOE.JL")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_VS.JL")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CONSULTA_RESTRINGIDA.JL")) {
				return "mapas_view";
			}

			if (adminData.getUsuario().getRolUsuario().equals("SCE.CONSULTA.JD")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA.JD")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_VERFE.JD")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_VRFE.JD")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_VOE.JD")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_VS.JD")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_CONSEJERO.JD")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CONSULTA_RESTRINGIDA.JD")) {
				return "reportes_view";
			}

			if (adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_VE.JD")
					|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_VECEYEC.JD")
						|| adminData.getUsuario().getRolUsuario().equals("SCE.CAPTURA_VCEYEC.JD")) {
				return "proceso_view";
			}
			
		} catch (Exception e) {
			logger.error("ERROR MBAdministradorSistema -determinaHome: ", e);
		}

		return "home_view";
	}
	
	private void obtieneLetraMesSorteados() {
		adminData.setLetraSorteada(null);
		adminData.setMesSorteado(null);
		
		try {
			
			String fechaEjecucionProceso = bsdAdminServ.obtenerParametro(adminData.getProcesoSeleccionado().getIdProcesoElectoral(), 
															adminData.getProcesoSeleccionado().getIdDetalleProceso(), 
															0, 
															0,
															Constantes.PARAMETRO_FECHA_EJECUCION_PROCESO);
			
			if (DateUtils.isSameDay(new SimpleDateFormat("dd/MM/yyyy").parse(fechaEjecucionProceso), new Date())) {
				adminData.setLetraSorteada(bsdAdminServ.obtenerParametro(adminData.getProcesoSeleccionado().getIdProcesoElectoral(), 
															adminData.getProcesoSeleccionado().getIdDetalleProceso(), 
															0, 
															0,
															Constantes.PARAMETRO_LETRA_A_INSACULAR));
				
				adminData.setMesSorteado(bsdAdminServ.obtenerParametro(adminData.getProcesoSeleccionado().getIdProcesoElectoral(), 
															adminData.getProcesoSeleccionado().getIdDetalleProceso(), 
															0, 
															0,
															Constantes.PARAMETRO_MES_A_INSACULAR));
			} else {
				adminData.setLetraSorteada(bsdAdminServ.obtenerParametro(adminData.getProcesoSeleccionado().getIdProcesoElectoral(), 
															adminData.getProcesoSeleccionado().getIdDetalleProceso(), 
															0, 
															0,
															Constantes.PARAMETRO_LETRA_A_INSACULAR_SIMULACRO));
				
				adminData.setMesSorteado(bsdAdminServ.obtenerParametro(adminData.getProcesoSeleccionado().getIdProcesoElectoral(), 
															adminData.getProcesoSeleccionado().getIdDetalleProceso(), 
															0, 
															0,
															Constantes.PARAMETRO_MES_A_INSACULAR_SIMULACRO));
			}
		} catch (Exception e) {
			logger.error("ERROR MBAdministradorSistema -obtieneLetraMesSorteados: ", e);
		}
		
		if(adminData.getLetraSorteada() == null) adminData.setLetraSorteada(".");
		if(adminData.getMesSorteado() == null) adminData.setMesSorteado("13");
	}
	
	public FormAdmin getAdminData() {
		return adminData;
	}
	
}
