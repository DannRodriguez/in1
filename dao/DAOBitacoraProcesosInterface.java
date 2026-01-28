package mx.ine.procprimerinsa.dao;

import java.io.Serializable;
import java.util.List;

import mx.ine.procprimerinsa.dto.db.DTOBitacoraProcesos;

public interface DAOBitacoraProcesosInterface extends DAOGenericInterface<DTOBitacoraProcesos, Serializable> {
	
	public void guardarBitacora(DTOBitacoraProcesos bitacora);
	
	public List<DTOBitacoraProcesos> consultaBitacora(List<Integer> detalles);

	public void setBitacoraJobExecutionIdDefaultPorParticipacion(Integer idDetalleProceso, Integer idParticipacionGeografica);
		
}
