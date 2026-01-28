package mx.ine.procprimerinsa.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import mx.ine.procprimerinsa.util.Constantes;
import mx.ine.procprimerinsa.util.Utilidades;

public class DTOEstatusCargaPrimeraCapaDistrito implements Serializable, DTOCSVPrintableInterface {
	
	private static final long serialVersionUID = 3940120199525649939L;
	
	private Integer idProcesoElectoral;
	private Integer idDetalleProceso;
	private Integer idEstado;
	private Integer idGrupo;
	private String nombreEstado;
	private Integer idParticipacion;
	private Integer idDistrito;
	private String nombreDistrito;
	private Integer etapa;
	private String estatus;
	private Integer datosInsaculados;
	private Integer datosInsaculadosCapa1;
	private Integer resultados1aInsa;
	private Integer resultados1aInsaCapa1;
	private String usuario;
	private Date fechaHora;
	private Date nombreOrden;
	private Date nombreOrdenFinalizado;
	private String tiempoMarcadoNombreOrden;
	private Date reinicioYaEsInsaculado;
	private Date reinicioYaEsInsaculadoFinalizado;
	private String tiempoReinicioYaEsInsaculado;
	private Date areasSecciones;
	private Date seccionesCompartidas;
	private Date malReferenciados;
	private Date marcadoFinalizado;
	private String tiempoMarcadoARE;
	private Date ordenGeografico;
	private Date ordenLetraAlf;
	private Date ordenVisita;
	private Date ordenFinalizado;
	private String tiempoOrden;
	private Date borradoPrimeraCapa;
	private Date cargaDatosInsaculados;
	private Date cargaResultados1aInsa;
	private Date creacionReinicioSecuencias;
	private Date cargaFinalizada;
	private String tiempoCarga;
	
	public DTOEstatusCargaPrimeraCapaDistrito() {
		super();
	}

	public Integer getIdProcesoElectoral() {
		return idProcesoElectoral;
	}

	public void setIdProcesoElectoral(Integer idProcesoElectoral) {
		this.idProcesoElectoral = idProcesoElectoral;
	}

	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}

	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public Integer getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}

	public Integer getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}

	public String getNombreDistrito() {
		return nombreDistrito;
	}

	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}

	public Integer getEtapa() {
		return etapa;
	}

	public void setEtapa(Integer etapa) {
		this.etapa = etapa;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Integer getDatosInsaculados() {
		return datosInsaculados;
	}

	public void setDatosInsaculados(Integer datosInsaculados) {
		this.datosInsaculados = datosInsaculados;
	}

	public Integer getDatosInsaculadosCapa1() {
		return datosInsaculadosCapa1;
	}

	public void setDatosInsaculadosCapa1(Integer datosInsaculadosCapa1) {
		this.datosInsaculadosCapa1 = datosInsaculadosCapa1;
	}

	public Integer getResultados1aInsa() {
		return resultados1aInsa;
	}

	public void setResultados1aInsa(Integer resultados1aInsa) {
		this.resultados1aInsa = resultados1aInsa;
	}

	public Integer getResultados1aInsaCapa1() {
		return resultados1aInsaCapa1;
	}

	public void setResultados1aInsaCapa1(Integer resultados1aInsaCapa1) {
		this.resultados1aInsaCapa1 = resultados1aInsaCapa1;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Date getNombreOrden() {
		return nombreOrden;
	}

	public void setNombreOrden(Date nombreOrden) {
		this.nombreOrden = nombreOrden;
	}

	public Date getNombreOrdenFinalizado() {
		return nombreOrdenFinalizado;
	}

	public void setNombreOrdenFinalizado(Date nombreOrdenFinalizado) {
		this.nombreOrdenFinalizado = nombreOrdenFinalizado;
	}

	public String getTiempoMarcadoNombreOrden() {
		return tiempoMarcadoNombreOrden;
	}

	public void setTiempoMarcadoNombreOrden(String tiempoMarcadoNombreOrden) {
		this.tiempoMarcadoNombreOrden = tiempoMarcadoNombreOrden;
	}

	public Date getReinicioYaEsInsaculado() {
		return reinicioYaEsInsaculado;
	}

	public void setReinicioYaEsInsaculado(Date reinicioYaEsInsaculado) {
		this.reinicioYaEsInsaculado = reinicioYaEsInsaculado;
	}

	public Date getReinicioYaEsInsaculadoFinalizado() {
		return reinicioYaEsInsaculadoFinalizado;
	}

	public void setReinicioYaEsInsaculadoFinalizado(Date reinicioYaEsInsaculadoFinalizado) {
		this.reinicioYaEsInsaculadoFinalizado = reinicioYaEsInsaculadoFinalizado;
	}

	public String getTiempoReinicioYaEsInsaculado() {
		return tiempoReinicioYaEsInsaculado;
	}

	public void setTiempoReinicioYaEsInsaculado(String tiempoReinicioYaEsInsaculado) {
		this.tiempoReinicioYaEsInsaculado = tiempoReinicioYaEsInsaculado;
	}

	public Date getAreasSecciones() {
		return areasSecciones;
	}

	public void setAreasSecciones(Date areasSecciones) {
		this.areasSecciones = areasSecciones;
	}

	public Date getSeccionesCompartidas() {
		return seccionesCompartidas;
	}

	public void setSeccionesCompartidas(Date seccionesCompartidas) {
		this.seccionesCompartidas = seccionesCompartidas;
	}

	public Date getMalReferenciados() {
		return malReferenciados;
	}

	public void setMalReferenciados(Date malReferenciados) {
		this.malReferenciados = malReferenciados;
	}

	public Date getMarcadoFinalizado() {
		return marcadoFinalizado;
	}

	public void setMarcadoFinalizado(Date marcadoFinalizado) {
		this.marcadoFinalizado = marcadoFinalizado;
	}

	public String getTiempoMarcadoARE() {
		return tiempoMarcadoARE;
	}

	public void setTiempoMarcadoARE(String tiempoMarcadoARE) {
		this.tiempoMarcadoARE = tiempoMarcadoARE;
	}

	public Date getOrdenGeografico() {
		return ordenGeografico;
	}

	public void setOrdenGeografico(Date ordenGeografico) {
		this.ordenGeografico = ordenGeografico;
	}

	public Date getOrdenLetraAlf() {
		return ordenLetraAlf;
	}

	public void setOrdenLetraAlf(Date ordenLetraAlf) {
		this.ordenLetraAlf = ordenLetraAlf;
	}

	public Date getOrdenVisita() {
		return ordenVisita;
	}

	public void setOrdenVisita(Date ordenVisita) {
		this.ordenVisita = ordenVisita;
	}

	public Date getOrdenFinalizado() {
		return ordenFinalizado;
	}

	public void setOrdenFinalizado(Date ordenFinalizado) {
		this.ordenFinalizado = ordenFinalizado;
	}

	public String getTiempoOrden() {
		return tiempoOrden;
	}

	public void setTiempoOrden(String tiempoOrden) {
		this.tiempoOrden = tiempoOrden;
	}

	public Date getBorradoPrimeraCapa() {
		return borradoPrimeraCapa;
	}

	public void setBorradoPrimeraCapa(Date borradoPrimeraCapa) {
		this.borradoPrimeraCapa = borradoPrimeraCapa;
	}

	public Date getCargaDatosInsaculados() {
		return cargaDatosInsaculados;
	}

	public void setCargaDatosInsaculados(Date cargaDatosInsaculados) {
		this.cargaDatosInsaculados = cargaDatosInsaculados;
	}

	public Date getCargaResultados1aInsa() {
		return cargaResultados1aInsa;
	}

	public void setCargaResultados1aInsa(Date cargaResultados1aInsa) {
		this.cargaResultados1aInsa = cargaResultados1aInsa;
	}

	public Date getCreacionReinicioSecuencias() {
		return creacionReinicioSecuencias;
	}

	public void setCreacionReinicioSecuencias(Date creacionReinicioSecuencias) {
		this.creacionReinicioSecuencias = creacionReinicioSecuencias;
	}

	public Date getCargaFinalizada() {
		return cargaFinalizada;
	}

	public void setCargaFinalizada(Date cargaFinalizada) {
		this.cargaFinalizada = cargaFinalizada;
	}

	public String getTiempoCarga() {
		return tiempoCarga;
	}

	public void setTiempoCarga(String tiempoCarga) {
		this.tiempoCarga = tiempoCarga;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idDetalleProceso, idParticipacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOEstatusCargaPrimeraCapaDistrito other = (DTOEstatusCargaPrimeraCapaDistrito) obj;
		return Objects.equals(idDetalleProceso, other.idDetalleProceso)
				&& Objects.equals(idParticipacion, other.idParticipacion);
	}

	@Override
	public String toString() {
		return "DTOEstatusCargaPrimeraCapaDistrito [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso="
				+ idDetalleProceso + ", idEstado=" + idEstado + ", idGrupo=" + idGrupo + ", nombreEstado="
				+ nombreEstado + ", idParticipacion=" + idParticipacion + ", idDistrito=" + idDistrito
				+ ", nombreDistrito=" + nombreDistrito + ", etapa=" + etapa + ", estatus=" + estatus
				+ ", datosInsaculados=" + datosInsaculados + ", datosInsaculadosCapa1=" + datosInsaculadosCapa1
				+ ", resultados1aInsa=" + resultados1aInsa + ", resultados1aInsaCapa1=" + resultados1aInsaCapa1
				+ ", usuario=" + usuario + ", fechaHora=" + fechaHora + ", nombreOrden=" + nombreOrden
				+ ", nombreOrdenFinalizado=" + nombreOrdenFinalizado + ", tiempoMarcadoNombreOrden="
				+ tiempoMarcadoNombreOrden + ", reinicioYaEsInsaculado=" + reinicioYaEsInsaculado
				+ ", reinicioYaEsInsaculadoFinalizado=" + reinicioYaEsInsaculadoFinalizado
				+ ", tiempoReinicioYaEsInsaculado=" + tiempoReinicioYaEsInsaculado + ", areasSecciones="
				+ areasSecciones + ", seccionesCompartidas=" + seccionesCompartidas + ", malReferenciados="
				+ malReferenciados + ", marcadoFinalizado=" + marcadoFinalizado + ", tiempoMarcadoARE="
				+ tiempoMarcadoARE + ", ordenGeografico=" + ordenGeografico + ", ordenLetraAlf=" + ordenLetraAlf
				+ ", ordenVisita=" + ordenVisita + ", ordenFinalizado=" + ordenFinalizado + ", tiempoOrden="
				+ tiempoOrden + ", borradoPrimeraCapa=" + borradoPrimeraCapa + ", cargaDatosInsaculados="
				+ cargaDatosInsaculados + ", cargaResultados1aInsa=" + cargaResultados1aInsa
				+ ", creacionReinicioSecuencias=" + creacionReinicioSecuencias + ", cargaFinalizada=" + cargaFinalizada
				+ ", tiempoCarga=" + tiempoCarga + "]";
	}

	@Override
	public Object[] getCSVPrintable() {
		return new Object[] {
				idProcesoElectoral,
				idDetalleProceso,
				idGrupo,
				idEstado,
				nombreEstado,
				idParticipacion,
				idDistrito,
				nombreDistrito,
				etapa + " - " + Constantes.ETAPA_CARGA_PRIMERA_CAPA.get(etapa),
				estatus,
				Utilidades.formatDate(nombreOrden),
				Utilidades.formatDate(nombreOrdenFinalizado),
				tiempoMarcadoNombreOrden,
				Utilidades.formatDate(reinicioYaEsInsaculado),
				Utilidades.formatDate(reinicioYaEsInsaculadoFinalizado),
				tiempoReinicioYaEsInsaculado,
				Utilidades.formatDate(areasSecciones),
				Utilidades.formatDate(seccionesCompartidas),
				Utilidades.formatDate(malReferenciados),
				Utilidades.formatDate(marcadoFinalizado),
				tiempoMarcadoARE,
				Utilidades.formatDate(ordenGeografico),
				Utilidades.formatDate(ordenLetraAlf),
				Utilidades.formatDate(ordenVisita),
				Utilidades.formatDate(ordenFinalizado),
				tiempoOrden,
				Utilidades.formatDate(borradoPrimeraCapa),
				Utilidades.formatDate(cargaDatosInsaculados),
				Utilidades.formatDate(cargaResultados1aInsa),
				Utilidades.formatDate(creacionReinicioSecuencias),
				Utilidades.formatDate(cargaFinalizada),
				tiempoCarga,
				datosInsaculados,
				datosInsaculadosCapa1,
				resultados1aInsa,
				resultados1aInsaCapa1,
				usuario,
				Utilidades.formatDate(fechaHora)
		};
		
	}

}
