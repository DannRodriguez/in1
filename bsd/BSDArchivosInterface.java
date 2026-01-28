package mx.ine.procprimerinsa.bsd;

import java.io.Serializable;
import java.util.Map;

import mx.ine.procprimerinsa.dto.db.DTOArchivos;

public interface BSDArchivosInterface extends Serializable{
	
	public Map<String, DTOArchivos> obtenerListadoArchivosProceso(DTOArchivos dtoArchivo);
		
}
