package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "MAPAS", schema = "INSA1")
public class DTOMapas implements Serializable {

	private static final long serialVersionUID = -599951528871704935L;
	
	@Id
	@NotNull
	@Column(name = "ID_CORTE", nullable = false, precision = 6, scale = 0)
	private Integer idCorte;
	
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
	@Column(name = "AMBITO", nullable = false, length = 1)
	private String ambito;
	
	@Id
	@NotNull
	@Column(name = "TIPO_CAPTURA", nullable = false, length = 1)
	private String tipoCaptura;
	
	@Column(name = "CLAVE", nullable = false, length = 7)
	private String clave;
	
	@Column(name = "RUTA", nullable = false, length = 3000)
	private String ruta;

	public Integer getIdCorte() {
		return idCorte;
	}

	public void setIdCorte(Integer idCorte) {
		this.idCorte = idCorte;
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

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public String getTipoCaptura() {
		return tipoCaptura;
	}

	public void setTipoCaptura(String tipoCaptura) {
		this.tipoCaptura = tipoCaptura;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ambito == null) ? 0 : ambito.hashCode());
		result = prime * result + ((idCorte == null) ? 0 : idCorte.hashCode());
		result = prime * result + ((idDistrito == null) ? 0 : idDistrito.hashCode());
		result = prime * result + ((idEstado == null) ? 0 : idEstado.hashCode());
		result = prime * result + ((idMunicipio == null) ? 0 : idMunicipio.hashCode());
		result = prime * result + ((tipoCaptura == null) ? 0 : tipoCaptura.hashCode());
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
		DTOMapas other = (DTOMapas) obj;
		if (ambito == null) {
			if (other.ambito != null)
				return false;
		} else if (!ambito.equals(other.ambito))
			return false;
		if (idCorte == null) {
			if (other.idCorte != null)
				return false;
		} else if (!idCorte.equals(other.idCorte))
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
		if (idMunicipio == null) {
			if (other.idMunicipio != null)
				return false;
		} else if (!idMunicipio.equals(other.idMunicipio))
			return false;
		if (tipoCaptura == null) {
			if (other.tipoCaptura != null)
				return false;
		} else if (!tipoCaptura.equals(other.tipoCaptura))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTOMapas [idCorte=" + idCorte + ", idEstado=" + idEstado + ", idDistrito=" + idDistrito
				+ ", idMunicipio=" + idMunicipio + ", ambito=" + ambito + ", tipoCaptura=" + tipoCaptura + ", clave="
				+ clave + ", ruta=" + ruta + "]";
	}	

}
