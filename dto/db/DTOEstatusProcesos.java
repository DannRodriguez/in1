package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "ESTATUS_PROCESOS", schema = "INSA1")
public class DTOEstatusProcesos implements Serializable {

	private static final long serialVersionUID = -7153494058114968035L;
	
	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@Column(name = "ID_PARTICIPACION", nullable = false, precision = 9, scale = 0)
	private Integer idParticipacion;
	
	@Column(name = "ID_ESTATUS_PROCESO", nullable = false, precision = 2, scale = 0)
	private Integer idEstatusProceso;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA_INICIO_PROCESO", nullable = true)
	private Date fechaHoraInicioProceso;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA_FIN_PROCESO", nullable = true)
	private Date fechaHoraFinProceso;
	
	@Column(name = "ID_REINICIO", nullable = false, precision = 4, scale = 0)
	private Integer idReinicio;
	
	@Column(name = "ID_EJECUCION", nullable = true, precision = 2, scale = 0)
	private Integer idEjecucion;
	
	@Column(name = "USUARIO", nullable = false, length = 50)
	private String usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = false)
	private Date fechaHora;
	
	@Column(name = "JOB_EXECUTION_ID", nullable = true, precision = 19, scale = 0)
	private Integer jobExecutionId;

	public DTOEstatusProcesos() {
	}

	public DTOEstatusProcesos(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
			Integer idEstatusProceso, Date fechaHoraInicioProceso, Date fechaHoraFinProceso, Integer idReinicio,
			Integer idEjecucion, String usuario, Date fechaHora) {
		super();
		this.idProcesoElectoral = idProcesoElectoral;
		this.idDetalleProceso = idDetalleProceso;
		this.idParticipacion = idParticipacion;
		this.idEstatusProceso = idEstatusProceso;
		this.fechaHoraInicioProceso = fechaHoraInicioProceso;
		this.fechaHoraFinProceso = fechaHoraFinProceso;
		this.idReinicio = idReinicio;
		this.idEjecucion = idEjecucion;
		this.usuario = usuario;
		this.fechaHora = fechaHora;
	}

	public DTOEstatusProcesos(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
			Integer idEstatusProceso, Date fechaHoraInicioProceso, Date fechaHoraFinProceso, Integer idReinicio,
			Integer idEjecucion, String usuario, Date fechaHora, Integer jobExecutionId) {
		super();
		this.idProcesoElectoral = idProcesoElectoral;
		this.idDetalleProceso = idDetalleProceso;
		this.idParticipacion = idParticipacion;
		this.idEstatusProceso = idEstatusProceso;
		this.fechaHoraInicioProceso = fechaHoraInicioProceso;
		this.fechaHoraFinProceso = fechaHoraFinProceso;
		this.idReinicio = idReinicio;
		this.idEjecucion = idEjecucion;
		this.usuario = usuario;
		this.fechaHora = fechaHora;
		this.jobExecutionId = jobExecutionId;
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
	
	public Integer getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}
	
	public Integer getIdEstatusProceso() {
		return idEstatusProceso;
	}

	public void setIdEstatusProceso(Integer idEstatusProceso) {
		this.idEstatusProceso = idEstatusProceso;
	}
	
	public Date getFechaHoraInicioProceso() {
		return fechaHoraInicioProceso;
	}

	public void setFechaHoraInicioProceso(Date fechaHoraInicioProceso) {
		this.fechaHoraInicioProceso = fechaHoraInicioProceso;
	}
	
	public Date getFechaHoraFinProceso() {
		return fechaHoraFinProceso;
	}

	public void setFechaHoraFinProceso(Date fechaHoraFinProceso) {
		this.fechaHoraFinProceso = fechaHoraFinProceso;
	}
	
	public Integer getIdReinicio() {
		return idReinicio;
	}

	public void setIdReinicio(Integer idReinicio) {
		this.idReinicio = idReinicio;
	}
	
	public Integer getIdEjecucion() {
		return idEjecucion;
	}

	public void setIdEjecucion(Integer idEjecucion) {
		this.idEjecucion = idEjecucion;
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
	
	public Integer getJobExecutionId() {
		return jobExecutionId;
	}

	public void setJobExecutionId(Integer jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDetalleProceso == null) ? 0 : idDetalleProceso.hashCode());
		result = prime * result + ((idParticipacion == null) ? 0 : idParticipacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOEstatusProcesos other = (DTOEstatusProcesos) obj;
		if (idDetalleProceso == null) {
			if (other.idDetalleProceso != null)
				return false;
		} else if (!idDetalleProceso.equals(other.idDetalleProceso))
			return false;
		if (idParticipacion == null) {
			if (other.idParticipacion != null)
				return false;
		} else if (!idParticipacion.equals(other.idParticipacion))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTOEstatusProcesos [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso
				+ ", idParticipacion=" + idParticipacion + ", idEstatusProceso=" + idEstatusProceso
				+ ", fechaHoraInicioProceso=" + fechaHoraInicioProceso + ", fechaHoraFinProceso=" + fechaHoraFinProceso
				+ ", idReinicio=" + idReinicio + ", idEjecucion=" + idEjecucion + ", usuario=" + usuario
				+ ", fechaHora=" + fechaHora + ", jobExecutionId=" + jobExecutionId + "]";
	}

}
