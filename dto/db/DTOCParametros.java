package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "C_PARAMETROS", schema = "INSA1")
public class DTOCParametros implements Serializable {

	private static final long serialVersionUID = -1616810513477414232L;

	@Id
	@NotNull
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
	@Column(name = "ID_DISTRITO", nullable = false, precision = 2, scale = 0)
	private Integer idDistrito;

	@Id
	@NotNull
	@Column(name = "ID_MUNICIPIO", nullable = false, precision = 3, scale = 0)
	private Integer idMunicipio;

	@Id
	@NotNull
	@Column(name = "ID_LOCALIDAD", nullable = false, precision = 5, scale = 0)
	private Integer idLocalidad;

	@Id
	@NotNull
	@Column(name = "ID_COMUNIDAD", nullable = false, precision = 4, scale = 0)
	private Integer idComunidad;

	@Id
	@NotNull
	@Column(name = "ID_REGIDURIA", nullable = false, precision = 2, scale = 0)
	private Integer idRegiduria;

	@Id
	@NotNull
	@Column(name = "TIPO_JUNTA", nullable = false, length = 2)
	private String tipoJunta;

	@Id
	@NotNull
	@Column(name = "ID_PARAMETRO", nullable = false, precision = 2, scale = 0)
	private Integer idParametro;
	
	@NotNull
	@Column(name = "VALOR_PARAMETRO", nullable = false, length = 10)
	private String valorParametro;

	public Integer getIdProcesoElectoral() {
		return idProcesoElectoral;
	}

	public void setIdProcesoElectoral(Integer idProcesoElectoral) {
		this.idProcesoElectoral = idProcesoElectoral;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getTipoJunta() {
		return tipoJunta;
	}

	public void setTipoJunta(String tipoJunta) {
		this.tipoJunta = tipoJunta;
	}

	public Integer getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(Integer idParametro) {
		this.idParametro = idParametro;
	}

	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}

	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
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

	public Integer getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(Integer idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public Integer getIdComunidad() {
		return idComunidad;
	}

	public void setIdComunidad(Integer idComunidad) {
		this.idComunidad = idComunidad;
	}

	public Integer getIdRegiduria() {
		return idRegiduria;
	}

	public void setIdRegiduria(Integer idRegiduria) {
		this.idRegiduria = idRegiduria;
	}

	public String getValorParametro() {
		return valorParametro;
	}

	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idComunidad == null) ? 0 : idComunidad.hashCode());
		result = prime * result + ((idDetalleProceso == null) ? 0 : idDetalleProceso.hashCode());
		result = prime * result + ((idDistrito == null) ? 0 : idDistrito.hashCode());
		result = prime * result + ((idEstado == null) ? 0 : idEstado.hashCode());
		result = prime * result + ((idLocalidad == null) ? 0 : idLocalidad.hashCode());
		result = prime * result + ((idMunicipio == null) ? 0 : idMunicipio.hashCode());
		result = prime * result + ((idParametro == null) ? 0 : idParametro.hashCode());
		result = prime * result + ((idProcesoElectoral == null) ? 0 : idProcesoElectoral.hashCode());
		result = prime * result + ((idRegiduria == null) ? 0 : idRegiduria.hashCode());
		result = prime * result + ((tipoJunta == null) ? 0 : tipoJunta.hashCode());
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
		DTOCParametros other = (DTOCParametros) obj;
		if (idComunidad == null) {
			if (other.idComunidad != null)
				return false;
		} else if (!idComunidad.equals(other.idComunidad))
			return false;
		if (idDetalleProceso == null) {
			if (other.idDetalleProceso != null)
				return false;
		} else if (!idDetalleProceso.equals(other.idDetalleProceso))
			return false;
		if (idDistrito == null) {
			if (other.idDistrito != null)
				return false;
		} else if (!idDistrito.equals(other.idDistrito))
			return false;
		if (idEstado == null) {
			if (other.idEstado != null)
				return false;
		} else if (!idEstado.equals(other.idEstado))
			return false;
		if (idLocalidad == null) {
			if (other.idLocalidad != null)
				return false;
		} else if (!idLocalidad.equals(other.idLocalidad))
			return false;
		if (idMunicipio == null) {
			if (other.idMunicipio != null)
				return false;
		} else if (!idMunicipio.equals(other.idMunicipio))
			return false;
		if (idParametro == null) {
			if (other.idParametro != null)
				return false;
		} else if (!idParametro.equals(other.idParametro))
			return false;
		if (idProcesoElectoral == null) {
			if (other.idProcesoElectoral != null)
				return false;
		} else if (!idProcesoElectoral.equals(other.idProcesoElectoral))
			return false;
		if (idRegiduria == null) {
			if (other.idRegiduria != null)
				return false;
		} else if (!idRegiduria.equals(other.idRegiduria))
			return false;
		if (tipoJunta == null) {
			if (other.tipoJunta != null)
				return false;
		} else if (!tipoJunta.equals(other.tipoJunta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTOCParametros [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso
				+ ", idEstado=" + idEstado + ", idDistrito=" + idDistrito + ", idMunicipio=" + idMunicipio
				+ ", idLocalidad=" + idLocalidad + ", idComunidad=" + idComunidad + ", idRegiduria=" + idRegiduria
				+ ", tipoJunta=" + tipoJunta + ", idParametro=" + idParametro + ", valorParametro=" + valorParametro
				+ "]";
	}

}