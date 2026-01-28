package mx.ine.procprimerinsa.form;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import mx.ine.procprimerinsa.dto.db.DTOArchivos;

public class FormArchivosCiudadanosInsaculados implements Serializable{

	private static final long serialVersionUID = 5982392246670640209L;
	
	private DTOArchivos dtoArchivo;
	
	private Map<Integer, DTOArchivos> dtoArchivos;
	
	public FormArchivosCiudadanosInsaculados() {
		super();
		this.dtoArchivo = new DTOArchivos();
		this.dtoArchivos = new HashMap<>();
	}
	
	public DTOArchivos getDtoArchivo() {
		return dtoArchivo;
	}
	
	public void setDtoArchivo(DTOArchivos dtoArchivo) {
		this.dtoArchivo = dtoArchivo;
	}
	
	public Map<Integer, DTOArchivos> getDtoArchivos() {
		return dtoArchivos;
	}
	
	public void setDtoArchivos(Map<Integer, DTOArchivos> dtoArchivos) {
		this.dtoArchivos = dtoArchivos;
	}
	
}
