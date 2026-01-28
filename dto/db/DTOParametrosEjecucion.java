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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "PARAMETROS_EJECUCION", schema = "INSA1")
public class DTOParametrosEjecucion implements Serializable {
	
	private static final long serialVersionUID = -336816908029683258L;

	@Id
	@NotNull
	@Column(name = "ID_PARAMETRO", nullable = false, precision = 3, scale = 0)
	private Integer idParametro;
	
	@Column(name = "DESCRIPCION_PARAMETRO", nullable = false, length = 300)
	private String descripcionParametro;
	
	@Column(name = "ENCABEZADO", nullable = false, length = 1000)
	private String encabezado;
	
	@Column(name = "EJECUTOR", nullable = false, length = 4000)
	private String ejecutor;
	
	@Column(name = "USUARIO", nullable = false, length = 50)
	private String usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = false)
	private Date fechaHora;

	public Integer getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(Integer idParametro) {
		this.idParametro = idParametro;
	}

	public String getDescripcionParametro() {
		return descripcionParametro;
	}

	public void setDescripcionParametro(String descripcionParametro) {
		this.descripcionParametro = descripcionParametro;
	}

	public String getEncabezado() {
		return encabezado;
	}

	public void setEncabezado(String encabezado) {
		this.encabezado = encabezado;
	}

	public String getEjecutor() {
		return ejecutor;
	}

	public void setEjecutor(String ejecutor) {
		this.ejecutor = ejecutor;
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
		return Objects.hash(idParametro);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOParametrosEjecucion other = (DTOParametrosEjecucion) obj;
		return Objects.equals(idParametro, other.idParametro);
	}

	@Override
	public String toString() {
		return "DTOParametrosEjecucion [idParametro=" + idParametro + ", descripcionParametro=" + descripcionParametro
				+ ", encabezado=" + encabezado + ", ejecutor=" + ejecutor + ", usuario=" + usuario + ", fechaHora="
				+ fechaHora + "]";
	}

}
