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
@Table(name = "ESTATUS_DATOS_PERSONALES", schema = "INSA1")
public class DTOEstatusDatosPersonales implements Serializable {
	
	private static final long serialVersionUID = -4998577626105084877L;

	@Id
	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@Column(name = "ID_ESTADO", nullable = false, precision = 2, scale = 0)
	private Integer idEstado;
	
	@Column(name = "ID_ACCION", nullable = false, length = 1)
	private Character idAccion;
	
	@Column(name = "ESTATUS", nullable = true, length = 2000)
	private String estatus;
	
	@Column(name = "CIUDADANOS_DESCARGA", nullable = true, length = 11)
	private Integer ciudadanosDescarga;
	
	@Column(name = "CIUDADANOS_CARGA", nullable = true, length = 11)
	private Integer ciudadanosCarga;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = false)
	private Date fechaHora;
	
	@Column(name = "USUARIO", nullable = false, length = 50)
	private String usuario;

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

	public Character getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(Character idAccion) {
		this.idAccion = idAccion;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Integer getCiudadanosDescarga() {
		return ciudadanosDescarga;
	}

	public void setCiudadanosDescarga(Integer ciudadanosDescarga) {
		this.ciudadanosDescarga = ciudadanosDescarga;
	}

	public Integer getCiudadanosCarga() {
		return ciudadanosCarga;
	}

	public void setCiudadanosCarga(Integer ciudadanosCarga) {
		this.ciudadanosCarga = ciudadanosCarga;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "DTOEstatusDatosPersonales [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso="
				+ idDetalleProceso + ", idEstado=" + idEstado + ", idAccion=" + idAccion + ", estatus=" + estatus
				+ ", ciudadanosDescarga=" + ciudadanosDescarga + ", ciudadanosCarga=" + ciudadanosCarga + ", fechaHora="
				+ fechaHora + ", usuario=" + usuario + "]";
	}
	
}
