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
@Table(name = "FIRMAS_CARTAS", schema = "PRIMERA_CAPA")
public class DTOFirmasCartas implements Serializable{	
	
	private static final long serialVersionUID = 8243863192784257224L;
	
	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProceso;
	
	@Id
	@Column(name = "ID_DETALLE_PROCESO", nullable = false,  precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@Column(name = "ID_PARTICIPACION", nullable = false,  precision = 9, scale = 0)
	private Integer idParticipacion;
	
	@Column(name = "NOMBRE_ARCHIVO", nullable = false, length = 100)
	private String nombreArchivo;
	
	@Column(name = "RUTA_ARCHIVO", nullable = false, length = 250)
	private String rutaArchivo;
	
	@Column(name = "PICADILLO", nullable = false, length = 100)
	private String picadillo;
	
	@Column(name = "USUARIO", nullable = false, length = 50)
	private String usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = false)
	private Date fechaHora;
	
	public DTOFirmasCartas() {
		
	}
	
	public DTOFirmasCartas(Integer idProceso, Integer idDetalleProceso, Integer idParticipacion, String nombreArchivo,
			String rutaArchivo, String picadillo, String usuario, Date fechaHora) {
		super();
		this.idProceso = idProceso;
		this.idDetalleProceso = idDetalleProceso;
		this.idParticipacion = idParticipacion;
		this.nombreArchivo = nombreArchivo;
		this.rutaArchivo = rutaArchivo;
		this.picadillo = picadillo;
		this.usuario = usuario;
		this.fechaHora = fechaHora;
	}
	
	public Integer getIdProceso() {
		return idProceso;
	}
	
	public void setIdProceso(Integer idProceso) {
		this.idProceso = idProceso;
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
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
	public String getRutaArchivo() {
		return rutaArchivo;
	}
	
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	
	public String getPicadillo() {
		return picadillo;
	}
	
	public void setPicadillo(String picadillo) {
		this.picadillo = picadillo;
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
		DTOFirmasCartas other = (DTOFirmasCartas) obj;
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
		return "DTOFirmasCartas [idProceso=" + idProceso + ", idDetalleProceso=" + idDetalleProceso
				+ ", idParticipacion=" + idParticipacion + ", nombreArchivo=" + nombreArchivo + ", rutaArchivo="
				+ rutaArchivo + ", picadillo=" + picadillo + ", usuario=" + usuario + ", fechaHora=" + fechaHora + "]";
	}
	
}
