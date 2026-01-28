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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "DATOS_INSACULADOS", schema = "INSA1")
public class DTODatosInsaculados implements Serializable {
	
	private static final long serialVersionUID = -2443696339847702104L;
	
	@Column(name = "ID_PROCESO_ELECTORAL", nullable = true, precision = 5, scale = 0)
	private Integer idProcesoElectoral;
	
	@Id
	@NotNull
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;
	
	@Id
	@NotNull
	@Column(name = "NUMERO_CREDENCIAL_ELECTOR", nullable = false, length = 18)
	private String numeroCredencialElector;
	
	@Column(name = "ID_PARTICIPACION", nullable = false, precision = 9, scale = 0)
	private Integer idParticipacion;
	
	@Column(name = "ID_ESTADO", nullable = false, precision = 2, scale = 0)
	private Integer idEstado;

	@Column(name = "ID_DISTRITO_FEDERAL", nullable = false, precision = 2, scale = 0)
	private Integer idDistritoFederal;

	@Column(name = "ID_DISTRITO_LOCAL", nullable = false, precision = 2, scale = 0)
	private Integer idDistritoLocal;
	
	@Column(name = "ID_MUNICIPIO", nullable = false, precision = 3, scale = 0)
	private Integer idMunicipio;
	
	@Column(name = "SECCION", nullable = false, precision = 4, scale = 0)
	private Integer seccion;

	@Column(name = "ID_CASILLA", nullable = false, precision = 2, scale = 0)
	private Integer idCasilla;

	@Column(name = "TIPO_CASILLA", nullable = false, length = 1)
	private String tipoCasilla;
	
	@Transient
	private String casilla;
	
	@Column(name = "ID_LOCALIDAD", nullable = false, precision = 5, scale = 0)
	private Integer idLocalidad;

	@Column(name = "MANZANA", nullable = false, precision = 4, scale = 0)
	private Integer manzana;
	
	@Column(name = "ID_CIUDADANO", nullable = true, precision = 12, scale = 0)
	private Integer idCiudadano;
	
	@Column(name = "ID_TIPO_VOTO", nullable = false, precision = 2, scale = 0)
	private Integer idTipoVoto;
	
	@Column(name = "FOLIO", nullable = false, precision = 5, scale = 0)
	private Integer folio;
	
	@Column(name = "APELLIDO_PATERNO", nullable = true, length = 40)
	private String apellidoPaterno;

	@Column(name = "APELLIDO_MATERNO", nullable = true, length = 40)
	private String apellidoMaterno;

	@Column(name = "NOMBRE", nullable = false, length = 100)
	private String nombre;
	
	@Column(name = "CALLE", nullable = true, length = 50)
	private String calle;

	@Column(name = "NUMERO_EXTERIOR", nullable = true, length = 8)
	private String numeroExterior;

	@Column(name = "NUMERO_INTERIOR", nullable = true, length = 8)
	private String numeroInterior;

	@Column(name = "COLONIA", nullable = true, length = 60)
	private String colonia;
	
	@Column(name = "CODIGO_POSTAL", nullable = true, length = 8)
	private String codigoPostal;

	@Column(name = "EDAD", nullable = false, precision = 3, scale = 0)
	private Integer edad;
	
	@Column(name = "SEXO", nullable = false, length = 1)
	private String sexo;
	
	@Column(name = "MES_NACIMIENTO", nullable = false, precision = 3, scale = 0)
	private Integer mesNacimiento;
	
	@Column(name = "ORDEN_GEOGRAFICO", nullable = true, precision = 10, scale = 0)
	private Integer ordenGeografico;
	
	@Column(name = "ID_AREA_RESPONSABILIDAD", nullable = true, precision = 4, scale = 0)
	private Integer idAreaResponsabilidad;
	
	@Column(name = "ORDEN_ALFABETICO", nullable = true, precision = 5, scale = 0)
	private Integer ordenAlfabetico;

	@Column(name = "ORDEN_VISITA", nullable = true, precision = 5, scale = 0)
	private Integer ordenVisita;

	@Column(name = "ORDEN_LETRA", nullable = true, precision = 5, scale = 0)
	private Integer ordenLetra;
	
	@Column(name = "SEMILLA", nullable = true, precision = 2, scale = 0)
	private Integer semilla;
	
	@Column(name = "USUARIO", nullable = false, length = 50)
	private String usuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = false)
	private Date fechaHora;
	
	@Column(name = "ENTIDAD_NACIMIENTO", nullable = true, precision = 2, scale = 0)
	private Integer entidadNacimiento;
	
	@Column(name = "SERVIDOR_PUBLICO", nullable = false, precision = 1, scale = 0)
	private Integer servidorPublico;
	
	public DTODatosInsaculados() {
	}

	public DTODatosInsaculados(String numeroCredencialElector, Integer idDetalleProceso) {
		super();
		this.numeroCredencialElector = numeroCredencialElector;
		this.idDetalleProceso = idDetalleProceso;
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

	public String getNumeroCredencialElector() {
		return numeroCredencialElector;
	}

	public void setNumeroCredencialElector(String numeroCredencialElector) {
		this.numeroCredencialElector = numeroCredencialElector;
	}

	public Integer getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(Integer idParticipacion) {
		this.idParticipacion = idParticipacion;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Integer getIdDistritoFederal() {
		return idDistritoFederal;
	}

	public void setIdDistritoFederal(Integer idDistritoFederal) {
		this.idDistritoFederal = idDistritoFederal;
	}

	public Integer getIdDistritoLocal() {
		return idDistritoLocal;
	}

	public void setIdDistritoLocal(Integer idDistritoLocal) {
		this.idDistritoLocal = idDistritoLocal;
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

	public Integer getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Integer idCasilla) {
		this.idCasilla = idCasilla;
	}

	public String getTipoCasilla() {
		return tipoCasilla;
	}

	public void setTipoCasilla(String tipoCasilla) {
		this.tipoCasilla = tipoCasilla;
	}

	public String getCasilla() {
		return casilla;
	}

	public void setCasilla(String casilla) {
		this.casilla = casilla;
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

	public Integer getIdCiudadano() {
		return idCiudadano;
	}

	public void setIdCiudadano(Integer idCiudadano) {
		this.idCiudadano = idCiudadano;
	}

	public Integer getIdTipoVoto() {
		return idTipoVoto;
	}

	public void setIdTipoVoto(Integer idTipoVoto) {
		this.idTipoVoto = idTipoVoto;
	}

	public Integer getFolio() {
		return folio;
	}

	public void setFolio(Integer folio) {
		this.folio = folio;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumeroExterior() {
		return numeroExterior;
	}

	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}

	public String getNumeroInterior() {
		return numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Integer getMesNacimiento() {
		return mesNacimiento;
	}

	public void setMesNacimiento(Integer mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}
	
	public Integer getOrdenGeografico() {
		return ordenGeografico;
	}

	public void setOrdenGeografico(Integer ordenGeografico) {
		this.ordenGeografico = ordenGeografico;
	}

	public Integer getIdAreaResponsabilidad() {
		return idAreaResponsabilidad;
	}

	public void setIdAreaResponsabilidad(Integer idAreaResponsabilidad) {
		this.idAreaResponsabilidad = idAreaResponsabilidad;
	}

	public Integer getOrdenAlfabetico() {
		return ordenAlfabetico;
	}

	public void setOrdenAlfabetico(Integer ordenAlfabetico) {
		this.ordenAlfabetico = ordenAlfabetico;
	}

	public Integer getOrdenVisita() {
		return ordenVisita;
	}

	public void setOrdenVisita(Integer ordenVisita) {
		this.ordenVisita = ordenVisita;
	}

	public Integer getOrdenLetra() {
		return ordenLetra;
	}

	public void setOrdenLetra(Integer ordenLetra) {
		this.ordenLetra = ordenLetra;
	}

	public Integer getSemilla() {
		return semilla;
	}

	public void setSemilla(Integer semilla) {
		this.semilla = semilla;
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

	public Integer getEntidadNacimiento() {
		return entidadNacimiento;
	}

	public void setEntidadNacimiento(Integer entidadNacimiento) {
		this.entidadNacimiento = entidadNacimiento;
	}

	public Integer getServidorPublico() {
		return servidorPublico;
	}

	public void setServidorPublico(Integer servidorPublico) {
		this.servidorPublico = servidorPublico;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(idDetalleProceso, numeroCredencialElector);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTODatosInsaculados other = (DTODatosInsaculados) obj;
		return Objects.equals(idDetalleProceso, other.idDetalleProceso)
				&& Objects.equals(numeroCredencialElector, other.numeroCredencialElector);
	}

	@Override
	public String toString() {
		return "DTODatosInsaculados [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso="
				+ idDetalleProceso + ", numeroCredencialElector=" + numeroCredencialElector + ", idParticipacion="
				+ idParticipacion + ", idEstado=" + idEstado + ", idDistritoFederal=" + idDistritoFederal
				+ ", idDistritoLocal=" + idDistritoLocal + ", idMunicipio=" + idMunicipio + ", seccion=" + seccion
				+ ", idCasilla=" + idCasilla + ", tipoCasilla=" + tipoCasilla + ", casilla=" + casilla
				+ ", idLocalidad=" + idLocalidad + ", manzana=" + manzana + ", idCiudadano=" + idCiudadano
				+ ", idTipoVoto=" + idTipoVoto + ", folio=" + folio + ", apellidoPaterno=" + apellidoPaterno
				+ ", apellidoMaterno=" + apellidoMaterno + ", nombre=" + nombre + ", calle=" + calle
				+ ", numeroExterior=" + numeroExterior + ", numeroInterior=" + numeroInterior + ", colonia=" + colonia
				+ ", codigoPostal=" + codigoPostal + ", edad=" + edad + ", sexo=" + sexo + ", mesNacimiento="
				+ mesNacimiento + ", ordenGeografico=" + ordenGeografico + ", idAreaResponsabilidad="
				+ idAreaResponsabilidad + ", ordenAlfabetico=" + ordenAlfabetico + ", ordenVisita=" + ordenVisita
				+ ", ordenLetra=" + ordenLetra + ", semilla=" + semilla + ", usuario=" + usuario + ", fechaHora="
				+ fechaHora + ", entidadNacimiento=" + entidadNacimiento + ", servidorPublico="
				+ servidorPublico +  "]";
	}
	
}
