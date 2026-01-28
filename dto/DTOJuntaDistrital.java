package mx.ine.procprimerinsa.dto;

import java.io.File;
import java.io.Serializable;

public class DTOJuntaDistrital implements Serializable{
	/**
	 * Serializado con java 11 25/09/2020
	 */
	private static final long serialVersionUID = 5990327717871335946L;
	
	private String name;
	private String nameEstado;
	private String domicilio;
	private String telefono;
	private File firma;
	
	public DTOJuntaDistrital() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public File getFirma() {
		return firma;
	}
	public void setFirma(File firma) {
		this.firma = firma;
	}
	public String getNameEstado() {
		return nameEstado;
	}
	public void setNameEstado(String nameEstado) {
		this.nameEstado = nameEstado;
	}
	@Override
	public String toString() {
		return "DTOJuntaDistrital [name=" + name + ", nameEstado=" + nameEstado + ", domicilio=" + domicilio
				+ ", telefono=" + telefono + ", firma=" + firma + "]";
	}

	
}
