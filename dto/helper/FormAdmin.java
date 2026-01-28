package mx.ine.procprimerinsa.dto.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.ine.parametrizacion.model.dto.DTODetalleProceso;
import mx.ine.parametrizacion.model.dto.DTOEstado;
import mx.ine.parametrizacion.model.dto.DTOMenu;
import mx.ine.parametrizacion.model.dto.DTOModulo;
import mx.ine.procprimerinsa.dto.DTOParticipacionGeneral;
import mx.ine.procprimerinsa.dto.DTOUsuarioLogin;
import mx.ine.procprimerinsa.util.Constantes;

public class FormAdmin implements Serializable {

	private static final long serialVersionUID = 4818206983696041686L;
	
    private Integer idSistema;
    private String tipoSistema;
    
    private DTOUsuarioLogin usuario;
    private String rolSistema;
        
    private List<DTOEstado> listaEstados;
    private boolean deshabilitaEstados;
    private Integer idEstadoSeleccionado;
    private DTOEstado estadoSeleccionado;
    
    private List<DTODetalleProceso> listaProcesos;
    private Integer idDetalleProcesoSeleccionado;
    private DTODetalleProceso procesoSeleccionado;
    private String tipoProceso;
    private Integer anio;
    private String vigente;
    private String ambitoCaptura;
    
    private List<DTOParticipacionGeneral> listaParticipaciones;
    private boolean deshabilitaParticipaciones;
    private Integer idParticipacionSeleccionada;
    private DTOParticipacionGeneral participacionSeleccionada;
    
    private Map<Integer, Map<String, List<DTOMenu>>> menuLateralAcciones;
    
    private List<DTOModulo> listaModulos;
    private Integer idModulo;
    private String modulo;
    private String operacion;
    private Integer idAccion;
    private String estatusModulo;
    
    private String letraSorteada;
	private String mesSorteado;
	
    public FormAdmin() {
        super();
        menuLateralAcciones = new HashMap<>();
        listaModulos = new ArrayList<>();
    }
    
    public DTOUsuarioLogin getUsuario() {
        return usuario;
    }

    public void setUsuario(DTOUsuarioLogin usuario) {
        this.usuario = usuario;
    }

    public String getRolSistema() {
        return rolSistema;
    }

    public void setRolSistema(String rolSistema) {
        this.rolSistema = rolSistema;
    }

   public Integer getIdSistema() {
        return idSistema;
    }

   public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
   }

    public List<DTOModulo> getListaModulos() {
    	return listaModulos;
    }

    public void setListaModulos(List<DTOModulo> listaModulos) {
    	this.listaModulos = listaModulos;
    }

	public Integer getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Integer idModulo) {
        this.idModulo = idModulo;
    }

    public String getOperacion() {
        return operacion;
    }
    
    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getTipoSistema() {
        return tipoSistema;
    }

    public void setTipoSistema(String tipoSistema) {
        this.tipoSistema = tipoSistema;
    }

    public String getEstatusModulo() {
        return estatusModulo;
    }

    public void setEstatusModulo(String estatusModulo) {
        this.estatusModulo = estatusModulo;
    }

    public Integer getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(Integer idAccion) {
        this.idAccion = idAccion;
    }

    public List<DTODetalleProceso> getListaProcesos() {
        return listaProcesos;
    }

    public void setListaProcesos(List<DTODetalleProceso> listaProcesos) {
        this.listaProcesos = listaProcesos;
    }

    public List<DTOEstado> getListaEstados() {
        return listaEstados;
    }

    public void setListaEstados(List<DTOEstado> listaEstados) {
        this.listaEstados = listaEstados;
    }

    public List<DTOParticipacionGeneral> getListaParticipaciones() {
		return listaParticipaciones;
	}

	public void setListaParticipaciones(List<DTOParticipacionGeneral> listaParticipaciones) {
		this.listaParticipaciones = listaParticipaciones;
	}

    public DTODetalleProceso getProcesoSeleccionado() {
        return procesoSeleccionado;
    }

    public void setProcesoSeleccionado(DTODetalleProceso procesoSeleccionado) {
        this.procesoSeleccionado = procesoSeleccionado;
        tipoProceso = Constantes.TIPO_DE_PROCESO_ORDINARIO;
        if(procesoSeleccionado != null
        	&& procesoSeleccionado.getIdProcesoElectoral() != 0
        	&& procesoSeleccionado.getDescripcionDetalle() != null
        	&& procesoSeleccionado.getDescripcionDetalle().contains(Constantes.VERIFICA_TIPO_DE_PROCESO)) {
        	tipoProceso = Constantes.TIPO_DE_PROCESO_EXTRAORDINARIO;
        }
    }

    public String getTipoProceso() {
		return tipoProceso;
	}

    public DTOEstado getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(DTOEstado estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    
    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

    public String getAmbitoCaptura() {
        return ambitoCaptura;
    }

    public void setAmbitoCaptura(String ambitoCaptura) {
        this.ambitoCaptura = ambitoCaptura;
    }

    public DTOParticipacionGeneral getParticipacionSeleccionada() {
		return participacionSeleccionada;
	}

	public void setParticipacionSeleccionada(DTOParticipacionGeneral participacionSeleccionada) {
		this.participacionSeleccionada = participacionSeleccionada;
	}

    public Integer getIdDetalleProcesoSeleccionado() {
        return idDetalleProcesoSeleccionado;
    }

    public void setIdDetalleProcesoSeleccionado(Integer idDetalleProcesoSeleccionado) {
        this.idDetalleProcesoSeleccionado = idDetalleProcesoSeleccionado;
    }

    public Integer getIdEstadoSeleccionado() {
        return idEstadoSeleccionado;
    }

    public void setIdEstadoSeleccionado(Integer idEstadoSeleccionado) {
        this.idEstadoSeleccionado = idEstadoSeleccionado;
    }

	public Integer getIdParticipacionSeleccionada() {
		return idParticipacionSeleccionada;
	}

	public void setIdParticipacionSeleccionada(Integer idParticipacionSeleccionada) {
		this.idParticipacionSeleccionada = idParticipacionSeleccionada;
	}
	
	public boolean isDeshabilitaEstados() {
		return deshabilitaEstados;
	}

	public void setDeshabilitaEstados(boolean deshabilitaEstados) {
		this.deshabilitaEstados = deshabilitaEstados;
	}

	public boolean isDeshabilitaParticipaciones() {
		return deshabilitaParticipaciones;
	}

	public void setDeshabilitaParticipaciones(boolean deshabilitaParticipaciones) {
		this.deshabilitaParticipaciones = deshabilitaParticipaciones;
	}

	public Map<Integer, Map<String, List<DTOMenu>>> getMenuLateralAcciones() {
		return menuLateralAcciones;
	}

	public void setMenuLateralAcciones(Map<Integer, Map<String, List<DTOMenu>>> menuLateralAcciones) {
		this.menuLateralAcciones = menuLateralAcciones;
	}

	public String getLetraSorteada() {
		return letraSorteada;
	}

	public void setLetraSorteada(String letraSorteada) {
		this.letraSorteada = letraSorteada;
	}

	public String getMesSorteado() {
		return mesSorteado;
	}

	public void setMesSorteado(String mesSorteado) {
		this.mesSorteado = mesSorteado;
	}

	@Override
	public String toString() {
		return "FormAdmin [idSistema=" + idSistema + ", tipoSistema=" + tipoSistema + ", usuario=" + usuario
				+ ", rolSistema=" + rolSistema + ", listaEstados=" + listaEstados + ", deshabilitaEstados="
				+ deshabilitaEstados + ", idEstadoSeleccionado=" + idEstadoSeleccionado + ", estadoSeleccionado="
				+ estadoSeleccionado + ", listaProcesos=" + listaProcesos + ", idDetalleProcesoSeleccionado="
				+ idDetalleProcesoSeleccionado + ", procesoSeleccionado=" + procesoSeleccionado + ", tipoProceso="
				+ tipoProceso + ", anio=" + anio + ", vigente=" + vigente + ", ambitoCaptura=" + ambitoCaptura
				+ ", listaParticipaciones=" + listaParticipaciones + ", deshabilitaParticipaciones="
				+ deshabilitaParticipaciones + ", idParticipacionSeleccionada=" + idParticipacionSeleccionada
				+ ", participacionSeleccionada=" + participacionSeleccionada 
				+ ", menuLateralAcciones=" + menuLateralAcciones + ", listaModulos=" + listaModulos + ", modulo="
				+ modulo + ", idModulo=" + idModulo + ", operacion=" + operacion + ", idAccion=" + idAccion
				+ ", estatusModulo=" + estatusModulo + ", letraSorteada=" + letraSorteada + ", mesSorteado="
				+ mesSorteado + "]";
	}
	
}
