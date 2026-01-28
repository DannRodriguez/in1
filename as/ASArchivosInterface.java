package mx.ine.procprimerinsa.as;

import java.io.Serializable;
import java.util.Map;

import mx.ine.procprimerinsa.dto.db.DTOArchivos;

public interface ASArchivosInterface extends Serializable {
	
	public Map<String, DTOArchivos> obtenerListadoArchivosProceso(DTOArchivos dtoArchivo);
	
	public boolean ejecutaGeneracionArchivos(Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idEstado, Integer id, Integer idParticipacion, String nombreProceso, String nombreDetalleProceso, 
			String nombreEstado, String nombreParticipacion, String modoEjecucion, String letra) 
			throws Exception;

	public void guardarArchivo(DTOArchivos archivo);
	
	public boolean eliminaRegistroArchivos(Integer idProcesoElectoral, Integer idDetalleProceso, 
			Integer idParticipacion);
	
}
