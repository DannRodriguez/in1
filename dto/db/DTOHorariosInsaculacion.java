package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "HORARIOS_INSACULACION", schema = "INSA1")
public class DTOHorariosInsaculacion implements Serializable {

	private static final long serialVersionUID = 5769381911391055379L;
	
	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@NotNull
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@NotNull
	@Column(name = "ID_ESTADO", nullable = false, precision = 2, scale = 0)
	private Integer idEstado;
	
	@Id
	@NotNull
	@Column(name = "ID_HORARIO", nullable = false, precision = 9, scale = 0)
	private Integer idHorario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HORARIO_EJECUCION", nullable = false)
	private Date horarioEjecucion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HORARIO_FIN_EJECUCION", nullable = true)
	private Date horarioFinEjecucion;
	
	@Column(name = "USUARIO", nullable = false, length = 50)
	private String usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = false)
	private Date fechaHora;

	public DTOHorariosInsaculacion() {
	}

	public DTOHorariosInsaculacion(Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idEstado, Integer idHorario, Date horarioEjecucion) {
		super();
		this.idProcesoElectoral = idProcesoElectoral;
		this.idDetalleProceso = idDetalleProceso;
		this.idEstado = idEstado;
		this.idHorario = idHorario;
		this.horarioEjecucion = horarioEjecucion;
	}
	
	public Integer getIdProcesoElectoral() {
		return this.idProcesoElectoral;
	}

	public void setIdProcesoElectoral(Integer idProcesoElectoral) {
		this.idProcesoElectoral = idProcesoElectoral;
	}
	
	public Integer getIdDetalleProceso() {
		return this.idDetalleProceso;
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
	
	public Integer getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(Integer idHorario) {
		this.idHorario = idHorario;
	}
	
	public Date getHorarioEjecucion() {
		return horarioEjecucion;
	}

	public void setHorarioEjecucion(Date horarioEjecucion) {
		this.horarioEjecucion = horarioEjecucion;
	}
	
	public Date getHorarioFinEjecucion() {
		return horarioFinEjecucion;
	}

	public void setHorarioFinEjecucion(Date horarioFinEjecucion) {
		this.horarioFinEjecucion = horarioFinEjecucion;
	}
	
	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public Date getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDetalleProceso == null) ? 0 : idDetalleProceso.hashCode());
		result = prime * result + ((idEstado == null) ? 0 : idEstado.hashCode());
		result = prime * result + ((idHorario == null) ? 0 : idHorario.hashCode());
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
		DTOHorariosInsaculacion other = (DTOHorariosInsaculacion) obj;
		if (idDetalleProceso == null) {
			if (other.idDetalleProceso != null)
				return false;
		} else if (!idDetalleProceso.equals(other.idDetalleProceso))
			return false;
		if (idEstado == null) {
			if (other.idEstado != null)
				return false;
		} else if (!idEstado.equals(other.idEstado))
			return false;
		if (idHorario == null) {
			if (other.idHorario != null)
				return false;
		} else if (!idHorario.equals(other.idHorario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTOHorariosInsaculacion [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso="
				+ idDetalleProceso + ", idEstado=" + idEstado + ", idHorario=" + idHorario + ", horarioEjecucion="
				+ horarioEjecucion + ", horarioFinEjecucion=" + horarioFinEjecucion + ", usuario=" + usuario
				+ ", fechaHora=" + fechaHora + "]";
	}

}
