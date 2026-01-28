package mx.ine.procprimerinsa.form;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import mx.ine.procprimerinsa.dto.db.DTOArchivos;
import mx.ine.procprimerinsa.dto.db.DTOCTipoVoto;
import mx.ine.procprimerinsa.dto.db.DTOResultados1aInsa;

public class FormProcesoInsa implements Serializable{

	private static final long serialVersionUID = -8620779707344717509L;

	private Integer idProceso;
	private Integer idDetalle;
	private Integer idParticipacion;
	private Integer idEstado;
	private Integer idDistrito;
	
	private boolean procesoValido;
	
	private Integer idCorteLN;
	private Integer idCorteLNAActualizar;
	private Integer validaYaEsInsaculado;
	
	private String mesesYaSorteados;
	private Integer consideraExtraordinarias;
	
	private boolean advertenciaParametros;
	private Integer statusActual;
	private String pasoActual;
	private boolean navegacionPermitida;
	private boolean saltoActivo;
	private String tipoEjecucion;
	private boolean simulacro;
	private boolean permitirEjecucion;
	private boolean mostrarBotonInsaculacion;
	private boolean detenDespliegue;
	private boolean archivosGenerados;
	
	private String mensaje;
	private String mensajeNavegacion;
	
	private Integer validarLlaves;
	private boolean llavesValidas;
	private boolean fallaLlaves;
	private String llaveVE;
	private String llaveVCEyEC;
	
	private String nombreEstado;
	private String nombreParticipacion;
	
	private String letraSorteada;
	private Integer mesSorteado;
	private String mesNombre;
	
	private Integer tiempoPoll;
	private Integer tiempoPollEjecucion;
	private float porcentajeEjecucion;
	
	private Map<Integer, DTOCTipoVoto> mTipoVoto;
	private Map<Integer, List<DTOResultados1aInsa>> resultados;
	private Queue<Integer> tipoVotoDespliegue;
	private String descripcionTipoVotoDespliegue;
	private String leyendaCasillaDespliegue;
	private Integer contadorDespliegue;
	
	private Integer totalLNDistrito;
	private DTOResultados1aInsa totales;
	
	private Integer insaculadosSeccion;
	private Integer insaculadosTotales;
	
	private boolean ventanaExito;
	private boolean ventanaExitoFinal;
	
	private Map<String, DTOArchivos> archivos; 
	
	public FormProcesoInsa(){
		
		contadorDespliegue = -1;
		
		mensajeNavegacion = "Siguiente";
		detenDespliegue = false;
		saltoActivo = true;
		
		totalLNDistrito = 0;
		totales = new DTOResultados1aInsa();
		
		inicializaTotales();
	}
	
	public void inicializaTotales() {
		totales.setSeccion(null);
		totales.setTipoCasilla("-");
		totales.setListaNominal(0);
		totales.setEnero(0);
		totales.setFebrero(0);
		totales.setMarzo(0);
		totales.setAbril(0);
		totales.setMayo(0);
		totales.setJunio(0);
		totales.setJulio(0);
		totales.setAgosto(0);
		totales.setSeptiembre(0);
		totales.setOctubre(0);
		totales.setNoviembre(0);
		totales.setDiciembre(0);
		totales.setHombres(0);
		totales.setMujeres(0);
		totales.setNoBinario(0);
		insaculadosSeccion = 0;
		insaculadosTotales = 0;
		porcentajeEjecucion = 0;
	}

	public Integer getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Integer idProceso) {
		this.idProceso = idProceso;
	}

	public Integer getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Integer getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Integer getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}

	public boolean isProcesoValido() {
		return procesoValido;
	}

	public void setProcesoValido(boolean procesoValido) {
		this.procesoValido = procesoValido;
	}

	public Integer getIdCorteLN() {
		return idCorteLN;
	}

	public void setIdCorteLN(Integer idCorteLN) {
		this.idCorteLN = idCorteLN;
	}

	public Integer getIdCorteLNAActualizar() {
		return idCorteLNAActualizar;
	}

	public void setIdCorteLNAActualizar(Integer idCorteLNAActualizar) {
		this.idCorteLNAActualizar = idCorteLNAActualizar;
	}

	public Integer getValidaYaEsInsaculado() {
		return validaYaEsInsaculado;
	}

	public void setValidaYaEsInsaculado(Integer validaYaEsInsaculado) {
		this.validaYaEsInsaculado = validaYaEsInsaculado;
	}

	public String getMesesYaSorteados() {
		return mesesYaSorteados;
	}

	public void setMesesYaSorteados(String mesesYaSorteados) {
		this.mesesYaSorteados = mesesYaSorteados;
	}

	public Integer getConsideraExtraordinarias() {
		return consideraExtraordinarias;
	}

	public void setConsideraExtraordinarias(Integer consideraExtraordinarias) {
		this.consideraExtraordinarias = consideraExtraordinarias;
	}

	public boolean isAdvertenciaParametros() {
		return advertenciaParametros;
	}

	public void setAdvertenciaParametros(boolean advertenciaParametros) {
		this.advertenciaParametros = advertenciaParametros;
	}

	public Integer getStatusActual() {
		return statusActual;
	}

	public void setStatusActual(Integer statusActual) {
		this.statusActual = statusActual;
	}

	public String getPasoActual() {
		return pasoActual;
	}

	public void setPasoActual(String pasoActual) {
		this.pasoActual = pasoActual;
	}

	public boolean isNavegacionPermitida() {
		return navegacionPermitida;
	}

	public void setNavegacionPermitida(boolean navegacionPermitida) {
		this.navegacionPermitida = navegacionPermitida;
	}

	public boolean isSaltoActivo() {
		return saltoActivo;
	}

	public void setSaltoActivo(boolean saltoActivo) {
		this.saltoActivo = saltoActivo;
	}

	public String getTipoEjecucion() {
		return tipoEjecucion;
	}

	public void setTipoEjecucion(String tipoEjecucion) {
		this.tipoEjecucion = tipoEjecucion;
	}

	public boolean isSimulacro() {
		return simulacro;
	}

	public void setSimulacro(boolean simulacro) {
		this.simulacro = simulacro;
	}

	public boolean isPermitirEjecucion() {
		return permitirEjecucion;
	}

	public void setPermitirEjecucion(boolean permitirEjecucion) {
		this.permitirEjecucion = permitirEjecucion;
	}

	public boolean isMostrarBotonInsaculacion() {
		return mostrarBotonInsaculacion;
	}

	public void setMostrarBotonInsaculacion(boolean mostrarBotonInsaculacion) {
		this.mostrarBotonInsaculacion = mostrarBotonInsaculacion;
	}

	public boolean isDetenDespliegue() {
		return detenDespliegue;
	}

	public void setDetenDespliegue(boolean detenDespliegue) {
		this.detenDespliegue = detenDespliegue;
	}

	public boolean isArchivosGenerados() {
		return archivosGenerados;
	}

	public void setArchivosGenerados(boolean archivosGenerados) {
		this.archivosGenerados = archivosGenerados;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensajeNavegacion() {
		return mensajeNavegacion;
	}

	public void setMensajeNavegacion(String mensajeNavegacion) {
		this.mensajeNavegacion = mensajeNavegacion;
	}

	public Integer getValidarLlaves() {
		return validarLlaves;
	}

	public void setValidarLlaves(Integer validarLlaves) {
		this.validarLlaves = validarLlaves;
	}

	public boolean isLlavesValidas() {
		return llavesValidas;
	}

	public void setLlavesValidas(boolean llavesValidas) {
		this.llavesValidas = llavesValidas;
	}

	public boolean isFallaLlaves() {
		return fallaLlaves;
	}

	public void setFallaLlaves(boolean fallaLlaves) {
		this.fallaLlaves = fallaLlaves;
	}

	public String getLlaveVE() {
		return llaveVE;
	}

	public void setLlaveVE(String llaveVE) {
		this.llaveVE = llaveVE;
	}

	public String getLlaveVCEyEC() {
		return llaveVCEyEC;
	}

	public void setLlaveVCEyEC(String llaveVCEyEC) {
		this.llaveVCEyEC = llaveVCEyEC;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public String getNombreParticipacion() {
		return nombreParticipacion;
	}

	public void setNombreParticipacion(String nombreParticipacion) {
		this.nombreParticipacion = nombreParticipacion;
	}

	public String getLetraSorteada() {
		return letraSorteada;
	}

	public void setLetraSorteada(String letraSorteada) {
		this.letraSorteada = letraSorteada;
	}

	public Integer getMesSorteado() {
		return mesSorteado;
	}

	public void setMesSorteado(Integer mesSorteado) {
		this.mesSorteado = mesSorteado;
	}

	public String getMesNombre() {
		return mesNombre;
	}

	public void setMesNombre(String mesNombre) {
		this.mesNombre = mesNombre;
	}

	public Integer getTiempoPoll() {
		return tiempoPoll;
	}

	public void setTiempoPoll(Integer tiempoPoll) {
		this.tiempoPoll = tiempoPoll;
	}

	public Integer getTiempoPollEjecucion() {
		return tiempoPollEjecucion;
	}

	public void setTiempoPollEjecucion(Integer tiempoPollEjecucion) {
		this.tiempoPollEjecucion = tiempoPollEjecucion;
	}

	public float getPorcentajeEjecucion() {
		return porcentajeEjecucion;
	}

	public void setPorcentajeEjecucion(float porcentajeEjecucion) {
		this.porcentajeEjecucion = porcentajeEjecucion;
	}

	public Map<Integer, DTOCTipoVoto> getmTipoVoto() {
		return mTipoVoto;
	}

	public void setmTipoVoto(Map<Integer, DTOCTipoVoto> mTipoVoto) {
		this.mTipoVoto = mTipoVoto;
	}

	public Map<Integer, List<DTOResultados1aInsa>> getResultados() {
		return resultados;
	}

	public void setResultados(Map<Integer, List<DTOResultados1aInsa>> resultados) {
		this.resultados = resultados;
	}

	public Queue<Integer>getTipoVotoDespliegue() {
		return tipoVotoDespliegue;
	}

	public void setTipoVotoDespliegue(Queue<Integer> tipoVotoDespliegue) {
		this.tipoVotoDespliegue = tipoVotoDespliegue;
	}

	public String getDescripcionTipoVotoDespliegue() {
		return descripcionTipoVotoDespliegue;
	}

	public void setDescripcionTipoVotoDespliegue(String descripcionTipoVotoDespliegue) {
		this.descripcionTipoVotoDespliegue = descripcionTipoVotoDespliegue;
	}

	public String getLeyendaCasillaDespliegue() {
		return leyendaCasillaDespliegue;
	}

	public void setLeyendaCasillaDespliegue(String leyendaCasillaDespliegue) {
		this.leyendaCasillaDespliegue = leyendaCasillaDespliegue;
	}

	public Integer getContadorDespliegue() {
		return contadorDespliegue;
	}

	public void setContadorDespliegue(Integer contadorDespliegue) {
		this.contadorDespliegue = contadorDespliegue;
	}

	public Integer getTotalLNDistrito() {
		return totalLNDistrito;
	}

	public void setTotalLNDistrito(Integer totalLNDistrito) {
		this.totalLNDistrito = totalLNDistrito;
	}

	public DTOResultados1aInsa getTotales() {
		return totales;
	}

	public void setTotales(DTOResultados1aInsa totales) {
		this.totales = totales;
	}

	public Integer getInsaculadosSeccion() {
		return insaculadosSeccion;
	}

	public void setInsaculadosSeccion(Integer insaculadosSeccion) {
		this.insaculadosSeccion = insaculadosSeccion;
	}

	public Integer getInsaculadosTotales() {
		return insaculadosTotales;
	}

	public void setInsaculadosTotales(Integer insaculadosTotales) {
		this.insaculadosTotales = insaculadosTotales;
	}
	
	public boolean isVentanaExitoFinal() {
		return ventanaExitoFinal;
	}

	public void setVentanaExitoFinal(boolean ventanaExitoFinal) {
		this.ventanaExitoFinal = ventanaExitoFinal;
	}

	public boolean isVentanaExito() {
		return ventanaExito;
	}

	public void setVentanaExito(boolean ventanaExito) {
		this.ventanaExito = ventanaExito;
	}

	public Map<String, DTOArchivos> getArchivos() {
		return archivos;
	}

	public void setArchivos(Map<String, DTOArchivos> archivos) {
		this.archivos = archivos;
	}
	
}
