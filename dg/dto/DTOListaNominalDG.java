package mx.ine.procprimerinsa.dg.dto;

import java.io.Serializable;
import java.util.Objects;

public class DTOListaNominalDG implements Serializable {

	private static final long serialVersionUID = 7829880150723358255L;
	
	private Integer idProcesoElectoral;
	private Integer idDetalleProceso;
	private Integer idParticipacion;
	private Integer idTipoVoto;
	
	private Integer folio;
	
	private Integer idCorteLNAActualizar;
	
	private Integer idCiudadano;
	private String numeroCredencialElector;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombre;
	private Integer idEstado;
	private Integer idDistrito;
	private Integer seccion;
	private Integer idCasilla;
	private String tipoCasilla;
	private Integer idMunicipio;
	private Integer idLocalidad;
	private Integer manzana;
	private Integer edad;
	private String nombreOrden;
	private Integer mesNacimiento;
	private String sexo;
	private String entidadNacimiento;
	private Integer idDistritoLocal;
	private Integer mesOrden;
	private Integer letraOrden;
	private Integer servidorPublico;
	
	private Integer idCircuitoJudicial;
	private Integer idDistritoJudicial;
	
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

	public Integer getIdParticipacion() {
		return idParticipacion;
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

	public Integer getFolio() {
		return folio;
	}

	public void setFolio(Integer folio) {
		this.folio = folio;
	}

	public Integer getIdCorteLNAActualizar() {
		return idCorteLNAActualizar;
	}

	public void setIdCorteLNAActualizar(Integer idCorteLNAActualizar) {
		this.idCorteLNAActualizar = idCorteLNAActualizar;
	}

	public Integer getIdCiudadano() {
		return idCiudadano;
	}

	public void setIdCiudadano(Integer idCiudadano) {
		this.idCiudadano = idCiudadano;
	}

	public String getNumeroCredencialElector() {
		return numeroCredencialElector;
	}

	public void setNumeroCredencialElector(String numeroCredencialElector) {
		this.numeroCredencialElector = numeroCredencialElector;
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

	public Integer getManzana() {
		return manzana;
	}

	public void setManzana(Integer manzana) {
		this.manzana = manzana;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getNombreOrden() {
		return nombreOrden;
	}

	public void setNombreOrden(String nombreOrden) {
		this.nombreOrden = nombreOrden;
	}

	public Integer getMesNacimiento() {
		return mesNacimiento;
	}

	public void setMesNacimiento(Integer mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEntidadNacimiento() {
		return entidadNacimiento;
	}

	public void setEntidadNacimiento(String entidadNacimiento) {
		this.entidadNacimiento = entidadNacimiento;
	}

	public Integer getIdDistritoLocal() {
		return idDistritoLocal;
	}

	public void setIdDistritoLocal(Integer idDistritoLocal) {
		this.idDistritoLocal = idDistritoLocal;
	}

	public Integer getMesOrden() {
		return mesOrden;
	}

	public void setMesOrden(Integer mesOrden) {
		this.mesOrden = mesOrden;
	}

	public Integer getLetraOrden() {
		return letraOrden;
	}

	public void setLetraOrden(Integer letraOrden) {
		this.letraOrden = letraOrden;
	}

	public Integer getServidorPublico() {
		return servidorPublico;
	}

	public void setServidorPublico(Integer servidorPublico) {
		this.servidorPublico = servidorPublico;
	}

	public Integer getIdCircuitoJudicial() {
		return idCircuitoJudicial;
	}

	public void setIdCircuitoJudicial(Integer idCircuitoJudicial) {
		this.idCircuitoJudicial = idCircuitoJudicial;
	}

	public Integer getIdDistritoJudicial() {
		return idDistritoJudicial;
	}

	public void setIdDistritoJudicial(Integer idDistritoJudicial) {
		this.idDistritoJudicial = idDistritoJudicial;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idProcesoElectoral, numeroCredencialElector);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOListaNominalDG other = (DTOListaNominalDG) obj;
		return Objects.equals(idProcesoElectoral, other.idProcesoElectoral)
				&& Objects.equals(numeroCredencialElector, other.numeroCredencialElector);
	}

	@Override
	public String toString() {
		return "DTOListaNominalDG [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso
				+ ", idParticipacion=" + idParticipacion + ", idTipoVoto=" + idTipoVoto + ", folio=" + folio
				+ ", idCorteLNAActualizar=" + idCorteLNAActualizar + ", idCiudadano=" + idCiudadano
				+ ", numeroCredencialElector=" + numeroCredencialElector + ", apellidoPaterno=" + apellidoPaterno
				+ ", apellidoMaterno=" + apellidoMaterno + ", nombre=" + nombre + ", idEstado=" + idEstado
				+ ", idDistrito=" + idDistrito + ", seccion=" + seccion + ", idCasilla=" + idCasilla + ", tipoCasilla="
				+ tipoCasilla + ", idMunicipio=" + idMunicipio + ", idLocalidad=" + idLocalidad + ", manzana=" + manzana
				+ ", edad=" + edad + ", nombreOrden=" + nombreOrden + ", mesNacimiento=" + mesNacimiento + ", sexo="
				+ sexo + ", entidadNacimiento=" + entidadNacimiento + ", idDistritoLocal=" + idDistritoLocal
				+ ", mesOrden=" + mesOrden + ", letraOrden=" + letraOrden + ", servidorPublico=" + servidorPublico
				+ ", idCircuitoJudicial=" + idCircuitoJudicial + ", idDistritoJudicial=" + idDistritoJudicial + "]";
	}
	
}
