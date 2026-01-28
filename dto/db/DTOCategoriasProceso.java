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
@Table(name = "CATEGORIAS_PROCESO", schema = "INSA1")
public class DTOCategoriasProceso implements Serializable {

	private static final long serialVersionUID = 1376438098615878254L;
	
	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@NotNull
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@NotNull
	@Column(name = "ID_CATEGORIA", nullable = false, precision = 2, scale = 0)
	private Integer idCategoria;
	
	@Column(name = "CODIGO_COLOR_CATEGORIA", nullable = false, length = 10)
	private String codigoColorCategoria;
	
	@Column(name = "ETIQUETA_CATEGORIA", nullable = true, length = 100)
	private String etiquetaCategoria;
	
	@Column(name = "PORCENTAJE_AVANCE_CATEGORIA", nullable = true, precision = 5)
	private Double porcentajeAvanceCategoria;
	
	@Column(name = "USUARIO", nullable = false, length = 50)
	private String usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = false)
	private Date fechaHora;

	public DTOCategoriasProceso() {
		
	}

	public DTOCategoriasProceso(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idCategoria,
			 String codigoColorCategoria, String etiquetaCategoria, Double porcentajeAvanceCategoria) {
		super();
		this.idProcesoElectoral = idProcesoElectoral;
		this.idDetalleProceso = idDetalleProceso;
		this.idCategoria = idCategoria;
		this.codigoColorCategoria = codigoColorCategoria;
		this.etiquetaCategoria = etiquetaCategoria;
		this.porcentajeAvanceCategoria = porcentajeAvanceCategoria;
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

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getCodigoColorCategoria() {
		return codigoColorCategoria;
	}

	public void setCodigoColorCategoria(String codigoColorCategoria) {
		this.codigoColorCategoria = codigoColorCategoria;
	}

	public String getEtiquetaCategoria() {
		return etiquetaCategoria;
	}

	public void setEtiquetaCategoria(String etiquetaCategoria) {
		this.etiquetaCategoria = etiquetaCategoria;
	}
	
	public Double getPorcentajeAvanceCategoria() {
		return porcentajeAvanceCategoria;
	}

	public void setPorcentajeAvanceCategoria(Double porcentajeAvanceCategoria) {
		this.porcentajeAvanceCategoria = porcentajeAvanceCategoria;
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
		result = prime * result + ((idCategoria == null) ? 0 : idCategoria.hashCode());
		result = prime * result + ((idDetalleProceso == null) ? 0 : idDetalleProceso.hashCode());
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
		DTOCategoriasProceso other = (DTOCategoriasProceso) obj;
		if (idCategoria == null) {
			if (other.idCategoria != null)
				return false;
		} else if (!idCategoria.equals(other.idCategoria))
			return false;
		if (idDetalleProceso == null) {
			if (other.idDetalleProceso != null)
				return false;
		} else if (!idDetalleProceso.equals(other.idDetalleProceso))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTOCategoriasProceso [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso="
				+ idDetalleProceso + ", idCategoria=" + idCategoria + ", codigoColorCategoria=" + codigoColorCategoria
				+ ", etiquetaCategoria=" + etiquetaCategoria + ", porcentajeAvanceCategoria="
				+ porcentajeAvanceCategoria + ", usuario=" + usuario + ", fechaHora=" + fechaHora + "]";
	}

}
