package mx.ine.procprimerinsa.dao;

import java.io.Serializable;
import java.util.List;

import mx.ine.procprimerinsa.dto.db.DTOArchivos;

public interface DAOArchivosInterface extends DAOGenericInterface<DTOArchivos, Serializable> {
	
	public List<DTOArchivos> consultaArchivos(DTOArchivos centros);
	
	public void guardarArchivo(DTOArchivos archivo);
	
	public void eliminarArchivo(DTOArchivos archivo);
	
}
