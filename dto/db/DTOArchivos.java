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
@Table(name = "ARCHIVOS", schema = "INSA1")
public class DTOArchivos implements Serializable {

	private static final long serialVersionUID = -8564740857276082908L;
	
	@Column(name = "ID_PROCESO_ELECTORAL", nullable = true, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@NotNull
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@NotNull
	@Column(name = "ID_PARTICIPACION", nullable = false, precision = 9, scale = 0)
	private Integer idParticipacion;
	
	@Id
	@Column(name = "ID_TIPO_VOTO", nullable = false, precision = 2, scale = 0)
	private Integer idTipoVoto;
	
	@Id
	@NotNull
	@Column(name = "ARCHIVO_SISTEMA", nullable = false, precision = 1, scale = 0)
	private Integer archivoSistema;
	
	@Column(name = "RUTA_ARCHIVO", nullable = true, length = 250)
	private String rutaArchivo;
	
	@Column(name = "NOMBRE_ARCHIVO", nullable = true, length = 100)
	private String nombreArchivo;
	
	@Column(name = "CODIGO_INTEGRIDAD", nullable = true, length = 100)
	private String codigoIntegridad;
	
	@Column(name = "USUARIO", nullable = true, length = 50)
	private String usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = true)
	private Date fechaHora;

	public DTOArchivos() {
		
	}
	
	public DTOArchivos(Integer idDetalleProceso, Integer idParticipacion, Integer idTipoVoto, Integer archivoSistema) {
		super();
		this.idDetalleProceso = idDetalleProceso;
		this.idParticipacion = idParticipacion;
		this.idTipoVoto = idTipoVoto;
		this.archivoSistema = archivoSistema;
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
	
	public Integer getIdParticipacion() {
		return this.idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}
	
	public Integer getIdTipoVoto() {
		return idTipoVoto;
	}

	public void setIdTipoVoto(Integer idTipoVoto) {
		this.idTipoVoto = idTipoVoto;
	}

	public Integer getArchivoSistema() {
		return this.archivoSistema;
	}

	public void setArchivoSistema(Integer archivoSistema) {
		this.archivoSistema = archivoSistema;
	}
	
	public String getRutaArchivo() {
		return this.rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	
	public String getNombreArchivo() {
		return this.nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
	public String getCodigoIntegridad() {
		return this.codigoIntegridad;
	}

	public void setCodigoIntegridad(String codigoIntegridad) {
		this.codigoIntegridad = codigoIntegridad;
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
		return Objects.hash(archivoSistema, idDetalleProceso, idParticipacion, idTipoVoto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOArchivos other = (DTOArchivos) obj;
		return Objects.equals(archivoSistema, other.archivoSistema)
				&& Objects.equals(idDetalleProceso, other.idDetalleProceso)
				&& Objects.equals(idParticipacion, other.idParticipacion)
				&& Objects.equals(idTipoVoto, other.idTipoVoto);
	}

	@Override
	public String toString() {
		return "DTOArchivos [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso
				+ ", idParticipacion=" + idParticipacion + ", idTipoVoto=" + idTipoVoto + ", archivoSistema="
				+ archivoSistema + ", rutaArchivo=" + rutaArchivo + ", nombreArchivo=" + nombreArchivo
				+ ", codigoIntegridad=" + codigoIntegridad + ", usuario=" + usuario + ", fechaHora=" + fechaHora + "]";
	}

}
