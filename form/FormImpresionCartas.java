package mx.ine.procprimerinsa.form;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mx.ine.procprimerinsa.dto.DTOCombo;
import mx.ine.procprimerinsa.dto.DTOJuntaDistrital;
import mx.ine.procprimerinsa.dto.DTOSeccionesLocalidades;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;

public class FormImpresionCartas implements Serializable{
	
	private static final long serialVersionUID = 8276636907027460368L;
	
	private boolean procesoValido;
	private String mensaje;
	
	private Integer estatusProceso;
	private Map<Integer, DTOCTipoVoto> mapVotos;
	private Integer idTipoVoto;

	private List<DTOCombo> lARES;
	private List<DTOSeccionesLocalidades> lSecciones;
	private List<DTOCombo> lLocalidades;
	private List<Integer> lManzanas;
	private List<Integer> lOrdenVisita;
	
	private Integer filtro;
	private Integer filtroSeccion;
	private Integer filtroIndividual;
	private Integer idAreaResponsabilidad;
	private Integer ordenVisitaInicio;
	private Integer ordenVisitaFinal;
	private Integer seccion;
	private Integer idLocalidad;
	private Integer manzana;
	private String numeroCredencialElector;
	private Integer folio;
	private Integer ordenamiento;
	
	private DTOJuntaDistrital dtoJuntaDistrital;
	private Boolean firma;
    private Boolean logo;
    private Boolean domicilioJunta;
    private String telefonoJunta;
    private boolean existeFirma;
    private boolean existeDomicilioJunta;
    private String urlSistemasSesiones;
    
	private Integer tabActivo;
		
	public void inicia() {
		lARES = new LinkedList<>();
		lSecciones = new LinkedList<>();
		lLocalidades = new LinkedList<>();
		lManzanas = new LinkedList<>();
		lOrdenVisita = new LinkedList<>();
		
		filtro = 1;
		tabActivo = 0;
	}
	
	public boolean isProcesoValido() {
		return procesoValido;
	}

	public void setProcesoValido(boolean procesoValido) {
		this.procesoValido = procesoValido;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Integer getEstatusProceso() {
		return estatusProceso;
	}
	
	public void setEstatusProceso(Integer estatusProceso) {
		this.estatusProceso = estatusProceso;
	}

	public List<DTOCombo> getlARES() {
		return lARES;
	}

	public void setlARES(List<DTOCombo> lARES) {
		this.lARES = lARES;
	}

	public List<DTOSeccionesLocalidades> getlSecciones() {
		return lSecciones;
	}

	public void setlSecciones(List<DTOSeccionesLocalidades> lSecciones) {
		this.lSecciones = lSecciones;
	}

	public List<DTOCombo> getlLocalidades() {
		return lLocalidades;
	}

	public void setlLocalidades(List<DTOCombo> lLocalidades) {
		this.lLocalidades = lLocalidades;
	}

	public List<Integer> getlManzanas() {
		return lManzanas;
	}

	public void setlManzanas(List<Integer> lManzanas) {
		this.lManzanas = lManzanas;
	}

	public List<Integer> getlOrdenVisita() {
		return lOrdenVisita;
	}

	public void setlOrdenVisita(List<Integer> lOrdenVisita) {
		this.lOrdenVisita = lOrdenVisita;
	}

	public Integer getFiltro() {
		return filtro;
	}

	public void setFiltro(Integer filtro) {
		this.filtro = filtro;
	}

	public Integer getFiltroSeccion() {
		return filtroSeccion;
	}

	public void setFiltroSeccion(Integer filtroSeccion) {
		this.filtroSeccion = filtroSeccion;
	}

	public Integer getFiltroIndividual() {
		return filtroIndividual;
	}

	public void setFiltroIndividual(Integer filtroIndividual) {
		this.filtroIndividual = filtroIndividual;
	}

	public Integer getIdAreaResponsabilidad() {
		return idAreaResponsabilidad;
	}

	public void setIdAreaResponsabilidad(Integer idAreaResponsabilidad) {
		this.idAreaResponsabilidad = idAreaResponsabilidad;
	}

	public Integer getOrdenVisitaInicio() {
		return ordenVisitaInicio;
	}

	public void setOrdenVisitaInicio(Integer ordenVisitaInicio) {
		this.ordenVisitaInicio = ordenVisitaInicio;
	}

	public Integer getOrdenVisitaFinal() {
		return ordenVisitaFinal;
	}

	public void setOrdenVisitaFinal(Integer ordenVisitaFinal) {
		this.ordenVisitaFinal = ordenVisitaFinal;
	}

	public Integer getSeccion() {
		return seccion;
	}

	public void setSeccion(Integer seccion) {
		this.seccion = seccion;
	}

	public Integer getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(Integer idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public Integer getManzana() {
		return manzana;
	}

	public void setManzana(Integer manzana) {
		this.manzana = manzana;
	}

	public String getNumeroCredencialElector() {
		return numeroCredencialElector;
	}

	public void setNumeroCredencialElector(String numeroCredencialElector) {
		this.numeroCredencialElector = numeroCredencialElector;
	}

	public Integer getFolio() {
		return folio;
	}

	public void setFolio(Integer folio) {
		this.folio = folio;
	}

	public Integer getOrdenamiento() {
		return ordenamiento;
	}

	public void setOrdenamiento(Integer ordenamiento) {
		this.ordenamiento = ordenamiento;
	}

	public DTOJuntaDistrital getDtoJuntaDistrital() {
		return dtoJuntaDistrital;
	}

	public void setDtoJuntaDistrital(DTOJuntaDistrital dtoJuntaDistrital) {
		this.dtoJuntaDistrital = dtoJuntaDistrital;
	}

	public Boolean getFirma() {
		return firma;
	}

	public void setFirma(Boolean firma) {
		this.firma = firma;
	}

	public Boolean getLogo() {
		return logo;
	}

	public void setLogo(Boolean logo) {
		this.logo = logo;
	}

	public Boolean getDomicilioJunta() {
		return domicilioJunta;
	}

	public void setDomicilioJunta(Boolean domicilioJunta) {
		this.domicilioJunta = domicilioJunta;
	}

	public String getTelefonoJunta() {
		return telefonoJunta;
	}

	public void setTelefonoJunta(String telefonoJunta) {
		this.telefonoJunta = telefonoJunta;
	}

	public boolean getExisteFirma() {
		return existeFirma;
	}

	public void setExisteFirma(boolean existeFirma) {
		this.existeFirma = existeFirma;
	}

	public boolean getExisteDomicilioJunta() {
		return existeDomicilioJunta;
	}

	public void setExisteDomicilioJunta(boolean existeDomicilioJunta) {
		this.existeDomicilioJunta = existeDomicilioJunta;
	}

	public String getUrlSistemasSesiones() {
		return urlSistemasSesiones;
	}

	public void setUrlSistemasSesiones(String urlSistemasSesiones) {
		this.urlSistemasSesiones = urlSistemasSesiones;
	}

	public Integer getTabActivo() {
		return tabActivo;
	}

	public void setTabActivo(Integer tabActivo) {
		this.tabActivo = tabActivo;
	}

	public Integer getIdTipoVoto() {
		return idTipoVoto;
	}

	public void setIdTipoVoto(Integer idTipoVoto) {
		this.idTipoVoto = idTipoVoto;
	}

	public Map<Integer, DTOCTipoVoto> getMapVotos() {
		return mapVotos;
	}

	public void setMapVotos(Map<Integer, DTOCTipoVoto> mapVotos) {
		this.mapVotos = mapVotos;
	}

	@Override
	public String toString() {
		return "FormImpresionCartas [procesoValido=" + procesoValido + ", mensaje=" + mensaje + ", estatusProceso="
				+ estatusProceso + ", mapVotos=" + mapVotos + ", lARES=" + lARES
				+ ", lOrdenVisita=" + lOrdenVisita + ", lSecciones=" + lSecciones + ", lLocalidades=" + lLocalidades
				+ ", lManzanas=" + lManzanas + ", filtro=" + filtro + ", filtroSeccion=" + filtroSeccion
				+ ", filtroIndividual=" + filtroIndividual + ", idAreaResponsabilidad=" + idAreaResponsabilidad
				+ ", ordenVisitaInicio=" + ordenVisitaInicio + ", ordenVisitaFinal=" + ordenVisitaFinal + ", seccion="
				+ seccion + ", idLocalidad=" + idLocalidad + ", manzana=" + manzana + ", numeroCredencialElector="
				+ numeroCredencialElector + ", folio=" + folio + ", ordenamiento=" + ordenamiento
				+ ", dtoJuntaDistrital=" + dtoJuntaDistrital + ", firma=" + firma + ", logo=" + logo
				+ ", domicilioJunta=" + domicilioJunta + ", telefonoJunta=" + telefonoJunta + ", existeFirma="
				+ existeFirma + ", existeDomicilioJunta=" + existeDomicilioJunta + ", urlSistemasSesiones="
				+ urlSistemasSesiones + ", tabActivo=" + tabActivo + "]";
	}
     
}
