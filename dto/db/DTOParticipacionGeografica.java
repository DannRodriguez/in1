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
@Table(name = "PARTICIPACION_GEOGRAFICA", schema = "INSA1")
public class DTOParticipacionGeografica implements Serializable {

	private static final long serialVersionUID = 4578254628398063924L;
	
	private Integer idProcesoElectoral;
	private Integer idDetalleProceso;
	private Integer idParticipacion;
	private Integer idEstado;
	private Integer idDistrito;
	private Integer idMunicipio;
	private Integer idRegiduria;
	private Integer idSeccionSede;
	private Integer idLocalidad;
	private Integer idComunidad;
	private String ambitoParticipacion;
	private String usuario;
	private String ipUsuario;
	private Date fechaHora;

	public DTOParticipacionGeografica() {
		
	}

	public DTOParticipacionGeografica(Integer idProcesoElectoral,
			Integer idDetalleProceso, Integer idParticipacion,
			Integer idEstado, Integer idDistrito, Integer idMunicipio,
			Integer idRegiduria, Integer idSeccionSede, Integer idLocalidad,
			Integer idComunidad, String ambitoParticipacion,
			String usuario, String ipUsuario, Date fechaHora) {
		super();
		this.idProcesoElectoral = idProcesoElectoral;
		this.idDetalleProceso = idDetalleProceso;
		this.idParticipacion = idParticipacion;
		this.idEstado = idEstado;
		this.idDistrito = idDistrito;
		this.idMunicipio = idMunicipio;
		this.idRegiduria = idRegiduria;
		this.idSeccionSede = idSeccionSede;
		this.idLocalidad = idLocalidad;
		this.idComunidad = idComunidad;
		this.ambitoParticipacion = ambitoParticipacion;
		this.usuario = usuario;
		this.ipUsuario = ipUsuario;
		this.fechaHora = fechaHora;
	}

	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	public Integer getIdProcesoElectoral() {
		return idProcesoElectoral;
	}

	public void setIdProcesoElectoral(Integer idProcesoElectoral) {
		this.idProcesoElectoral = idProcesoElectoral;
	}

	@Id
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}

	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}

	@Id
	@Column(name = "ID_PARTICIPACION", nullable = false, precision = 9, scale = 0)
	public Integer getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}

	@Column(name = "ID_ESTADO", nullable = false, precision = 2, scale = 0)
	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	@Column(name = "ID_DISTRITO", nullable = false, precision = 5, scale = 0)
	public Integer getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}

	@Column(name = "ID_MUNICIPIO", nullable = false, precision = 3, scale = 0)
	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	
	@Column(name = "ID_REGIDURIA", nullable = true, precision = 2, scale = 0)
	public Integer getIdRegiduria() {
		return idRegiduria;
	}

	public void setIdRegiduria(Integer idRegiduria) {
		this.idRegiduria = idRegiduria;
	}

	@Column(name = "ID_SECCION_SEDE", nullable = true, precision = 4, scale = 0)
	public Integer getIdSeccionSede() {
		return idSeccionSede;
	}

	public void setIdSeccionSede(Integer idSeccionSede) {
		this.idSeccionSede = idSeccionSede;
	}

	@Column(name = "ID_LOCALIDAD", nullable = false, precision = 5, scale = 0)
	public Integer getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(Integer idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	@Column(name = "ID_COMUNIDAD", nullable = false, precision = 4, scale = 0)
	public Integer getIdComunidad() {
		return idComunidad;
	}

	public void setIdComunidad(Integer idComunidad) {
		this.idComunidad = idComunidad;
	}

	@Column(name = "AMBITO_PARTICIPACION", nullable = true, length = 1)
	public String getAmbitoParticipacion() {
		return ambitoParticipacion;
	}

	public void setAmbitoParticipacion(String ambitoParticipacion) {
		this.ambitoParticipacion = ambitoParticipacion;
	}

	@Column(name = "USUARIO", nullable = false, length = 50)
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	@Column(name = "IP_USUARIO", nullable = false, length = 15)
	public String getIpUsuario() {
		return ipUsuario;
	}

	public void setIpUsuario(String ipUsuario) {
		this.ipUsuario = ipUsuario;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = false)
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
		DTOParticipacionGeografica other = (DTOParticipacionGeografica) obj;
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
		return "DTOParticipacionGeografica [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso="
				+ idDetalleProceso + ", idParticipacion=" + idParticipacion + ", idEstado=" + idEstado + ", idDistrito="
				+ idDistrito + ", idMunicipio=" + idMunicipio + ", idRegiduria=" + idRegiduria + ", idSeccionSede="
				+ idSeccionSede + ", idLocalidad=" + idLocalidad + ", idComunidad=" + idComunidad
				+ ", ambitoParticipacion=" + ambitoParticipacion + ", usuario=" + usuario + ", ipUsuario=" + ipUsuario
				+ ", fechaHora=" + fechaHora + "]";
	}

}
