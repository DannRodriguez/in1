package mx.ine.procprimerinsa.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import mx.ine.procprimerinsa.dto.DTOAdministradorBatch;
import mx.ine.procprimerinsa.dto.db.DTOBatchJobExecution;
import mx.ine.procprimerinsa.dto.db.DTOCEstatusProceso;

public class FormAdministradorBatch implements Serializable {
	
	private static final long serialVersionUID = -7415993563532070635L;
			
	private boolean procesoValido;
	private String mensaje;
	
	private List<Integer> detalles;
	
	private Integer threadPoolSize;
	
	private Map<Integer, DTOCEstatusProceso> mEstatusProceso;
	
	private Integer tipoEjecucion;
	private List<DTOAdministradorBatch> listaJobs;
	private List<DTOAdministradorBatch> listaJobsFiltrada;
	private Queue<DTOAdministradorBatch> queueDistritosEjecutando;
	
	private DTOBatchJobExecution jobSeleccionado;
	
	private Integer idCorteLN;
	private String letraSorteada;
	private Integer mesSorteado;
	private String tipoProceso;
	private Integer idCorteLNAActualizar;
	private Integer validaYaEsInsaculado;
	private String mesesYaSorteados;
	
	private Integer consideraExtraordinarias;
	
	private Date fechaInicioBitacoraAcceso;

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

	public List<Integer> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<Integer> detalles) {
		this.detalles = detalles;
	}

	public Integer getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(Integer threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public Map<Integer, DTOCEstatusProceso> getmEstatusProceso() {
		return mEstatusProceso;
	}

	public void setmEstatusProceso(Map<Integer, DTOCEstatusProceso> mEstatusProceso) {
		this.mEstatusProceso = mEstatusProceso;
	}

	public Integer getTipoEjecucion() {
		return tipoEjecucion;
	}

	public void setTipoEjecucion(Integer tipoEjecucion) {
		this.tipoEjecucion = tipoEjecucion;
	}

	public List<DTOAdministradorBatch> getListaJobs() {
		return listaJobs;
	}

	public void setListaJobs(List<DTOAdministradorBatch> listaJobs) {
		this.listaJobs = listaJobs;
	}

	public List<DTOAdministradorBatch> getListaJobsFiltrada() {
		return listaJobsFiltrada;
	}

	public void setListaJobsFiltrada(List<DTOAdministradorBatch> listaJobsFiltrada) {
		this.listaJobsFiltrada = listaJobsFiltrada;
	}

	public Queue<DTOAdministradorBatch> getQueueDistritosEjecutando() {
		return queueDistritosEjecutando;
	}

	public void setQueueDistritosEjecutando(Queue<DTOAdministradorBatch> queueDistritosEjecutando) {
		this.queueDistritosEjecutando = queueDistritosEjecutando;
	}

	public DTOBatchJobExecution getJobSeleccionado() {
		return jobSeleccionado;
	}

	public void setJobSeleccionado(DTOBatchJobExecution jobSeleccionado) {
		this.jobSeleccionado = jobSeleccionado;
	}

	public Integer getIdCorteLN() {
		return idCorteLN;
	}

	public void setIdCorteLN(Integer idCorteLN) {
		this.idCorteLN = idCorteLN;
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

	public String getTipoProceso() {
		return tipoProceso;
	}

	public void setTipoProceso(String tipoProceso) {
		this.tipoProceso = tipoProceso;
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

	public Date getFechaInicioBitacoraAcceso() {
		return fechaInicioBitacoraAcceso;
	}

	public void setFechaInicioBitacoraAcceso(Date fechaInicioBitacoraAcceso) {
		this.fechaInicioBitacoraAcceso = fechaInicioBitacoraAcceso;
	}

	@Override
	public String toString() {
		return "FormAdministradorBatch [procesoValido=" + procesoValido + ", mensaje=" + mensaje + ", detalles="
				+ detalles + ", threadPoolSize=" + threadPoolSize + ", mEstatusProceso=" + mEstatusProceso
				+ ", tipoEjecucion=" + tipoEjecucion + ", listaJobs=" + listaJobs + ", listaJobsFiltrada="
				+ listaJobsFiltrada + ", queueDistritosEjecutando=" + queueDistritosEjecutando + ", jobSeleccionado="
				+ jobSeleccionado + ", idCorteLN=" + idCorteLN + ", letraSorteada=" + letraSorteada + ", mesSorteado="
				+ mesSorteado + ", tipoProceso=" + tipoProceso + ", idCorteLNAActualizar=" + idCorteLNAActualizar
				+ ", validaYaEsInsaculado=" + validaYaEsInsaculado + ", mesesYaSorteados=" + mesesYaSorteados
				+ ", consideraExtraordinarias=" + consideraExtraordinarias + ", fechaInicioBitacoraAcceso="
				+ fechaInicioBitacoraAcceso + "]";
	}
	
}
