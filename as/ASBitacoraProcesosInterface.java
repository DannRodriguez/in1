package mx.ine.procprimerinsa.as;

import java.io.Serializable;
import java.util.List;

import mx.ine.procprimerinsa.dto.db.DTOBitacoraProcesos;

public interface ASBitacoraProcesosInterface extends Serializable {
	
	public List<DTOBitacoraProcesos> obtenerBitacoraProcesosRegistros(List<Integer> detalles);

	public boolean setJobExecutionIdDefaultPorParticipacion(Integer idDetalleProceso, Integer idParticipacion);
	
}
