package mx.ine.procprimerinsa.dto.db;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import mx.ine.procprimerinsa.dto.DTOCSVPrintableInterface;

@Entity
@Table(name = "C_TIPO_VOTO", schema = "INSA1")
public class DTOCTipoVoto implements Serializable, DTOCSVPrintableInterface {
	
	private static final long serialVersionUID = 481725469643169521L;

	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@Column(name = "ID_TIPO_VOTO", nullable = false, precision = 2, scale = 0)
	private Integer idTipoVoto;
	
	@Column(name = "DESCRIPCION", nullable = false, length = 200)
	private String descripcion;
	
	@Column(name = "SIGLAS", nullable = false, length = 50)
	private String siglas;
	
	@Column(name = "PORCENTAJE_INSACULAR", nullable = false, precision = 3, scale = 0)
	private Integer porcentajeInsacular;
	
	@Column(name = "MINIMO_INSACULAR", nullable = false, precision = 3, scale = 0)
	private Integer minimoInsacular;
	
	@Column(name = "NOMBRE_ARCHIVO_LISTADO", nullable = false, length = 200)
	private String nombreArchivoListado;
	
	@Column(name = "TITULO_ARCHIVO_LISTADO", nullable = false, length = 200)
	private String tituloArchivoListado;
	
	@Column(name = "NOMBRE_ARCHIVO_CEDULA", nullable = false, length = 200)
	private String nombreArchivoCedula;
	
	@Column(name = "TITULO_ARCHIVO_CEDULA", nullable = false, length = 200)
	private String tituloArchivoCedula;
	
	@Column(name = "TIPO_INTEGRACION", nullable = false, precision = 1, scale = 0)
	private Integer tipoIntegracion;
	
	@Column(name = "LEYENDA_CASILLA", nullable = false, length = 50)
	private String leyendaCasilla;
	
	public DTOCTipoVoto() {
		super();
	}
	
	public DTOCTipoVoto(DTOCTipoVoto tipoVoto) {
		this.idProcesoElectoral = tipoVoto.getIdProcesoElectoral();
		this.idDetalleProceso = tipoVoto.getIdDetalleProceso();
		this.idTipoVoto = tipoVoto.getIdTipoVoto();
		this.descripcion = tipoVoto.getDescripcion();
		this.siglas = tipoVoto.getSiglas();
		this.porcentajeInsacular = tipoVoto.getPorcentajeInsacular();
		this.minimoInsacular = tipoVoto.getMinimoInsacular();
		this.nombreArchivoListado = tipoVoto.getNombreArchivoListado();
		this.tituloArchivoListado = tipoVoto.getTituloArchivoListado();
		this.nombreArchivoCedula = tipoVoto.getNombreArchivoCedula();
		this.tituloArchivoCedula = tipoVoto.getTituloArchivoCedula();
		this.tipoIntegracion = tipoVoto.getTipoIntegracion();
		this.leyendaCasilla = tipoVoto.getLeyendaCasilla();
	}

	public DTOCTipoVoto(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idTipoVoto, String descripcion,
			String siglas, Integer porcentajeInsacular, Integer minimoInsacular, String nombreArchivoListado,
			String tituloArchivoListado, String nombreArchivoCedula, String tituloArchivoCedula,
			Integer tipoIntegracion, String leyendaCasilla) {
		super();
		this.idProcesoElectoral = idProcesoElectoral;
		this.idDetalleProceso = idDetalleProceso;
		this.idTipoVoto = idTipoVoto;
		this.descripcion = descripcion;
		this.siglas = siglas;
		this.porcentajeInsacular = porcentajeInsacular;
		this.minimoInsacular = minimoInsacular;
		this.nombreArchivoListado = nombreArchivoListado;
		this.tituloArchivoListado = tituloArchivoListado;
		this.nombreArchivoCedula = nombreArchivoCedula;
		this.tituloArchivoCedula = tituloArchivoCedula;
		this.tipoIntegracion = tipoIntegracion;
		this.leyendaCasilla = leyendaCasilla;
	}

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

	public Integer getIdTipoVoto() {
		return idTipoVoto;
	}

	public void setIdTipoVoto(Integer idTipoVoto) {
		this.idTipoVoto = idTipoVoto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSiglas() {
		return siglas;
	}

	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}

	public Integer getPorcentajeInsacular() {
		return porcentajeInsacular;
	}

	public void setPorcentajeInsacular(Integer porcentajeInsacular) {
		this.porcentajeInsacular = porcentajeInsacular;
	}

	public Integer getMinimoInsacular() {
		return minimoInsacular;
	}

	public void setMinimoInsacular(Integer minimoInsacular) {
		this.minimoInsacular = minimoInsacular;
	}

	public String getNombreArchivoListado() {
		return nombreArchivoListado;
	}

	public void setNombreArchivoListado(String nombreArchivoListado) {
		this.nombreArchivoListado = nombreArchivoListado;
	}

	public String getTituloArchivoListado() {
		return tituloArchivoListado;
	}

	public void setTituloArchivoListado(String tituloArchivoListado) {
		this.tituloArchivoListado = tituloArchivoListado;
	}

	public String getNombreArchivoCedula() {
		return nombreArchivoCedula;
	}

	public void setNombreArchivoCedula(String nombreArchivoCedula) {
		this.nombreArchivoCedula = nombreArchivoCedula;
	}

	public String getTituloArchivoCedula() {
		return tituloArchivoCedula;
	}

	public void setTituloArchivoCedula(String tituloArchivoCedula) {
		this.tituloArchivoCedula = tituloArchivoCedula;
	}

	public Integer getTipoIntegracion() {
		return tipoIntegracion;
	}

	public void setTipoIntegracion(Integer tipoIntegracion) {
		this.tipoIntegracion = tipoIntegracion;
	}

	public String getLeyendaCasilla() {
		return leyendaCasilla;
	}

	public void setLeyendaCasilla(String leyendaCasilla) {
		this.leyendaCasilla = leyendaCasilla;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idDetalleProceso, idTipoVoto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOCTipoVoto other = (DTOCTipoVoto) obj;
		return Objects.equals(idDetalleProceso, other.idDetalleProceso) && Objects.equals(idTipoVoto, other.idTipoVoto);
	}
	
	@Override
	public String toString() {
		return "DTOCTipoVoto [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso
				+ ", idTipoVoto=" + idTipoVoto + ", descripcion=" + descripcion + ", siglas=" + siglas
				+ ", porcentajeInsacular=" + porcentajeInsacular + ", minimoInsacular=" + minimoInsacular
				+ ", nombreArchivoListado=" + nombreArchivoListado + ", tituloArchivoListado=" + tituloArchivoListado
				+ ", nombreArchivoCedula=" + nombreArchivoCedula + ", tituloArchivoCedula=" + tituloArchivoCedula
				+ ", tipoIntegracion=" + tipoIntegracion + ", leyendaCasilla=" + leyendaCasilla + "]";
	}

	@Override
	public Object[] getCSVPrintable() {
		return new Object[] {
				idProcesoElectoral,
				idDetalleProceso,
				idTipoVoto,
				descripcion,
				siglas,
				porcentajeInsacular,
				minimoInsacular,
				nombreArchivoListado,
				tituloArchivoListado,
				nombreArchivoCedula,
				tituloArchivoCedula,
				tipoIntegracion,
				leyendaCasilla
		};
	}

}
