package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DIRECCIONES_JUNTAS", schema = "PRIMERA_CAPA")
public class DTODireccionesJuntas implements Serializable {
	
	private static final long serialVersionUID = -6038738479596423470L;

	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false)
	private Integer idProceso;
	
	@Id
	@Column(name = "ID_DETALLE_PROCESO", nullable = false)
	private Integer idDetalle;
	
	@Id
	@Column(name = "ID_ESTADO", nullable = false)
	private Integer idEstado;

	@Id
	@Column(name = "ID_DISTRITO")
	private Integer idDistrito;
	 
	@Column(name = "ID_MUNICIPIO")
	private Integer idMunicipio;
	 
	@Column(name = "DIRECCION")
	private String direccion;
	
	@Column(name = "FECHA_HORA")
	private Date fechaHora;

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

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	@Override
	public String toString() {
		return "DTODireccionesJuntas [idProceso=" + idProceso + ", idDetalle=" + idDetalle + ", idEstado=" + idEstado
				+ ", idDistrito=" + idDistrito + ", idMunicipio=" + idMunicipio + ", direccion=" + direccion
				+ ", fechaHora=" + fechaHora + "]";
	}

}
