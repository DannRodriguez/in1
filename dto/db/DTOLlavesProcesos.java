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
@Table(name = "LLAVES_PROCESOS", schema = "INSA1")
public class DTOLlavesProcesos implements Serializable {

	private static final long serialVersionUID = 2769077366921003558L;
	
	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@Column(name = "ID_PARTICIPACION", nullable = false, precision = 9, scale = 0)
	private Integer idParticipacion;
	
	@Id
	@Column(name = "MODO_EJECUCION", nullable = false, precision = 1, scale = 0)
	private Integer modoEjecucion;
	
	@Id
	@Column(name = "TIPO_LLAVE", nullable = false, precision = 1, scale = 0)
	private Integer tipoLlave;
	
	@Column(name = "RUTA_LLAVE", nullable = true, length = 255)
	private String rutaLlave;
	
	@Column(name = "SEMILLA", nullable = true, length = 4)
	private String semilla;
	
	@Column(name = "USUARIO", nullable = false, length = 50)
	private String usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = false)
	private Date fechaHora;

	public DTOLlavesProcesos() {
		
	}

	public DTOLlavesProcesos(Integer idProcesoElectoral, Integer idDetalleProceso, 
							Integer idParticipacion, Integer modoEjecucion, 
							Integer tipoLlave, String rutaLlave, String semilla) {
		super();
		this.idProcesoElectoral = idProcesoElectoral;
		this.idDetalleProceso = idDetalleProceso;
		this.idParticipacion = idParticipacion;
		this.modoEjecucion = modoEjecucion;
		this.tipoLlave = tipoLlave;
		this.rutaLlave = rutaLlave;
		this.semilla = semilla;
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
	
	public Integer getModoEjecucion() {
		return modoEjecucion;
	}

	public void setModoEjecucion(Integer modoEjecucion) {
		this.modoEjecucion = modoEjecucion;
	}
	
	public Integer getTipoLlave() {
		return tipoLlave;
	}

	public void setTipoLlave(Integer tipoLlave) {
		this.tipoLlave = tipoLlave;
	}
	
	public String getRutaLlave() {
		return rutaLlave;
	}

	public void setRutaLlave(String rutaLlave) {
		this.rutaLlave = rutaLlave;
	}
	
	public String getSemilla() {
		return semilla;
	}

	public void setSemilla(String semilla) {
		this.semilla = semilla;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDetalleProceso == null) ? 0 : idDetalleProceso.hashCode());
		result = prime * result + ((idParticipacion == null) ? 0 : idParticipacion.hashCode());
		result = prime * result + ((modoEjecucion == null) ? 0 : modoEjecucion.hashCode());
		result = prime * result + ((tipoLlave == null) ? 0 : tipoLlave.hashCode());
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
		DTOLlavesProcesos other = (DTOLlavesProcesos) obj;
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
		if (modoEjecucion == null) {
			if (other.modoEjecucion != null)
				return false;
		} else if (!modoEjecucion.equals(other.modoEjecucion))
			return false;
		if (tipoLlave == null) {
			if (other.tipoLlave != null)
				return false;
		} else if (!tipoLlave.equals(other.tipoLlave))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTOLlavesProcesos [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso
				+ ", idParticipacion=" + idParticipacion + ", modoEjecucion=" + modoEjecucion + ", tipoLlave="
				+ tipoLlave + ", rutaLlave=" + rutaLlave + ", semilla=" + semilla + ", usuario=" + usuario
				+ ", fechaHora=" + fechaHora + "]";
	}


}
