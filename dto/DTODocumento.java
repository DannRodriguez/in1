package mx.ine.procprimerinsa.dto;

import java.io.Serializable;

public class DTODocumento implements Serializable {
	
	private static final long serialVersionUID = 9077359502212802310L;
	public static final String FOLDER = "Folder";
	public static final String FILE = "File";
	
	private String nombre;
	private String tamanio;
	private String tipo;
	private String ruta;
	
	public DTODocumento(String nombre, String tamanio, String tipo) {
		super();
		this.nombre = nombre;
		this.tamanio = tamanio;
		this.tipo = tipo;
	}

	public DTODocumento(String nombre, String tamanio, String tipo, String ruta) {
		super();
		this.nombre = nombre;
		this.tamanio = tamanio;
		this.tipo = tipo;
		this.ruta = ruta;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getTamanio() {
		return tamanio;
	}
	
	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	@Override
	public String toString() {
		return this.ruta + this.nombre;
	}
}
