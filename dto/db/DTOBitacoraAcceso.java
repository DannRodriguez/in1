package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import mx.ine.procprimerinsa.dto.DTOCSVPrintableInterface;
import mx.ine.procprimerinsa.util.Utilidades;

@Entity
@Table(name = "BITACORA_ACCESO", schema = "INSA1")
public class DTOBitacoraAcceso implements Serializable, DTOCSVPrintableInterface {

	private static final long serialVersionUID = -7368083151156664394L;

	 @Id
	 @Column(name = "ID_BITACORA_ACCESO", nullable = false, precision = 10, scale = 0)
	 private Integer idBitacoraAcceso;

	 @Column(name = "ID_SISTEMA", nullable = false, precision = 10, scale = 0)
	 private Integer idSistema;
	 
	 @Column(name = "USUARIO", nullable = false, length = 50)
	 private String usuario;
	
	 @Column(name = "ROL_USUARIO", nullable = false, length = 50)
	 private String rolUsuario;
	
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "FECHA_HORA_INICIO", nullable = true)
	 private Date fechaHoraInicio;
	
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "FECHA_HORA_FIN", nullable = true)
	 private Date fechaHoraFin;
	
	 @Column(name = "IP_USUARIO", nullable = false, length = 20)
	 private String ipUsuario;
	
	 @Column(name = "IP_NODO", nullable = false, length = 20)
	 private String ipNodo;

	public Integer getIdBitacoraAcceso() {
		return idBitacoraAcceso;
	}

	public void setIdBitacoraAcceso(Integer idBitacoraAcceso) {
		this.idBitacoraAcceso = idBitacoraAcceso;
	}

	public Integer getIdSistema() {
		return idSistema;
	}

	public void setIdSistema(Integer idSistema) {
		this.idSistema = idSistema;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}

	public Date getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(Date fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}

	public Date getFechaHoraFin() {
		return fechaHoraFin;
	}

	public void setFechaHoraFin(Date fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}

	public String getIpUsuario() {
		return ipUsuario;
	}

	public void setIpUsuario(String ipUsuario) {
		this.ipUsuario = ipUsuario;
	}

	public String getIpNodo() {
		return ipNodo;
	}

	public void setIpNodo(String ipNodo) {
		this.ipNodo = ipNodo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idBitacoraAcceso == null) ? 0 : idBitacoraAcceso.hashCode());
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
		DTOBitacoraAcceso other = (DTOBitacoraAcceso) obj;
		if (idBitacoraAcceso == null) {
			if (other.idBitacoraAcceso != null)
				return false;
		} else if (!idBitacoraAcceso.equals(other.idBitacoraAcceso))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTOBitacoraAcceso [idBitacoraAcceso=" + idBitacoraAcceso + ", idSistema=" + idSistema + ", usuario="
				+ usuario + ", rolUsuario=" + rolUsuario + ", fechaHoraInicio=" + fechaHoraInicio + ", fechaHoraFin="
				+ fechaHoraFin + ", ipUsuario=" + ipUsuario + ", ipNodo=" + ipNodo + "]";
	}
	
	@Override
	public Object[] getCSVPrintable() {
		return new Object[] {
				idBitacoraAcceso,
				idSistema,
				usuario,
				rolUsuario,
				Utilidades.formatDate(fechaHoraInicio),
				Utilidades.formatDate(fechaHoraFin),
				ipUsuario,
				ipNodo
		};
	}

}
