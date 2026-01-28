package mx.ine.procprimerinsa.as;

import java.io.File;
import java.io.Serializable;

import mx.ine.procprimerinsa.dto.db.DTOFirmasCartas;
import mx.ine.procprimerinsa.util.Archivo;

public interface ASFirmaInterface extends Serializable {

	public boolean guardarArchivoGluster(DTOFirmasCartas firma, Archivo archivo, String relativePath);
	
	public File obtenerArchivoGluster(String ruta);
	
	public boolean guardarFirma(DTOFirmasCartas firma);

	public DTOFirmasCartas obtenerFirma(Integer idDetalle, Integer idParticipacion);

}
