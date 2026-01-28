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
import jakarta.persistence.Transient;

@Entity
@Table(name = "ORDENADA", schema = "INSA1")
public class DTOOrdenadaP implements Serializable {
	
	private static final long serialVersionUID = 5425380751164511993L;

	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@Column(name = "ID_PARTICIPACION", nullable = false, precision = 9, scale = 0)
	private Integer idParticipacion;
	
	@Id
	@Column(name = "ID_MUNICIPIO", nullable = false, precision = 3, scale = 0)
	private Integer idMunicipio;
	
	@Id
	@Column(name = "SECCION", nullable = false, precision = 4, scale = 0)
	private Integer seccion;
	
	@Id
	@Column(name = "ID_LOCALIDAD", nullable = false, precision = 5, scale = 0)
	private Integer idLocalidad;
	
	@Id
	@Column(name = "MANZANA", nullable = false, precision = 4, scale = 0)
	private Integer manzana;
	
	@Column(name = "ORDEN", nullable = false, precision = 4, scale = 0)
	private Integer orden;
	
	@Column(name = "USUARIO", nullable = true, length = 50)
	private String usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = true)
	private Date fechaHora;
	
	@Transient
	private Integer idEstado;
	
	@Transient
	private Integer idDistrito;

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

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Integer getSeccion() {
		return seccion;
	}

	public void setSeccion(Integer seccion) {
		this.seccion = seccion;
	}

	public Integer getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(Integer idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public Integer getManzana() {
		return manzana;
	}

	public void setManzana(Integer manzana) {
		this.manzana = manzana;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
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

	@Override
	public int hashCode() {
		return Objects.hash(idDetalleProceso, idLocalidad, idMunicipio, idParticipacion, manzana, seccion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOOrdenadaP other = (DTOOrdenadaP) obj;
		return Objects.equals(idDetalleProceso, other.idDetalleProceso)
				&& Objects.equals(idLocalidad, other.idLocalidad) && Objects.equals(idMunicipio, other.idMunicipio)
				&& Objects.equals(idParticipacion, other.idParticipacion) && Objects.equals(manzana, other.manzana)
				&& Objects.equals(seccion, other.seccion);
	}

	@Override
	public String toString() {
		return "DTOOrdenadaP [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso
				+ ", idParticipacion=" + idParticipacion + ", idMunicipio=" + idMunicipio + ", seccion=" + seccion
				+ ", idLocalidad=" + idLocalidad + ", manzana=" + manzana + ", orden=" + orden + ", usuario=" + usuario
				+ ", fechaHora=" + fechaHora + ", idEstado=" + idEstado + ", idDistrito=" + idDistrito + "]";
	}

}
