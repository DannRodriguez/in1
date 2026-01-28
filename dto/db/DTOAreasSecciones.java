package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "AREAS_SECCIONES", schema = "INSA1")
public class DTOAreasSecciones implements Serializable {
	
	private static final long serialVersionUID = 6894602954467051752L;

	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@Column(name = "ID_PARTICIPACION", nullable = false, precision = 9, scale = 0)
	private Integer idParticipacion;
	
	@Id
	@Column(name = "ID_AREA_RESPONSABILIDAD", nullable = false, precision = 4, scale = 0)
	private Integer idAreaResponsabilidad;
	
	@Column(name = "ID_TIPO_VOTO", nullable = false, precision = 2, scale = 0)
	private Integer idTipoVoto;
	
	@Id
	@Column(name = "SECCION", nullable = false, precision = 4, scale = 0)
	private Integer seccion;
	
	@Column(name = "USUARIO", nullable = true, length = 50)
	private String usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = true)
	private Date fechaHora;

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

	public Integer getIdAreaResponsabilidad() {
		return idAreaResponsabilidad;
	}

	public void setIdAreaResponsabilidad(Integer idAreaResponsabilidad) {
		this.idAreaResponsabilidad = idAreaResponsabilidad;
	}

	public Integer getIdTipoVoto() {
		return idTipoVoto;
	}

	public void setIdTipoVoto(Integer idTipoVoto) {
		this.idTipoVoto = idTipoVoto;
	}

	public Integer getSeccion() {
		return seccion;
	}

	public void setSeccion(Integer seccion) {
		this.seccion = seccion;
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
		return Objects.hash(idAreaResponsabilidad, idDetalleProceso, idParticipacion, seccion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOAreasSecciones other = (DTOAreasSecciones) obj;
		return Objects.equals(idAreaResponsabilidad, other.idAreaResponsabilidad)
				&& Objects.equals(idDetalleProceso, other.idDetalleProceso)
				&& Objects.equals(idParticipacion, other.idParticipacion) && Objects.equals(seccion, other.seccion);
	}

	@Override
	public String toString() {
		return "DTOAreasSecciones [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso
				+ ", idParticipacion=" + idParticipacion + ", idAreaResponsabilidad=" + idAreaResponsabilidad
				+ ", idTipoVoto=" + idTipoVoto + ", seccion=" + seccion + ", usuario=" + usuario + ", fechaHora="
				+ fechaHora + "]";
	}
	
}
