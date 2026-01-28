package mx.ine.procprimerinsa.dto;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "responseDirJunta")
@XmlAccessorType (XmlAccessType.FIELD)
public class DTODireccionJuntaDistrital implements Serializable {
	
	private static final long serialVersionUID = -8317959713015762000L;

	private Integer idEstado;
	private Integer idDistritoFederal;
	private String nombreSede;
	private Integer idDomicilio;
	private String direccion;
	private String calle;
	private String numeroExterior;
	private String numeroInterior;
	private String colonia;
	private Integer codigoPostal;
	private String nombreEstado;
	private String nombreMunicipio;

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

	public String getNombreSede() {
		return nombreSede;
	}

	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
	}

	public Integer getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(Integer idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}

	@Override
	public String toString() {
		return "DTODireccionJuntaDistrital [idEstado=" + idEstado + ", idDistritoFederal=" + idDistritoFederal
				+ ", nombreSede=" + nombreSede + ", idDomicilio=" + idDomicilio + ", direccion=" + direccion
				+ ", calle=" + calle + ", numeroExterior=" + numeroExterior + ", numeroInterior=" + numeroInterior
				+ ", colonia=" + colonia + ", codigoPostal=" + codigoPostal + ", nombreEstado=" + nombreEstado
				+ ", nombreMunicipio=" + nombreMunicipio + "]";
	}
	
}
