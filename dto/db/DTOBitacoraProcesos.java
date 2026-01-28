package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import mx.ine.procprimerinsa.dto.DTOCSVPrintableInterface;
import mx.ine.procprimerinsa.util.Utilidades;

@Entity
@Table(name = "BITACORA_PROCESOS", schema = "INSA1")
public class DTOBitacoraProcesos implements Serializable, DTOCSVPrintableInterface {

	private static final long serialVersionUID = 8031365099460762214L;
	
	@Id
	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@Column(name = "ID_PARTICIPACION", nullable = false, precision = 9, scale = 0)
	private Integer idParticipacion;
	
	@Id
	@Column(name = "ID_EJECUCION", nullable = false, precision = 2, scale = 0)
	private Integer idEjecucion;
	
	@Id
	@Column(name = "ID_REINICIO", nullable = false, precision = 4, scale = 0)
	private Integer idReinicio;
	
	@Id
	@Column(name = "ID_ESTATUS_PROCESO", nullable = false, precision = 2, scale = 0)
	private Integer idEstatusProceso;
	
	@Column(name = "USUARIO", nullable = false, length = 50)
	private String usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = false)
	private Date fechaHora;
	
	@Column(name = "JOB_EXECUTION_ID", nullable = true, precision = 19, scale = 0)
	private Integer jobExecutionId;
	
	@Id
	@Column(name = "ID_BITACORA_PROCESOS", nullable = false, precision = 9, scale = 0)
	private Integer idBitacoraProceso;

	public DTOBitacoraProcesos() {
	}

	public DTOBitacoraProcesos(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
			Integer idEjecucion, Integer idReinicio, Integer idEstatusProceso, String usuario, Date fechaHora) {
		super();
		this.idProcesoElectoral = idProcesoElectoral;
		this.idDetalleProceso = idDetalleProceso;
		this.idParticipacion = idParticipacion;
		this.idEjecucion = idEjecucion;
		this.idReinicio = idReinicio;
		this.idEstatusProceso = idEstatusProceso;
		this.usuario = usuario;
		this.fechaHora = fechaHora;
	}

	public DTOBitacoraProcesos(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
			Integer idEjecucion, Integer idReinicio, Integer idEstatusProceso, String usuario, Date fechaHora,
			Integer jobExecutionId) {
		super();
		this.idProcesoElectoral = idProcesoElectoral;
		this.idDetalleProceso = idDetalleProceso;
		this.idParticipacion = idParticipacion;
		this.idEjecucion = idEjecucion;
		this.idReinicio = idReinicio;
		this.idEstatusProceso = idEstatusProceso;
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
	
	public Integer getIdEjecucion() {
		return idEjecucion;
	}

	public void setIdEjecucion(Integer idEjecucion) {
		this.idEjecucion = idEjecucion;
	}
	
	public Integer getIdReinicio() {
		return idReinicio;
	}

	public void setIdReinicio(Integer idReinicio) {
		this.idReinicio = idReinicio;
	}
	
	public Integer getIdEstatusProceso() {
		return idEstatusProceso;
	}
	
	public void setIdEstatusProceso(Integer idEstatusProceso) {
		this.idEstatusProceso = idEstatusProceso;
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
	
	public Integer getIdBitacoraProceso() {
		return idBitacoraProceso;
	}
	
	public void setIdBitacoraProceso(Integer idBitacoraProceso) {
		this.idBitacoraProceso = idBitacoraProceso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idBitacoraProceso == null) ? 0 : idBitacoraProceso.hashCode());
		result = prime * result + ((idDetalleProceso == null) ? 0 : idDetalleProceso.hashCode());
		result = prime * result + ((idEjecucion == null) ? 0 : idEjecucion.hashCode());
		result = prime * result + ((idEstatusProceso == null) ? 0 : idEstatusProceso.hashCode());
		result = prime * result + ((idParticipacion == null) ? 0 : idParticipacion.hashCode());
		result = prime * result + ((idProcesoElectoral == null) ? 0 : idProcesoElectoral.hashCode());
		result = prime * result + ((idReinicio == null) ? 0 : idReinicio.hashCode());
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
		DTOBitacoraProcesos other = (DTOBitacoraProcesos) obj;
		if (idBitacoraProceso == null) {
			if (other.idBitacoraProceso != null)
				return false;
		} else if (!idBitacoraProceso.equals(other.idBitacoraProceso))
			return false;
		if (idDetalleProceso == null) {
			if (other.idDetalleProceso != null)
				return false;
		} else if (!idDetalleProceso.equals(other.idDetalleProceso))
			return false;
		if (idEjecucion == null) {
			if (other.idEjecucion != null)
				return false;
		} else if (!idEjecucion.equals(other.idEjecucion))
			return false;
		if (idEstatusProceso == null) {
			if (other.idEstatusProceso != null)
				return false;
		} else if (!idEstatusProceso.equals(other.idEstatusProceso))
			return false;
		if (idParticipacion == null) {
			if (other.idParticipacion != null)
				return false;
		} else if (!idParticipacion.equals(other.idParticipacion))
			return false;
		if (idProcesoElectoral == null) {
			if (other.idProcesoElectoral != null)
				return false;
		} else if (!idProcesoElectoral.equals(other.idProcesoElectoral))
			return false;
		if (idReinicio == null) {
			if (other.idReinicio != null)
				return false;
		} else if (!idReinicio.equals(other.idReinicio))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTOBitacoraProcesos [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso="
				+ idDetalleProceso + ", idParticipacion=" + idParticipacion + ", idEjecucion=" + idEjecucion
				+ ", idReinicio=" + idReinicio + ", idEstatusProceso=" + idEstatusProceso + ", usuario=" + usuario
				+ ", fechaHora=" + fechaHora + ", jobExecutionId=" + jobExecutionId + ", idBitacoraProceso="
				+ idBitacoraProceso + "]";
	}
	
	@Override
	public Object[] getCSVPrintable() {
		return new Object[] {
				idProcesoElectoral,
				idDetalleProceso,
				idParticipacion,
				idEjecucion,
				idReinicio,
				idEstatusProceso,
				usuario,
				Utilidades.formatDate(fechaHora),
				jobExecutionId,
				idBitacoraProceso
		};
	}

}
