package mx.ine.procprimerinsa.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;

import mx.ine.procprimerinsa.as.ASParametrosInterface;
import mx.ine.procprimerinsa.as.ASWSRegistraBitacoraInterface;
import mx.ine.procprimerinsa.bsd.BSDMapasInterface;
import mx.ine.procprimerinsa.dto.DTOEstado;
import mx.ine.procprimerinsa.dto.DTOEstatusParticipacion;
import mx.ine.procprimerinsa.dto.DTOMapaInformacion;
import mx.ine.procprimerinsa.dto.db.DTOCEstatusProceso;
import mx.ine.procprimerinsa.dto.db.DTOCategoriasProceso;
import mx.ine.procprimerinsa.dto.db.DTOHorariosInsaculacion;
import mx.ine.procprimerinsa.form.FormMapas;
import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

import org.jboss.logging.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier("mbMapas")
@RequestScoped
public class MBMapas implements Serializable {
	
	private static final long serialVersionUID = -3941419136305827061L;
	private static final Logger logger = Logger.getLogger(MBMapas.class);
	
	@Autowired
	@Qualifier("mbAdmin")
	private MBAdministradorSistema mbAdmin;
	
	@Autowired
	@Qualifier("bsdMapas")
	private BSDMapasInterface bsdMapas;

	@Autowired
	@Qualifier("asParametros")
	private ASParametrosInterface asParametros;
	
	@Autowired
	@Qualifier("wsRegistraBitacora")
	private transient ASWSRegistraBitacoraInterface wsRegistraBitacora;
	
	private FormMapas formMapas;
	
	private Map<Integer, DTOCEstatusProceso> listaEstatus;
	private List<DTOCategoriasProceso> listaCategorias;
	private List<DTOMapaInformacion> listaMapaInformacion;
	private List<DTOMapaInformacion> listaMapaInformacionFiltrada;
	private List<Integer> detalles;
	private Map<Integer, DTOEstado> estados;

	private int tabViewIndex;
	
	public void inicia() {
		try {
			
			formMapas = new FormMapas();
			
			String menuValido = mbAdmin.verificaElementosMenu(true, false, false);
			
			if (!menuValido.isEmpty()) {
				formMapas.setProcesoValido(false);
				formMapas.setMensaje(menuValido);
				return;
			}
			
			
			wsRegistraBitacora.solicitaRegistro(mbAdmin.getAdminData().getUsuario(), 
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(),
											mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
											Constantes.SERVICIO_BITACORA_MAPA,
											Constantes.EJECUTA);
			
			if(!bsdMapas.obtenerParametros(formMapas, 
									mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
									mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
									0, 
									0)) {
				formMapas.setProcesoValido(false);
				formMapas.setMensaje("Los parámetros del módulo no se encuentran asignados");
				return;
			}
			
			detalles = bsdMapas.getDetallesProceso(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
												mbAdmin.getAdminData().getTipoProceso());
			
			formMapas.setIdProceso(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral());
			formMapas.setIdDetalle(mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso());
			formMapas.setNombreProceso(mbAdmin.getAdminData().getProcesoSeleccionado().getNombreProceso());
			
			listaCategorias = bsdMapas.getListaCategorias(mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso());
			listaEstatus = bsdMapas.convetirCategoriasToEstatus(listaCategorias);
			
			estados = bsdMapas.getEstados(detalles, 
										mbAdmin.getAdminData().getProcesoSeleccionado().getCorte());
			
			List<DTOHorariosInsaculacion> horarios = bsdMapas.obtenerHorarios(detalles);
			formMapas.setGrupos(bsdMapas.getGrupos(horarios, 
												estados));
			formMapas.setProcesoValido(true);
			
		} catch (Exception e) {
			formMapas.setProcesoValido(false);
			formMapas.setMensaje(Utilidades.mensajeProperties("validacion_mensajes_generales_error_iniciar_modulo"));
			logger.error("ERROR MBMapas -inicia: ", e);
		}
	}
	
	public String getTipoMapa() {
		
		if(!formMapas.isProcesoValido()) {
    		formMapas.setParametro("3");
    		formMapas.setMapaActual("3");
        	return "3";
    	} 
		
		try{

			Integer idEstadoUsuario = (mbAdmin.getAdminData().getUsuario().getIdEstado().equals(0)? 
					mbAdmin.getAdminData().getIdEstadoSeleccionado() 
					: mbAdmin.getAdminData().getUsuario().getIdEstado());

			if(idEstadoUsuario != null && idEstadoUsuario > 0) {
				formMapas.setParametro("2");
			} else {
				formMapas.setParametro(asParametros.obtenerParametro(mbAdmin.getAdminData().getProcesoSeleccionado().getIdProcesoElectoral(), 
														mbAdmin.getAdminData().getProcesoSeleccionado().getIdDetalleProceso(),
														0, 
														0, 
														Constantes.PARAMETRO_TIPO_DE_MAPA_A_MOSTRAR));
			}
		} catch(Exception e){
			logger.error("ERROR MBMapaMapas -getTipoMapa: ", e);
			formMapas.setParametro("0");
		}
		
    	formMapas.setMapaActual(formMapas.getParametro());
    	return formMapas.getParametro();
	}
	
	public void iniciaMapa(Integer mapaSeleccionado) {
			
		if(!formMapas.isProcesoValido()){
			return;
		} 
		
		try{
			formMapas.setMapaActual(String.valueOf(mapaSeleccionado));
			tabViewIndex = 0;
			
			if(mapaSeleccionado.equals(2) && formMapas.getParametro().equals("2")) {
				if(formMapas.getIdEstado() == null) {
					formMapas.setIdEstado(mbAdmin.getAdminData().getIdEstadoSeleccionado());
				}
				formMapas.setBaseGrafica(bsdMapas.getBaseGrafica(formMapas.getIdEstado()));
				formMapas.setNombreEstado(estados.containsKey(formMapas.getIdEstado()) ? 
									estados.get(formMapas.getIdEstado()).getNombreEstado()
									: " ");
			} else {
				formMapas.setBaseGrafica(bsdMapas.getBaseGrafica(null));
			}
			
			formMapas.setEstatusSelecionado(null);
			
			formMapas.setMostrarCategorias(true);
			
			verificaEstatus(mapaSeleccionado.equals(1));
			
		} catch (Exception e){
			logger.error("ERROR MBMapas -iniciaPantalla: ", e);
		}
	}
	
	public void verificaEstatus(boolean verificaGrupos) {
    	obtenDatosMapas();
    	formMapas.setEstatusTabla(bsdMapas.obtenerEstadisticasParticipacion(listaMapaInformacion,
															    			listaEstatus));
    	if(verificaGrupos) {
    		formMapas.setGrupos(bsdMapas.calcularPorcentajeGrupalPorEstatus(listaMapaInformacion, 
																			7,
																			formMapas.getGrupos()));
    	}
    }
	
	public void obtenDatosMapas() {
		try {
			List<Integer> estadosM = new ArrayList<>();
    		
    		if(formMapas.getMapaActual().equals("2")) {
    			estadosM.add(formMapas.getIdEstado());
    		} else if(formMapas.getMapaActual().equals("1")) {
    			estadosM.addAll(formMapas.getGrupos().get(tabViewIndex).getEstados().keySet());    		
    		}
			
			listaMapaInformacion = bsdMapas.obtenerDatosMapasBD(estadosM,
															detalles,
															mbAdmin.getAdminData().getProcesoSeleccionado().getCorte());
			

			filtraMapaInformacion();
    	} catch(Exception e) {
    		logger.error("ERROR MBMapas -obtenDatosMapa: ", e);
    	}
	}
	
	private void filtraMapaInformacion() {
    	
    	if(formMapas.getEstatusSelecionado() != null) {
    		listaMapaInformacionFiltrada = listaMapaInformacion.stream()
      		      .filter(participacion -> participacion.getEstatus().equals(formMapas.getEstatusSelecionado().getIdEstatusProceso()))
      		      .collect(Collectors.toList());
		} else {
			listaMapaInformacionFiltrada = listaMapaInformacion;
		}
    	
    }
	
	public void onRowSelect(SelectEvent<DTOEstatusParticipacion> event) {
    	formMapas.setEstatusSelecionado(event.getObject());
    	filtraMapaInformacion();
    }
	
	public void onRowUnselect() {
		formMapas.setEstatusSelecionado(null);
	 	listaMapaInformacionFiltrada = listaMapaInformacion;
	 	PrimeFaces.current().executeScript("PF('stest').unselectAllRows()");
	}
	
	public String cambiaTipoMapa(Integer idEstado, String nombreEstado) {
		formMapas.setIdEstado(idEstado);
		formMapas.setNombreEstado(nombreEstado);
		return "2";
	}
	
	public String getEncabezado(int index) {
		return "encabezado"+(index - 1);
	}
	
	public void onTabChange(TabChangeEvent<EventObject> event) {
	   	 this.tabViewIndex = ((TabView) event.getSource()).getActiveIndex();
	   	 verificaEstatus(true);
    }

	public FormMapas getFormMapas() {
		return formMapas;
	}

	public Map<Integer, DTOCEstatusProceso> getListaEstatus() {
		return listaEstatus;
	}

	public List<DTOCategoriasProceso> getListaCategorias() {
		return listaCategorias;
	}

	public List<DTOMapaInformacion> getListaMapaInformacion() {
		return listaMapaInformacion;
	}

	public List<DTOMapaInformacion> getListaMapaInformacionFiltrada() {
		return listaMapaInformacionFiltrada;
	}

	public Map<Integer, DTOEstado> getEstados() {
		return estados;
	}

	public int getTabViewIndex() {
		return tabViewIndex;
	}

	public void setTabViewIndex(int tabViewIndex) {
		this.tabViewIndex = tabViewIndex;
	}
	
}
